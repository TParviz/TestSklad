package tj.test.testsklad.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import tj.test.testsklad.databinding.ItemGoodsBinding
import tj.test.testsklad.ui.models.PriemkaUi

class GoodsDetailsAdapter() :
    ListAdapter<PriemkaUi, GoodsDetailsAdapter.GoodsDetailsViewHolder>(COMPARATOR) {

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): GoodsDetailsViewHolder {
        return GoodsDetailsViewHolder(
            binding = ItemGoodsBinding.inflate(
                LayoutInflater.from(viewGroup.context),
                viewGroup,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: GoodsDetailsViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class GoodsDetailsViewHolder(
        private val binding: ItemGoodsBinding,
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: PriemkaUi) = with(binding) {
            tvCode.text = item.numberCode
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
