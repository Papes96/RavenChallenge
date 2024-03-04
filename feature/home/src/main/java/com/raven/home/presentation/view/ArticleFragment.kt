package com.raven.home.presentation.view

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.navArgs
import com.raven.core.bases.ViewState
import com.raven.home.databinding.ArticleFragmentBinding
import com.raven.home.databinding.ArticleSuccessBinding
import com.raven.home.databinding.ErrorScreenBinding
import com.raven.home.presentation.model.Article
import com.raven.home.presentation.viewmodel.ArticleData
import com.raven.home.presentation.viewmodel.ArticleViewModel
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ArticleFragment : Fragment() {

    private lateinit var binding: ArticleFragmentBinding
    private lateinit var errorBinding: ErrorScreenBinding
    private lateinit var successBinding: ArticleSuccessBinding
    private val articleViewModel by viewModels<ArticleViewModel>()
    private val args by navArgs<ArticleFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = ArticleFragmentBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.lifecycleOwner = this.viewLifecycleOwner

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {

                launch {
                    articleViewModel.state.collect { state ->

                        clean()

                        when (state) {
                            is ViewState.Initial -> articleViewModel.load(args.articleId)

                            is ViewState.Success -> {
                                when {
                                    state.data.isLoading -> displayLoading()
                                    else -> displayArticle(state.data)
                                }
                            }

                            is ViewState.Error -> displayError()
                        }
                    }
                }
            }
        }
    }

    private fun clean() {
        binding.loadingLayout.loadingScreen.visibility = View.GONE
        binding.articleStub.root?.visibility = View.GONE
        binding.errorStub.root?.visibility = View.GONE
    }

    private fun displayLoading() {
        binding.loadingLayout.loadingScreen.visibility = View.VISIBLE
    }

    private fun displayArticle(state: ArticleData) {
        initArticle()

        state.article?.let {
            bindArticle(state.article)
            binding.articleStub.root?.visibility = View.VISIBLE
        } ?: run {
            displayError()
        }
    }

    private fun initArticle() {
        binding.articleStub.viewStub?.apply {
            setOnInflateListener { stub, inflated ->
                successBinding = ArticleSuccessBinding.bind(inflated)
            }
            visibility = View.VISIBLE
        }
    }

    private fun bindArticle(article: Article) {
        if (this@ArticleFragment::successBinding.isInitialized) {
            successBinding.apply {
                this.article = article

                if (article.media.isNotEmpty()) {
                    Picasso
                        .get()
                        .load(article.media)
                        .into(articleImage)
                }

                goToButton.setOnClickListener { _ ->
                    val intent = Intent(
                        Intent.ACTION_VIEW,
                        Uri.parse(article.url)
                    )
                    startActivity(intent)
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