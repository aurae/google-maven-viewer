package de.mannodermaus.googlemavenviewer.models

import android.support.annotation.LayoutRes
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup

open class Adapter<T>(private val factory: (ViewGroup, Int) -> ViewHolder<T>)
    : RecyclerView.Adapter<ViewHolder<T>>() {

    private var items = mutableListOf<T>()

    fun setItems(items: List<T>) {
        this.items.clear()
        this.items.addAll(items)
        this.notifyDataSetChanged()
    }

    override fun getItemCount(): Int = items.size
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder<T> = factory.invoke(parent, viewType)
    override fun onBindViewHolder(holder: ViewHolder<T>, position: Int) {
        holder.bind(items[position])
    }
}

abstract class ViewHolder<in T>(@LayoutRes layout: Int, parent: ViewGroup)
    : RecyclerView.ViewHolder(LayoutInflater.from(parent.context).inflate(layout, parent, false)) {

    abstract fun bind(item: T)
}