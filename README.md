### Project Interop

---

# Compose Multiplatform Project with Service Implementation

This project demonstrates a Kotlin Multiplatform Mobile (KMM) setup with service implementation for Android and iOS using Koin for dependency injection. The main feature is handling platform-specific services in a common, reusable way.

## Table of Contents
1. [Overview](#overview)
2. [Project Structure](#project-structure)
3. [Service Implementation](#service-implementation)
4. [Dependency Injection with Koin](#dependency-injection-with-koin)
5. [Running the Project](#running-the-project)

---

### 1. Overview <a name="overview"></a>
This project uses Kotlin Multiplatform to share business logic between Android and iOS platforms. It demonstrates a simple `Service` interface implemented differently for Android and iOS, and the usage of Koin for dependency injection to resolve platform-specific implementations.

### 2. Project Structure <a name="project-structure"></a>

The project is divided into three source sets:

- `commonMain`: Contains shared code for both platforms, including the `Service` interface.
- `androidMain`: Contains Android-specific code, including the implementation of `Service` and its dependency injection module.
- `iosMain`: Contains iOS-specific code, with a placeholder for future implementation of the `Service`.

**Directory Structure:**

```
composeApp/
│
├── src/
│   ├── commonMain/
│   │   └── kotlin/com/rakangsoftware/interop/
│   │       └── Service.kt  # Common interface for Service
│   │
│   ├── androidMain/
│   │   └── kotlin/com/rakangsoftware/interop/
│   │       └── AndroidService.kt  # Android-specific Service implementation
│   │   └── kotlin/di/
│   │       └── module.android.kt  # Android Koin module
│   │
│   ├── iosMain/
│   │   └── kotlin/di/
│   │       └── module.ios.kt  # iOS Koin module (yet to implement iOSService)
│
└── build.gradle.kts  # KMM project build configuration
```

---

### 3. Service Implementation <a name="service-implementation"></a>

The service implementation is split between common and platform-specific modules.

#### Step 1: Define a Common Interface

In `commonMain`, define the `Service` interface that both Android and iOS will implement.

```kotlin
// composeApp/src/commonMain/kotlin/com/rakangsoftware/interop/Service.kt

package com.rakangsoftware.interop

interface Service {
    fun onDone(callback: (String) -> Unit)
}
```

#### Step 2: Implement Android Service

In `androidMain`, implement the `Service` interface using Kotlin coroutines. The service simulates some work by using a delay and calling a callback at different times.

```kotlin
// composeApp/src/androidMain/kotlin/com/rakangsoftware/interop/AndroidService.kt

package com.rakangsoftware.interop

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class AndroidService : Service {

    private val serviceScope = CoroutineScope(Dispatchers.Default)

    override fun onDone(callback: (String) -> Unit) {
        serviceScope.launch {
            callback("Waiting for Android!")
            delay(3000)
            callback("Android Done!")
        }
    }
}
```

#### Future Steps: Implement iOS Service

For iOS, you will eventually add an implementation of the `Service` interface using Swift-specific APIs. This part is still pending.

---

### 4. Dependency Injection with Koin <a name="dependency-injection-with-koin"></a>

Koin is used for dependency injection in this project. The shared `appModule` is defined in `commonMain`, and platform-specific modules are defined in `androidMain` and `iosMain`.

#### Step 1: Define the Common Koin Module

In `commonMain`, define the `appModule`. This module can be extended later with shared dependencies, but for now, it is empty.

```kotlin
// composeApp/src/commonMain/kotlin/di/module.kt

package di

import org.koin.core.module.Module
import org.koin.dsl.module

val appModule = module {
    // Add shared dependencies here
}

expect val platformModule: Module
```

#### Step 2: Define the Android Koin Module

In `androidMain`, define the `platformModule` to provide the `AndroidService` implementation of `Service`.

```kotlin
// composeApp/src/androidMain/kotlin/di/module.android.kt

package di

import com.rakangsoftware.interop.AndroidService
import com.rakangsoftware.interop.Service
import org.koin.dsl.module

actual val platformModule = module {
    single<Service> { AndroidService() }
}
```

#### Step 3: Define the iOS Koin Module

In `iosMain`, define a placeholder `platformModule` for future iOS implementation.

```kotlin
// composeApp/src/iosMain/kotlin/di/module.ios.kt

package di

import org.koin.dsl.module

actual val platformModule = module {
    // Add iOS specific service binding here
}
```

#### Step 4: Inject Dependencies in Compose

In your `@Composable` functions, inject dependencies using Koin. Here's an example of how to use the `Service` and handle the callback.

```kotlin
@Composable
@Preview
fun App() {
    KoinApplication(application = {
        modules(appModule, platformModule)
    }) {
        val service = koinInject<Service>()
        
        // Remember the message state
        var message by remember { mutableStateOf("Waiting...") }

        // LaunchedEffect to handle the callback
        LaunchedEffect(Unit) {
            service.onDone { result ->
                message = result
            }
        }

        // Display the message
        MaterialTheme {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(text = message, fontSize = 32.sp)
            }
        }
    }
}
```

---

### 5. Running the Project <a name="running-the-project"></a>

#### Android:
1. Open the project in Android Studio.
2. Select an Android device/emulator and run the project.
3. The app should display a message from the `AndroidService`.

#### iOS:
1. Open the project in Xcode (after generating the necessary Xcode project files).
2. Select an iOS device/emulator and run the project.
3. (iOS service implementation is pending, so nothing is displayed yet.)
