package com.wangxingxing.wanandroidcompose.ui.main.project

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.btpj.lib_base.base.BaseViewModel
import com.wangxingxing.wanandroidcompose.data.bean.ProjectTitle
import com.wangxingxing.wanandroidcompose.data.remote.DataRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * author : 王星星
 * date : 2025/4/21 11:22
 * email : 1099420259@qq.com
 * description :
 */
@HiltViewModel
class ProjectViewModel @Inject constructor() : BaseViewModel<Unit>() {

    /** 项目标题列表LiveData */
    private val _projectTitleListLiveData = MutableLiveData<List<ProjectTitle>?>()
    val projectTitleListLiveData: LiveData<List<ProjectTitle>?> = _projectTitleListLiveData

    /** 请求项目标题列表 */
    fun fetchProjectTitleList() {
        launch({
            handleRequest(
                DataRepository.getProjectTitleList()
            ) {
                val list = ArrayList<ProjectTitle>()
                list.add(
                    ProjectTitle(
                        author = "",
                        children = ArrayList(),
                        courseId = 0,
                        cover = "",
                        desc = "",
                        id = -1,
                        lisense = "",
                        lisenseLink = "",
                        name = "最新项目",
                        order = 0,
                        parentChapterId = 0,
                        userControlSetTop = true,
                        visible = 0
                    )
                )
                list.addAll(it.data)
                _projectTitleListLiveData.value = list
            }
        })
    }

}