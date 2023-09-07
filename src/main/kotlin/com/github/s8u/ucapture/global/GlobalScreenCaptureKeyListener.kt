package com.github.s8u.ucapture.global

import com.github.kwhat.jnativehook.keyboard.NativeKeyEvent
import com.github.kwhat.jnativehook.keyboard.NativeKeyListener

class GlobalScreenCaptureKeyListener : NativeKeyListener {

    override fun nativeKeyPressed(nativeEvent: NativeKeyEvent) {
        println(NativeKeyEvent.getKeyText(nativeEvent.keyCode))
    }

}