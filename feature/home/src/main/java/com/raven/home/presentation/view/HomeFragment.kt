package com.raven.home.presentation.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.raven.core.bases.ViewState.Error
import com.raven.core.bases.ViewState.Initial
import com.raven.core.bases.ViewState.Success
import com.raven.core.networkmonitor.NetworkMonitor
import com.raven.home.R
import com.raven.home.databinding.ErrorScreenBinding
import com.raven.home.databinding.HomeFragmentBinding
import com.raven.home.databinding.HomeSuccessBinding
import com.raven.home.presentation.model.Filter
import com.raven.home.presentation.model.Period
import com.raven.home.presentation.model.PopularType
import com.raven.home.presentation.view.adapter.ArticleAdapter
import com.raven.home.presentation.viewmodel.HomeData
import com.raven.home.presentation.viewmodel.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private val homeViewModel by viewModels<HomeViewModel>()
    private lateinit var binding: HomeFragmentBinding
    private lateinit var errorBinding: ErrorScreenBinding
    private lateinit var successBinding: HomeSuccessBinding
    private lateinit var adapter: ArticleAdapter

    @Inject
    lateinit var networkMonitor: NetworkMonitor

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        if (!this@HomeFragment::binding.isInitialized) {
            binding = HomeFragmentBinding.inflate(inflater, container, false)
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.lifecycleOwner = this.viewLifecycleOwner

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {

                launch {
                    networkMonitor.isOnline.collect { isOnline ->
                        if (isOnline.not()) {
                            Snackbar.make(
                                binding.root,
                                getString(R.string.no_internet_connection),
                                Snackbar.LENGTH_LONG
                            )
                                .show()
                        }
                    }
                }

                launch {
                    homeViewModel.state.collect { state ->
                        clean()

                        when (state) {
                            is Initial -> {
                                homeViewModel.load()
                            }

                            is Success -> {
                                when {
                                    state.data.isLoading -> displayLoading()
                                    else -> displayArticles(state.data)
                                }
                            }

                            is Error -> displayError()
                        }
                    }
                }
            }
        }
    }

    private fun clean() {
        binding.loadingLayout.loadingScreen.visibility = View.GONE
        binding.articlesStub.viewStub?.visibility = View.GONE
        binding.errorStub.viewStub?.visibility = View.GONE
    }

    private fun displayLoading() {
        binding.loadingLayout.loadingScreen.visibility = View.VISIBLE
    }

    private fun displayArticles(state: HomeData) {
        initArticles()
        bindArticles(state)

        binding.articlesStub.root?.visibility = View.VISIBLE
    }

    private fun initArticles() {
        binding.articlesStub.viewStub?.apply {
            setOnInflateListener { _, inflated ->
                successBinding = HomeSuccessBinding.bind(inflated)
                if (successBinding.recyclerView.adapter == null) {
                    adapter = ArticleAdapter {
                        val directions =
                            HomeFragmentDirections.homeToArticle(it.id)
                        findNavController().navigate(directions)
                    }
                    successBinding.recyclerView.adapter = adapter
                }
            }
            visibility = View.VISIBLE
        }
    }

    private fun bindArticles(state: HomeData) {
        if (this@HomeFragment::successBinding.isInitialized) {

            val listChanged = adapter.currentList != state.articles
            adapter.submitList(state.articles) {
                if (listChanged) successBinding.recyclerView.scrollToPosition(0)
            }

            if(state.articles.isEmpty()) {
                successBinding.emptyStub.viewStub?.visibility = View.VISIBLE
                successBinding.emptyStub.root?.visibility = View.VISIBLE
            } else {
                successBinding.emptyStub.viewStub?.visibility = View.GONE
                successBinding.emptyStub.root?.visibility = View.GONE
            }

            successBinding.apply {
                viewModel = homeViewModel

                popularButton.setOnClickListener {
                    val modal = BottomSheetDialog(
                        title = getString(R.string.w_popular),
                        list = PopularType.entries.map { enumValue ->
                            Filter(enumValue.name, enumValue.printableName)
                        },
                        callback = {
                            homeViewModel.load(PopularType.valueOf(it), state.period)
                            successBinding.recyclerView.scrollToPosition(0)
                        }
                    )
                    modal.show(childFragmentManager, BottomSheetDialog.TAG)
                }

                periodButton.setOnClickListener {
                    val modal = BottomSheetDialog(
                        title = getString(R.string.w_period),
                        list = Period.entries.map { enumValue ->
                            Filter(enumValue.name, enumValue.printableName)
                        },
                        callback = {
                            homeViewModel.load(state.type, Period.valueOf(it))
                            successBinding.recyclerView.scrollToPosition(0)
                        }
                    )
                    modal.show(childFragmentManager, BottomSheetDialog.TAG)
                }

                refreshButton.setOnClickListener {
                    homeViewModel.load(state.type, state.period)
                    successBinding.recyclerView.scrollToPosition(0)
                }
            }
        }
    }

    private fun displayError() {
        binding.errorStub.viewStub?.apply {
            setOnInflateListener { _, inflated ->
                errorBinding = ErrorScreenBinding.bind(inflated)
            }
            visibility = View.VISIBLE
        }

        binding.errorStub.root?.visibility = View.VISIBLE
    }

}
