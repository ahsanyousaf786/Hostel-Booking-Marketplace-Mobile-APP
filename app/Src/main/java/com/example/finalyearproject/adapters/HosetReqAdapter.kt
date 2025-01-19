package com.example.finalyearproject.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.finalyearproject.HomeScreen
import com.example.finalyearproject.databinding.DetailrecBinding
import com.example.finalyearproject.instances.RealtimeObj
import com.example.finalyearproject.modals.AddHostelModal
import com.example.finedelivery.utils.ConstantVariables
import com.example.finedelivery.utils.NewScreen
import com.example.finedelivery.utils.ToastObj


class HosetReqAdapter(
    var modallist:ArrayList<AddHostelModal>
) :
    RecyclerView.Adapter<HosetReqAdapter.ViewHolder>() {
    lateinit var click: PassData
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v =DetailrecBinding.inflate(LayoutInflater.from(parent.context), parent, false)
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
        holder.binding.name.text=modal.name
        holder.binding.desc.text=modal.desc
        holder.binding.address.text=modal.address
        holder.binding.baths.text=modal.baths
        holder.binding.beds.text=modal.beds
        holder.binding.hostelFor.text=modal.hotselfor
        Glide.with(holder.itemView.context).load(modal.image).into(holder.binding.image)
        holder.binding.book.setText("Accept Hostel Owner Request")
        holder.binding.book.setOnClickListener {
           click.delete(modal)
            val myRef = RealtimeObj.database.getReference(ConstantVariables.ACCEPTED_HOSTEL)
            val instanceDb = myRef.push()
            val pushKey=instanceDb.key
            instanceDb.setValue(
                AddHostelModal(modal.name,modal.email.toString(),modal.contact,modal.city,modal.province,modal.beds,modal.baths,
                    modal.sqft,modal.address,modal.desc,modal.hotselfor,modal.image,"Owner",pushKey)
            ).addOnSuccessListener {
                ToastObj.l(holder.itemView.context,"Request Accepted")
            }.addOnFailureListener{
            }
        }
    }
    override fun getItemCount(): Int {
        return modallist.size
    }
    class ViewHolder(var binding:DetailrecBinding) :
        RecyclerView.ViewHolder(binding.root) {
    }
}