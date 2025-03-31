package dev.rarebit.kollage.onboarding.ui.tutorial

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandHorizontally
import androidx.compose.animation.shrinkHorizontally
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import dev.rarebit.design.component.PrimaryButton
import dev.rarebit.design.modifier.regularScreen
import dev.rarebit.kollage.onboarding.ui.tutorial.data.TutorialViewData
import dev.rarebit.design.R as DR

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
    Scaffold(
        topBar = {
            TopAppBar(
                title = { },
                navigationIcon = {
                    IconButton(
                        onClick = {
                            onViewAction(TutorialViewAction.OnClickBack)
                        }
                    ) {
                        Icon(
                            painter = painterResource(
                                id = DR.drawable.ic_back,
                            ),
                            contentDescription = null,
                        )
                    }
                },
            )
        }
    ) { contentPadding ->
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
            }
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                AnimatedVisibility(
                    visible = viewData.showSkip,
                    enter = expandHorizontally(
                        expandFrom = Alignment.Start
                    ),
                    exit = shrinkHorizontally(
                        shrinkTowards = Alignment.Start
                    )
                ) {
                    PrimaryButton(
                        modifier = Modifier.weight(1f),
                        text = viewData.skipCtaLabel,
                    ) {
                        onViewAction(TutorialViewAction.OnClickSkipCta)
                    }
                }
                PrimaryButton(
                    modifier = Modifier.weight(1f),
                    text = viewData.primaryCtaLabel
                ) {
                    onViewAction(TutorialViewAction.OnClickPrimaryCta)
                }
            }
        }
    }
}
