package com.example.onlineshopapplication.data.models.profile


import com.google.gson.annotations.SerializedName

data class ProfileResponse(
    @SerializedName("avatar")
    val avatar: String?, // https://shop.nouri-api.ir/avatar/478.jpg?174266351246
    @SerializedName("birth_date")
    val birthDate: String?, // 1374-03-12T20:34:16.000000Z
    @SerializedName("cellphone")
    val cellphone: String?, // 09398492959
    @SerializedName("email")
    val email: Any?, // null
    @SerializedName("firstname")
    val firstname: String?, // مرتضی
    @SerializedName("id")
    val id: Int?, // 478
    @SerializedName("idNumber")
    val idNumber: String?, // 48484545
    @SerializedName("job")
    val job: Any?, // null
    @SerializedName("lastname")
    val lastname: String?, // کلاته
    @SerializedName("register_date")
    val registerDate: String?, // 02 ، اسف ، 03
    @SerializedName("wallet")
    val wallet: String? // 100000
)