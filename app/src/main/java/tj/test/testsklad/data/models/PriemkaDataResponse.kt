package tj.test.testsklad.data.models

import com.google.gson.annotations.SerializedName
import tj.test.testsklad.ui.models.PriemkaUi

data class PriemkaDataResponse(
    @SerializedName("Nomber")
    val number: String
)

fun PriemkaDataResponse.toUi() : PriemkaUi {
    return PriemkaUi(numberCode = number)
}