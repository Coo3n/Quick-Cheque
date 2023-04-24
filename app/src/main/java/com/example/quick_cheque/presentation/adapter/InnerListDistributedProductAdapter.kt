package com.example.quick_cheque.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.quick_cheque.databinding.ListProductsChequeBinding
import com.example.quick_cheque.domain.model.Product

class InnerListDistributedProductAdapter :
    ListAdapter<Product, InnerListDistributedProductAdapter.InnerListProductsViewHolder>(
        InnerListProductsDiffCallBack()
    ) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InnerListProductsViewHolder {
        return InnerListProductsViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: InnerListProductsViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class InnerListProductsDiffCallBack : DiffUtil.ItemCallback<Product>() {
        override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem == newItem
        }
    }

    class InnerListProductsViewHolder(
        private val binding: ListProductsChequeBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(product: Product) = with(binding) {
            titleProduct.text = product.titleProduct
            priceProduct.text = getPriceProductInString(product)
        }

        companion object {
            fun create(parent: ViewGroup): InnerListProductsViewHolder {
                return InnerListProductsViewHolder(
                    ListProductsChequeBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
            }
        }

        private fun getPriceProductInString(product: Product): String {
            val stringBuilder = StringBuilder()

            return stringBuilder.append(product.price.toString())
                .append(" руб / ")
                .append(product.count)
                .append(" шт").toString()
        }
    }
}