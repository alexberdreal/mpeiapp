package kekmech.ru.feature_schedule.main.item

import android.content.Context
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import kekmech.ru.common_adapter.AdapterItem
import kekmech.ru.coreui.items.ClassesItemBinder
import kekmech.ru.coreui.items.ClassesViewHolder
import kekmech.ru.coreui.items.ClassesViewHolderImpl
import kekmech.ru.coreui.items.ClickableItemViewHolder
import kekmech.ru.domain_schedule.dto.Classes
import kekmech.ru.domain_schedule.dto.ClassesStackType
import kekmech.ru.feature_schedule.R
import kotlinx.android.extensions.LayoutContainer

internal interface ClassesStackMiddleViewHolder : ClassesViewHolder, ClickableItemViewHolder
internal interface ClassesStackEndViewHolder : ClassesViewHolder, ClickableItemViewHolder

internal class ClassesStackMiddleViewHolderImpl(
    override val containerView: View
) :
    ClassesViewHolder by ClassesViewHolderImpl(containerView),
    RecyclerView.ViewHolder(containerView),
    LayoutContainer,
    ClassesStackMiddleViewHolder
{
    override fun setStartTime(time: String) = Unit

    override fun setEndTime(time: String) = Unit

    override fun setNumberName(name: String) = Unit
}

internal class ClassesStackEndViewHolderImpl(
    override val containerView: View
) :
    ClassesViewHolder by ClassesViewHolderImpl(containerView),
    RecyclerView.ViewHolder(containerView),
    LayoutContainer,
    ClassesStackEndViewHolder
{
    override fun setStartTime(time: String) = Unit

    override fun setEndTime(time: String) = Unit

    override fun setNumberName(name: String) = Unit
}

internal class ClassesStackStartAdapterItem(
    context: Context,
    onClickListener: ((Classes) -> Unit)? = null
) : AdapterItem<ClassesViewHolder, Classes>(
    isType = { it is Classes && it.stackType == ClassesStackType.START },
    layoutRes = R.layout.item_classes_stack_start,
    viewHolderGenerator = ::ClassesViewHolderImpl,
    itemBinder = ClassesItemBinder(context, onClickListener)
)

internal class ClassesStackMiddleAdapterItem(
    context: Context,
    onClickListener: ((Classes) -> Unit)? = null
) : AdapterItem<ClassesStackMiddleViewHolder, Classes>(
    isType = { it is Classes && it.stackType == ClassesStackType.MIDDLE },
    layoutRes = R.layout.item_classes_stack_middle,
    viewHolderGenerator = ::ClassesStackMiddleViewHolderImpl,
    itemBinder = ClassesItemBinder(context, onClickListener)
)

internal class ClassesStackEndAdapterItem(
    context: Context,
    onClickListener: ((Classes) -> Unit)? = null
) : AdapterItem<ClassesStackEndViewHolder, Classes>(
    isType = { it is Classes && it.stackType == ClassesStackType.END },
    layoutRes = R.layout.item_classes_stack_end,
    viewHolderGenerator = ::ClassesStackEndViewHolderImpl,
    itemBinder = ClassesItemBinder(context, onClickListener)
)

