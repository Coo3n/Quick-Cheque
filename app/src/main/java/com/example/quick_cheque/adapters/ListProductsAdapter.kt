package com.example.quick_cheque.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.quick_cheque.R
import com.example.quick_cheque.databinding.CardChoiceProductItemBinding
import com.example.quick_cheque.model.Product
import com.example.quick_cheque.model.User

open class ListProductsAdapter :
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

        private fun setupRecyclerViewListProducts() = with(binding) {
            listProductsMembers.adapter = InnerListMembersChequeAdapter()
            listProductsMembers.layoutManager = LinearLayoutManager(binding.root.context)
            (listProductsMembers.adapter as InnerListMembersChequeAdapter).submitList(
                mutableListOf(
                    User("Zloi", R.drawable.cheque)
                )
            )
        }

        private fun getPriceProductInString(product: Product): String {
            val stringBuilder = StringBuilder()

            return stringBuilder.append(product.price.toString())
                .append(" руб / ")
                .append(product.count)
                .append(" шт").toString()
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