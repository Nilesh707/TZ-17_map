package com.technozion.tz_17;

import java.util.ArrayList;

/**
 * Created by A on 3/8/2016.
 */
public class SignupRetrofitClass {
    String inputName;
    String inputEmail;
    String inputPhone;
    String inputCollege;
    String inputGender;
    String inputCollegeId;
    String inputCity;
    String inputState;
    String inputPassword;
    ArrayList<String> ids;
    SignupRetrofitClass(ArrayList<String>ids,String Name,String Email,String Phone, String College, String Gender, String CollegeId, String City, String State, String Password)
    {
        this.ids=new ArrayList<>();
        this.ids=ids;
        this.inputName=Name;
        this.inputEmail=Email;
        this.inputPhone=Phone;
        this.inputCollege=College;
        this.inputGender=Gender;
        this.inputCollegeId=CollegeId;
        this.inputCity=City;
        this.inputState=State;
        this.inputPassword=Password;


    }
}
