package kekmech.ru.coreui.adapter

import android.support.v7.widget.RecyclerView
import android.view.View
import kotlin.reflect.KProperty

/**
 * Created by Kolomeytsev Anton on 09.07.2016.
 * This class is a part of Mr. Captain project.
 */
@Suppress("UNCHECKED_CAST")
abstract class BaseViewHolder : RecyclerView.ViewHolder {

    constructor(itemView: View) : super(itemView) {
        this.onCreateView(itemView)
    }

    abstract fun onCreateView(view: View)

    fun<D: View> bind(id: Int): ViewDelegate<D> {
        return ViewDelegate(id)
    }

    inner class ViewDelegate<V:View>(val id: Int) {
        var view: V? = null

        operator fun getValue(thisRef: BaseViewHolder, property: KProperty<*>): V {
            if (view == null) view = itemView.findViewById(id) as V
            return view!!
        }

        operator fun setValue(thisRef: BaseViewHolder, property: KProperty<*>, value: View) {
            view = value as V
        }
    }

}