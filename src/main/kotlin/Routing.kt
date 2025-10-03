package com.saulnunez

import Repository.UrlRepository
import com.saulnunez.DatasourceFactory.dataSource
import com.saulnunez.Models.UrlShortenInput
import com.saulnunez.Services.UrlService
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.*
import io.ktor.server.http.content.staticResources
import io.ktor.server.request.receive
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.SerializationException
import org.jetbrains.exposed.v1.jdbc.Database

val database = Database.connect(
    datasource = dataSource,
)
val repository = UrlRepository(database)
val service = UrlService(repository)

fun Application.configureRouting() {
    routing {
        staticResources("/", "content")

        get("/{shortUrl}") {
            val shortUrl = call.parameters["shortUrl"]

            if(shortUrl == null)
            {
                call.response.status(HttpStatusCode.NotFound)
                return@get
            }
            try{
                val redirectInfo = service.getUrlBySlug(shortUrl)

                call.respondRedirect(redirectInfo.originalUrl, permanent = false)
            } catch (e: Exception) {
                call.respond(HttpStatusCode.NotFound)
                return@get
            }
        }

        post("/") {
            try {
                val input = call.receive<UrlShortenInput>()
                val mappingInformation = service.addNewUrlMapping(input)
                call.respond(mappingInformation)
            } catch (ex: IllegalStateException) {
                call.respond(HttpStatusCode.BadRequest)
            } catch (ex: SerializationException) {
                call.respond(HttpStatusCode.BadRequest)
            }
        }
    }
}
