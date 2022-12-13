package com.example.rawggames

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.rawggames.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnGames.setOnClickListener {
            val intent = Intent(this@MainActivity, GamesActivity::class.java)
            startActivity(intent)
        }
        binding.btnPlatforms.setOnClickListener {
            val act = Intent(this@MainActivity, PlatFormsActivity::class.java)
            startActivity(act)
        }
    }




}