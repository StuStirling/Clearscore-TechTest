package com.stustirling.clearscore.ui.creditscore

import android.animation.ArgbEvaluator
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.annotation.ColorInt
import androidx.constraintlayout.widget.ConstraintLayout
import com.stustirling.clearscore.R
import com.stustirling.clearscore.databinding.LayoutCreditScoreDoughnutViewBinding
import com.stustirling.clearscore.model.CreditScoreUiModel

class CreditScoreDoughnutView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {
    companion object {
        private val DOUGHNUT_STARTING_COLOUR = Color.parseColor("#FFA500")
        private val DOUGHNUT_FINISHING_COLOUR = Color.GREEN
    }

    private var _binding: LayoutCreditScoreDoughnutViewBinding? = null
    private val binding get() = _binding!!

    private val outlinePaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.STROKE
        color = Color.BLACK
        strokeWidth = 5f
    }

    private val percentagePaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        strokeWidth = 15f
        style = Paint.Style.STROKE
    }

    @ColorInt
    private var outlineColor: Int = 0
        set(value) {
            outlinePaint.color = value
            field = value
        }

    private val scoreTextColorEvaluator = ArgbEvaluator()

    private var creditScoreUiModel: CreditScoreUiModel? = null

    init {
        setWillNotDraw(false)

        LayoutInflater.from(context).inflate(
            R.layout.layout_credit_score_doughnut_view,
            this,
            true
        )

        _binding = LayoutCreditScoreDoughnutViewBinding.bind(this)
        configureAttributes(attrs)
    }

    private fun configureAttributes(attrs: AttributeSet?) {
        context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.CreditScoreDoughnutView,
            0,
            0
        ).apply {
            try {
                val textColor =
                    getColor(R.styleable.CreditScoreDoughnutView_dvTextColor, Color.BLACK)
                binding.title.setTextColor(textColor)
                binding.max.setTextColor(textColor)

                outlineColor =
                    getColor(R.styleable.CreditScoreDoughnutView_dvOuterCircleColor, Color.BLACK)
            } finally {
                recycle()
            }
        }
    }

    fun setCreditScore(creditScoreUiModel: CreditScoreUiModel) {
        this.creditScoreUiModel = creditScoreUiModel
        binding.score.text = creditScoreUiModel.score.toString()
        binding.max.text = context.getString(
            R.string.credit_score_doughtnut_view_max_score,
            creditScoreUiModel.maxScoreValue
        )

        invalidate()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        val creditScore = creditScoreUiModel ?: return

        drawOutline(canvas)

        drawPercentage(creditScore, canvas)
    }

    private fun drawOutline(canvas: Canvas) {
        canvas.drawCircle(
            width / 2f,
            height / 2f,
            (width.coerceAtMost(height) / 2f) - outlinePaint.strokeWidth / 2,
            outlinePaint
        )
    }

    private fun drawPercentage(creditScoreUiModel: CreditScoreUiModel, canvas: Canvas) {
        val centreX = width / 2f
        val centreY = height / 2f

        val gradient = SweepGradient(
            width / 2f,
            height / 2f,
            DOUGHNUT_STARTING_COLOUR,
            DOUGHNUT_FINISHING_COLOUR
        ).apply {
            setLocalMatrix(
                Matrix().apply {
                    preRotate(-90f, centreX, centreY)
                }
            )
        }
        percentagePaint.shader = gradient

        val radius = width.coerceAtMost(height)
        val percentageRadius =
            radius / 2f - outlinePaint.strokeWidth - percentagePaint.strokeWidth * 2

        val startingArcAngle = 270f
        val scorePercentage =
            (creditScoreUiModel.score - creditScoreUiModel.minScoreValue).toFloat() /
                    (creditScoreUiModel.maxScoreValue - creditScoreUiModel.minScoreValue).toFloat()

        canvas.drawArc(
            centreX - percentageRadius,
            centreY - percentageRadius,
            centreX + percentageRadius,
            centreY + percentageRadius,
            startingArcAngle,
            scorePercentage * 360,
            false,
            percentagePaint
        )

        binding.score.setTextColor(
            scoreTextColorEvaluator.evaluate(
                scorePercentage,
                DOUGHNUT_STARTING_COLOUR, DOUGHNUT_FINISHING_COLOUR
            ) as Int
        )
    }

    override fun onDetachedFromWindow() {
        _binding = null
        super.onDetachedFromWindow()
    }
}