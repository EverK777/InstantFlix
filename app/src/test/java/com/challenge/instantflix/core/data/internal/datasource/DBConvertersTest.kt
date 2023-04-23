package com.challenge.instantflix.core.data.internal.datasource

import com.challenge.instantflix.core.data.model.RequestCategory
import com.challenge.instantflix.core.data.model.TypeRequest
import junit.framework.TestCase.assertEquals
import org.junit.Before
import org.junit.Test

class DBConvertersTest {

    private lateinit var dbConverters: DBConverters

    @Before
    fun setUp() {
        dbConverters = DBConverters()
    }

    @Test
    fun testFromStringToRequestCategory_popular() {
        val result = dbConverters.fromStringToRequestCategory(RequestCategory.POPULAR.valueName)
        assertEquals(RequestCategory.POPULAR, result)
    }

    @Test
    fun testFromStringToRequestCategory_byGenre() {
        val result = dbConverters.fromStringToRequestCategory(RequestCategory.BY_GENRE.valueName)
        assertEquals(RequestCategory.BY_GENRE, result)
    }

    @Test
    fun testFromStringToRequestCategory_topRated() {
        val result = dbConverters.fromStringToRequestCategory(RequestCategory.TOP_RATED.valueName)
        assertEquals(RequestCategory.TOP_RATED, result)
    }

    @Test
    fun testFromStringToRequestCategory_trending() {
        val result = dbConverters.fromStringToRequestCategory("some_invalid_value")
        assertEquals(RequestCategory.TRENDING, result)
    }

    @Test
    fun testRequestCategoryToString_popular() {
        val result = dbConverters.requestCategoryToString(RequestCategory.POPULAR)
        assertEquals(RequestCategory.POPULAR.valueName, result)
    }

    @Test
    fun testRequestCategoryToString_byGenre() {
        val result = dbConverters.requestCategoryToString(RequestCategory.BY_GENRE)
        assertEquals(RequestCategory.BY_GENRE.valueName, result)
    }

    @Test
    fun testRequestCategoryToString_topRated() {
        val result = dbConverters.requestCategoryToString(RequestCategory.TOP_RATED)
        assertEquals(RequestCategory.TOP_RATED.valueName, result)
    }

    @Test
    fun testRequestCategoryToString_trending() {
        val result = dbConverters.requestCategoryToString(RequestCategory.TRENDING)
        assertEquals(RequestCategory.TRENDING.valueName, result)
    }

    @Test
    fun testFromStringToTypeRequest_all() {
        val result = dbConverters.fromStringToTypeRequest(TypeRequest.ALL.type)
        assertEquals(TypeRequest.ALL, result)
    }

    @Test
    fun testFromStringToTypeRequest_movie() {
        val result = dbConverters.fromStringToTypeRequest(TypeRequest.MOVIE.type)
        assertEquals(TypeRequest.MOVIE, result)
    }

    @Test
    fun testFromStringToTypeRequest_tvShow() {
        val result = dbConverters.fromStringToTypeRequest(TypeRequest.TV_SHOW.type)
        assertEquals(TypeRequest.TV_SHOW, result)
    }

    @Test
    fun testFromTypeRequestToString_all() {
        val result = dbConverters.fromTypeRequestToString(TypeRequest.ALL)
        assertEquals(TypeRequest.ALL.type, result)
    }

    @Test
    fun testFromTypeRequestToString_movie() {
        val result = dbConverters.fromTypeRequestToString(TypeRequest.MOVIE)
        assertEquals(TypeRequest.MOVIE.type, result)
    }

    @Test
    fun testFromTypeRequestToString_tvShow() {
        val result = dbConverters.fromTypeRequestToString(TypeRequest.TV_SHOW)
        assertEquals(TypeRequest.TV_SHOW.type, result)
    }

    @Test
    fun testFromStringToListInt_singleValue() {
        val result = dbConverters.fromStringToListInt("10")
        assertEquals(listOf(10), result)
    }

    @Test
    fun testFromStringToListInt_multipleValues() {
        val result = dbConverters.fromStringToListInt("10, 20, 30, 40, 50")
        assertEquals(listOf(10, 20, 30, 40, 50), result)
    }

    @Test
    fun testFromStringToListInt_invalidValue() {
        val result = dbConverters.fromStringToListInt("10, 20, 30, abc, 50")
        assertEquals(emptyList<Int>(), result)
    }

    @Test
    fun testFromListIntToString_singleValue() {
        val result = dbConverters.fromListIntToString(listOf(10))
        assertEquals("10", result)
    }

    @Test
    fun testFromListIntToString_multipleValues() {
        val result = dbConverters.fromListIntToString(listOf(10, 20, 30, 40, 50))
        assertEquals("10, 20, 30, 40, 50", result)
    }

    @Test
    fun testFromListIntToString_emptyList() {
        val result = dbConverters.fromListIntToString(emptyList())
        assertEquals("", result)
    }
}
