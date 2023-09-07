package com.github.s8u.ucapture

import com.github.s8u.ucapture.gui.ScreenShotFrame
import java.awt.GraphicsEnvironment
import java.awt.MouseInfo
import java.util.Scanner
import javax.swing.JFrame

fun main() {

    val frames = arrayListOf<JFrame>()

/*    while (true) {
        println(MouseInfo.getPointerInfo().location)
        Thread.sleep(1000L / 20L)
    }*/

    ScreenShotFrame()

}