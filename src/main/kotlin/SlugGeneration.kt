package com.saulnunez

import org.sqids.Sqids

object SlugGeneration {
    private val generator = Sqids(minLength = 5)

    fun generateSlug(entry_id: List<Long>) = generator.encode(entry_id)
    fun decodeSlug(slug: String) = generator.decode(slug)
}