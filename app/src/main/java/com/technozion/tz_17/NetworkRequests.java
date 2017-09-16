package com.technozion.tz_17;

import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.HttpURLConnection;
import java.util.HashMap;
import java.util.Map;

public class NetworkRequests {

    public static void EventData() {
        HashMap<String, String> params = new HashMap<String, String>();
        System.out.println("eventdata");
        JsonObjectRequest req = new JsonObjectRequest(URLGenerator.getUrl(URLGenerator.eventdata), new JSONObject(params),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            if ((int) response.get("status") == HttpURLConnection.HTTP_OK) {
                                Log.e("Status is True", response.toString());
                                SplashActivity.getInstance().onSuccess(response);
                            } else if ((int) response.get("status") == HttpURLConnection.HTTP_ACCEPTED) {
                                SplashActivity.getInstance().onSuccess(response);
                            } else {
                                SplashActivity.getInstance().onRequestFailure(503, "Please Check Connectivity");
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                SplashActivity.getInstance().onRequestFailure(503, "Please Check Connectivity");
            }
        }
        ) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Authorization", "Token " + Constants.TOKEN);
                Log.e("TOKEN", Constants.TOKEN + " ");
                return headers;
            }
        };
        req.setRetryPolicy(
                new DefaultRetryPolicy(
                        DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 4,
                        0,
                        DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        ApplicationController.getInstance().
                addToRequestQueue(req);
    }


    public static void EventDataNoAuth() {
        HashMap<String, String> params = new HashMap<String, String>();
        System.out.println("eventdata");
        JsonObjectRequest req = new JsonObjectRequest(URLGenerator.getUrl(URLGenerator.eventdata), new JSONObject(params),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            if ((int) response.get("status") == HttpURLConnection.HTTP_OK) {
                                Log.e("Status is True", response.toString());
                                SplashActivity.getInstance().onSuccess(response);
                            } else if ((int) response.get("status") == HttpURLConnection.HTTP_ACCEPTED) {
                                SplashActivity.getInstance().onSuccess(response);
                            } else {
                                SplashActivity.getInstance().onRequestFailure(503, "Please Check Connectivity");
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                SplashActivity.getInstance().onRequestFailure(503, "Please Check Connectivity");
            }
        }
        );
        req.setRetryPolicy(
                new DefaultRetryPolicy(
                        DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 4,
                        0,
                        DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        ApplicationController.getInstance().
                addToRequestQueue(req);
    }

    public static void Login(String Email, String password) {
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("email", Email);
        params.put("password", password);
        System.out.println("Params are: " + params);
        JsonObjectRequest req = new JsonObjectRequest(URLGenerator.getUrl(URLGenerator.login), new JSONObject(params),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        System.out.println(response);
                        try {
                            if ((int) response.get("status") == HttpURLConnection.HTTP_ACCEPTED) {
                                Login.getInstance().Loggedin(response);
                            } else if ((int) response.get("status") == HttpURLConnection.HTTP_OK) {
                                Login.getInstance().Loggedin(response);
                            } else if ((int) response.get("status") == HttpURLConnection.HTTP_CREATED) {
                                Login.getInstance().UpdateProfile(response);
                            } else {
                                Login.getInstance().Invaliddata(response);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Login.getInstance().onRequestFailure(503, "Please Check Connectivity");
            }
        }
        );
        req.setRetryPolicy(
                new DefaultRetryPolicy(
                        DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 4,
                        0,
                        DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        ApplicationController.getInstance().
                addToRequestQueue(req);
    }

    public static void Register(String firstname, String lastname, String email, String password) {

        HashMap<String, String> params = new HashMap<String, String>();
        params.put("firstname", firstname);
        params.put("lastname", lastname);
        params.put("email", email);
        params.put("password", password);
        JsonObjectRequest req = new JsonObjectRequest(URLGenerator.getUrl(URLGenerator.register), new JSONObject(params),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            if ((int) response.get("status") == HttpURLConnection.HTTP_OK) {
                                Register.getInstance().Registered(response);
                            } else if ((int) response.get("status") == HttpURLConnection.HTTP_NOT_ACCEPTABLE) {
                                Register.getInstance().alreadyRegistered(response);
                            } else {
                                Register.getInstance().onRequestFailure(503, "Please Check Connectivity");
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Register.getInstance().onRequestFailure(503, "Please Check Connectivity");
            }
        }
        );
        req.setRetryPolicy(
                new DefaultRetryPolicy(
                        DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 4,
                        0,
                        DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        ApplicationController.getInstance().
                addToRequestQueue(req);

    }

    public static void UpdateProfile(String rcode, String phone, int clgpos, int citypos, String college, String city, String state, String Gender, String collegeid) {
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("Gender", Gender);
        params.put("Phone", phone);
        params.put("Collegeid", collegeid);
        params.put("City", String.valueOf(citypos));
        params.put("College", String.valueOf(clgpos));
        params.put("referralCode", rcode);
        params.put("PlaceOtherName", city);
        params.put("CollegeOtherName", college);
        params.put("State", state);
        JsonObjectRequest req = new JsonObjectRequest(URLGenerator.getUrl(URLGenerator.updateprofile), new JSONObject(params),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            if ((int) response.get("status") == HttpURLConnection.HTTP_OK) {
                                UpdateProfile.getInstance().Updated(response);
                            } else if ((int) response.get("status") == HttpURLConnection.HTTP_NOT_ACCEPTABLE) {
                                UpdateProfile.getInstance().Updated(response);
                            } else {
                                UpdateProfile.getInstance().onRequestFailure(503, "Please Check Connectivity");
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                UpdateProfile.getInstance().onRequestFailure(503, "Please Check Connectivity");
            }
        }
        ) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Authorization", "Token " + Constants.TOKEN);
                Log.e("TOKEN", Constants.TOKEN + " ");
                return headers;
            }
        };
        req.setRetryPolicy(
                new DefaultRetryPolicy(
                        DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 4,
                        0,
                        DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        ApplicationController.getInstance().
                addToRequestQueue(req);


    }

    public static void CheckAppUser() {
        HashMap<String, String> params = new HashMap<String, String>();
        JsonObjectRequest req = new JsonObjectRequest(URLGenerator.getUrl(URLGenerator.checkappuser), new JSONObject(params),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        System.out.println(response);
                        try {
                            if ((int) response.get("status") == HttpURLConnection.HTTP_OK) {
                                Profile.getInstance().onRegistered(response);
                            } else if ((int) response.get("status") == HttpURLConnection.HTTP_NOT_ACCEPTABLE) {
                                Profile.getInstance().onNotRegistered(response);
                            } else {
                                Profile.getInstance().onRequestFailure(503, "Please Check Connectivity");
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Profile.getInstance().onRequestFailure(503, "Please Check Connectivity");
            }
        }
        ) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Authorization", "Token " + Constants.TOKEN);
                Log.e("TOKEN", Constants.TOKEN + " ");
                return headers;
            }
        };
        req.setRetryPolicy(
                new DefaultRetryPolicy(
                        DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 4,
                        0,
                        DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        ApplicationController.getInstance().
                addToRequestQueue(req);
    }

    public static void RegCheck() {
        HashMap<String, String> params = new HashMap<String, String>();
        JsonObjectRequest req = new JsonObjectRequest(URLGenerator.getUrl(URLGenerator.regcheck), new JSONObject(params),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        System.out.println(response);
                        try {
                            if ((int) response.get("status") == HttpURLConnection.HTTP_OK) {
                                System.out.println(response);
                                Profile.getInstance().Registered(response);
                            } else if ((int) response.get("status") == HttpURLConnection.HTTP_ACCEPTED) {
                                System.out.println(response);
                                Profile.getInstance().NotPaid(response);
                            } else {
                                Profile.getInstance().onRequestFailure(503, "Please Check Connectivity");
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Profile.getInstance().onRequestFailure(503, "Please Check Connectivity");
            }
        }
        ) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Authorization", "Token " + Constants.TOKEN);
                Log.e("TOKEN", Constants.TOKEN + " ");
                return headers;
            }
        };
        req.setRetryPolicy(
                new DefaultRetryPolicy(
                        DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 4,
                        0,
                        DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        ApplicationController.getInstance().
                addToRequestQueue(req);
    }

    public static void ReglogCheck() {
        HashMap<String, String> params = new HashMap<String, String>();
        JsonObjectRequest req = new JsonObjectRequest(URLGenerator.getUrl(URLGenerator.regcheck), new JSONObject(params),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            System.out.println(response);
                            if ((int) response.get("status") == HttpURLConnection.HTTP_OK) {
                                Login.getInstance().Registered(response);
                            } else if ((int) response.get("status") == HttpURLConnection.HTTP_ACCEPTED) {
                                Login.getInstance().NotPaid(response);
                            } else {
                                Login.getInstance().onRequestFailure(503, "Please Check Connectivity");
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Login.getInstance().onRequestFailure(503, "Please Check Connectivity");
            }
        }
        ) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Authorization", "Token " + Constants.TOKEN);
                Log.e("TOKEN", Constants.TOKEN + " ");
                return headers;
            }
        };
        req.setRetryPolicy(
                new DefaultRetryPolicy(
                        DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 4,
                        0,
                        DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        ApplicationController.getInstance().
                addToRequestQueue(req);
    }

//    public static void AddEvent(String id) {
//        HashMap<String, String> params = new HashMap<String, String>();
//        params.put("eventid", id);
//        JsonObjectRequest req = new JsonObjectRequest(URLGenerator.getUrl(URLGenerator.addevent), new JSONObject(params),
//                new Response.Listener<JSONObject>() {
//                    @Override
//                    public void onResponse(JSONObject response) {
//                        try {
//                            if ((int) response.get("status") == HttpURLConnection.HTTP_OK) {
//                                System.out.println(response);
//<<<<<<< HEAD
//                                EventRegister.getInstance().onSuccessAddition(response);
//                            } else if ((int) response.get("status") == HttpURLConnection.HTTP_CREATED) {
//                                EventRegister.getInstance().payment(response);
//=======
//                                //EventRegister.getInstance().onSuccessAddition(response);
//>>>>>>> 951ba85ac588ebc4dfeaaa0285a61e8592b10c57
//                            } else if ((int) response.get("status") == HttpURLConnection.HTTP_NOT_ACCEPTABLE) {
//                                //EventRegister.getInstance().onSuccessAddition(response);
//                            } else {
//                                //EventRegister.getInstance().onRequestFailure(503, "Please Check Connectivity");
//                            }
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                //EventRegister.getInstance().onRequestFailure(503, "Please Check Connectivity");
//            }
//        }
//        ) {
//            @Override
//            public Map<String, String> getHeaders() throws AuthFailureError {
//                HashMap<String, String> headers = new HashMap<String, String>();
//                headers.put("Authorization", "Token " + Constants.TOKEN);
//                Log.e("TOKEN", Constants.TOKEN + " ");
//                return headers;
//            }
//        };
//        req.setRetryPolicy(
//                new DefaultRetryPolicy(
//                        DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 4,
//                        0,
//                        DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
//        ApplicationController.getInstance().
//                addToRequestQueue(req);
//    }

    public static void getReferal() {
        HashMap<String, String> params = new HashMap<String, String>();
        JsonObjectRequest req = new JsonObjectRequest(URLGenerator.getUrl(URLGenerator.ref), new JSONObject(params),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            if ((int) response.get("status") == HttpURLConnection.HTTP_OK) {
                                System.out.println(response);
                                Referral.getInstance().onSuccessRef(response);
                            } else if ((int) response.get("status") == HttpURLConnection.HTTP_NOT_ACCEPTABLE) {
                                Referral.getInstance().onLogerrorRef(response);
                            } else {
                                Referral.getInstance().onRequestFailure(503, "Please Check Connectivity");
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Referral.getInstance().onRequestFailure(503, "Please Check Connectivity");
            }
        }
        ) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Authorization", "Token " + Constants.TOKEN);
                Log.e("TOKEN", Constants.TOKEN + " ");
                return headers;
            }
        };
        req.setRetryPolicy(
                new DefaultRetryPolicy(
                        DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 4,
                        0,
                        DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        ApplicationController.getInstance().
                addToRequestQueue(req);
    }

    public static void addAppReg() {
        HashMap<String, String> params = new HashMap<String, String>();
        JsonObjectRequest req = new JsonObjectRequest(URLGenerator.getUrl(URLGenerator.addapppreg), new JSONObject(params),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            if ((int) response.get("status") == HttpURLConnection.HTTP_OK) {
                                System.out.println(response);
                                Profile.getInstance().onRegRespSuccess(response);
                            } else if ((int) response.get("status") == HttpURLConnection.HTTP_NOT_ACCEPTABLE) {
                                Profile.getInstance().onRegRespSuccess(response);
                            } else {
                                Profile.getInstance().onRequestFailure(503, "Please Check Connectivity");
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Profile.getInstance().onRequestFailure(503, "Please Check Connectivity");
            }
        }
        ) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Authorization", "Token " + Constants.TOKEN);
                Log.e("TOKEN", Constants.TOKEN + " ");
                return headers;
            }
        };
        req.setRetryPolicy(
                new DefaultRetryPolicy(
                        DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 4,
                        0,
                        DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        ApplicationController.getInstance().
                addToRequestQueue(req);
    }

    public static void addAppWorkshop(String id, String tzids) {

        HashMap<String, String> params = new HashMap<String, String>();
        params.put("eventid", id);
        params.put("tzids", tzids);
        JsonObjectRequest req = new JsonObjectRequest(URLGenerator.getUrl(URLGenerator.addappworkshop), new JSONObject(params),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            if ((int) response.get("status") == HttpURLConnection.HTTP_OK) {
                                EventRegister.getInstance().onRegRespSuccess(response);
                            } else if ((int) response.get("status") == HttpURLConnection.HTTP_NOT_ACCEPTABLE) {
                                EventRegister.getInstance().onRegRespSuccess(response);
                            } else {
                                EventRegister.getInstance().onRequestFailure(503, "Please Check Connectivity");
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                EventRegister.getInstance().onRequestFailure(503, "Please Check Connectivity");
            }
        }
        ) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Authorization", "Token " + Constants.TOKEN);
                Log.e("TOKEN", Constants.TOKEN + " ");
                return headers;
            }
        };
        req.setRetryPolicy(
                new DefaultRetryPolicy(
                        DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 4,
                        0,
                        DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        ApplicationController.getInstance().
                addToRequestQueue(req);
    }


    public static void addAppHosp() {
        HashMap<String, String> params = new HashMap<String, String>();
        JsonObjectRequest req = new JsonObjectRequest(URLGenerator.getUrl(URLGenerator.addappphosp), new JSONObject(params),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            if ((int) response.get("status") == HttpURLConnection.HTTP_OK) {
                                System.out.println(response);
                                Profile.getInstance().onRegRespSuccess(response);
                            } else if ((int) response.get("status") == HttpURLConnection.HTTP_NOT_ACCEPTABLE) {
                                Profile.getInstance().onRegRespSuccess(response);
                            } else {
                                Profile.getInstance().onRequestFailure(503, "Please Check Connectivity");
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Profile.getInstance().onRequestFailure(503, "Please Check Connectivity");
            }
        }
        ) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Authorization", "Token " + Constants.TOKEN);
                Log.e("TOKEN", Constants.TOKEN + " ");
                return headers;
            }
        };
        req.setRetryPolicy(
                new DefaultRetryPolicy(
                        DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 4,
                        0,
                        DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        ApplicationController.getInstance().
                addToRequestQueue(req);
    }

    public static void addAppBoth() {
        HashMap<String, String> params = new HashMap<String, String>();
        JsonObjectRequest req = new JsonObjectRequest(URLGenerator.getUrl(URLGenerator.addapppboth), new JSONObject(params),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            if ((int) response.get("status") == HttpURLConnection.HTTP_OK) {
                                System.out.println(response);
                                Profile.getInstance().onRegRespSuccess(response);
                            } else if ((int) response.get("status") == HttpURLConnection.HTTP_NOT_ACCEPTABLE) {
                                Profile.getInstance().onRegRespSuccess(response);
                            } else {
                                Profile.getInstance().onRequestFailure(503, "Please Check Connectivity");
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Profile.getInstance().onRequestFailure(503, "Please Check Connectivity");
            }
        }
        ) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Authorization", "Token " + Constants.TOKEN);
                Log.e("TOKEN", Constants.TOKEN + " ");
                return headers;
            }
        };
        req.setRetryPolicy(
                new DefaultRetryPolicy(
                        DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 4,
                        0,
                        DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        ApplicationController.getInstance().
                addToRequestQueue(req);
    }


    public static void getCollegeList(int city) {

        HashMap<String, String> params = new HashMap<String, String>();
        JsonObjectRequest req = new JsonObjectRequest(URLGenerator.getUrl(URLGenerator.getcollegelist + city + "/"), new JSONObject(params),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            if ((int) response.get("status") == HttpURLConnection.HTTP_OK) {
                                UpdateProfile.getInstance().onCollegeListSuccess(response);
                            } else if ((int) response.get("status") == HttpURLConnection.HTTP_NOT_ACCEPTABLE) {
                                UpdateProfile.getInstance().onCollegeListSuccess(response);
                            } else {
                                UpdateProfile.getInstance().onRequestFailure(503, "Please Check Connectivity");
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                UpdateProfile.getInstance().onRequestFailure(503, "Please Check Connectivity");
            }
        }
        ) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Authorization", "Token " + Constants.TOKEN);
                Log.e("TOKEN", Constants.TOKEN + " ");
                return headers;
            }
        };
        req.setRetryPolicy(
                new DefaultRetryPolicy(
                        DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 4,
                        0,
                        DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        ApplicationController.getInstance().
                addToRequestQueue(req);
    }

    public static void validateAppTzid(String tzid) {

        HashMap<String, String> params = new HashMap<String, String>();
        params.put("tzid", tzid);
        JsonObjectRequest req = new JsonObjectRequest(URLGenerator.getUrl(URLGenerator.validatetzid), new JSONObject(params),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            if ((int) response.get("status") == HttpURLConnection.HTTP_OK) {
                                EventRegister.getInstance().onValidationSuccess();
                            } else if ((int) response.get("status") == HttpURLConnection.HTTP_NOT_ACCEPTABLE) {
                                EventRegister.getInstance().onValidationFailure(response);
                            } else {
                                EventRegister.getInstance().onRequestFailure(503, "Please Check Connectivity");
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                EventRegister.getInstance().onRequestFailure(503, "Please Check Connectivity");
            }
        }
        ) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Authorization", "Token " + Constants.TOKEN);
                Log.e("TOKEN", Constants.TOKEN + " ");
                return headers;
            }
        };
        req.setRetryPolicy(
                new DefaultRetryPolicy(
                        DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 4,
                        0,
                        DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        ApplicationController.getInstance().
                addToRequestQueue(req);
    }

    public static void addAppEvent(String tzids, String eventid) {

        HashMap<String, String> params = new HashMap<String, String>();
        params.put("tzid", tzids);
        params.put("eventid", eventid);
        JsonObjectRequest req = new JsonObjectRequest(URLGenerator.getUrl(URLGenerator.addappevent), new JSONObject(params),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            if ((int) response.get("status") == HttpURLConnection.HTTP_OK) {
                                EventRegister.getInstance().onEventSuccess(response);
                            } else if ((int) response.get("status") == HttpURLConnection.HTTP_NOT_ACCEPTABLE) {
                                EventRegister.getInstance().onEventFailure(response);
                            } else {
                                EventRegister.getInstance().onRequestFailure(503, "Please Check Connectivity");
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                EventRegister.getInstance().onRequestFailure(503, "Please Check Connectivity");
            }
        }
        ) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Authorization", "Token " + Constants.TOKEN);
                Log.e("TOKEN", Constants.TOKEN + " ");
                return headers;
            }
        };
        req.setRetryPolicy(
                new DefaultRetryPolicy(
                        DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 4,
                        0,
                        DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        ApplicationController.getInstance().
                addToRequestQueue(req);
    }

    public static void validateindiAppTzid(String id1, final int i) {
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("tzid", id1);
        JsonObjectRequest req = new JsonObjectRequest(URLGenerator.getUrl(URLGenerator.validateindiid), new JSONObject(params),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            if ((int) response.get("status") == HttpURLConnection.HTTP_OK) {
                                System.out.println(response);
                                EventRegister.getInstance().onIndividualIdSuccess(i, response);
                            } else if ((int) response.get("status") == HttpURLConnection.HTTP_NOT_ACCEPTABLE) {
                                EventRegister.getInstance().onIndividualFailure(i);
                            } else if ((int) response.get("status") == HttpURLConnection.HTTP_CREATED) {
                                EventRegister.getInstance().sameids(response, i);
                            } else {
                                EventRegister.getInstance().onRequestFailure(503, "Please Check Connectivity");
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                EventRegister.getInstance().onRequestFailure(503, "Please Check Connectivity");
            }
        }
        ) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Authorization", "Token " + Constants.TOKEN);
                Log.e("TOKEN", Constants.TOKEN + " ");
                return headers;
            }
        };
        req.setRetryPolicy(
                new DefaultRetryPolicy(
                        DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 4,
                        0,
                        DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        ApplicationController.getInstance().
                addToRequestQueue(req);
    }

    public static void getAllEvents() {
        HashMap<String, String> params = new HashMap<String, String>();
        JsonObjectRequest req = new JsonObjectRequest(URLGenerator.getUrl(URLGenerator.allevents), new JSONObject(params),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            if ((int) response.get("status") == HttpURLConnection.HTTP_OK) {
                                System.out.println(response);
                                Profile.getInstance().onEventSuccess(response);
                            } else if ((int) response.get("status") == HttpURLConnection.HTTP_NOT_ACCEPTABLE) {
                                Profile.getInstance().onEventSuccess(response);
                            } else {
                                Profile.getInstance().onRequestFailure(503, "Please Check Connectivity");
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Profile.getInstance().onRequestFailure(503, "Please Check Connectivity");
            }
        }
        ) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Authorization", "Token " + Constants.TOKEN);
                Log.e("TOKEN", Constants.TOKEN + " ");
                return headers;
            }
        };
        req.setRetryPolicy(
                new DefaultRetryPolicy(
                        DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 4,
                        0,
                        DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        ApplicationController.getInstance().
                addToRequestQueue(req);
    }

    public static void getLocation(String eventid) {
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("eventid", eventid);
        JsonObjectRequest req = new JsonObjectRequest(URLGenerator.getUrl(URLGenerator.getAppLocation), new JSONObject(params),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            if ((int) response.get("status") == HttpURLConnection.HTTP_OK) {
                                Event_main.getInstance().onLocationSuccess(response);
                            } else if ((int) response.get("status") == HttpURLConnection.HTTP_NOT_ACCEPTABLE) {
                                Event_main.getInstance().onLocationSuccess(response);
                            } else {
                                Event_main.getInstance().onRequestFailure(503, "Please Check Connectivity");
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Event_main.getInstance().onRequestFailure(503, "Please Check Connectivity");
            }
        }
        ) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Authorization", "Token " + Constants.TOKEN);
                Log.e("TOKEN", Constants.TOKEN + " ");
                return headers;
            }
        };
        req.setRetryPolicy(
                new DefaultRetryPolicy(
                        DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 4,
                        0,
                        DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        ApplicationController.getInstance().
                addToRequestQueue(req);
    }

    public static void getNoAuthLocation(String eventid) {
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("eventid", eventid);
        JsonObjectRequest req = new JsonObjectRequest(URLGenerator.getUrl(URLGenerator.getAppLocation), new JSONObject(params),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            if ((int) response.get("status") == HttpURLConnection.HTTP_OK) {
                                Event_main.getInstance().onLocationSuccess(response);
                            } else if ((int) response.get("status") == HttpURLConnection.HTTP_NOT_ACCEPTABLE) {
                                Event_main.getInstance().onLocationSuccess(response);
                            } else {
                                Event_main.getInstance().onRequestFailure(503, "Please Check Connectivity");
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Event_main.getInstance().onRequestFailure(503, "Please Check Connectivity");
            }
        }
        );
        req.setRetryPolicy(
                new DefaultRetryPolicy(
                        DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 4,
                        0,
                        DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        ApplicationController.getInstance().
                addToRequestQueue(req);
    }


    public static void Checkdatetime(final String event_id) {

        HashMap<String, String> params = new HashMap<String, String>();
        params.put("eventid", event_id);
        JsonObjectRequest req = new JsonObjectRequest(URLGenerator.getUrl(URLGenerator.getappdatetime), new JSONObject(params),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            if ((int) response.get("status") == HttpURLConnection.HTTP_OK) {
                                Event_main.getInstance().onDateTimeSuccess(response, event_id);
                            } else if ((int) response.get("status") == HttpURLConnection.HTTP_NOT_ACCEPTABLE) {
                                Event_main.getInstance().onDateTimeSuccess(response, event_id);
                            } else {
                                Event_main.getInstance().onDateTimeError(503, "Please Check Connectivity");
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Event_main.getInstance().onDateTimeError(503, "Please Check Connectivity");
            }
        }
        ) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Authorization", "Token " + Constants.TOKEN);
                Log.e("TOKEN", Constants.TOKEN + " ");
                return headers;
            }
        };
        req.setRetryPolicy(
                new DefaultRetryPolicy(
                        DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 4,
                        0,
                        DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        ApplicationController.getInstance().
                addToRequestQueue(req);


    }

    public static void CheckNoAuthdatetime(final String event_id) {

        HashMap<String, String> params = new HashMap<String, String>();
        params.put("eventid", event_id);
        JsonObjectRequest req = new JsonObjectRequest(URLGenerator.getUrl(URLGenerator.getappdatetime), new JSONObject(params),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            if ((int) response.get("status") == HttpURLConnection.HTTP_OK) {
                                Event_main.getInstance().onDateTimeSuccess(response, event_id);
                            } else if ((int) response.get("status") == HttpURLConnection.HTTP_NOT_ACCEPTABLE) {
                                Event_main.getInstance().onDateTimeSuccess(response, event_id);
                            } else {
                                Event_main.getInstance().onDateTimeError(503, "Please Check Connectivity");
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Event_main.getInstance().onDateTimeError(503, "Please Check Connectivity");
            }
        }
        );
        req.setRetryPolicy(
                new DefaultRetryPolicy(
                        DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 4,
                        0,
                        DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        ApplicationController.getInstance().
                addToRequestQueue(req);


    }


    public static void getNewEvents(String ids) {
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("event_ids", ids);
        JsonObjectRequest req = new JsonObjectRequest(URLGenerator.getUrl(URLGenerator.addnewevents), new JSONObject(params),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            if ((int) response.get("status") == HttpURLConnection.HTTP_OK) {
                                MainActivity.getInstance().onEventSuccess(response);
                            } else if ((int) response.get("status") == HttpURLConnection.HTTP_NOT_ACCEPTABLE) {
                                MainActivity.getInstance().onNoEventSuccess(response);
                            } else {
                                MainActivity.getInstance().onRequestFailure(503, "Please Check Connectivity");
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                MainActivity.getInstance().onRequestFailure(503, "Please Check Connectivity");
            }
        }
        ) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Authorization", "Token " + Constants.TOKEN);
                Log.e("TOKEN", Constants.TOKEN + " ");
                return headers;
            }
        };
        req.setRetryPolicy(
                new DefaultRetryPolicy(
                        DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 4,
                        0,
                        DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        ApplicationController.getInstance().
                addToRequestQueue(req);
    }

    public static void getNoAuthNewEvents(String ids) {
        HashMap<String, String> params = new HashMap<String, String>();
        params.put("event_ids", ids);
        JsonObjectRequest req = new JsonObjectRequest(URLGenerator.getUrl(URLGenerator.addnewevents), new JSONObject(params),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            if ((int) response.get("status") == HttpURLConnection.HTTP_OK) {
                                MainActivity.getInstance().onEventSuccess(response);
                            } else if ((int) response.get("status") == HttpURLConnection.HTTP_NOT_ACCEPTABLE) {
                                MainActivity.getInstance().onNoEventSuccess(response);
                            } else {
                                MainActivity.getInstance().onRequestFailure(503, "Please Check Connectivity");
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                MainActivity.getInstance().onRequestFailure(503, "Please Check Connectivity");
            }
        }
        );
        req.setRetryPolicy(
                new DefaultRetryPolicy(
                        DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 4,
                        0,
                        DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        ApplicationController.getInstance().
                addToRequestQueue(req);
    }

//    public static void getAllEvents() {
//        HashMap<String, String> params = new HashMap<String, String>();
//        JsonObjectRequest req = new JsonObjectRequest(URLGenerator.getUrl(URLGenerator.allevents), new JSONObject(params),
//                new Response.Listener<JSONObject>() {
//                    @Override
//                    public void onResponse(JSONObject response) {
//                        try {
//                            if ((int) response.get("status") == HttpURLConnection.HTTP_OK) {
//                                Profile.getInstance().onEventSuccess(response);
//                            } else if ((int) response.get("status") == HttpURLConnection.HTTP_ACCEPTED) {
//                                Profile.getInstance().onEventSuccess(response);
//                            } else {
//                                Profile.getInstance().onRequestFailure(503, "Please Check Connectivity");
//                            }
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                Profile.getInstance().onRequestFailure(503, "Please Check Connectivity");
//            }
//        }
//        ){
//            @Override
//            public Map<String, String> getHeaders() throws AuthFailureError {
//                HashMap<String, String> headers = new HashMap<String, String>();
//                headers.put("Authorization", "Token " + Constants.TOKEN);
//                Log.e("TOKEN", Constants.TOKEN + " ");
//                return headers;
//            }
//        };
//        req.setRetryPolicy(
//                new DefaultRetryPolicy(
//                        DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 4,
//                        0,
//                        DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
//        ApplicationController.getInstance().
//                addToRequestQueue(req);
//    }

}