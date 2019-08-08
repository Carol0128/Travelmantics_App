package com.example.travelmantics;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class UserActivity extends AppCompatActivity
{
   /* ArrayList<TravelDeal> mdeals;
    private FirebaseDatabase myFirebaseDb;
    private DatabaseReference myDbRef;
    private ChildEventListener myChildListener;
*/
    //FirebaseUtil.openFirebaseRef("traveldeals");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.user_activity_menu, menu);
        MenuItem insertMenu = menu.findItem(R.id.insert_menu);
        if (FirebaseUtil.isAdmin == true) {
            insertMenu.setVisible(true);
        }
        else {
            insertMenu.setVisible(false);
        }


        return true;
       // return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId()) {
            case R.id.insert_menu:
                Intent intent = new Intent(this, AdminsActivity.class);
                startActivity(intent);
                return true;
            case R.id.logout_menu:
                AuthUI.getInstance()
                        .signOut(this)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            public void onComplete(@NonNull Task<Void> task) {
                                Log.d("Logout", "User Logged Out");
                                FirebaseUtil.attachListener();
                            }
                        });
                FirebaseUtil.detachListener();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPause()
    {
        super.onPause();
        FirebaseUtil.detachListener();
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        FirebaseUtil.openFirebaseRef("traveldeals", this);
        RecyclerView rvDeals = (RecyclerView) findViewById(R.id.rvDeals);
        final DealAdapter adapter = new DealAdapter();
        rvDeals.setAdapter(adapter);
        LinearLayoutManager dealsLayoutManager =
                new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        rvDeals.setLayoutManager(dealsLayoutManager);
        FirebaseUtil.attachListener();
    }
    public void showMenu() {
        invalidateOptionsMenu();
    }
}
