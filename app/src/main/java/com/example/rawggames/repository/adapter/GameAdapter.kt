package com.example.rawggames.repository.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.rawggames.R
import com.example.rawggames.repository.model.Result

class GameAdapter(private val games:List<Result>, private val onClickListener:(Int) -> Unit) : RecyclerView.Adapter<GameViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GameViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return GameViewHolder(layoutInflater.inflate(R.layout.item_game, parent, false ))
    }

    override fun onBindViewHolder(holder: GameViewHolder, position: Int) {
        val item = games[position]
        holder.render(item, onClickListener)
    }

    override fun getItemCount(): Int {
        return games.size
    }

}