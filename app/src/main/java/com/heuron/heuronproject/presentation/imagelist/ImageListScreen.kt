package com.heuron.heuronproject.presentation.imagelist

import android.app.Activity
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.heuron.heuronproject.R
import com.heuron.heuronproject.presentation.util.CustomTextField
import com.heuron.heuronproject.presentation.util.HighlightedText
import com.heuron.heuronproject.core.UiState
import com.heuron.heuronproject.ui.theme.HeuronProjectTheme

@Composable
fun ImageListScreen(
    navController: NavController, viewModel: ImageListViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val activity = context as? Activity
    var showExitDialog by remember { mutableStateOf(false) }
    val query by viewModel.query.collectAsState()
    val uiState by viewModel.imageUiState.collectAsState()

    // 뒤로가기 버튼제어(팝업창 생성)
    BackHandler {
        showExitDialog = true
    }

    if (showExitDialog) {
        AlertDialog(
            onDismissRequest = { showExitDialog = false },
            confirmButton = {
                TextButton(onClick = { activity?.finishAffinity() }) { Text(stringResource(R.string.str_yes)) }
            },
            dismissButton = {
                TextButton(onClick = {
                    showExitDialog = false
                }) { Text(stringResource(R.string.str_no)) }
            },
            title = { Text(stringResource(R.string.str_finish_title)) },
            text = { Text(stringResource(R.string.str_finish_subtitle)) })
    }

    // 에러 메시지 토스트
    LaunchedEffect(uiState) {
        if (uiState is UiState.Error) {
            val msg = (uiState as UiState.Error).message
            Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .windowInsetsPadding(WindowInsets.systemBars)
            .padding(16.dp)
    ) {
        // 검색창
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                CustomTextField(
                    label = "ID",
                    value = query.id,
                    modifier = Modifier.weight(1f),
                    onValueChange = { viewModel.setQueryChanged(id = it) })
                CustomTextField(
                    label = "Author",
                    value = query.author,
                    modifier = Modifier.weight(1f),
                    onValueChange = { viewModel.setQueryChanged(author = it) })
            }

            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                CustomTextField(
                    label = "Width",
                    value = query.width,
                    modifier = Modifier.weight(1f),
                    onValueChange = { viewModel.setQueryChanged(width = it) })
                CustomTextField(
                    label = "Height",
                    value = query.height,
                    modifier = Modifier.weight(1f),
                    onValueChange = { viewModel.setQueryChanged(height = it) })
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // 결과 상태에 따른 분기처리
        when (uiState) {
            is UiState.Loading -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }

            is UiState.Error -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(stringResource(R.string.str_not_loaded))
                }
            }

            is UiState.Success -> {
                val imageList = (uiState as UiState.Success).data

                if (imageList.isEmpty()) {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text(stringResource(R.string.str_no_matching_data))
                    }
                } else {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        itemsIndexed(imageList) { _, item ->
                            ImageListItem(
                                id = item.id,
                                author = item.author,
                                width = item.width,
                                height = item.height,
                                downloadUrl = item.imageUrl,
                                queryId = query.id,
                                queryAuthor = query.author,
                                queryWidth = query.width,
                                queryHeight = query.height,
                                onImageClick = {
                                    navController.navigate("detail/${item.id}/${Uri.encode(item.imageUrl)}")
                                })
                        }
                    }
                }
            }
        }
    }
}

// 이미지 및 이미지정보 화면
@Composable
fun ImageListItem(
    id: String,
    author: String,
    width: Int,
    height: Int,
    downloadUrl: String,
    queryId: String,
    queryAuthor: String,
    queryWidth: String,
    queryHeight: String,
    onImageClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onImageClick() }) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current).data(downloadUrl).crossfade(true).build(),
            contentDescription = "Image for $author",
            modifier = Modifier
                .size(130.dp)
                .background(Color.LightGray),
            contentScale = ContentScale.Crop
        )

        Spacer(modifier = Modifier.width(12.dp))

        Column(
            modifier = Modifier.height(130.dp), verticalArrangement = Arrangement.Center
        ) {
            HighlightedText("id", id, queryId)
            HighlightedText("Author", author, queryAuthor)
            HighlightedText("Width", width.toString(), queryWidth)
            HighlightedText("Height", height.toString(), queryHeight)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ImageListScreenPreview() {
    HeuronProjectTheme {
        Surface {
            val navController = rememberNavController()
            ImageListScreen(navController = navController)
        }
    }
}
