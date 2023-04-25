package com.challenge.instantflix.core.utils

// TODO: ADD UNIT TEST
fun String?.emptyStringHandler(): String {
    return if (this.isNullOrEmpty()) {
        "-"
    } else {
        this
    }
}
