package com.test.data.repository

import com.test.data.dto.toUiList
import com.test.data.utils.MyPrefs
import com.test.domain.model.ImageModelUi
import com.test.domain.repository.FetchPicsRepository
import com.test.framework.core.NetworkResponse
import com.test.network.interfaces.GetPics
import com.test.network.ktor.GetPicsWithKtorImpl
import com.test.network.retrofit.GetPicsRetrofitImpl

class FetchPicsRepositoryImpl(
    private val prefs: MyPrefs
) : FetchPicsRepository {
    private lateinit var getPics: GetPics

    override suspend fun getPictures(): NetworkResponse<List<ImageModelUi>> {
        getPics = if (prefs.useRetrofit) {
            GetPicsRetrofitImpl()
        } else {
            GetPicsWithKtorImpl()
        }
        return getPics.getPics().toUiList()
    }
}