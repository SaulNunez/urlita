package Models

import org.jetbrains.exposed.v1.core.Table
import org.jetbrains.exposed.v1.core.dao.id.UUIDTable

object UrlInformation: UUIDTable("Urls") {
    val originalUrl =  varchar("original_url", 2000)
}