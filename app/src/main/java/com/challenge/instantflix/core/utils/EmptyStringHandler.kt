package com.challenge.instantflix.core.utils

fun String?.emptyStringHandler(): String {
    return if (this.isNullOrEmpty()) {
        "-"
    } else {
        this
    }
}
