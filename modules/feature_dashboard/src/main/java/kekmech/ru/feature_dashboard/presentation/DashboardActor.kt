package kekmech.ru.feature_dashboard.presentation

import io.reactivex.Observable
import kekmech.ru.common_mvi.Actor
import kekmech.ru.domain_notes.NotesRepository
import kekmech.ru.domain_notes.NotesScheduleTransformer
import kekmech.ru.domain_schedule.ScheduleRepository
import kekmech.ru.feature_dashboard.presentation.DashboardEvent.News

class DashboardActor(
    private val scheduleRepository: ScheduleRepository,
    private val notesRepository: NotesRepository,
    private val notesScheduleTransformer: NotesScheduleTransformer
) : Actor<DashboardAction, DashboardEvent> {

    override fun execute(action: DashboardAction): Observable<DashboardEvent> = when (action) {
        is DashboardAction.LoadSchedule -> scheduleRepository.loadSchedule(weekOffset = action.weekOffset)
            .flatMap(notesScheduleTransformer::transform)
            .mapEvents({ News.ScheduleLoaded(it, action.weekOffset) }, News::ScheduleLoadError)
        is DashboardAction.GetSelectedGroupName -> scheduleRepository.getSelectedGroup()
            .mapSuccessEvent(News::SelectedGroupNameLoaded)
        is DashboardAction.LoadNotes -> notesRepository.getNotes()
            .mapEvents(News::NotesLoaded, News::NotesLoadError)
        is DashboardAction.LoadFavoriteSchedules -> scheduleRepository.getFavorites()
            .mapSuccessEvent(News::FavoriteSchedulesLoaded)
        is DashboardAction.SelectGroup -> scheduleRepository.selectGroup(action.groupName)
            .mapSuccessEvent(News.FavoriteGroupSelected)
    }
}