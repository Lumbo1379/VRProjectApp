package com.example.vrproject

import com.example.vrproject.helpers.UserHelper
import com.example.vrproject.models.User
import com.google.firebase.auth.FirebaseAuth

class Utils {

    companion object {

        fun createTestProfiles() {
            val user1 = User("user1", 5.4f)
            val user2 = User("user2", 1.3f)
            val user3 = User("user3", 7.9f)

            UserHelper.createUser(user1)
            UserHelper.createUser(user2)
            UserHelper.createUser(user3)
        }

        fun addMyselfTestProfile() {
            val user = User(FirebaseAuth.getInstance().currentUser?.uid.toString(), 6.0f)

            UserHelper.createUser(user)
        }
    }
}