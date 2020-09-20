package com.alian.recipemvp.contracts

import android.content.Context

interface LoginActivityContract {
    interface LoginView {
        fun showToast(message : String)
        fun successLogin()
        fun showLoading()
        fun hideLoading()
    }

    interface LoginPresenter{
        fun login(email : String, password : String, context: Context)

        fun destroy()
    }
}