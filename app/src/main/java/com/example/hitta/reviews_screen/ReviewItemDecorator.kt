package com.example.hitta.reviews_screen

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.hitta.R
import com.example.core.R as coreR

class ReviewItemDecorator(
    context: Context,
) : RecyclerView.ItemDecoration() {

    private val dividerHeight = context.resources.getDimensionPixelSize(coreR.dimen.spacing_1)
    private val verticalMargin = context.resources.getDimensionPixelSize(coreR.dimen.spacing_12)
    private val dividerPaint = Paint().apply {
        color = ContextCompat.getColor(context, coreR.color.light_grey)
    }

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)
        when (getViewType(view, parent)) {
            R.layout.view_holder_header -> setRectMargins(outRect, verticalMargin, 0)
            else -> setRectMargins(outRect, verticalMargin, verticalMargin)
        }
    }

    private fun setRectMargins(outRect: Rect, top: Int, bottom: Int) {
        outRect.top = top
        outRect.bottom = bottom
    }

    override fun onDrawOver(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        super.onDrawOver(c, parent, state)

        val childCount = parent.childCount
        val left = 0
        val right = parent.width

        for (index in 0 until childCount - 2) {
            val view = parent.getChildAt(index)

            if (getViewType(view, parent) == R.layout.view_holder_header) {
                continue
            }

            val top = view.bottom + verticalMargin
            val bottom = top + dividerHeight
            c.drawRect(left.toFloat(), top.toFloat(), right.toFloat(), bottom.toFloat(), dividerPaint)
        }
    }

    private fun getViewType(
        view: View,
        parent: RecyclerView,
    ): Int? {
        val adapterPosition = parent.getChildAdapterPosition(view)
        return parent.adapter?.getItemViewType(adapterPosition)
    }
}