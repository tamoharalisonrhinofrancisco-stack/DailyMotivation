package com.rhino.dailymotivation.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun AboutScreen(
    authorized: Boolean,
    onBack: () -> Unit
) {
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
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = "Daily Motivation",
                style = MaterialTheme.typography.headlineSmall
            )

            Text(
                text = "Created by TAMOHA Ralison Rhino Françisco",
                style = MaterialTheme.typography.bodyMedium
            )

            Text(
                text = "Offline • Private • Device Bound",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.primary
            )

            Card(
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Text(text = "Device Status", style = MaterialTheme.typography.titleMedium)
                    Text(
                        text = if (authorized) "Authorized" else "Unauthorized",
                        style = MaterialTheme.typography.bodyLarge
                    )

                    Text(text = "Connection", style = MaterialTheme.typography.titleMedium)
                    Text(text = "Offline", style = MaterialTheme.typography.bodyLarge)

                    Text(text = "Device-bound security: Active", style = MaterialTheme.typography.bodyLarge)
                }
            }

            Button(
                onClick = onBack,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Back")
            }
        }
    }
}
