package com.sk.broadband.socketdrawview.server

import android.content.Context
import android.util.AttributeSet
import com.sk.broadband.socketdrawview.DrawContract
import com.sk.broadband.socketdrawview.DrawView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import java.net.InetSocketAddress
import java.net.ServerSocket
import java.net.Socket


class ServerSocketDrawView(context: Context, attrs: AttributeSet) : DrawView(context, attrs),
    ServerSocketContract.View {

    lateinit var socket: Socket
    lateinit var serverSocket: ServerSocket

    var port: Int = 3000

    override fun createServerSocket(port: Int) {
        this.port = port

        CoroutineScope(Dispatchers.Main).launch {
            bindServerSocket()
            acceptSocket()
            while (socket.isConnected) {
                reflectOnScreen(readBuffer())
            }
        }
    }

    private suspend fun bindServerSocket() {
        CoroutineScope(Dispatchers.Default).async {
            serverSocket = ServerSocket()
            serverSocket.bind(InetSocketAddress(port))
        }.await()
    }

    private suspend fun acceptSocket() {
        CoroutineScope(Dispatchers.Default).async {
            socket = serverSocket.accept()
        }.await()
    }

    private suspend fun readBuffer(): ByteArray {

        val buffer = ByteArray(21)

        CoroutineScope(Dispatchers.Default).async {
            val inputStream = socket.getInputStream()
            inputStream.read(buffer)
        }.await()

        return buffer
    }

    private fun reflectOnScreen(data: ByteArray) {
        val mode = data[0].toInt()

        when (mode) {
            0 -> { isPath = false }
            1 -> { isPath = true }
            2 -> {
                undoPrev()
                return
            }
            3 -> {
                eraseAll()
                return
            }
        }

        draw(transfromRawData(data))
    }

//    (Mode) 1Byte, (Action) 4Byte, (X coordinate) 4Byte, (Y coordinate) 4Byte, (Color) 4Byte, (Stroke Width) 4Byte
    private fun transfromRawData(data: ByteArray): DrawContract.View.TouchInfo {

        pathColor = toNumber(13, data)
        pathStrokeWidth = toNumber(17, data).toFloat()

        return DrawContract.View.TouchInfo(
            recoordinateX(toNumber(5, data)),
            recoordinateY(toNumber(9, data)),
            toNumber(1, data))
    }

    private fun toNumber(startIndex: Int, array: ByteArray): Int {
        return ((array[startIndex].toInt() and 0xff) shl 24) or
                ((array[startIndex+1].toInt() and 0xff) shl 16) or
                ((array[startIndex+2].toInt() and 0xff) shl 8) or
                (array[startIndex+3].toInt() and 0xff)
    }

    private fun recoordinateX(curX: Int): Float {
        val location = IntArray(2)
        getLocationInWindow(location)
        return ((curX.toFloat() / 100000) * location[0]) - location[0]
    }

    private fun recoordinateY(curY: Int): Float {
        val location = IntArray(2)
        getLocationInWindow(location)
        return ((curY.toFloat() / 100000) * location[1]) - location[1]
    }

    override fun disconnectServer() {
        socket.close()
        serverSocket.close()
    }

    override fun getIPAddress(): String {
        return serverSocket.inetAddress.hostAddress
    }
}