package com.manager.main

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.base.BaseActivity
import com.manager.repot_one.ReportOneActivity
import com.manager.repot_two.ReportTwoActivity
import com.user.R
import com.user.databinding.ActivityManagerBinding

class ManagerActivity : BaseActivity<ActivityManagerBinding,ManagerViewModel>(),Navigator {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewDataBinding.vmManager = viewModel
        viewModel.navigator = this
    }

    override fun getLayoutID(): Int {
        return R.layout.activity_manager
    }

    override fun makeViewModelProvider(): ManagerViewModel {
        return ViewModelProvider(this).get(ManagerViewModel::class.java)
    }

    override fun openReportOne() {
        val intent = Intent(this,ReportOneActivity::class.java)
        startActivity(intent)
    }

    override fun openReportTwo() {
        val intent = Intent(this,ReportTwoActivity::class.java)
        startActivity(intent)
    }
}