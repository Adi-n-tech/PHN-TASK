package com.phn.task.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.app.trackinggadgetspro.Interface.APIResponseListener
import com.google.firebase.firestore.DocumentSnapshot
import com.phn.task.model.Product
import com.phn.task.repository.MainRepository
import kotlinx.coroutines.*

class MainViewModel constructor(private val mainRepository: MainRepository) : ViewModel(),
    APIResponseListener {

    val errorMessage = MutableLiveData<String>()
    val productList = MutableLiveData<List<Product>>()
    val userRegistered = MutableLiveData<String>()
    var job: Job? = null

    val loading = MutableLiveData<Boolean>()
    val documentSnapshot = MutableLiveData<DocumentSnapshot>()

    fun getAllProducts() {
        job = CoroutineScope(Dispatchers.IO).launch {
            val response = mainRepository.getAllProducts()
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    productList.postValue(response.body())
                    loading.value = false
                } else {
                    onError("Error : ${response.message()} ")
                }
            }
        }
    }

    fun registerUser(user: HashMap<String, String>, requestCode: Int) {
        loading.value = true
        mainRepository.registerUser(user, this, requestCode)
    }

    fun loginUser(email: String, password: String, requestCode: Int) {
        loading.value = true
        mainRepository.loginUser(email, password, requestCode, this)
    }

    private fun onError(message: String) {
        errorMessage.value = message
        loading.value = false
    }

    override fun onCleared() {
        super.onCleared()
        job?.cancel()
    }

    override fun onSuccess(callResponse: Any?, requestID: Int?) {
        if (callResponse is String) {
            errorMessage.postValue(callResponse)
        } else {
            when (requestID) {
                101 -> userRegistered.postValue("Register Successfully")
                102 -> userRegistered.postValue("Login Successfully")
            }
        }
        loading.value = false
    }

    override fun onFailure(error: Throwable?, requestID: Int?) {
        if (error != null) {
            errorMessage.value = error.message
        }
    }
}