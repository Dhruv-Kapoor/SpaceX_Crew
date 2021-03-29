package com.example.spacexcrew.dataClasses;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Objects;

@Entity
public class Crew implements Parcelable {
    public static final Creator<Crew> CREATOR = new Creator<Crew>() {
        @Override
        public Crew createFromParcel(Parcel in) {
            return new Crew(in);
        }

        @Override
        public Crew[] newArray(int size) {
            return new Crew[size];
        }
    };
    @PrimaryKey
    @NonNull
    private String id;
    private String image;
    private String agency;
    private String name;
    private String wikipedia;
    private String status;

    public Crew(String image, String agency, String name, String wikipedia, @NonNull String id, String status) {
        this.image = image;
        this.agency = agency;
        this.name = name;
        this.wikipedia = wikipedia;
        this.id = id;
        this.status = status;
    }

    protected Crew(Parcel in) {
        id = in.readString();
        image = in.readString();
        agency = in.readString();
        name = in.readString();
        wikipedia = in.readString();
        status = in.readString();
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getAgency() {
        return agency;
    }

    public void setAgency(String agency) {
        this.agency = agency;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getWikipedia() {
        return wikipedia;
    }

    public void setWikipedia(String wikipedia) {
        this.wikipedia = wikipedia;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Crew{" +
                "image='" + image + '\'' +
                ", agency='" + agency + '\'' +
                ", name='" + name + '\'' +
                ", wikipedia='" + wikipedia + '\'' +
                ", id='" + id + '\'' +
                ", status='" + status + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Crew crew = (Crew) o;

        if (!id.equals(crew.id)) return false;
        if (!Objects.equals(image, crew.image)) return false;
        if (!Objects.equals(agency, crew.agency)) return false;
        if (!Objects.equals(name, crew.name)) return false;
        if (!Objects.equals(wikipedia, crew.wikipedia))
            return false;
        return Objects.equals(status, crew.status);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(image);
        dest.writeString(agency);
        dest.writeString(name);
        dest.writeString(wikipedia);
        dest.writeString(status);
    }
}