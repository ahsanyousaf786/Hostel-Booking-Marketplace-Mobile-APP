package com.example.finalyearproject

import android.R
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.view.WindowManager
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.lifecycle.lifecycleScope
import com.example.finalyearproject.databinding.ActivityAddHostelBinding
import com.example.finalyearproject.instances.FirebaseAuthSingleton
import com.example.finalyearproject.instances.RealtimeObj
import com.example.finalyearproject.modals.AddHostelModal
import com.example.finedelivery.utils.*
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.launch

class AddHostel : AppCompatActivity() {
    lateinit var binding: ActivityAddHostelBinding
    private val pickImage = 1122
    private var imageUri: Uri? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityAddHostelBinding.inflate(layoutInflater)
        setContentView(binding.root)
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);


        var arraySpinner = arrayOf("Boys","Girls","Both")
        val adapter = ArrayAdapter(this, R.layout.simple_spinner_item, arraySpinner)
        binding.hostelfor.adapter = adapter

        binding.addimage.setOnClickListener {
            val gallery = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
            startActivityForResult(gallery, pickImage)
        }
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
        val dialog = ProgressDialogSingleton.getInstance(this, "Sending Request")
        val name = binding.nameEd.text.toString()
        val email = binding.emailEd.text.toString()
        val contact = binding.contactNo.text.toString()
        val city = binding.cityEd.text.toString()
        val province = binding.provinceEd.text.toString()
        val beds = binding.bedsEd.text.toString()
        val baths = binding.bathEd.text.toString()
        val sqft = binding.sqEd.text.toString()
        val address = binding.addressEd.text.toString()
        val desc = binding.descEd.text.toString()
        val password = binding.passwordEd.text.toString()
        if (imageUri!=null){
            val spinnerItem=binding.hostelfor.selectedItem.toString()
            lifecycleScope.launch {
                val storageReference = FirebaseStorage.getInstance().reference
                val fileUri: Uri = imageUri!!
                val imageRef = storageReference.child("images/${fileUri.lastPathSegment}")
                try {
                    val myRef = RealtimeObj.database.getReference(ConstantVariables.HOSTEL)
                    val instanceDb = myRef.push()
                    val pushKey=instanceDb.key
                    FirebaseAuthSingleton.authInstance.createUserWithEmailAndPassword(
                        email, password
                    ).addOnSuccessListener {
                        dialog.show()
                        val uploadTask = imageRef.putFile(fileUri)
                        uploadTask.continueWithTask { task ->
                            if (!task.isSuccessful) {
                                task.exception?.let {
                                    throw it
                                }
                            }
                            // Continue with the task to get the download URL
                            imageRef.downloadUrl
                        }.addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                val downloadUrl = task.result
                                // Save the download URL to the Realtime Database
                                instanceDb.setValue(
                                    AddHostelModal(name,email,contact,city,province,beds,baths,
                                        sqft,address,desc,spinnerItem,downloadUrl.toString(),"Owner",pushKey)
                                ).addOnSuccessListener {
                                    binding.emailEd.setText("")
                                    binding.passwordEd.setText("")
                                    binding.nameEd.setText("")
                                    ToastObj.l(this@AddHostel,"Request Sent")
                                    NewScreen.start(this@AddHostel,HomeScreen::class.java)
                                    dialog.dismiss()
                                }.addOnFailureListener{
                                    dialog.dismiss()
                                }
                            } else {
                                // Handle the error
                            }
                        }
                    }.addOnFailureListener{
                        dialog.dismiss()
                    }
                }catch (e:java.lang.Exception){
                }
            }
        }else{
            ToastObj.s(this,"Please select image")
        }

    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK && requestCode == pickImage) {
            imageUri = data?.data
        }
    }
}