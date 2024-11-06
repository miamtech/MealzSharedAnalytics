package ai.mealz.analytics.utils

import ai.mealz.analytics.androidAndIosShared.utils.PlatformMapShared

actual class PlatformMap<K, V> actual constructor(vararg elements: Pair<K, V>) : IPlatformMap<K, V>, PlatformMapShared<K, V>(*elements)
