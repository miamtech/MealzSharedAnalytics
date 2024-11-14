package ai.mealz.analytics

import ai.mealz.analytics.utils.PlatformMap

expect object SharedAnalytics {
    internal var abTestKey: String?
    internal var affiliate: String?
    fun initSharedAnalytics(domain: String, version: String, onEmit: onEmitFunction)
    fun sendPlausibleRequest(plausiblePath: String, path: String, journey: String, plausibleProps: PlatformMap<String, String?>)
}
