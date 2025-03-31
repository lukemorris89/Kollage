package dev.rarebit.kollage.ui.tutorial

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import dev.rarebit.design.component.ButtonColours
import dev.rarebit.design.component.HorizontalSpacer
import dev.rarebit.design.component.PrimaryButton
import dev.rarebit.design.modifier.regularScreen
import dev.rarebit.design.theme.White
import dev.rarebit.design.theme.paddingLarge
import dev.rarebit.design.theme.paddingSmall
import dev.rarebit.kollage.ui.tutorial.data.TutorialViewData

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TutorialContent(
    viewData: TutorialViewData,
    onViewAction: (TutorialViewAction) -> Unit,
) {
    val pagerState = rememberPagerState(
        pageCount = { viewData.pages.size },
        initialPage = viewData.currentPageIndex
    )
    Scaffold { contentPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .regularScreen()
                .padding(contentPadding)
        ) {
            HorizontalPager(
                modifier = Modifier.weight(1f),
                state = pagerState,
                beyondViewportPageCount = 2,
            ) { pageIndex ->
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                    Text(
                        modifier = Modifier.padding(horizontal = paddingLarge),
                        text = viewData.pages[viewData.currentPageIndex].description,
                        style = MaterialTheme.typography.titleLarge.copy(
                            color = White,
                            textAlign = TextAlign.Center,
                        )
                    )
                }
            }
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                PrimaryButton(
                    modifier = Modifier.weight(1f),
                    buttonColours = ButtonColours.PrimaryOutlined,
                    text = viewData.backCtaLabel,
                ) {
                    onViewAction(TutorialViewAction.OnClickBack)
                }
                HorizontalSpacer(paddingSmall)
                PrimaryButton(
                    modifier = Modifier.weight(1f),
                    buttonColours = ButtonColours.Secondary,
                    text = viewData.primaryCtaLabel
                ) {
                    onViewAction(TutorialViewAction.OnClickPrimaryCta)
                }
            }
        }
    }
}
