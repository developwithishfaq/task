package com.test.domain.usecases

import com.test.domain.model.ImageModelUi
import com.test.domain.repository.FavouriteRepository
import javax.inject.Inject

class ClearAllFavtsUseCase @Inject constructor(
    private val repository: FavouriteRepository
) {
    suspend operator fun invoke() {
        repository.deleteAll()
    }
}