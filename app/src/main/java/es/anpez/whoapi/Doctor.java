package es.anpez.whoapi;

import com.google.gson.annotations.SerializedName;

public class Doctor {
  @SerializedName("id")
  public int id;

  @SerializedName("incarnation")
  public String alias;

  @Override
  public String toString() {
    return "Doctor{" +
            "id=" + id +
            ", alias='" + alias + '\'' +
            '}';
  }
}
