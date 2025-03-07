package academy.nouri.storeapp.data.models.login

import com.google.gson.annotations.SerializedName
import javax.inject.Inject

data class BodyLogin(
    @SerializedName("login")
    var login: String? = null,
    @SerializedName("hash_code")
    var hashCode: String? = null,
    @SerializedName("code")
    var code: Int? = null
)