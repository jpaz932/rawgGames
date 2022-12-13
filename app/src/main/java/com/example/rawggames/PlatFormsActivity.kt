package com.example.rawggames


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import android.widget.SearchView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.rawggames.databinding.ActivityPlatFormsBinding
import com.example.rawggames.repository.adapter.PlatFormsAdapter
import com.example.rawggames.repository.api.ApiService
import com.example.rawggames.repository.model.ResultX
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class PlatFormsActivity : AppCompatActivity(), SearchView.OnQueryTextListener,
    androidx.appcompat.widget.SearchView.OnQueryTextListener {
    private lateinit var binding: ActivityPlatFormsBinding
    private lateinit var adapter: PlatFormsAdapter
    private var platFormsImages = mutableListOf<ResultX>()
    private var key = "?key=5ef1f3e1290b43c7af30638687b77ae8"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPlatFormsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.searchPlatforms.setOnQueryTextListener(this)
        initRecyclerView()

        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.title = "PlatForms"
    }

    private fun initRecyclerView() {
        adapter= PlatFormsAdapter(platFormsImages){
            idPlatForms -> onItemSelected(idPlatForms)
        }
        binding.recyclerPlatForms.layoutManager = LinearLayoutManager(this)
        binding.recyclerPlatForms.adapter=adapter
    }

    private fun onItemSelected(idPlatFormd: Int) {
        val intent = Intent(this,PlatFormsDetailActivity::class.java)
        intent.putExtra("idPlatForms",idPlatFormd)
        startActivity(intent)
    }

    private fun getRetrofit():Retrofit{
        return Retrofit.Builder()
            .baseUrl("https://api.rawg.io/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    private fun searchPlatforms(query:String){
        CoroutineScope(Dispatchers.IO).launch {
            val call = getRetrofit().create(ApiService::class.java).getPlatFormsBySearch("platforms$key&search=$query")
            val platForms = call.body()

            runOnUiThread {
                if(call.isSuccessful) {
                    val platformsResult = platForms?.results ?: emptyList()
                    platFormsImages.clear()
                    platFormsImages.addAll(platformsResult)
                    adapter.notifyDataSetChanged()
                } else {
                    showError()
                }
                hideKeyboard()
            }
        }
    }
    private fun hideKeyboard(){
        val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(binding.viewRoot.windowToken, 0)
    }
    private fun showError(){
        Toast.makeText(this,"ha ocurrido un error", Toast.LENGTH_SHORT).show()
    }
    override fun onQueryTextSubmit(query: String?): Boolean {
        if(!query.isNullOrEmpty()) {
            searchPlatforms(query.lowercase())
        }
        return true
    }

    override fun onQueryTextChange(p0: String?): Boolean {
        return true
    }
}