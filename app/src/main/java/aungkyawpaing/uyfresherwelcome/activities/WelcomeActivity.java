package aungkyawpaing.uyfresherwelcome.activities;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import aungkyawpaing.uyfresherwelcome.R;
import aungkyawpaing.uyfresherwelcome.utils.PrefManager;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by vincentpaing on 29/12/15.
 */
public class WelcomeActivity extends AppCompatActivity {

  @Bind(R.id.major_spinner) Spinner majorSpinner;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_welcome);
    ButterKnife.bind(this);

    ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.major_array,
        android.R.layout.simple_spinner_item);
    // Specify the layout to use when the list of choices appears
    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    // Apply the adapter to the spinner
    majorSpinner.setAdapter(adapter);

    majorSpinner.setSelection(0);
  }

  @OnClick(R.id.btn_continue) void onContinueClick(View view) {
    AlertDialog.Builder builder = new AlertDialog.Builder(this);
    PrefManager.getInstance(this).setMajor(majorSpinner.getSelectedItem().toString());
    Intent intent = new Intent(this, MainActivity.class);
    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
    startActivity(intent);
    this.finish();
  }
}
