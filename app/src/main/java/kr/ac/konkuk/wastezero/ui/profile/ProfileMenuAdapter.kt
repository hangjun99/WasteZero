package kr.ac.konkuk.wastezero.ui.profile

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import kr.ac.konkuk.wastezero.R
import kr.ac.konkuk.wastezero.ui.MainActivity
import kr.ac.konkuk.wastezero.ui.ingredient.HoldIngredientsActivity
import kr.ac.konkuk.wastezero.ui.ingredient.UsedIngredientsActivity

class ProfileMenuAdapter(
    private val context: Context,
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

        holder.itemView.setOnClickListener {
            when (menuTitle) {
                "보유한 식재료" -> {
                    val intent = Intent(context, HoldIngredientsActivity::class.java)
                    context.startActivity(intent)
                }
                "사용한 식재료" -> {
                    val intent = Intent(context, UsedIngredientsActivity::class.java)
                    context.startActivity(intent)
                }
                "닉네임 변경" -> showNicknameChangeDialog()
                "로그아웃" -> logoutUser(holder.itemView)
                "회원탈퇴" -> deleteUserAccount(holder.itemView)
            }
        }
    }

    override fun getItemCount(): Int = menuList.size

    private fun showNicknameChangeDialog() {
        val builder = AlertDialog.Builder(context)
        builder.setTitle("닉네임 변경")
        builder.setMessage("새 닉네임을 입력하세요:")
        val inputField = android.widget.EditText(context)
        builder.setView(inputField)

        builder.setPositiveButton("확인") { _, _ ->
            val newNickname = inputField.text.toString()
            if (newNickname.isNotEmpty()) {
                val userId = FirebaseAuth.getInstance().currentUser?.uid
                if (userId != null) {
                    val userRef = FirebaseDatabase.getInstance().getReference("users").child(userId)
                    userRef.child("nickname").setValue(newNickname).addOnSuccessListener {
                        android.widget.Toast.makeText(context, "닉네임이 변경되었습니다.", android.widget.Toast.LENGTH_SHORT).show()
                    }.addOnFailureListener {
                        android.widget.Toast.makeText(context, "닉네임 변경 실패", android.widget.Toast.LENGTH_SHORT).show()
                    }
                }
            } else {
                android.widget.Toast.makeText(context, "닉네임을 입력해주세요.", android.widget.Toast.LENGTH_SHORT).show()
            }
        }

        builder.setNegativeButton("취소", null)
        builder.show()
    }

    private fun logoutUser(view: View) {
        // GoogleSignInClient 초기화
        val googleSignInClient = GoogleSignIn.getClient(
            context,
            GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(context.getString(R.string.default_web_client_id))
                .requestEmail()
                .build()
        )

        // Firebase 로그아웃
        FirebaseAuth.getInstance().signOut()

        // Google 로그인 세션 초기화
        googleSignInClient.signOut().addOnCompleteListener {
            android.widget.Toast.makeText(context, "로그아웃 되었습니다.", android.widget.Toast.LENGTH_SHORT).show()

            // MainActivity로 이동
            val intent = Intent(context, MainActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            }
            context.startActivity(intent)
        }
    }

    private fun deleteUserAccount(view: View) {
        val user = FirebaseAuth.getInstance().currentUser
        val userId = user?.uid

        if (user != null && userId != null) {
            val builder = AlertDialog.Builder(context)
            builder.setTitle("회원탈퇴")
            builder.setMessage("정말로 회원탈퇴 하시겠습니까?")
            builder.setPositiveButton("확인") { _, _ ->
                // Firebase Database에서 데이터 삭제
                val userRef = FirebaseDatabase.getInstance().getReference("users").child(userId)
                userRef.removeValue().addOnSuccessListener {
                    // Google 로그인 세션 초기화
                    val googleSignInClient = GoogleSignIn.getClient(
                        context,
                        GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                            .requestIdToken(context.getString(R.string.default_web_client_id))
                            .requestEmail()
                            .build()
                    )

                    // Firebase Authentication 계정 삭제
                    user.delete()
                        .addOnSuccessListener {
                            // Google 세션 초기화
                            googleSignInClient.signOut().addOnCompleteListener { signOutTask ->
                                googleSignInClient.revokeAccess().addOnCompleteListener { revokeTask ->
                                    android.widget.Toast.makeText(context, "회원탈퇴가 완료되었습니다.", android.widget.Toast.LENGTH_SHORT).show()

                                    // 로그인 화면으로 이동
                                    val intent = Intent(context, MainActivity::class.java).apply {
                                        flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                                    }
                                    context.startActivity(intent)
                                }
                            }
                        }
                        .addOnFailureListener { authError ->
                            android.widget.Toast.makeText(context, "회원탈퇴 중 오류가 발생했습니다: ${authError.message}", android.widget.Toast.LENGTH_SHORT).show()
                        }
                }.addOnFailureListener { dbError ->
                    android.widget.Toast.makeText(context, "데이터 삭제 실패: ${dbError.message}", android.widget.Toast.LENGTH_SHORT).show()
                }
            }
            builder.setNegativeButton("취소", null)
            builder.show()
        }
    }


}
