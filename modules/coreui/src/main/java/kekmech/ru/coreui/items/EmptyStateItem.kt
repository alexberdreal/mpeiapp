package kekmech.ru.coreui.items

import android.view.View
import androidx.annotation.StringRes
import androidx.recyclerview.widget.RecyclerView
import kekmech.ru.common_adapter.AdapterItem
import kekmech.ru.common_adapter.BaseItemBinder
import kekmech.ru.coreui.R
import kotlinx.android.extensions.LayoutContainer
import kotlinx.android.synthetic.main.item_empty_state.*

data class EmptyStateItem(
    @StringRes val titleRes: Int? = null,
    @StringRes val subtitleRes: Int? = null
)

interface EmptyStateViewHolder {
    fun setTitle(@StringRes titleRes: Int)
    fun setSubtitle(@StringRes subtitleRes: Int)
}

class EmptyStateViewHolderImpl(
    override val containerView: View
) : EmptyStateViewHolder, RecyclerView.ViewHolder(containerView), LayoutContainer {

    override fun setTitle(@StringRes titleRes: Int) {
        textViewTitle.setText(titleRes)
    }

    override fun setSubtitle(@StringRes subtitleRes: Int) {
        textViewSubtitle.setText(subtitleRes)
    }
}

class EmptyStateItemBinder : BaseItemBinder<EmptyStateViewHolder, EmptyStateItem>() {

    override fun bind(vh: EmptyStateViewHolder, model: EmptyStateItem, position: Int) {
        model.titleRes?.let(vh::setTitle)
        model.subtitleRes?.let(vh::setSubtitle)
    }
}

class EmptyStateAdapterItem : AdapterItem<EmptyStateViewHolder, EmptyStateItem>(
    isType = { it is EmptyStateItem },
    layoutRes = R.layout.item_empty_state,
    viewHolderGenerator = ::EmptyStateViewHolderImpl,
    itemBinder = EmptyStateItemBinder()
)