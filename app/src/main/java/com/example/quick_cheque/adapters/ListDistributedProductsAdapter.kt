package com.example.quick_cheque.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.quick_cheque.R
import com.example.quick_cheque.databinding.CardDistributedChequeBinding
import com.example.quick_cheque.model.DistributedChequeUserItem
import com.example.quick_cheque.model.Product

class ListDistributedProductsAdapter :
    ListAdapter<DistributedChequeUserItem, ListDistributedProductsAdapter.DistributedProductsViewHolder>(
        ListDistributedProductsDiffCallback()
    ) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): DistributedProductsViewHolder {
        return DistributedProductsViewHolder(
            CardDistributedChequeBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: DistributedProductsViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ListDistributedProductsDiffCallback : DiffUtil.ItemCallback<DistributedChequeUserItem>() {
        override fun areItemsTheSame(
            oldItem: DistributedChequeUserItem,
            newItem: DistributedChequeUserItem
        ): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(
            oldItem: DistributedChequeUserItem,
            newItem: DistributedChequeUserItem
        ): Boolean {
            return oldItem == newItem
        }
    }

    inner class DistributedProductsViewHolder(
        private val binding: CardDistributedChequeBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        private lateinit var innerListDistributedProductAdapter: InnerListDistributedProductAdapter
        private var countProductsUser: Int = 0
        private var isExpanded: Boolean = false

        fun bind(distributedChequeUserItem: DistributedChequeUserItem) = with(binding) {
            iconUserInDistributedCheque.setImageResource(distributedChequeUserItem.user.icon)
            nameUserInDistributedCheque.text = distributedChequeUserItem.user.name
            countProductsUser = distributedChequeUserItem.listProductsUser.size

            setupDistributedProductList(distributedChequeUserItem.listProductsUser)
            changingStyleExpandableDistributionChequeItem()

            expandableButtonDistributedCheque.setOnClickListener {
                isExpanded = !isExpanded
                changingStyleExpandableDistributionChequeItem()
            }
        }

        private fun changingStyleExpandableDistributionChequeItem() = with(binding) {
            previewCardDistributedCheque.setBackgroundResource(
                if (isExpanded  && countProductsUser != 0) {
                    R.drawable.style_only_top_corner_radius_cheque
                } else {
                    R.drawable.style_left_vertical_corner
                }
            )
            expandableButtonDistributedCheque.rotation = if (isExpanded) -90f else 90f
            fullInformationOfUserProducts.visibility =
                if (isExpanded && countProductsUser != 0) View.VISIBLE else View.GONE
        }

        private fun setupDistributedProductList(listProducts: MutableList<Product>) =
            with(binding) {
                innerListDistributedProductAdapter = InnerListDistributedProductAdapter()
                listProductsByUser.adapter = innerListDistributedProductAdapter
                listProductsByUser.layoutManager = LinearLayoutManager(itemView.context)
                innerListDistributedProductAdapter.submitList(listProducts)
            }
    }
}
