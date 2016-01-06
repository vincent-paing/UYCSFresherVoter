package aungkyawpaing.uyfresherwelcome.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by vincentpaing on 17/12/15.
 */
public class PrefManager {

  private static PrefManager prefManager;
  private SharedPreferences mPreferences;
  private SharedPreferences.Editor mEditor;
  private String FIRST_TIME = "FIRST_TIME";
  private String MAJOR_KEY = "MAJOR_KEY";
  public static String NULL_MAJOR = "NULL_MAJOR";

  public PrefManager(Context mContext) {
    mPreferences = PreferenceManager.getDefaultSharedPreferences(mContext);
    mEditor = mPreferences.edit();
  }

  public static synchronized PrefManager getInstance(Context context) {
    if (prefManager == null) {
      prefManager = new PrefManager(context);
    }
    return prefManager;
  }

  public boolean isFirstTime() {
    return mPreferences.getBoolean(FIRST_TIME, true);
  }

  public void setFirstTime(boolean firstTime) {
    mEditor.putBoolean(FIRST_TIME, firstTime);
    mEditor.commit();
  }

  public String getMajor() {
    return mPreferences.getString(MAJOR_KEY, "NULL_MAJOR");
  }

  public void setMajor(String major) {
    mEditor.putString(MAJOR_KEY, major);
    mEditor.commit();
  }
}
