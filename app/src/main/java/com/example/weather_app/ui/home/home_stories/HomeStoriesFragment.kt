package com.example.weather_app.ui.home.home_stories

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.view.WindowManager
import androidx.core.content.FileProvider
import androidx.navigation.fragment.findNavController
import com.example.weather_app.R
import com.example.weather_app.base.BaseFragment
import com.example.weather_app.databinding.BaseToolbarBinding
import com.example.weather_app.databinding.FragmentHomeStoriesBinding
import com.example.weather_app.domain.models.WeatherStoryModel
import com.example.weather_app.ui.home.adapter.HomeStoriesAdapter
import com.example.weather_app.ui.home.adapter.HomeStoriesListener
import com.example.weather_app.utils.Utils.createImageFile
import com.example.weather_app.utils.Utils.shareToFaceBook
import com.example.weather_app.utils.Utils.shareToTwitter
import com.example.weather_app.utils.secretMe
import com.example.weather_app.utils.showMe
import org.koin.android.ext.android.inject

class HomeStoriesFragment : BaseFragment<HomeStoriesFragmentContract.Presenter>(),
    HomeStoriesListener,
    HomeStoriesFragmentContract.View {
    lateinit var fragmentHomeStoriesBinding: FragmentHomeStoriesBinding
    private val homeStoriesAdapter = HomeStoriesAdapter(this)
    override val presenter: HomeStoriesFragmentContract.Presenter by inject()
    private var pickedPhotoPath: String? = null

    override fun getLayoutResource() = R.layout.fragment_home_stories

    override fun initViews(savedInstanceState: Bundle?, view: View) {
        fragmentHomeStoriesBinding = FragmentHomeStoriesBinding.bind(view)
        setUpToolBar()
        presenter.attachView(this, lifecycle)
        presenter.loadSavedWeatherStories()
        setUpController()
    }

    private fun setUpToolBar() {
        fragmentHomeStoriesBinding.homeToolBar.titleTxtView.text =
            getString(R.string.your_weather_stories)
    }

    private fun setUpController() {
        fragmentHomeStoriesBinding.addImageFloatingButton.setOnClickListener {
            pickImageFromCamera()
        }
    }

    private fun pickImageFromCamera() {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            takePictureIntent.resolveActivity(requireActivity().packageManager)?.also {
                createImageFile(requireContext())?.also {
                    pickedPhotoPath = it.absolutePath
                    val photoUri: Uri = FileProvider.getUriForFile(
                        requireContext(),
                        "${requireActivity().packageName}.provider",
                        it
                    )
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri)
                    startActivityForResult(takePictureIntent, REQUEST_CODE_IMAGE)
                }

            }
        }
    }

    private fun openWeatherDetailsFragment(path: String) {
        val action =
            HomeStoriesFragmentDirections.actionHomeStoriesFragmentToWeatherDetailsFragment(path)
        findNavController().navigate(action)
    }


    /**
     * get weather details callBacks
     * */
    override fun onLoadWeatherStoriesSuccess(weatherStoryDetailsList: MutableList<WeatherStoryModel>) {
        setUpRecyclerView(weatherStoryDetailsList)
        checkDataEmptyState()
    }

    override fun onDeleteWeatherStorySuccess(storyId: String) {
        homeStoriesAdapter.removeStory(storyId)
        checkDataEmptyState()
    }

    private fun checkDataEmptyState() =
        with(fragmentHomeStoriesBinding) {
            if (homeStoriesAdapter.weatherStories.isEmpty()) {
                emptyConstraintLayout.showMe()
            } else {
                emptyConstraintLayout.secretMe()
            }
        }

    private fun setUpRecyclerView(weatherStoryDetailsList: MutableList<WeatherStoryModel>) {
        fragmentHomeStoriesBinding.weatherStatusRecyclerView.adapter = homeStoriesAdapter
        homeStoriesAdapter.swapData(weatherStoryDetailsList)
    }

    /**
     * weather adapter callBacks
     * */
    override fun onShareWeatherWithFacebook(imagePath: String) {
        shareToFaceBook(requireActivity(), imagePath)
    }

    override fun onShareWeatherWithTwitter(imagePath: String) {
        shareToTwitter(requireActivity(), imagePath)
    }

    override fun onDeleteStory(storyId: String) {
        presenter.deleteWeatherStory(storyId)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when {
            requestCode == REQUEST_CODE_IMAGE && resultCode == Activity.RESULT_OK -> {
                pickedPhotoPath?.let { filePath ->
                    openWeatherDetailsFragment(filePath)
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        activity?.window?.clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
        activity?.window?.setFlags(
            WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN
        )
    }

    companion object {
        private const val REQUEST_CODE_IMAGE = 3
    }
}