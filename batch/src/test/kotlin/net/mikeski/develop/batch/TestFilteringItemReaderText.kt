package net.mikeski.develop.batch

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

internal class TestFilteringItemReaderText {

    @Test
    fun read() {
        val reader = TextFilteringItemReader()
        val items = reader.items
        items.forEach{ item -> assertEquals(item, reader.read())}
        assertNull(reader.read())
        assertNull(reader.read())
        assertNull(reader.read())
        assertNull(reader.read())
    }
}