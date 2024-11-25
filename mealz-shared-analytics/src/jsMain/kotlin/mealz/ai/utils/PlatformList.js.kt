package ai.mealz.analytics.utils

actual class PlatformList<T> actual constructor(vararg elements: T) : IPlatformList<T> {
    private val array = js("[]")

    actual override val size: Int get() = array.length

    init {
        for (element in elements) {
            array.push(element)
        }
    }

    actual override operator fun get(index: Int): T = array[index] as T

    actual override fun isEmpty(): Boolean {
        return array.length == 0
    }

    actual override fun forEach(predicate: (T) -> Unit) {
        array.forEach(predicate)
    }

    actual override fun all(predicate: (T) -> Boolean): Boolean {
        return array.every(predicate) as Boolean
    }

    actual override fun push(element: T) {
        array.push(element)
    }

    actual override fun contains(element: T): Boolean {
        return array.includes(element) as Boolean
    }

    override fun toString(): String {
        var string = ""
        for (element in array) {
            string += "$element,"
        }
        return "[${string.substringBeforeLast(',')}]"
    }
}
