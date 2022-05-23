package com.user.account.sign

import android.util.Log
import androidx.databinding.ObservableField
import com.base.BaseViewModel
import com.database.getUser
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.model.ApplicationUser
import com.model.DataUtil

class LoginViewModel : BaseViewModel<Navigator>() {
    var email = ObservableField<String>()
    var password = ObservableField<String>()
//    var checkedUser:Boolean=false
//    var checkedAdmin:Boolean=false
//    var checkedManager:Boolean=false

    // set error
    var emailError = ObservableField<String>()
    var passwordError = ObservableField<String>()
    // set onclick
    var auth = Firebase.auth
    fun openHome(){
        when {
            validation() -> {
                if (userMessage.value==true){
                  //  messageLiveData.value="user success"
                    loginAccountFormFirebase()
                }
                if (adminMessage.value==true){
                  //  messageLiveData.value="admin success"
                    adminLogin()
                }
                if (managerMessage.value==true){
                    //messageLiveData.value="manager success"
                    managerLogin()
                }
            }
        }

    }

    private fun loginAccountFormFirebase() {
        showLoading.value=true
        auth.signInWithEmailAndPassword(email.get()!!,password.get()!!).addOnCompleteListener { task->
            showLoading.value=false
            when{
                task.isSuccessful->{
                    //  navigator?.openHome()
                    checkUserFormFireStore(task.result.user?.uid )
                    Log.e("firebase","Success Login"+task.exception?.localizedMessage)
                }else->{
                messageLiveData.value="password or email is wrong"
            }
            }
        }
    }

    private fun checkUserFormFireStore(uid: String?) {
        showLoading.value=true
        getUser(uid!!, OnSuccessListener {
            showLoading.value=false
            // transfer data form AppUser to make compress in user variable
            val user = it.toObject(ApplicationUser::class.java)
            if (user!=null){
                DataUtil.user=user
                navigator?.openHome()
                return@OnSuccessListener
            }
            messageLiveData.value="Invalid email or password"
        } , OnFailureListener {
            showLoading.value=false
            messageLiveData.value="Invalid email or password"
        })
    }

    fun  backToRegister(){
        navigator?.backToRegister()
    }
    fun validation():Boolean{
        var valid = true
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
        return valid
    }

    private fun adminLogin() {
        showLoading.value=true
        auth.signInWithEmailAndPassword(email.get()!!,password.get()!!).addOnCompleteListener { task->
            showLoading.value=false
            when{
                task.isSuccessful->{
                    //  navigator?.openHome()
                    adminChecked(task.result.user?.uid )
                    Log.e("firebase","Success Login"+task.exception?.localizedMessage)
                }else->{
                messageLiveData.value="password or email is wrong"
            }
            }
        }
    }
    private fun adminChecked(uid: String?) {
        showLoading.value=true
        getUser(uid!!, OnSuccessListener {
            showLoading.value=false
            // transfer data form AppUser to make compress in user variable
            val user = it.toObject(ApplicationUser::class.java)
            if (user!=null){
                DataUtil.user=user
                navigator?.openAdminPage()
                return@OnSuccessListener
            }
            messageLiveData.value="Invalid email or password"
        } , OnFailureListener {
            showLoading.value=false
            messageLiveData.value="Invalid email or password"
        })
    }
    //...
    private fun managerLogin() {
        showLoading.value=true
        auth.signInWithEmailAndPassword(email.get()!!,password.get()!!).addOnCompleteListener { task->
            showLoading.value=false
            when{
                task.isSuccessful->{
                    //  navigator?.openHome()
                    mangerChecked(task.result.user?.uid )
                    Log.e("firebase","Success Login"+task.exception?.localizedMessage)
                }else->{
                messageLiveData.value="password or email is wrong"
            }
            }
        }
    }
    private fun mangerChecked(uid: String?) {
        showLoading.value=true
        getUser(uid!!, OnSuccessListener {
            showLoading.value=false
            // transfer data form AppUser to make compress in user variable
            val user = it.toObject(ApplicationUser::class.java)
            if (user!=null){
                DataUtil.user=user
                navigator?.openManagerPage()
                return@OnSuccessListener
            }
            messageLiveData.value="Invalid email or password"
        } , OnFailureListener {
            showLoading.value=false
            messageLiveData.value="Invalid email or password"
        })
    }

}