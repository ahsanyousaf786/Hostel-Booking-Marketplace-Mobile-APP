package com.example.finalyearproject

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.Gravity
import android.view.View
import android.view.WindowManager
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.EditText
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.recyclerview.widget.RecyclerView
import com.example.finalyearproject.adapters.HomeRecAdapter
import com.example.finalyearproject.databinding.ActivityHomeScreenBinding
import com.example.finalyearproject.instances.FirebaseAuthSingleton
import com.example.finalyearproject.instances.RealtimeObj
import com.example.finalyearproject.modals.AddHostelModal
import com.example.fine_delivery.modals.HomeRecModal
import com.example.finedelivery.utils.ConstantVariables
import com.example.finedelivery.utils.NewScreen
import com.example.finedelivery.utils.ToastObj
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
class HomeScreen : AppCompatActivity() {
    // laten... use for late intia....

    lateinit var toggle: ActionBarDrawerToggle
    // lazy for instantly asign value to variable which return object
    private val mAdapter by lazy { HomeRecAdapter(modalList) }
    private val modalList:ArrayList<AddHostelModal> by lazy { ArrayList() }
    lateinit var binding: ActivityHomeScreenBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityHomeScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);


        binding.addHostel.setOnClickListener {
            NewScreen.start(this,AddHostel::class.java)
        }
        binding.navHeader.hostelowner.setOnClickListener {
            NewScreen.start(this,HostelOwner::class.java)
        }
        binding.navHeader.logout.setOnClickListener {
            FirebaseAuthSingleton.authInstance.signOut()
            ToastObj.l(this,"Account has been logout")
        }
        binding.navHeader.admin.setOnClickListener {
            NewScreen.start(this,Admin::class.java)
        }

        getData()
        navigationDrawer()
        searchView()
    }



    private fun navigationDrawer() {
        binding.drawerBtn.setOnClickListener {
            binding.drawer.openDrawer(Gravity.LEFT)
        }
        getSupportActionBar()?.setDisplayShowHomeEnabled(true);
        getSupportActionBar()?.setDisplayHomeAsUpEnabled(true);
        //toggle  is usedc for drawer
        toggle= ActionBarDrawerToggle(this,binding.drawer,R.string.open,R.string.close)
        binding.drawer.addDrawerListener(toggle)
        toggle.syncState()
        binding.navHeader.close.setOnClickListener {
            binding.drawer.close()
        }
    }
    fun getData() {
        val myRef =RealtimeObj.database.getReference(ConstantVariables.ACCEPTED_HOSTEL)
        myRef.addValueEventListener(object :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                modalList.clear()
                for (childSnap in snapshot.children) {
                    val data = childSnap.getValue(AddHostelModal::class.java)
                    if (data!=null){
                        modalList.add(data!!)
                        mAdapter.notifyDataSetChanged()
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {

            }

        })
        binding.recylerView.adapter=mAdapter
        mAdapter.notifyDataSetChanged()
        mAdapter.recylerClick(object : HomeRecAdapter.PassData{
            override fun clickFunction(position: Int, modal: AddHostelModal) {
                val intent = Intent(this@HomeScreen,HostelDetail::class.java)
                intent.putExtra("M",modal)
                startActivity(intent)
            }
            override fun delete(modal: AddHostelModal) {
            }
        })
    }
    private fun searchView() {
        binding.searchView.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable?) {
                filter(s.toString())
            }

        })
    }
    private fun filter(text: String) {
        val filteredList = ArrayList<AddHostelModal>()

        for (item in modalList) {
            if (item.name!!.toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(item)
                mAdapter.notifyDataSetChanged()
            }
        }

        mAdapter.filterList(filteredList)
        mAdapter.notifyDataSetChanged()
    }
}