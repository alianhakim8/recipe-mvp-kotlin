package com.alian.recipemvp.contracts

import com.alian.recipemvp.models.Post

interface MainActivityContract {
    interface MainActivityView {
        fun showToast(message: String)
        fun attachToRecycler(posts: List<Post>)
    }

    interface MainActivityPresenter {
        fun all(token: String)
        fun destroy()
    }
}