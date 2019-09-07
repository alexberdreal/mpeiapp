package kekmech.ru.domain.feed

import kekmech.ru.core.dto.DayStatus
import kekmech.ru.core.dto.Time
import kekmech.ru.core.usecases.LoadDayStatusUseCase
import javax.inject.Inject

class LoadDayStatusUseCaseImpl @Inject constructor() : LoadDayStatusUseCase {
    private val mapOfDays = mutableMapOf<Int, Time>()
    override fun execute(offset: Int, refresh: Boolean): DayStatus {
        if (refresh) mapOfDays.clear()
        if (mapOfDays[offset] == null) {
            val newDay = Time.today().getDayWithOffset(offset)
            mapOfDays[offset] = newDay
            return DayStatus(newDay)
        } else {
            return DayStatus(mapOfDays[offset]!!)
        }
    }

}