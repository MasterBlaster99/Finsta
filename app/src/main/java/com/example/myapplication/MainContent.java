package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.firebase.ui.auth.AuthUI;
import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseUser;

public class MainContent extends AppCompatActivity {
    FirebaseUser firebaseAuth;
    ViewPager viewPager;
    TabLayout tablayout;
    TabItem firstitem, seconditem, thirditem;
    DrawerLayout drawerLayout;
    PagerAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_content);
        Toolbar toolbar =findViewById(R.id.toolbar);
        toolbar.setTitle("Finsta");
        setSupportActionBar(toolbar);

        viewPager = findViewById(R.id.viewpager);
        tablayout = findViewById(R.id.tablayout);
        firstitem = findViewById(R.id.firstitem);
        seconditem = findViewById(R.id.seconditem);
        thirditem = findViewById(R.id.thirditem);
        drawerLayout = findViewById(R.id.drawerLayout);
        adapter = new PagerAdapter(getSupportFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT,tablayout.getTabCount());
        viewPager.setAdapter(adapter);
        tablayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tablayout));
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item1 :Intent i=new Intent(getApplicationContext(),MainActivity.class);
                startActivity(i);
                AuthUI.getInstance().signOut(this);
                return true;
            case R.id.item2 :  Intent intent=new Intent(getApplicationContext(),ProfileActivity.class);
                startActivity(intent);
                return true;
            default:return super.onOptionsItemSelected(item);
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.example_menu, menu);
        return true;
    }
}