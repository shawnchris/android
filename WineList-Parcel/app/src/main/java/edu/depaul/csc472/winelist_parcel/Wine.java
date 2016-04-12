package edu.depaul.csc472.winelist_parcel;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Xiaoping Jia on 10/6/14.
 */
public class Wine implements Parcelable {

  enum Type {Red, White, Rosé, Sparkling}

  String name;
  Type type;
  String shortDescription;
  String longDescription;
  float rating = 4.0f;

  public Wine(String name, Type type, String shortDescription, String longDescription) {
    this.name = name;
    this.type = type;
    this.shortDescription = shortDescription;
    this.longDescription = longDescription;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getShortDescription() {
    return shortDescription;
  }

  public void setShortDescription(String shortDescription) {
    this.shortDescription = shortDescription;
  }

  public String getLongDescription() {
    return longDescription;
  }

  public void setLongDescription(String longDescription) {
    this.longDescription = longDescription;
  }

  public Type getType() {
    return type;
  }

  public void setType(Type type) {
    this.type = type;
  }

  public float getRating() {
    return rating;
  }

  public void setRating(float rating) {
    this.rating = rating;
  }

  public String toString() {
    return name;
  }

  public static int getIconResource(Type type) {
    switch (type) {
      case White:
        return R.drawable.white;
      case Red:
        return R.drawable.red;
      case Rosé:
        return R.drawable.rose;
      case Sparkling:
        return R.drawable.sparkling;
    }
    return -1;
  }

  //// implement Parcelable

  private Wine(Parcel in) {
    name = in.readString();
    type = Type.values()[in.readInt()];
    shortDescription = in.readString();
    longDescription = in.readString();
    rating = in.readFloat();
  }

  @Override
  public int describeContents() {
    return 0;
  }

  @Override
  public void writeToParcel(Parcel out, int flags) {
    out.writeString(name);
    out.writeInt(type.ordinal());
    out.writeString(shortDescription);
    out.writeString(longDescription);
    out.writeFloat(rating);
  }

  public static final Creator<Wine> CREATOR
      = new Creator<Wine>() {
    public Wine createFromParcel(Parcel in) {
      return new Wine(in);
    }

    public Wine[] newArray(int size) {
      return new Wine[size];
    }
  };

}
