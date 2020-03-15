package ru.app.incredible.pets.ui.common.adapter

import androidx.recyclerview.widget.ListAdapter
import io.reactivex.functions.Consumer
import ru.app.incredible.pets.domain.Identifiable

abstract class BaseListAdapter<T : Identifiable, VH : BaseViewHolder<T>> :
    ListAdapter<T, VH>(SimpleItemDiffCallback()),
    Consumer<List<T>> {

    override fun accept(data: List<T>) {
        submitList(data)
    }

    override fun getItemId(position: Int): Long {
        return getItem(position).id
    }

    final override fun onBindViewHolder(holder: VH, position: Int) {
        holder.bind(getItem(position))
    }

}