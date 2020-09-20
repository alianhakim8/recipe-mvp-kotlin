package com.alian.recipemvp

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.alian.recipemvp.contracts.RegisterActivityContract
import com.alian.recipemvp.presenters.RegisterActivityPresenter
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : AppCompatActivity(), RegisterActivityContract.RegisterView {
    private var presenter: RegisterActivityContract.RegisterPresenter? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        supportActionBar?.hide()
        presenter = RegisterActivityPresenter(this)
        doRegister()
    }

    private fun doRegister() {
        btn_register.setOnClickListener {
            val name = et_name.text.toString().trim()
            val email = et_email.text.toString().trim()
            val password = et_password.text.toString().trim()

            if (name.isNotEmpty() && email.isNotEmpty() && password.isNotEmpty()) {
                if (password.length > 8) {
                    if (name.length > 5) {
                        presenter?.register(name, email, password, this@RegisterActivity)
                    } else {
                        showToast("Name must be 5 character or more")
                    }
                } else {
                    showToast("Password must be 8 character or more")
                }
            } else {
                showToast("Please filled all forms")
            }
        }

        btn_back.setOnClickListener {
            finish()
        }
    }

    override fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun successRegister() {
        finish()
    }

    override fun showLoading() {
        btn_register.isEnabled = false
        btn_back.isEnabled = false
        loading.apply {
            isIndeterminate = true
        }
    }

    override fun hideLoading() {
        btn_register.isEnabled = true
        btn_back.isEnabled = true
        loading.apply {
            isIndeterminate = false
            progress = 0
        }
    }
}