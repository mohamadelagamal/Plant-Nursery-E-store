package com.user.account.register

import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.base.BaseActivity
import com.user.R
import com.user.account.sign.LoginActivity
import com.user.databinding.ActivityRegsiterBinding
import com.user.main.UserActivity

class RegisterActivity : BaseActivity<ActivityRegsiterBinding,RegisterViewModel>() ,Navigator{
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewDataBinding.vmRegister=viewModel
        viewModel.navigator=this
            }

    override fun getLayoutID(): Int {
        return R.layout.activity_regsiter
    }

    override fun makeViewModelProvider(): RegisterViewModel {
    return ViewModelProvider(this).get(RegisterViewModel::class.java)   }

    override fun openLoginActivity() {
        val intent = Intent (this,LoginActivity::class.java)
        startActivity(intent)
        finish()
    }

    override fun openUserActivity() {
        val intent = Intent(this,UserActivity::class.java)
        startActivity(intent)
        finish()
            }


}