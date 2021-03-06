package com.intive.toz.petslist.presenter;

import com.hannesdorfmann.mosby3.mvp.MvpBasePresenter;
import com.intive.toz.data.DataLoader;
import com.intive.toz.data.DataProvider;
import com.intive.toz.petslist.model.Pet;
import com.intive.toz.petslist.view.PetsListView;

import java.util.List;

/**
 *  Presenter for pets list.
 */

public class PetsListPresenter extends MvpBasePresenter<PetsListView> {

    /**
     *  Sends a request to DataLoader. On success invokes a method displaying
     *  loaded data.
     *
     *  @param pullToRefresh boolean pullToRefresh
     */
    public void loadPetsList(final boolean pullToRefresh) {
        getView().showLoading(pullToRefresh);
        DataLoader dataLoader = new DataLoader();

        dataLoader.fetchPets(new DataProvider.ResponseCallback<List<Pet>>() {
            @Override
            public void onSuccess(final List<Pet> petsList) {
                if (isViewAttached()) {
                    getView().setData(petsList);
                    getView().showContent();
                }
            }

            @Override
            public void onError(final Throwable e) {
                if (isViewAttached()) {
                    getView().showError(e, false);
                }
            }
        });
    }
}
