package com.happy.girls.ui

import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.view.View
import com.happy.girls.NetWork.Api
import com.happy.girls.NetWork.GirlService
import com.happy.girls.R
import com.happy.girls.bean.Girl
import com.happy.girls.ui.adapter.GirlAdapter
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.ArrayList

class MainActivity : AppCompatActivity() {
    companion object{
        val GIRLS_BASE_URL = "http://gank.io/api/"
    }
    val mPageCount:Int = 10
    val mPageIndex:Int = 1
    var mData = ArrayList<Girl>()
    lateinit var adapter:GirlAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initRefreshLayout()
        initRecyclerView()
    }

    private fun initRecyclerView() {
        girlList.layoutManager = GridLayoutManager(this,2)
        adapter = GirlAdapter(mData,{_:View,i:Int->jump2GirlDetail(i)})
        girlList.adapter = adapter

    }

    private fun initRefreshLayout() {
        girlRefresh.setColorSchemeColors(Color.RED)
        girlRefresh.setOnRefreshListener {  }
    }
    private fun getGirl(){
        val retrofit = Retrofit.Builder()
                .baseUrl(GIRLS_BASE_URL)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build()


    }
    private fun jump2GirlDetail(position:Int){

    }
}
