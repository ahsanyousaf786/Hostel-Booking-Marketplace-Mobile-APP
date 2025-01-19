package com.example.finalyearproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.example.finalyearproject.databinding.ActivityHostelDetailBinding
import com.example.finalyearproject.instances.FirebaseAuthSingleton
import com.example.finalyearproject.modals.AddHostelModal
import com.example.finedelivery.utils.NewScreen

class HostelDetail : AppCompatActivity() {
    lateinit var binding: ActivityHostelDetailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityHostelDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val modal=intent.getSerializableExtra("M") as AddHostelModal
        Glide.with(this).load(modal.image).into(binding.image)
        binding.name.setText(modal.name)
        binding.desc.setText(modal.desc)
        binding.address.setText(modal.address)
        binding.beds.setText(modal.beds)
        binding.baths.setText(modal.baths)
        binding.hostelFor.setText(modal.hotselfor)
        binding.book.setOnClickListener {
            val intent = Intent(this@HostelDetail,BookHostel::class.java)
            intent.putExtra("hn",modal.name)
            intent.putExtra("ba",modal.baths)
            intent.putExtra("be",modal.beds)
            intent.putExtra("hf",modal.hotselfor)
                startActivity(intent)
        }

    }
}