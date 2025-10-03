package com.saulnunez.Models

import kotlinx.serialization.Serializable

@Serializable
data class UrlShortenInput (var url: String)