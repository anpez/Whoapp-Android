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
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.security.ProviderInstaller;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

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
    final DoctorsService doctorsService = retrofit.create(DoctorsService.class);

    final RecyclerView recyclerView = findViewById(R.id.list);
    recyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
    final DoctorsAdapter doctorsAdapter = new DoctorsAdapter(new ArrayList<Doctor>());
    recyclerView.setAdapter(doctorsAdapter);

    doctorsService.all()
            .enqueue(new Callback<List<Doctor>>() {
              @Override
              public void onResponse(@NonNull Call<List<Doctor>> call, @NonNull Response<List<Doctor>> response) {
                Log.d(MainActivity.class.getSimpleName(), "Respuesta:"+response.body());
                doctorsAdapter.swapData(response.body());

                for(Doctor doctor: response.body()) {
                  doctorsService.doctorActor(doctor.id)
                          .enqueue(new Callback<List<Actor>>() {
                            @Override
                            public void onResponse(Call<List<Actor>> call, Response<List<Actor>> response) {
                              Toast.makeText(
                                      MainActivity.this,
                                      "El actor es: "+response.body().get(0).name,
                                      Toast.LENGTH_SHORT
                              ).show();
                            }

                            @Override
                            public void onFailure(Call<List<Actor>> call, Throwable t) {

                            }
                          });
                }
              }

              @Override
              public void onFailure(@NonNull Call<List<Doctor>> call, @NonNull Throwable t) {
                Log.e(MainActivity.class.getSimpleName(), "Error obteniendo la lista de doctores", t);
              }
            });
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

    void swapData(List<Doctor> doctors) {
      this.doctors = doctors;
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
      holder.nameTextView.setText(doctors.get(position).alias);
      holder.aliasTextView.setText(String.valueOf(position));
    }

    @Override
    public int getItemCount() {
      return doctors.size();
    }
  }
}
