package com.bonobono.presentation.ui.mypage

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.bonobono.presentation.R
import com.bonobono.presentation.ui.SettingNav
import com.bonobono.presentation.ui.mypage.view.MyPageButton
import com.bonobono.presentation.ui.mypage.view.MyPageInfoCard
import com.bonobono.presentation.ui.mypage.view.MyPageProfileImg
import com.bonobono.presentation.ui.mypage.view.WaveAnimation
import com.bonobono.presentation.ui.theme.WaveBlue
import com.bonobono.presentation.ui.theme.White

@Composable
fun MainMyPageScreen(navController: NavController) {
    LazyColumn() {
        item {
            // blue wave background
            WaveBackGround(navController)
            // experience and money info box
            MyPageInfoCard(seaAnimalExp = 1000, rewardMoney = 1000)
            // rest buttons
            Spacer(modifier = Modifier.height(16.dp))
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            ) {
                MyPageButton(
                    buttonType = "포인트 상점",
                    iconName = "ic_point_store",
                    navController = navController
                )
                MyPageButton(
                    buttonType = "나의 글",
                    iconName = "ic_feed_history",
                    navController = navController
                )
                MyPageButton(
                    buttonType = "회원정보 수정",
                    iconName = "ic_profile_edit",
                    navController = navController
                )
                MyPageButton(
                    buttonType = "로그아웃",
                    iconName = "ic_logout",
                    navController = navController
                )
            }
        }


    }
}

@Composable
fun WaveBackGround(navController: NavController) {  // blue wave background, setting button, profile image, nickname
    Box(modifier = Modifier.fillMaxWidth()) {
        Image(
            painter = painterResource(id = R.drawable.blue_wave_background),
            contentDescription = "waveBackground",
            modifier = Modifier.fillMaxWidth()
        )
        WaveAnimation()
        Column(modifier = Modifier.fillMaxWidth()) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                IconButton(
                    onClick = { navController.navigate(SettingNav.route) },
                    modifier = Modifier.size(60.dp)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_setting),
                        contentDescription = "settingBtn",
                        tint = WaveBlue
                    )
                }
            }
            Column(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(32.dp))
                MyPageProfileImg()
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "test",
                    color = White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 24.sp
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MyPageScreenPreview() {
    MainMyPageScreen(navController = rememberNavController())
}