package ru.andreycherenkov.catandmouse

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import kotlin.random.Random
import kotlinx.coroutines.*

import android.view.MotionEvent
import android.view.View
import kotlin.math.atan2

class GameActivity : AppCompatActivity() {


    companion object {
        const val SIZE_VALUE: String = "size"
        const val SPEED_VALUE: String = "speed"
        const val COUNT_VALUE: String = "count"

        @JvmStatic
        fun getIntent(
            context: Context,
            clazz: Class<out GameActivity>,
            sizeValue: Int,
            speedValue: Int,
            count: Int
        ): Intent {
            val intent = Intent(context, clazz).apply {
                putExtra(SIZE_VALUE, sizeValue)
                putExtra(SPEED_VALUE, speedValue)
                putExtra(COUNT_VALUE, count)
            }
            return intent
        }
    }

    private val screenWidth by lazy { resources.displayMetrics.widthPixels }
    private val screenHeight by lazy { resources.displayMetrics.heightPixels }
//    private lateinit var databaseHelper: DatabaseHelper //todo

    private var totalClicks = 0
    private var mouseClicks = 0

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        databaseHelper = DatabaseHelper(this) //todo

        val size = intent.getIntExtra(SIZE_VALUE, 300)
        val speed = intent.getIntExtra(SPEED_VALUE, 1500)
        val quantity = intent.getIntExtra(COUNT_VALUE, 0)


        for (i in 0 until quantity) {
            createMouse(size, speed)
        }

        // Устанавливаем слушатель нажатий на экран
        findViewById<View>(android.R.id.content).setOnTouchListener { _, event ->
            if (event.action == MotionEvent.ACTION_DOWN) {
                totalClicks++
                if (isMouseClicked(event.x, event.y)) {
                    mouseClicks++
                }
            }
            true
        }
    }

    private fun createMouse(size: Int, speed: Int) {
        val mouseImageView = ImageView(this).apply {
            setImageResource(R.drawable.mouse2)
            layoutParams = ViewGroup.LayoutParams(size, size)
            x = Random.nextInt(0, screenWidth - size).toFloat()
            y = Random.nextInt(0, screenHeight - size).toFloat()
            tag = this
        }

        (findViewById<ViewGroup>(android.R.id.content)).addView(mouseImageView)

        moveMouse(mouseImageView, speed)
    }

    private fun moveMouse(mouseImageView: ImageView, speed: Int) {
        CoroutineScope(Dispatchers.Main).launch {
            val startX = mouseImageView.x
            val startY = mouseImageView.y

            val (endX, endY) = generateRandomEndCoordinates(mouseImageView)
            val angle =
                Math.toDegrees(atan2((endY - startY).toDouble(), (endX - startX).toDouble()))
                    .toFloat() + 270f
            mouseImageView.rotation = angle

            val animator = ValueAnimator.ofFloat(0f, 1f).apply {
                duration = speed.toLong()
                addUpdateListener { animation ->
                    val animatedValue = animation.animatedValue as Float
                    mouseImageView.x = startX + (endX - startX) * animatedValue
                    mouseImageView.y = startY + (endY - startY) * animatedValue
                }
            }

            animator.addListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator) {
                    moveMouse(mouseImageView, speed) // Перемещаем мышку снова
                }
            })

            animator.start()
        }
    }

    private fun generateRandomEndCoordinates(mouseImageView: ImageView): Pair<Float, Float> {
        val endX: Float
        val endY: Float

        when (Random.nextInt(4)) {
            0 -> {
                endX = Random.nextFloat() * (screenWidth - mouseImageView.width)
                endY = 0f
            }

            1 -> {
                endX = Random.nextFloat() * (screenWidth - mouseImageView.width)
                endY = screenHeight - mouseImageView.height.toFloat()
            }

            2 -> {
                endX = 0f
                endY = Random.nextFloat() * (screenHeight - mouseImageView.height)
            }

            3 -> {
                endX = screenWidth - mouseImageView.width.toFloat()
                endY = Random.nextFloat() * (screenHeight - mouseImageView.height)
            }

            else -> {
                endX = mouseImageView.x
                endY = mouseImageView.y
            }
        }
        return Pair(endX, endY)
    }


    private fun isMouseClicked(x: Float, y: Float): Boolean {
        // Проверяем все мышки на экране
        for (i in 0 until (findViewById<ViewGroup>(android.R.id.content)).childCount) {
            val child = (findViewById<ViewGroup>(android.R.id.content)).getChildAt(i)
            if (child is ImageView) {
                if (x >= child.x && x <= child.x + child.width && y >= child.y && y <= child.y + child.height) {
                    return true // Попали по мышке
                }
            }
        }
        return false // Не попали ни по одной мышке
    }

    override fun onDestroy() {
        super.onDestroy()
        // Сохраняем статистику в БД при завершении игры
//        databaseHelper.insertStatistics(totalClicks, mouseClicks) //todo
    }

}