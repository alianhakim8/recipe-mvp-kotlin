package com.alian.recipemvp.contracts

import android.content.Context


interface RegisterActivityContract {

    interface RegisterView {
        fun showToast(message: String)
        fun successRegister()
        fun showLoading()
        fun hideLoading()
    }

    interface RegisterPresenter {
        fun register(name: String, email: String, password: String, context: Context)

        fun destroy()
    }
}


