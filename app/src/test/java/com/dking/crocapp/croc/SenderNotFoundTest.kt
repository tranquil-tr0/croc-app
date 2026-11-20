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

    private fun offer(
        opName: String = "Receive",
        engine: CrocEngine = CrocEngine.CURRENT,
        relayAddress: String = "",
        exitCode: Int = 1,
        output: List<String> = emptyRoomOutput
    ) = CrocProcess.shouldOfferLegacyForMissingSender(opName, engine, relayAddress, exitCode, output)

    @Test
    fun emptyRoomOnAutomaticRelay_offersLegacy() {
        assertTrue(offer())
    }

    @Test
    fun configuredRelay_doesNotOfferLegacy() {
        assertFalse(offer(relayAddress = "croc.schollz.com:9009"))
    }

    @Test
    fun legacyEngine_doesNotOfferLegacyAgain() {
        assertFalse(offer(engine = CrocEngine.LEGACY))
    }

    @Test
    fun storeReceiveAndSends_doNotOfferLegacy() {
        assertFalse(offer(opName = "ReceiveStore"))
        assertFalse(offer(opName = "Send"))
    }

    @Test
    fun otherFailures_doNotOfferLegacy() {
        assertFalse(offer(output = listOf("connecting...", "could not secure channel")))
        assertFalse(offer(exitCode = 0))
    }
}
