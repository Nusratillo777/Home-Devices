## Description
This app shows a list of devices in the smart home such as lights, roller shutters, or heaters.
You can filter them by type and control them. And also it has a user profile page. With this, you can change the user's
personal info. This app is available in 2 languages(English/French). To make the app comfortable for users, material design
components were used. In addition, if you change the device's mode to dark, the app adjusts dark mode.

## Architecture
    - UI: activities is used with ViewModels (MVVM)
    - Data: Models, Enntities, Mappers, Data stroges, and Business logic are located
    - Domain: Encapsulates business logic

## Technologies
    - Android SDK
    - Kotlin
    - RxJava
    - Room for database
    - JUnit/Mockito for testing
    - Koin for Dependency Injection
    - Proguard rules are configured