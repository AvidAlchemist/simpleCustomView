package com.example.simplecustomview

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View
import com.example.simplecustomview.MainActivity.Companion.happinessState
import kotlin.math.min

class CustomEmojiView(context : Context, attrs : AttributeSet) : View(context,attrs) {

    // Объект Paint для задания цветов и стиля
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    // Задание цветов для Emoji
    private var faceColor = Color.YELLOW
    private var eyesColor = Color.BLACK
    private var mouthColor = Color.BLACK
    private var borderColor = Color.BLACK
    // Ширина окантовки Emoji в пикселях
    private var borderWidth =4.0f
    // Размер Emoji в пикселях
    private var size = 320

    //Чтобы нарисовать рот в виде дуги
    private val mouthPath = Path()

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        drawFaceBackground(canvas)
        drawEyes(canvas)
        drawMouth(canvas)

        invalidate()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        //Рассчитываем минимальную сторону View (высота или ширина)
        size = min(measuredHeight,measuredWidth)
        //Сохраняем размер View, делая ширину и высоту View равными
        setMeasuredDimension(size, size)

    }

    private fun drawFaceBackground(canvas: Canvas) {

        // Определение цвета фона из ранее созданной переменной, заполнение им области рисования
        paint.color = faceColor
        paint.style = Paint.Style.FILL

        //Установка радиуса круга, который хотим нарисовать в качестве фона
        val radius = size / 2f

        // Нарисуем круг с центром (cx,cy) (где x и y равны половине размера), с рассчитанным
        // радиусои и параметром цвета
        canvas.drawCircle(size / 2f, size / 2f, radius, paint)

        // Изменим цвет краски на borderColor и сделаем границу вокруг области риссования,
        // установив стиль STROKE
        paint.color = borderColor
        paint.style = Paint.Style.STROKE
        paint.strokeWidth = borderWidth

        // Нарисуем границу с тем же центром, но с радиусом меньшим, чем предыдущий радиус,
        // на значение borderWidth
        canvas.drawCircle(size / 2f, size / 2f, radius - borderWidth / 2f, paint)
    }

    private fun drawEyes(canvas: Canvas) {
        // Установим цвет глаз и зальем им область рисования
        paint.color = eyesColor
        paint.style = Paint.Style.FILL
        // Создадим объект RectF с параметрами left, top, right и bottom, используя следующие
        // проценты размера: (25%, 23%, 43%, 50%). Затем нарисуем левый глаз, рисуя овал с
        // помощью созданного RectF. Дополнительную информацию о RectF можно найти в документации
        val leftEyeRect = RectF(size * 0.25f, size * 0.23f, size * 0.43f, size * 0.50f)

        canvas.drawOval(leftEyeRect, paint)

        // То же самое с правым глазом, размеры: (57%, 23%, 75%, 50%)
        val rightEyeRect = RectF(size * 0.57f, size * 0.23f, size * 0.75f, size * 0.50f)

        canvas.drawOval(rightEyeRect, paint)

    }

    private fun drawMouth(canvas: Canvas) {

        // В начале метода мы очищаем наш Path, чтобы рисунок рта не дублировался
        mouthPath.reset()
        // Установим начальную точку рисования нашей дуги
        mouthPath.moveTo(size * 0.22f, size * 0.7f)
        // Добавим условие отрисовки линии рта, в зависимости от состояния View
        if (happinessState == HAPPY_STATE) {
            // Устанавливаем координаты для верхней дуги рта
            mouthPath.quadTo(size * 0.50f, size * 0.80f, size * 0.78f, size * 0.70f)
            // Устанавливаем координаты для нижней дуги рта
            mouthPath.quadTo(size * 0.50f, size * 1f, size * 0.22f, size * 0.70f)
        } else {
            // Устанавливаем координаты для верхней дуги рта
            mouthPath.quadTo(size * 0.50f, size * 0.80f, size * 0.78f, size * 0.70f)
            // Устанавливаем координаты для нижней дуги рта
            mouthPath.quadTo(size * 0.50f, size * 0.60f, size * 0.22f, size * 0.70f)
        }

        //Заливаем пространство между линиями выбранным цветом
        paint.color = mouthColor
        paint.style = Paint.Style.FILL

        canvas.drawPath(mouthPath, paint)
    }
}