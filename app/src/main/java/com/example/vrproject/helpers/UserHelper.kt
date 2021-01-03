package com.example.vrproject.helpers

import android.util.Log
import com.example.vrproject.models.User
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.*

class UserHelper {

    companion object {
        private val COLLECTION_NAME = "users"

        // --Collection Reference--

        fun getUsersCollection(): CollectionReference {
            return FirebaseFirestore.getInstance().collection(COLLECTION_NAME)
        }

        // --Create--

        fun createUser(user: User): Task<Void> {
            return getUsersCollection()
                .document(user.uid).set(user)
        }

        // --Get--

        fun getUsers(): Query {
            return getUsersCollection()
        }

        // --Delete--

        fun deleteUser(uid: String): Task<Void> {
            return getUsersCollection()
                .document(uid).delete()
        }
    }
}