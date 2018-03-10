package es.anpez.whoapi;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface DoctorsService {
  @GET("doctors")
  Call<List<Doctor>> all();

  @GET("doctors/{id}/actors")
  Call<List<Actor>> doctorActor(@Path("id") int doctorId);
}
