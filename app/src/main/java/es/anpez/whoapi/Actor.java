package es.anpez.whoapi;

import com.google.gson.annotations.SerializedName;

public class Actor {
  @SerializedName("id")
  public Integer id;

  @SerializedName("name")
  public String name;

  @SerializedName("gender")
  public String gender;
}
