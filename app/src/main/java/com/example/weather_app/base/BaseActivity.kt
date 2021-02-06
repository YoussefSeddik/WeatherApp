package com.example.weather_app.base

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.weather_app.R
import com.example.weather_app.utils.Utils
import com.example.weather_app.utils.Utils.changeLang
import io.reactivex.disposables.CompositeDisposable

abstract class BaseActivity<P> : AppCompatActivity(), MvpViewUtils {
    protected abstract val presenter: P
    private var loadingDialog: AlertDialog? = null

    protected val compositeDisposable = CompositeDisposable()
    protected open fun fullScreen() = false
    protected abstract fun getLayoutResource(): Int

    override fun onCreate(savedInstanceState: Bundle?) {
        if (fullScreen()) {
            Utils.fullScreen(this)
        }
        super.onCreate(savedInstanceState)
        val view = layoutInflater.inflate(getLayoutResource(), null, false)
        setContentView(view)
        initViews(savedInstanceState, view)
    }

    protected abstract fun initViews(savedInstanceState: Bundle?, view: View)

    override fun onNetworkError() {
        Toast.makeText(this, getString(R.string.network_error), Toast.LENGTH_SHORT).show()
    }

    override fun hideLoading() {
        loadingDialog?.dismiss()
    }

    override fun showLoading() {
        loadingDialog = Utils.showLoadingDialog(this, false)
    }

    override fun showMessage(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }

    override fun attachBaseContext(newBase: Context?) {
        newBase?.let {
            super.attachBaseContext(changeLang("en", it))
        }
    }

}