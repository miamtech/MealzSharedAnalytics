package ai.mealz.analytics.events

import ai.mealz.analytics.PlausibleDestinations
import ai.mealz.analytics.PlausibleProps
import ai.mealz.analytics.SharedAnalytics
import kotlin.js.JsExport
import kotlin.js.JsName

@JsExport
@JsName("sendPointOfSaleSelectedEvent")
fun sendPointOfSaleSelectedEvent(path: String, pointOfSaleName: String, pointOfSaleId: String) {
    SharedAnalytics.sendPlausibleRequest(
        PlausibleDestinations.POINT_OF_SALE_SELECTED.plausiblePath,
        path,
        PlausibleProps(pos_id = pointOfSaleId, pos_name = pointOfSaleName)
    )
}
