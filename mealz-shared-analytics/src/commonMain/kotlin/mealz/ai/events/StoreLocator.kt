package ai.mealz.analytics.events

import ai.mealz.analytics.PlausibleDestinations
import ai.mealz.analytics.PlausibleProps
import ai.mealz.analytics.SharedAnalytics
import kotlin.js.JsExport
import kotlin.js.JsName

@JsExport
@JsName("sendPointOfSaleSelectedEvent")
fun sendPointOfSaleSelectedEvent(path: String, pointOfSaleName: String, pointOfSaleId: String, supplierName: String) {
    SharedAnalytics.sendPlausibleRequest(
        PlausibleDestinations.POINT_OF_SALE_SELECTED.plausiblePath,
        path,
        PlausibleProps(pos_id = pointOfSaleId, pos_name = pointOfSaleName, supplier_name = supplierName)
    )
}

@JsExport
@JsName("sendPointOfSaleSearchEvent")
fun sendPointOfSaleSearchEvent(path: String, searchTerm: String, storesFoundCount: String) {
    SharedAnalytics.sendPlausibleRequest(
        PlausibleDestinations.POINT_OF_SALE_SEARCH.plausiblePath,
        path,
        PlausibleProps(search_term = searchTerm, stores_found_count = storesFoundCount)
    )
}

@JsExport
@JsName("sendLocatorCloseEvent")
fun sendLocatorCloseEvent(path: String, timePassed: String) {
    SharedAnalytics.sendPlausibleRequest(
        PlausibleDestinations.LOCATOR_CLOSE.plausiblePath,
        path,
        PlausibleProps(time_passed = timePassed)
    )
}
