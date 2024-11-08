package ai.mealz.analytics.utils

import ai.mealz.analytics.EventService
import ai.mealz.analytics.PlausibleProps
import kotlin.js.collections.JsMap

@OptIn(ExperimentalJsCollectionsApi::class)
private typealias JsObject = JsMap<String, String?>

@OptIn(ExperimentalJsCollectionsApi::class)
@JsExport
@JsName("sendEvent")
fun sendEvent(name: String, path: String, props: JsObject) {
    EventService.sendEvent(name, path, PlausibleProps(PlatformMap.fromNative(props)))
}
