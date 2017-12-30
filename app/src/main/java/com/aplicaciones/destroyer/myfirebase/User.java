package com.aplicaciones.destroyer.myfirebase;

import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.databinding.BindingAdapter;
import android.databinding.InverseBindingAdapter;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by Destroyer on 27/12/2017.
 */

public class User extends BaseObservable{

    private String name;
    private String email;
    private int age;
    private BaseDatos baseDatos;
    private Context context;


    public  User(){}

    public  User(Context context){
        baseDatos = new BaseDatos();
        this.context = context;
    }

    public User(String name, String email, int age) {
        this.name = name;
        this.email = email;
        this.age = age;

    }

    @Bindable
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        notifyPropertyChanged(BR.name);
    }
    @Bindable
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
        notifyPropertyChanged(BR.email);
    }
    @Bindable
    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
        notifyPropertyChanged(BR.age);
    }


    @BindingAdapter("android:text")
    public static void setText(TextView view, int value) {

        if (view.getText() != null
                && ( !view.getText().toString().isEmpty() )
                && Integer.parseInt(view.getText().toString()) != value) {
            view.setText(Integer.toString(value));
        }

    }

    @InverseBindingAdapter(attribute = "android:text")
    public static int getText(TextView view) {
        return Integer.parseInt(view.getText().toString());
    }


    public void create(View view){

        baseDatos.create(new User(name,email,age));

    }

    public void search(View view){

        Query query= baseDatos.search(getName());
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.getChildren().iterator().hasNext()){
                    User usuario = dataSnapshot.getChildren().iterator().next().getValue(User.class);
                    setName(usuario.getName());
                    setEmail(usuario.getEmail());
                    setAge(usuario.getAge());

                }
                else{
                    Toast toast = Toast.makeText(context,"Registro no encontrado", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0,0);
                    toast.show();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }

    public  void delete(View view){

        baseDatos.delete(getName());
        setName(null);
        setEmail(null);
        setAge(0);

    }

    public void update(View view){
        baseDatos.update(new User(getName(),getEmail(),getAge()));
    }
}
