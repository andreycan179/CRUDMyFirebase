package com.aplicaciones.destroyer.myfirebase;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Destroyer on 28/12/2017.
 */

public class BaseDatos {

    private FirebaseDatabase database;
    private DatabaseReference reference;
    private int countChildren;

    public  BaseDatos(){

        database= FirebaseDatabase.getInstance();
        reference= database.getReference("Users");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                countChildren = Long.valueOf(dataSnapshot.getChildrenCount()).intValue();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    public void create(User usuario){
        reference.child("User"+countChildren).setValue(usuario);

    }

    public Query search(String name){

        return reference.orderByChild("name").equalTo(name);

    }

    public void delete(String name){


        Query query = search(name);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            DatabaseReference ref;
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.getChildren().iterator().hasNext()){
                    ref = dataSnapshot.getChildren().iterator().next().getRef();
                    ref.removeValue();

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }


    public void  update (final User usuario){

        Query query = search(usuario.getName());
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            DatabaseReference ref;
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.getChildren().iterator().hasNext()){
                    ref = dataSnapshot.getChildren().iterator().next().getRef();
                    Map<String, Object> map = new HashMap<>();
                    map.put(ref.getKey(),usuario);
                    reference.updateChildren(map);

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
}
