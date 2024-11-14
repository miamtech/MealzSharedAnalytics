package ai.mealz.analytics.androidAndIosShared.utils

import ai.mealz.analytics.utils.IPlatformMap
import ai.mealz.analytics.utils.PlatformMap

open class PlatformMapShared<K, V>(vararg elements: Pair<K, V>) : IPlatformMap<K, V>, AbstractMutableMap<K, V>() {
    private val map = mutableMapOf(*elements)

    override val value: Any get() = map

    override val entries: MutableSet<MutableMap.MutableEntry<K, V>>
        get() = map.entries

    override val size: Int
        get() = entries.size

    override operator fun set(key: K, value: V) {
        map[key] = value
    }

    override operator fun get(key: K): V? {
        return map[key]
    }

    override fun put(key: K, value: V): V? {
        return map.put(key, value)
    }

    override fun putAll(from: PlatformMap<K, V>) {
        from.forEach { k, v ->
            this.put(k, v)
        }
    }

    override fun forEach(predicate: (key: K, value: V) -> Unit) {
        map.forEach {
            predicate(it.key, it.value)
        }
    }

    fun toMutableMap(): MutableMap<K, V> {
        return map
    }
}
