package com.togedo.app.ui.onboarding

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.koinScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.togedo.app.designsystem.AppTheme
import com.togedo.app.designsystem.Spacing
import com.togedo.app.designsystem.components.Button
import com.togedo.app.designsystem.components.ButtonVariant
import com.togedo.app.designsystem.components.Icon
import com.togedo.app.designsystem.components.Text
import compose.icons.FeatherIcons
import compose.icons.feathericons.ArrowRight
import compose.icons.feathericons.Heart
import kotlinx.coroutines.launch
import org.jetbrains.compose.ui.tooling.preview.Preview

class OnboardingScreen : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val screenModel = koinScreenModel<OnboardingScreenModel>()
        val state by screenModel.state.collectAsState()

        OnboardingScreenContent(
            state = state,
            onPageChanged = screenModel::onPageChanged,
            onSkip = { screenModel.onSkip(navigator) },
            onStart = { screenModel.onStart(navigator) },
            onNextPage = screenModel::onNextPage,
        )
    }
}

@Composable
private fun OnboardingScreenContent(
    state: OnboardingScreenModel.State,
    onPageChanged: (Int) -> Unit,
    onSkip: () -> Unit,
    onStart: () -> Unit,
    onNextPage: () -> Unit,
) {
    val pagerState = rememberPagerState(pageCount = { state.pages.size })
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(pagerState.currentPage) {
        onPageChanged(pagerState.currentPage)
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(AppTheme.colors.background)
    ) {
        HorizontalPager(
            state = pagerState,
            modifier = Modifier.fillMaxSize(),
        ) { pageIndex ->
            val isLastPage = pageIndex == state.pages.size - 1
            OnboardingPageContent(
                page = pageIndex,
                isLastPage = isLastPage,
                onSkip = onSkip,
                onNext = {
                    if (isLastPage) {
                        onStart()
                    } else {
                        coroutineScope.launch {
                            pagerState.animateScrollToPage(pageIndex + 1)
                            onNextPage()
                        }
                    }
                },
            )
        }
    }
}

@Composable
private fun OnboardingPageContent(
    page: Int,
    isLastPage: Boolean,
    onSkip: () -> Unit,
    onNext: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = Spacing.spacing5),
    ) {
        Spacer(modifier = Modifier.height(Spacing.spacing14))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End,
        ) {
            if (!isLastPage) {
                Text(
                    text = "Skip",
                    style = AppTheme.typography.label1,
                    color = AppTheme.colors.textSecondary,
                    modifier = Modifier
                        .clickable(onClick = onSkip)
                        .padding(Spacing.spacing2),
                )
            }
        }

        CirclesHero(page = page)

        Spacer(modifier = Modifier.height(Spacing.spacing5))

        val headlines = listOf(
            "Made for two.",
            "Plan together.",
            "Together, but\nnever distant.",
        )
        val descriptions = listOf(
            "A shared space just for the two of you — ideas, plans and memories in one place.",
            "Add activities, set dates and keep track of your shared bucket list.",
            "Togedo is a shared space for the two of you — plan moments, capture ideas, stay close.",
        )

        Text(
            text = headlines[page],
            style = AppTheme.typography.display1,
            color = AppTheme.colors.text,
        )

        Spacer(modifier = Modifier.height(Spacing.spacing3))

        Text(
            text = descriptions[page],
            style = AppTheme.typography.body1,
            color = AppTheme.colors.textSecondary,
        )

        Spacer(modifier = Modifier.height(Spacing.spacing7))

        DotIndicator(
            totalDots = 3,
            selectedIndex = page,
        )

        Spacer(modifier = Modifier.height(Spacing.spacing5))

        Button(
            onClick = onNext,
            modifier = Modifier.fillMaxWidth(),
            variant = ButtonVariant.Primary,
        ) {
            Text(
                text = if (isLastPage) "Let's go" else "Next",
                style = AppTheme.typography.button,
            )
            Spacer(modifier = Modifier.width(Spacing.spacing2))
            Icon(
                imageVector = FeatherIcons.ArrowRight,
                contentDescription = null,
                modifier = Modifier.size(18.dp),
            )
        }
    }
}

@Composable
private fun CirclesHero(page: Int) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(280.dp),
        contentAlignment = Alignment.Center,
    ) {
        val leftOffset = when (page) {
            0 -> (-60).dp to (-30).dp
            1 -> (-70).dp to (-20).dp
            else -> (-64).dp to (-28).dp
        }
        val rightOffset = when (page) {
            0 -> 20.dp to 20.dp
            1 -> 10.dp to 30.dp
            else -> 16.dp to 22.dp
        }

        Box(
            modifier = Modifier
                .size(180.dp)
                .offset(x = leftOffset.first, y = leftOffset.second)
                .clip(CircleShape)
                .background(AppTheme.colors.primary),
        )

        Box(
            modifier = Modifier
                .size(180.dp)
                .offset(x = rightOffset.first, y = rightOffset.second)
                .clip(CircleShape)
                .background(AppTheme.colors.secondary.copy(alpha = 0.9f)),
        )

        Box(
            modifier = Modifier
                .offset(x = (-16).dp, y = 10.dp),
            contentAlignment = Alignment.Center,
        ) {
            Icon(
                imageVector = FeatherIcons.Heart,
                contentDescription = null,
                tint = AppTheme.colors.tertiary,
                modifier = Modifier.size(36.dp),
            )
        }
    }
}

@Composable
private fun DotIndicator(
    totalDots: Int,
    selectedIndex: Int,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(Spacing.spacing2),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        repeat(totalDots) { index ->
            if (index == selectedIndex) {
                Box(
                    modifier = Modifier
                        .width(22.dp)
                        .height(6.dp)
                        .clip(RoundedCornerShape(3.dp))
                        .background(AppTheme.colors.primary),
                )
            } else {
                Box(
                    modifier = Modifier
                        .size(6.dp)
                        .clip(CircleShape)
                        .background(AppTheme.colors.tertiary.copy(alpha = 0.6f)),
                )
            }
        }
    }
}

@Composable
@Preview
fun OnboardingScreenPreview() {
    AppTheme {
        OnboardingScreenContent(
            state = OnboardingScreenModel.State(currentPage = 0),
            onPageChanged = {},
            onSkip = {},
            onStart = {},
            onNextPage = {},
        )
    }
}
