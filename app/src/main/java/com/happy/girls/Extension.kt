package com.happy.girls

import android.content.Context
import android.support.design.widget.Snackbar
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy

/**
 * Created by ChangQin on 2017/5/22 0022.
 */
fun Context.toast(msg:String,duration:Int = Toast.LENGTH_SHORT){
    Toast.makeText(this,msg,duration).show()
}
fun View.snackbar(message: String, duration: Int = Snackbar.LENGTH_SHORT) {
    Snackbar.make(this,message,duration).show()
}

fun View.snackbar(messageRes: Int, duration: Int = Snackbar.LENGTH_SHORT) {
    Snackbar.make(this,messageRes,duration).show()
}
fun ImageView.displayUrl(url:String){
    Glide.with(context).load(url).diskCacheStrategy(DiskCacheStrategy.SOURCE).into(this)
}