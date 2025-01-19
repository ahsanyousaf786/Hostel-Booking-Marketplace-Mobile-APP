package com.example.finalyearproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.finalyearproject.databinding.ActivityBookHostelBinding
import com.example.finalyearproject.instances.FirebaseAuthSingleton
import com.example.finalyearproject.instances.RealtimeObj
import com.example.finalyearproject.modals.AddHostelModal
import com.example.fine_delivery.modals.BookHostelModal
import com.example.finedelivery.utils.ConstantVariables
import com.example.finedelivery.utils.NewScreen
import com.example.finedelivery.utils.ToastObj

class BookHostel : AppCompatActivity() {
    lateinit var binding: ActivityBookHostelBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityBookHostelBinding.inflate(layoutInflater)
        setContentView(binding.root)
        if (FirebaseAuthSingleton.authInstance.currentUser!=null){
            val hn=intent.getStringExtra("hn")
            val ba=intent.getStringExtra("ba")
            val be=intent.getStringExtra("be")
            val hf=intent.getStringExtra("hf")
            val myRef = RealtimeObj.database.getReference(ConstantVariables.BOOK_HOSTEL)
            val instanceDb = myRef.push()
            val pushKey=instanceDb.key
            binding.submitButton.setOnClickListener {
                val email = binding.emailEd.text.toString()
                val name = binding.nameEd.text.toString()
                val contact = binding.contactNo.text.toString()
                val date=binding.bookingDate.text.toString()
                instanceDb.setValue(
                    BookHostelModal(name,email,contact,hn,ba,be,hf,date,pushKey)
                ).addOnSuccessListener {
                    ToastObj.l(this@BookHostel,"Request Sent")
                    NewScreen.start(this@BookHostel,HomeScreen::class.java)
                }.addOnFailureListener{
                }
            }
        }else{
            NewScreen.start(this,MainActivity::class.java)
        }

    }
}