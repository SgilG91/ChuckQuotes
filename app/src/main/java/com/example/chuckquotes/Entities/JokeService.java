package com.example.chuckquotes.Entities;
import retrofit2.Call;
import retrofit2.http.GET;

    public interface JokeService {
        @GET("jokes/random?category=dev")
        Call<Joke> jokeNow();
}
