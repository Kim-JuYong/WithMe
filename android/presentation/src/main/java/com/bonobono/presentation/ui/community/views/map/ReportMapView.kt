package com.bonobono.presentation.ui.community.views.map

import android.os.Bundle
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.bonobono.presentation.R
import com.bonobono.presentation.ui.common.SubmitButton
import com.bonobono.presentation.ui.theme.Black_100
import com.bonobono.presentation.ui.theme.LightGray
import com.bonobono.presentation.ui.theme.White
import com.bonobono.presentation.viewmodel.CommunityViewModel
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.CameraPosition
import com.naver.maps.map.MapView
import com.naver.maps.map.compose.CameraPositionState
import com.naver.maps.map.compose.ExperimentalNaverMapApi
import com.naver.maps.map.compose.MapUiSettings
import com.naver.maps.map.compose.Marker
import com.naver.maps.map.compose.MarkerState
import com.naver.maps.map.compose.NaverMap
import com.naver.maps.map.overlay.Marker
import com.naver.maps.map.overlay.OverlayImage
import kotlinx.coroutines.launch

const val MAX_ZOOM = 21.0
const val MIN_ZOOM = 16.0

@Composable
fun ReportMapView(
    modifier: Modifier = Modifier,
    navController: NavController,
    communityViewModel: CommunityViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val coroutineScope = rememberCoroutineScope()
    val marker by remember { mutableStateOf(Marker()) }

    val mapView =
        MapView(context).apply {
            getMapAsync { naverMap ->
                naverMap.apply {
                    minZoom = MIN_ZOOM
                    maxZoom = MAX_ZOOM
                }
                // 마커 달기
                marker.position = LatLng(
                    naverMap.cameraPosition.target.latitude,
                    naverMap.cameraPosition.target.longitude
                )

                marker.apply {
                    icon = OverlayImage.fromResource(R.drawable.ic_marker_trash)
                    map = naverMap
                }

                naverMap.addOnCameraChangeListener { _, _ ->
                    marker.position = LatLng(
                        naverMap.cameraPosition.target.latitude,
                        naverMap.cameraPosition.target.longitude
                    )
                }
                naverMap.addOnCameraIdleListener {
                    marker.position = LatLng(
                        naverMap.cameraPosition.target.latitude,
                        naverMap.cameraPosition.target.longitude
                    )
                }
            }
        }


    val lifecycleObserver = remember {
        LifecycleEventObserver { _, event ->
            coroutineScope.launch {
                when (event) {
                    Lifecycle.Event.ON_CREATE -> mapView.onCreate(Bundle())
                    Lifecycle.Event.ON_START -> mapView.onStart()
                    Lifecycle.Event.ON_RESUME -> mapView.onResume()
                    Lifecycle.Event.ON_PAUSE -> mapView.onPause()
                    Lifecycle.Event.ON_STOP -> mapView.onStop()
                    Lifecycle.Event.ON_DESTROY -> mapView.onDestroy()
                    else -> throw IllegalStateException()
                }
            }
        }
    }

    // 뷰가 해제될 때 리스너 remove
    DisposableEffect(true) {
        lifecycleOwner.lifecycle.addObserver(lifecycleObserver)
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(lifecycleObserver)
        }
    }

    Box(modifier.fillMaxSize()) {
        AndroidView(factory = { mapView })
        Box(
            modifier = modifier
                .wrapContentSize()
                .padding(start = 16.dp, end = 16.dp, bottom = 48.dp)
                .align(Alignment.BottomCenter)
        ) {
            SubmitButton(
                modifier = modifier
                    .fillMaxWidth()
                    .height(48.dp),
                text = "선택",
                textStyle = TextStyle(color = White)
            ) {
                communityViewModel.setMapPosition(marker.position)
                navController.popBackStack()
            }
        }
    }
}

@OptIn(ExperimentalNaverMapApi::class)
@Composable
fun SelectedMapView(
    modifier: Modifier = Modifier,
    mapState: LatLng,
    onDeleteClicked: () -> Unit
) {
    val markerState = MarkerState().apply { position = mapState }
    val cameraPositionState = CameraPositionState().apply {
        position = CameraPosition(mapState, MIN_ZOOM)
    }
    val mapUiSettings = MapUiSettings(
        isLocationButtonEnabled = false,
        isZoomControlEnabled = false,
        isLogoClickEnabled = false,
    )
    Box(
        modifier = modifier
            .fillMaxWidth()
            .aspectRatio(1f)
            .border(
                width = 2.dp,
                color = LightGray,
                shape = RoundedCornerShape(10.dp)
            )
    ) {
        NaverMap(
            cameraPositionState = cameraPositionState,
            uiSettings = mapUiSettings
        ) {
            Marker(
                state = markerState,
                icon = OverlayImage.fromResource(R.drawable.ic_marker_trash),
            )
        }
        Box(
            modifier = modifier
                .size(80.dp)
                .align(Alignment.TopEnd)
                .padding(16.dp)
        ) {
            Box(
                modifier = modifier
                    .size(64.dp)
                    .border(width = 2.dp, color = White, shape = CircleShape)
                    .clip(CircleShape)
                    .background(color = Black_100)
            ) {
                IconButton(
                    modifier = modifier.align(Alignment.Center),
                    onClick = { onDeleteClicked() },
                ) {
                    Icon(
                        modifier = modifier.size(24.dp),
                        painter = painterResource(R.drawable.ic_delete),
                        contentDescription = "아이콘",
                        tint = White
                    )
                }
            }
        }
    }
}


@Preview
@Composable
fun PreviewReportScreen() {
    ReportMapView(navController = rememberNavController())
}