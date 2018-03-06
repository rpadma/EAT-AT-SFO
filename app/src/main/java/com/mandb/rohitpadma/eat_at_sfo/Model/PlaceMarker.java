package com.mandb.rohitpadma.eat_at_sfo.Model;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * Created by rohitpadma on 3/5/18.
 */

public class PlaceMarker implements Parcelable {

    public String place_id;
    public String rating;
    public String reference;
    public String vicinity;
    public String name;
    public Location location;
    public OpeningHour opening_hours;


    protected PlaceMarker(Parcel in) {
        place_id = in.readString();
        rating = in.readString();
        reference = in.readString();
        vicinity = in.readString();
        name = in.readString();
    }



    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(place_id);
        dest.writeString(rating);
        dest.writeString(reference);
        dest.writeString(vicinity);
        dest.writeString(name);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<PlaceMarker> CREATOR = new Creator<PlaceMarker>() {
        @Override
        public PlaceMarker createFromParcel(Parcel in) {
            return new PlaceMarker(in);
        }

        @Override
        public PlaceMarker[] newArray(int size) {
            return new PlaceMarker[size];
        }
    };

    public String getPlace_id() {
        return place_id;
    }

    public void setPlace_id(String place_id) {
        this.place_id = place_id;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public String getVicinity() {
        return vicinity;
    }

    public void setVicinity(String vicinity) {
        this.vicinity = vicinity;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public OpeningHour getOpening_hours() {
        return opening_hours;
    }

    public void setOpening_hours(OpeningHour opening_hours) {
        this.opening_hours = opening_hours;
    }

    @Override
    public String toString() {
        return "PlaceMarker{" +
                "place_id='" + place_id + '\'' +
                ", rating='" + rating + '\'' +
                ", reference='" + reference + '\'' +
                ", vicinity='" + vicinity + '\'' +
                ", name='" + name + '\'' +
                ", location=" + location +
                ", opening_hours=" + opening_hours +
                '}';
    }

}
