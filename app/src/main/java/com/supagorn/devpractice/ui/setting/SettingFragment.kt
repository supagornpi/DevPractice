package com.supagorn.devpractice.ui.setting

import android.support.v7.widget.LinearLayoutManager
import com.supagorn.devpractice.R
import com.supagorn.devpractice.customs.AbstractFragment
import com.supagorn.devpractice.ui.setting.adapter.SettingAdapter
import kotlinx.android.synthetic.main.fragment_setting.*

class SettingFragment : AbstractFragment(), SettingContract.View {

    private val settingAdapter: SettingAdapter = SettingAdapter()
    private var presenter: SettingContract.Presenter? = null

    override fun setLayoutView(): Int = R.layout.fragment_setting

    override fun setupView() {
        setTitle(R.string.title_setting)
        presenter = SettingPresenter(this)
        initRecyclerView()
        (presenter as SettingPresenter).start()
    }

    private fun initRecyclerView() {
        recyclerView.layoutManager = LinearLayoutManager(activity)
        recyclerView.adapter = settingAdapter
    }

    override fun showSetting(settings: MutableList<Settings>) {
        settingAdapter.setSettings(settings)
    }
}