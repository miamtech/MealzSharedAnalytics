package ai.mealz.analytics

import ai.mealz.analytics.utils.PlatformList
import ai.mealz.analytics.utils.PlatformMap

class PlausibleProps(initialProps: PlatformMap<String, String?> = PlatformMap()) {
    private val props = PlatformMap(
        // Recipe
        "recipe_id" to initialProps["recipe_id"],
        // Catalog
        "category_id" to initialProps["category_id"],
        // Entry / Item / Product
        "entry_name" to initialProps["entry_name"],
        "item_id" to initialProps["item_id"],
        "ext_item_id" to initialProps["ext_item_id"],
        "item_ean" to initialProps["item_ean"],
        "old_item_id" to initialProps["old_item_id"],
        "old_item_ext_id" to initialProps["old_item_ext_id"],
        "old_item_ean" to initialProps["old_item_ean"],
        "new_item_id" to initialProps["new_item_id"],
        "new_item_ext_id" to initialProps["new_item_ext_id"],
        "new_item_ean" to initialProps["new_item_ean"],
        "product_quantity" to initialProps["product_quantity"],
        // Basket / Payment
        "basket_id" to initialProps["basket_id"],
        "miam_amount" to initialProps["miam_amount"],
        "total_amount" to initialProps["total_amount"],
        "miam_products" to initialProps["miam_products"],
        "total_products" to initialProps["total_products"],
        "client_order_id" to initialProps["client_order_id"],
        "supplier_id" to initialProps["supplier_id"],
        // Locator
        "supplier_name" to initialProps["supplier_name"],
        // Point Of Sale
        "pos_id" to initialProps["pos_id"],
        "pos_name" to initialProps["pos_name"],
        // Search
        "search_term" to initialProps["search_term"],
        "stores_found_count" to initialProps["stores_found_count"],
        // Meal Planner
        "budget" to initialProps["budget"],
        "budget_user" to initialProps["budget_user"],
        "budget_planner" to initialProps["budget_planner"],
        "recipe_count" to initialProps["recipe_count"],
        "guests" to initialProps["guests"],
        "uses_count" to initialProps["uses_count"],
        "time_passed" to initialProps["time_passed"],
        // Onboarding
        "steps_completed" to initialProps["steps_completed"],
        // Global
        "client_sdk_version" to initialProps["client_sdk_version"],
        "analytics_sdk_version" to initialProps["analytics_sdk_version"],
        "platform" to initialProps["platform"],
        "affiliate" to initialProps["affiliate"],
        "abTestKey" to initialProps["abTestKey"]
    )

    val value get() = props

    operator fun get(key: String): String? = props[key]

    operator fun set(key: String, value: String?) {
        this.props[key] = value
    }

    internal fun areValidForEvent(mandatoryPropsForEvent: PlatformList<String>): Boolean {
        return mandatoryPropsForEvent.all { key -> props[key] != null }
    }
}
