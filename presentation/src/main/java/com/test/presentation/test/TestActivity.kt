package com.test.presentation.test

import android.Manifest
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.LaunchedEffect
import com.test.presentation.NotificationHelper
import com.test.presentation.xml.XmlAppActivity
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class TestActivity : ComponentActivity() {
    private lateinit var notiHelper: NotificationHelper

    //    private val fikerFreeHelper = FikerFreeHelper()
//    private val fikerFreeHelper = TelenorHelper(this@TestActivity)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val launcher =
                rememberLauncherForActivityResult(ActivityResultContracts.RequestPermission()) {

                }
            LaunchedEffect(Unit) {
                launcher.launch(Manifest.permission.POST_NOTIFICATIONS)
            }
            Column {
                Button(
                    onClick = {
                        startActivity(
                            Intent(
                                this@TestActivity,
                                XmlAppActivity::class.java
                            )
                        )
                        finish()
                        notiHelper = NotificationHelper(this@TestActivity)
                        notiHelper.showNotification(
                            title = "Hi",
                            message = "Hello",
                            intent = Intent(
                                this@TestActivity,
                                XmlAppActivity::class.java
                            ),
                            notificationId = 1
                        )
                    }
                ) {
                    Text("Click")
                }
            }
        }
    }
}