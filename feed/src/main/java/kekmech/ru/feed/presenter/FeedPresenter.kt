package kekmech.ru.feed.presenter

import android.content.Context
import androidx.lifecycle.Observer
import kekmech.ru.core.Presenter
import kekmech.ru.core.Router
import kekmech.ru.core.Screens.*
import kekmech.ru.core.UpdateChecker
import kekmech.ru.core.scopes.ActivityScope
import kekmech.ru.coreui.adapter.BaseAdapter
import kekmech.ru.feed.IFeedFragment
import kekmech.ru.feed.items.*
import kekmech.ru.feed.model.FeedModel
import kotlinx.coroutines.*
import java.util.*
import javax.inject.Inject

@ActivityScope
class FeedPresenter @Inject constructor(
    private val model: FeedModel,
    private val context: Context,
    private val router: Router,
    private val updateChecker: UpdateChecker
) : Presenter<IFeedFragment>() {

    var view: IFeedFragment? = null
    val adapter by lazy { BaseAdapter.Builder()
        .registerViewTypeFactory(SessionItem.Factory())
        .registerViewTypeFactory(CarouselItem.Factory())
        .registerViewTypeFactory(EmptyItem.Factory())
        .build()
    }

    /**
     * subscribe to view events
     */
    override fun onResume(view: IFeedFragment) {
        this.view = view

        adapter.baseItems.clear()
        adapter.baseItems.add(CarouselItem())
        view.setAdapter(adapter)
        adapter.notifyDataSetChanged()

        GlobalScope.launch(Dispatchers.Main) {
            if (withContext(Dispatchers.IO) { model.isSchedulesEmpty }) {
                adapter.baseItems.add(EmptyItem(::onStatusEdit))
                adapter.notifyItemInserted(adapter.baseItems.lastIndex)
                view.hideLoading()
            } else {
                val academicSession = withContext(Dispatchers.IO) { model.getAcademicSession() }
                if (academicSession != null) {
                    adapter.baseItems.add(SessionItem())
                    adapter.notifyItemInserted(adapter.baseItems.lastIndex)
                }
                if (adapter.baseItems.size == 1) {// если только карусель
//                    adapter.baseItems.add(NothingToShowItem())
//                    adapter.notifyItemInserted(adapter.baseItems.lastIndex)
                }
                view.hideLoading()
            }
        }


        // check for updates
        GlobalScope.launch(Dispatchers.Main) {
            delay(1000)
            if (model.isNotShowedUpdateDialog) {
                updateChecker.check { url, desc ->
                    model.saveForceUpdateArgs(url, desc)
                    router.navigate(FEED_TO_FORCE)
                    model.isNotShowedUpdateDialog = false
                }
            }
        }
    }

    private fun onStatusEdit() {
        //view?.showMenu()
        GlobalScope.launch(Dispatchers.Main){
            delay(100)
            router.navigate(FEED_TO_ADD)
        }
    }

    /**
     * unsubscribe to view events
     */
    override fun onPause(view: IFeedFragment) {
        this.view = null
    }

}