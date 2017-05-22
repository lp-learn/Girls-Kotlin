package com.happy.girls.NetWork;

import com.happy.girls.bean.Girls;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by ChangQin on 2017/5/22 0022.
 */

public interface Api{
    @GET("data/福利/{pageCount}/{pageIndex}")
    Observable<Girls> getGirls(@Path("pageCount")int pageCount,
                               @Path("pageIndex")int pageIndex);
}
