package com.example.studymanager.models.DTO

import com.squareup.moshi.Json

data class UserDTO(
    @Json(name = "token")
    val authToken: String
)