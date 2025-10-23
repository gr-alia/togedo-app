package com.togedo.app.ui.onboarding

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.koin.koinScreenModel
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.togedo.app.designsystem.AppTheme
import com.togedo.app.ui.auth.LoginScreen
import com.togedo.app.designsystem.Spacing
import com.togedo.app.designsystem.components.Button
import com.togedo.app.designsystem.components.ButtonVariant
import com.togedo.app.designsystem.components.Icon
import com.togedo.app.designsystem.components.Scaffold
import com.togedo.app.designsystem.components.Text
import compose.icons.FeatherIcons
import compose.icons.feathericons.ArrowLeft
import compose.icons.feathericons.ArrowRight
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
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
            onPreviousPage = screenModel::onPreviousPage,
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
    onPreviousPage: () -> Unit,
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
    ) {

        HorizontalPagerWithIndicators(
            state.pages,
            onPageChanged,
            onSkip,
            onStart,
            onNextPage,
            onPreviousPage
        )

    }
}

@Composable
private fun HorizontalPagerWithIndicators(
    pages: List<OnboardingPageUiModel>,
    onPageChanged: (Int) -> Unit,
    onSkip: () -> Unit,
    onStart: () -> Unit,
    onNextPage: () -> Unit,
    onPreviousPage: () -> Unit
) {
    val pagerState = rememberPagerState(pageCount = { pages.size })
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(pagerState.currentPage) {
        onPageChanged(pagerState.currentPage)
    }

    Column(
        modifier = Modifier.fillMaxSize(),
    ) {
        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),
        ) { page ->
            OnboardingPage(page = pages[page])
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(Spacing.spacing4),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            when (pagerState.currentPage) {
                0 -> {
                    Button(
                        text = "Skip",
                        variant = ButtonVariant.Ghost,
                        onClick = onSkip,
                    )
                }

                1 -> {
                    Button(
                        variant = ButtonVariant.Ghost,
                        onClick = {
                            coroutineScope.launch {
                                pagerState.animateScrollToPage(pagerState.currentPage - 1)
                                onPreviousPage()
                            }
                        },
                    ) {
                        Icon(
                            imageVector = FeatherIcons.ArrowLeft,
                            contentDescription = "Previous",
                        )
                    }
                }

                2 -> {
                    Button(
                        variant = ButtonVariant.Ghost,
                        onClick = {
                            coroutineScope.launch {
                                pagerState.animateScrollToPage(pagerState.currentPage - 1)
                                onPreviousPage()
                            }
                        },
                    ) {
                        Icon(
                            imageVector = FeatherIcons.ArrowLeft,
                            contentDescription = "Previous",
                        )
                    }
                }
            }

            DotIndicator(
                totalDots = pages.size,
                selectedIndex = pagerState.currentPage,
            )

            when (pagerState.currentPage) {
                0 -> {
                    Button(
                        variant = ButtonVariant.Ghost,
                        onClick = {
                            coroutineScope.launch {
                                pagerState.animateScrollToPage(pagerState.currentPage + 1)
                                onNextPage()
                            }
                        },
                    ) {
                        Icon(
                            imageVector = FeatherIcons.ArrowRight,
                            contentDescription = "Next",
                        )
                    }
                }

                1 -> {
                    Button(
                        variant = ButtonVariant.Ghost,
                        onClick = {
                            coroutineScope.launch {
                                pagerState.animateScrollToPage(pagerState.currentPage + 1)
                                onNextPage()
                            }
                        },
                    ) {
                        Icon(
                            imageVector = FeatherIcons.ArrowRight,
                            contentDescription = "Next",
                        )
                    }
                }

                2 -> {
                    Button(
                        text = "Start",
                        variant = ButtonVariant.Primary,
                        onClick = onStart,
                    )
                }
            }
        }
    }
}

@Composable
private fun OnboardingPage(page: OnboardingPageUiModel) {
    Column(
        modifier = Modifier
            .padding(top = Spacing.spacing10)
            .padding(horizontal = Spacing.spacing4)
            .fillMaxSize()
            .background(AppTheme.colors.background),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(page.image),
            contentDescription = ""
        )
        Text(
            modifier = Modifier.padding(top=Spacing.spacing5),
            text = stringResource(page.title),
            style = AppTheme.typography.h2,
            textAlign = TextAlign.Center

        )
        Text(
            modifier = Modifier.padding(top=Spacing.spacing3),
            text = stringResource(page.description),
            style = AppTheme.typography.body2,
            textAlign = TextAlign.Center
        )
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
    ) {
        repeat(totalDots) { index ->
            Box(
                modifier = Modifier
                    .size(8.dp)
                    .background(
                        color = if (index == selectedIndex) {
                            AppTheme.colors.primary
                        } else {
                            AppTheme.colors.brandEarthYellow
                        },
                        shape = CircleShape,
                    ),
            )
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
            onPreviousPage = {},
        )
    }
}
