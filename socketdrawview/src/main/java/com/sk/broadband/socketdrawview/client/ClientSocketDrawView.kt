package com.sk.broadband.socketdrawview.client

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import com.sk.broadband.socketdrawview.DrawView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.io.IOException
import java.net.InetSocketAddress
import java.net.Socket
import java.net.SocketTimeoutException
import java.nio.channels.IllegalBlockingModeException


class ClientSocketDrawView(context: Context, attrs: AttributeSet) : DrawView(context, attrs),
    ClientSocketContract.View {

    private var socket: Socket = Socket()

    private lateinit var ipAddress: String
    private var port: Int = 3000

    override var onClosedListener: (()->Unit)? = null
    override var onIOExceptionListener: (()->Unit)? = null
    override var onSocketTimeoutExceptionListener: (()->Unit)? = null
    override var onIllegalBlockingModeExceptionListener: (()->Unit)? = null
    override var onIllegalArgumentExceptionListener: (()->Unit)? = null


    override fun connectSocket(ip: String, port: Int) {
        ipAddress = ip
        this.port = port

        GlobalScope.launch(Dispatchers.Default) {
            try {
                socket.connect(InetSocketAddress(ip, port))
            } catch (e: IOException) {
                Log.d("ERROR", "IOException")
                onIOExceptionListener?.invoke()
            } catch (e: SocketTimeoutException) {
                Log.d("ERROR", "SocketTimeoutException")
                onSocketTimeoutExceptionListener?.invoke()
            } catch (e: IllegalBlockingModeException) {
                Log.d("ERROR", "IllegalBlockingModeException")
                onIllegalBlockingModeExceptionListener?.invoke()
            } catch (e: IllegalArgumentException){
                Log.d("ERROR", "IllegalArgumentExcept")
                onIllegalArgumentExceptionListener?.invoke()
            }
        }
    }

    override fun disconnectClient() {
        val data = ByteArray(21)
        data[0] = 9
        sendServer(data)

        GlobalScope.launch(Dispatchers.Default) {
            socket.close()
        }
    }

    override fun drawServer(event: MotionEvent) {
        sendServer(toByteArray(event))
    }

    override fun undoPrevServer() {
        val data = ByteArray(21)
        data[0] = 2
        sendServer(data)
    }

    override fun eraseAllServer() {
        val data = ByteArray(21)
        data[0] = 3
        sendServer(data)
    }

    private fun sendServer(data: ByteArray) {
        GlobalScope.launch(Dispatchers.Default) {
            if (!socket.isConnected || socket.isClosed) {
                Log.d("ERROR", "Socket is not Connected")
                onClosedListener?.invoke()
            } else {
                val outputStream = socket.getOutputStream()
                outputStream.write(data)
            }
        }
    }


    //    (Mode) 1Byte, (Action) 4Byte, (X coordinate) 4Byte, (Y coordinate) 4Byte, (Color) 4Byte, (Stroke Width) 4Byte
    fun toByteArray(event: MotionEvent?): ByteArray {
        val eventByteArray = ByteArray(21)

        val action = event!!.action
        val x = recoordinateX(event.x)
        val y = recoordinateY(event.y)

        eventByteArray[0] = if (isPath) 1 else 0
        insertIntToByteArray(1, action, eventByteArray)
        insertIntToByteArray(5, x, eventByteArray)
        insertIntToByteArray(9, y, eventByteArray)
        insertIntToByteArray(13, pathColor, eventByteArray)
        insertIntToByteArray(17, pathStrokeWidth.toInt(), eventByteArray)

        return eventByteArray
    }

    fun insertIntToByteArray(startIndex: Int, value: Int, array: ByteArray) {
        array[startIndex] = (value shr 24).toByte()
        array[startIndex+1] = (value shr 16).toByte()
        array[startIndex+2] = (value shr 8).toByte()
        array[startIndex+3] = value.toByte()
    }

    fun recoordinateX(curX: Float): Int {
        val location = IntArray(2)
        getLocationInWindow(location)
        return (((curX - location[0].toFloat()) / width) * 100000).toInt()
    }

    fun recoordinateY(curY: Float): Int {
        val location = IntArray(2)
        getLocationInWindow(location)
        return (((curY - location[1].toFloat()) / height) * 100000).toInt()
    }
}