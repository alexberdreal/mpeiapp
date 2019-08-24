package kekmech.ru.core.dto

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = "users")
data class User (
    @PrimaryKey(autoGenerate = true) var id: Int = 0,
    @ColumnInfo(name = "first_launch") var firstLaunchFlag: Boolean? = null, // первый запуск
    @ColumnInfo(name = "dev_mode") var developerMode: Boolean? = null, // режим разработчика
    @ColumnInfo(name = "group") var groupName: String? = null, // номер группы
    @ColumnInfo(name = "night_mode") var nightMode: Boolean? = null,
    @ColumnInfo(name = "last_schedule_id") var lastScheduleId: Int = 0 // последнее открытое расписание
)