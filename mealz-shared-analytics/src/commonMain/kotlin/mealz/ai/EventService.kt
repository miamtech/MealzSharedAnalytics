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
        PlausibleDestinations.RECIPE_COOKING_DISPLAY.plausiblePath to propsOf(PlatformList("recipe_id"), PlatformList()),
        PlausibleDestinations.RECIPE_SHOPPING_DISPLAY.plausiblePath to propsOf(PlatformList("recipe_id"), PlatformList()),
        PlausibleDestinations.RECIPE_ADD.plausiblePath to propsOf(PlatformList("recipe_id"), PlatformList()),
        PlausibleDestinations.RECIPE_REMOVE.plausiblePath to propsOf(PlatformList("recipe_id"), PlatformList()),
        PlausibleDestinations.RECIPE_CHANGE_GUESTS.plausiblePath to propsOf(PlatformList("recipe_id"), PlatformList()),
        PlausibleDestinations.RECIPE_LIKE.plausiblePath to propsOf(PlatformList("recipe_id"), PlatformList("category_id")),
        PlausibleDestinations.RECIPE_UNLIKE.plausiblePath to propsOf(PlatformList("recipe_id"), PlatformList("category_id")),
        PlausibleDestinations.RECIPE_CONTINUE.plausiblePath to propsOf(PlatformList("recipe_id"), PlatformList()),
        PlausibleDestinations.RECIPE_CLOSE.plausiblePath to propsOf(PlatformList("recipe_id"), PlatformList()),

        // ------------------------------- RECIPE ORDERED ------------------------------------------
        PlausibleDestinations.RECIPE_ORDERED_DISPLAY.plausiblePath to propsOf(PlatformList("recipe_id"), PlatformList()),
        PlausibleDestinations.RECIPE_ORDERED_CLOSE.plausiblePath to propsOf(PlatformList("recipe_id"), PlatformList()),

        // ---------------------------------- SPONSOR ----------------------------------------------
        PlausibleDestinations.SPONSOR_DISPLAY.plausiblePath to propsOf(PlatformList("recipe_id"), PlatformList()),
        PlausibleDestinations.SPONSOR_BACK.plausiblePath to propsOf(PlatformList("recipe_id"), PlatformList()),
        PlausibleDestinations.SPONSOR_CLOSE.plausiblePath to propsOf(PlatformList("recipe_id"), PlatformList()),

        // ------------------------------------ HOME -----------------------------------------------
        PlausibleDestinations.HOME_DISPLAY.plausiblePath to propsOf(PlatformList(), PlatformList()),

        // --------------------------------- BREADCRUMB --------------------------------------------
        PlausibleDestinations.BREADCRUMB_SELECT.plausiblePath to propsOf(PlatformList("step"), PlatformList()),

        // ---------------------------------- CATEGORY ----------------------------------------------
        PlausibleDestinations.CATEGORY_SHOW.plausiblePath to propsOf(PlatformList("category_id"), PlatformList()),
        PlausibleDestinations.CATEGORY_DISPLAY.plausiblePath to propsOf(PlatformList("category_id"), PlatformList()),
        PlausibleDestinations.CATEGORY_BACK.plausiblePath to propsOf(PlatformList("category_id"), PlatformList()),

        // ----------------------------------- LIKED -----------------------------------------------
        PlausibleDestinations.LIKED_DISPLAY.plausiblePath to propsOf(PlatformList(), PlatformList()),
        PlausibleDestinations.LIKED_BACK.plausiblePath to propsOf(PlatformList(), PlatformList()),
        PlausibleDestinations.LIKED_SEARCH.plausiblePath to propsOf(PlatformList("search_term"), PlatformList()),

        // ---------------------------------- ORDERS -----------------------------------------------
        PlausibleDestinations.ORDERS_DISPLAY.plausiblePath to propsOf(PlatformList(), PlatformList()),
        PlausibleDestinations.ORDERS_BACK.plausiblePath to propsOf(PlatformList(), PlatformList()),
        PlausibleDestinations.ORDERS_SEARCH.plausiblePath to propsOf(PlatformList("search_term"), PlatformList()),

        // ----------------------------------- ORDER -----------------------------------------------
        PlausibleDestinations.ORDER_DISPLAY.plausiblePath to propsOf(PlatformList("basket_id"), PlatformList()),
        PlausibleDestinations.ORDER_BACK.plausiblePath to propsOf(PlatformList("basket_id"), PlatformList()),
        PlausibleDestinations.ORDER_CLOSE.plausiblePath to propsOf(PlatformList("basket_id"), PlatformList()),

        // -------------------------------- PREFERENCES --------------------------------------------
        PlausibleDestinations.PREFERENCES_DISPLAY.plausiblePath to propsOf(PlatformList(), PlatformList()),
        PlausibleDestinations.PREFERENCES_CLOSE.plausiblePath to propsOf(PlatformList(), PlatformList()),
        PlausibleDestinations.PREFERENCES_RESET.plausiblePath to propsOf(PlatformList(), PlatformList()),
        PlausibleDestinations.PREFERENCES_SAVE.plausiblePath to propsOf(PlatformList(), PlatformList()),
        PlausibleDestinations.PREFERENCES_GUESTS.plausiblePath to propsOf(PlatformList("guests"), PlatformList()),
        PlausibleDestinations.PREFERENCES_TAG_SELECTED.plausiblePath to propsOf(PlatformList("tag_type", "tag_id"), PlatformList()),
        PlausibleDestinations.PREFERENCES_SEARCH.plausiblePath to propsOf(PlatformList("search_term"), PlatformList()),

        // ---------------------------------- PRODUCT ----------------------------------------------
        PlausibleDestinations.ENTRY_ADD.plausiblePath to propsOf(PlatformList("entry_name", "item_id", "ext_item_id", "item_ean", "product_quantity"), PlatformList("recipe_id")),
        PlausibleDestinations.ENTRY_ADDED.plausiblePath to propsOf(PlatformList("entry_name", "item_id", "ext_item_id", "item_ean", "product_quantity"), PlatformList("recipe_id")),
        PlausibleDestinations.ENTRY_ADD_ALL.plausiblePath to propsOf(PlatformList("recipe_id", "entry_count"), PlatformList()),
        PlausibleDestinations.ENTRY_ADD_ALL_AGAIN.plausiblePath to propsOf(PlatformList("recipe_id", "entry_count"), PlatformList()),
        PlausibleDestinations.ENTRY_DELETE.plausiblePath to propsOf(PlatformList("entry_name", "item_id", "ext_item_id", "item_ean", "product_quantity"), PlatformList("recipe_id")),
        PlausibleDestinations.ENTRY_DELETED.plausiblePath to propsOf(PlatformList("entry_name", "item_id", "ext_item_id", "item_ean", "product_quantity"), PlatformList("recipe_id")),
        PlausibleDestinations.ENTRY_REPLACE.plausiblePath to propsOf(PlatformList("entry_name", "new_item_id", "new_item_ext_id", "new_item_ean"), PlatformList("old_item_id", "old_item_ext_id", "old_item_ean", "product_quantity", "search_term", "recipe_id")),
        PlausibleDestinations.ENTRY_REPLACED.plausiblePath to propsOf(PlatformList("entry_name", "new_item_id", "new_item_ext_id", "new_item_ean"), PlatformList("old_item_id", "old_item_ext_id", "old_item_ean", "product_quantity", "search_term", "recipe_id")),
        PlausibleDestinations.ENTRY_CHANGE_QUANTITY.plausiblePath to propsOf(PlatformList("entry_name", "item_id", "ext_item_id", "item_ean", "product_quantity"), PlatformList("recipe_id")),
        PlausibleDestinations.ENTRY_IGNORE.plausiblePath to propsOf(PlatformList("entry_name", "item_id", "ext_item_id", "item_ean", "product_quantity"), PlatformList("recipe_id")),

        // ------------------------------- ITEM SELECTOR -------------------------------------------
        PlausibleDestinations.ITEM_SELECTOR_BACK.plausiblePath to propsOf(PlatformList("recipe_id", "entry_count"), PlatformList()),
        PlausibleDestinations.ITEM_SELECTOR_CLOSE.plausiblePath to propsOf(PlatformList("recipe_id", "entry_count"), PlatformList()),
        PlausibleDestinations.ITEM_SELECTOR_SEARCH.plausiblePath to propsOf(PlatformList("search_term"), PlatformList("old_item_id", "old_item_ext_id", "old_item_ean", "recipe_id")),

        // ---------------------------------- PAYMENT ----------------------------------------------
        PlausibleDestinations.PAYMENT_STARTED.plausiblePath to propsOf(PlatformList("basket_id", "miam_amount", "total_amount", "pos_id", "pos_name", "recipe_count", "miam_products"), PlatformList("total_products", "client_order_id")),
        PlausibleDestinations.PAYMENT_CONFIRMED.plausiblePath to propsOf(PlatformList("basket_id", "miam_amount", "total_amount", "pos_id", "pos_name", "recipe_count", "miam_products"), PlatformList("total_products", "client_order_id")),

        // ---------------------------------- BASKET ----------------------------------------------
        PlausibleDestinations.BASKET_DISPLAY.plausiblePath to propsOf(PlatformList(), PlatformList()),
        PlausibleDestinations.BASKET_RECIPES_DISPLAY.plausiblePath to propsOf(PlatformList(), PlatformList()),
        PlausibleDestinations.BASKET_PRODUCTS_DISPLAY.plausiblePath to propsOf(PlatformList(), PlatformList()),
        PlausibleDestinations.BASKET_CLOSE.plausiblePath to propsOf(PlatformList(), PlatformList()),
        PlausibleDestinations.BASKET_ENTRY_ADD.plausiblePath to propsOf(PlatformList(), PlatformList()),
        PlausibleDestinations.BASKET_CONFIRMED.plausiblePath to propsOf(PlatformList("basket_id", "miam_amount", "total_amount", "pos_id", "pos_name", "recipe_count", "miam_products"), PlatformList("total_products", "client_order_id")),
        PlausibleDestinations.BASKET_TRANSFER.plausiblePath to propsOf(PlatformList("basket_id", "miam_amount", "pos_id", "pos_name", "supplier_id"), PlatformList()),

        // ------------------------------- MEAL PLANNER --------------------------------------------
        PlausibleDestinations.PLANNER_STARTED.plausiblePath to propsOf(PlatformList("budget", "guests", "recipe_count"), PlatformList()),
        PlausibleDestinations.PLANNER_RECIPE_DELETED.plausiblePath to propsOf(PlatformList("recipe_id"), PlatformList()),
        PlausibleDestinations.PLANNER_CONFIRM.plausiblePath to propsOf(PlatformList("budget_user", "budget_planner", "recipe_count", "guests", "uses_count", "time_passed"), PlatformList()),
        PlausibleDestinations.PLANNER_FINALIZE.plausiblePath to propsOf(PlatformList("budget_user", "budget_planner", "recipe_count", "guests"), PlatformList()),

        // ------------------------------- STORE LOCATOR -------------------------------------------
        PlausibleDestinations.LOCATOR_DISPLAY.plausiblePath to propsOf(PlatformList(), PlatformList()),
        PlausibleDestinations.LOCATOR_MAP_DISPLAY.plausiblePath to propsOf(PlatformList(), PlatformList()),
        PlausibleDestinations.LOCATOR_LIST_DISPLAY.plausiblePath to propsOf(PlatformList(), PlatformList()),
        PlausibleDestinations.LOCATOR_BACK.plausiblePath to propsOf(PlatformList(), PlatformList()),
        PlausibleDestinations.LOCATOR_CLOSE.plausiblePath to propsOf(PlatformList(), PlatformList()),
        PlausibleDestinations.LOCATOR_SEARCH.plausiblePath to propsOf(PlatformList("search_term", "stores_found_count"), PlatformList()),
        PlausibleDestinations.LOCATOR_FILTER.plausiblePath to propsOf(PlatformList("supplier_name"), PlatformList()),
        PlausibleDestinations.LOCATOR_SELECT.plausiblePath to propsOf(PlatformList("pos_id", "pos_name", "supplier_name"), PlatformList()),

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
