package com.yxlh.androidxy.demo.ui.tag

/**
 *@describe 测试数据
 *@author zwl
 *@date on 2022/8/28
 */
object TagDataHelper {

    fun tagList(): MutableList<String> {
        return mutableListOf(
            "电子商务", "游戏", "媒体", "广告营销", "数据服务", "医疗健康", "生活服务", "O2O",
            "旅游", "分类信息", "音乐/视频/阅读", "在线教育", "社交网络", "人力资源服务", "企业服务",
            "信息安全", "智能硬件", "移动互联,网", "互联网", "计算机软件", "通信/网络设备", "广告/公关/会展",
            "互联网金融", "物流/仓储", "贸易/进出口", "咨询", "工程施工", "汽车生产", "其他行业",
        )
    }

    fun tagBeanList(): MutableList<TagBean> {
        return mutableListOf(
            TagBean("行业筛选", tagList()),
            TagBean("职业筛选", tagList()),
            TagBean("方向筛选", tagList()),
            TagBean("市场筛选", tagList()),
            TagBean("人才筛选", tagList()),
        )
    }

}

data class TagBean(var title: String, var tags: MutableList<String>)