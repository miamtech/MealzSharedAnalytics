package ai.mealz.analytics

import ai.mealz.analytics.handler.LogHandler
import ai.mealz.analytics.utils.PlatformMap
import com.google.gson.Gson
import io.ktor.client.HttpClient
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

actual object SharedAnalytics : AbstractSharedAnalytics() {
    private val gson = Gson()

    private val coroutineHandler = CoroutineExceptionHandler { _, exception ->
        LogHandler.error("Mealz error in Analytics $exception ${exception.stackTraceToString()}")
    }
    private val coroutineScope: CoroutineScope = CoroutineScope(Dispatchers.IO + coroutineHandler)

    private val httpClient = HttpClient {
        install(DefaultRequest)
    }

    override fun sendRequest(event: PlausibleEvent) {
        LogHandler.info("Will send event $event to $PLAUSIBLE_URL and $MEALZ_ANALYTICS_URL")
        val block: HttpRequestBuilder.() -> Unit = {
            contentType(ContentType.Application.Json)
            setBody(gson.toJson(event))
        }
        coroutineScope.launch {
            try {
                httpClient.post(PLAUSIBLE_URL, block)
            } catch (e: Exception) {
                LogHandler.error("Failed to send Plausible request: ${e.message}")
            }
            try {
                httpClient.post(MEALZ_ANALYTICS_URL, block)
            } catch (e: Exception) {
                LogHandler.error("Failed to send mealz-analytics request: ${e.message}")
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
