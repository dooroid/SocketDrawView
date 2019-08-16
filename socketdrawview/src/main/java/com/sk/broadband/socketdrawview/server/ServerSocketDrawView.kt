package com.sk.broadband.socketdrawview.server

import android.content.Context
import android.util.AttributeSet
import com.sk.broadband.socketdrawview.DrawView


class ServerSocketDrawView(context: Context, attrs: AttributeSet) : DrawView(context, attrs),
    ServerSocketContract.View {

    override fun createServerSocket(port: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun disconnectServer() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun readBuffer() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}