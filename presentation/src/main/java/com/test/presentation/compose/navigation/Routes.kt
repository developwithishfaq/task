package com.test.presentation.compose.navigation

import kotlinx.serialization.Serializable

sealed class Routes {
    @Serializable
    data object PhotosScreen : Routes()
    @Serializable
    data object FavtScreen : Routes()
}