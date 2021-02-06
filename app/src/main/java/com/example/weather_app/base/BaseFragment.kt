package com.example.weather_app.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.example.weather_app.R
import com.example.weather_app.utils.Utils
import io.reactivex.disposables.CompositeDisposable

abstract class BaseFragment<P> : Fragment(), MvpViewUtils {
    protected abstract val presenter: P
    private var loadingDialog: AlertDialog? = null
    protected val compositeDisposable = CompositeDisposable()

    protected abstract fun getLayoutResource(): Int

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(getLayoutResource(), container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews(savedInstanceState, view)
    }

    protected abstract fun initViews(savedInstanceState: Bundle?, view: View)

    override fun onNetworkError() {
        Toast.makeText(requireActivity(), getString(R.string.network_error), Toast.LENGTH_SHORT)
            .show()
    }

    override fun hideLoading() {
        loadingDialog?.dismiss()
    }

    override fun showLoading() {
        loadingDialog = Utils.showLoadingDialog(requireContext(), false)
    }
    override fun showMessage(msg: String) {
        Toast.makeText(requireContext(), msg, Toast.LENGTH_SHORT).show()
    }
}