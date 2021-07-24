package com.example

import io.ktor.util.*
import org.opencv.core.Mat
import org.opencv.core.MatOfByte
import org.opencv.core.Size
import org.opencv.imgcodecs.Imgcodecs
import org.opencv.imgproc.Imgproc.*
import java.util.*


class Asciify(private val base64img : String, private val maxWidth : Int) {
    var height = 0.0
    var width = 0.0
    var src : Mat = Mat()

    init {
        var bytes = Base64.getDecoder().decode(base64img)
        src = Imgcodecs.imdecode(MatOfByte(*bytes), Imgcodecs.IMREAD_UNCHANGED)
        val img = src.clone()
        val imgSize = img.size()
        val reductionFactor = maxWidth.toDouble()/imgSize.width
        width = maxWidth.toDouble()
        height = imgSize.height*reductionFactor
    }

    fun preProccess() : Mat {
        val img = src.clone()
        resize(img, img,  Size(width, height))
        cvtColor(img,img, COLOR_BGR2GRAY)
        return img
    }
    fun getAsciiArt(): String {
        val img = preProccess()
        var text = ""
        val threshList = listOf<Double>(80.0,120.0,150.0,170.0)
        val asciiColors = listOf<Char>('.','-','+','@')
        var res = mutableListOf<Char>()
        for (i in 0..width.toInt()*height.toInt()) {
            res.add(' ')
        }
        for ((i, T) in threshList.withIndex()) {
            val threshImg = img.clone()
            threshold(img,threshImg,T,255.0, THRESH_BINARY)
            for(x in 0 until width.toInt()) {
                for(y in 0 until height.toInt()) {
                    val v = threshImg.get(x,y)[0]
                    if(v == 255.0) {
                        res[width.toInt()*x+y] = asciiColors[i]
                    }
                }
            }
        }
        for ((i,c) in res.withIndex()) {
            if(i%(width.toInt()) == 0) {
                text = "$text\n"
            }
            text = "$text${c}"
        }
        return text
    }
}
