package ai.mealz.analytics

@Suppress("UNCHECKED_CAST")
fun PlausibleEvent.getProps(): Map<String, String?> {
    return try {
        props as MutableMap<String, String?>
    } catch (e: Exception) {
        mapOf()
    }
}
