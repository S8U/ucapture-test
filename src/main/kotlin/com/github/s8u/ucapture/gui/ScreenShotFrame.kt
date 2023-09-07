package com.github.s8u.ucapture.gui

import java.awt.*
import java.awt.event.*
import java.awt.image.BufferedImage
import java.io.File
import javax.imageio.ImageIO
import javax.swing.JFrame
import javax.swing.JLabel
import kotlin.math.abs

class ScreenShotFrame : JFrame() {

    companion object val BACKGROUND_ALPHA = 0.425F

    lateinit var image: BufferedImage

    val minX: Int
    val minY: Int
    val maxX: Int
    val maxY: Int

    var minusX: Int = 0
    var minusY: Int = 0

    var captureStartX: Int? = null
    var captureStartY: Int? = null

    var captureEndX: Int? = null
    var captureEndY: Int? = null

    init {
        this.isUndecorated = true

        // 모든 화면 계산
        val graphicsDevices = GraphicsEnvironment.getLocalGraphicsEnvironment().screenDevices

        minX = graphicsDevices.minOf { it.defaultConfiguration.bounds.minX }.toInt()
        minY = graphicsDevices.minOf { it.defaultConfiguration.bounds.minY }.toInt()
        maxX = graphicsDevices.maxOf { it.defaultConfiguration.bounds.maxX }.toInt()
        maxY = graphicsDevices.maxOf { it.defaultConfiguration.bounds.maxY }.toInt()

        if (minX < 0) {
            minusX = abs(minX)
        }

        if (minY < 0) {
            minusY = abs(minY)
        }

        val width = maxX - minX
        val height = maxY - minY

        this.bounds = Rectangle(minX, minY, width, height)
        image = Robot().createScreenCapture(bounds)

        val drawLabel = DrawLabel {
            it.drawImage(image, 0, 0, this)

            it.color = Color(1.0F, 1.0F, 1.0F, BACKGROUND_ALPHA)
            it.fillRect(0, 0, bounds.width, bounds.height)

            if (captureStartX != null && captureStartY != null) {
                val currentLocation = MouseInfo.getPointerInfo().location

                it.drawImage(
                    image,
                    captureStartX!!,
                    captureStartY!!,
                    currentLocation.x + minusX,
                    currentLocation.y + minusY,
                    captureStartX!!,
                    captureStartY!!,
                    currentLocation.x + minusX,
                    currentLocation.y + minusY,
                    this
                )
            }
        }

        drawLabel.addMouseListener(object : MouseAdapter() {
            override fun mousePressed(e: MouseEvent) {
                val currentLocation = MouseInfo.getPointerInfo().location

                captureStartX = currentLocation.x + minusX
                captureStartY = currentLocation.y + minusY
            }

            override fun mouseReleased(e: MouseEvent) {
                val currentLocation = MouseInfo.getPointerInfo().location

                captureEndX = currentLocation.x + minusX
                captureEndY = currentLocation.y + minusY

                // 스크린샷 생성
                saveScreenShot()
                System.exit(0)
            }
        })

        drawLabel.addMouseMotionListener(object : MouseMotionAdapter() {
            override fun mouseDragged(e: MouseEvent?) {
                if (captureStartX != null && captureStartY != null) {
                    drawLabel.repaint()
                }
            }
        })

        addWindowListener(object : WindowAdapter() {
            override fun windowDeactivated(e: WindowEvent) {
                this@ScreenShotFrame.dispose()
            }
        })

        add(drawLabel)


        this.isVisible = true
    }

    fun saveScreenShot() {
        if (captureStartX == null || captureStartY == null || captureEndX == null || captureEndY == null) {
            return
        }

        val width = captureEndX!! - captureStartX!!
        val height = captureEndY!! - captureStartY!!

        val saveImage = BufferedImage(width, height, BufferedImage.TYPE_INT_RGB)
        val graphics = saveImage.createGraphics()
        graphics.drawImage(
            image,
            0,
            0,
            captureEndX!! - captureStartX!!,
            captureEndY!! - captureStartY!!,
            captureStartX!!,
            captureStartY!!,
            captureEndX!!,
            captureEndY!!,
            null
        )

        ImageIO.write(saveImage, "png", File("screenshot.png"))
    }

}

class DrawLabel(val drawFunction: (g: Graphics2D) -> Unit) : JLabel() {

    override fun paintComponent(g: Graphics) {
        drawFunction(g as Graphics2D)
    }

}