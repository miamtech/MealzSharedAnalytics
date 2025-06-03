# Always keep plausible events fields name to have a valid json to send in android minified project
-keep class ai.mealz.analytics.PlausibleEvent {
    <fields>;
}

# Don't minify Platform enum
-keep public class ai.mealz.analytics.Platform {
    *;
}