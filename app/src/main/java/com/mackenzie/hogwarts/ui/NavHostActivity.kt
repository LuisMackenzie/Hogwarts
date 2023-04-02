package com.mackenzie.hogwarts.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.mackenzie.hogwarts.databinding.ActivityNavHostBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NavHostActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityNavHostBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}