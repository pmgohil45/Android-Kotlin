package com.pmgohil.thoughts

data class UserData(
    val name: String,
    val email: String,
    val unm: String,
    val mail_id_image: String,
    val internal_image: String
)

data class ThoughtsDataModel(
    val thoughts: String = ""
)

data class ImageModel(
    val imageUrl: String
)

data class MenuItem(val id: String, val title: String)