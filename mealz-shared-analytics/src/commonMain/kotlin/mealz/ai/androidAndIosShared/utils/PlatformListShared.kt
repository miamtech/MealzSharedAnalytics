package ai.mealz.analytics.androidAndIosShared.utils

import ai.mealz.analytics.utils.IPlatformList

open class PlatformListShared<T>(vararg elements: T) : IPlatformList<T>, AbstractMutableList<T>() {
    private val array = mutableListOf(*elements)

    override val size: Int
        get() = array.size

    override operator fun get(index: Int): T {
        return array[index]
    }

    override fun forEach(predicate: (element: T) -> Unit) {
        array.forEach(predicate)
    }

    override fun all(predicate: (element: T) -> Boolean): Boolean {
        return array.all(predicate)
    }

    override fun push(element: T) {
        this.add(element)
    }

    // Inherited from AbstractMutableList
    override fun add(index: Int, element: T) {
        array.add(index, element)
    }

    // Inherited from AbstractMutableList
    override fun removeAt(index: Int): T {
        return array.removeAt(index)
    }

    // Inherited from AbstractMutableList
    override fun set(index: Int, element: T): T {
        return array.set(index, element)
    }
}
