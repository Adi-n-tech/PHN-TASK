package com.phn.task.repository

import android.util.Log
import com.app.trackinggadgetspro.Interface.APIResponseListener
import com.google.firebase.firestore.FirebaseFirestore
import com.phn.task.api.RetrofitService

class MainRepository constructor(private val retrofitService: RetrofitService) {
    val db = FirebaseFirestore.getInstance()

    suspend fun getAllProducts() = retrofitService.getAllProducts()

    fun loginUser(
        email: String,
        password: String,
        requestCode: Int,
        apiResponseListener: APIResponseListener
    ) = db.collection("users").whereEqualTo("email", email).whereEqualTo("password", password).get()
        .addOnCompleteListener { documents ->
            if (documents.result.documents.isNotEmpty()) {
                var doc = documents.result.documents[0]
                if (doc.exists()) {
                    apiResponseListener.onSuccess(
                        doc,
                        requestCode
                    )
                } else {
                    apiResponseListener.onSuccess(
                        "user not exist",
                        requestCode
                    )
                }
            } else {
                apiResponseListener.onSuccess(
                    "user not exist",
                    requestCode
                )
            }
        }
        .addOnFailureListener { apiResponseListener.onFailure(it, requestCode) }

    fun registerUser(
        user: HashMap<String, String>,
        apiResponseListener: APIResponseListener,
        requestCode: Int
    ) = db.collection("users").add(user)
        .addOnSuccessListener { apiResponseListener.onSuccess(it.get(), 101) }
        .addOnFailureListener { apiResponseListener.onFailure(it, requestCode) }
}