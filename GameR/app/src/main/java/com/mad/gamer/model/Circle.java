package com.mad.gamer.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Circle implements Parcelable {
    int color;
    float cx, cy, radius;

    public Circle(int color, float cx, float cy, float radius) {
        this.color = color;
        this.cx = cx;
        this.cy = cy;
        this.radius = radius;
    }

    protected Circle(Parcel in) {
        color = in.readInt();
        cx = in.readFloat();
        cy = in.readFloat();
        radius = in.readFloat();
    }

    public static final Creator<Circle> CREATOR = new Creator<Circle>() {
        @Override
        public Circle createFromParcel(Parcel in) {
            return new Circle(in);
        }

        @Override
        public Circle[] newArray(int size) {
            return new Circle[size];
        }
    };

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public float getCx() {
        return cx;
    }

    public void setCx(float cx) {
        this.cx = cx;
    }

    public float getCy() {
        return cy;
    }

    public void setCy(float cy) {
        this.cy = cy;
    }

    public float getRadius() {
        return radius;
    }

    public void setRadius(float radius) {
        this.radius = radius;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(color);
        parcel.writeFloat(cx);
        parcel.writeFloat(cy);
        parcel.writeFloat(radius);
    }

    @Override
    public String toString() {
        return "Circle{" +
                "color=" + color +
                ", cx=" + cx +
                ", cy=" + cy +
                ", radius=" + radius +
                '}';
    }
}
