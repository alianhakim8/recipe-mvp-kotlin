package com.alian.recipemvp.presenters

import com.alian.recipemvp.contracts.RecipeActivityContract
import com.alian.recipemvp.models.Post
import com.alian.recipemvp.responses.WrappedResponse
import com.alian.recipemvp.utilities.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RecipeActivityPresenter(view: RecipeActivityContract.RecipeView) :
    RecipeActivityContract.RecipePresenter {
    private var view: RecipeActivityContract.RecipeView? = view
    private var apiService = ApiClient.apiService()

    override fun create(token: String, title: String, content: String) {
        view?.showLoading()
        val request = apiService.create(token, title, content)
        request.enqueue(object : Callback<WrappedResponse<Post>> {
            override fun onResponse(
                call: Call<WrappedResponse<Post>>,
                response: Response<WrappedResponse<Post>>
            ) {
                if (response.isSuccessful) {
                    val body = response.body()
                    if (body != null && body.status == "1") {
                        view?.toast("success create recipe")
                        view?.success()

                    }
                } else {
                    view?.toast("Cannot create data")
                }
                view?.hideLoading()
            }

            override fun onFailure(call: Call<WrappedResponse<Post>>, t: Throwable) {
                view?.hideLoading()
                view?.toast("Cannot connect to network")
                println(t.message)
            }

        })
    }

    override fun update(token: String, id: String, title: String, content: String) {
        view?.showLoading()
        val request = apiService.update(token, id, title, content)
        request.enqueue(object : Callback<WrappedResponse<Post>> {
            override fun onResponse(
                call: Call<WrappedResponse<Post>>,
                response: Response<WrappedResponse<Post>>
            ) {
                if (response.isSuccessful) {
                    val body = response.body()
                    if (body != null && body.status == "1") {
                        view?.toast("Successfully update")
                        view?.success()
                    } else {
                        view?.toast("Failed to update data")
                    }
                } else {
                    view?.toast("Something went wrong , try again later")
                }
                view?.hideLoading()
            }

            override fun onFailure(call: Call<WrappedResponse<Post>>, t: Throwable) {
                view?.hideLoading()
                view?.toast("Cannot connect to server")
                println(t.message)
            }

        })
    }

    override fun delete(token: String, id: String) {
        view?.showLoading()
        val request = apiService.delete(token, id)
        request.enqueue(object : Callback<WrappedResponse<Post>> {
            override fun onResponse(
                call: Call<WrappedResponse<Post>>,
                response: Response<WrappedResponse<Post>>
            ) {
                if (response.isSuccessful) {
                    val body = response.body()
                    if (body != null && body.status == "1") {
                        view?.toast("Successfully deleted")
                        view?.success()

                    } else {
                        view?.toast("Failed to deleted data")
                    }
                    view?.hideLoading()
                }
            }

            override fun onFailure(call: Call<WrappedResponse<Post>>, t: Throwable) {
                view?.toast("Cannot connect to server")
                view?.hideLoading()
                println(t.message)
            }


        })
    }

    override fun destroy() {
        view = null
    }
}