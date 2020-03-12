package student.tina.stylish.`object`

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize

@Parcelize
data class UserSignIn(
    @Json(name = "access_token") var accessToken : String,
    @Json(name = "access_expired")var accessExpired : Int,
    var user : User
): Parcelable