package es.anpez.whoapi;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Doctor {
  @SerializedName("id")
  public int id;

  @SerializedName("incarnation")
  public String alias;

  public List<Actor> actors;

  @Override
  public String toString() {
    return "Doctor{" +
            "id=" + id +
            ", alias='" + alias + '\'' +
            ", actors=" + actors +
            '}';
  }

  public void setActors(List<Actor> actors) {
    this.actors = actors;
  }
}
