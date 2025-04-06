package dev.rarebit.kollage.ui.webview

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavHostController
import com.kevinnzou.web.WebView
import com.kevinnzou.web.rememberWebViewState
import dev.rarebit.design.theme.Black
import dev.rarebit.design.theme.White
import dev.rarebit.design.R as DR

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WebViewScreen(
    navHostController: NavHostController,
    url: String,
) {
    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .safeDrawingPadding(),
        topBar = {
            TopAppBar(
                title = {},
                navigationIcon = {
                    IconButton(
                        onClick = {
                            navHostController.popBackStack()
                        }
                    ) {
                        Icon(
                            painter = painterResource(id = DR.drawable.ic_back),
                            contentDescription = null,
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Black,
                    navigationIconContentColor = White,
                )
            )
        }
    ) { contentPadding ->
        val state = rememberWebViewState(url)
        WebView(
            modifier = Modifier
                .fillMaxSize()
                .padding(contentPadding),
            state = state,
        )
    }
}
