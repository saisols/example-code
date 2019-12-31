package net.mikeski.develop.springclouddataflowfirstbatchjob.steps

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

internal class SquaringIntProcessorTest {

    @Test
    fun process() {
        val sip = SquaringIntProcessor()
        assertEquals(1, sip.process(1))
        assertEquals(4, sip.process(2))
        assertEquals(100, sip.process(10))
    }
}