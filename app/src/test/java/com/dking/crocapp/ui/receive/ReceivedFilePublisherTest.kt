package com.dking.crocapp.ui.receive

import org.junit.Assert.assertEquals
import org.junit.Test

class ReceivedFilePublisherTest {

    @Test
    fun `single file goes straight into the picked folder`() {
        assertEquals(emptyList<String>(), ReceivedFilePublisher.safSubdirSegments("photo.jpg"))
    }

    @Test
    fun `folder transfer keeps only its own subdirectories`() {
        assertEquals(
            listOf("myfolder", "sub"),
            ReceivedFilePublisher.safSubdirSegments("myfolder/sub/file.txt")
        )
    }

}
