package com.mad.gamer.utils;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;

public class CustomCircle extends View {
    Paint paint;
    Canvas canvas;
    public float cx, cy, radius;
    public int color;
    public int id ;

    public CustomCircle(Context context) {
        super(context);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        this.canvas = canvas;
        canvas.drawCircle(cx, cy, radius, paint);
    }

    public void init( int id,int color, float radius) {
        this.id = id;
        this.color = color;
        this.radius = radius;
        paint = new Paint();
        paint.setColor(color);
    }

    public void move(float cx, float cy) {
        this.cx = cx;
        this.cy = cy;
    }

    @Override
    public String toString() {
        return "CustomCircle{" +
                "id=" + id +
                ", cx=" + cx +
                ", cy=" + cy +
                '}';
    }
}
