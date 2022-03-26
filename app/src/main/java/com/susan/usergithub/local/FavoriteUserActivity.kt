package com.susan.usergithub.local

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.susan.usergithub.GitUserAdapter
import com.susan.usergithub.R
import com.susan.usergithub.databinding.ActivityFavoriteUserBinding
import com.susan.usergithub.detail.DetailUserActivity
import com.susan.usergithub.model.GitUser
import com.susan.usergithub.setting.SettingActivity

class FavoriteUserActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFavoriteUserBinding
    private lateinit var adapter: GitUserAdapter
    private lateinit var viewModel: FavoriteUserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = "Favorite User"
        actionBar?.setDisplayHomeAsUpEnabled(true)

        adapter = GitUserAdapter()
        adapter.notifyDataSetChanged()

        viewModel = ViewModelProvider(this).get(FavoriteUserViewModel::class.java)

        adapter.setOnItemClickCallback(object : GitUserAdapter.OnItemClickCallback {
            override fun onItemClicked(data: GitUser) {
                Intent(this@FavoriteUserActivity, DetailUserActivity::class.java).also {
                    it.putExtra(DetailUserActivity.USERNAME, data.login)
                    it.putExtra(DetailUserActivity.ID, data.id)
                    it.putExtra(DetailUserActivity.AVATAR, data.avatar_url)
                    startActivity(it)
                }
            }
        })

        binding.apply {
            rvFavorite.setHasFixedSize(true)
            rvFavorite.layoutManager = LinearLayoutManager(this@FavoriteUserActivity)
            rvFavorite.adapter = adapter
        }

        viewModel.getFavoriteUser()?.observe(this, {
            if (it != null) {
                val list = mapList(it)
                adapter.setList(list)
            }
        })
    }

    private fun mapList(favorites: List<FavoriteUser>): ArrayList<GitUser> {
        val listFavorites = ArrayList<GitUser>()
        for (favorite in favorites) {
            val favoriteMapped = GitUser(
                favorite.login,
                favorite.id,
                favorite.avatar_url
            )
            listFavorites.add(favoriteMapped)
        }
        return listFavorites
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.setting_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.switch_theme -> {
                Intent(this, SettingActivity::class.java).also {
                    startActivity(it)
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }
}