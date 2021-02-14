package ru.app.incredible.pets.ui.gallery.adapter

import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.item_image_pet.*
import ru.app.incredible.pets.R
import ru.app.incredible.pets.extensions.inflate
import ru.app.incredible.pets.ui.common.adapter.BaseListAdapter
import ru.app.incredible.pets.ui.common.adapter.BaseViewHolder

class GalleryAdapter(
    private val imagePetClickListener: (image: String) -> Unit
) : BaseListAdapter<PetItem, GalleryAdapter.PetImageItemViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PetImageItemViewHolder {
        return PetImageItemViewHolder(parent.inflate(R.layout.item_image_pet))
    }

    inner class PetImageItemViewHolder(view: View) : BaseViewHolder<PetItem>(view) {

        private var item: PetItem? = null

        init {
            containerView.setOnClickListener {
                item?.let { petItem ->
                    imagePetClickListener(petItem.image)
                }
            }
        }

        override fun bind(item : PetItem) {
            this.item = item
            Glide.with(itemView)
                .load(item.image)
                .into(petImage)
        }
    }
}