package com.saulnunez

import io.ktor.serialization.kotlinx.json.json
import io.ktor.server.application.*
import io.ktor.server.plugins.contentnegotiation.ContentNegotiation
import io.ktor.server.plugins.httpsredirect.*

fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
}

fun Application.module() {
    //install(HttpsRedirect)
    install(ContentNegotiation) {
        json()
    }
    configureRouting()
}
