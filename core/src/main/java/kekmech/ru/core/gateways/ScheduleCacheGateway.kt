package kekmech.ru.core.gateways

import kekmech.ru.core.dto.CoupleNative
import kekmech.ru.core.dto.Schedule
import kekmech.ru.core.dto.ScheduleNative

/**
 * Yes, it's just a another called repository
 */
interface ScheduleCacheGateway {
    var scheduleId: Int

    fun getSchedule(): Schedule?

    fun getCouples(dayNum: Int, odd: Boolean): List<CoupleNative>?

    fun newSchedule(schedule: Schedule)

    fun getGroupNum(): String

    fun getAllSchedules(): List<ScheduleNative>

    fun setCurrentScheduleId(id: Int)
}