package com.intive.toz.network;


import com.intive.toz.info.model.Info;
import com.intive.toz.login.model.Jwt;
import com.intive.toz.login.model.Login;
import com.intive.toz.news.model.News;
import com.intive.toz.petslist.model.Pet;
import com.intive.toz.schedule.model.Reservation;
import com.intive.toz.schedule.model.Reserve;
import com.intive.toz.schedule.model.Schedule;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Interface json file.
 */
public interface PetsApi {
    /**
     * return json array.
     *
     * @return /pets.json.
     */

    @GET("/pets")
    Call<List<Pet>> getGalleryPetsListCall();

    /**
     * Get pet.
     *
     * @param id pet id
     * @return pet details
     */
    @GET("/pets/{id}")
    Call<Pet> getPetDetailsCall(@Path("id") String id);

    /**
     * Gets news.
     *
     * @return the news
     */
    @GET("/news")
    Call<List<News>> getNews();

    /**
     * Call to financial data.
     *
     * @return /financial.json
     */
    @GET("/organization/Info")
    Call<Info> getFinancialInfo();

    /**
     * Get one object of detailed news by Id.
     *
     * @param id id
     * @return /news/{id} json
     */
    @GET("/news/{id}")
    Call<News> getDetailNews(@Path("id") String id);

    /**
     * get response login by send json object with login and password.
     *
     * @param loginObj contain login and password.
     * @return response body from server in JSON format.
     */
    @POST("/tokens/acquire")
    Call<Jwt> login(@Body Login loginObj);

    /**
     * Gets schedule.
     *
     * @param from the from
     * @param to   the to
     * @return the schedule
     */
    @GET("/schedule")
    Call<Schedule> getSchedule(@Query("from") String from, @Query("to") String to);

    /**
     * Reservation call.
     *
     * @param reserve the reserve
     * @return the call
     */
    @POST("/schedule")
    Call<Reservation> reservation(@Body Reserve reserve);
}


