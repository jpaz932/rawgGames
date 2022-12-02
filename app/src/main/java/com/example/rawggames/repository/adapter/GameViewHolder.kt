package com.example.rawggames.repository.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.rawggames.databinding.ItemGameBinding
import com.example.rawggames.repository.model.GameDetail
import com.example.rawggames.repository.model.Result
import com.squareup.picasso.Picasso

class GameViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    private val binding = ItemGameBinding.bind(view)

    fun render(game: Result, onClickListener:(Int) -> Unit) {
        Picasso.get().load(game.background_image).into(binding.imgGame)
        binding.txtGameName.text = game.name
        binding.txtGameRelease.text = game.released
        binding.txtGameScore.text = game.score

        itemView.setOnClickListener {
            onClickListener(game.id)
        }
    }
}