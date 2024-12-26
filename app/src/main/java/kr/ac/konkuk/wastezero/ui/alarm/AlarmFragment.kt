package kr.ac.konkuk.wastezero.ui.alarm

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import kr.ac.konkuk.wastezero.R
import kr.ac.konkuk.wastezero.databinding.FragmentAlarmBinding
import kr.ac.konkuk.wastezero.domain.entity.Alarm
import kr.ac.konkuk.wastezero.util.base.BaseFragment
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class AlarmFragment(

) : BaseFragment<FragmentAlarmBinding>(R.layout.fragment_alarm) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setRecyclerView()
    }

    private fun setRecyclerView() {
        val adapter = AlarmAdapter(requireContext(), example)
        binding.alarmRv.adapter = adapter
        binding.alarmRv.layoutManager = LinearLayoutManager(context)
    }

    companion object {
        val example = listOf(
            AlarmAdapter.ItemType.TitleItem,
            AlarmAdapter.ItemType.AlarmItem(
                Alarm(
                    1, "식재료 유통기한 알림", LocalDateTime.parse(
                        "2024-12-23 09:00", DateTimeFormatter.ofPattern(
                            kr.ac.konkuk.wastezero.util.date.dateTimeFormatter
                        )
                    ), false
                ),
            ),
            AlarmAdapter.ItemType.AlarmItem(
                Alarm(
                    1, "식재료 유통기한 알림", LocalDateTime.parse(
                        "2024-12-22 09:00", DateTimeFormatter.ofPattern(
                            kr.ac.konkuk.wastezero.util.date.dateTimeFormatter
                        )
                    ), false
                ),
            ),
            AlarmAdapter.ItemType.AlarmItem(
                Alarm(
                    1, "식재료 유통기한 알림", LocalDateTime.parse(
                        "2024-12-21 09:00", DateTimeFormatter.ofPattern(
                            kr.ac.konkuk.wastezero.util.date.dateTimeFormatter
                        )
                    ), false
                ),
            ),

            )
    }

}