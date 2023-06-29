package com.geeks.presentation.base

import android.os.Bundle
import android.view.View
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.viewbinding.ViewBinding

abstract class BaseFragment<VB : ViewBinding, VM : ViewModel>(@LayoutRes layoutId: Int) :
    Fragment(layoutId) {

    protected abstract val binding: VB
    protected abstract val viewModel: VM

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initialize()
        setupRequest()
        setupListener()
        setupSubscribe()
    }

    protected open fun initialize() {}
    protected open fun setupRequest() {}
    protected open fun setupListener() {}
    protected open fun setupSubscribe() {}
}