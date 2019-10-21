package kekmech.ru.update.model

import kekmech.ru.core.usecases.GetForceUpdateDataUseCase
import javax.inject.Inject

class ForceUpdateFragmentModelImpl @Inject constructor(
    private val getForceUpdateDataUseCase: GetForceUpdateDataUseCase
) : ForceUpdateFragmentModel {
    override val url: String
        get() = getForceUpdateDataUseCase().first

    override val description: String
        get() = getForceUpdateDataUseCase().second
}