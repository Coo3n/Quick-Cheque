package com.example.quick_cheque.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.quick_cheque.R
import com.example.quick_cheque.databinding.CardChoiceProductItemBinding
import com.example.quick_cheque.databinding.CardDistributedChequeBinding
import com.example.quick_cheque.databinding.FragmentDistributedChequeBinding
import com.example.quick_cheque.model.Product

class ListDistributedProductsAdapter :
    ListAdapter<Product, ListDistributedProductsAdapter.DistributedProductsViewHolder>(
        ListDistributedProductsDiffCallback()
    ) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): DistributedProductsViewHolder {
        return DistributedProductsViewHolder.create(parent)
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
        fun bind(product: Product) {

        }

        companion object {
            fun create(parent: ViewGroup): DistributedProductsViewHolder {
                return DistributedProductsViewHolder(
                    CardDistributedChequeBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
            }
        }
    }
}