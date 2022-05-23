package com.user.account.register

import android.util.Log
import androidx.databinding.ObservableField
import com.base.BaseViewModel
import com.database.addUserToFirebase
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.model.ApplicationUser
import com.model.DataUtil

class RegisterViewModel : BaseViewModel<Navigator>() {
    // make connection between items by observer
    var name = ObservableField<String>()
    var password = ObservableField<String>()
    var email = ObservableField<String>()
    var phone = ObservableField<String>()
    //.. set error result
    var nameError = ObservableField<String>()
    var passwordError = ObservableField<String>()
    var emailError = ObservableField<String>()
    var phoneError = ObservableField<String>()
    fun openLoginActivity(){
        navigator?.openLoginActivity()
    }

    val auth = Firebase.auth
    fun openHome(){
        if (validation()){
        createAccount()
        }
    }

    private fun createAccount() {
        showLoading.value=true
        auth.createUserWithEmailAndPassword(email.get()!!,password.get()!!)
            .addOnCompleteListener {task->
            showLoading.value=false
            when {
                task.isSuccessful -> {
                    // this function set item in fireStore in firebase
                    createFireStoreUser(task.result.user?.uid)
                }
                else->{
                    messageLiveData.value=task.exception?.localizedMessage
                    Log.e("firebase","filed"+task.exception?.localizedMessage)
                }
            }
        }
    }

    private fun createFireStoreUser(uid: String?) {
       showLoading.value=true
        val appUser = ApplicationUser(
            id = uid,
            userName = name.get(),
            email = email.get()
        )
        addUserToFirebase(appUser ,
            OnSuccessListener {
                showLoading.value=false
                DataUtil.user = appUser
                navigator?.openUserActivity()
        }, OnFailureListener {
            showLoading.value=false
            messageLiveData.value=it.localizedMessage
        })
    }

    fun validation():Boolean{
        var valid = true
        if (name.get().isNullOrBlank()){
            nameError.set("please enter your name")
            valid=false
        }else{
            nameError.set(null)
        }
        if (email.get().isNullOrBlank()){
            emailError.set("please enter your email")
            valid=false
        }else{
            emailError.set(null)
        }
        if (password.get().isNullOrBlank()){
            passwordError.set("please enter your password")
            valid=false
        }else{
            passwordError.set(null)
        }
        if (phone.get().isNullOrBlank()){
            phoneError.set("please enter your phone")
            valid=false
        }else{
            phoneError.set(null)
        }
        return valid
    }

}