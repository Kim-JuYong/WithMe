package com.bonobono.presentation.ui

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Star
import androidx.compose.ui.graphics.vector.ImageVector
import com.bonobono.presentation.R


sealed class MainFab(val route: String, val icon: Int, val title: String) {
    object MISSION : MainFab(NavigationRouteName.MISSION, R.drawable.ic_mission, FabTitle.MISSION)
    object ENCYCLOPEDIA : MainFab(NavigationRouteName.ENCYCLOPEDIA, R.drawable.ic_ecyclopedia, FabTitle.ENCYCLOPEDIA)
    object NOTICE : MainFab(NavigationRouteName.NOTICE, R.drawable.ic_notice, FabTitle.NOTICE)
}

object FabTitle {
    const val MISSION = "미션"
    const val ENCYCLOPEDIA = "도감"
    const val NOTICE = "공지"
}