package aungkyawpaing.uyfresherwelcome.activities;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import aungkyawpaing.uyfresherwelcome.R;
import aungkyawpaing.uyfresherwelcome.models.Student;
import aungkyawpaing.uyfresherwelcome.utils.L;
import aungkyawpaing.uyfresherwelcome.utils.ParseConstants;
import aungkyawpaing.uyfresherwelcome.utils.PrefManager;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.bumptech.glide.Glide;
import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import java.util.List;

/**
 * Created by vincentpaing on 17/12/15.
 */
public class ResultActivity extends AppCompatActivity {

  private Context mContext;
  @Bind(R.id.queen_image) ImageView queenImageView;
  @Bind(R.id.queen_name) TextView queenName;
  @Bind(R.id.king_image) ImageView kingImage;
  @Bind(R.id.king_name) TextView kingName;
  @Bind(R.id.loading_layout) View loadingLayout;
  @Bind(R.id.toolbar) Toolbar toolbar;
  @Bind(R.id.total_vote_count) TextView totalVoteCount;
  private int voteCount = 0;

  @Override public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_result);
    ButterKnife.bind(this);
    mContext = getApplicationContext();

    setSupportActionBar(toolbar);
    getSupportActionBar().setTitle("Result");
    getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    ParseQuery<ParseObject> countQuery = ParseQuery.getQuery(ParseConstants.CLASS_NAME)
        .whereEqualTo(ParseConstants.MAJOR_KEY, PrefManager.getInstance(mContext).getMajor());

    ParseQuery<ParseObject> maleQuery = ParseQuery.getQuery(ParseConstants.CLASS_NAME)
        .whereEqualTo(ParseConstants.GENDER_KEY, "Male")
        .whereEqualTo(ParseConstants.MAJOR_KEY, PrefManager.getInstance(mContext).getMajor())
        .orderByDescending(ParseConstants.VOTE_COUNT_KEY);
    final ParseQuery<ParseObject> femaleQuery = ParseQuery.getQuery(ParseConstants.CLASS_NAME)
        .whereEqualTo(ParseConstants.GENDER_KEY, "Female")
        .whereEqualTo(ParseConstants.MAJOR_KEY, PrefManager.getInstance(mContext).getMajor())
        .orderByDescending(ParseConstants.VOTE_COUNT_KEY);

    countQuery.findInBackground(new FindCallback<ParseObject>() {
      @Override public void done(List<ParseObject> objects, ParseException e) {
        for (ParseObject object : objects) {
          Student temp = new Student(object);
          voteCount += temp.getVoteCount();
          totalVoteCount.setText("Total Vote Count : " + voteCount);
        }
      }
    });

    maleQuery.getFirstInBackground(new GetCallback<ParseObject>() {
      @Override public void done(ParseObject king, ParseException e) {
        if (e == null) {
          kingName.setText(king.getString(ParseConstants.NAME_KEY));
          ParseFile postImage = king.getParseFile(ParseConstants.PICTURE_KEY);
          String imageUrl = postImage.getUrl();
          Uri imageUri = Uri.parse(imageUrl);
          Glide.with(mContext).load(imageUri).centerCrop().into(kingImage);

          femaleQuery.getFirstInBackground(new GetCallback<ParseObject>() {
            @Override public void done(ParseObject queen, ParseException e) {
              queenName.setText(queen.getString(ParseConstants.NAME_KEY));
              ParseFile postImage = queen.getParseFile(ParseConstants.PICTURE_KEY);
              String imageUrl = postImage.getUrl();
              Uri imageUri = Uri.parse(imageUrl);
              Glide.with(mContext).load(imageUri).centerCrop().into(queenImageView);

              loadingLayout.setVisibility(View.GONE);
            }
          });
        } else {
          e.printStackTrace();
        }
      }
    });
  }

  @Override public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
      case android.R.id.home:
        this.finish();
        return true;
      default:
        return super.onOptionsItemSelected(item);
    }
  }
}
