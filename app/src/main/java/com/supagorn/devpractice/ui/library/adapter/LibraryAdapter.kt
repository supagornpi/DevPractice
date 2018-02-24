package com.supagorn.devpractice.ui.library.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.leocardz.link.preview.library.LinkPreviewCallback
import com.leocardz.link.preview.library.SourceContent
import com.leocardz.link.preview.library.TextCrawler
import com.supagorn.devpractice.R
import com.supagorn.devpractice.utils.ChromeCustomTabs
import com.supagorn.devpractice.utils.GlideLoader
import kotlinx.android.synthetic.main.layout_item_library.view.*

class LibraryAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var links: MutableList<String> = mutableListOf()
    private var chromeCustomTabs: ChromeCustomTabs? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder? {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.layout_item_library, parent, false))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        chromeCustomTabs = ChromeCustomTabs.getInstance(holder.itemView.context)
        val textCrawler = TextCrawler()
        textCrawler.makePreview(object : LinkPreviewCallback {
            override fun onPre() {
                //start animation of place holder
                holder.itemView.shimmerViewContainer.startShimmerAnimation()
            }

            override fun onPos(sourceContent: SourceContent, isNull: Boolean) {
                if (!isNull) {
                    //stop animation place holder
                    holder.itemView.shimmerViewContainer.stopShimmerAnimation()
                    //hide place holder
                    holder.itemView.placeHolder.visibility = View.GONE
                    //set action
                    bindAction(holder, position)
                    try {
                        //load image preview
                        val imageUrl = sourceContent.images.lastOrNull() ?: ""
                        GlideLoader.load(holder.itemView.context, imageUrl, holder.itemView.imgPicture)
                    } catch (e: IndexOutOfBoundsException) {

                    }
                    holder.itemView.tvTitle.text = sourceContent.title
                    holder.itemView.tvDescription.text = sourceContent.description
                    holder.itemView.tvUrl.text = sourceContent.url
                }

            }
        }, links[position])
    }

    override fun getItemCount(): Int {
        return this.links.size
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view)

    private fun bindAction(holder: RecyclerView.ViewHolder, position: Int) {
        holder.itemView.setOnClickListener {
            chromeCustomTabs!!.openCustomTabs(links[position])
        }
    }

    fun setLinks(links: MutableList<String>) {
        this.links = links
        notifyDataSetChanged()
    }
}