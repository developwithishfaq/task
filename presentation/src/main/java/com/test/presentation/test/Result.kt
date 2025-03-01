package com.test.presentation.test

import kotlinx.serialization.Serializable

@Serializable
data class Result(
    val cnic: Long? = null,
    val dob: String? = null,
    val email: String? = null,
    val gender: String? = null,
    val name: String? = null,
    val profile_pic_url: String? = null,
    val residential_address: String? = null,
    val userId: Int? = null
)