package com.rhino.dailymotivation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import com.rhino.dailymotivation.security.DeviceAuthorizationManager
import com.rhino.dailymotivation.ui.AboutScreen
import com.rhino.dailymotivation.ui.MainScreen
import com.rhino.dailymotivation.ui.UnauthorizedScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val authorizationManager = DeviceAuthorizationManager(applicationContext)
        val isAuthorized = authorizationManager.initializeAndAuthorize()

        setContent {
            DailyMotivationTheme(darkTheme = isSystemInDarkTheme()) {
                if (isAuthorized) {
                    MainAppScreen(authorizationManager = authorizationManager)
                } else {
                    UnauthorizedScreen(
                        onClose = {
                            finishAffinity()
                        }
                    )
                }
            }
        }
    }
}

@Composable
private fun MainAppScreen(
    authorizationManager: DeviceAuthorizationManager
) {
    var showAbout by remember { mutableStateOf(false) }

    if (showAbout) {
        AboutScreen(
            authorized = true,
            onBack = { showAbout = false }
        )
    } else {
        MainScreen(
            authorizationManager = authorizationManager,
            onOpenAbout = { showAbout = true }
        )
    }
}
