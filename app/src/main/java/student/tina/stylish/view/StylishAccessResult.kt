package student.tina.stylish.view

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import student.tina.stylish.`object`.UserSignIn
import student.tina.stylish.`object`.User

@Parcelize
data class StylishAccessResult(
    val data: UserSignIn
) : Parcelable