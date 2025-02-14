package com.test.framework.core.composables

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun Test(modifier: Modifier = Modifier) {
    Text("Hi")
}

@Composable
fun VerticalSpace(space: Int = 10) {
    Spacer(
        Modifier
            .height(space.dp)
    )
}

@Composable
fun NormalText(
    text: String,
    fontSize: Int = 18,
    color: Color = Color.Black,
    modifier: Modifier = Modifier
) {
    Text(
        text = text,
        color = color,
        fontSize = fontSize.sp,
        modifier = modifier
    )
}

@Composable
fun HorizontalSpace(space: Int = 10) {
    Spacer(
        Modifier
            .width(space.dp)
    )
}