package com.alian.recipemvp.responses

import com.google.gson.annotations.SerializedName

data class WrappedResponse<T>(
    @SerializedName("status") var status : String,
    @SerializedName("message") var message : String,
    @SerializedName("data") var data : T
)


data class WrappedListResponse<T>(
    @SerializedName("message") var message: String,
    @SerializedName("status") var status : String,
    @SerializedName("data") var data : List<T>
)