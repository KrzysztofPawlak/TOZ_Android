package com.intive.toz.info.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Model bank account information. part Info.
 */

public class BankAccount implements Serializable {
    @SerializedName("number")
    private String number;
    @SerializedName("bankName")
    private String bankName;

    /**
     * getter bank name, part object Info.
     * @return bank name.
     */
    public String getBankName() {
        return bankName;
    }

    /**
     * getter bank number, part object Info.
     * @return bank number.
     */
    public String getNumber() {
        return number;
    }
}
