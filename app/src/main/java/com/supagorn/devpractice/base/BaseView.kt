package com.supagorn.devpractice.base

interface BaseView {
    fun showProgress()
    fun hideProgress()
    fun showNotFound()
    fun hideNotFound()

    interface Progress {
        fun showProgressDialog()
        fun hideProgressDialog()
        fun error(message: String)
    }
}
