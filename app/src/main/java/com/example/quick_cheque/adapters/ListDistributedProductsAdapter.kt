package com.example.quick_cheque.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
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

    class DistributedProductsViewHolder(
        private val binding: CardDistributedChequeBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        private lateinit var innerListDistributedProductAdapter: InnerListDistributedProductAdapter

        fun bind(distributedChequeUserItem: DistributedChequeUserItem) = with(binding) {
            iconUserInDistributedCheque.setImageResource(distributedChequeUserItem.user.icon)
            nameUserInDistributedCheque.text = distributedChequeUserItem.user.name

            setupMembersRecyclerList(distributedChequeUserItem.listProductsUser)

            expandableButtonDistributedCheque.setOnClickListener {
                distributedChequeUserItem.isExpanded = !distributedChequeUserItem.isExpanded
                fullInformationOfUserProducts.visibility =
                    if (distributedChequeUserItem.isExpanded) View.VISIBLE else View.GONE
            }
        }

        private fun setupMembersRecyclerList(listProducts: MutableList<Product>) = with(binding) {
            innerListDistributedProductAdapter = InnerListDistributedProductAdapter()
            listProductsByUser.adapter = innerListDistributedProductAdapter
            listProductsByUser.layoutManager = LinearLayoutManager(itemView.context)
            innerListDistributedProductAdapter.submitList(listProducts)
        }
    }
}