package ai.mealz.analytics

import ai.mealz.analytics.utils.PlatformMap
import kotlinx.browser.window
import org.w3c.fetch.Headers
import org.w3c.fetch.RequestInit

actual object SharedAnalytics : AbstractSharedAnalytics() {

    override fun sendRequest(event: PlausibleEvent) {
        val analyticsUrl = getAnalyticsUrl()
        val headers = Headers()
        headers.set("Content-Type", "application/json")
        val body = JSON.stringify(event)
        val init = RequestInit(
            method = "POST",
            headers,
            body
        )
        window.fetch(PLAUSIBLE_URL, init)
        window.fetch(analyticsUrl, init)
    }

    actual fun sendPlausibleRequest(plausiblePath: String, path: String, journey: String, plausibleProps: PlatformMap<String, String?>) {
        this.buildAndSendPlausibleRequest(plausiblePath, path, journey, plausibleProps)
    }

    actual fun initSharedAnalytics(domain: String, version: String, onEmit: onEmitFunction, environment: String) {
        this.init(domain, version, onEmit, environment)
    }
}
