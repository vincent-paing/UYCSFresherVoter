package aungkyawpaing.uyfresherwelcome.adapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import aungkyawpaing.uyfresherwelcome.R;
import aungkyawpaing.uyfresherwelcome.models.Student;
import aungkyawpaing.uyfresherwelcome.utils.L;
import aungkyawpaing.uyfresherwelcome.utils.ParseConstants;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.bumptech.glide.Glide;
import com.parse.ParseFile;
import com.parse.ParseObject;
import java.util.ArrayList;

/**
 * Created by vincentpaing on 17/12/15.
 */
public class GridAdapter extends BaseAdapter {

  private ArrayList<Student> studentArrayList;
  private Context mContext;

  public GridAdapter(Context mContext, ArrayList<Student> studentArrayList) {
    this.mContext = mContext;
    this.studentArrayList = studentArrayList;
  }

  @Override public int getCount() {
    return studentArrayList.size();
  }

  @Override public Student getItem(int position) {
    return studentArrayList.get(position);
  }

  @Override public long getItemId(int position) {
    return 0;
  }

  @Override public View getView(int position, View convertView, ViewGroup parent) {

    ViewHolder viewHolder;

    if (convertView == null) {
      LayoutInflater inflater =
          (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
      convertView = inflater.inflate(R.layout.item_grid, parent, false);
      viewHolder = new ViewHolder(convertView);
      convertView.setTag(viewHolder);
    } else {
      viewHolder = (ViewHolder) convertView.getTag();
    }

    Student student = getItem(position);

    viewHolder.nameText.setText(student.getStudentName());
    ParseFile postImage = student.getPicture();
    String imageUrl = postImage.getUrl();//live url
    Uri imageUri = Uri.parse(imageUrl);
    Glide.with(mContext).load(imageUri).centerCrop().into(viewHolder.profielImage);
    return convertView;
  }

  static class ViewHolder {
    @Bind(R.id.profile_image) ImageView profielImage;
    @Bind(R.id.profile_name) TextView nameText;

    public ViewHolder(View itemView) {
      ButterKnife.bind(this, itemView);
    }
  }
}
