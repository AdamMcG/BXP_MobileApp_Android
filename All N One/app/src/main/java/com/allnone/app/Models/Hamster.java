package com.allnone.app.Models;

/**
 * Created by testing on 06/08/15.
 * This class is to hold the list of hamster errors one can get.
 */
public class Hamster
{
    private String name;
    private int detail;

    //region Accessor Methods
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getDetail() {
        return detail;
    }

    public void setDetail(int detail) {
        this.detail = detail;
    }
    //endregion
}
