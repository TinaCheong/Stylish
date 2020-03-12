package student.tina.stylish.`object`

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class User(
    var id : Int, //User's id
    var provider : String, //Service provider
    var name : String, //User's name
    var email : String, //User's email
    var picture : String //User's picture
): Parcelable