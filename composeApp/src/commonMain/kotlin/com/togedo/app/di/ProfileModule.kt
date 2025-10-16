package com.togedo.app.di

import com.togedo.app.ui.profile.ProfileScreenModel
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

val profileModule = module {
    factoryOf(::ProfileScreenModel)
}