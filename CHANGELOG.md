## 4.1.0
[FEA] Added "status" to `entry.replace` optional props
[FEA] Added "guests" to `recipe.change-guests` optional props

## 4.0.0
[BRK][FIX] Fix `entry.replace` event had the wrong props
[BRK][FIX] Fix `item-selector.back` and `item-selector.close` event had the wrong props

## 3.0.0
[BRK][FEA] Replaced PlausibleProps by `MutableMap<String, String?>` in mobile and `{ [key: string]: string }` in js
[BRK][FEA] Replaced all event sending functions by generic EventSender.sendEvent
[BRK][FEA] Renamed pos.* events to locator.*
[BRK][FEA] Updated valid path parts
[BRK][FEA] Added a journey property to all events
[FEA] Add new events
[FEA] Add PlatformList wrapper so that we can use native lists in JS
[FEA] Add PlatformMap wrapper so that we can use native objects in JS
[FIX] CI/CD can now recognize alpha versions

## 2.0.0
[BRK][FIX] Rename search.store in pos.search
[FEA] Add recipe.close, recipe.continue, locator.close, onboarding.action, onboarding.close events
[FEA] Add supplier_name, steps_completed props

## 1.0.0
[FEA] Create project
