package com.aljon.ipeople.features.person.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.aljon.ipeople.R
import com.aljon.ipeople.base.BaseRecyclerViewAdapter
import com.aljon.ipeople.base.ViewHolder
import com.aljon.ipeople.databinding.ItemPersonBinding
import com.aljon.ipeople.features.person.DisplayablePerson

class PersonAdapter(private var list: List<DisplayablePerson>) : BaseRecyclerViewAdapter<ItemPersonBinding, DisplayablePerson>() {

    override fun getItemForPosition(position: Int): DisplayablePerson = list[position]

    override fun getLayoutIdForPosition(position: Int): Int = R.layout.item_person

    override fun getItemCount(): Int = list.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder<ItemPersonBinding> {
        return ViewHolder(
            ItemPersonBinding
                .inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
        )
    }

    fun updateList(newList: List<DisplayablePerson>) {
        this.list = newList
        notifyDataSetChanged()
    }
}
