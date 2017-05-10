package com.intive.toz.common.view.calendar.presenter;

import android.text.format.DateFormat;

import com.hannesdorfmann.mosby3.mvp.MvpBasePresenter;
import com.intive.toz.common.view.calendar.WeekMvp;
import com.intive.toz.common.view.calendar.dialogs.DialogFactory;
import com.intive.toz.data.DataLoader;
import com.intive.toz.data.DataProvider;
import com.intive.toz.schedule.model.Schedule;

import java.util.Date;


/**
 * mvp presenter for calendar activity.
 */

public class WeekPresenter extends MvpBasePresenter<WeekMvp.ButtonsView> implements WeekMvp.Presenter, DataProvider.ResponseCallback<List<Schedule>> {

    @Override
    public void loadData(final int week) {

    }


    @Override
    public void checkDate(final int position, final Date day, final int week, final boolean isMorning) {

        DialogFactory.day = day;
        DialogFactory.position = position;
        DialogFactory.isMorning = isMorning;
        DialogFactory.week = week;

        ReservedDay reservedDay = getDateObjectReserved(day);

        assert reservedDay != null;
        int resoult = isMorning ? reservedDay.getStateMorning() : reservedDay.getStateAfternoon();
        String name = isMorning ? reservedDay.getUserNameMorning() : reservedDay.getUserNameAfternoon();
        switch (resoult) {
            case 1:
                getView().showDialog(DialogFactory.infoDialog(name));
                break;
            case 2:
                getView().showDialog(DialogFactory.deleteDialog(name));
                break;
            default:
                getView().showDialog(DialogFactory.saveDialog());
                break;
        }*/
    }

    @Override
    public void setDate(final String date, final int week, final boolean isSaved, final boolean isMorning) {

        if (isSaved) {
            getView().showSnackbar();
        }
        loadData(week);
    }

    @Override
    public void fetchSchedule(final String from, final String to) {
        DataLoader dataLoader = new DataLoader();
        dataLoader.fetchSchedule(this, from, to);
    }

    /**
     * Get date.
     *
     * @param day the day
     * @return string date
     */
    private String getDate(final Date day) {
        return DateFormat.format("dd", day).toString()
                + DateFormat.format("MM", day).toString()
                + DateFormat.format("yy", day).toString();
    }

    @Override
    public void onSuccess(final List<Schedule> response) {
        //getView().setButtons(response.getReservations());
    }

    @Override
    public void onError(final Throwable e) {

    }
}
