package com.raven.home.presentation.model

enum class Period(val path: String, val printableName: String) {
    ONE_DAY("1", "from past day"),
    SEVEN_DAYS("7", "from past week"),
    THIRTY_DAYS("30", "from past month")
}
