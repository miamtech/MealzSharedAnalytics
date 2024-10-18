package ai.mealz.analytics.events

import ai.mealz.analytics.PlausibleDestinations
import ai.mealz.analytics.PlausibleProps
import ai.mealz.analytics.SharedAnalytics
import kotlin.js.JsExport
import kotlin.js.JsName

@JsExport
@JsName("sendOnboardingActionEvent")
fun sendOnboardingActionEvent(path: String, stepsCompleted: String) {
    SharedAnalytics.sendPlausibleRequest(
        PlausibleDestinations.ONBOARDING_ACTION.plausiblePath,
        path,
        PlausibleProps(steps_completed = stepsCompleted)
    )
}

@JsExport
@JsName("sendOnboardingCloseEvent")
fun sendOnboardingCloseEvent(path: String, stepsCompleted: String) {
    SharedAnalytics.sendPlausibleRequest(
        PlausibleDestinations.ONBOARDING_CLOSE.plausiblePath,
        path,
        PlausibleProps(steps_completed = stepsCompleted)
    )
}
