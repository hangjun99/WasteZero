package kr.ac.konkuk.wastezero.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import kr.ac.konkuk.wastezero.R

class ProfileMenuAdapter(
    private val fragmentManager: FragmentManager,
    private val menuList: List<String>
) : RecyclerView.Adapter<ProfileMenuAdapter.MenuViewHolder>() {

    inner class MenuViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textViewMenuTitle: TextView = view.findViewById(R.id.textViewMenuTitle)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.profile_menu, parent, false)
        return MenuViewHolder(view)
    }

    override fun onBindViewHolder(holder: MenuViewHolder, position: Int) {
        val menuTitle = menuList[position]
        holder.textViewMenuTitle.text = menuTitle

        // 메뉴 항목 클릭 이벤트 설정
        holder.itemView.setOnClickListener {
            when (menuTitle) {
                "보유한 식재료" -> {
                    // HoldIngredientsFragment로 전환
                    fragmentManager.beginTransaction()
//                        .replace(R.id.container_fl, HoldIngredientsFragment())
                        .addToBackStack(null)
                        .commit()
                }
                // 다른 메뉴 항목에 대한 처리를 추가할 수 있음
                else -> {
                    // 기본 클릭 동작
                }
            }
        }
    }

    override fun getItemCount(): Int = menuList.size
}