package com.example.weather_app.ui.home.weather_details

import android.content.Intent
import android.location.Location
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.weather_app.R
import com.example.weather_app.base.BaseFragment
import com.example.weather_app.databinding.FragmentWeatherDetailsBinding
import com.example.weather_app.domain.inputs.GetWeatherDetailsInput
import com.example.weather_app.domain.inputs.SaveWeatherStoryInput
import com.example.weather_app.domain.mapper.convertToSaveWeatherInput
import com.example.weather_app.domain.models.WeatherModel
import com.example.weather_app.domain.models.WindDirectionsEnum
import com.example.weather_app.utils.*
import com.example.weather_app.utils.AnimationUtils.animateHideShareLayout
import com.example.weather_app.utils.AnimationUtils.animateHideStoryActions
import com.example.weather_app.utils.AnimationUtils.animateShowWeatherLayout
import com.example.weather_app.utils.AnimationUtils.animateShowShareLayout
import com.example.weather_app.utils.AnimationUtils.animateShowSaveStory
import com.example.weather_app.utils.Utils.FULL_DATE_FORMAT
import com.example.weather_app.utils.Utils.longToString
import mumayank.com.airlocationlibrary.AirLocation
import org.koin.android.ext.android.inject
import java.io.Serializable
import java.util.*


class WeatherDetailsFragment : BaseFragment<WeatherDetailsFragmentContract.Presenter>(),
    AirLocation.Callback,
    WeatherDetailsFragmentContract.View {
    private lateinit var fragmentWeatherDetailsBinding: FragmentWeatherDetailsBinding
    override val presenter: WeatherDetailsFragmentContract.Presenter by inject()
    private val airLocation by lazy { AirLocation(requireActivity(), this, true) }
    private val arguments: WeatherDetailsFragmentArgs by navArgs()
    private var weatherDetailsModel: WeatherModel? = null
    private var generatedStoryId = ""
    private var generatedStoryImagePath = ""
    private lateinit var entranceModel: WeatherDetailsEntranceModel


    override fun getLayoutResource() = R.layout.fragment_weather_details

    override fun initViews(savedInstanceState: Bundle?, view: View) {
        presenter.attachView(this, lifecycle)
        fragmentWeatherDetailsBinding = FragmentWeatherDetailsBinding.bind(view)
        setUpArguments()
        setUpEntranceTypeViews()
        setUpController()
    }

    private fun setUpArguments() {
        entranceModel = arguments.entranceModel
        fragmentWeatherDetailsBinding.pickedPhotoImageView.loadImage(entranceModel.imagePath)
    }

    private fun setUpEntranceTypeViews() = with(fragmentWeatherDetailsBinding) {
        if (entranceModel.entranceType == EntranceType.ADD_STORY) {
            storyActionsLayout.showMe()
        } else {
            generatedStoryImagePath = entranceModel.imagePath
            generatedStoryId = entranceModel.generatedStoryId
            storyActionsLayout.secretMe()
            animateShowShareLayout()
        }
    }

    private fun setUpController() = with(fragmentWeatherDetailsBinding) {
        loadWeatherDetailsButton.setOnClickListener {
            checkLocationPermissions()
        }
        pickedPhotoImageView.setOnClickListener {
            determineShareLayoutVisibility()
        }
        saveDataToImageFloatingButton.setOnClickListener {
            checkStoragePermissions()
        }
        facebookShareImageView.setOnClickListener {
            Utils.shareToFaceBook(requireActivity(), generatedStoryImagePath)
        }
        twitterShareImageView.setOnClickListener {
            Utils.shareToTwitter(requireActivity(), generatedStoryImagePath)
        }
        deleteStatusImageView.setOnClickListener {
            presenter.deleteWeatherStory(generatedStoryId)
        }
    }

    private fun checkLocationPermissions() {
        if (Permission.hasLocationPermissions(requireActivity())) {
            airLocation.start()
        } else {
            requestLocationPermissions()
        }
    }

    private fun requestLocationPermissions() {
        Permission.checkLocationPermission(requireActivity(), object : Permission.Callback {
            override fun isPermissionAccepted(isAccepted: Boolean) {
                if (isAccepted) {
                    airLocation.start()
                } else {
                    showMessage(getString(R.string.permission_denied))
                }
            }

        })
    }

    private fun checkStoragePermissions() {
        if (Permission.hasStoragePermissions(requireActivity())) {
            saveWeatherStory()
        } else {
            requestStoragePermissions()
        }
    }

    private fun requestStoragePermissions() {
        Permission.checkStoragePermission(requireActivity(), object : Permission.Callback {
            override fun isPermissionAccepted(isAccepted: Boolean) {
                if (isAccepted) {
                    saveWeatherStory()
                } else {
                    showMessage(getString(R.string.permission_denied))
                }
            }

        })
    }

    private fun saveWeatherStory() = with(fragmentWeatherDetailsBinding) {
        val generatedBitmap = weatherDetailsConstraintLayout.generateBitmap()
        val generatedImageFile = Utils.createFileFromBitmap(requireContext(), generatedBitmap)
        generatedImageFile?.path?.let {
            val saveWeatherInput = weatherDetailsModel?.convertToSaveWeatherInput(it)
            saveWeatherInput?.let { it1 -> presenter.saveWeatherStory(it1) }
        }

    }

    private fun determineShareLayoutVisibility() = with(fragmentWeatherDetailsBinding) {
        if (storyActionsLayout.isVisible.not()) {
            if (shareStoryLayout.isVisible) {
                animateHideShareLayout()
            } else {
                animateShowShareLayout()
            }
        }
    }

    private fun setUpViewsData(weatherModel: WeatherModel) = with(fragmentWeatherDetailsBinding) {
        val strWindDirection = resources.getStringArray(R.array.WindDirections)
            .elementAt(WindDirectionsEnum.values().indexOf(weatherModel.windDirection))
        locationTextView.text = "${weatherModel.countryCode}, ${weatherModel.cityName}"
        tempTextView.text = "${weatherModel.temp.toString()} °C"
        maxMinTemp.text = "${weatherModel.maxTemp}°/${weatherModel.minTemp}°"
        weatherDescTextView.text = "${weatherModel.tempDescription}"
        updatedAtTextView.text =
            longToString(weatherModel.updatedAt ?: 0L, FULL_DATE_FORMAT)
        windSpeedTextView.text = "${weatherModel.windSpeed}m/s $strWindDirection"
        humidityTextView.text = "${weatherModel.humidity}%"
        pressureTextView.text = "${weatherModel.pressure}hPa"
        feelsLikeTextView.text = "${getString(R.string.feels_like)} ${weatherModel.feelsLike} °C"
        weatherIconImageView.loadImage(weatherModel.tempIcon?.let { it1 ->
            ContextCompat.getDrawable(
                requireContext(),
                it1
            )
        })
    }

    /**
     * get weather details callBacks
     * */
    override fun onGetWeatherDetailsSuccess(weatherModel: WeatherModel) {
        weatherDetailsModel = weatherModel
        setUpViewsData(weatherModel)
        fragmentWeatherDetailsBinding.loadWeatherDetailsButton.text =
            getString(R.string.update_weather_data)
        fragmentWeatherDetailsBinding.animateShowWeatherLayout()
        fragmentWeatherDetailsBinding.animateShowSaveStory()
    }

    override fun onSaveWeatherStorySuccess(saveWeatherStoryInput: SaveWeatherStoryInput) {
        showMessage(getString(R.string.weather_story_is_saved))
        generatedStoryImagePath = saveWeatherStoryInput.thumbnailPath
        generatedStoryId = saveWeatherStoryInput.storyId
        fragmentWeatherDetailsBinding.animateHideStoryActions()
        fragmentWeatherDetailsBinding.animateShowShareLayout()
    }

    override fun onDeleteWeatherStorySuccess(storyId: String) {
        showMessage(getString(R.string.weather_story_is_deleted))
        findNavController().navigate(R.id.action_weatherDetailsFragment_to_homeStoriesFragment)
    }


    override fun onSuccess(locations: ArrayList<Location>) {
        val lastLocationUpdate = locations.first()
        val getWeatherDetailsInput = GetWeatherDetailsInput(
            lastLocationUpdate.latitude,
            lastLocationUpdate.longitude
        )
        presenter.getWeatherDetails(getWeatherDetailsInput)
    }

    override fun onFailure(locationFailedEnum: AirLocation.LocationFailedEnum) {
        onNetworkError()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        airLocation.onActivityResult(requestCode, resultCode, data)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        airLocation.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    override fun onResume() {
        super.onResume()
        activity?.window?.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
    }

    companion object {
        data class WeatherDetailsEntranceModel(
            val imagePath: String,
            val generatedStoryId: String,
            val entranceType: EntranceType
        ) : Serializable

        enum class EntranceType {
            ADD_STORY,
            PREVIEW_STORY
        }
    }

}