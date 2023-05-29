package com.theblackbit.instantflix.core.utils

fun String.slashReplacer(): String {
    return this.replace("/", "%2F")
}
