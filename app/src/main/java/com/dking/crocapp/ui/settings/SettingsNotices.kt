package com.dking.crocapp.ui.settings

import androidx.annotation.StringRes
import com.dking.crocapp.R
import com.dking.crocapp.data.preferences.UserPreferencesRepository.CrocPreferences

/** A setting that is ignored or overridden by another, explained to the user without blocking them. */
enum class SettingsNotice(@StringRes val message: Int) {
    OLD_DEFAULT_RELAY(R.string.settings_notice_old_default_relay),
    RELAY6_IGNORED(R.string.settings_notice_relay6_ignored),
    HTTP_PROXY_IGNORED(R.string.settings_notice_http_proxy_ignored),
    LOCAL_ONLY_IGNORES_RELAY(R.string.settings_notice_local_only_ignores_relay),
    LOCAL_ONLY_IGNORES_TRANSPORT(R.string.settings_notice_local_only_ignores_transport),
}

/** The relay croc used before 11.2; peers on croc 11.2+ meet on the automatic relays instead. */
private const val OLD_DEFAULT_RELAY_HOST = "croc.schollz.com"

fun settingsNotices(prefs: CrocPreferences): List<SettingsNotice> = buildList {
    val relay = prefs.relayAddress.isNotBlank()
    val relay6 = prefs.relay6Address.isNotBlank()
    val socks5 = prefs.socks5Proxy.isNotBlank()
    val http = prefs.httpProxy.isNotBlank()
    val customTransport = prefs.transferTransport != "auto"

    if (prefs.relayAddress.trim().substringBefore(':').equals(OLD_DEFAULT_RELAY_HOST, ignoreCase = true)) {
        add(SettingsNotice.OLD_DEFAULT_RELAY)
    }
    if (relay && relay6) add(SettingsNotice.RELAY6_IGNORED)
    if (socks5 && http) add(SettingsNotice.HTTP_PROXY_IGNORED)
    if (prefs.forceLocal) {
        if (relay || relay6 || socks5 || http) add(SettingsNotice.LOCAL_ONLY_IGNORES_RELAY)
        if (customTransport) add(SettingsNotice.LOCAL_ONLY_IGNORES_TRANSPORT)
    }
}
