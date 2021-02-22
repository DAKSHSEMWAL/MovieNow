package com.varistor.assignment.utils

import android.content.Context
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.varistor.assignment.R

object Common {
    fun setToolbarWithBackAndTitle(
        ctx: Context,
        title: String?,
        value: Boolean?,
        backResource: Int
    ) {
        val toolbar = (ctx as AppCompatActivity).findViewById<Toolbar>(R.id.toolbar)
        ctx.setSupportActionBar(toolbar)
        val title_tv = toolbar.findViewById<TextView>(R.id.title_tv)
        val actionBar = ctx.supportActionBar
        if (actionBar != null) {
            if (backResource != 0) toolbar.setNavigationIcon(backResource)
            actionBar.setDisplayShowHomeEnabled(value!!)
            actionBar.setDisplayShowTitleEnabled(false)
            title_tv.text = title
        }
    }
}