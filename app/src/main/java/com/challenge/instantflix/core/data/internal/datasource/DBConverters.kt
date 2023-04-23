package com.challenge.instantflix.core.data.internal.datasource

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.challenge.instantflix.core.data.model.RequestCategory
import com.challenge.instantflix.core.data.model.TypeRequest
import java.lang.NumberFormatException

/**
 * Class that converts Classes to Classes that SQLITE supports
 */
@ProvidedTypeConverter
class DBConverters {

    /**
     * Converts a string to a [RequestCategory]
     *
     * @param value category name
     */
    @TypeConverter
    fun fromStringToRequestCategory(value: String): RequestCategory {
        return when (value) {
            RequestCategory.POPULAR.valueName -> RequestCategory.POPULAR
            RequestCategory.BY_GENRE.valueName -> RequestCategory.BY_GENRE
            RequestCategory.TOP_RATED.valueName -> RequestCategory.TOP_RATED
            else -> RequestCategory.TRENDING
        }
    }

    /**
     * Converts a [RequestCategory] to a String taking the value
     *
     * @param value [RequestCategory] to take the value as name
     */
    @TypeConverter
    fun requestCategoryToString(value: RequestCategory): String {
        return when (value) {
            RequestCategory.POPULAR -> RequestCategory.POPULAR.valueName
            RequestCategory.BY_GENRE -> RequestCategory.BY_GENRE.valueName
            RequestCategory.TOP_RATED -> RequestCategory.TOP_RATED.valueName
            RequestCategory.TRENDING -> RequestCategory.TRENDING.valueName
        }
    }

    /**
     * Converts a string value to a [TypeRequest].
     *
     * @param value The string value to be converted.
     * @return The corresponding [TypeRequest].
     */
    @TypeConverter
    fun fromStringToTypeRequest(value: String): TypeRequest {
        return when (value) {
            TypeRequest.MOVIE.type -> TypeRequest.MOVIE
            TypeRequest.TV_SHOW.type -> TypeRequest.TV_SHOW
            else -> TypeRequest.ALL
        }
    }

    /**
     * Converts a [TypeRequest] to its corresponding string value.
     *
     * @param value The [TypeRequest] object to be converted.
     * @return The corresponding string value.
     */
    @TypeConverter
    fun fromTypeRequestToString(value: TypeRequest): String {
        return when (value) {
            TypeRequest.MOVIE -> TypeRequest.MOVIE.type
            TypeRequest.TV_SHOW -> TypeRequest.TV_SHOW.type
            TypeRequest.ALL -> TypeRequest.ALL.type
        }
    }

    /**
     * Converts a string value to a list of integers.
     *
     * @param value The string value to be converted.
     * @return The corresponding list of integers.
     */

    @TypeConverter
    fun fromStringToListInt(value: String): List<Int> {
        return try {
            value.split(",").map { it.trim().toInt() }
        } catch (e: NumberFormatException) {
            emptyList()
        }
    }

    /**
     * Converts a list of integers to its corresponding string value.
     *
     * @param value The list of integers to be converted.
     * @return The corresponding string value.
     */
    @TypeConverter
    fun fromListIntToString(value: List<Int>): String {
        return value.joinToString()
    }
}
