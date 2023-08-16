package com.bonobono.data.model.registration.response

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class MemberResponse(
    val role: List<AuthoritySetResponse>,
    val name: String,
    val nickname: String,
    val phoneNumber: String,
    val username: String
) : Parcelable