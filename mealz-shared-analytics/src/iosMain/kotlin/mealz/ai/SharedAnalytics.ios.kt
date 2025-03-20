package ai.mealz.analytics

import ai.mealz.analytics.handler.LogHandler
import ai.mealz.analytics.utils.PlatformMap
import io.ktor.client.HttpClient
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import kotlinx.cinterop.BetaInteropApi
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.launch
import platform.Foundation.NSData
import platform.Foundation.NSDictionary
import platform.Foundation.NSJSONSerialization
import platform.Foundation.NSString
import platform.Foundation.NSUTF8StringEncoding
import platform.Foundation.create

actual object SharedAnalytics : AbstractSharedAnalytics() {
    private val coroutineHandler = CoroutineExceptionHandler { _, exception ->
        LogHandler.error("Mealz error in Analytics $exception ${exception.stackTraceToString()}")
    }
    private val coroutineScope: CoroutineScope = CoroutineScope(Dispatchers.IO + coroutineHandler)

    private val httpClient = HttpClient {
        install(DefaultRequest)
    }

    private fun plausiblePropsToDict(props: Map<String, String?>): NSDictionary {
        return props as NSDictionary
    }

    @OptIn(ExperimentalForeignApi::class, BetaInteropApi::class)
    private fun plausibleEventToJson(event: PlausibleEvent): NSString? {
        val propsDict = plausiblePropsToDict(event.getProps())

        val eventDict = mapOf(
            "name" to event.name,
            "url" to event.url,
            "domain" to event.domain,
            "props" to propsDict
        ) as NSDictionary

        val jsonData: NSData? = NSJSONSerialization.dataWithJSONObject(eventDict, 0u, null)

        return jsonData?.let { NSString.create(data = it, encoding = NSUTF8StringEncoding) }
    }

    override fun sendRequest(event: PlausibleEvent) {
        LogHandler.info("Will send event $event to $PLAUSIBLE_URL and $MEALZ_ANALYTICS_URL")
        val block: HttpRequestBuilder.() -> Unit = {
            contentType(ContentType.Application.Json)
            setBody(plausibleEventToJson(event))
        }
        coroutineScope.launch {
            try {
                httpClient.post(PLAUSIBLE_URL, block)
                httpClient.post(MEALZ_ANALYTICS_URL, block)
            } catch (e: Exception) {
                LogHandler.error("Failed to send event: ${e.message}")
            }
        }
    }

    actual fun sendPlausibleRequest(plausiblePath: String, path: String, journey: String, plausibleProps: PlatformMap<String, String?>) {
        this.buildAndSendPlausibleRequest(plausiblePath, path, journey, plausibleProps)
    }

    actual fun initSharedAnalytics(domain: String, version: String, onEmit: onEmitFunction) {
        this.init(domain, version, onEmit)
        LogHandler.info("Analytics init for $domain")
    }
}
