package com.example.vrproject.models

data class User (
    val uid: String,
    val score: Float
) {
    constructor() : this("", 0.0f)
}