package tj.test.testsklad.ui.adapters

import androidx.recyclerview.widget.RecyclerView
import tj.test.testsklad.databinding.ItemPriemkaDetailsBinding
import tj.test.testsklad.ui.models.PriemkaDetailsUi

class PriemkaDetailsViewHolder(
    private val binding: ItemPriemkaDetailsBinding,
    private val onViewClicked: (PriemkaDetailsUi) -> Unit,
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(item: PriemkaDetailsUi) = with(binding) {
        tvName.text = item.nomenclature
        tvSeriy.text = "Серия: - ${item.series}"
        tvQntPlan.text = "План: - ${item.qntPlan}"
        tvQntFact.text = "Факт: - ${item.qntFact}"
        root.setOnClickListener {
            onViewClicked(item)
        }
    }
}