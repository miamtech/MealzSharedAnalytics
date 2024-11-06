package ai.mealz.analytics.utils

import ai.mealz.analytics.androidAndIosShared.utils.PlatformListShared

actual class PlatformList<T> actual constructor(vararg elements: T) : PlatformListShared<T>(*elements), IPlatformList<T>