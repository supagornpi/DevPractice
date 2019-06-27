package com.supagorn.devpractice.dialog

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.supagorn.devpractice.R
import com.supagorn.devpractice.customs.adapter.kotlin.CustomAdapter
import com.supagorn.devpractice.customs.view.DialogListView
import com.supagorn.devpractice.utils.ResolutionUtils

class DialogLists<T>(context: Context) : Dialog(context) {

    companion object {
        fun <T> build(context: Context): DialogLists<T> {
            val dialogList = DialogLists<T>(context)
            dialogList.apply {
                createView()
            }
            return dialogList
        }

        fun <T> build(context: Context, items: MutableList<T>): DialogLists<T> {
            val dialogList = DialogLists<T>(context)
            dialogList.apply {
                createView()
            }
            return dialogList.setItems(items)
        }
    }

    private var onCreateItemView: OnCreateItemView? = null
    private var adapter: CustomAdapter<T>? = null

    fun setItems(items: MutableList<T>): DialogLists<T> {
        adapter?.setListItem(items)
        return this
    }

    fun onCreateItemView(onCreateItemView: OnCreateItemView): DialogLists<T> {
        this.onCreateItemView = onCreateItemView
        return this
    }

    private fun createView() {
        val inflater = context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = inflater.inflate(R.layout.dialog_lists, null)
        setContentView(view)
        ResolutionUtils.setViewWidth(context, 1.2f, view)

//        setView(view)
        window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        adapter = CustomAdapter(object : CustomAdapter.OnBindViewListener {
            override fun <T> onBindViewHolder(item: T, itemView: View, viewType: Int, position: Int) {
                //casting
                itemView as DialogListView

                if (item is String) {
                    itemView.bind(item)
                }

                onCreateItemView?.apply {
                    //bind item view
                    onBindItemView(item, itemView.getTextView(), position)
                    //on item clicked
                    itemView.setOnClickListener {
                        onItemClicked(item, position)
                        dismiss()
                    }
                }
            }

            override fun onCreateView(): View {
                return DialogListView(context)
            }
        })

        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = adapter

    }

    interface OnCreateItemView {
        fun <T> onBindItemView(item: T, textView: TextView, position: Int)

        fun <T> onItemClicked(item: T, position: Int)
    }
}