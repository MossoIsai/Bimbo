package com.mosso.bimbo.pokemon.presentation.ui

import android.animation.AnimatorSet
import android.content.Context
import android.graphics.Typeface
import android.util.AttributeSet
import android.view.View
import android.view.View.OnClickListener
import android.view.View.inflate
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.mosso.bimbo.R

class SnackBarView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr), BaseTransientBottomBar.ContentViewCallback {

    companion object {
        private const val ANIMATION_DURATION = 350L
    }

    private val view: View
    private val messageTextView: TextView
    private val snackBarButton: TextView

    init {
        inflate(context, R.layout.layout_snackbar, this)
        this.view = findViewById(R.id.llSnackBarContainer)
        this.messageTextView = findViewById(R.id.messageTextView)
        this.snackBarButton = findViewById(R.id.labelButton)
        clipToPadding = false
    }

    fun setMessage(message: CharSequence) {
        this.messageTextView.text = message
    }

    fun addButton(text: CharSequence, callback: OnClickListener) {
        snackBarButton.text = text
        messageTextView.typeface = Typeface.DEFAULT
        snackBarButton.setOnClickListener(callback)
    }

    override fun animateContentIn(delay: Int, duration: Int) {
        val animatorSet = AnimatorSet().apply {
            setDuration(ANIMATION_DURATION)
        }
        animatorSet.start()
    }

    override fun animateContentOut(delay: Int, duration: Int) = Unit
}