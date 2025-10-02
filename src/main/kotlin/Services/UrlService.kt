package com.saulnunez.Services

import Repository.UrlRepository
import com.mayakapps.kache.InMemoryKache
import com.mayakapps.kache.KacheStrategy
import com.saulnunez.Models.UrlInformationDao
import com.saulnunez.Models.UrlShortenInput
import com.saulnunez.SlugGeneration
import java.util.UUID

class UrlService(private val repository: UrlRepository) {
    private val cache = InMemoryKache<String, UrlInformationDao>(maxSize= 1000) {
        strategy = KacheStrategy.LRU
    }

    suspend fun getUrlBySlug(slug: String): UrlInformationDao {
        val cached = cache.getOrPut(slug) {
            val id = SlugGeneration.decodeSlug(slug).toUUID()
            val dbRecord = repository.getMappingById(id)

            if (dbRecord == null)
            {
                return@getOrPut null
            }

            return@getOrPut UrlInformationDao(slug, dbRecord)
        }

        if(cached == null){
            throw IllegalStateException("Not found")
        }

        return cached
    }

    fun addNewUrlMapping(input: UrlShortenInput): UrlInformationDao {
        val id = repository.addNewMapping(input.url).value

        val slug = SlugGeneration.generateSlug(id.toByteArray().toList())

        return UrlInformationDao(slug, input.url)
    }
}

private fun UUID.toByteArray(): List<Long> {
    return listOf(this.mostSignificantBits, this.leastSignificantBits)
}

private fun List<Long>.toUUID(list: List<Long>): UUID {
    if (list.size != 2) throw Exception("List is too long or too short")
    return UUID(list[0], list[1])
}
