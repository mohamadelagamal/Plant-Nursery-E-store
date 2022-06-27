package com.user.main.ui.home.add_card.moving.address

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.admin.model.Teacher
import com.base.BaseActivity
import com.user.R
import com.user.databinding.ActivityAdressPaiementBinding
import com.user.main.ui.home.ConstantCollection
import com.user.main.ui.home.add_card.moving.DoneActivity
import com.user.main.ui.home.poduct.ProductActivityDetails


class AddressPaiementActivity : BaseActivity<ActivityAdressPaiementBinding,AddressViewModel>(),Navigator {
    lateinit var room:Teacher
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        room = intent.getParcelableExtra(ConstantCollection.AddressPaiementActivity)!!
        viewDataBinding.vmAddress=viewModel
        viewModel.navigator=this

    }

    override fun getLayoutID(): Int {
        return R.layout.activity_adress_paiement
    }

    override fun makeViewModelProvider(): AddressViewModel {
      return ViewModelProvider(this).get(AddressViewModel::class.java)
    }

    override fun gps() {
        val intent = Intent(Intent.ACTION_VIEW)
        intent.setData(Uri.parse("geo:47.4925,19.0513"))
        val chooser =Intent.createChooser(intent,"Lauch Maps")
        startActivity(chooser)
    }

    override fun nextPage() {
        room.name = viewModel.fullNameAddress.get()
        room.phone = viewModel.phone.get()
        room.address = viewModel.address.get()
        val intent = Intent(this@AddressPaiementActivity, DoneActivity::class.java)
        // send data
        intent.putExtra(ConstantCollection.DONE_ACTIVITY, room)
        startActivity(intent)
    }
}