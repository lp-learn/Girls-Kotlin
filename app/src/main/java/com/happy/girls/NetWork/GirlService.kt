package com.happy.girls.NetWork

import com.happy.girls.bean.Girls
import retrofit2.http.GET
import retrofit2.http.Path
import rx.Observable

/**
 * Created by ChangQin on 2017/5/22 0022.
 */
interface GirlService {

    @GET("data/福利/{pageCount}/{pageIndex}")
    fun getGirls(@Path("pageCount") pageIndex: Int,
                 @Path("pageIndex") pageCount: Int): Observable<Girls>
}