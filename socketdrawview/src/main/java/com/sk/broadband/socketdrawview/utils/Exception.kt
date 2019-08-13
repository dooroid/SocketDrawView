package com.sk.broadband.socketdrawview.utils

object Exception {
    fun SystemCanvasNullException() {
        throw IllegalArgumentException("System Canvas is null")
    }

    fun TemporaryCanvasNullException() {
        throw IllegalArgumentException("Temporary Canvas is null")
    }
}