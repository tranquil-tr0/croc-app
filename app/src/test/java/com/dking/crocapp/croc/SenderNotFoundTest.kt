package com.dking.crocapp.croc

import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class SenderNotFoundTest {

    // Captured from croc 11.6.5 receiving a code nobody is sending on the automatic relay.
    private val emptyRoomOutput = listOf(
        "connecting...",
        "looking for sender...",
        "local discovery failed: read tcp 10.0.0.2:53366->46.225.4.16:9009: i/o timeout"
    )

    @Test
    fun emptyRoom_isSenderNotFound() {
        assertTrue(CrocProcess.isSenderNotFound(1, emptyRoomOutput))
    }

    @Test
    fun otherFailures_areNotSenderNotFound() {
        assertFalse(CrocProcess.isSenderNotFound(1, listOf("connecting...", "could not secure channel")))
        assertFalse(CrocProcess.isSenderNotFound(0, emptyRoomOutput))
    }
}
