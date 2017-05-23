package com.happy.girls.ui.girl;

import com.happy.girls.bean.Girl;
import com.happy.girls.bean.Girls;

import java.util.List;

import rx.Subscriber;
import rx.functions.Func1;

/**
 * Created by Administrator on 2017/5/23.
 */

public class GirlPresenter implements GirlContract.Presenter {
    private GirlContract.Model girlModel = new GirlModel();
    private GirlContract.View girlView;
    public GirlPresenter(GirlContract.View view) {
        this.girlView = view;
    }

    @Override
    public void getGirl(int pageCount, int pageIndex) {
        girlModel.getGirl(pageCount,pageIndex)
                .map(new Func1<Girls, List<Girl>>() {
                    @Override
                    public List<Girl> call(Girls girls) {
                        return girls.getResults();
                    }
                })
                .subscribe(new Subscriber<List<Girl>>() {
                    @Override
                    public void onStart() {
                        girlView.showLoading();
                    }

                    @Override
                    public void onCompleted() {
                        girlView.hideLoading();
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(List<Girl> girls) {
                        girlView.showGirl(girls);
                    }
                });
    }
}
