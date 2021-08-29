package com.studying.mvistudyingapp.ui.main

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.studying.mvistudyingapp.R
import com.studying.mvistudyingapp.databinding.ActivityMainBinding
import com.studying.mvistudyingapp.util.DataState

class MainActivity : AppCompatActivity(), DataStateListener {
    lateinit var viewModel: MainViewModel
    private lateinit var binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initViewModel()
        if (savedInstanceState == null)
            showFragment()
    }

    private fun initViewModel() {
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

    }

    private fun showFragment() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, MainFragment(), "MainFragment").commit()

    }

    override fun onDataStateChange(dataState: DataState<*>?) {
        handleDataStateChange(dataState)
    }

    private fun handleDataStateChange(dataState: DataState<*>?) {
        dataState?.let {
            //handle loading
            showProgressBar(it.loading)
            //handle message
            it.message?.let { event ->
                event.getContentIfNotHandled()?.let { message ->
                    showToast(message)
                }
            }
        }
    }

    fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    fun showProgressBar(isVisible: Boolean) {
        if (isVisible)
            binding.progressBar.visibility = View.VISIBLE
        else binding.progressBar.visibility = View.GONE

    }
}