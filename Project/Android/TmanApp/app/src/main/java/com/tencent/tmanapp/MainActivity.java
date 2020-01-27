package com.tencent.tmanapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.annotation.NonNull;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.tencent.tmanapp.menu.DashboardFragment;
import com.tencent.tmanapp.menu.HomeFragment;
import com.tencent.tmanapp.menu.MessageFragment;

public class MainActivity extends AppCompatActivity implements
        HomeFragment.OnFragmentInteractionListener,
        DashboardFragment.OnFragmentInteractionListener,
        MessageFragment.OnFragmentInteractionListener{
    // 底部菜单
    private Fragment homeFragment;
    private Fragment dashboardFragment;
    private Fragment messageFragment;
    private Fragment[] listFragment;
    private int lastIndex;

    private void initData(){
        homeFragment = new HomeFragment();
        dashboardFragment = new DashboardFragment();
        messageFragment = new MessageFragment();
        listFragment = new Fragment[]{homeFragment, dashboardFragment, messageFragment};
        lastIndex = 0;
        // 默认显示 home fragment
        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentView, homeFragment).show(homeFragment).commit();
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    setFragment(lastIndex, 0);
                    lastIndex = 0;
                    return true;
                case R.id.navigation_dashboard:
                    setFragment(lastIndex, 1);
                    lastIndex = 1;
                    return true;
                case R.id.navigation_notifications:
                    setFragment(lastIndex, 2);
                    lastIndex = 2;
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initData();

        BottomNavigationView navView = findViewById(R.id.nav_view);
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    private void setFragment(int lastIndex, int index){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.hide(listFragment[lastIndex]);
        if (!listFragment[index].isAdded()){
            transaction.add(R.id.fragmentView, listFragment[index]);
        }
        transaction.show(listFragment[index]).commitAllowingStateLoss();
    }

    @Override
    public void onFragmentInteraction(Uri uri) {
        Toast.makeText(getApplicationContext(), "click", Toast.LENGTH_SHORT).show();
    }
}
