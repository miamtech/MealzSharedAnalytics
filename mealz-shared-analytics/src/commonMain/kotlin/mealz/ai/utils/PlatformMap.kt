package ai.mealz.analytics.utils

expect class PlatformMap<K, V>(vararg elements: Pair<K, V>): IPlatformMap<K, V> {
    override val size: Int
    override operator fun get(key: K): V?
    override operator fun set(key: K, value: V)
    override operator fun plus(element: PlatformMap<K, V>): PlatformMap<K, V>
    override fun put(key: K, value: V): V?
    override fun putAll(from: PlatformMap<K, V>)
    override fun isEmpty(): Boolean
    override fun forEach(predicate: (key: K, value: V) -> Unit)
}

interface IPlatformMap<K, V> {
    val size: Int
    operator fun get(key: K): V?
    operator fun set(key: K, value: V)
    operator fun plus(element: PlatformMap<K, V>): PlatformMap<K, V>
    fun put(key: K, value: V): V?
    fun putAll(from: PlatformMap<K, V>)
    fun isEmpty(): Boolean
    fun forEach(predicate: (key: K, value: V) -> Unit)
}
