## 4.6.0
[FEA] Added `planner.onboarding.display` event with `type` and `step` parameters
[FEA] Added `planner.onboarding.start` event with `type` parameter
[FEA] Added `planner.onboarding.skip` event with `type` parameter
[FEA] Added `planner.onboarding.next` event with `type` and `step` parameters
[FEA] Added `planner.onboarding.understood` event with `type` parameter
[FEA] Added `planner.onboarding.close` event with `type` and `step` parameters
[FEA] Added `planner.help.question-mark` event with `from` parameter
[FEA] Added `planner.help.display` event with `step` parameter
[FEA] Added `planner.help.next` event with `step` parameter
[FEA] Added `planner.help.previous` event with `step` parameter
[FEA] Added `planner.help.understood` event
[FEA] Added `planner.help.close` event with `step` parameter

## 4.5.0
[FEA] Events now send to plausible and mealz-analytics
[FIX] Fix a problem where coroutines would stop if a request crashed in Android and iOS, meaning no other request could be sent.

## 4.4.0
[FEA] Added `supplier-selector.display` event
[FEA] Added `supplier-selector.back` event  
[FEA] Added `supplier-selector.close` event
[FEA] Added `supplier-selector.select` event
[FEA] Added "supplier-selector" to `VALID_PATH_PARTS`

## 4.3.1
[FEA] Added `planner.item.replaced` event

## 4.3.0
[FEA] Added `planner.reset` event
[FEA] Added `planner.recipe.add` event
[FEA] Added `planner.recipe.swap` event
[FEA] Added `planner.recipe.catalog-prompt` event
[FEA] Added `planner.suggestion.show` event
[FEA] Added `planner.item.delete` event
[FEA] Added `planner.item.add` event
[FEA] Added `planner.item.replace` event
[FEA] Added `dashboard`, `current`, `history` to `VALID_PATH_PARTS`
[FEA] Updated `PLANNER_STARTED` parameters
[FEA] Updated `PLANNER_FINALIZE` parameters
[FIX] Add ProGuard rule to keep object directly used for analytics

## 4.2.0
[FEA] Added `basket.show` event
[FEA] Added `search.results` event
[FEA] Added "filter" & "search" to `path`

## 4.1.1
[FIX] `entry.replace` props "item_id", "ext_item_id" and "item_ean" are now optional

## 4.1.0
[FEA] Added "status" to `entry.replace` optional props
[FEA] Added "guests" to `recipe.change-guests` optional props
[FEA] Added "list-scan" to valid path parts

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
