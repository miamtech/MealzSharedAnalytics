package ai.mealz.analytics

import kotlin.js.JsExport
import kotlin.js.JsName

@JsExport
@JsName("PlausibleEvent")
class PlausibleEvent(
    val name: String,
    val url: String,
    val domain: String,
    val props: Any
)
