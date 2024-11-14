package ai.mealz.analytics

import ai.mealz.analytics.utils.PlatformMap
import kotlinx.browser.window
import org.w3c.fetch.RequestInit

actual object SharedAnalytics : AbstractSharedAnalytics() {

    override fun sendRequest(event: PlausibleEvent) {
        window.fetch(
            PLAUSIBLE_URL,
            init = RequestInit(
                method = "POST",
                body = JSON.stringify(event)
            )
        )
    }

    actual fun sendPlausibleRequest(plausiblePath: String, path: String, journey: String, plausibleProps: PlatformMap<String, String?>) {
        this.buildAndSendPlausibleRequest(plausiblePath, path, journey, plausibleProps)
    }

    actual fun initSharedAnalytics(domain: String, version: String, onEmit: onEmitFunction) {
        this.init(domain, version, onEmit)
    }
}
