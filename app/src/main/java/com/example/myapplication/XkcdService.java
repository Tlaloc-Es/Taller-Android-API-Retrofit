package com.example.myapplication;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface XkcdService {
    @GET("{comicId}/info.0.json")
    Call<Xkcd > getComic(@Path("comicId") int comicId);
}
