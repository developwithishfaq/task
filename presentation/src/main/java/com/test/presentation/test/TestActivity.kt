package com.test.presentation.test

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class TestActivity : ComponentActivity() {
    private val fikerFreeHelper = FikerFreeHelper()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Column {
                Button(
                    onClick = {
                        Toast.makeText(this@TestActivity, "Clicked", Toast.LENGTH_SHORT).show()
                        fikerFreeHelper.fetchProfileData(this@TestActivity)
                    }
                ) {
                    Text("Click")
                }
            }
        }
    }
}