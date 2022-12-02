package com.example.rawggames

import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.rawggames.databinding.ActivityGameDetailBinding
import com.example.rawggames.repository.api.ApiService
import com.squareup.picasso.Picasso
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class GameDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityGameDetailBinding
    private var key = "?key=5ef1f3e1290b43c7af30638687b77ae8"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGameDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.title = "Game Detail"

        binding.txtDescription.movementMethod = ScrollingMovementMethod()

        var idGame: Int = intent.extras!!.get("idGame").toString().toInt()
        gameDetail(idGame)
    }

    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://api.rawg.io/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private fun gameDetail(idGame: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            val call = getRetrofit().create(ApiService::class.java).getGameDetail("games/$idGame$key")
            val game = call.body()

            runOnUiThread {
                if(call.isSuccessful) {
                    var platforms = StringBuilder()
                    var genres = StringBuilder()

                    Picasso.get().load(game?.background_image).into(binding.detailImg)
                    binding.txtDetailName.text = game?.name
                    binding.txtMetartic.text = game?.metacritic.toString()
                    binding.txtReleased.text = game?.released
                    binding.txtRating.text = game?.rating.toString()

                    for (item in game!!.parent_platforms) {
                        platforms.append(item.platform.name).append(", ")
                    }
                    binding.txtPlatforms.text = platforms

                    for (item in game.genres) {
                        genres.append(item.name).append(", ")
                    }
                    binding.txtGenres.text = genres
                    binding.txtDescription.text = game.description_raw

                } else {
                    showError()
                }
            }

        }
    }

    private fun showError() {
        Toast.makeText(this, "Ha ocurrido un error", Toast.LENGTH_SHORT).show()
    }

}