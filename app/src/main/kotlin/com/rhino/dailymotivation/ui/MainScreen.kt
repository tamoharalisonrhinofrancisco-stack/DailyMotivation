package com.rhino.dailymotivation.ui

import android.content.Context
import android.content.Intent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.unit.dp
import com.rhino.dailymotivation.data.MotivationRepository
import com.rhino.dailymotivation.security.DeviceAuthorizationManager
import com.rhino.dailymotivation.util.DateUtils

@Composable
fun MainScreen(
    authorizationManager: DeviceAuthorizationManager,
    onOpenAbout: () -> Unit
) {
    val context = LocalContext.current
    val clipboardManager = LocalClipboardManager.current
    val repository = remember { MotivationRepository() }
    val initialMessage = remember { repository.getTodayMessage(DateUtils.todayKey()) }
    var currentMessage by remember { mutableStateOf(initialMessage) }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(18.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "DAILY MOTIVATION",
                style = MaterialTheme.typography.headlineMedium
            )

            Text(
                text = DateUtils.todayDateLabel(),
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.primary
            )

            Card(
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = currentMessage,
                    modifier = Modifier.padding(20.dp),
                    style = MaterialTheme.typography.bodyLarge
                )
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Button(
                    onClick = {
                        currentMessage = repository.getRandomMessage(currentMessage)
                    },
                    modifier = Modifier.weight(1f)
                ) {
                    Text("New Thought")
                }

                Button(
                    onClick = {
                        clipboardManager.setText(AnnotatedString(currentMessage))
                    },
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Copy")
                }
            }

            Button(
                onClick = onOpenAbout,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("About")
            }

            Text(
                text = if (authorizationManager.isDeviceAuthorizedState()) "Device-bound security: Active" else "Device verification: Failed",
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.secondary
            )
        }
    }
}
