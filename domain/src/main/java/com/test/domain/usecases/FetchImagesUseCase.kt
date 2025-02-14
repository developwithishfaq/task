package com.test.domain.usecases

import com.test.domain.repository.FetchPicsRepository
import javax.inject.Inject

class FetchImagesUseCase @Inject constructor(
    private val repository: FetchPicsRepository
) {
    suspend operator fun invoke() = repository.getPictures()
}