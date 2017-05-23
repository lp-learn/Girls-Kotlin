package com.happy.girls.ui.girl;

import com.happy.girls.bean.Girl;
import com.happy.girls.bean.Girls;

import java.util.List;

import rx.Observable;

/**
 * Created by Administrator on 2017/5/23.
 */

public interface GirlContract {
    interface Model {
        Observable<Girls> getGirl(int pageCount, int pageIndex);
    }
    interface View{
        void showGirl(List<Girl> girls);
        void showLoading();
        void hideLoading();
    }
    interface Presenter{
        void getGirl(int pageCount, int pageIndex);
    }
}
