package com.codepath.googleimagesearch;

import com.codepath.googleimagesearch.models.GoogleImageResponse;

import java.util.Map;

import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Query;
import retrofit.http.QueryMap;

/**
 * Created by yahuijin on 9/9/15.
 */
public interface GoogleImageService {

    @GET("/ajax/services/search/images")
    Call<GoogleImageResponse> getImages(
            @Query("v") String version,
            @Query("q") String keyword,
            @Query("rsz") int resultSize,
            @Query("start") int start,
            @QueryMap Map<String, String> filters);
}
