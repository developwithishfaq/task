package com.test.presentation.compose.screens.favt_screen

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
import androidx.compose.runtime.Composable
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
import com.test.framework.core.composables.NormalText
import com.test.presentation.xml.fragments.favts.FavtScreenEvents
import com.test.presentation.xml.fragments.favts.FavtsFragmentViewModel

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun FavtScreen(
    viewModel: FavtsFragmentViewModel = hiltViewModel()
) {
    val state by viewModel.images.collectAsStateWithLifecycle()
    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
                .padding(horizontal = 10.dp, vertical = 10.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            NormalText(
                text = "Favourites"
            )
            NormalText(
                "Clear All",
                fontSize = 15,
                modifier = Modifier
                    .clickable {
                        viewModel.onEvent(FavtScreenEvents.ClearAll)
                    }
            )
        }
        if (state.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                NormalText("No Favourites")
            }
        } else {
            LazyVerticalGrid(
                columns = GridCells.Fixed(3)
            ) {
                items(state) { model ->
                    Box(
                        modifier = Modifier
                            .padding(horizontal = 10.dp, vertical = 6.dp)
                            .fillMaxWidth()
                            .height(140.dp)
                            .clip(RoundedCornerShape(12.dp))
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
                                        FavtScreenEvents.RemoveFromFav(model)
                                    )
                                }
                        )
                    }
                }
            }
        }
    }

}