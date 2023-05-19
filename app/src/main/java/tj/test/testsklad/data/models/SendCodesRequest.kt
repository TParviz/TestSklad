package tj.test.testsklad.data.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class SendCodesRequest(

    @SerializedName("priemka")
    val priemka : String,

    @SerializedName("code")
    val code : String,

    @SerializedName("seria")
    val seria : String,

    @SerializedName("barCodes")
    val barCodes : List<String>,

    @SerializedName("user")
    val username: String

): Parcelable