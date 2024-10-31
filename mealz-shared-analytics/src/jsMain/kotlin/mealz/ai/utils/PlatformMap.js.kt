package ai.mealz.analytics.utils

actual class PlatformMap<K, V> actual constructor(vararg elements: Pair<K, V>) : IPlatformMap<K, V> {
    private val map = js("{}")

    init {
        for (element in elements) {
            this.put(element.first, element.second)
        }
    }

    actual override val size: Int
        get() = Object.keys(map).length as Int

    actual override fun isEmpty(): Boolean {
        return Object.keys(map).length === 0
    }

    actual override fun put(key: K, value: V): V? {
        map[key] = value
        return map[key] as? V
    }

    actual override fun putAll(from: PlatformMap<K, V>) {
        from.forEach { k, v ->
            this.put(k, v)
        }
    }

    actual override operator fun get(key: K): V? {
        return map[key] as? V
    }

    actual override operator fun plus(element: PlatformMap<K, V>): PlatformMap<K, V> {
        val newMap = PlatformMap<K, V>()

        this.forEach { key, value ->
            newMap[key] = value
        }

        element.forEach { key, value ->
            newMap[key] = value
        }

        return newMap
    }

    actual override operator fun set(key: K, value: V) {
        map[key] = value
    }

    override fun toString(): String {
        return JSON.stringify(map)
    }

    actual override fun forEach(predicate: (key: K, value: V) -> Unit) {
        Object.entries(map).forEach { entry: dynamic -> predicate(entry[0] as K, entry[1] as V)}
    }
}

private external class Object {
    companion object {
        fun keys(obj: dynamic): dynamic
        fun entries(obj: dynamic): dynamic
    }
}
