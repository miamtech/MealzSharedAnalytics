package ai.mealz.analytics.utils

expect class PlatformList<T>(vararg elements: T): IPlatformList<T> {
    override val size: Int
    override operator fun get(index: Int): T
    override fun contains(element: T): Boolean
    override fun push(element: T)
    override fun isEmpty(): Boolean
    override fun forEach(predicate: (element: T) -> Unit)
    override fun all(predicate: (element: T) -> Boolean): Boolean
}

interface IPlatformList<T> {
    val size: Int
    operator fun get(index: Int): T
    fun contains(element: T): Boolean
    fun push(element: T)
    fun isEmpty(): Boolean
    fun forEach(predicate: (element: T) -> Unit)
    fun all(predicate: (element: T) -> Boolean): Boolean
}

fun String.splitToPlatformList(delimiter: Char): PlatformList<String> {
    val result = PlatformList<String>()
    var currentIndex = 0
    var startIndex = 0

    while (currentIndex < this.length) {
        if (this[currentIndex] == delimiter) {
            if (currentIndex > startIndex) {
                result.push(this.substring(startIndex, currentIndex))
            }
            startIndex = currentIndex + 1
        }
        currentIndex++
    }
    // Add last segment if necessary
    if (startIndex < this.length) {
        result.push(this.substring(startIndex))
    }
    return result
}
