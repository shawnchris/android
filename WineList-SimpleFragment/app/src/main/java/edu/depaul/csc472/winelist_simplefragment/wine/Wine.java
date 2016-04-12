package edu.depaul.csc472.winelist_simplefragment.wine;

/**
 * Created by Xiaoping Jia on 10/6/14.
 */
public class Wine {

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

}
