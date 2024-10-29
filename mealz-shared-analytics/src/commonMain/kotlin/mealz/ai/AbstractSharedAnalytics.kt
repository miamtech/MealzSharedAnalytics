package ai.mealz.analytics

import ai.mealz.analytics.utils.PlatformList
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
        val validParts = PlatformList("", "recipes", "liked", "categories", "my-meals", "detail", "replace-item", "sponsor",
            "meals-planner", "catalog", "results", "basket-preview", "finalize", "onboarding", "locator")

        pathSplit.forEach { part ->
            if (!validParts.contains(part) && part.toIntOrNull() == null) {
                // If the part is not in the valid set of path segments nor a number, throw an exception
                throw IllegalArgumentException("Invalid path : \"$path\". \"$part\" is not a valid path part.")
            }
        }
    }

    internal fun buildAndSendPlausibleRequest(eventType: String, path: String, props: PlausibleProps) {
        if (!alreadyInitialized) return

        validatePath(path)
        val fullProps = props.copy(
            client_sdk_version = clientSDKVersion,
            analytics_sdk_version = analyticsSDKVersion,
            platform = getPlatform().name,
            abTestKey = abTestKey,
            affiliate = affiliate
        )
        val event = PlausibleEvent(
            eventType,
            path,
            domain,
            fullProps
        )
        onEmit(event)
        sendRequest(event)
    }

    companion object {
        internal const val PLAUSIBLE_URL: String = "https://plausible.io/api/event"
    }
}
