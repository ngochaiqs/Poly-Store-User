package com.poly_store_user.retrofit;

import com.poly_store_user.model.NotiResponse;
import com.poly_store_user.model.NotiSendData;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface ApiPushNofication {
    //Completable sendNofitication(NotiSendData notiSendData);
    @Headers(
            {
                    "Content-Type: application/json",
                    "Authorization: key= AAAAFtY5slI:APA91bGag3wf4DeUcY4sYsbiNoFBnyRmtWyc0I7IPAIrzhFNfUzWeqf7KAYGRwVtA-756gO7C3a-uwLG4iOnRce5jDV7UWe2hsMpWbLZ2qvS25kjCGlQCEZ7Y_crOE7eVcqFGDrAG5NU"
            }
    )
    @POST("fcm/send")
    Observable<NotiResponse> sendNofitication(@Body NotiSendData data);
}
