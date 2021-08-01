package ru.app.incredible.pets.ui.gallery.full_image

import android.os.Bundle
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import kotlinx.android.synthetic.main.screen_full_image.*
import me.dmdev.rxpm.bindTo
import org.koin.android.ext.android.getKoin
import ru.app.incredible.pets.R
import ru.app.incredible.pets.di.PmModule
import ru.app.incredible.pets.ui.common.BaseScreen
import ru.app.incredible.pets.ui.gallery.adapter.PetItem
import ru.app.incredible.pets.ui.gallery.full_image.adapter.SlideImagePetAdapter
import java.util.ArrayList
import kotlin.math.abs

class FullImageScreen : BaseScreen<FullImagePm>() {

    companion object {
        private const val ARG_IMAGE_ID = "arg_image_id"
        private const val ARG_IMAGE_LIST = "arg_image_list"

        fun newInstance(imageId: String, imageList: ArrayList<PetItem>): FullImageScreen {
            return FullImageScreen().apply {
                arguments = Bundle().apply {
                    putString(ARG_IMAGE_ID, imageId)
                    putParcelableArrayList(ARG_IMAGE_LIST, imageList)
                }
            }
        }
    }

    private val slideImagePetAdapter = SlideImagePetAdapter()

    private val imageId by lazy { requireArguments().getString(ARG_IMAGE_ID) as String }
    private val imageList by lazy {
        requireArguments().getParcelableArrayList<PetItem>(
            ARG_IMAGE_LIST
        ) as ArrayList<PetItem>
    }

    override val screenLayout = R.layout.screen_full_image

    override fun providePresentationModel() = getKoin()
        .apply {
            setProperty(PmModule.IMAGE_ID, imageId)
            setProperty(PmModule.IMAGE_LIST, imageList)
        }
        .get<FullImagePm>()

    override fun onInitView() {
        super.onInitView()

        imageViewPager.adapter = slideImagePetAdapter
        setPageTransformerForImageViewPager()
    }

    override fun onBindPresentationModel(pm: FullImagePm) {
        super.onBindPresentationModel(pm)

        pm.images bindTo { imageList ->
            slideImagePetAdapter.accept(imageList)
            imageViewPager.setCurrentItem(imageList.indexOf(imageList.first { it.image == imageId }), false)
        }
    }

    private fun setPageTransformerForImageViewPager() {
        with(imageViewPager) {
            clipToPadding = false
            clipChildren = false
            offscreenPageLimit = 3
            getChildAt(0).overScrollMode = RecyclerView.OVER_SCROLL_NEVER
        }

        val compositePageTransformer = CompositePageTransformer()
        compositePageTransformer.addTransformer(MarginPageTransformer(5))
        compositePageTransformer.addTransformer { page, position ->
            val r = 1 - abs(position)
            page.scaleY = 0.85f + r * 0.15f
        }

        imageViewPager.setPageTransformer(compositePageTransformer)
    }
}