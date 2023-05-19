package tj.test.testsklad.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import tj.test.testsklad.databinding.ItemPriemkaBinding
import tj.test.testsklad.ui.models.PriemkaUi

class PriemkaAdapter(
    private val onViewClicked: (PriemkaUi) -> Unit,
) : ListAdapter<PriemkaUi, PriemkaAdapter.PriemkaViewHolder>(COMPARATOR) {

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): PriemkaViewHolder {
        return PriemkaViewHolder(
            binding = ItemPriemkaBinding.inflate(
                LayoutInflater.from(viewGroup.context),
                viewGroup,
                false
            ),
            onViewClicked = onViewClicked,
        )
    }

    override fun onBindViewHolder(holder: PriemkaViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class PriemkaViewHolder(
        private val binding: ItemPriemkaBinding,
        private val onViewClicked: (PriemkaUi) -> Unit,
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: PriemkaUi) = with(binding) {
            tvCode.text = item.numberCode
            root.setOnClickListener {
                onViewClicked(item)
            }
        }
    }

    companion object {
        private val COMPARATOR = object : DiffUtil.ItemCallback<PriemkaUi>() {
            override fun areItemsTheSame(
                oldItem: PriemkaUi,
                newItem: PriemkaUi
            ): Boolean =
                oldItem.numberCode == newItem.numberCode

            override fun areContentsTheSame(
                oldItem: PriemkaUi,
                newItem: PriemkaUi
            ): Boolean =
                oldItem == newItem
        }
    }
}
