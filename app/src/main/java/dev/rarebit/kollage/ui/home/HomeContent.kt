package dev.rarebit.kollage.ui.home

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import dev.rarebit.design.theme.Black
import dev.rarebit.design.theme.KollageTheme
import dev.rarebit.design.theme.White
import dev.rarebit.design.theme.paddingLarge
import dev.rarebit.design.theme.paddingSmall
import dev.rarebit.kollage.ui.home.component.BottomNavBar
import dev.rarebit.kollage.ui.home.data.HomeViewData
import dev.rarebit.kollage.ui.home.data.NavigationItem
import dev.rarebit.design.R as DR

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeContent(
    viewData: HomeViewData,
    onViewAction: (HomeViewAction) -> Unit,
) {
    val navigationItems = persistentListOf(
        NavigationItem.Gallery,
        NavigationItem.More,
    )
    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(Black),
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Black,
                    titleContentColor = White,
                    navigationIconContentColor = White,
                    actionIconContentColor = White,
                ),
                title = {},
            )
        },
        bottomBar = {
            BottomNavBar(
                modifier = Modifier.padding(
                    bottom = paddingLarge,
                    start = paddingLarge,
                    end = paddingLarge,
                ),
                selectedItem = viewData.selectedTab,
                navigationItems = navigationItems,
                onClickTab = { onViewAction(HomeViewAction.OnClickBottomNavigationTab(it)) },
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                modifier = Modifier.padding(end = paddingSmall),
                containerColor = White,
                contentColor = Black,
                shape = CircleShape,
                onClick = {}
            ) {
                Icon(
                    painter = painterResource(id = DR.drawable.ic_add),
                    contentDescription = null,
                )
            }
        }
    ) { contentPadding ->
        Crossfade(
            modifier = Modifier.padding(contentPadding),
            targetState = viewData.selectedTab,
        ) { selectedTab ->
            when (selectedTab) {
                NavigationItem.Gallery -> {}
                NavigationItem.More -> {}
            }
        }
    }
}

@Preview
@Composable
private fun HomeContentPreview() {
    KollageTheme {
        HomeContent(
            viewData = HomeViewData(),
            onViewAction = {}
        )
    }
}
