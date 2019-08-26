package kekmech.ru.feed

import android.content.Context
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import dagger.android.support.DaggerFragment
import kekmech.ru.core.Presenter
import kekmech.ru.coreui.Resources
import kekmech.ru.coreui.adapter.BaseAdapter
import kotlinx.android.synthetic.main.fragment_feed.*
import kotlinx.android.synthetic.main.fragment_feed.view.*
import javax.inject.Inject


class FeedFragment : DaggerFragment() {

    @Inject
    lateinit var presenter: Presenter<FeedFragment>

    /**
     * fires when user scroll his feed down to the end.
     * presenter must subscribe for this event and load new items to the feed
     */
    var onScrollEndListener: () -> Unit = {}

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_feed, container, false)
        view.recyclerView.layoutManager = LinearLayoutManager(activity)
        return view
    }

    override fun onResume() {
        super.onResume()
        presenter.onResume(this)
    }

    override fun onPause() {
        super.onPause()
        presenter.onPause(this)
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        activity!!.window.statusBarColor = Resources.getColor(context, R.color.colorSecondary)
    }

    @Deprecated("Нарушение dependency rule")
    fun updateView(adapter: BaseAdapter) {
        recyclerView.adapter = adapter
    }

    fun showLoading() {

    }

    fun hideLoading() {

    }
}
