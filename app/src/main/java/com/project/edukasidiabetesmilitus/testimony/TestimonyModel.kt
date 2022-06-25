package com.project.edukasidiabetesmilitus.testimony

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class TestimonyModel(
    var uid : String? = null,
    var title : String? = null,
    var titleTemp : String? = null,
    var description : String? = null,
    var name : String? = null,
    var avatar : String? = null,
    var image : String? = null,
    var status : String? = null,
    var commentCount : String? = null,
) : Parcelable
