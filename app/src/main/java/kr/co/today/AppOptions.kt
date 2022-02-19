package kr.co.today

import android.annotation.SuppressLint
import android.content.Context
import android.provider.Settings
import kr.co.today.utils.Preferences
import org.bson.Document

const val SHARED_PREFERENCE_NAME = "ohmyapp.pref"

@SuppressLint("HardwareIds")
@Suppress("PropertyName")
class AppOptions(context: Context): Preferences(context, SHARED_PREFERENCE_NAME){

    val androidId  by stringPref("androidId", Settings.Secure.getString(context.contentResolver, Settings.Secure.ANDROID_ID)) //SSAID
    var appVersion by stringPref("appVersion", "")
    var osVersion by intPref("osVersion", 0)

    var minVersion by stringPref("minVersion", "")
    var minVersionCode by stringPref("minVersionCode", "")
    var latestVersion by stringPref("latestVersion", "")

    var marketUrl by stringPref("marketUrl", "")

    var firstRunning by booleanPref("firstRunning", true) //최초실행여부
    var isAutoLogin by booleanPref("isAutoLogin", false) //자동로그인
    var isSocialAutoLogin by booleanPref("isSocialAutoLogin", false) //자동로그인

    var isAuth by booleanPref("isAuth",false)//로그인 필수 여부

    var socialId by stringPref("socialId", "") //자동로그인
    var socialType by stringPref("socialType", "") //자동로그인
    var loginId by stringPref("loginId", "")
    var loginPw by stringPref("loginPw", "")
    var accessToken by stringPref("accessToken","")

    var iosBundleId by stringPref("iosBundleId","")
    var iosAppStoreId by stringPref("iosAppStoreId","")
    var iosLatestVersion by stringPref("iosLatestVersion","")
    var iosMinVersion by stringPref("iosMinVersion","")

    /**
     * PUSH
     */
    var fcmToken by stringPref("fcmToken", "")

    var pushCnt by intPref("pushCnt", 0) //fcm push

    var isGetPush by booleanPref("isGetPush",false)
    var tempPush by stringPref("tempPush","")
    var isPushHistoryClick by booleanPref("isPushHistoryClick",false)
    var PushHistoryMetaCode by stringPref("PushHistoryMetaCode","")



    var token by stringPref("token", "")
    var userIndex by stringPref("userIndex","")

    var isPushMarketing by booleanPref("isPushMarketing",false)
    var isPushChallenge by booleanPref("isPushChallenge",true)
    var isPushService by booleanPref("isPushService",true)

    var isNickName by booleanPref("isNickName",false)
    var loginUI by stringPref("loginUI","")

    var iamportKey by stringPref("iamportKey","")
    var registerType by stringPref("registerType","anauth")

    var checkAuth by stringPref("checkAuth","")
    var loadingSwitch by stringPref("loadingSwitch","")


    //loginSetting
    var loginTitle by stringPref("loginTitle","")
    var loginSubTitle by stringPref("loginSubTitle","")
    var loginImageUrl by stringPref("loginImageUrl","")
    var defaultUserImage by stringPref("defaultUserImage","")
    var isEmail by booleanPref("isEmail",false)
    var isKakao by booleanPref("isKakao",false)
    var isGoogle by booleanPref("isGoogle",false)
    var isApple by booleanPref("isApple",false)
    var isMetaCache by booleanPref("isMetaCache",false)

    //color
    var Main                by stringPref("Main",               "#3772FF")
    var Sub                 by stringPref("Sub",                "#1E41CC")
    var Error               by stringPref("Error",              "#FA5C5B")
    var Warning             by stringPref("Warning",            "#FA5B6D")
    var BaseM4              by stringPref("BaseM4",             "#FFFFFF")
    var BaseM3              by stringPref("BaseM3",             "#CCCCCC")
    var BaseM2              by stringPref("BaseM2",             "#B0B0B0")
    var BaseM1              by stringPref("BaseM1",             "#7A7A7A")
    var Base0               by stringPref("Base0",              "#262626")
    var BaseP1              by stringPref("BaseP1",             "#1D1D1D")
    var BaseP2              by stringPref("BaseP2",             "#000000")
    var Disabled            by stringPref("Disabled",           "#AAAAAD")
    var TextField           by stringPref("TextField",          "#F6F6F7")
    var Line                by stringPref("Line",               "#EBECED")
    var DarkGray            by stringPref("DarkGray",           "#43434D")
    var LightGray           by stringPref("LightGray",          "#F2F3F5")
    var NavSelect           by stringPref("NavSelect",          "#3772FF")
    var NavUnSelect         by stringPref("NavUnSelect",        "#CCCCCC")
    var Bg                  by stringPref("Bg",                 "#FFFFFF")
    var Loading             by stringPref("Loading",            "#3772FF")
    var Main_dark           by stringPref("Main_dark",          "#448FF4")
    var Sub_dark            by stringPref("Sub_dark",           "#FEDA86")
    var Error_dark          by stringPref("Error_dark",         "#E65C67")
    var Warning_dark        by stringPref("Warning_dark",       "#F69193")
    var BaseM4_dark         by stringPref("BaseM4_dark",        "#27292B")
    var BaseM3_dark         by stringPref("BaseM3_dark",        "#404346")
    var BaseM2_dark         by stringPref("BaseM2_dark",        "#727478")
    var BaseM1_dark         by stringPref("BaseM1_dark",        "#96999E")
    var Base0_dark          by stringPref("Base0_dark",         "#EDEFF0")
    var BaseP1_dark         by stringPref("BaseP1_dark",        "#F2F3F5")
    var BaseP2_dark         by stringPref("BaseP2_dark",        "#FFFFFF")
    var Disabled_dark       by stringPref("Disabled_dark",      "#2E3033")
    var TextField_dark      by stringPref("TextField_dark",     "#2D3033")
    var Line_dark           by stringPref("Line_dark",          "#2D3033")
    var DarkGray_dark       by stringPref("DarkGray_dark",      "#2A2C2E")
    var LightGray_dark      by stringPref("LightGray_dark",     "#383A3D")
    var NavSelect_dark      by stringPref("NavSelect_dark",     "#BF5EFE")
    var NavUnSelect_dark    by stringPref("NavUnSelect_dark",   "#727478")
    var Bg_dark             by stringPref("Bg_dark",            "#1E2021")
    var Loading_dark        by stringPref("Loading_dark",       "#0EC796")
    var Hint                by stringPref("Hint",       "#c2c6cc")
    var Hint_dark           by stringPref("Hint_dark",       "#c2c6cc")



    lateinit var metaData: Document
}