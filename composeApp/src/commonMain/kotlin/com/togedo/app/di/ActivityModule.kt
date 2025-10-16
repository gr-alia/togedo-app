package com.togedo.app.di

import com.togedo.app.data.repositoryimpl.ActivityRepositoryImpl
import com.togedo.app.domain.repository.ActivityRepository
import com.togedo.app.ui.activity.add.AddActivityScreenModel
import com.togedo.app.ui.activity.list.ActivityListScreenModel
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val activityModule = module {
    singleOf(::ActivityRepositoryImpl) { bind<ActivityRepository>() }
    factoryOf(::ActivityListScreenModel)
    factoryOf(::AddActivityScreenModel)
}