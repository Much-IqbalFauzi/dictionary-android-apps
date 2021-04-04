package com.midtest.dictionary.model

class User(val User: UserData)

class UserData(
    val username: String,
    val nama: String,
    val email: String,
    val password: String,
    val bio: String
)

class UserLogin(val status: String, val user: UserData)
