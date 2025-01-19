package com.example.finalyearproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.view.WindowManager
import com.example.finalyearproject.adapters.HomeRecAdapter
import com.example.finalyearproject.adapters.HosetReqAdapter
import com.example.finalyearproject.adapters.HostelOwnerAdapter
import com.example.finalyearproject.databinding.ActivityHostelOwnerBinding
import com.example.finalyearproject.instances.FirebaseAuthSingleton
import com.example.finalyearproject.instances.RealtimeObj
import com.example.finalyearproject.modals.AddHostelModal
import com.example.fine_delivery.modals.BookHostelModal
import com.example.finedelivery.utils.ConstantVariables
import com.example.finedelivery.utils.NewScreen
import com.example.finedelivery.utils.ProgressDialogSingleton
import com.example.finedelivery.utils.ToastObj
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener

class HostelOwner : AppCompatActivity() {
    private val mAdapter by lazy { HostelOwnerAdapter(modalList) }
    private val modalList:ArrayList<BookHostelModal> by lazy { ArrayList() }
    lateinit var binding: ActivityHostelOwnerBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityHostelOwnerBinding.inflate(layoutInflater)
        setContentView(binding.root)
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        binding.backButton.setOnClickListener {
            onBackPressed()
        }
        binding.submitButton.setOnClickListener {
            loginAdmin()
        }
        getDat()
    }
    fun getDat() {
        val myRef = RealtimeObj.database.getReference(ConstantVariables.BOOK_HOSTEL)
        myRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                modalList.clear()
                for (childSnap in snapshot.children) {
                    val data = childSnap.getValue(BookHostelModal::class.java)
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
        mAdapter.recylerClick(object : HostelOwnerAdapter.PassData{
            override fun clickFunction(position: Int, modal: BookHostelModal) {

            }

            override fun delete(modal: BookHostelModal) {

            }

        })
    }
    private fun loginAdmin() {
        val d= ProgressDialogSingleton.getInstance(this,"Logging User")
        val email=binding.emailEd.text.toString()
        val password=binding.passwordEd.text.toString()
        if (email.isEmpty()){
            binding.emailEd.error="Please enter email"
        }else if (email.length<5){
            binding.emailEd.error="Must be more than 4 words"
        }else if (email.length>27){
            binding.emailEd.error="No more than 27 words"
        }else if (!Patterns.EMAIL_ADDRESS.matcher(binding.emailEd.text.toString()).matches()){
            binding.emailEd.error="invalid email"
        }else if (password.isEmpty()){
            binding.passwordEd.error="Please enter password"
        }else if (password.length<5){
            binding.passwordEd.error="Must be more than 5 words"
        }else{
            d.show()
            FirebaseAuthSingleton.authInstance.signInWithEmailAndPassword(email, password)
                .addOnSuccessListener {
                    binding.emailEd.setText("")
                    binding.passwordEd.setText("")
                    d.dismiss()
                        binding.recL.visibility= View.VISIBLE
                        binding.loginCard.visibility= View.GONE
                }.addOnFailureListener {
                    d.dismiss()
                    ToastObj.s(this,it.message.toString())
                }
        }

    }


}