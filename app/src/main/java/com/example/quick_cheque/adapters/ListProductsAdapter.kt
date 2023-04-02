package com.example.quick_cheque.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.quick_cheque.databinding.CardChoiceProductItemBinding
import com.example.quick_cheque.model.Product

class ListProductsAdapter :
    ListAdapter<Product, ListProductsAdapter.ListProductsViewHolder>(ListProductDiffCallBack()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListProductsViewHolder {
        return ListProductsViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: ListProductsViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ListProductDiffCallBack : DiffUtil.ItemCallback<Product>() {
        override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem == newItem
        }
    }

    class ListProductsViewHolder(
        private val binding: CardChoiceProductItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(product: Product) = with(binding) {
            titleProduct.text = product.titleProduct
            priceProduct.text = getPriceProductInString(product)
        }

        private fun getPriceProductInString(product: Product): String {
            val stringBuilder = StringBuilder()

            return stringBuilder.append(product.price.toString())
                .append(" руб / ")
                .append(product.count)
                .append(" шт")
                .toString()
        }

        companion object {
            fun create(parent: ViewGroup): ListProductsViewHolder {
                return ListProductsViewHolder(
                    CardChoiceProductItemBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
            }
        }
    }
}