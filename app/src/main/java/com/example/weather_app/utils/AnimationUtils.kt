package com.example.weather_app.utils

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.view.animation.AccelerateInterpolator
import androidx.core.view.isVisible
import com.example.weather_app.databinding.ActivityMainBinding
import com.example.weather_app.databinding.FragmentHomeStoriesBinding
import com.example.weather_app.databinding.FragmentWeatherDetailsBinding
import com.mikhaellopez.rxanimation.*
import io.reactivex.disposables.CompositeDisposable

object AnimationUtils {

    /**
     * Main activity animations
     * */
    fun ActivityMainBinding.startSplashAnimation(
        compositeDisposable: CompositeDisposable
    ) {
        compositeDisposable.add(
            RxAnimation.sequentially(
                RxAnimation.together(
                    buildingImageView.translationY(500f),
                    circleCloudImageView.fadeOut(0L),
                    buildingImageView.fadeOut(0L),
                    leftCloudImageView.translationX(-500F, 0L),
                    rightCloudImageView.translationX(500f, 0L),
                    centerCloudImageView.fadeOut(0L),
                    welcomeTextView.fadeOut(0L),
                    continueButton.translationY(500F, 0),
                    continueButton.fadeOut(0L),
                ),

                RxAnimation.together(
                    buildingImageView.fadeIn(1000L),
                    buildingImageView.translationY(-1f),
                ),

                RxAnimation.together(
                    circleCloudImageView.fadeIn(1000L),
                    circleCloudImageView.translationY(-50F, 1000L)
                ),

                RxAnimation.together(
                    leftCloudImageView.translationX(-15f, 1000L),
                    rightCloudImageView.translationX(25f, 1000L)
                ),
                centerCloudImageView.fadeIn(500L),
            ).doOnTerminate {
                endSplashAnimation(compositeDisposable)
            }.subscribe()
        )
    }

    private fun ActivityMainBinding.endSplashAnimation(
        compositeDisposable: CompositeDisposable
    ) {
        compositeDisposable.add(
            RxAnimation.sequentially(
                RxAnimation.together(
                    buildingImageView.fadeOut(300L),
                    buildingImageView.translationY(100f),
                ),

                RxAnimation.together(
                    circleCloudImageView.fadeOut(300L),
                    circleCloudImageView.translationY(500F, 300L)
                ),

                RxAnimation.together(
                    leftCloudImageView.translationX(500f, 300L),
                    rightCloudImageView.translationX(-500f, 300L)
                ),

                RxAnimation.together(
                    centerCloudImageView.fadeOut(300L),
                    centerCloudImageView.translationY(-1000F, 400)
                ),
            ).doOnTerminate {
                startWelcomeAnimation(compositeDisposable)
            }.subscribe()
        )
    }

    private fun ActivityMainBinding.startWelcomeAnimation(
        compositeDisposable: CompositeDisposable
    ) {
        compositeDisposable.add(
            RxAnimation.sequentially(
                RxAnimation.together(
                    centerCloudImageView.translationY(-1000F, 0),
                    centerCloudImageView.fadeOut(0L),
                ),
                RxAnimation.together(
                    centerCloudImageView.translationY(0F, 1000),
                    centerCloudImageView.fadeIn(500),
                ),
                RxAnimation.together(
                    welcomeTextView.fadeIn(500),
                    continueButton.translationY(0F, 1000),
                    continueButton.fadeIn(500)
                )
            ).subscribe()
        )
    }

    /**
     * Weather details animations
     * */
    fun FragmentWeatherDetailsBinding.animateShowWeatherLayout() {
        if (weatherDetailsLayout.isVisible.not()) {
            weatherDetailsLayout.translationY = -1000F
            weatherDetailsLayout.animate().translationY(0F).setDuration(600)
                .setInterpolator(AccelerateInterpolator())
                .setListener(object : AnimatorListenerAdapter() {
                    override fun onAnimationStart(animation: Animator?) {
                        super.onAnimationStart(animation)
                        weatherDetailsLayout.showMe()
                    }
                })
        }
    }

    fun FragmentWeatherDetailsBinding.animateShowSaveStory() {
        if (saveDataToImageFloatingButton.isVisible.not()) {
            saveDataToImageFloatingButton.translationX = 200F
            saveDataToImageFloatingButton.animate().translationX(0F).setDuration(400)
                .setInterpolator(AccelerateInterpolator())
                .setListener(object : AnimatorListenerAdapter() {
                    override fun onAnimationStart(animation: Animator?) {
                        super.onAnimationStart(animation)
                        saveDataToImageFloatingButton.showMe()
                    }
                })
        }
    }

    fun FragmentWeatherDetailsBinding.animateHideStoryActions() {
        storyActionsLayout.animate().translationY(200F).setDuration(200)
            .setListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator?) {
                    super.onAnimationEnd(animation)
                    storyActionsLayout.secretMe()
                }
            })
    }

    fun FragmentWeatherDetailsBinding.animateShowStoryActions() {
        storyActionsLayout.translationY = 500F
        storyActionsLayout.animate().translationY(0F).setDuration(200)
            .setListener(object : AnimatorListenerAdapter() {
                override fun onAnimationStart(animation: Animator?) {
                    storyActionsLayout.showMe()
                }
            })
    }


    fun FragmentWeatherDetailsBinding.animateHideShareLayout() {
        shareStoryLayout.animate().translationY(500F).setDuration(200)
            .setListener(object :
                AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator?) {
                    super.onAnimationEnd(animation)
                    shareStoryLayout.secretMe()
                }
            })
    }

    fun FragmentWeatherDetailsBinding.animateShowShareLayout() {
        shareStoryLayout.translationY = 500F
        shareStoryLayout.animate().translationY(0F).setDuration(200)
            .setInterpolator(AccelerateInterpolator()).setListener(object :
                AnimatorListenerAdapter() {
                override fun onAnimationStart(animation: Animator?) {
                    super.onAnimationStart(animation)
                    shareStoryLayout.showMe()
                }
            })
    }

    fun FragmentHomeStoriesBinding.animateShowPickCamera() {
        addImageFloatingButton.translationY = 500F
        addImageFloatingButton.animate().translationY(0F).setDuration(200)
            .setInterpolator(AccelerateInterpolator()).setListener(object :
                AnimatorListenerAdapter() {
                override fun onAnimationStart(animation: Animator?) {
                    super.onAnimationStart(animation)
                    addImageFloatingButton.showMe()
                }
            })
    }

    fun FragmentHomeStoriesBinding.animateHidePickCamera() {
        addImageFloatingButton.animate().translationY(500F).setDuration(200)
            .setInterpolator(AccelerateInterpolator()).setListener(object :
                AnimatorListenerAdapter() {
                override fun onAnimationStart(animation: Animator?) {
                    super.onAnimationStart(animation)
                    addImageFloatingButton.showMe()
                }
            })
    }
}