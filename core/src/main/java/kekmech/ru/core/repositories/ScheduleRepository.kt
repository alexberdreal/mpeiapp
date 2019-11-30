package kekmech.ru.core.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kekmech.ru.core.dto.CoupleNative
import kekmech.ru.core.dto.Schedule
import kekmech.ru.core.dto.ScheduleNative

interface ScheduleRepository {
    var isNeedToUpdateFeed: MutableLiveData<Boolean>

    val scheduleId: Int

    fun getOffsetCouples(offset:Int, refresh: Boolean): List<CoupleNative>

    fun getSchedule(refresh: Boolean): Schedule?

    fun saveSchedule(schedule: Schedule)

    fun getGroupNum(): LiveData<String>

    fun getAllSchedules(): List<ScheduleNative>

    fun setCurrentScheduleId(id: Int)
}