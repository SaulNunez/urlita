package com.saulnunez

import org.sqids.Sqids

object SlugGeneration {
    private val generator = Sqids(minLength = 5)

    fun generateSlug(original_url: String) = generator.encode(original_url)
}