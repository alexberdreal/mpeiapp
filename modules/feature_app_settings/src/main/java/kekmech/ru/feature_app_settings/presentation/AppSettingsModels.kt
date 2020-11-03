package kekmech.ru.feature_app_settings.presentation

import kekmech.ru.common_mvi.Feature
import kekmech.ru.domain_app_settings.AppSettings

typealias AppSettingsFeature = Feature<AppSettingsState, AppSettingsEvent, AppSettingsEffect>

data class AppSettingsState(
    val appSettings: AppSettings? = null,
    val hash: String = ""
)

sealed class AppSettingsEvent {

    sealed class Wish : AppSettingsEvent() {

        object Init : Wish()

        object Action {
            data class SetDarkThemeEnabled(val isEnabled: Boolean) : Wish()
            data class SetChangeDayAfterChangeWeek(val isEnabled: Boolean) : Wish()
            data class SetAutoHideBottomSheet(val isEnabled: Boolean) : Wish()
            object ClearSelectedGroup : Wish()
        }
    }

    sealed class News : AppSettingsEvent() {
        data class AppSettingsLoaded(val appSettings: AppSettings) : News()
        object AppSettingsChanged : News()
    }
}

sealed class AppSettingsAction {
    object LoadAppSettings : AppSettingsAction()

    data class SetDarkThemeEnabled(val isEnabled: Boolean) : AppSettingsAction()
    data class SetChangeDayAfterChangeWeek(val isEnabled: Boolean) : AppSettingsAction()
    data class SetAutoHideBottomSheet(val isEnabled: Boolean) : AppSettingsAction()
    object ClearSelectedGroup : AppSettingsAction()
}

sealed class AppSettingsEffect {
    object RecreateActivity : AppSettingsEffect()
}