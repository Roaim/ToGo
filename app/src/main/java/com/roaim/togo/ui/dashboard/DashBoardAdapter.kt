package com.roaim.togo.ui.dashboard

import android.view.LayoutInflater
import android.view.ViewGroup
import com.roaim.togo.base.RecyclerBindingAdapter
import com.roaim.togo.base.RecyclerBindingViewHolder
import com.roaim.togo.data.model.ToGo
import com.roaim.togo.databinding.ItemRvDashboardBinding

class DashBoardAdapter : RecyclerBindingAdapter<ToGo, VH, ItemRvDashboardBinding>() {
    override fun onGetViewBinding(
        layoutInflater: LayoutInflater,
        parent: ViewGroup
    ): ItemRvDashboardBinding = ItemRvDashboardBinding.inflate(layoutInflater, parent, false)

    override fun onCreateViewHolder(binding: ItemRvDashboardBinding, viewType: Int): VH =
        VH(binding)


}

class VH(binding: ItemRvDashboardBinding) :
    RecyclerBindingViewHolder<ToGo, ItemRvDashboardBinding>(binding) {
    override fun bind(item: ToGo) {
        binding.togo = item
    }

}