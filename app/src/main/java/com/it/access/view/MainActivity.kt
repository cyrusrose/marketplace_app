package com.it.access.view

import android.os.Bundle
import androidx.activity.viewModels
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.WindowCompat
import com.it.access.R
import com.it.access.databinding.ActivityMainBinding
import com.it.access.presentation.MyViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val ui by lazy {  ActivityMainBinding.inflate(layoutInflater) }
    private val vm by viewModels<MyViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        WindowCompat.setDecorFitsSystemWindows(window, false)
        super.onCreate(savedInstanceState)

        setContentView(ui.root)

        ui.fab.setOnClickListener { view ->

        }
    }


}