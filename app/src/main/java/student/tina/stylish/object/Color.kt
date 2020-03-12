package student.tina.stylish.`object`

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Color(
    var name : String, //Color's name
    var code : String //Color's hex code
): Parcelable