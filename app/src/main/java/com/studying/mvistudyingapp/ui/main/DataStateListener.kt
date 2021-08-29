package com.studying.mvistudyingapp.ui.main

import com.studying.mvistudyingapp.util.DataState

interface DataStateListener {

    fun onDataStateChange(dataState: DataState<*>?)
}