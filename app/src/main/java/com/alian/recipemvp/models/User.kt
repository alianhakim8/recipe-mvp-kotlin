package com.alian.recipemvp.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class User(
    @SerializedName("id") var id : Int?,
    @SerializedName("name") var name : String?,
    @SerializedName("api_token") var api_token : String?
) : Parcelable {
    constructor() : this(null, null, null)
}