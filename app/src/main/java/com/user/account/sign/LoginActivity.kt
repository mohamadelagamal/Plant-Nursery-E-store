package com.user.account.sign

import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.admin.AdminActivity
import com.base.BaseActivity
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.manager.main.ManagerActivity
import com.user.R
import com.user.account.register.RegisterActivity
import com.user.databinding.ActivityLoginBinding
import com.user.main.UserActivity


class LoginActivity : BaseActivity<ActivityLoginBinding,LoginViewModel>() ,Navigator{
    override fun onCreate(savedInstanceState: Bundle?) {
        chose()
        super.onCreate(savedInstanceState)
        viewDataBinding.vmLogin=viewModel
        viewModel.navigator=this
            }

    override fun getLayoutID(): Int {
        return R.layout.activity_login
    }

    override fun makeViewModelProvider(): LoginViewModel {
        return ViewModelProvider(this).get(LoginViewModel::class.java)
    }

    override fun backToRegister() {
        val intent = Intent(this,RegisterActivity::class.java)
        startActivity(intent)
        finish()
    }

    override fun openHome() {
        val intent = Intent(this,UserActivity::class.java)
        startActivity(intent)
        finish()
    }

    override fun openAdminPage() {
        val intent = Intent(this,AdminActivity::class.java)
        startActivity(intent)
        finish()
    }

    override fun openManagerPage() {
    val intent= Intent(this, ManagerActivity::class.java)
    startActivity(intent)
    finish()
    }

    fun chose(){
        MaterialAlertDialogBuilder(this).setMessage("Who are you,Please check?").setCancelable(false)
            .setPositiveButton("Admin"){dialog,which->
                viewModel.adminMessage.value=true
            }
            .setNegativeButton("Manager"){dialog,which->
                viewModel.managerMessage.value=true
            }
            .setNeutralButton("User") {dialog,which->
                viewModel.userMessage.value=true
            }
            .show()
    }

}