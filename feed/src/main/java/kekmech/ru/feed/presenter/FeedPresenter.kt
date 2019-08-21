package kekmech.ru.feed.presenter

import android.util.Log
import kekmech.ru.core.Presenter
import kekmech.ru.core.scopes.ActivityScope
import kekmech.ru.coreui.adapter.BaseAdapter
import kekmech.ru.feed.FeedFragment
import kekmech.ru.feed.items.CoupleItem
import kekmech.ru.feed.items.FeedDividerItem
import kekmech.ru.feed.items.LunchItem
import kekmech.ru.feed.model.FeedModel
import kotlinx.android.synthetic.main.fragment_feed.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.inject.Inject

@ActivityScope
class FeedPresenter @Inject constructor(
    private val model: FeedModel
) : Presenter<FeedFragment> {

    var view: FeedFragment? = null
    var offset = 0
    val adapter by lazy { BaseAdapter.Builder()
        .registerViewTypeFactory(FeedDividerItem.Factory())
        .registerViewTypeFactory(CoupleItem.Factory())
        .registerViewTypeFactory(LunchItem.Factory())
        .build()
    }

    /**
     * subscribe to view events
     */
    override fun onResume(view: FeedFragment) {
        this.view = view
        view.onScrollEndListener = { onScrollEnd() }
        if (view.recyclerView.adapter == null) view.updateView(adapter)
        if (offset == 0) onScrollEnd()
    }

    /**
     * unsubscribe to view events
     */
    override fun onPause(view: FeedFragment) {
        view.onScrollEndListener = {}
        this.view = null
    }

    private fun onScrollEnd() {
        GlobalScope.launch(Dispatchers.Main) {
            view?.showLoading()
            val couples = model.getDayCouples(offset, refresh = false)
            adapter.baseItems.addAll(couples)
            adapter.notifyDataSetChanged()
            Log.d(this@FeedPresenter::class.java.simpleName, couples.size.toString())
            view?.updateView(adapter)
            view?.hideLoading()
            offset++
        }

    }
}