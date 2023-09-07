package com.github.s8u.ucapture

import com.github.kwhat.jnativehook.GlobalScreen
import com.github.s8u.ucapture.global.GlobalScreenCaptureKeyListener
import com.github.s8u.ucapture.gui.ScreenShotFrame
import java.awt.GraphicsEnvironment
import java.awt.MouseInfo
import java.util.Scanner
import javax.swing.JFrame

fun main() {

/*    while (true) {
        println(MouseInfo.getPointerInfo().location)
        Thread.sleep(1000L / 20L)
    }*/

    ScreenShotFrame()

    GlobalScreen.registerNativeHook()
    GlobalScreen.addNativeKeyListener(GlobalScreenCaptureKeyListener())

}