package aungkyawpaing.uyfresherwelcome.fragments;

import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import aungkyawpaing.uyfresherwelcome.R;
import aungkyawpaing.uyfresherwelcome.adapter.GridAdapter;
import aungkyawpaing.uyfresherwelcome.models.Student;
import aungkyawpaing.uyfresherwelcome.utils.L;
import aungkyawpaing.uyfresherwelcome.utils.ParseConstants;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.bumptech.glide.Glide;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by vincentpaing on 16/12/15.
 */
public class VotingFragment extends Fragment {

  @Bind(R.id.profile_grid) GridView mGridView;
  //private static VotingFragment mFragment;
  private final static String ARG_POSITION = "ARG_POSITION";
  private GridAdapter mGridAdapter;
  private int position;
  private Context mContext;
  ArrayList<Student> studentArrayList = new ArrayList<>();

  public static VotingFragment newInstance(int position) {
    VotingFragment mFragment = new VotingFragment();
    Bundle args = new Bundle();
    args.putInt(ARG_POSITION, position);
    mFragment.setArguments(args);
    return mFragment;
  }

  @Nullable @Override public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    View rootView = inflater.inflate(R.layout.fragment_main, container, false);
    ButterKnife.bind(this, rootView);
    mContext = this.getContext();

    Bundle args = getArguments();
    position = args.getInt(ARG_POSITION);

    new LoadDataTask().execute(position);

    mGridAdapter = new GridAdapter(getContext(), studentArrayList);
    mGridView.setAdapter(mGridAdapter);

    mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
      @Override public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        final Student student = studentArrayList.get(position);
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(getContext());
        mBuilder.setTitle("Are you sure?")
            .setView(R.layout.item_dialog)
            .setMessage(
                "You are voting for " + student.getStudentName() + ". Click Vote to Continue")
            .setPositiveButton("Vote", new DialogInterface.OnClickListener() {
              @Override public void onClick(DialogInterface dialog, int which) {
                student.incrementVote();
                student.saveEventually();
              }
            })
            .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
              @Override public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
              }
            });
        AlertDialog dialog = mBuilder.create();
        dialog.show();
        L.e("WTF");
        ImageView imageView = (ImageView) dialog.findViewById(R.id.dialog_image);
        TextView textView = (TextView) dialog.findViewById(R.id.dialog_name);

        ParseFile postImage = student.getPicture();
        String imageUrl = postImage.getUrl();//live url
        Uri imageUri = Uri.parse(imageUrl);
        L.e("imageuri is " + imageUri.toString());
        Glide.with(mContext).load(imageUri).into(imageView);
        textView.setText(student.getStudentName());
      }
    });
    return rootView;
  }
  private class LoadDataTask extends AsyncTask<Integer, Void, String> {

    @Override protected String doInBackground(Integer... params) {
      int position = params[0];
      ParseQuery<ParseObject> query = null;
      switch (position) {
        case 0:
          query = ParseQuery.getQuery("Student").whereEqualTo(ParseConstants.GENDER_KEY, "Male").orderByAscending(
              ParseConstants.NAME_KEY);
          break;
        case 1:
          query = ParseQuery.getQuery("Student").whereEqualTo(ParseConstants.GENDER_KEY,
              "Female").orderByAscending(ParseConstants.NAME_KEY);
          break;
        default:
          query = ParseQuery.getQuery("Student").whereEqualTo(ParseConstants.GENDER_KEY, "Female");
          break;
      }
      query.clearCachedResult();
      query.fromLocalDatastore();
      query.findInBackground(new FindCallback<ParseObject>() {

        @Override public void done(List<ParseObject> objects, ParseException e) {
          studentArrayList.clear();
          for (ParseObject object : objects) {
            studentArrayList.add(new Student(object));
          }
          mGridAdapter.notifyDataSetChanged();
        }
      });
      return null;
    }
  }
}
