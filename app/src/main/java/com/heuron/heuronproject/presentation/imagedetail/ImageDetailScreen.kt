package com.heuron.heuronproject.presentation.imagedetail

import androidx.compose.foundation.gestures.rememberTransformableState
import androidx.compose.foundation.gestures.transformable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ColorMatrix
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.heuron.heuronproject.R
import com.heuron.heuronproject.ui.theme.HeuronProjectTheme

@Composable
fun ImageDetailScreen(
    imageId: String,
    imageUrl: String,
    viewModel: ImageDetailViewModel,
    onBackClick: () -> Unit
) {
    val grayScaleMap by viewModel.grayScaleMap.collectAsState()
    val isGrayscale = grayScaleMap[imageId] ?: false
    val colorMatrix = if (isGrayscale) {
        ColorMatrix().apply { setToSaturation(0f) }
    } else {
        ColorMatrix().apply { setToSaturation(1f) }
    }

    // 이미지 상세정보 및 버튼제어(흑백&컬러보기, 이미지초기화)
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .windowInsetsPadding(WindowInsets.systemBars)
    ) {
        IconButton(
            onClick = onBackClick,
            modifier = Modifier.size(48.dp)
        ) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = stringResource(R.string.str_back)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        var scale by remember { mutableFloatStateOf(1f) }
        var rotation by remember { mutableFloatStateOf(0f) }
        var offset by remember { mutableStateOf(Offset.Zero) }
        val state = rememberTransformableState { zoomChange, offsetChange, rotationChange ->
            scale *= zoomChange
            rotation += rotationChange
            offset += offsetChange
        }
        val isPortrait = LocalContext.current.resources.configuration.orientation == android.content.res.Configuration.ORIENTATION_PORTRAIT
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .then(if (isPortrait) Modifier.height(300.dp)
                    else Modifier.weight(1f)
                )
                .padding(horizontal = 10.dp)
                .clipToBounds()
                .graphicsLayer(
                    scaleX = scale,
                    scaleY = scale,
                    rotationZ = rotation,
                    translationX = offset.x,
                    translationY = offset.y
                )
                .transformable(state = state)
        ) {
            AsyncImage(
                model = imageUrl,
                contentDescription = "Detailed Image",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop,
                colorFilter = ColorFilter.colorMatrix(colorMatrix)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Button(onClick = { viewModel.toggleGrayscale(imageId) }) {
                Text(if (isGrayscale) stringResource(R.string.str_color) else stringResource(R.string.str_grayscale))
            }

            Button(onClick = {
                scale = 1f
                rotation = 0f
                offset = Offset.Zero
            }) {
                Text(stringResource(R.string.str_image_reset))
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ImageDetailScreenPreview() {
    HeuronProjectTheme {
        Surface {
            ImageDetailScreen("","", hiltViewModel(), {})
        }
    }
}