package ai.mealz.analytics

import ai.mealz.analytics.utils.PlatformList
import ai.mealz.analytics.utils.PlatformMap

object EventService : EventSender {
    override fun sendEvent(name: String, path: String, props: PlatformMap<String, String?>) {
        PROPS_FOR_EVENT[name]?.let { propsForEvent ->
            propsForEvent["mandatory"]?.let { mandatoryProps ->
                if (!props.areValidForEvent(mandatoryProps)) {
                    error("Invalid props for $name event : mandatory props are $mandatoryProps and optional props are ${propsForEvent["optional"]}")
                }
                SharedAnalytics.sendPlausibleRequest(
                    name,
                    path,
                    props
                )
            } ?: error("No mandatory props found for $name")
        } ?: error("Unknown event $name")
    }

    private val PROPS_FOR_EVENT = PlatformMap(
        // -------------------------------- PAGE VIEW ---------------------------------------------
        PlausibleDestinations.PAGEVIEW.plausiblePath to propsOf(PlatformList(), PlatformList()),

        // ---------------------------------- SEARCH ----------------------------------------------
        PlausibleDestinations.SEARCH.plausiblePath to propsOf(PlatformList("search_term"), PlatformList()),

        // ---------------------------------- RECIPE ----------------------------------------------
        PlausibleDestinations.RECIPE_SHOW.plausiblePath to propsOf(PlatformList("recipe_id"), PlatformList("category_id")),
        PlausibleDestinations.RECIPE_DISPLAY.plausiblePath to propsOf(PlatformList("recipe_id"), PlatformList("category_id")),
        PlausibleDestinations.RECIPE_ADD.plausiblePath to propsOf(PlatformList("recipe_id"), PlatformList()),
        PlausibleDestinations.RECIPE_REMOVE.plausiblePath to propsOf(PlatformList("recipe_id"), PlatformList()),
        PlausibleDestinations.RECIPE_CHANGE_GUESTS.plausiblePath to propsOf(PlatformList("recipe_id"), PlatformList()),
        PlausibleDestinations.RECIPE_LIKE.plausiblePath to propsOf(PlatformList("recipe_id"), PlatformList("category_id")),
        PlausibleDestinations.RECIPE_UNLIKE.plausiblePath to propsOf(PlatformList("recipe_id"), PlatformList("category_id")),
        PlausibleDestinations.RECIPE_SPONSOR.plausiblePath to propsOf(PlatformList("recipe_id"), PlatformList()),
        PlausibleDestinations.RECIPE_CONTINUE.plausiblePath to propsOf(PlatformList("recipe_id"), PlatformList()),
        PlausibleDestinations.RECIPE_CLOSE.plausiblePath to propsOf(PlatformList("recipe_id"), PlatformList()),

        // ---------------------------------- CATALOG ----------------------------------------------
        PlausibleDestinations.CATEGORY_SHOW.plausiblePath to propsOf(PlatformList("category_id"), PlatformList()),
        PlausibleDestinations.CATEGORY_DISPLAY.plausiblePath to propsOf(PlatformList("category_id"), PlatformList()),

        // ---------------------------------- PRODUCT ----------------------------------------------
        PlausibleDestinations.BASKET_ENTRY_ADD.plausiblePath to propsOf(PlatformList("entry_name", "recipe_id", "item_id", "ext_item_id", "item_ean", "product_quantity"), PlatformList("recipe_id")),
        PlausibleDestinations.BASKET_ENTRY_DELETE.plausiblePath to propsOf(PlatformList("entry_name", "recipe_id", "item_id", "ext_item_id", "item_ean", "product_quantity"), PlatformList("recipe_id")),
        PlausibleDestinations.BASKET_ENTRY_REPLACE.plausiblePath to propsOf(PlatformList("entry_name", "recipe_id", "new_item_id", "new_item_ext_id", "new_item_ean"), PlatformList("old_item_id", "old_item_ext_id", "old_item_ean", "product_quantity", "search_term", "recipe_id")),
        PlausibleDestinations.BASKET_ENTRY_CHANGE_QUANTITY.plausiblePath to propsOf(PlatformList("entry_name", "recipe_id", "item_id", "ext_item_id", "item_ean", "product_quantity"), PlatformList("recipe_id")),

        // ---------------------------------- PAYMENT ----------------------------------------------
        PlausibleDestinations.PAYMENT_STARTED.plausiblePath to propsOf(PlatformList("basket_id", "miam_amount", "total_amount", "pos_id", "pos_name", "recipe_count", "miam_products"), PlatformList("total_products", "client_order_id")),
        PlausibleDestinations.PAYMENT_CONFIRMED.plausiblePath to propsOf(PlatformList("basket_id", "miam_amount", "total_amount", "pos_id", "pos_name", "recipe_count", "miam_products"), PlatformList("total_products", "client_order_id")),

        // ---------------------------------- BASKET ----------------------------------------------
        PlausibleDestinations.BASKET_CONFIRMED.plausiblePath to propsOf(PlatformList("basket_id", "miam_amount", "total_amount", "pos_id", "pos_name", "recipe_count", "miam_products"), PlatformList("total_products", "client_order_id")),
        PlausibleDestinations.BASKET_TRANSFER.plausiblePath to propsOf(PlatformList("basket_id", "miam_amount", "pos_id", "pos_name", "supplier_id"), PlatformList()),

        // ------------------------------- MEAL PLANNER --------------------------------------------
        PlausibleDestinations.PLANNER_STARTED.plausiblePath to propsOf(PlatformList("budget", "guests", "recipe_count"), PlatformList()),
        PlausibleDestinations.PLANNER_RECIPE_DELETED.plausiblePath to propsOf(PlatformList("recipe_id"), PlatformList()),
        PlausibleDestinations.PLANNER_CONFIRM.plausiblePath to propsOf(PlatformList("budget_user", "budget_planner", "recipe_count", "guests", "uses_count", "time_passed"), PlatformList()),
        PlausibleDestinations.PLANNER_FINALIZE.plausiblePath to propsOf(PlatformList("budget_user", "budget_planner", "recipe_count", "guests"), PlatformList()),

        // ------------------------------- STORE LOCATOR -------------------------------------------
        PlausibleDestinations.POINT_OF_SALE_SEARCH.plausiblePath to propsOf(PlatformList("search_term", "stores_found_count"), PlatformList()),
        PlausibleDestinations.POINT_OF_SALE_SELECTED.plausiblePath to propsOf(PlatformList("pos_id", "pos_name", "supplier_name"), PlatformList()),
        PlausibleDestinations.LOCATOR_CLOSE.plausiblePath to propsOf(PlatformList("time_passed"), PlatformList()),

        // -------------------------------- ONBOARDING ---------------------------------------------
        PlausibleDestinations.ONBOARDING_ACTION.plausiblePath to propsOf(PlatformList("steps_completed"), PlatformList()),
        PlausibleDestinations.ONBOARDING_CLOSE.plausiblePath to propsOf(PlatformList("steps_completed"), PlatformList())
    )
}

interface EventSender {
    fun sendEvent(name: String, path: String, props: PlatformMap<String, String?>)
}

private fun propsOf(
    mandatory: PlatformList<String>,
    optional: PlatformList<String>
): PlatformMap<String, PlatformList<String>> {
    return PlatformMap(
        "mandatory" to mandatory,
        "optional" to optional
    )
}

private fun PlatformMap<String, String?>.areValidForEvent(mandatoryPropsForEvent: PlatformList<String>): Boolean {
    return mandatoryPropsForEvent.all { key -> this[key] != null }
}
