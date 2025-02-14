package com.test.domain.usecases

import com.test.domain.repository.FavouriteRepository
import javax.inject.Inject

class FetchFavtImagesUseCase @Inject constructor(
    private val repository: FavouriteRepository
) {
    operator fun invoke() = repository.getAllFavourites()
}