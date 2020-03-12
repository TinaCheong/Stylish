package student.tina.stylish.`object`

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class UserProfile(
    var data : User?,
    var error : String?
): Parcelable
