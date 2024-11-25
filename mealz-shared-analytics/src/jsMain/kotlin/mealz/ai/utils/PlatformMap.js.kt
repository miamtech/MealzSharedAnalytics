package ai.mealz.analytics.utils

actual class PlatformMap<K, V> actual constructor(vararg elements: Pair<K, V>) : IPlatformMap<K, V> {
    private val map = js("{}")

    actual override val value: Any get() = map as Any

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

    actual override operator fun set(key: K, value: V) {
        map[key] = value
    }

    override fun toString(): String {
        return JSON.stringify(map)
    }

    actual override fun forEach(predicate: (K, V) -> Unit) {
        Object.entries(map).forEach { entry: dynamic -> predicate(entry[0] as K, entry[1] as V)}
    }

    companion object {
        fun <K, V> fromNative(obj: dynamic): PlatformMap<K, V> {
            val map = PlatformMap<K, V>()
            for (key in js("Object.keys(obj)")) {
                map[key as K] = obj[key] as V
            }
            return map
        }
    }
}

private external class Object {
    companion object {
        fun keys(obj: dynamic): dynamic
        fun entries(obj: dynamic): dynamic
    }
}
