package com.alian.recipemvp.presenters

import android.content.Context
import com.alian.recipemvp.contracts.RegisterActivityContract
import com.alian.recipemvp.models.User
import com.alian.recipemvp.responses.WrappedResponse
import com.alian.recipemvp.utilities.ApiClient
import com.alian.recipemvp.utilities.Constants
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterActivityPresenter(view: RegisterActivityContract.RegisterView) :
    RegisterActivityContract.RegisterPresenter {

    private var view: RegisterActivityContract.RegisterView? = view
    private var apiService = ApiClient.apiService()
    override fun register(name: String, email: String, password: String, context: Context) {
        view?.showLoading()
        val request = apiService.register(name, email, password)
        request.enqueue(object : Callback<WrappedResponse<User>> {
            override fun onResponse(
                call: Call<WrappedResponse<User>>,
                response: Response<WrappedResponse<User>>
            ) {
                if (response.isSuccessful) {
                    val body = response.body()
                    if (body != null && body.status == "1") {
                        Constants.setToken(context, body.data.api_token!!)
                        view?.showToast("Register Success")
                        view?.successRegister()
                    } else {
                        view?.showToast("Failed to login,email might be already used")
                    }
                }
                view?.hideLoading()
            }

            override fun onFailure(call: Call<WrappedResponse<User>>, t: Throwable) {
                view?.showToast("Cannot connect to server")
                view?.hideLoading()
            }

        })
    }

    override fun destroy() {
        view = null
    }
}