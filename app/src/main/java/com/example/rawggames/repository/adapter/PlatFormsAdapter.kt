package com.example.rawggames.repository.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.rawggames.R
import com.example.rawggames.repository.model.ResultX

class PlatFormsAdapter(private val images:List<ResultX>, private val onClickListener:(Int) -> Unit) :RecyclerView.Adapter<PlatFormsViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlatFormsViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return PlatFormsViewHolder(layoutInflater.inflate(R.layout.item_platforms,parent,false))
    }

    override fun onBindViewHolder(holder: PlatFormsViewHolder, position: Int) {
        val item = images[position]
        holder.bin(item,onClickListener)
    }

    override fun getItemCount(): Int {
        return images.size
    }
}