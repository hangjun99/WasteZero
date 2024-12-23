package kr.ac.konkuk.wastezero.ui.alarm

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kr.ac.konkuk.wastezero.databinding.ItemAlarmAlarmBinding
import kr.ac.konkuk.wastezero.databinding.ItemAlarmTitleBinding
import kr.ac.konkuk.wastezero.domain.entity.Alarm
import kr.ac.konkuk.wastezero.util.date.toFormattedString
import kr.ac.konkuk.wastezero.util.notification.Notification

class AlarmAdapter(
    private val context: Context,
    private val items: List<ItemType>,
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            TYPE_TITLE -> {
                val binding = ItemAlarmTitleBinding.inflate(inflater, parent, false)
                TitleViewHolder(binding)
            }

            TYPE_ALARM -> {
                val binding = ItemAlarmAlarmBinding.inflate(inflater, parent, false)
                AlarmViewHolder(binding)
            }

            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is TitleViewHolder -> holder.bind()
            is AlarmViewHolder -> {
                val alarm = (items[position] as ItemType.AlarmItem).alarm
                holder.bind(alarm)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (items[position]) {
            is ItemType.TitleItem -> TYPE_TITLE
            is ItemType.AlarmItem -> TYPE_ALARM
        }
    }

    inner class TitleViewHolder(
        private val binding: ItemAlarmTitleBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind() {
            binding.itemAlarmTitleTv.setOnClickListener {
                Notification.sendNotification(context)
            }
        }
    }

    inner class AlarmViewHolder(
        private val binding: ItemAlarmAlarmBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(alarm: Alarm) {
            binding.apply {
                itemAlarmTimeTv.text = alarm.time.toFormattedString()
                itemAlarmContentTv.text = alarm.title
            }
        }
    }

    sealed class ItemType {
        data object TitleItem : ItemType()
        data class AlarmItem(val alarm: Alarm) : ItemType()
    }

    companion object {
        private const val TYPE_TITLE = 0
        private const val TYPE_ALARM = 1
    }
}