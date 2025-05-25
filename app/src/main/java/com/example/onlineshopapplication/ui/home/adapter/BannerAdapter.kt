package com.example.onlineshopapplication.ui.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.lottie.utils.Utils
import com.example.onlineshopapplication.data.models.home.BannerResponse
import com.example.onlineshopapplication.databinding.ItemBannerBinding
import com.example.onlineshopapplication.ui.base.BaseDiffUtils
import com.example.onlineshopapplication.utils.BASE_URL_WITH_STORAGE
import com.example.onlineshopapplication.utils.extensions.loadImage
import javax.inject.Inject

class BannerAdapter @Inject constructor() : RecyclerView.Adapter<BannerAdapter.ViewHolder>() {

    private var items = emptyList<BannerResponse.BannerResponseItem>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BannerAdapter.ViewHolder {
       val binding = ItemBannerBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BannerAdapter.ViewHolder, position: Int) {
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

    inner class ViewHolder(private val binding:ItemBannerBinding):RecyclerView.ViewHolder(binding.root){
        fun bind(item:BannerResponse.BannerResponseItem){
            binding.apply {
                tvTitle.text = item.title
                val imageUrl = "$BASE_URL_WITH_STORAGE${item.image}"
                ivBanner.loadImage(imageUrl)
                root.setOnClickListener {  }
            }
        }
    }

    private var onItemClickListener:((String) -> Unit)? = null

    fun setOnItemClickListener(listener : (String) -> Unit){
        onItemClickListener = listener
    }

    fun setData(data:List<BannerResponse.BannerResponseItem>){
        val adapterDiffUtils = BaseDiffUtils(items , data)
        val diffUtils = DiffUtil.calculateDiff(adapterDiffUtils)
        items = data
        diffUtils.dispatchUpdatesTo(this)
    }
}