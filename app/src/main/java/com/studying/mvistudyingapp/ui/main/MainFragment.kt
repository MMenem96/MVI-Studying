package com.studying.mvistudyingapp.ui.main

import android.content.Context
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.studying.mvistudyingapp.R
import com.studying.mvistudyingapp.databinding.FragmentMainBinding
import com.studying.mvistudyingapp.model.BlogPost
import com.studying.mvistudyingapp.model.User
import com.studying.mvistudyingapp.ui.main.state.MainStateEvent.GetBlogPostEvent
import com.studying.mvistudyingapp.ui.main.state.MainStateEvent.GetUserEvent
import com.studying.mvistudyingapp.util.TopSpacingItemDecoration

class MainFragment : Fragment(), BlogPostListAdapter.Interaction {
    lateinit var blogPostListAdapter: BlogPostListAdapter
    lateinit var viewModel: MainViewModel
    lateinit var dataStateListener: DataStateListener
    lateinit var binding: FragmentMainBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainBinding.inflate(inflater, container, false);
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
        initRecyclerView()
        initViewModel()
        subscribeObservers()
    }

    private fun initRecyclerView() {
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(activity)
            addItemDecoration(TopSpacingItemDecoration(30))
            blogPostListAdapter = BlogPostListAdapter(this@MainFragment)
            adapter = blogPostListAdapter
        }
    }

    private fun initViewModel() {
        viewModel = activity?.run {
            ViewModelProvider(this).get(MainViewModel::class.java)
        } ?: throw Exception("Invalid activity")
    }

    private fun subscribeObservers() {
        viewModel.dataState.observe(viewLifecycleOwner, Observer { dataState ->
            println("DEBUG: Datastate: $dataState")
            dataStateListener.onDataStateChange(dataState)
            //Handle success
            dataState.data?.let { event ->
                event.getContentIfNotHandled()?.let { mainViewState ->
                    mainViewState.blogPosts?.let { blogPosts ->
                        //set BlogPosts data
                        viewModel.setBlogListData(blogPosts)
                    }
                    mainViewState.user?.let { user ->
                        //set BlogPosts data
                        viewModel.setUser(user)
                    }
                }
            }
        })
        viewModel.viewState.observe(viewLifecycleOwner, Observer { viewState ->
            viewState.blogPosts?.let { blogPosts ->
                println("DEBUG: Setting blog posts to recyclerview")
                blogPostListAdapter.submitList(blogPosts)
            }
            viewState.user?.let { user ->
                println("DEBUG: Setting user data")
                setUserProperties(user)
            }
        })
    }

    private fun setUserProperties(user: User) {
        binding.username.text = user.userName
        binding.email.text = user.email
        view?.let {
            Glide.with(it.context)
                .load(user.image)
                .into(binding.image)
        }
    }

    private fun triggerGetUserEvent() {
        viewModel.setStateEvent(GetUserEvent("1"))
    }

    private fun triggerGetBlogsEvent() {
        viewModel.setStateEvent(GetBlogPostEvent())
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            dataStateListener = context as DataStateListener
        } catch (e: ClassCastException) {
            println("DEBUG: Message $context must implement DataStateListener")
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.main_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_get_user -> {
                triggerGetUserEvent()
            }
            R.id.action_get_blogs -> {
                triggerGetBlogsEvent()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onItemSelected(position: Int, item: BlogPost) {
        println("DEBUG: $position")
    }


}