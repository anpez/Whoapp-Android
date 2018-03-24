package es.anpez.whoapi;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.security.ProviderInstaller;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
  private DoctorsService doctorsService;
  private DoctorsAdapter doctorsAdapter;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    ProviderInstaller.installIfNeededAsync(this, new ProviderInstaller.ProviderInstallListener() {
      @Override
      public void onProviderInstalled() {}

      @Override
      public void onProviderInstallFailed(int errorCode, Intent recoveryIntent) {}
    });

    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://api.catalogopolis.xyz/v1/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();
    doctorsService = retrofit.create(DoctorsService.class);

    final RecyclerView recyclerView = findViewById(R.id.list);
    recyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
    doctorsAdapter = new DoctorsAdapter(new ArrayList<Doctor>());
    recyclerView.setAdapter(doctorsAdapter);

    doctorsService.all()
            .enqueue(new Callback<List<Doctor>>() {
              @Override
              public void onResponse(@NonNull Call<List<Doctor>> call, @NonNull Response<List<Doctor>> response) {
                Log.d(MainActivity.class.getSimpleName(), "Respuesta:"+response.body());
                doctorsAdapter.swapData(response.body());
              }

              @Override
              public void onFailure(@NonNull Call<List<Doctor>> call, @NonNull Throwable t) {
                Log.e(MainActivity.class.getSimpleName(), "Error obteniendo la lista de doctores", t);
              }
            });
  }

  private void getActorsForDoctor(final Doctor doctor) {
    doctorsService.doctorActor(doctor.id)
            .enqueue(new Callback<List<Actor>>() {
              @Override
              public void onResponse(Call<List<Actor>> actorCall, Response<List<Actor>> actorResponse) {
                List<Actor> actors = actorResponse.body();
                doctor.setActors(actors);
                doctorsAdapter.notifyDataSetChanged();
              }

              @Override
              public void onFailure(Call<List<Actor>> call, Throwable t) {
              }
            });
  }

  public class DoctorsAdapter extends RecyclerView.Adapter<DoctorsAdapter.DoctorViewHolder> {
    public class DoctorViewHolder extends RecyclerView.ViewHolder {
      TextView nameTextView;
      TextView aliasTextView;
      LinearLayout doctorsLinearLayout;

      public DoctorViewHolder(View itemView) {
        super(itemView);
        nameTextView = itemView.findViewById(R.id.name);
        aliasTextView= itemView.findViewById(R.id.alias);
        doctorsLinearLayout = itemView.findViewById(R.id.list_actors);
      }
    }

    private List<Doctor> doctors;

    void swapData(List<Doctor> doctors) {
      this.doctors = doctors;

      for(final Doctor doctor: doctors) {
        getActorsForDoctor(doctor);
      }
      notifyDataSetChanged();
    }

    DoctorsAdapter(List<Doctor> doctors) {
      this.doctors = doctors;
    }

    @Override
    public DoctorViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
      View view = LayoutInflater.from(MainActivity.this).inflate(R.layout.list_doctors_item, parent, false);
      return new DoctorViewHolder(view);
    }

    @Override
    public void onBindViewHolder(DoctorViewHolder holder, int position) {
      final Doctor doctor = doctors.get(position);
      holder.nameTextView.setText(doctor.alias);
      holder.aliasTextView.setText(String.valueOf(position));

      holder.doctorsLinearLayout.removeAllViews();
      if (null != doctor.actors) {
        for(Actor actor: doctor.actors) {
          View view = LayoutInflater.from(MainActivity.this).inflate(R.layout.list_actors_item, holder.doctorsLinearLayout, false);
          TextView nameTextView = view.findViewById(R.id.actor_name);
          nameTextView.setText(actor.name);

          holder.doctorsLinearLayout.addView(view);
        }
      }

      holder.itemView.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
          startActivity(DoctorDetailActivity.newIntent(MainActivity.this, doctor));
        }
      });
    }

    @Override
    public int getItemCount() {
      return doctors.size();
    }
  }
}
