package com.wangxingxing.wanandroidcompose.ui.setting

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.blankj.utilcode.util.ToastUtils
import com.btpj.lib_base.BaseApp.Companion.appContext
import com.btpj.lib_base.base.BaseViewModel
import com.btpj.lib_base.utils.AppUtil
import com.btpj.lib_base.utils.CacheUtil
import com.tencent.bugly.beta.Beta
import com.wangxingxing.wanandroidcompose.R
import com.wangxingxing.wanandroidcompose.data.local.UserManager
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * author : 王星星
 * date : 2025/4/23 10:07
 * email : 1099420259@qq.com
 * description :
 */
@HiltViewModel
class SettingViewModel @Inject constructor() : BaseViewModel<Unit>() {

    private val _showLogoutBtn = MutableLiveData<Boolean>()
    val showLogoutBtn: LiveData<Boolean> = _showLogoutBtn

    private val _haveNewVersion = MutableLiveData<Boolean>()
    val haveNewVersion: LiveData<Boolean> = _haveNewVersion

    private val _cacheSize = MutableLiveData<String>()
    val cacheSize: LiveData<String> = _cacheSize

    private val _appVersionName = MutableLiveData<String>()
    val appVersionName: LiveData<String> = _appVersionName

    fun start() {
        _showLogoutBtn.value = UserManager.isLogin()
        _cacheSize.value = CacheUtil.getTotalCacheSize(appContext)
        _appVersionName.value = AppUtil.getAppVersionName(appContext)
        checkAppUpdate()
    }

    /**
     * APP更新检查
     *
     * @param isManual 是否手动检查，默认为false
     */
    fun checkAppUpdate(isManual: Boolean = false) {
        Beta.checkUpgrade(isManual, !isManual)
        val upgradeInfo = Beta.getUpgradeInfo()
        if (upgradeInfo == null) {
            _haveNewVersion.value = false
            if (isManual) {
                ToastUtils.showShort(R.string.toast_latest_version)
            }
        } else {
            _haveNewVersion.value = true
        }
    }

    fun clearAllCache() {
        CacheUtil.clearAllCache(appContext)
        _cacheSize.value = CacheUtil.getTotalCacheSize(appContext)
    }
}