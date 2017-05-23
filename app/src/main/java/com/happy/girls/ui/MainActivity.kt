package com.happy.girls.ui

import android.annotation.TargetApi
import android.app.ActivityOptions
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import com.happy.girls.R
import com.happy.girls.bean.Girl
import com.happy.girls.ui.adapter.GirlAdapter
import com.happy.girls.ui.girl.GirlContract
import com.happy.girls.ui.girl.GirlPresenter
import com.happy.girls.ui.girldetail.GirlDetailActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.item_girl.*
import java.util.ArrayList

class MainActivity : AppCompatActivity(), GirlContract.View{

    companion object {
        val GIRLS_BASE_URL = "http://gank.io/api/"
    }
    val mPageCount: Int = 10
    var mPageIndex: Int = 1
    var loadMoreEnable:Boolean = true
    var mData = ArrayList<Girl>()
    val mPresenter: GirlPresenter = GirlPresenter(this)
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

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private fun jump2GirlDetail(position: Int) {
        val intent = Intent(this,GirlDetailActivity::class.java)
        intent.putExtra("girlUrl",mData[position].url)
        startActivity(intent)
        //startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this,img_girl,"girl").toBundle())
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



