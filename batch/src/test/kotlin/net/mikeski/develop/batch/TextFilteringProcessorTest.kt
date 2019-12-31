package net.mikeski.develop.batch

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

internal class TextFilteringProcessorTest {
    var textFilteringProcessor = TextFilteringProcessor()

    @BeforeEach
    fun setUp() {
        textFilteringProcessor = TextFilteringProcessor()
    }

    @Test
    fun testProcess_NoBadWord() {
        assertEquals("this is crrap" ,textFilteringProcessor.process("this is crrap"))
    }

    @Test
    fun testProcess_OneBadWord() {
        assertEquals("this is ****" ,textFilteringProcessor.process("this is crap"))
    }

    @Test
    fun testProcess_OneBadWordCase() {
        assertEquals("this is ****" ,textFilteringProcessor.process("this is crAP"))
    }

    @Test
    fun testProcess_RepeatedBadWord() {
        assertEquals("****, this is **** ****" ,textFilteringProcessor.process("crap, this is crap crap"))
    }

    @Test
    fun testProcess_RepeatedBadWordCase() {
        assertEquals("****, this is **** ****" ,textFilteringProcessor.process("Crap, this is CRAP cRap"))
    }

    @Test
    fun testProcess_RepeatedBadWordOtherBadWord() {
        assertEquals("****, this is **** ****" ,textFilteringProcessor.process("damn, this is crap damn"))
    }

    @Test
    fun testProcess_EmptyString() {
        assertEquals("" ,textFilteringProcessor.process(""))
    }
}