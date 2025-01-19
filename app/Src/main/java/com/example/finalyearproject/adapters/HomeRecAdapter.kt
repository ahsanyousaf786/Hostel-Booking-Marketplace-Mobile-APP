package com.example.finalyearproject.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.finalyearproject.AddHostel
import com.example.finalyearproject.R
import com.example.finalyearproject.databinding.HomerecBinding
import com.example.finalyearproject.modals.AddHostelModal
import com.example.fine_delivery.modals.HomeRecModal


class HomeRecAdapter(
    var modallist:ArrayList<AddHostelModal>
) :
    RecyclerView.Adapter<HomeRecAdapter.ViewHolder>() {
    lateinit var click: PassData
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v =HomerecBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(v)
    }
    interface PassData {
        fun clickFunction(position:Int,modal: AddHostelModal)
        fun delete(modal: AddHostelModal)
    }
    fun recylerClick(listener: PassData){
        click=listener
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var modal=modallist[position]
        holder.binding.nameEd.text=modal.name
        Glide.with(holder.itemView.context).load(modal.image).into(holder.binding.hostelimage)
        holder.itemView.setOnClickListener {
            click.clickFunction(position,modal)
        }
    }
    fun filterList(filteredList: ArrayList<AddHostelModal>) {
        modallist = filteredList
        notifyDataSetChanged()
    }
    override fun getItemCount(): Int {
        return modallist.size
    }
    class ViewHolder(var binding: HomerecBinding) :
        RecyclerView.ViewHolder(binding.root) {
    }
}