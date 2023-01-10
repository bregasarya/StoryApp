package com.example.storyapp.ui.main

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.storyapp.R
import com.example.storyapp.adapter.LoadingStateAdapter
import com.example.storyapp.adapter.StoryAdapter
import com.example.storyapp.session.LoginPreferences
import com.example.storyapp.factory.ViewModelFactory
import com.example.storyapp.databinding.ActivityMainBinding
import com.example.storyapp.remote.respone.ListStoryItem
import com.example.storyapp.remote.respone.LoginResultModel
import com.example.storyapp.ui.add.AddStoryActivity
import com.example.storyapp.ui.detail.DetailStoryActivity
import com.example.storyapp.ui.login.LoginActivity
import com.example.storyapp.ui.maps.MapsActivity
import com.example.storyapp.util.Constant.Companion.EXTRA_ID

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var storyAdapter: StoryAdapter
    private lateinit var viewModelFactory: ViewModelFactory
    private lateinit var loginPreferences: LoginPreferences
    private lateinit var loginResultModel: LoginResultModel
    private val mainViewModel: MainViewModel by viewModels { viewModelFactory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        loginPreferences = LoginPreferences(binding.root.context)
        loginResultModel = loginPreferences.getUser()

        setViewModel()
        setRecycler(binding.root.context)
        getStories()
        onClick()
        toAddStory()
    }

    private fun setViewModel() {
        viewModelFactory = ViewModelFactory.getInstnce(binding.root.context)
    }

    private fun setRecycler(context: Context) {
        binding.rvMain.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            storyAdapter = StoryAdapter()
            adapter = storyAdapter
        }
    }


    override fun onBackPressed() {
        super.onBackPressed()
        finishAffinity()
    }

    private fun getStories() {
        loadingState(true)
        binding.rvMain.adapter = storyAdapter.withLoadStateFooter(
            footer = LoadingStateAdapter {
                storyAdapter.retry()
            }
        )

        mainViewModel.getAllStory.observe(this) {
            loadingState(false)
            storyAdapter.submitData(lifecycle, it)
        }
    }

    private fun loadingState(isLoading: Boolean) {
        if (isLoading) {
            binding.apply {
                rvMain.visibility = View.INVISIBLE
                progressBarHome.visibility = View.VISIBLE
            }
        } else {
            binding.apply {
                rvMain.visibility = View.VISIBLE
                progressBarHome.visibility = View.GONE
            }
        }
    }

    private fun onClick() {
        storyAdapter.setOnItemClickCallback(object : StoryAdapter.OnItemClickCallback {
            override fun onItemClicked(data: ListStoryItem) {
                startActivity(Intent(this@MainActivity, DetailStoryActivity::class.java).also {
                    it.putExtra(EXTRA_ID, data)
                })
            }
        })
    }

    private fun toAddStory() {
        binding.addStory.setOnClickListener {
            startActivity(Intent(this, AddStoryActivity::class.java))
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.option_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val builder = AlertDialog.Builder(this)
        builder.setTitle(getString(R.string.alert_title))
        builder.setMessage(getString(R.string.alert_message))
        builder.setNegativeButton(getString(R.string.alert_negativ)) { _, _ ->

        }
        builder.setPositiveButton(getString(R.string.alert_positive)) { _, _ ->
            startActivity(Intent(this@MainActivity, LoginActivity::class.java).also {
                loginPreferences.deleteUser()
                Toast.makeText(this, getString(R.string.logout_succes), Toast.LENGTH_SHORT)
                    .show()
            })
        }
        val alert = builder.create()
        when (item.itemId) {
            R.id.action_logout ->
                alert.show()
            R.id.maps ->
                startActivity(Intent(this, MapsActivity::class.java))
        }
        return true
    }
}