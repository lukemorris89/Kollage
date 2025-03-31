package dev.rarebit.kollage.ui.home

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import dev.rarebit.design.theme.paddingLarge
import dev.rarebit.kollage.ui.gallery.GalleryScreen
import dev.rarebit.kollage.ui.home.component.BottomNavBar
import dev.rarebit.kollage.ui.home.data.HomeViewData
import dev.rarebit.kollage.ui.home.data.NavigationItem
import dev.rarebit.kollage.ui.more.MoreScreen
import kotlinx.collections.immutable.persistentListOf

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeContent(
    viewData: HomeViewData,
    onViewAction: (HomeViewAction) -> Unit,
    navHostController: NavHostController,
) {
    val navigationItems = persistentListOf(
        NavigationItem.Gallery,
        NavigationItem.More,
    )
    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .safeDrawingPadding(),
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
    ) { contentPadding ->
        Crossfade(
            modifier = Modifier.padding(contentPadding),
            targetState = viewData.selectedTab,
        ) { selectedTab ->
            when (selectedTab) {
                NavigationItem.Gallery -> {
                    GalleryScreen(
                        navHostController = navHostController,
                    )
                }

                NavigationItem.More -> {
                    MoreScreen(
                        navHostController = navHostController,
                    )
                }
            }
        }
    }
}
