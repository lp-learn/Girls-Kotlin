package com.happy.girls.ui

import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import com.happy.girls.NetWork.GirlService
import com.happy.girls.R
import com.happy.girls.bean.Girl
import com.happy.girls.bean.Girls
import com.happy.girls.log
import com.happy.girls.ui.adapter.GirlAdapter
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import rx.Observable
import rx.Subscriber
import rx.android.schedulers.AndroidSchedulers
import rx.exceptions.OnErrorThrowable
import rx.internal.util.ActionSubscriber
import rx.schedulers.Schedulers
import java.util.ArrayList

class MainActivity : AppCompatActivity(),GirlContract.View{

    companion object {
        val GIRLS_BASE_URL = "http://gank.io/api/"
    }
    val mPageCount: Int = 10
    var mPageIndex: Int = 1
    var loadMoreEnable:Boolean = true
    var mData = ArrayList<Girl>()
    val mPresenter:GirlPresenter = GirlPresenter(this)
    lateinit var adapter: GirlAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initRefreshLayout()
        initRecyclerView()
        if (mData.size==0){
            getGirl()
        }
    }

    private fun initRecyclerView() {
        val layoutManager  = GridLayoutManager(this, 2)
        girlList.layoutManager = layoutManager
        adapter = GirlAdapter(mData, { _: View, i: Int -> jump2GirlDetail(i) })
        girlList.adapter = adapter
        girlList.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (dy > 0)
                //向下滚动
                {
                    val visibleItemCount = layoutManager.getChildCount()
                    val totalItemCount = layoutManager.getItemCount()
                    val pastVisiblesItems = layoutManager.findFirstVisibleItemPosition()

                    if (loadMoreEnable && visibleItemCount + pastVisiblesItems >= totalItemCount) {
                        getGirl()
                    }
                }
            }
        })
    }

    private fun initRefreshLayout() {
        girlRefresh.setColorSchemeColors(Color.RED)
        girlRefresh.setOnRefreshListener {
            mData.clear()
            mPageIndex = 0
            getGirl()
        }
    }
    private fun getGirl() {
        mPresenter.getGirl(mPageCount,mPageIndex)
        adapter.notifyDataSetChanged()
    }

    private fun jump2GirlDetail(position: Int) {

    }
    override fun showGirl(girls: MutableList<Girl>?) {
        if (girls!=null&&girls.size>0){
            mData.addAll(girls)
            adapter.notifyDataSetChanged()
            mPageIndex++
        }
    }
    override fun showLoading() {
        progressBar.visibility = VISIBLE
    }

    override fun hideLoading() {
        progressBar.visibility = INVISIBLE
        girlRefresh.isRefreshing = false
    }
}



