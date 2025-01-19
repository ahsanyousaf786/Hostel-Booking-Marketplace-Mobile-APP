package com.example.finalyearproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.view.WindowManager
import androidx.lifecycle.lifecycleScope
import com.example.finalyearproject.databinding.ActivityMainBinding
import com.example.finalyearproject.instances.FirebaseAuthSingleton
import com.example.finalyearproject.instances.RealtimeObj
import com.example.fine_delivery.modals.LoginUser
import com.example.fine_delivery.modals.RegisterUser
import com.example.finedelivery.utils.*
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN)
        binding.loginButton.setOnClickListener {
            NewScreen.start(this,RegisterScreen::class.java)
        }
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        binding.submitButton.setOnClickListener {
            if (InternetConnection.isInternetAvailable(this)) {
                performBackgroundOperation()
            } else {
                ToastObj.s(this, "Network error, please try later")
            }
        }

    }
    fun performBackgroundOperation() {
        val dialog = ProgressDialogSingleton.getInstance(this, "Logging User")
        val email = binding.emailEd.text.toString()
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
            dialog.show()
            lifecycleScope.launch {
                try {
                    FirebaseAuthSingleton.authInstance.signInWithEmailAndPassword(
                        email, password
                    ).addOnSuccessListener {
                        NewScreen.start(this@MainActivity,BookHostel::class.java)
                        dialog.dismiss()
                    }.addOnFailureListener{
                        dialog.dismiss()
                    }
                }catch (e:java.lang.Exception){
                }
            }
        }
    }
}