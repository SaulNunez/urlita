package com.saulnunez.Services

import Repository.UrlRepository
import com.mayakapps.kache.InMemoryKache
import com.mayakapps.kache.KacheStrategy
import com.saulnunez.Models.UrlInformationDao
import com.saulnunez.Models.UrlShortenInput
import com.saulnunez.SlugGeneration

class UrlService(private val repository: UrlRepository) {
    private val cache = InMemoryKache<String, UrlInformationDao>(maxSize= 1000) {
        strategy = KacheStrategy.LRU
    }

    suspend fun getUrlBySlug(slug: String): UrlInformationDao {
        val cached = cache.getOrPut(slug) {
            val dbRecord = repository.getUrlBySlug(slug)

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
        val slug = SlugGeneration.generateSlug(input.url)

        repository.addNewMapping(input.url, slug)

        return UrlInformationDao(slug, input.url)
    }
}