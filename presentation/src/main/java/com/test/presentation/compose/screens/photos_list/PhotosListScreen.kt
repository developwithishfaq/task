package com.test.presentation.compose.screens.photos_list

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.test.framework.core.NetworkResponse
import com.test.framework.core.composables.NormalText
import com.test.framework.core.composables.VerticalSpace
import com.test.presentation.xml.fragments.photos.PhotosFragmentViewModel
import com.test.presentation.xml.fragments.photos.PhotosScreenEvents

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun PhotosListScreen(
    moveToFavtScreen: () -> Unit,
    viewModel: PhotosFragmentViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    LaunchedEffect(Unit) {
        viewModel.onEvent(PhotosScreenEvents.Refresh)
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        VerticalSpace()
        if (state.selectedImage != null) {
            val model = state.selectedImage
            BackHandler {
                viewModel.onEvent(PhotosScreenEvents.SetImageModel(null))
            }
            Column(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                Column {
                    VerticalSpace(5)
                    GlideImage(
                        model = model?.download_url,
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxSize()
                            .weight(1f)
                            .background(Color.Black)
                    )
                    VerticalSpace()
                    NormalText("Id : ${model?.id}")
                    VerticalSpace(5)
                    NormalText("Author : ${model?.author}")
                }
            }
        } else {

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White)
                    .padding(horizontal = 10.dp, vertical = 10.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                NormalText(
                    text = "Photos"
                )
                NormalText(
                    "Show Favs",
                    fontSize = 15,
                    modifier = Modifier
                        .clickable {
                            moveToFavtScreen.invoke()
                        }
                )
            }
            when (state.photos) {
                is NetworkResponse.Failure -> {
                    Box(
                        modifier = Modifier
                            .fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            NormalText(text = "Something Went Wrong")
                            Button(
                                onClick = {
                                    viewModel.onEvent(
                                        PhotosScreenEvents.Refresh
                                    )
                                }
                            ) {
                                NormalText("Refresh")
                            }
                        }
                    }
                }

                is NetworkResponse.Idle -> {

                }

                is NetworkResponse.Loading -> {
                    Box(
                        modifier = Modifier
                            .fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }

                is NetworkResponse.Success -> {
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(3)
                    ) {
                        items(state.photos.data ?: emptyList()) { model ->
                            Box(
                                modifier = Modifier
                                    .padding(horizontal = 10.dp, vertical = 6.dp)
                                    .fillMaxWidth()
                                    .height(140.dp)
                                    .clip(RoundedCornerShape(12.dp))
                                    .clickable {
                                        viewModel.onEvent(PhotosScreenEvents.SetImageModel(model))
                                    }
                            ) {
                                GlideImage(
                                    model = model.download_url,
                                    contentDescription = null,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(140.dp),
                                    contentScale = ContentScale.Crop
                                )
                                Image(
                                    painter = painterResource(
                                        if (model.isFav) {
                                            com.test.framework.R.drawable.ic_fav_filled
                                        } else {
                                            com.test.framework.R.drawable.ic_favourite
                                        }
                                    ),
                                    contentDescription = null,
                                    modifier = Modifier
                                        .padding(10.dp)
                                        .size(25.dp)
                                        .align(Alignment.TopEnd)
                                        .clickable {
                                            viewModel.onEvent(
                                                PhotosScreenEvents.OnFavt(model)
                                            )
                                        }
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
