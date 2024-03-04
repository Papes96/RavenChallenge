package com.raven.home.presentation.view

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.raven.home.R
import com.raven.home.databinding.FilterBottomSheetBinding
import com.raven.home.presentation.model.Filter

class BottomSheetDialog(
    val title: String,
    val list: List<Filter>,
    val callback: (String) -> Unit
) : BottomSheetDialogFragment() {

    private lateinit var binding: FilterBottomSheetBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FilterBottomSheetBinding.inflate(inflater, container, false)
        binding.bottomSheetTitle.text = title
        binding.bottomSheetList.adapter = ArrayAdapter(
            requireContext(),
            R.layout.filter_item,
            list.map { it.printableName }
        )
        binding.bottomSheetList.setOnItemClickListener { _, _, position, _ ->
            callback(list[position].id)
            dismiss()
        }
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        // This forces the sheet to appear at max height even on landscape
        val behavior = BottomSheetBehavior.from(requireView().parent as View)
        behavior.state = BottomSheetBehavior.STATE_EXPANDED
    }

    companion object {
        const val TAG = "com.raven.home.presentation.view.BottomSheetDialog"
    }
}