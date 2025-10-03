package com.saulnunez.Models

import kotlinx.serialization.Serializable

@Serializable
data class UrlInformationDao(
    val slug: String,
    val originalUrl: String
)
