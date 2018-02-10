package es.anpez.whoapi;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

    final RecyclerView recyclerView = findViewById(R.id.list);
    recyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
    recyclerView.setAdapter(new DoctorsAdapter(doctors));
  }

  public class DoctorsAdapter extends RecyclerView.Adapter<DoctorsAdapter.DoctorViewHolder> {
    public class DoctorViewHolder extends RecyclerView.ViewHolder {
      TextView nameTextView;
      TextView aliasTextView;

      public DoctorViewHolder(View itemView) {
        super(itemView);
        nameTextView = itemView.findViewById(R.id.name);
        aliasTextView= itemView.findViewById(R.id.alias);
      }
    }

    private List<Doctor> doctors;

    public DoctorsAdapter(List<Doctor> doctors) {
      this.doctors = doctors;
    }

    @Override
    public DoctorViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
      View view = LayoutInflater.from(MainActivity.this).inflate(R.layout.list_doctors_item, parent, false);
      return new DoctorViewHolder(view);
    }

    @Override
    public void onBindViewHolder(DoctorViewHolder holder, int position) {
      holder.nameTextView.setText(doctors.get(position).name);
      holder.aliasTextView.setText(String.valueOf(position));
    }

    @Override
    public int getItemCount() {
      return doctors.size();
    }
  }
}
