package ru.app.incredible.pets.ui.common.adapter

import android.content.res.Resources
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.extensions.LayoutContainer

abstract class BaseViewHolder<in I>(
    override val containerView: View
) : RecyclerView.ViewHolder(containerView), LayoutContainer {

    val resources: Resources get() = containerView.resources

    abstract fun bind(item: I)
}