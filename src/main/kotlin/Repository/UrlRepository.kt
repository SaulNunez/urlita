package Repository

import Models.UrlInformation
import org.jetbrains.exposed.v1.core.eq
import org.jetbrains.exposed.v1.jdbc.Database
import org.jetbrains.exposed.v1.jdbc.insert
import org.jetbrains.exposed.v1.jdbc.select
import org.jetbrains.exposed.v1.jdbc.transactions.transaction

class UrlRepository(private  val db: Database) {
    fun getUrlBySlug(slug: String): String? = transaction(db) {
        UrlInformation
            .select(UrlInformation.originalUrl)
            .where { UrlInformation.slug eq slug }
            .map { it [UrlInformation.originalUrl] }
            .firstOrNull()
    }

    fun addNewMapping(mappedUrl: String, createdSlug: String): String = transaction(db) {
        UrlInformation
            .insert {
            it[originalUrl] = mappedUrl
            it[slug] = createdSlug
        }
            .get(UrlInformation.slug)
    }
}