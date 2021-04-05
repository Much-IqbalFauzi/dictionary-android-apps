package com.midtest.dictionary.model

class User(val User: UserData)

class UserData(
    var username: String,
    var nama: String,
    var email: String,
    var password: String,
    var bio: String
)

class UserLogin(val status: String, val user: UserData)

class LoggedUser {
    var user = UserData("none", "none", "none", "none", "none")

    get() = field

    set(value) {
        field = value
    }
}

class LoggedInUser {
    var username: String = "none"
    var nama: String  = "none"
    var email: String = "none"
    var password: String = "none"
    var bio: String = "none"

        get() = field

        set(value) {
            field = value
        }
}
