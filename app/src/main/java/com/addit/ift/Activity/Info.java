package com.addit.ift.Activity;

/**
 * Created by user on 6/28/2016.
 */

public class Info {
    public static String USER_ID;
    final static String LOGIN_SERVER = "http://clientworksarea.com/ift/WebService.asmx/Login";
    final static String EVENT_SERVER = "http://clientworksarea.com/ift/WebService.asmx/GetEvent";
    final static String POST_EVENT_SERVER = "http://clientworksarea.com/ift/WebService.asmx/PostEvent";
    final static String REGISTER_URL = "http://clientworksarea.com/ift/WebService.asmx/UserRegistration";
    final static String FBTW_LOGIN_URL = "http://clientworksarea.com/ift/WebService.asmx/FBTWLogin";
    final static String PARTICIPATE_URL = "http://clientworksarea.com/ift/WebService.asmx/UserParticipate";
    final static String LIKE_URL = "http://clientworksarea.com/ift/WebService.asmx/UserLike";
    final static String GET_LIKE_URL = "http://clientworksarea.com/ift/WebService.asmx/GetLike";
    final static String SEARCH_URL = "http://clientworksarea.com/ift/WebService.asmx/GetSearchEvent";
    final static String IMAGE_UPLOAD_URL = "http://clientworksarea.com/ift/WebService.asmx/GetImageUploadDetail";
    final static String FRGT_PWD_URL = "http://clientworksarea.com/ift/WebService.asmx/";
    final static String UPDATE_URL = "http://clientworksarea.com/ift/WebService.asmx/UpdateUserInfo";
    final static String UPDATE_PASSWOD_URL = "http://clientworksarea.com/ift/WebService.asmx/UpdatePassword";
    String FTP_USER_NAME = "";
    String FTP_PWD = "";
    public static String PROFILE_IMAGE_LOC = null;
    public static String IMG_ROOT = null;

    public static String getEventServer() {
        return EVENT_SERVER;
    }

    public static String getForgotPWD_WB() {
        return FRGT_PWD_URL;
    }

    public static String getPostEventServer() {
        return POST_EVENT_SERVER;
    }

    public static String getUserID() {
        return USER_ID;
    }

    public static String getFBTWServer() {
        return FBTW_LOGIN_URL;
    }

    public static String getLoginServer() {
        return LOGIN_SERVER;
    }

    public static String getParticipateWB() {
        return PARTICIPATE_URL;
    }

    public static String getUpdateServer() {
        return UPDATE_URL;
    }

    public static String getupdatePWDSever() {
        return UPDATE_PASSWOD_URL;
    }

    public static String getLikeWB() {
        return LIKE_URL;
    }

    public static String getSearchWB() {
        return SEARCH_URL;
    }

    public static String getLikeRecordWB() {
        return GET_LIKE_URL;
    }

    public static String getRegisterServer() {
        return REGISTER_URL;
    }

    public static String getImageLoc() {
        return PROFILE_IMAGE_LOC;
    }

    public static String getImageRoot() {
        return IMG_ROOT;
    }

    public static String getImageUploadWB() {
        return IMAGE_UPLOAD_URL;
    }

}
