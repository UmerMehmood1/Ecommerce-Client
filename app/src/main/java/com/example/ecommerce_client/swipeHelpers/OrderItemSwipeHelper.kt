package com.example.ecommerce_client.swipeHelpers

import android.content.res.Resources
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.graphics.drawable.Drawable
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
abstract class OrderItemSwipeHelper(private val deleteIcon: Drawable) : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
    private val backgroundColor = Color.RED
    private val borderRadius = 10.dpToPx()
    private val iconMargin = 16.dpToPx()

    private val backgroundPaint = Paint().apply {
        color = backgroundColor
    }
    private val iconBounds = Rect()

    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {
        return false
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        onSwipe(viewHolder.adapterPosition)
    }

    override fun onChildDraw(
        c: Canvas,
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        dX: Float,
        dY: Float,
        actionState: Int,
        isCurrentlyActive: Boolean
    ) {
        val itemView = viewHolder.itemView
        val itemHeight = itemView.bottom - itemView.top

        // Draw background
        c.drawRoundRect(
            itemView.right.toFloat() + dX,
            itemView.top.toFloat(),
            itemView.right.toFloat(),
            itemView.bottom.toFloat(),
            borderRadius.toFloat(),
            borderRadius.toFloat(),
            backgroundPaint
        )

        // Draw delete icon
        val iconTop = itemView.top + (itemHeight - deleteIcon.intrinsicHeight)  / 2
        val iconBottom = iconTop + deleteIcon.intrinsicHeight
        val iconRight = itemView.right - iconMargin
        val iconLeft = iconRight - deleteIcon.intrinsicWidth
        deleteIcon.bounds = Rect(iconLeft, iconTop, iconRight, iconBottom)
        deleteIcon.draw(c)


        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
    }
    private fun Int.dpToPx(): Int {
        val density = Resources.getSystem().displayMetrics.density
        return (this * density).toInt()
    }

    abstract fun onSwipe(position: Int)
}