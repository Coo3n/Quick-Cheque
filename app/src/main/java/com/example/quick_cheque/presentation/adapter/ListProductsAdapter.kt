package com.example.quick_cheque.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.quick_cheque.R
import com.example.quick_cheque.databinding.CardChoiceProductItemBinding
import com.example.quick_cheque.domain.model.Product
import com.example.quick_cheque.domain.model.User

open class ListProductsAdapter : ListAdapter<Product, ListProductsAdapter.ListProductsViewHolder>(
    ListProductDiffCallBack()
) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListProductsViewHolder {
        return ListProductsViewHolder(
            CardChoiceProductItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
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

    inner class ListProductsViewHolder(
        private val binding: CardChoiceProductItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        private lateinit var innerListMembersProductAdapter: InnerListMembersChequeAdapter
        fun bind(product: Product) = with(binding) {
            titleProduct.text = product.titleProduct
            priceProduct.text = getPriceProductInString(product)

            setupMembersRecyclerList(product.membersProduct)

            buttonAddMembersInProduct.setOnClickListener {
                innerListMembersProductAdapter.addNewListMemberCheque(
                    User("Olua", "es", R.drawable.person_filled)
                )
            }
        }

        private fun setupMembersRecyclerList(
            listMembersCheque: MutableList<User>
        ) = with(binding) {
            innerListMembersProductAdapter = InnerListMembersChequeAdapter()
            listProductsMembers.adapter = innerListMembersProductAdapter
            listProductsMembers.layoutManager = LinearLayoutManager(
                binding.root.context,
                LinearLayoutManager.HORIZONTAL,
                false
            )
            innerListMembersProductAdapter.submitList(listMembersCheque)
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