package com.supagorn.devpractice.ui.library.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.supagorn.devpractice.R
import com.supagorn.devpractice.ui.library.model.LinkPreviewEntity
import com.supagorn.devpractice.utils.ChromeCustomTabs
import com.supagorn.devpractice.utils.GlideLoader
import kotlinx.android.synthetic.main.layout_item_library.view.*

class LibraryAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var linkPreviews: MutableList<LinkPreviewEntity> = mutableListOf()
    private var chromeCustomTabs: ChromeCustomTabs? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder? {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.layout_item_library, parent, false))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        chromeCustomTabs = ChromeCustomTabs.getInstance(holder.itemView.context)

        val entity = linkPreviews[position]
        GlideLoader.load(holder.itemView.context, entity.imageUrl, holder.itemView.imgPicture)
        holder.itemView.tvTitle.text = entity.title
        holder.itemView.tvDescription.text = entity.description
        holder.itemView.tvUrl.text = entity.link

        bindAction(holder, position)

    }

    override fun getItemCount(): Int {
        return this.linkPreviews.size
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view)

    private fun bindAction(holder: RecyclerView.ViewHolder, position: Int) {
        holder.itemView.setOnClickListener {
            chromeCustomTabs!!.openCustomTabs(linkPreviews[position].link)
        }
    }

    fun setLinks(links: MutableList<LinkPreviewEntity>) {
        this.linkPreviews = links
        notifyDataSetChanged()
    }

    fun addLinks(linkPreviewEntity: LinkPreviewEntity) {
        this.linkPreviews.add(linkPreviewEntity)
        notifyItemInserted(this.linkPreviews.size - 1)
    }
}