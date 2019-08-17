package com.example.chat;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class DashBoard extends AppCompatActivity {

        private FirebaseAuth mAuth;
    ActionBar actionBar;
    TabLayout mtabl;
    ViewPager viewPager;
    private SectionPageAdapter mSectionp ;


    @Override
        protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);


        actionBar = getSupportActionBar();
        actionBar.setTitle("Schat");


        mAuth = FirebaseAuth.getInstance();

        actionBar.setTitle("Schat");
        mtabl = findViewById(R.id.Tbs);
        mtabl.addTab(mtabl.newTab().setText("Request"));
        mtabl.addTab(mtabl.newTab().setText("Chats"));
        mtabl.addTab(mtabl.newTab().setText("Friend"));
        mtabl.setTabGravity(TabLayout.GRAVITY_FILL);
        viewPager = findViewById(R.id.pageview);
        SectionPageAdapter adapter = new SectionPageAdapter(getSupportFragmentManager(), mtabl.getTabCount());

        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mtabl));
        mtabl.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
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
    }
    private void checkuserstatus(){

        FirebaseUser user  =mAuth.getCurrentUser();

        if (user!=null){

            //display user  info



        }else{
            startActivity(new Intent(DashBoard.this,MainActivity.class));
            finish();
        }
    }

      @Override
        protected void onStart() {
            checkuserstatus();
            super.onStart();

        }

/*        @Override
        public void onStart() {

            super.onStart();
            // Check if user is signed in (non-null) and update UI accordingly.
            FirebaseUser currentUser = mAuth.getCurrentUser();
            if (currentUser == null) {
                sendtostart();
            }
        }

        private void sendtostart() {

            Intent Startintent = new Intent(DashBoard.this, MainActivity.class);


            startActivity(Startintent);
            finish();

        }*/

       @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id=item.getItemId();
        if (id==R.id.logout_item) {
            mAuth.signOut();
            checkuserstatus();

        }
        if (id==R.id.prof){
            Intent prof =new Intent(DashBoard.this, com.example.chat.prof.class);
            startActivity(prof);

        }
        if (id==R.id.alluser){
            Intent alluser =new Intent(DashBoard.this, com.example.chat.all_user.class);
            startActivity(alluser);

        }


        return super.onOptionsItemSelected(item);
    }

       /*     Friend frig1=new Friend();
            FragmentTransaction ft1= getSupportFragmentManager().beginTransaction();
            ft1.replace(R.id.content,frig1,"");
            ft1.commit();


        }

        private BottomNavigationView.OnNavigationItemSelectedListener selectedListener=
               new BottomNavigationView.OnNavigationItemSelectedListener() {
                   @Override
                   public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                       switch (menuItem.getItemId()){
                           case  R.id.nav_home:
                               actionBar.setTitle("Home");
                               Friend frig1=new Friend();
                               FragmentTransaction ft1= getSupportFragmentManager().beginTransaction();
                               ft1.replace(R.id.content,frig1,"");
                               ft1.commit();
                               return  true;
                        /*   case  R.id.nav_profile:
                               actionBar.setTitle("Profile");
                               ProfileFragment frig2=new ProfileFragment();
                               FragmentTransaction ft2= getSupportFragmentManager().beginTransaction();
                               ft2.replace(R.id.content,frig2,"");
                               ft2.commit();
                               return  true;
                           case  R.id.navi_user:
                               actionBar.setTitle("Users");
                               UserFragment frig3=new UserFragment();
                               FragmentTransaction ft3= getSupportFragmentManager().beginTransaction();
                               ft3.replace(R.id.content,frig3,"");
                               ft3.commit();
                               return  true;

                       }
                       return  false;
                   }
               };*/
     /*   private void checkuserstatus(){

            FirebaseUser user  =mAuth.getCurrentUser();

            if (user!=null){

                //display user  info



            }else{
                startActivity(new Intent(DashBoard.this,MainActivity.class));
                finish();
            }
        }

      /*  @Override
        protected void onStart() {
            checkuserstatus();
            super.onStart();

        }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
            getMenuInflater().inflate(R.menu.main_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

            int id=item.getItemId();
        if (id==R.id.logout_item) {
            mAuth.signOut();
            checkuserstatus();

        }
        if (id==R.id.prof){
                Intent prof =new Intent(DashBoard.this, com.example.chat.prof.class);
                startActivity(prof);

        }
        if (id==R.id.alluser){
            Intent alluser =new Intent(DashBoard.this, com.example.chat.all_user.class);
            startActivity(alluser);

        }


        return super.onOptionsItemSelected(item);
    }*/
}
