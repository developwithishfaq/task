package com.test.framework.core.core

import android.view.View


fun View.makeVisible(check: Boolean = true) {
    visibility = if (check) {
        View.VISIBLE
    } else {
        View.GONE
    }
}

fun View.makeGone(check: Boolean = true) {
    visibility = if (check) {
        View.GONE
    } else {
        View.VISIBLE
    }
}