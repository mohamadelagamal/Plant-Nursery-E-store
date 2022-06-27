package com.base
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

abstract class BaseViewModel<N> : ViewModel(){
    var navigator:N?=null
    var showLoading = MutableLiveData<Boolean>()
    var messageLiveData = MutableLiveData<String>()
    var userMessage = MutableLiveData<Boolean>()
    var adminMessage = MutableLiveData<Boolean>()
    var managerMessage = MutableLiveData<Boolean>()

}