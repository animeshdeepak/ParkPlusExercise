package com.example.parkplusexercise.main

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.parkplusexercise.R
import com.example.parkplusexercise.base.SingleEvent
import com.example.parkplusexercise.base.gone
import com.example.parkplusexercise.base.observe
import com.example.parkplusexercise.base.show
import com.example.parkplusexercise.databinding.ActivityMainBinding
import com.example.parkplusexercise.model.Item
import com.example.parkplusexercise.network.ApiStatus


class MainActivity : AppCompatActivity() {
    private lateinit var viewModel: MainViewModel
    private lateinit var binding: ActivityMainBinding
    private lateinit var repoItemAdapter: RepoItemAdapter
    private var pageCount: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        setContentView(binding.root)
        setUpProgressView()

        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        observe(viewModel.apiData, ::onApiDataSuccess)
        observe(viewModel.isLoading, ::loading)
        setUpRecyclerView()
    }

    private fun onApiDataSuccess(event: SingleEvent<ArrayList<Item>>) {
        event.contentIfNotHandled?.let {
            if (it.size > 0) {
                repoItemAdapter.addItem(it)
//                pageCount++
            }
        }
    }

    private fun showToastMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun setUpRecyclerView() {
        repoItemAdapter = RepoItemAdapter(arrayListOf())
        val layoutManager = LinearLayoutManager(this)
        val dividerItemDecoration = DividerItemDecoration(
            this,
            layoutManager.orientation
        )
        binding.itemRv.apply {
            setLayoutManager(layoutManager)
            addItemDecoration(dividerItemDecoration)
            adapter = repoItemAdapter
            addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    viewModel.getApiData(page = pageCount, perPage = 10)
                }
            })
        }
        repoItemAdapter.notifyItemInserted(0)
    }

    private fun setUpProgressView() {
        binding.progress.rlProgress.gone()
    }

    private fun loading(event: SingleEvent<ApiStatus>) {
        event.contentIfNotHandled?.let {
            when (it) {
                ApiStatus.LOADING -> progressBarVisibility(true)
                ApiStatus.SUCCESS -> progressBarVisibility(false)
                ApiStatus.FAILURE -> progressBarVisibility(false)
            }
        }
    }

    private fun progressBarVisibility(flag: Boolean) {
        if (flag) binding.progress.rlProgress.show() else binding.progress.rlProgress.gone()
    }
}