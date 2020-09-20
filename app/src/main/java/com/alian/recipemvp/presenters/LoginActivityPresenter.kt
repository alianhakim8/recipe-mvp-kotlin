package com.alian.recipemvp.presenters

import android.content.Context
import com.alian.recipemvp.contracts.LoginActivityContract
import com.alian.recipemvp.models.User
import com.alian.recipemvp.responses.WrappedResponse
import com.alian.recipemvp.utilities.ApiClient
import com.alian.recipemvp.utilities.Constants
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivityPresenter(v: LoginActivityContract.LoginView) :
    LoginActivityContract.LoginPresenter {
    private var view: LoginActivityContract.LoginView? = v
    private var apiService = ApiClient.apiService()
    override fun login(email: String, password: String, context: Context) {
        val request = apiService.login(email, password)
        view?.showLoading()
        request.enqueue(object : Callback<WrappedResponse<User>> {
            override fun onResponse(
                call: Call<WrappedResponse<User>>,
                response: Response<WrappedResponse<User>>
            ) {
                if (response.isSuccessful) {
                    val body = response.body()
                    if (body != null && body.status == "1") {
                        Constants.setToken(context, body.data.api_token!!)
                        view?.showToast("Welcome Back ${body.data.name}")
                        view?.successLogin()

                    } else {
                        view?.showToast("Failed,check your email or password")
                    }
                } else {
                    view?.showToast("Something went wrong ,try again later")
                }
                view?.hideLoading()
            }

            override fun onFailure(call: Call<WrappedResponse<User>>, t: Throwable) {
                view?.hideLoading()
                view?.showToast("Cannot connect to server")
                println(t.message)
            }

        })
    }

    override fun destroy() {
        view = null
    }
}