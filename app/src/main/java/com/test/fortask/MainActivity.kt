package com.test.fortask

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.test.data.utils.MyPrefs
import com.test.framework.core.composables.NormalText
import com.test.framework.core.composables.VerticalSpace
import com.test.presentation.compose.ComposeActivity
import com.test.presentation.xml.XmlAppActivity
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject
    lateinit var prefs: MyPrefs
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 10.dp, vertical = 10.dp)
            ) {
                var showAppInXml by remember { mutableStateOf(true) }
                var useRetrofit by remember { mutableStateOf(true) }
                LaunchedEffect(useRetrofit) {
                    prefs.useRetrofit = useRetrofit
                }
                VerticalSpace()
                NormalText(
                    text = "Task",
                    fontSize = 20
                )
                VerticalSpace(6)
                NormalText(
                    text = "I did this task , I have added few extra things like instead of simple mvvm i did used Clean Architecture which u know it contains MVVM and also I developed this app in multi module",
                    fontSize = 14
                )
                VerticalSpace(15)
                NormalText(
                    text = "Project Modes",
                    fontSize = 18
                )
                Row(
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RadioBtnWithText(
                        selected = showAppInXml,
                        text = "XML"
                    ) {
                        showAppInXml = !showAppInXml
                    }
                    RadioBtnWithText(
                        selected = showAppInXml.not(),
                        text = "Jetpack Compose"
                    ) {
                        showAppInXml = !showAppInXml
                    }
                }
                VerticalSpace(12)
                NormalText(
                    text = "Networking Library",
                    fontSize = 18
                )
                Row(
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RadioBtnWithText(
                        selected = useRetrofit,
                        text = "Retrofit"
                    ) {
                        useRetrofit = !useRetrofit
                    }
                    RadioBtnWithText(
                        selected = useRetrofit.not(),
                        text = "Ktor"
                    ) {
                        useRetrofit = !useRetrofit
                    }
                }
                VerticalSpace(20)
                Button(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 10.dp, vertical = 4.dp),
                    onClick = {
                        val activity = if (showAppInXml) {
                            XmlAppActivity::class.java
                        } else {
                            ComposeActivity::class.java
                        }
                        startActivity(
                            Intent(this@MainActivity, activity)
                        )
                    }
                ) {
                    NormalText(
                        text = "Let Start",
                        color = Color.White,
                        modifier = Modifier
                            .padding(vertical = 8.dp),
                        fontSize = 16
                    )
                }
            }
        }
    }
}


@Composable
fun RadioBtnWithText(selected: Boolean, text: String, onClick: () -> Unit) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        RadioButton(
            selected = selected,
            onClick = {
                onClick.invoke()
            }
        )
        Text(text)
    }
}
