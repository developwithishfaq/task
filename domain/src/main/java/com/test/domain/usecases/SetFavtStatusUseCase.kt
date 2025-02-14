package com.test.domain.usecases

import com.test.domain.model.ImageModelUi
import com.test.domain.repository.FavouriteRepository
import javax.inject.Inject

class SetFavtStatusUseCase @Inject constructor(
    private val repository: FavouriteRepository
) {
    suspend operator fun invoke(model: ImageModelUi, favt: Boolean) {
        if (favt) {
            repository.saveAsFavourite(model)
        } else {
            repository.delete(model.id)
        }
    }
}