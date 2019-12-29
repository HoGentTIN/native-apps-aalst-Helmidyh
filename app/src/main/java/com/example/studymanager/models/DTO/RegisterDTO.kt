package com.example.studymanager.models.DTO

data class RegisterDTO(
    var Picture: String?,
    var Email: String,
    var Password: String,
    var PasswordConfirm: String
)