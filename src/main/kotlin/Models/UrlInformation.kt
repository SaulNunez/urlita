package Models

import org.jetbrains.exposed.v1.core.Table

object UrlInformation: Table("Urls") {
    val id = integer("id").autoIncrement()
    val originalUrl =  varchar("original_url", 2000)
    val slug = varchar("slug", 10)
}