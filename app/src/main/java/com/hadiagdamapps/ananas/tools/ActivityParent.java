package com.hadiagdamapps.ananas.tools;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

abstract public class ActivityParent extends AppCompatActivity {

    public final int layout;
    public final Server server;
    public final Data data;

    public void volleyError(Exception ex) {

    }

    public ActivityParent(int layout) {
        this.layout = layout;
        this.server = new Server(this){
            @Override
            public void onError(@NonNull Exception exception) {
                volleyError(exception);
            }
        };
        this.data = new Data(this);
    }

    public abstract void initialView();

    public abstract void main();



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layout);
        initialView();
        main();
    }
}
