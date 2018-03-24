package es.anpez.whoapi;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class Actor implements Parcelable {
  @SerializedName("id")
  public Integer id;

  @SerializedName("name")
  public String name;

  @SerializedName("gender")
  public String gender;

  protected Actor(Parcel in) {
    if (in.readByte() == 0) {
      id = null;
    } else {
      id = in.readInt();
    }
    name = in.readString();
    gender = in.readString();
  }

  @Override
  public void writeToParcel(Parcel dest, int flags) {
    if (id == null) {
      dest.writeByte((byte) 0);
    } else {
      dest.writeByte((byte) 1);
      dest.writeInt(id);
    }
    dest.writeString(name);
    dest.writeString(gender);
  }

  @Override
  public int describeContents() {
    return 0;
  }

  public static final Creator<Actor> CREATOR = new Creator<Actor>() {
    @Override
    public Actor createFromParcel(Parcel in) {
      return new Actor(in);
    }

    @Override
    public Actor[] newArray(int size) {
      return new Actor[size];
    }
  };

  @Override
  public String toString() {
    return "Actor{" +
            "id=" + id +
            ", name='" + name + '\'' +
            ", gender='" + gender + '\'' +
            '}';
  }
}
