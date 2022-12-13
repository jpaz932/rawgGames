package com.example.rawggames.repository.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.rawggames.databinding.ItemPlatformsBinding
import com.example.rawggames.repository.model.PlatForms
import com.example.rawggames.repository.model.ResultX
import com.squareup.picasso.Picasso

class PlatFormsViewHolder(View: View):RecyclerView.ViewHolder(View) {
    private val binding = ItemPlatformsBinding.bind(View)

    fun bin(image:ResultX, onClickListener:(Int) -> Unit){
        Picasso.get().load(image.image_background).into(binding.imgPlatForms)

    }
}