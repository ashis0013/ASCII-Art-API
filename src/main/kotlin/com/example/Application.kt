package com.example

import io.ktor.server.engine.*
import io.ktor.server.netty.*
import com.example.plugins.*
import io.ktor.application.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import org.opencv.core.Core

fun main() {
    System.loadLibrary(Core.NATIVE_LIBRARY_NAME)
    embeddedServer(Netty, port = 8080, host = "localhost") {
        routing {
            get("/") {
                call.respondText("Hello")
            }
            post("/ascii-art") {
                val params = call.receiveParameters()
                val imgString = params["img"].toString()
                val maxWidth = params["width"].toString()
                val a = Asciify(imgString,maxWidth.toInt())
                val res = a.getAsciiArt()
                call.respondText(res)
            }
        }
    }.start(wait = true)
}
