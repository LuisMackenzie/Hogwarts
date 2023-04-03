package com.mackenzie.hogwarts.ui.favorite

import com.mackenzie.domain.HeadItem

interface OnItemClickListener {

    fun onClick(head: HeadItem)
    fun onLongClick(head: HeadItem)

}