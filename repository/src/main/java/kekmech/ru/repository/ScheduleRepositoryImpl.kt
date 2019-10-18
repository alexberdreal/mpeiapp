package kekmech.ru.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kekmech.ru.core.dto.CoupleNative
import kekmech.ru.core.dto.DayStatus
import kekmech.ru.core.dto.Schedule
import kekmech.ru.core.dto.Time
import kekmech.ru.core.gateways.ScheduleCacheGateway
import kekmech.ru.core.repositories.ScheduleRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ScheduleRepositoryImpl @Inject constructor(
    private val scheduleCacheGateway: ScheduleCacheGateway
) : ScheduleRepository {

//    override fun getDayStatus(offset: Int): LiveData<DayStatus> {
//        // update daystatus but return livedata
//        GlobalScope.launch(Dispatchers.IO) {
//            val newDay = Time.today().getDayWithOffset(offset)
//            dayStatus.value = DayStatus(newDay, getGroupNum())
//        }
//        return dayStatus
//    }

    override fun getSchedule(refresh: Boolean): Schedule? {
        return scheduleCacheGateway.getSchedule()
    }

    override fun getOffsetCouples(offset: Int, refresh: Boolean): List<CoupleNative> {
        val today = Time.today()
        val offsetDay = today.getDayWithOffset(offset)
        if (today.dayOfWeek + offset <= 7) {
            val couples = scheduleCacheGateway.getCouples(today.dayOfWeek + offset, offsetDay.parity == Time.Parity.ODD)
            when {
                couples == null -> return emptyList()
                couples.isEmpty() -> return listOf(CoupleNative.dayOff)
                else -> return couples
            }
        } else {
            val necessaryDayOfWeek = (today.dayOfWeek + offset) % 7
            val couples = scheduleCacheGateway.getCouples(necessaryDayOfWeek, offsetDay.parity == Time.Parity.ODD)
            when {
                couples == null -> return emptyList()
                couples.isEmpty() -> return listOf(CoupleNative.dayOff)
                else -> return couples
            }
        }
    }

    override fun saveSchedule(schedule: Schedule) {
        scheduleCacheGateway.newSchedule(schedule)
    }

    private val groupNumber = MutableLiveData<String>().apply { value = "" }
    override fun getGroupNum(): LiveData<String> {
        GlobalScope.launch(Dispatchers.Main) {
            groupNumber.value = withContext(Dispatchers.IO) { scheduleCacheGateway.getGroupNum() }
        }
        return groupNumber
    }

}