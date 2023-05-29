package com.theblackbit.instantflix.core.utils

import android.content.Context
import java.io.InputStream

class JsonFileReader(private val context: Context) {

    fun getJsonFromAsset(fileName: String): String {
        val stream: InputStream = context.assets.open(fileName)
        val size = stream.available()
        val buffer = ByteArray(size)
        stream.read(buffer)
        stream.close()

        return String(buffer)
    }
}
