package com.raven.home.presentation.model

enum class PopularType(val path: String, val printableName: String) {
    VIEWED("viewed", "Most viewed"),
    EMAILED("emailed", "Most emailed"),
    SHARED("shared", "Most shared");
}