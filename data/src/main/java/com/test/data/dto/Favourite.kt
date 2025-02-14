package com.test.data.dto

import com.test.domain.model.ImageModelUi
import com.test.framework.core.NetworkResponse
import com.test.network.model.ImageModelNetwork
import com.test.persistence.entity.FavouriteEntity


fun ImageModelUi.toFavEntity(): FavouriteEntity {
    return FavouriteEntity(
        id = id, author = author, url = url, download_url = download_url
    )
}


fun ImageModelUi.toNetWorkModel(): FavouriteEntity {
    return FavouriteEntity(
        id = id, author = author, url = url, download_url = download_url
    )
}

fun FavouriteEntity.toUiModel(): ImageModelUi {
    return ImageModelUi(
        id = id, author = author, url = url, download_url = download_url, isFav = true
    )
}

fun ImageModelNetwork.toUiModel(): ImageModelUi {
    return ImageModelUi(
        id = id, author = author, url = url, download_url = download_url
    )
}


fun NetworkResponse<List<ImageModelNetwork>>.toUiList(): NetworkResponse<List<ImageModelUi>> {
    return when (this) {
        is NetworkResponse.Failure -> NetworkResponse.Failure(this.error)
        is NetworkResponse.Idle -> NetworkResponse.Idle()
        is NetworkResponse.Loading -> NetworkResponse.Loading()
        is NetworkResponse.Success -> {
            val data = this.data?.map {
                it.toUiModel()
            }
            NetworkResponse.Success(data)
        }
    }
}