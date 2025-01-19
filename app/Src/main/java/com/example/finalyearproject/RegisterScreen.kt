package com.example.finalyearproject

import android.R
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.view.WindowManager
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.lifecycle.lifecycleScope
import com.example.finalyearproject.databinding.ActivityRegisterScreenBinding
import com.example.finalyearproject.instances.FirebaseAuthSingleton
import com.example.finalyearproject.instances.RealtimeObj
import com.example.fine_delivery.modals.RegisterUser
import com.example.finedelivery.utils.*
import kotlinx.coroutines.launch

class RegisterScreen : AppCompatActivity() {
    lateinit var binding: ActivityRegisterScreenBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityRegisterScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.loginButton.setOnClickListener {
            NewScreen.start(this,MainActivity::class.java)
        }
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        binding.submitButton.setOnClickListener {
            if (InternetConnection.isInternetAvailable(this)) {
                performBackgroundOperation()
            } else {
                ToastObj.s(this, "Network error, please try later")
            }
        }
        binding.backButton.setOnClickListener {
            onBackPressed()
        }
    }
    fun performBackgroundOperation() {
        val dialog = ProgressDialogSingleton.getInstance(this, "Creating account")
        val email = binding.emailEd.text.toString()
        val name = binding.nameEd.text.toString()
        val password = binding.passwordEd.text.toString()
        if (email.isEmpty()) {
            binding.emailEd.error = "Please enter email"
        } else if (email.length < 5) {
            binding.emailEd.error = "Must be more than 4 words"
        } else if (email.length > 27) {
            binding.emailEd.error = "No more than 27 words"
        } else if (!Patterns.EMAIL_ADDRESS.matcher(binding.emailEd.text.toString()).matches()) {

        } else if (password.isEmpty()) {
            binding.passwordEd.error = "Please enter password"
        } else if (password.length < 8) {
            binding.passwordEd.error = "Must be more than 8 words"
        } else {
            binding.emailEd.setText("")
            binding.passwordEd.setText("")
            binding.nameEd.setText("")
            dialog.show()
            lifecycleScope.launch {
              try {
                  val myRef =RealtimeObj.database.getReference(ConstantVariables.USER)
                  val instanceDb = myRef.push()
                  val pushKey=instanceDb.key
                  FirebaseAuthSingleton.authInstance.createUserWithEmailAndPassword(
                      email, password
                  ).addOnSuccessListener {
                      instanceDb.setValue(
                          RegisterUser(name, email, pushKey, password)
                      ).addOnSuccessListener {
                          val intent = Intent(this@RegisterScreen, BookHostel::class.java)
                          intent.putExtra("E", email)
                          startActivity(intent)
                          dialog.dismiss()
                      }.addOnFailureListener{
                          dialog.dismiss()
                      }
                  }.addOnFailureListener{
                      dialog.dismiss()
                  }
              }catch (e:java.lang.Exception){
              }
            }
        }
    }
}