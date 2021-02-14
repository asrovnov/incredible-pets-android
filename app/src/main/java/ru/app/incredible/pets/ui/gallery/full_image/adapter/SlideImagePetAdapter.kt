package ru.app.incredible.pets.ui.gallery.full_image.adapter

import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.item_slide_image.view.*
import ru.app.incredible.pets.R
import ru.app.incredible.pets.extensions.inflate
import ru.app.incredible.pets.ui.common.adapter.BaseListAdapter
import ru.app.incredible.pets.ui.common.adapter.BaseViewHolder
import ru.app.incredible.pets.ui.gallery.adapter.PetItem

class SlideImagePetAdapter() : BaseListAdapter<PetItem, SlideImagePetAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(parent.inflate(R.layout.item_slide_image))
    }

    class ViewHolder(view: View) : BaseViewHolder<PetItem>(view) {

        private var item: PetItem? = null

        override fun bind(item: PetItem) {
            this.item = item

            Glide.with(itemView)
                .load(item.image)
                .into(itemView.slideImageView)
        }
    }
}