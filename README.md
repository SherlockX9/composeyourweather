This is a Kotlin Multiplatform project targeting Desktop (JVM).
It provides weather information retrieved from https://openweathermap.org/.

It uses:
- Compose multiplatform
- Ktor (client)
- Datastore Preferences

### Build and Run Desktop (JVM) Application

- Create an api key from https://openweathermap.org/.
- Then build and run the application:

  - on macOS/Linux
    ```shell
    ./gradlew :composeApp:run
    ```
  - on Windows
    ```shell
    .\gradlew.bat :composeApp:run
    ```
- To successfully retrieve weather information, open the settings menu within the application and enter your api key.

---
