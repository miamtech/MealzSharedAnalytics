# mealz-shared-analytics

Share analytics between Mealz projects

- [mealz-shared-analytics](#mealz-shared-analytics)
  - [Introduction](#introduction)
  - [expect/actual](#expectactual)
    - [File Structure](#file-structure)
  - [File structure](#file-structure-1)
    - [commonMain](#commonmain)
    - [jsMain](#jsmain)
    - [androidMain and IosMain](#androidmain-and-iosmain)
  - [Build](#build)
    - [Using Makefile](#using-makefile)
    - [Using gradle (legacy)](#using-gradle-legacy)
      - [Android aar](#android-aar)
      - [iOS Framework](#ios-framework)
      - [JS file](#js-file)
  - [Test](#test)
    - [JS](#js)
    - [Android and iOS](#android-and-ios)
  - [Release](#release)
  - [FAQ](#faq)

## Introduction

This library allows us to share the analytics logic between all platforms (Android, iOS and Web). It is also a source of truth for the events and their properties.

The only things necessary outside of this library are the `sendEvent` and the `init` functions.

All functions exported have the `@JsExport` and `@JsName(...)` tags above them to expose them in the JS build.

## expect/actual

In Kotlin, expect and actual files are used to share code between different platforms (like iOS and JavaScript) while allowing platform-specific implementations.

- `expect` file: Defines an interface or class that you expect to use in your common code. It's like a contract saying, "I expect this function or class to exist."

- `actual` file: Provides the platform-specific implementation of the expect declaration. It's where you write the actual code that fulfills the contract defined in the expect file.

### File Structure

- `commonMain` directory contains shared code and `expect` declarations.
- Platform-specific directories (`androidMain`, `iosMain`, `jsMain`) contain `actual` implementations.
To use or modify platform-specific behavior, edit the actual file in the relevant platform directory.

> This project also have an `androidAndIosShared` folder under `commonMain`, to avoid boilerplate for the code used both Android and iOS side.

## File structure

I will not describe every file since some of them are obvious, but here is a list of the most complex or confusing ones.

### commonMain

- `utils/PlatformList` and `utils/PlatformMap` are an override of the Kotlin and JS Arrays and Maps. <br/>
Their purpose is to lighten the project, because transpiling Kotlin code in JS is heavy and slow.<br/>
These have an implementation inside `androidAndIosShared` and under `jsMain`.
- `AbstractSharedAnalytics` : This is the main file of this project. It regroups all the shared logic for initializing the library and validating, building and sending events.
- `EventService` : This file is the singleton used to validate events and their properties before sending them to AbstractSharedAnalytics. <br/>
It lists all of the mandatory and optional properties for each event.
- `SharedAnalytics` : An expect/actual file that implements `AbstractSharedAnalytics` in all platforms, especially for HTTP request sending.

### jsMain

- `resources/package.json` : The package.json used when publishing the lib to [npm](https://www.npmjs.com/package/mealz-shared-analytics).
- `utils/sendEvent` : An override for the `EventService.sendEvent` method that allows us to use native JS objects instead of using the `PlausibleEvent` type.

### androidMain and IosMain

- `PlausibleEvent.<platform>.kt` : Contains an util function to cast the props in the right type in the Android and iOS projects.

## Build

### Using Makefile

You can run `make` to build every platform, you also can specify `make <js|ios|android>`.

Only the `js` one is really useful, to build android or iOS, you can just run `./gradlew build`, or let the IDE build the dependency itself, as explained in the [Test section](#test).

### Using gradle (legacy)

#### Android aar

- Run `./gradlew assembleRelease`
- Output: `/mealz-shared-analytics/build/outputs/aar/mealz-shared-analytics-release.aar`

#### iOS Framework

- Run `./gradlew assembleXCFramework`
- Output: `/mealz-shared-analytics/build/XCFrameworks/release/mealz-shared-analytics.xcframework`

#### JS file

- Run `./gradlew jsBrowserProductionWebpack`
- Output: `/mealz-shared-analytics/build/dist/js/productionExecutable/main.js`

## Test

Depending on the dev you are doing, you may need to test it in different platforms. It is a good reflex to test every one of them before pushing a branch.

### JS

Build the output and link it to you component by doing this :

```bash
make js                              # Build
cd mealz-shared-analytics/dist       # Go to build folder
npm link                             # Create npm link
cd ../../../mealz-ssr-api            # Go to web project
npm link mealz-shared-analytics      # Link to local analytics
```

You can now test with your local changes.

### Android and iOS

Inside `MealzCore`, use the local dependency by adding this code in `settings.gradle.kts`.

```kotlin
includeBuild("../../MealzSharedAnalytics") {
    dependencySubstitution {
        substitute(module("ai.mealz.analytics:mealz-shared-analytics")).using(project(":mealz-shared-analytics"))
    }
}
```

Once it is done, use your local MealzCore project in Android and iOS, and test your changes.

## Release

The release is done using [Fastlane](https://fastlane.tools/). A staging release is created in Android and iOS when a pull request is merged into `develop`. A production release is created when `develop` is merged into `main`. 

> Take care not to delete the `develop` branch when merging it into `main`.

The GitHub CI/CD scripts are located in `.github/workflows`. Release and Staging scripts call `fastlane/Fastfile` to do the **mobile** release. The npm release is done directly in the GitHub workflow.

## FAQ

Here is a list of all StackOverflow questions :

- [Add an event](https://stackoverflowteams.com/c/miam/questions/52)
- [Edit event properties](https://stackoverflowteams.com/c/miam/questions/58)
- [Edit valid path parts](https://stackoverflowteams.com/c/miam/questions/60)
- [Edit valid journeys](https://stackoverflowteams.com/c/miam/questions/62)
- [Edit HTTP requests](https://stackoverflowteams.com/c/miam/questions/64)
