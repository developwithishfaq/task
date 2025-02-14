package com.test.domain.usecases

import com.test.domain.repository.FavouriteRepository
import javax.inject.Inject

class IsFavtUseCase @Inject constructor(
    private val repository: FavouriteRepository
) {
    suspend operator fun invoke(id: String) = repository.isFavourite(id)
}