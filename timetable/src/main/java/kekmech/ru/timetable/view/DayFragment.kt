package kekmech.ru.timetable.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kekmech.ru.coreui.adapter.BaseAdapter
import kekmech.ru.coreui.adapter.BaseItem
import kekmech.ru.timetable.MinCoupleItem
import kekmech.ru.timetable.MinLunchItem
import kekmech.ru.timetable.R
import kotlinx.android.synthetic.main.fragment_day.view.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.coroutines.coroutineContext

class DayFragment(
    private val couples: () -> List<BaseItem<*>>
) : Fragment() {

    private val adapter = BaseAdapter.Builder()
        .registerViewTypeFactory(MinCoupleItem.Factory())
        .registerViewTypeFactory(MinLunchItem.Factory())
        .build()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_day, container, false)
        view.dayFragmentRecyclerView.layoutManager = LinearLayoutManager(context)
        view.dayFragmentRecyclerView.setRecycledViewPool(Companion.recycledViewPool)
        view.dayFragmentRecyclerView.adapter = adapter
        GlobalScope.launch(Dispatchers.IO) {
            val awaitedCouples = couples()
            withContext(Dispatchers.Main) {
                adapter.baseItems.clear()
                adapter.baseItems.addAll(awaitedCouples)
                adapter.notifyDataSetChanged()
            }
        }
        // Inflate the layout for this fragment
        return view
    }

    companion object {
        private val recycledViewPool by lazy { RecyclerView.RecycledViewPool() }
    }
}