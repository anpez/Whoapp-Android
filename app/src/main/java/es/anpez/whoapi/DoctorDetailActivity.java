package es.anpez.whoapi;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

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
    Doctor doctor = getIntent().getParcelableExtra(DOCTOR);
    Toast.makeText(this, "Doctor: "+doctor, Toast.LENGTH_LONG).show();
  }
}
