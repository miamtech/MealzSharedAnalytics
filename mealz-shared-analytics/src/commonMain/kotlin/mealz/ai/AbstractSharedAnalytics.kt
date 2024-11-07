package ai.mealz.analytics

import ai.mealz.analytics.handler.LogHandler
import ai.mealz.analytics.utils.PlatformList
import ai.mealz.analytics.utils.PlatformMap
import ai.mealz.analytics.utils.splitToPlatformList

typealias onEmitFunction = (PlausibleEvent) -> Unit

abstract class AbstractSharedAnalytics {
    private lateinit var onEmit: onEmitFunction
    private lateinit var domain: String
    private lateinit var analyticsSDKVersion: String
    private lateinit var clientSDKVersion: String

    internal var abTestKey: String? = null
    internal var affiliate: String? = null

    private val alreadyInitialized: Boolean
        get() = this::domain.isInitialized && this::clientSDKVersion.isInitialized && domain.isNotBlank() && clientSDKVersion.isNotBlank()

    fun init(domain: String, version: String, onEmit: onEmitFunction) {
        if (alreadyInitialized) return

        this.domain = domain
        this.analyticsSDKVersion = "##VERSION##"
        this.clientSDKVersion = version
        this.onEmit = onEmit
    }

    abstract fun sendRequest(event: PlausibleEvent)

    private fun validatePath(path: String) {
        // If the path does not contain "/miam/", it may just be the URL of the page (web) or
        // an empty path (mobile) so we assume it is valid
        if (!path.contains("/miam/")) return

        // Only keep Mealz's part
        val pathSplit = path.substringAfter("/miam/").splitToPlatformList('/')

        pathSplit.forEach { part ->
            if (!VALID_PATH_PARTS.contains(part) && part.toIntOrNull() == null) {
                // If the part is not in the valid set of path segments nor a number, throw an exception
                throw IllegalArgumentException("Invalid path : \"$path\". \"$part\" is not a valid path part.")
            }
        }
    }

    internal fun buildAndSendPlausibleRequest(eventType: String, path: String, props: PlatformMap<String, String?>) {
        if (!alreadyInitialized) return LogHandler.warn("Tried to send event before analytics initialization")

        validatePath(path)

        props["client_sdk_version"] = clientSDKVersion
        props["analytics_sdk_version"] = analyticsSDKVersion
        props["platform"] = getPlatform().name
        props["abTestKey"] = abTestKey
        props["affiliate"] = affiliate
        val event = PlausibleEvent(
            eventType,
            path,
            domain,
            props.value
        )
        onEmit(event)
        sendRequest(event)
    }

    companion object {
        internal const val PLAUSIBLE_URL: String = "https://plausible.io/api/event"
        private val VALID_PATH_PARTS = PlatformList(
            "",
            "basket",
            "basket-preview",
            "catalog",
            "categories",
            "cooking",
            "finalize",
            "item-selector",
            "liked",
            "locator",
            "meals-planner",
            "onboarding",
            "ordered",
            "orders",
            "preferences",
            "products",
            "recipes",
            "results",
            "shopping",
            "sponsor"
        )
    }
}
