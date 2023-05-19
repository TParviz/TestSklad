package tj.test.testsklad.data.models

import com.google.gson.annotations.SerializedName
import tj.test.testsklad.ui.models.PriemkaDetailsUi


data class PriemkaDetailsResponse(

    @SerializedName("Nomenklatura")
    val nomenclature : String,

    @SerializedName("Code")
    val code: String,

    @SerializedName("Seriy")
    val series : String,

    @SerializedName("QntPlan")
    val qntPlan : Int,

    @SerializedName("QntFact")
    val qntFact : Int
)

fun PriemkaDetailsResponse.toUi() : PriemkaDetailsUi {
    return PriemkaDetailsUi( nomenclature, code, series, qntPlan, qntFact)
}