package com.user.main.ui

import com.base.BaseViewModel

class UserActivityViewModel: BaseViewModel<Navigator>() {


    fun addToCard(){
        navigator?.openCardPage()
    }
}