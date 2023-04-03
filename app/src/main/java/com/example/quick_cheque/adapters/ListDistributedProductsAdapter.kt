package com.example.quick_cheque.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.quick_cheque.R
import com.example.quick_cheque.databinding.CardDistributedChequeBinding
import com.example.quick_cheque.model.Product

class ListDistributedProductsAdapter :
    ListAdapter<Product, ListDistributedProductsAdapter.DistributedProductsViewHolder>(
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

    class ListDistributedProductsDiffCallback : DiffUtil.ItemCallback<Product>() {
        override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem == newItem
        }
    }

    class DistributedProductsViewHolder(
        private val binding: CardDistributedChequeBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        private lateinit var innerListDistributedProductAdapter: InnerListDistributedProductAdapter

        fun bind(product: Product) = with(binding) {
            iconUserInDistributedCheque.setImageResource(R.drawable.person_filled)
            nameUserInDistributedCheque.text = "Kolya"
            setupMembersRecyclerList(product)

//            expandableButtonDistributedCheque.setOnClickListener {
//                expandableListItem.isExpanded = !expandableListItem.isExpanded
//                changingStyleExpandableObjectInChequeListItem(
//                    expandableListItem.isExpanded,
//                    expandableListItem.isClicked
//                )
//            }
        }

        private fun setupMembersRecyclerList(listProducts: Product) = with(binding) {
            innerListDistributedProductAdapter = InnerListDistributedProductAdapter()
            listProductsByUser.adapter = innerListDistributedProductAdapter
            listProductsByUser.layoutManager = LinearLayoutManager(
                itemView.context,
                LinearLayoutManager.HORIZONTAL,
                false
            )
            innerListDistributedProductAdapter.submitList(
                mutableListOf(
                    listProducts,
                    listProducts,
                    listProducts
                )
            )
        }
    }
}