package com.example.onlineshopapplication.ui.home.adapter

import android.content.Context
import android.graphics.Paint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.lottie.utils.Utils
import com.example.onlineshopapplication.R
import com.example.onlineshopapplication.data.models.home.BannerResponse
import com.example.onlineshopapplication.data.models.home.DiscountResponse
import com.example.onlineshopapplication.databinding.ItemBannerBinding
import com.example.onlineshopapplication.databinding.ItemDiscountBinding
import com.example.onlineshopapplication.ui.base.BaseDiffUtils
import com.example.onlineshopapplication.utils.BASE_URL_IMAGE
import com.example.onlineshopapplication.utils.BASE_URL_WITH_STORAGE
import com.example.onlineshopapplication.utils.extensions.loadImage
import com.example.onlineshopapplication.utils.extensions.moneySeparating
import dagger.hilt.android.qualifiers.ApplicationContext
import java.text.DecimalFormat
import javax.inject.Inject

class DiscountAdapter @Inject constructor(@ApplicationContext private val context: Context) : RecyclerView.Adapter<DiscountAdapter.ViewHolder>() {

    private var items = emptyList<DiscountResponse.ResponseDiscountItem>()
    private val decimalFormat by lazy { DecimalFormat("#,###.##") }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DiscountAdapter.ViewHolder {
       val binding = ItemDiscountBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DiscountAdapter.ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    inner class ViewHolder(private val binding:ItemDiscountBinding):RecyclerView.ViewHolder(binding.root){
        fun bind(item:DiscountResponse.ResponseDiscountItem){
            binding.apply {
                itemTitle.text = item.title
                val imageUrl = "$BASE_URL_IMAGE${item.image}"
                itemImg.loadImage(imageUrl)
                itemPriceDiscount.text = item.finalPrice.toString().toInt().moneySeparating()
                itemPrice.text = item.discountedPrice.toString().toInt().moneySeparating()
                itemPrice.paintFlags = this.itemPrice.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
                itemPrice.setTextColor(ContextCompat.getColor(context,R.color.salmon))
                root.setOnClickListener {  }
            }
        }
    }

    private var onItemClickListener:((String) -> Unit)? = null

    fun setOnItemClickListener(listener : (String) -> Unit){
        onItemClickListener = listener
    }

    fun setData(data:List<DiscountResponse.ResponseDiscountItem>){
        val adapterDiffUtils = BaseDiffUtils(items , data)
        val diffUtils = DiffUtil.calculateDiff(adapterDiffUtils)
        items = data
        diffUtils.dispatchUpdatesTo(this)
    }
}