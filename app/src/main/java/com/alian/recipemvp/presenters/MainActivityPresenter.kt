package com.alian.recipemvp.presenters

import com.alian.recipemvp.contracts.MainActivityContract
import com.alian.recipemvp.models.Post
import com.alian.recipemvp.responses.WrappedListResponse
import com.alian.recipemvp.utilities.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivityPresenter(private var view: MainActivityContract.MainActivityView?) :
    MainActivityContract.MainActivityPresenter {
    private var apiService = ApiClient.apiService()

    override fun all(token: String) {
        val request = apiService.all(token)
        request.enqueue(object : Callback<WrappedListResponse<Post>> {
            override fun onResponse(
                call: Call<WrappedListResponse<Post>>,
                response: Response<WrappedListResponse<Post>>
            ) {
                if (response.isSuccessful) {
                    val body = response.body()
                    if (body != null && body.status == "1") {
                        view?.attachToRecycler(body.data)
                    }
                } else {
                    view?.showToast("something went wrong, try again later")
                }
            }

            override fun onFailure(call: Call<WrappedListResponse<Post>>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })
    }

    override fun destroy() {
        view = null
    }
}