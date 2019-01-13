package nnr.app.search.com.searchapp2;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.List;

public class AppUtil {
    static AppUtil appUtilInstance = new AppUtil();

    public static AppUtil getAppUtilInstance(){
        return appUtilInstance;
    }

    private SharedPreferences getSharedPrefs(Context context,String sharedPrefsName){
        SharedPreferences sharedPreferences = context.getSharedPreferences(sharedPrefsName,Context.MODE_PRIVATE);
        return sharedPreferences;
    }

    private SharedPreferences getLoginSharedPrefs(Context context){
        return  getSharedPrefs(context,AppConstants.SHARED_PREFS_LOGIN_NAME);
    }

    public boolean isUserAcceptedTerms(Context context){
        SharedPreferences myPrefs = getLoginSharedPrefs(context);
        return (myPrefs.getBoolean(AppConstants.SHARED_PREFS_ALREADY_LOGIN_KEY,false));
    }

    public void setUserAcceptedTerms(Context context){
        SharedPreferences myPrefs = getLoginSharedPrefs(context);
        SharedPreferences.Editor loginEditor = myPrefs.edit();
        loginEditor.putBoolean(AppConstants.SHARED_PREFS_ALREADY_LOGIN_KEY,true);
        loginEditor.apply();
    }

    public String getStringFromList(List<String> stringList){
        StringBuilder builder = new StringBuilder();
        int listSize = stringList.size();
        for(int i = 0 ;i < listSize ; i++){
            builder.append(stringList.get(i));
            if(i != listSize-1){
                builder.append(",");
            }
        }
        return builder.toString();
    }

}
