package tj.test.testsklad.ui.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class PriemkaDetailsUi(
    val nomenclature: String,
    val code: String,
    val series: String,
    val qntPlan: Int,
    val qntFact: Int
): Parcelable