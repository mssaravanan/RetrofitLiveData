package com.kotlin.livedata.constants

object Constants {

    val PREF_FILE_NAME = ("KAL5" + "_PREF").replace(" ", "_").toUpperCase()

    //val BASE_URL = "http://kal5-dev.augustasoftsol.com/api/" //Dev
   // val BASE_URL = "http://kal5qa.augusta-stg.com/api/" //QA
    val BASE_URL  = "http://kal5-client.augusta-stg.com/api/" //Staging

    val TERMS = "http://kal5-dev.augustasoftsol.com/Files/TermsConditions.html"
    val PRIVACY_POLICY = "http://kal5-dev.augustasoftsol.com/Files/PrivacyPolicy.html"
    val ABOUT_US = "http://www.tastenumber.com/about-us.html"

    val USER_TOKEN = "userToken"

    val INSTAGRAM_CLIENT_ID = "18cf01e0a88e46099e5f1d45d59fefe1"
    val INSTAGRAM_CLIENT_SECRET = "cb1c1df49f7845e092219d5a19defe9b"
    val INSTAGRAM_REDIRECT_URI = "https://augustahitech.com"

    val POST_SIGN_UP_URL = "accounts/TasterUserRegister"
    val POST_SIGN_UP_REQUEST = 3

    val POST_CHECK_SOCIAL_URL = "accounts/SocialRegister"
    val POST_CHECK_SOCIAL_REQUEST = 4

    val POST_DELETE_USER_URL = "accounts/DeleteTaster"
    val POST_DELETE_USER_REQUEST = 5

    val POST_PASSWORD_RESET = "accounts/TasterReset"
    val POST_PASSWORD_RESET_REQUEST = 6

    val POST_SIGN_IN_URL = "accounts/TasterSignIn"
    val POST_SIGN_IN_REQUEST = 1

    val POST_FORGET_USER_URL = "accounts/forgotTaster"
    val POST_FORGET_USER_REQUEST = 2

    val POST_UPDATE_NAME_URL = "accounts/updateName"
    val POST_UPDATE_NAME_REQUEST = 7

    val POST_UPDATE_IMAGE_URL = "accounts/editPicture"
    val POST_UPDATE_IMAGE_REQUEST = 8

    val POST_WINERIES_URL = "KAL5Winery/UUIDWineries"
    val POST_WINERIES_REQUEST = 11

    val GET_FLIGHTS_URL = "KAL5Winery/flights?wineryId="
    val GET_FLIGHTS_REQUEST = 12

    val POST_FLIGHT_WINES_URL = "KAL5Winery/flightWines"
    val POST_FLIGHT_WINES_REQUEST = 13

    val POST_ADD_OUTSIDE_WINE = "KAL5Wine/AddPhotoOutside"
    val POST_ADD_OUTSIDE_WINE_REQUEST = 14

    val POST_WINE_DETAILS_URL = "KAL5Winery/wineDetails"
    val POST_WINE_DETAILS_REQUEST = 15

    val POST_WINE_RATING_URL = "KAL5Wine/Rating"
    val POST_WINE_RATING_REQUEST = 16

    val POST_SEARCH_WINE_URL = "KAL5Wine/SearchWine"
    val POST_SEARCH_WINE_REQUEST = 17

    val POST_SEARCH_EVENT_URL = "KAL5Wine/SearchEvent"
    val POST_SEARCH_EVENT_REQUEST = 18

    val POST_SEARCH_WINERY_URL = "KAL5Wine/SearchWinery"
    val POST_SEARCH_WINERY_REQUEST = 19

    val POST_VIEW_HISTORY_DETAIL_URL = "KAL5Wine/viewhistory"
    val POST_VIEW_HISTORY__DETAIL_REQUEST = 20

    val POST_VIEW_WINE_HISTORY_URL = "KAL5Wine/viewWineHistory"
    val POST_VIEW_WINE_HISTORY_REQUEST = 21

    val POST_VIEW_TASTING_HISTORIES = "KAL5Wine/TastingHistories"
    val POST_VIEW_TASTING_HISTORIES_REQUEST = 22

    val POST_VIEW_UPCOMING_EVENTS = "KAL5Wine/UpcomingEvents"
    val POST_VIEW_UPCOMING_EVENTS_REQUEST = 23


    val POST_VIEW_ADD_COMMENT = "KAL5Wine/AddComment"
    val POST_VIEW_ADD_COMMENT_REQUEST = 24

    val POST_ACC_RESEND_MAIL = "accounts/resendConfirmationEmail?userEmail="
    val POST_ACC_RESEND_MAIL_REQUEST = 25

    val POST_ACC_IS_MAIL_EXISTS = "accounts/IsEmailExists?userEmail="
    val POST_ACC_IS_MAIL_EXISTS_REQUEST = 26

    val POST_ADD_PHOTO_EVENT = "KAL5Wine/EventSave"
    val POST_ADD_PHOTO_EVENT_REQUEST = 27

    val POST_SET_NOTIFICATION_SETTING_URL = "accounts/TasterNotificationSetting"
    val POST_SET_NOTIFICATION_SETTING_REQUEST = 28

    val POST_GET_NOTIFICATION_SETTING_URL = "accounts/GetNotificationSettings"
    val POST_GET_NOTIFICATION_SETTING_REQUEST = 29

    val POST_PHOTOS_LIST_URL = "KAL5Wine/GetPhotos"
    val POST_PHOTOS_LIST_REQUEST = 30

    val POST_PHOTOS_DETAIL_URL = "KAL5Wine/ViewDetails"
    val POST_PHOTOS_DETAIL_REQUEST = 31

    val POST_UPDATE_ZIP_URL = "accounts/UpdateZipCode"
    val POST_UPDATE_ZIP_REQUEST = 32


    val POST_AUTO_SEARCH = "KAL5Wine/AutoSearchByLocation"
    val POST_AUTO_SEARCH_REQUEST = 33

    val POST_GET_VARIETAL = "KAL5Wine/GetVarietalALL"
    val POST_GET_VARIETAL_REQUEST = 34

    val POST_PHOTOS_SEARCH_LIST_URL = "KAL5Wine/GetPhotoListBySearchText"
    val POST_PHOTOS_SEARCH_LIST_REQUEST = 35


    val USER_FULL_NAME = "userFullName"
    val USER_EMAIL = "userEmail"
    val USER_NAME = "userName"
    val USER_PROFILE_IMAGE = "userProfileImage"
    val USER_ID = "userId"
    val USER_ACC_ID = "userAccId"
    val IS_LOGGED_IN = "isLoggedIn"
    val USER_DOB = "userDob"
    val USER_FIRST_NAME = "firstName"
    val USER_ZIP_CODE = "userZipCode"
    val USER_LAST_NAME = "lastName"
    val USER_RESET_PASSWORD = "resetPassword"
    val USER_PASSWORD = "userPassword"
    val KEY_CONTENT_TYPE = "contentType"

    val BEACON_WINERY_NAME = "beaconWineryName"
    val BEACON_WINERY_ID = "beaconWineryId"
    val BEACON_WINERY_IMAGE = "beaconWineryImage"
    val KEY_WINE_IMAGE_LIST = "addNewWineImageList"
    val KEY_PHOTO_IMAGE_LIST = "addPhotoImageList"
    val KEY_WINE_DATA = "NewWineData"
    val KEY_ADD_WINE_TYPE = "AddWineType"
    val WINE_COUNT = "wineCount"
    val WINE_CURRENT_POSITION = "wineCurrentPosition"
    val SUCCESS_RESULT = 0
    val FAILURE_RESULT = 1
    val PACKAGE_NAME = "com.augusta.kal5"
    val RECEIVER = "$PACKAGE_NAME.RECEIVER"
    val RESULT_DATA_KEY = "$PACKAGE_NAME.RESULT_DATA_KEY"
    val LOCATION_DATA_EXTRA = "$PACKAGE_NAME.LOCATION_DATA_EXTRA"

    val PLACES_API_BASE = "https://maps.googleapis.com/maps/api/place/"
    val TYPE_AUTOCOMPLETE = "autocomplete/"
    val API_KEY = "AIzaSyBEPZOUQNYOvGa3OvIIVKCuTko_jhNXK3o"
    val OUT_JSON = "json"
}
