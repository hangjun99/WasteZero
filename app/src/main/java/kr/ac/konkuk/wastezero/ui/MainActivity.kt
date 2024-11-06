package kr.ac.konkuk.wastezero.ui

import android.os.Bundle
import dagger.hilt.android.AndroidEntryPoint
import kr.ac.konkuk.wastezero.R
import kr.ac.konkuk.wastezero.databinding.ActivityMainBinding
import kr.ac.konkuk.wastezero.util.base.BaseActivity

//@AndroidEntryPoint
class MainActivity(

) : BaseActivity<ActivityMainBinding>(R.layout.activity_main) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        /*enableEdgeToEdge()
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }*/
    }
}