package es.anpez.whoapi;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

public class DoctorDetailActivity extends AppCompatActivity {
  public static final String DOCTOR = "doctor";

  public static Intent newIntent(Context context, Doctor doctor) {
    Intent intent = new Intent(context, DoctorDetailActivity.class);
    intent.putExtra(DOCTOR, doctor);
    return intent;
  }
  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_doctor_detail);

    Toolbar toolbar = findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);

    ActionBar actionBar = getSupportActionBar();
    if (actionBar != null) {
      actionBar.setDisplayHomeAsUpEnabled(true);
    }

    Doctor doctor = getIntent().getParcelableExtra(DOCTOR);
    Toast.makeText(this, "Doctor: "+doctor, Toast.LENGTH_LONG).show();

    TextView tv = findViewById(R.id.actor_name);
    tv.setText(doctor.actors.get(0).name);

    String url = "http://images.amcnetworks.com/bbcamerica.com/wp-content/uploads/2014/04/davidtennant-1600x720.jpg";
    ImageView avatarImageView = findViewById(R.id.avatar);
    Picasso.get().load(url).into(avatarImageView);
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    if (item.getItemId()==android.R.id.home) {
      supportFinishAfterTransition();
      return true;
    }

    return super.onOptionsItemSelected(item);
  }
}
