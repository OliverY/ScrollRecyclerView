package com.yxj.scrollrecyclerview

import android.content.Context
import android.graphics.PointF
import android.util.DisplayMetrics
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSmoothScroller
import androidx.recyclerview.widget.RecyclerView

/**
 * Created by yxj (yanxujun@dxy.cn)
 * Date: 2019-07-31 09:57
 */
class CustomScrollLinearLayoutManager @JvmOverloads constructor(
    private val context: Context,
    orientation: Int = RecyclerView.VERTICAL,
    reverseLayout: Boolean = false
) : LinearLayoutManager(context, orientation, reverseLayout) {

    private var MILLISECONDS_PRE_INCH = 0.03f
    private var snapToBounds = -2

    init {
        setSpeedSlow()
    }

    override fun smoothScrollToPosition(recyclerView: RecyclerView, state: RecyclerView.State?, position: Int) {
        super.smoothScrollToPosition(recyclerView, state, position)
        val linearSmoothScroller = object : LinearSmoothScroller(recyclerView.context) {
            override fun computeScrollVectorForPosition(targetPosition: Int): PointF? {
                return this@CustomScrollLinearLayoutManager.computeScrollVectorForPosition(targetPosition)
            }

            override fun calculateSpeedPerPixel(displayMetrics: DisplayMetrics): Float {
                return MILLISECONDS_PRE_INCH / displayMetrics.density
            }

            // 滑动到中间
//            override fun calculateDtToFit(
//                viewStart: Int,
//                viewEnd: Int,
//                boxStart: Int,
//                boxEnd: Int,
//                snapPreference: Int
//            ): Int {
//                return (boxStart + (boxEnd - boxStart)/2) - (viewStart + (viewEnd - viewStart)/2) // 中间点减中间点
//            }

            override fun getVerticalSnapPreference(): Int {
                return if (snapToBounds != -2) {
                    snapToBounds
                } else super.getVerticalSnapPreference()
                return SNAP_TO_END
            }
        }
        linearSmoothScroller.targetPosition = position
        startSmoothScroll(linearSmoothScroller)
    }

    fun setSpeedSlow() {
        MILLISECONDS_PRE_INCH = context.resources.displayMetrics.density * 0.3f
    }

    fun setSpeedFast() {
        MILLISECONDS_PRE_INCH = context.resources.displayMetrics.density * 0.03f
    }

    fun setSnapToBounds(snapToBounds: Boolean) {
        this.snapToBounds = if (snapToBounds) LinearSmoothScroller.SNAP_TO_START else LinearSmoothScroller.SNAP_TO_END
    }

    override fun onLayoutChildren(recycler: RecyclerView.Recycler?, state: RecyclerView.State) {
        try {
            super.onLayoutChildren(recycler, state)
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }
}