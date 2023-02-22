package com.phn.task.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.databinding.DataBindingUtil
import com.phn.task.R
import com.phn.task.adaptor.ImageSlideAdapter
import com.phn.task.databinding.ActivityProductDetailsBinding
import com.phn.task.model.Product
import me.relex.circleindicator.CircleIndicator

class ProductDetailsActivity : AppCompatActivity() {

    private var product: Product? = null
    lateinit var viewPagerAdapter: ImageSlideAdapter

    lateinit var binding: ActivityProductDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_product_details)

        product = intent.getParcelableExtra("product")
        //---
        viewPagerAdapter = ImageSlideAdapter(this, product!!.images)
        binding.viewpager.adapter = viewPagerAdapter
        binding.indicator.setViewPager(binding.viewpager)
        //---
        product.let {
            binding.name.text = it?.title
            binding.description.text = it?.description
            binding.price.text = "Rs.${it?.price}"
        }
    }
}