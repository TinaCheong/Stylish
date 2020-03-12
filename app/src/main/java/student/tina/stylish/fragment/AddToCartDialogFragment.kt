package student.tina.stylish.fragment

import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatDialog
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import student.tina.stylish.R
import androidx.lifecycle.ViewModelProviders
import student.tina.stylish.MyApplication
import student.tina.stylish.`object`.Product
import student.tina.stylish.adapter.AddToCartColorAdapter
import student.tina.stylish.adapter.AddToCartSizeAdapter
import student.tina.stylish.adapter.bindRecyclerViewGrid
import student.tina.stylish.database.CartItems
import student.tina.stylish.database.CartItemsDatabase
import student.tina.stylish.databinding.DialogAddToCartBinding
import student.tina.stylish.factory.DetailSelectViewModelFactory
import student.tina.stylish.view.AddToCartViewModel


class AddToCartDialogFragment(val product: Product) : BottomSheetDialogFragment() {

    lateinit var binding: DialogAddToCartBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater, R.layout.dialog_add_to_cart, container, false
        )

        binding.detailClose.setOnClickListener {
            this@AddToCartDialogFragment.dismiss()
        }

        val viewModelFactory = DetailSelectViewModelFactory(product)

        val viewModel = ViewModelProviders.of(
            this, viewModelFactory
        ).get(AddToCartViewModel::class.java)

        binding.lifecycleOwner = this

        binding.viewModel = viewModel

        binding.detailColorLinear.adapter = AddToCartColorAdapter(viewModel)

        binding.detailSizeLinear.adapter = AddToCartSizeAdapter(viewModel)

        binding.addButton.setOnClickListener {
            viewModel.plusAmount()
        }

        binding.subtractButton.setOnClickListener {
            viewModel.minusAmount()
        }


        viewModel.size.observe(this, Observer {
            if (viewModel.size.value != null) {
                binding.addCart2.setOnClickListener {
                    viewModel.onStartTracking()
                    showAddSuccessDialog()
                }
            }
        })

        viewModel.items.observe(this, Observer {
            if (it != null)
                Log.i("Tina", "getAllItems = ${it}")
                Log.i("Tina", "getAllItems. size = ${it.size}")
        })




        return binding.root
    }

//    override fun onViewCreated(view: View, @Nullable savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//        view.viewTreeObserver.addOnGlobalLayoutListener {
//            val dialog = dialog as BottomSheetDialog?
//            val bottomSheet =
//                dialog!!.findViewById<FrameLayout>(com.google.android.material.R.id.design_bottom_sheet) as FrameLayout
//            val behavior = BottomSheetBehavior.from(bottomSheet)
//            behavior.state = BottomSheetBehavior.STATE_EXPANDED
//            behavior.setPeekHeight(0)
//        }
//    }

    override fun onStart() {
        super.onStart()

        val dialog = getDialog() as BottomSheetDialog
        dialog.let {
            val bottomSheet =
                it.delegate.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet)
            bottomSheet?.let { view ->
                view.layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT
            }
        }

        val view = view
        view?.let {
            it.post {
                kotlin.run {
                    val parent = it.parent as View
                    val params = parent.layoutParams as CoordinatorLayout.LayoutParams
                    val behavior = params.behavior
                    val bottomSheetBehavior = behavior as BottomSheetBehavior
                    bottomSheetBehavior.peekHeight = view.measuredHeight
                }
            }
        }
    }

    fun showAddSuccessDialog() {
        val successDialog =
            AppCompatDialog(activity) //activity is for UI, global context is for data
        successDialog.setContentView(R.layout.dialog_success_add_to_cart)
        successDialog.setCancelable(false) //cannot cancel by click
        successDialog.show()
        Handler().postDelayed({
            //After 1 second, dialog dismiss
            successDialog.dismiss()
        }, 1000)
    }


}