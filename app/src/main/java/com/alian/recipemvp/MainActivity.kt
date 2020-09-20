package com.alian.recipemvp

import android.content.Intent
import android.os.Bundle
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.alian.recipemvp.adapters.PostAdapter
import com.alian.recipemvp.contracts.MainActivityContract
import com.alian.recipemvp.models.Post
import com.alian.recipemvp.presenters.MainActivityPresenter
import com.alian.recipemvp.utilities.Constants
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*

class MainActivity : AppCompatActivity(), MainActivityContract.MainActivityView {
    private var presenter: MainActivityContract.MainActivityPresenter? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.toolbar))
        checkIsLogin()
        fab.setOnClickListener {
            startActivity(Intent(this@MainActivity, RecipeActivity::class.java).apply {
                putExtra("IS_NEW", true)
            })
        }

        presenter = MainActivityPresenter(this@MainActivity)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_logout -> {
                val b = AlertDialog.Builder(this@MainActivity)
                b.apply {
                    setMessage("Are you sure want to log out?")
                    setPositiveButton("LOGOUT") { _, _ ->
                        Constants.clearToken(this@MainActivity)
                        checkIsLogin()
                    }
                    setNegativeButton("CANCEL") { dialogInterface, _ -> dialogInterface.cancel() }
                }
                val alert = b.create()
                alert.show()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }


    override fun showToast(message: String) {
        Toast.makeText(this@MainActivity, message, Toast.LENGTH_SHORT).show()
    }

    override fun attachToRecycler(posts: List<Post>) {
        rv_post.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = PostAdapter(posts, this@MainActivity)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter?.destroy()
    }

    private fun checkIsLogin() {
        val token = Constants.getToken(this@MainActivity)
        if (token == "UNDEFINED") {
            startActivity(Intent(this@MainActivity, LoginActivity::class.java).also {
                finish()
            })
        }
    }

    override fun onResume() {
        super.onResume()
        getPosts()
    }

    private fun getPosts() {
        presenter?.all("Bearer ${Constants.getToken(this@MainActivity)}")
    }
}

