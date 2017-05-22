package com.happy.girls.bean

/**
 * Created by ChangQin on 2017/5/22 0022.
 */
data class Girl(val _id: String, val createdAt: String, val desc: String, val publishedAt: String, val source: String,
                val type: String, val url: String, val used: Boolean, val who: String)
data class Girls(val error:Boolean,val results:List<Girl>)