package aungkyawpaing.uyfresherwelcome.activities;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import aungkyawpaing.uyfresherwelcome.R;
import aungkyawpaing.uyfresherwelcome.adapter.MainPagerAdapter;
import aungkyawpaing.uyfresherwelcome.utils.L;
import aungkyawpaing.uyfresherwelcome.utils.ParseConstants;
import aungkyawpaing.uyfresherwelcome.utils.PrefManager;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import java.util.List;

public class MainActivity extends AppCompatActivity {

  @Bind(R.id.main_pager) ViewPager mViewPager;
  @Bind(R.id.toolbar) Toolbar toolbar;
  @Bind(R.id.main_tab) TabLayout mTabLayout;
  @Bind(R.id.loading_layout) View loadingLayout;

  private MainPagerAdapter mPagerAdapter;
  private Context mContext;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    ButterKnife.bind(this);

    setSupportActionBar(toolbar);
    getSupportActionBar().setTitle(R.string.app_name);

    mContext = getApplicationContext();

    if (PrefManager.getInstance(mContext).getMajor().equals(PrefManager.NULL_MAJOR)) {
      Intent intent = new Intent(this, WelcomeActivity.class);
      intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
      startActivity(intent);
      this.finish();
    } else {
      Parse.enableLocalDatastore(this);
      Parse.initialize(this);
      if (PrefManager.getInstance(mContext).isFirstTime()) {
        new DataLoadingTask().execute("Start");
      } else {
        mPagerAdapter = new MainPagerAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(mPagerAdapter);
        mTabLayout.setupWithViewPager(mViewPager);
      }
    }
  }

  private class DataLoadingTask extends AsyncTask<String, Void, String> {

    @Override protected void onPreExecute() {
      loadingLayout.setVisibility(View.VISIBLE);
      super.onPreExecute();
    }

    @Override protected String doInBackground(String... params) {
      //@TODO: Sync with Online Data
      ParseQuery<ParseObject> query = ParseQuery.getQuery("Student")
          .whereEqualTo(ParseConstants.MAJOR_KEY, PrefManager.getInstance(mContext).getMajor());
      query.findInBackground(new FindCallback<ParseObject>() {

        @Override public void done(List<ParseObject> objects, ParseException e) {
          ParseObject.pinAllInBackground(objects);
          mPagerAdapter = new MainPagerAdapter(getSupportFragmentManager());
          mViewPager.setAdapter(mPagerAdapter);
          mTabLayout.setupWithViewPager(mViewPager);
          loadingLayout.setVisibility(View.GONE);
          PrefManager.getInstance(mContext).setFirstTime(false);
        }
      });
      return null;
    }
  }

  @Override public boolean onCreateOptionsMenu(Menu menu) {
    // Inflate the menu; this adds items to the action bar if it is present.
    getMenuInflater().inflate(R.menu.menu_main, menu);
    return true;
  }

  @Override public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
      case R.id.action_finish:
        Intent intent = new Intent(this, ResultActivity.class);
        startActivity(intent);
        return true;
      case R.id.action_refresh:
        new DataLoadingTask().execute("start");
        return true;
      default:
        return super.onOptionsItemSelected(item);
    }
  }
}
