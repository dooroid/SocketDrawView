package com.sk.broadband.socketdrawview.client

import android.content.Context
import android.util.AttributeSet
import com.sk.broadband.socketdrawview.DrawView


class ClientSocketDrawView(context: Context, attrs: AttributeSet) : DrawView(context, attrs),
    ClientSocketContract.View {

    override fun connectSocket(ip: String, port: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun disconnectClient() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun writeBuffer(data: ByteArray) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}