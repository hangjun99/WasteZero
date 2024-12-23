package kr.ac.konkuk.wastezero.util.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import timber.log.Timber

abstract class BaseFragment<T : ViewDataBinding>(
    @LayoutRes private val layoutResId: Int
) : Fragment() {

    private var _binding: T? = null
    protected val binding: T
        get() = requireNotNull(_binding) { "${javaClass.simpleName}'s binding is not initialized" }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DataBindingUtil.inflate(inflater, layoutResId, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Binding이 초기화된 후 작업을 수행
        if (_binding != null) {
            initBinding()
        } else {
            Timber.e("${javaClass.simpleName}: Binding is null in onViewCreated")
        }
    }

    open fun initBinding() {
        // 자식 클래스에서 필요한 초기화 작업 수행
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
