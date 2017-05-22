package com.happy.girls.ui

import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.util.Log
import android.view.View
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import com.happy.girls.NetWork.GirlService
import com.happy.girls.R
import com.happy.girls.bean.Girl
import com.happy.girls.log
import com.happy.girls.ui.adapter.GirlAdapter
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import rx.android.schedulers.AndroidSchedulers
import rx.exceptions.OnErrorThrowable
import rx.internal.util.ActionSubscriber
import rx.schedulers.Schedulers
import java.util.ArrayList

class MainActivity : AppCompatActivity() {
    companion object {
        val GIRLS_BASE_URL = "http://gank.io/api/"
    }

    val mPageCount: Int = 10
    val mPageIndex: Int = 1
    var mData = ArrayList<Girl>()
    lateinit var adapter: GirlAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initRefreshLayout()
        initRecyclerView()
        getGirl()
    }

    private fun initRecyclerView() {
        girlList.layoutManager = GridLayoutManager(this, 2)
        adapter = GirlAdapter(mData, { _: View, i: Int -> jump2GirlDetail(i) })
        girlList.adapter = adapter

    }

    private fun initRefreshLayout() {
        girlRefresh.setColorSchemeColors(Color.RED)
        girlRefresh.setOnRefreshListener { getGirl() }
    }

    private fun getGirl() {
        val retrofit = Retrofit.Builder()
                .baseUrl(GIRLS_BASE_URL)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build()

        val girlService = retrofit.create(GirlService::class.java)

        girlService.getGirls(mPageCount, mPageIndex)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map { it.results }
                .subscribe({
                    girls -> mData.addAll(girls)
                })

        adapter.notifyDataSetChanged()
        //{ girls -> mData.addAll(girls) }, { adapter.notifyDataSetChanged() }
    }

    private fun jump2GirlDetail(position: Int) {

    }

    private fun showLoading() {
        progressBar.visibility = VISIBLE
    }

    private fun hideLoading() {
        progressBar.visibility = INVISIBLE
    }
}



