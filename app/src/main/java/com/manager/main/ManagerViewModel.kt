package com.manager.main

import com.base.BaseViewModel

class ManagerViewModel : BaseViewModel<Navigator>() {

    fun openReportOne(){
       navigator?.openReportOne()
    }
    fun openReportTwo(){
        navigator?.openReportTwo()
    }
    fun logOut(){
        navigator?.openMenu()
    }
}