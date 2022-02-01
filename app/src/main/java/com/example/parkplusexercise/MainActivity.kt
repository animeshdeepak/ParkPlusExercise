package com.example.parkplusexercise

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.parkplusexercise.databinding.ActivityMainBinding
import com.example.parkplusexercise.model.Item
import com.example.parkplusexercise.network.SingleEvent

class MainActivity : AppCompatActivity() {
    private lateinit var viewModel: MainViewModel
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        observe(viewModel.apiData, ::onApiDataSuccess)
    }

    private fun onApiDataSuccess(event: SingleEvent<ArrayList<Item>>) {
        event.contentIfNotHandled?.let {
            showToastMessage(it[0].assigneesUrl ?: "no data")
        }
    }

    private fun showToastMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}