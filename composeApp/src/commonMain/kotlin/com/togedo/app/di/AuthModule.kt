package com.togedo.app.di

import com.togedo.app.ui.onboarding.OnboardingScreenModel
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

val authModule = module {
    factoryOf(::OnboardingScreenModel)
}