package com.example.rawggames

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.rawggames.databinding.ActivityGamesBinding
import com.example.rawggames.repository.adapter.GameAdapter
import com.example.rawggames.repository.api.ApiService
import com.example.rawggames.repository.model.Result
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class GamesActivity : AppCompatActivity(), SearchView.OnQueryTextListener {

    private lateinit var binding: ActivityGamesBinding
    private lateinit var adapter: GameAdapter
    private var gameImages = mutableListOf<Result>()
    private var key = "?key=5ef1f3e1290b43c7af30638687b77ae8"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGamesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.searchGames.setOnQueryTextListener(this)
        initRecyclerView()

        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.title = "Games"
    }

    private fun initRecyclerView() {
        adapter = GameAdapter(gameImages) {
                idGame -> onItemSelected(idGame)
        }
        binding.recyclerGames.layoutManager = LinearLayoutManager(this)
        binding.recyclerGames.adapter = adapter
    }

    private fun onItemSelected(id: Int) {
        val intent = Intent(this, GameDetailActivity::class.java)
        intent.putExtra("idGame", id)
        startActivity(intent)
    }

    private fun getRetrofit():Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://api.rawg.io/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private fun searchGame(query:String) {
        CoroutineScope(Dispatchers.IO).launch {
            val call = getRetrofit().create(ApiService::class.java).getGamesBySearch("games$key&search=$query")
            val games = call.body()

            runOnUiThread {
                if(call.isSuccessful) {
                    val gamesResults = games?.results ?: emptyList()
                    gameImages.clear()
                    gameImages.addAll(gamesResults)
                    adapter.notifyDataSetChanged()
                } else {
                    showError()
                }
                hideKeyboard()
            }

        }
    }

    private fun hideKeyboard() {
        val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(binding.viewRoot.windowToken, 0)
    }

    private fun showError() {
        Toast.makeText(this, "Ha ocurrido un error", Toast.LENGTH_SHORT).show()
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        if(!query.isNullOrEmpty()) {
            searchGame(query.lowercase())
        }
        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        return true
    }

}