package Repository

import Models.UrlInformation
import org.jetbrains.exposed.v1.core.eq
import org.jetbrains.exposed.v1.jdbc.Database
import org.jetbrains.exposed.v1.jdbc.insert
import org.jetbrains.exposed.v1.jdbc.select
import org.jetbrains.exposed.v1.jdbc.transactions.transaction
import org.jetbrains.exposed.v1.jdbc.update
import java.util.UUID
import kotlin.uuid.Uuid

class UrlRepository(private  val db: Database) {
    fun getMappingById(id: UUID): String? = transaction(db) {
        UrlInformation
            .select(UrlInformation.originalUrl)
            .where { UrlInformation.id eq id }
            .map { it [UrlInformation.originalUrl] }
            .firstOrNull()
    }

    fun addNewMapping(mappedUrl: String) = transaction(db) {
        UrlInformation.insert {
            it[originalUrl] = mappedUrl
        } get UrlInformation.id
    }
}