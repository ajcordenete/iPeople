package com.aljon.ipeople.base

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.aljon.ipeople.BR
import com.aljon.ipeople.R
import com.aljon.module.common.ninjaTap
import io.reactivex.subjects.PublishSubject

/**
 * @param B item view data binding.
 * @param I item class type.
 */
abstract class BaseRecyclerViewAdapter<B : ViewDataBinding, I : Any> : RecyclerView.Adapter<ViewHolder<B>>() {

    val itemClickListener = PublishSubject.create<I>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder<B> {
        return ViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                viewType,
                parent,
                false
            )
        )
    }

    override fun getItemViewType(position: Int): Int {
        return getLayoutIdForPosition(position)
    }

    override fun onBindViewHolder(holder: ViewHolder<B>, position: Int) {
        holder.bind(getItemForPosition(position))
        holder
            .binding
            .root
            .findViewById<View?>(R.id.itemClickable)
            ?.ninjaTap {
                itemClickListener.onNext(getItemForPosition(position))
            }
    }

    abstract fun getItemForPosition(position: Int): I

    @LayoutRes
    abstract fun getLayoutIdForPosition(position: Int): Int
}

open class ViewHolder<B : ViewDataBinding>(val binding: B) : RecyclerView.ViewHolder(binding.root) {
    fun bind(item: Any) {
        binding.setVariable(BR.item, item)
        binding.executePendingBindings()
    }
}
