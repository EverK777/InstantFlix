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
        val result = dbConverters.fromStringToListInt("test")
        assertEquals(listOf("test"), result)
    }

    @Test
    fun testFromStringToListInt_multipleValues() {
        val result = dbConverters.fromStringToListInt("test1, test2, test3, test4, test5")
        assertEquals(listOf("test1", "test2", "test3", "test4", "test5"), result)
    }

    @Test
    fun testFromListIntToString_singleValue() {
        val result = dbConverters.fromListIntToString(listOf("test"))
        assertEquals("test", result)
    }

    @Test
    fun testFromListIntToString_multipleValues() {
        val result =
            dbConverters.fromListIntToString(listOf("test1", "test2", "test3", "test4", "test5"))
        assertEquals("test1, test2, test3, test4, test5", result)
    }

    @Test
    fun testFromListIntToString_emptyList() {
        val result = dbConverters.fromListIntToString(emptyList())
        assertEquals("", result)
    }
}
