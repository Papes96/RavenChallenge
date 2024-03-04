package com.raven.home.presentation.model

data class Article(
    val id: Long,
    val url: String,
    val title: String,
    val byline: String,
    val content: String,
    val media: String,
    val date: String
)