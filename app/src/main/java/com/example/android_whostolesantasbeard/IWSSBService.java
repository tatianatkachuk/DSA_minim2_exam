package com.example.android_whostolesantasbeard;

import com.example.android_whostolesantasbeard.Issue;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface IWSSBService {

    @POST("issue/report")
    Call<Issue> reportIssue(@Body Issue issueInfo);


}