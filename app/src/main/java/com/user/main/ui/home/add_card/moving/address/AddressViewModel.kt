package com.user.main.ui.home.add_card.moving.address
import androidx.databinding.ObservableField
import com.base.BaseViewModel
class AddressViewModel :BaseViewModel<Navigator>(){
    val fullNameAddress= ObservableField<String>()
    val fullNameAddressError= ObservableField<String>()
    val phone= ObservableField<String>()
    val phoneError= ObservableField<String>()
    val address= ObservableField<String>()
    val addressError= ObservableField<String>()

    fun openGPS(){
    navigator?.gps()
    }
    fun nextPage(){
        if(validation()){
          navigator?.nextPage()
        }
    }
    fun validation():Boolean{
        var valid = true
        if (fullNameAddress.get().isNullOrBlank()){
            fullNameAddressError.set("please enter your name")
            valid=false
        }else{
            fullNameAddressError.set(null)
        }
        if (address.get().isNullOrBlank()){
            addressError.set("please enter your address")
            valid=false
        }else{
            addressError.set(null)
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