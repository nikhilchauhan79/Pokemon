package com.example.pokemon.utils

import android.view.View

object ExtensionUtils {
  fun View.show(){
    visibility = View.VISIBLE
  }

  fun View.hide(){
    visibility = View.GONE
  }
}