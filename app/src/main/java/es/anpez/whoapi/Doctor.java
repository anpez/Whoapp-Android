package es.anpez.whoapi;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Doctor implements Parcelable {
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

  protected Doctor(Parcel in) {
    id = in.readInt();
    alias = in.readString();
    if (in.readByte() == 0x01) {
      actors = new ArrayList<>();
      in.readList(actors, Actor.class.getClassLoader());
    } else {
      actors = null;
    }
  }

  @Override
  public int describeContents() {
    return 0;
  }

  @Override
  public void writeToParcel(Parcel dest, int flags) {
    dest.writeInt(id);
    dest.writeString(alias);
    if (actors == null) {
      dest.writeByte((byte) (0x00));
    } else {
      dest.writeByte((byte) (0x01));
      dest.writeList(actors);
    }
  }

  @SuppressWarnings("unused")
  public static final Parcelable.Creator<Doctor> CREATOR = new Parcelable.Creator<Doctor>() {
    @Override
    public Doctor createFromParcel(Parcel in) {
      return new Doctor(in);
    }

    @Override
    public Doctor[] newArray(int size) {
      return new Doctor[size];
    }
  };
}
