package aungkyawpaing.uyfresherwelcome.models;

/**
 * Created by vincentpaing on 17/12/15.
 */

import android.content.Context;
import aungkyawpaing.uyfresherwelcome.utils.ParseConstants;
import aungkyawpaing.uyfresherwelcome.utils.PrefManager;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.SaveCallback;

/**
 * Created by vincentpaing on 22/12/15.
 */
public class Student {

  private ParseObject mParseObject;

  public Student(ParseObject mParseObject) {
    this.mParseObject = mParseObject;
  }

  public Student() {
    mParseObject = new ParseObject(ParseConstants.CLASS_NAME);
  }

  public String getStudentName() {
    return mParseObject.getString(ParseConstants.NAME_KEY);
  }

  public ParseFile getPicture() {
    return mParseObject.getParseFile(ParseConstants.PICTURE_KEY);
  }

  public String getGender() {
    return mParseObject.getString(ParseConstants.GENDER_KEY);
  }

  public int getVoteCount() {
    return mParseObject.getInt(ParseConstants.VOTE_COUNT_KEY);
  }

  public String getObjectId() {
    return mParseObject.getObjectId();
  }

  public String getMajor() {
    return mParseObject.getString(ParseConstants.MAJOR_KEY);
  }

  public ParseObject getParseObject() {
    return mParseObject;
  }

  public void setStudentName(String name) {
    mParseObject.put(ParseConstants.NAME_KEY, name);
  }

  public void setPicture(ParseFile picture) {
    mParseObject.put(ParseConstants.PICTURE_KEY, picture);
  }

  public void setGender(String gender) {
    mParseObject.put(ParseConstants.GENDER_KEY, gender);
  }

  public void setVoteCount(int voteCount) {
    mParseObject.put(ParseConstants.VOTE_COUNT_KEY, voteCount);
  }

  public void setMajor(Context context) {
    mParseObject.put(ParseConstants.MAJOR_KEY, PrefManager.getInstance(context).getMajor());
  }

  public void incrementVote() {
    mParseObject.increment(ParseConstants.VOTE_COUNT_KEY);
  }

  public void save(SaveCallback saveCallback) {
    mParseObject.saveInBackground(saveCallback);
  }

  public void saveEventually() {
    mParseObject.saveEventually();
  }
}