package tj.test.testsklad.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import tj.test.testsklad.databinding.ItemPriemkaDetailsBinding
import tj.test.testsklad.ui.models.PriemkaDetailsUi

class PriemkaDetailsAdapter(
    private val onViewClicked: (PriemkaDetailsUi) -> Unit,
) : ListAdapter<PriemkaDetailsUi, PriemkaDetailsViewHolder>(COMPARATOR) {

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): PriemkaDetailsViewHolder {
        return PriemkaDetailsViewHolder(
            binding = ItemPriemkaDetailsBinding.inflate(
                LayoutInflater.from(viewGroup.context),
                viewGroup,
                false
            ),
            onViewClicked = onViewClicked,
        )
    }

    override fun onBindViewHolder(holder: PriemkaDetailsViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    companion object {
        private val COMPARATOR = object : DiffUtil.ItemCallback<PriemkaDetailsUi>() {
            override fun areItemsTheSame(
                oldItem: PriemkaDetailsUi,
                newItem: PriemkaDetailsUi
            ): Boolean =
                oldItem.code == newItem.code

            override fun areContentsTheSame(
                oldItem: PriemkaDetailsUi,
                newItem: PriemkaDetailsUi
            ): Boolean =
                oldItem == newItem
        }
    }
}