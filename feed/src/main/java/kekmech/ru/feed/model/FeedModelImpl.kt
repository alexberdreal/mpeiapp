package kekmech.ru.feed.model

import android.content.Context
import kekmech.ru.core.dto.CoupleNative
import kekmech.ru.core.dto.Time
import kekmech.ru.core.scopes.ActivityScope
import kekmech.ru.core.usecases.LoadDayStatusUseCase
import kekmech.ru.core.usecases.LoadOffsetScheduleUseCase
import kekmech.ru.coreui.adapter.BaseItem
import kekmech.ru.feed.R
import kekmech.ru.feed.items.*
import kotlinx.coroutines.*
import javax.inject.Inject

@ActivityScope
class FeedModelImpl @Inject constructor(
    private val context: Context,
    private val loadOffsetScheduleUseCase: LoadOffsetScheduleUseCase,
    private val loadDayStatusUseCase: LoadDayStatusUseCase
) : FeedModel {

    override val today: Time
        get() = Time.today()

    override var scheduleInfoUpdateListener: (List<CoupleNative>) -> Unit = {}

    /**
     * Group number like "C-12-16"
     */
    override val groupNumber: String
        get() = loadDayStatusUseCase.execute(0).groupNum

    /**
     * Current week number
     */
    override val currentWeekNumber: Int
        get() = today.weekOfSemester

    override val formattedTodayStatus: String
        get() = "${today.formattedAsDayName(context, R.array.days_of_week)}, ${today.dayOfMonth} " +
                today.formattedAsMonthName(context, R.array.months)

    override var weekendOffset: Int = 0

    /**
     * Get couples for day
     * @param offset - 0 - today, 1 - yesterday etc.
     * @return return today's couples if offset == 0
     */
    override suspend fun getDayCouples(offset: Int, refresh: Boolean): List<BaseItem<*>> {
        return withContext(Dispatchers.IO) {
            val list = loadOffsetScheduleUseCase.execute(offset, refresh)
            if (list.isNotEmpty()) {
                val couples = mutableListOf<BaseItem<*>>()
                if (list.first().type == CoupleNative.WEEKEND) {
                    val firstWeekendTime = today.getDayWithOffset(offset)
                    // если сегодня выходной, то пытаемся собрать все последующие выходные в стек и вернуть сразу все
                    var i = 1
                    val nativeCouples = mutableListOf<CoupleNative>()
                    var nextDayCouples = loadOffsetScheduleUseCase.execute(offset + i, refresh)
                    while (nextDayCouples.isNotEmpty() && nextDayCouples.first().type == CoupleNative.WEEKEND) {
                        nativeCouples.addAll(nextDayCouples)
                        nextDayCouples = loadOffsetScheduleUseCase.execute(offset + (++i), refresh)
                    }
                    weekendOffset += i - 1

                    if (nativeCouples.size == 0) {
                        if (offset > 0) couples += FeedDividerItem(
                            today.getDayWithOffset(offset).formatAsDivider(),
                            offset == 0
                        )
                        couples += WeekendItem()
                    } else {
                        couples.add(
                            WeekendStackItem(
                                firstWeekendTime.formatAsDivider(),
                                firstWeekendTime.getDayWithOffset(nativeCouples.size).formatAsDivider(),
                                offset != 0
                            )
                        )
                    }
                    return@withContext couples
                }

                if (offset > 0) couples += FeedDividerItem(
                    today.getDayWithOffset(offset).formatAsDivider(),
                    offset == 0
                )
                list.forEachIndexed { i, e ->
                    // вставим обед между второй и третьей парой
                    if (e.num == 3 && i != 0 && !couples.any { it is LunchItem }) {
                        couples += LunchItem()
                    }
                    couples += CoupleItem(e).apply {
                        // если пара последняя, не показываем разделительную линию внизу
                        isDividerVisible = (i != list.size - 1)
                    }
                }
                return@withContext couples
            } else {
                // TODO DEPRECATED
                val couples = mutableListOf<BaseItem<*>>()
                if (offset > 0) couples += FeedDividerItem(
                    today.getDayWithOffset(offset).formatAsDivider(),
                    offset == 0
                )
                couples += WeekendItem()
                return@withContext couples
            }
        }
    }

    private fun Time.formatAsDivider() =
        "${formattedAsDayName(context, R.array.days_of_week)}, $dayOfMonth " +
                formattedAsMonthName(context, R.array.months)

}