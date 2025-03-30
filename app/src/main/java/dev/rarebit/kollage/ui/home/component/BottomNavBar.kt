package dev.rarebit.kollage.ui.home.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.rarebit.design.theme.Black
import dev.rarebit.design.theme.KollageTheme
import dev.rarebit.design.theme.White
import dev.rarebit.design.theme.paddingLarge
import dev.rarebit.kollage.ui.home.data.NavigationItem

@Composable
fun BottomNavBar(
    modifier: Modifier = Modifier,
    selectedItem: NavigationItem,
    navigationItems: List<NavigationItem>,
    onClickTab: (NavigationItem) -> Unit,
) {
    Row(
        modifier = modifier
            .clip(RoundedCornerShape(100.dp))
            .background(White),
        horizontalArrangement = Arrangement.SpaceAround,
    ) {
        navigationItems.forEach {
            NavigationBarItem(
                modifier = Modifier.height(54.dp),
                selected = selectedItem == it,
                onClick = { onClickTab(it) },
                icon = {
                    Icon(
                        painter = painterResource(id = it.iconRes),
                        contentDescription = null,
                    )
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = White,
                    selectedTextColor = Black,
                    indicatorColor = Black,
                    unselectedIconColor = Black,
                    unselectedTextColor = Black,
                )

            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun BottomNavBarPreview() {
    KollageTheme {
        BottomNavBar(
            selectedItem = NavigationItem.Gallery,
            navigationItems = NavigationItem.entries,
            onClickTab = {},

        )
    }
}