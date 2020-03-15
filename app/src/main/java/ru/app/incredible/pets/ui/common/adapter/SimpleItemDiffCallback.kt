package ru.app.incredible.pets.ui.common.adapter

import android.annotation.SuppressLint
import androidx.recyclerview.widget.DiffUtil
import ru.app.incredible.pets.domain.Identifiable

class SimpleItemDiffCallback<T> : DiffUtil.ItemCallback<T>() {

    override fun areItemsTheSame(oldItem: T, newItem: T): Boolean {
        return if (oldItem is Identifiable && newItem is Identifiable) {
            oldItem.id == newItem.id
        } else {
            oldItem === newItem
        }
    }

    @SuppressLint("DiffUtilEquals")
    override fun areContentsTheSame(oldItem: T, newItem: T): Boolean {
        return oldItem == newItem
    }
}