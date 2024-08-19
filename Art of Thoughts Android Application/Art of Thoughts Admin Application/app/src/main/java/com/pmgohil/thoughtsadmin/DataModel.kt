package com.pmgohil.thoughtsadmin

data class UserData(
    val name: String,
    val email: String,
    val unm: String,
    val mail_id_image: String,
    val internal_image: String
)

data class ThoughtsData(
    val thoughts_key: String, val thoughts: String
)

data class ImageModel(
    val imageUrl: String, val imageId: String
)

data class AdminUsersData(
    val email: String, val password: String, var role: String, val unm: String
)

data class AdminRequestData(
    val email: String, val password: String, val confirm_password: String, val unm: String
)