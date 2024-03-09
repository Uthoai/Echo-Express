package com.top.best.ecommerce.echoexpress.view.dashboard.seller.dashboard

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.top.best.ecommerce.echoexpress.data.Product
import com.top.best.ecommerce.echoexpress.databinding.ItemSellerProductBinding

class SellerProductAdapter(private var productList: List<Product>):
    RecyclerView.Adapter<SellerProductAdapter.ProductViewHolder>() {

    inner class ProductViewHolder(val binding: ItemSellerProductBinding): RecyclerView.ViewHolder(binding.root)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        return ProductViewHolder(ItemSellerProductBinding.inflate(LayoutInflater.from(parent.context), parent,false))
    }

    override fun getItemCount(): Int {
        return productList.size
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        productList[position].let {
            holder.binding.apply {
                txtProductName.text = it.name
                txtProductDescription.text = it.description
                txtProductPrice.text = "Price: ${it.price}"
                txtProductStock.text = "Stock: ${it.amount}"
                ivProduct.load(it.imageLink)
            }
        }
    }
}