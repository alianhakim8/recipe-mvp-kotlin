package com.alian.recipemvp.contracts

interface RecipeActivityContract {
    interface RecipeView {
        fun showLoading()

        fun hideLoading()

        fun toast(message : String)

        fun success()
    }

    interface RecipePresenter {
        fun create(token : String, title : String, content : String)

        fun update(token : String, id : String, title : String, content: String)

        fun delete(token : String, id : String)

        fun destroy()
    }
}