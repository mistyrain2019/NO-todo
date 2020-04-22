package com.gang.notodo.ui.calendar

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint

import com.gang.notodo.util.CalendarUtil
import com.haibin.calendarview.Calendar
import com.haibin.calendarview.WeekView


class IndexWeekView(context: Context) : WeekView(context) {

    private val mPadding = CalendarUtil.dipToPx(getContext(), 4f)
    private val mH = CalendarUtil.dipToPx(getContext(), 2f)
    private val mW = CalendarUtil.dipToPx(getContext(), 8f)

    private val mSchemeBasicPaint = Paint().apply {
        this.isAntiAlias = true
        this.style = Paint.Style.FILL
        this.textAlign = Paint.Align.CENTER
        this.color = -0xcccccd
        this.isFakeBoldText = true
    }


    override fun onPreviewHook() {
    }

    /**
     * 如果这里和 onDrawScheme 是互斥的，则 return false，
     * return true 会先绘制 onDrawSelected，再绘制onDrawSelected
     *
     * @param canvas    canvas
     * @param calendar  日历日历calendar
     * @param x         日历Card x起点坐标
     * @param hasScheme hasScheme 非标记的日期
     */
    override fun onDrawSelected(
        canvas: Canvas,
        calendar: Calendar,
        x: Int,
        hasScheme: Boolean
    ): Boolean {
        mSelectedPaint.style = Paint.Style.FILL
        canvas.drawRect(
            (x + mPadding).toFloat(),
            mPadding.toFloat(),
            (x + mItemWidth - mPadding).toFloat(),
            (mItemHeight - mPadding).toFloat(),
            mSelectedPaint
        )
        return true
    }

    /**
     * 绘制下标标记
     *
     * @param canvas   canvas
     * @param calendar 日历calendar
     * @param x        日历Card x起点坐标
     */
    override fun onDrawScheme(canvas: Canvas, calendar: Calendar, x: Int) {
        mSchemeBasicPaint.color = calendar.schemeColor
        canvas.drawRect(
            (x + mItemWidth / 2 - mW / 2).toFloat(),
            (mItemHeight - mH * 2 - mPadding).toFloat(),
            (x + mItemWidth / 2 + mW / 2).toFloat(),
            (mItemHeight - mH - mPadding).toFloat(), mSchemeBasicPaint
        )
    }

    override fun onDrawText(
        canvas: Canvas,
        calendar: Calendar,
        x: Int,
        hasScheme: Boolean,
        isSelected: Boolean
    ) {
        val cx = x + mItemWidth / 2
        val top = -mItemHeight / 6
        if (hasScheme) {
            canvas.drawText(
                calendar.day.toString(), cx.toFloat(), mTextBaseLine + top,
                when {
                    calendar.isCurrentDay -> mCurDayTextPaint
                    calendar.isCurrentMonth -> mSchemeTextPaint
                    else -> mCurMonthTextPaint
                }
            )
            canvas.drawText(
                calendar.lunar, cx.toFloat(), mTextBaseLine + mItemHeight / 10,
                if (calendar.isCurrentDay)
                    mCurDayLunarTextPaint
                else
                    mCurMonthLunarTextPaint
            )
        } else {
            canvas.drawText(
                calendar.day.toString(), cx.toFloat(), mTextBaseLine + top,
                when {
                    calendar.isCurrentDay -> mCurDayTextPaint
                    calendar.isCurrentMonth -> mCurMonthTextPaint
                    else -> mCurMonthTextPaint
                }
            )
            canvas.drawText(
                calendar.lunar, cx.toFloat(), mTextBaseLine + mItemHeight / 10,
                if (calendar.isCurrentDay)
                    mCurDayLunarTextPaint
                else
                    mCurMonthLunarTextPaint
            )
        }
    }
}
