package com.example.finalyearproject.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.finalyearproject.AddHostel
import com.example.finalyearproject.R
import com.example.finalyearproject.databinding.HomerecBinding
import com.example.finalyearproject.databinding.HostelownerrecBinding
import com.example.finalyearproject.modals.AddHostelModal
import com.example.fine_delivery.modals.BookHostelModal
import com.example.fine_delivery.modals.HomeRecModal


class HostelOwnerAdapter(
    var modallist:ArrayList<BookHostelModal>
) :
    RecyclerView.Adapter<HostelOwnerAdapter.ViewHolder>() {
    lateinit var click: PassData
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v =HostelownerrecBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(v)
    }
    interface PassData {
        fun clickFunction(position:Int,modal: BookHostelModal)
        fun delete(modal: BookHostelModal)
    }
    fun recylerClick(listener: PassData){
        click=listener
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var modal=modallist[position]
        holder.binding.name.text=modal.name
        holder.binding.email.text=modal.email
        holder.binding.contactNo.text=modal.contact
        holder.binding.baths.text=modal.baths
        holder.binding.beds.text=modal.beds
        holder.binding.hostelFor.text=modal.type
        holder.binding.date.text=modal.date
    }
    fun filterList(filteredList: ArrayList<BookHostelModal>) {
        modallist = filteredList
        notifyDataSetChanged()
    }
    override fun getItemCount(): Int {
        return modallist.size
    }
    class ViewHolder(var binding: HostelownerrecBinding) :
        RecyclerView.ViewHolder(binding.root) {
    }
}