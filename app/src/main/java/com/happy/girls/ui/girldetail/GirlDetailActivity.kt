package com.happy.girls.ui.girldetail

import android.annotation.TargetApi
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.support.v7.app.AlertDialog
import com.bumptech.glide.Glide
import com.happy.girls.R
import com.happy.girls.snackbar
import kotlinx.android.synthetic.main.activity_girl_detail.*
import java.io.File
import java.io.FileOutputStream

class GirlDetailActivity : AppCompatActivity() {

    lateinit var girlUrl:String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_girl_detail)

        initToolBar()

        val girlIntent = intent

        girlUrl = girlIntent.getStringExtra("girlUrl")

        loadGirl()

    }

    private fun loadGirl() {
        Glide.with(this).load(girlUrl).crossFade().into(imgGirlDetail)
        imgGirlDetail.setOnLongClickListener { shy() }
    }

    private fun initToolBar() {
        setSupportActionBar(toolBar)
        toolBar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp)
        toolBar.setNavigationOnClickListener { finish() }
    }
    private fun shy(): Boolean {
        AlertDialog.Builder(this)
                .setTitle("n(*≧▽≦*)n")
                .setMessage("是否要收了小女子呢？")
                .setNegativeButton("必须滴") { dialog, which ->saveGirl() }
                .setPositiveButton("你走吧") { dialog, which -> }
                .create()
                .show()
        return true
    }
    private fun saveGirl(){
        //查看存储状态
        val storageState = Environment.getExternalStorageState()
        if(!storageState.equals(Environment.MEDIA_MOUNTED)){
            return
        }
        //保存图片
        val DIR = File(Environment.getExternalStorageDirectory().absolutePath + "/girl/")
        if (!DIR.exists())DIR.mkdir()
        val girlName = girlUrl.substring(girlUrl.lastIndexOf('/')+1)
        val girlFile = File(DIR,girlName)
        imgGirlDetail.isDrawingCacheEnabled = true
        val girlBmp = imgGirlDetail.drawingCache
        val fos = FileOutputStream(girlFile)
        girlBmp.compress(Bitmap.CompressFormat.JPEG, 100, fos)
        fos.flush()
        fos.close()
        imgGirlDetail.isDrawingCacheEnabled = false

        //通知相册更新
        MediaStore.Images.Media.insertImage(contentResolver,
                girlFile.absolutePath, girlName, null)
        this.sendBroadcast(Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(girlFile)))

        imgGirlDetail.snackbar("收服妖精成功 n(*≧▽≦*)n")
    }


}
