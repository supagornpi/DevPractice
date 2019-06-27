package com.supagorn.devpractice.ui.category.adapter

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.supagorn.devpractice.R
import com.supagorn.devpractice.enums.Categories
import com.supagorn.devpractice.ui.category.viewpager.FragmentViewPagerActivity
import com.supagorn.devpractice.ui.category.viewpager.ViewPagerActivity
import com.supagorn.devpractice.ui.video.VideoFullScreenActivity
import com.supagorn.devpractice.utils.ResolutionUtils
import kotlinx.android.synthetic.main.layout_item_category.view.*

class CategoryAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var list: MutableList<Categories> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.layout_item_category, parent, false)
        //set item size
        ResolutionUtils.setViewSize(parent.context, 2.0f, 2.0f, view)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val entity = list[position]
        holder.itemView.imgIcon.setImageResource(entity.icon)

        if (entity.icon == 0) {
            holder.itemView.tvLargeTitle.text = holder.itemView.context.resources.getString(entity.title)
        } else {
            holder.itemView.tvTitle.text = holder.itemView.context.resources.getString(entity.title)
        }
        bindAction(holder, position)
    }

    override fun getItemCount(): Int {
        return this.list.size
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view)

    private fun bindAction(holder: RecyclerView.ViewHolder, position: Int) {
        holder.itemView.setOnClickListener {
            when (list[position]) {
                Categories.Video -> {
                    VideoFullScreenActivity.start(holder.itemView.context)
                }
                Categories.ViewPagerVertical -> {
                    ViewPagerActivity.start()
                }
                Categories.FragmentViewPagerVertical -> {
                    FragmentViewPagerActivity.start()
                }
            }
        }
    }

    fun setList(links: MutableList<Categories>) {
        this.list = links
        notifyDataSetChanged()
    }
}