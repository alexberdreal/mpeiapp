package kekmech.ru.feature_dashboard.di

import kekmech.ru.common_di.ModuleProvider
import kekmech.ru.feature_dashboard.DashboardAnalytics
import kekmech.ru.feature_dashboard.presentation.DashboardActor
import kekmech.ru.feature_dashboard.presentation.DashboardFeatureFactory
import org.koin.dsl.bind

object DashboardModule : ModuleProvider({
    single { DashboardDependencies(get(), get(), get(), get(), get()) } // он не просто так single!!!
    factory { DashboardFeatureFactory(get()) } bind DashboardFeatureFactory::class
    factory { DashboardActor(get(), get(), get()) } bind DashboardActor::class
    factory { DashboardAnalytics(get()) } bind DashboardAnalytics::class
})