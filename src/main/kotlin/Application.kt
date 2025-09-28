package com.saulnunez

import io.ktor.server.application.*
import io.ktor.server.plugins.httpsredirect.*

fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
}

fun Application.module() {
    install(HttpsRedirect)
    configureRouting()
}
