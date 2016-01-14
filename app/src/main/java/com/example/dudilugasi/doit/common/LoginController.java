package com.example.dudilugasi.doit.common;


import android.content.Context;

import com.example.dudilugasi.doit.dal.DAO;
import com.example.dudilugasi.doit.dal.IDataAccess;

public class LoginController {
    IDataAccess dao;
    Context context;
    public LoginController(Context context) {
        dao = DAO.getInstance(context.getApplicationContext());
        this.context = context;
    }

    public boolean isAdmin() {
        return false;
    }

    public String getUserName() {
        return "ben";
    }
}
