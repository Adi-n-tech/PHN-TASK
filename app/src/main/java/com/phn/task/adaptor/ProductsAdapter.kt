package com.phn.task.adaptor

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat.startActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.phn.task.R
import com.phn.task.databinding.ItemProductBinding
import com.phn.task.model.Product
import com.phn.task.view.MainActivity
import com.phn.task.view.ProductDetailsActivity
import com.phn.task.view.RegistrationActivity
import com.squareup.picasso.Picasso

class ProductsAdapter(context: Context) : RecyclerView.Adapter<ProductsAdapter.ViewHolder>() {

    var mNewList: List<Product> = emptyList()
    var context: Context = context

    // create new views
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // inflates the card_view_design view
        // that is used to hold list item
        val view: ItemProductBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_product,
            parent,
            false
        )
        return ViewHolder(view)
    }

    // binds the list items to a view
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val product: Product = mNewList[position]
        holder.itemTransactionBinding.title.text = product.title
        holder.itemTransactionBinding.name.text = product.description
        holder.itemTransactionBinding.price.text = "Rs.${product.price}"
        //------
        Picasso.get().load(product.images[0]).into(holder.itemTransactionBinding.image)
        //----
        holder.itemTransactionBinding.parant.setOnClickListener {
            context.startActivity(
                Intent(
                    context,
                    ProductDetailsActivity::class.java
                ).putExtra("product", product)
            )
        }
    }

    // return the number of the items in the list
    override fun getItemCount(): Int {
        return mNewList.size
    }

    // Holds the views for adding it to image and text
    class ViewHolder(itemGoalsBinding: ItemProductBinding) :
        RecyclerView.ViewHolder(itemGoalsBinding.root) {
        val itemTransactionBinding = itemGoalsBinding
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateList(list: List<Product>) {
        this.mNewList = list
        notifyDataSetChanged()
    }


}