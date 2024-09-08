package com.rakangsoftware.interop

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import di.appModule
import di.platformModule
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.KoinApplication
import org.koin.compose.koinInject

@Composable
@Preview
fun App() {
    KoinApplication(application = {
        modules(appModule, platformModule)
    }) {
        val service = koinInject<Service>()

        // Remember the message state
        var message by remember { mutableStateOf("Waiting for message...") }

        // LaunchedEffect to call the service.onDone and update message
        LaunchedEffect(Unit) {
            service.onDone { result ->
                message = result
            }
        }

        // Displaying the message in a centered Text component
        MaterialTheme {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = message,
                    fontSize = 32.sp, // Set the font size to 32sp
                    fontWeight = FontWeight.Bold // Optional: Make the text bold
                )
            }
        }
    }
}
