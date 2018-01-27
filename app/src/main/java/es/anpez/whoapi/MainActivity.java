package es.anpez.whoapi;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    List<Doctor> doctors = new ArrayList<>();
    //doctors.add(new Doctor("William Hartnell"));
    //doctors.add(new Doctor("David Tennant"));
for(int i=0;i<1000;i++) {
  doctors.add(new Doctor("Doctor"+i));
}
    ListView list = findViewById(R.id.list);
    list.setAdapter(new DoctorsAdapter(this, doctors));
  }

  public class DoctorsAdapter extends ArrayAdapter<Doctor> {
    public DoctorsAdapter(Context context, List<Doctor> doctors) {
      super(context, 0, doctors);
    }


    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
      Doctor doctor = getItem(position);

      if (null == convertView) {
        Log.d("DoctorsAdapter", "Creando posicion:"+position);
        convertView = LayoutInflater.from(MainActivity.this).inflate(R.layout.list_doctors_item, parent, false);
      } else {
        Log.d("DoctorsAdapter", "Reusando posicion:"+position);
      }

      TextView tv = convertView.findViewById(R.id.name);
      tv.setText(doctor.name);

      return convertView;
    }
  }
}
