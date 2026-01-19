package com.review.reviewdialog

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.res.ColorStateList
import android.graphics.drawable.Drawable
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.lottie.LottieAnimationView
import com.airbnb.lottie.LottieDrawable
import com.android.reviewdialog.adapter.RateUsModel
import com.android.reviewdialog.adapter.RateusAdapter
import com.exaple.reviewdialog.R
import com.google.android.play.core.review.ReviewInfo
import com.google.android.play.core.review.ReviewManagerFactory

object ReviewDialog {
    private var rateusdialog: Dialog? = null
    private var reviewDialog: Dialog? = null
    private var reviewInfo: ReviewInfo? = null
    private val handler = Handler(Looper.getMainLooper())
    private var toast: Toast? = null
    private var rateusAdapter: RateusAdapter? = null

    fun showReviewDialog(context: Context , onDismiss: (isReviewGiven : Boolean) -> Unit) {
        reviewDialog?.let {
            if (it.isShowing) {
                it.dismiss()
            }
        }

        reviewDialog = Dialog(context)
        reviewDialog?.window?.let { window ->
            window.setBackgroundDrawableResource(android.R.color.transparent)
        }
        reviewDialog?.setContentView(R.layout.reviewdialog)
        reviewDialog?.setCancelable(false)
        reviewDialog?.show()

        val likeBtn = reviewDialog?.findViewById<ImageView>(R.id.likeBtn)
        val dislikeBtn = reviewDialog?.findViewById<ImageView>(R.id.dislikeBtn)
        val closeBtn = reviewDialog?.findViewById<ImageView>(R.id.crossBtn)

        likeBtn?.setOnClickListener {
            reviewDialog?.dismiss()
            onDismiss(true)
            showInAppReviewScreen(context)
        }

        dislikeBtn?.setOnClickListener {
            Toast.makeText(context , "Thanks for your feedback." , Toast.LENGTH_SHORT).show()
            onDismiss(false)
            reviewDialog?.dismiss()
        }

        closeBtn?.setOnClickListener {
            reviewDialog?.dismiss()
            onDismiss(false)
        }

    }

    fun showRateusDialog(
        context: Context,
        themecolor: Int,
        listOfDrawable: ArrayList<Drawable?>,
        listOfUserName: ArrayList<String>,
        listOfReviews: ArrayList<String>,
        onDismiss: (isReviewGiven:Boolean) -> Unit
    ) {
        rateusAdapter = RateusAdapter()
        rateusdialog?.let {
            if (it.isShowing) {
                it.dismiss()
            }
        }
        rateusdialog = Dialog(context)
        rateusdialog?.window?.let { window ->
            window.setBackgroundDrawableResource(android.R.color.transparent)
        }
        rateusdialog?.setContentView(R.layout.dialog_rateus)
        rateusdialog?.setCancelable(false)

        rateusdialog?.show()

        rateusdialog?.findViewById<ImageView>(R.id.arrowImg)
            ?.let { setImageViewTint(it, themecolor) }

        rateusdialog?.findViewById<TextView>(R.id.textView62)?.setTextColor(themecolor)
        rateusdialog?.findViewById<TextView>(R.id.textView63)?.setTextColor(themecolor)


        var recycerview = rateusdialog?.findViewById<RecyclerView>(R.id.recyclerView)
        var closeBtn = rateusdialog?.findViewById<ImageView>(R.id.crossBtn)

        val onestarBtn = rateusdialog?.findViewById<LottieAnimationView>(R.id.onestarBtn)
        val twostarBtn = rateusdialog?.findViewById<LottieAnimationView>(R.id.twostarBtn)
        val threestarBtn = rateusdialog?.findViewById<LottieAnimationView>(R.id.threestarBtn)
        val fourstarBtn = rateusdialog?.findViewById<LottieAnimationView>(R.id.fourstarBtn)
        val fivestarBtn = rateusdialog?.findViewById<LottieAnimationView>(R.id.fivestarBtn)


        if (twostarBtn != null) {
            if (onestarBtn != null) {
                if (threestarBtn != null) {
                    if (fourstarBtn != null) {
                        if (fivestarBtn != null) {
                            changeTexts(
                                onestarBtn,
                                twostarBtn,
                                threestarBtn,
                                fourstarBtn,
                                fivestarBtn,
                                context
                            )
                        }
                    }
                }
            }
        }


        recycerview.let {
            it?.layoutManager = GridLayoutManager(context, 1)
            it?.adapter = rateusAdapter
        }


        onestarBtn?.setOnClickListener {
            toast(context as Activity, "Thanks for your feedback.")
            onDismiss(false)
            rateusdialog?.dismiss()
        }

        twostarBtn?.setOnClickListener {
            toast(context as Activity, "Thanks for your feedback.")
            onDismiss(false)
            rateusdialog?.dismiss()
        }

        threestarBtn?.setOnClickListener {
            toast(context as Activity, "Thanks for your feedback.")
            onDismiss(false)
            rateusdialog?.dismiss()
        }

        fourstarBtn?.setOnClickListener {
            showInAppReviewScreen(context)
            onDismiss(true)
            rateusdialog?.dismiss()
        }

        fivestarBtn?.setOnClickListener {
            showInAppReviewScreen(context)
            onDismiss(true)
            rateusdialog?.dismiss()
        }

        closeBtn?.setOnClickListener {
            onDismiss(false)
            rateusdialog?.dismiss()
        }

        if (listOfDrawable.isNotEmpty())
            rateusAdapter?.setListData(
                context,
                setuprateuslist(listOfDrawable, listOfReviews, listOfUserName)
            )
    }



    fun setImageViewTint(imageView: ImageView, color: Int) {
        imageView.imageTintList = ColorStateList.valueOf(color)
    }


    private fun changeTexts(
        a1: LottieAnimationView,
        a2: LottieAnimationView,
        a3: LottieAnimationView,
        a4: LottieAnimationView,
        a5: LottieAnimationView,
        context: Context
    ) {
        handler.postDelayed({
            a1.loadAnimationFromRaw(context, R.raw.one_star)
        }, 0)
        handler.postDelayed({ a2.loadAnimationFromRaw(context, R.raw.two_star) }, 600)
        handler.postDelayed({ a3.loadAnimationFromRaw(context, R.raw.three_star) }, 1200)
        handler.postDelayed({ a4.loadAnimationFromRaw(context, R.raw.four_star) }, 1800)
        handler.postDelayed({ a5.loadAnimationFromRaw(context, R.raw.five_star) }, 2400)
    }


    fun setuprateuslist(
        listOfDrawable: ArrayList<Drawable?>,
        listOfUserName: ArrayList<String>,
        listOfReviews: ArrayList<String>
    ): ArrayList<RateUsModel> {

        val size =
            listOfDrawable.size.coerceAtMost(listOfUserName.size).coerceAtMost(listOfReviews.size)

        val rateUsModels = ArrayList<RateUsModel>()

        for (i in 0 until size) {
            val rateUsModel = RateUsModel(
                icon = listOfDrawable[i],
                name = listOfUserName[i],
                reviewtext = listOfReviews[i]
            )
            rateUsModels.add(rateUsModel)
        }

        return rateUsModels
    }

    private fun showInAppReviewScreen(context: Context) {
        val manager = ReviewManagerFactory.create(context)
        val request = manager.requestReviewFlow()
        request.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                reviewInfo = task.result
                reviewInfo?.let {
                    if (context is Activity) {
                        val flow = manager.launchReviewFlow(context, it)
                        flow.addOnCompleteListener {
                            // Handle completion of the review flow
                        }
                    } else {
                        Log.e("ReviewDialog", "Context is not an instance of Activity")
                    }
                }
            } else {
                Log.e("ReviewDialog", "Failed to request review flow: ${task.exception}")
                reviewInfo = null
            }
        }
    }

    fun LottieAnimationView.loadAnimationFromRaw(context: Context, rawResId: Int) {
        setAnimation(rawResId)
        // Optional: Configure additional properties
        repeatCount = LottieDrawable.INFINITE
        playAnimation()
    }


    fun toast(context: Activity, message: String) {
        try {
            if (context.isDestroyed || context.isFinishing) return
            if (toast != null) {
                toast?.cancel()
            }
            toast = Toast.makeText(context, message, Toast.LENGTH_SHORT)
            if (context.isDestroyed || context.isFinishing) return
            toast?.show()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}
