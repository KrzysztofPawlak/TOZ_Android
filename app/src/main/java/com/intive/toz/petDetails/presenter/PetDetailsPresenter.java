package com.intive.toz.petDetails.presenter;

import com.hannesdorfmann.mosby3.mvp.MvpBasePresenter;
import com.intive.toz.data.DataLoader;
import com.intive.toz.data.DataProvider;
import com.intive.toz.data.DateFormatter;
import com.intive.toz.info.model.Help;
import com.intive.toz.info.model.Info;
import com.intive.toz.network.ApiClient;
import com.intive.toz.petDetails.model.Comment;
import com.intive.toz.petDetails.view.PetDetailsView;
import com.intive.toz.petslist.model.Pet;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Presenter for Pet Details.
 */
public class PetDetailsPresenter extends MvpBasePresenter<PetDetailsView> {

    private DateFormatter dateFormatter = new DateFormatter();

    /**
     * Load pet details.
     *
     * @param petID pet id
     */
    public void loadPetsDetails(final String petID) {
        getView().showProgress();
        DataLoader dataLoader = new DataLoader();

        dataLoader.fetchPetDetails(petID, new DataProvider.ResponseCallback<Pet>() {
            @Override
            public void onSuccess(final Pet pet) {
                if (isViewAttached()) {
                    getView().hideProgress();
                    getView().showPetDetails(pet, dateFormatter.convertToDate(pet.getCreated()));
                }
            }

            @Override
            public void onError(final Throwable e) {
                if (isViewAttached()) {
                    getView().showError(e);
                }
            }
        });
    }

    /**
     * Load financial data.
     */
    public void loadFinancialData() {
        getView().showProgressHelp();
        Call<Info> call = ApiClient.getPetsApiService().getFinancialInfo();
        call.enqueue(new Callback<Info>() {
            @Override
            public void onResponse(final Call<Info> call, final Response<Info> response) {
                if (response.isSuccessful()) {
                    getView().setFinancialData(response.body());
                    getView().hideProgressHelp();
                }
            }

            @Override
            public void onFailure(final Call<Info> call, final Throwable t) {
                getView().hideProgressHelp();
            }
        });
    }


    /**
     * Load how to donate data.
     */
    public void loadHowToDonateData() {
        getView().showProgressHelp();
        Call<Help> call = ApiClient.getPetsApiService().getDonateInfo();
        call.enqueue(new Callback<Help>() {
            @Override
            public void onResponse(final Call<Help> call, final Response<Help> response) {
                if (response.isSuccessful()) {
                    getView().setDonateInfo(response.body());
                    getView().hideProgressHelp();
                }
            }

            @Override
            public void onFailure(final Call<Help> call, final Throwable t) {
                getView().hideProgressHelp();
            }
        });
    }

    /**
     * Load comments.
     *
     * @param id the id
     */
    public void loadComments(final String id) {
        DataLoader dataLoader = new DataLoader();
        dataLoader.petComments(new DataProvider.ResponseCallback<List<Comment>>() {
            @Override
            public void onSuccess(final List<Comment> response) {
                getView().showComments(response);
            }

            @Override
            public void onError(final Throwable e) {

            }
        }, id, "ACTIVE");
    }

    /**
     * Add comment.
     *
     * @param comment the comment
     */
    public void addComment(final Comment comment) {
        DataLoader dataLoader = new DataLoader();
        dataLoader.addComment(new DataProvider.ResponseCallback<Comment>() {
            @Override
            public void onSuccess(final Comment response) {
                getView().onAddCommentSuccess();
            }

            @Override
            public void onError(final Throwable e) {
                getView().onAddCommentError();
            }
        }, comment);
    }
}
