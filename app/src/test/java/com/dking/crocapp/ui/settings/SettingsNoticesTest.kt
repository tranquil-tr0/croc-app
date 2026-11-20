package com.dking.crocapp.ui.settings

import com.dking.crocapp.data.preferences.UserPreferencesRepository.CrocPreferences
import com.dking.crocapp.ui.settings.SettingsNotice.HTTP_PROXY_IGNORED
import com.dking.crocapp.ui.settings.SettingsNotice.LEGACY_IGNORES_TRANSPORT
import com.dking.crocapp.ui.settings.SettingsNotice.LOCAL_ONLY_IGNORES_RELAY
import com.dking.crocapp.ui.settings.SettingsNotice.LOCAL_ONLY_IGNORES_TRANSPORT
import com.dking.crocapp.ui.settings.SettingsNotice.OLD_DEFAULT_RELAY
import com.dking.crocapp.ui.settings.SettingsNotice.RELAY6_IGNORED
import org.junit.Assert.assertEquals
import org.junit.Test

class SettingsNoticesTest {

    @Test
    fun defaults_haveNoNotices() {
        assertEquals(emptyList<SettingsNotice>(), settingsNotices(CrocPreferences()))
    }

    @Test
    fun oldDefaultRelay_isFlagged() {
        assertEquals(listOf(OLD_DEFAULT_RELAY), settingsNotices(CrocPreferences(relayAddress = " croc.schollz.com:9009 ")))
        assertEquals(emptyList<SettingsNotice>(), settingsNotices(CrocPreferences(relayAddress = "relay.example:9009")))
    }

    @Test
    fun relayAndRelay6_flagsRelay6Ignored() {
        assertEquals(
            listOf(RELAY6_IGNORED),
            settingsNotices(CrocPreferences(relayAddress = "relay.example:9009", relay6Address = "[2001:db8::1]:9009"))
        )
        assertEquals(emptyList<SettingsNotice>(), settingsNotices(CrocPreferences(relay6Address = "[2001:db8::1]:9009")))
    }

    @Test
    fun bothProxies_flagsHttpProxyIgnored() {
        assertEquals(
            listOf(HTTP_PROXY_IGNORED),
            settingsNotices(CrocPreferences(socks5Proxy = "127.0.0.1:9050", httpProxy = "127.0.0.1:8080"))
        )
    }

    @Test
    fun localOnly_flagsIgnoredRelayAndTransport() {
        assertEquals(
            listOf(LOCAL_ONLY_IGNORES_RELAY, LOCAL_ONLY_IGNORES_TRANSPORT),
            settingsNotices(
                CrocPreferences(forceLocal = true, socks5Proxy = "127.0.0.1:9050", transferTransport = "derp", tryLegacyFirst = true)
            )
        )
        assertEquals(emptyList<SettingsNotice>(), settingsNotices(CrocPreferences(forceLocal = true)))
    }

    @Test
    fun legacyFirstWithTransport_flagsTransportIgnoredByLegacy() {
        assertEquals(
            listOf(LEGACY_IGNORES_TRANSPORT),
            settingsNotices(CrocPreferences(tryLegacyFirst = true, transferTransport = "relay"))
        )
    }
}
