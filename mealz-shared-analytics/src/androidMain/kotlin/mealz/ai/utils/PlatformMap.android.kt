package ai.mealz.analytics.utils

import ai.mealz.analytics.androidAndIosShared.utils.PlatformMapShared

actual class PlatformMap<K, V> actual constructor(vararg elements: Pair<K, V>) : IPlatformMap<K, V>, PlatformMapShared<K, V>(*elements) {
    companion object {
        fun <K, V> fromMap(map: Map<K, V>) = PlatformMap<K, V>().apply {
            map.forEach { (key, value) -> this[key] = value }
        }
    }
}
