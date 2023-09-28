package com.example.demo.customview

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import androidx.annotation.ColorInt
import androidx.core.content.res.use
import com.example.demo.R

class CustomCanvasView @JvmOverloads constructor(
  context: Context,
  attrs: AttributeSet? = null,
  defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {
  @ColorInt
  private var circleColor: Int = Color.BLACK
  private var circleRadius: Int = 0

  private val paint: Paint

  init {
    context.obtainStyledAttributes(attrs, R.styleable.CustomCanvasView).use { typeArray ->
      circleColor = typeArray.getColor(R.styleable.CustomCanvasView_circleColor, Color.BLACK)
      circleRadius = typeArray.getDimensionPixelSize(R.styleable.CustomCanvasView_circleRadius, 0)
    }

    paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
      color = circleColor
      style = Paint.Style.FILL
    }
  }

  override fun onDraw(canvas: Canvas) {
    super.onDraw(canvas)

    canvas.drawCircle(
      /* cx = */ width.toFloat() / 2,
      /* cy = */ height.toFloat() / 2,
      /* radius = */ circleRadius.toFloat(),
      /* paint = */ paint,
    )
  }
}