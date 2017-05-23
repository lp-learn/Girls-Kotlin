package com.happy.girls.ui.girl;

import com.happy.girls.NetWork.Api;
import com.happy.girls.NetWork.GirlService;
import com.happy.girls.bean.Girls;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2017/5/23.
 */

public class GirlModel implements GirlContract.Model{
    @Override
    public Observable<Girls> getGirl(int pageCount, int pageIndex) {
        return Api.getInstance().getRetrofit().create(GirlService.class)
                .getGirls(pageCount,pageIndex)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
