package com.example.mytrips;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.mytrips.databinding.ActivityMainBinding;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class MainActivity<mDatabase> extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainBinding binding;
    private DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    public static final long MEGABYTES = 1024 * 1024;
    public static Bitmap mBitmap = null;
    private final StorageReference mStorageReference =  FirebaseStorage.getInstance().getReference();
    private FirebaseUser mUser;
    private String mUserId;
    private TextView mUserName;
    private TextView mUserEmail;
    private ImageView mUserImage;
    private Intent mIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appBarMain.toolbar);
        binding.appBarMain.fab.setOnClickListener(view -> {
                    mIntent = new Intent(MainActivity.this, AddTripPage.class);
                    startActivity(mIntent);
                });
        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_upcoming,R.id.nav_history, R.id.nav_map_history)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
        View headerView = navigationView.getHeaderView(0);
        mUser = mAuth.getCurrentUser();
        assert mUser != null;
        mUserId = mUser.getUid();
        mUserName = headerView.findViewById(R.id.nav_username);
        mUserEmail = headerView.findViewById(R.id.addTripPage_txt);
        mUserImage = headerView.findViewById(R.id.user_img);
        mDatabase.child(mUserId).child("username").get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                mUserName.setText((CharSequence) task.getResult().getValue());
                mUserEmail.setText(mUser.getEmail());
                mUserImage.setImageBitmap(downloadImage(mUser.getUid()));
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id  = item.getItemId();
        if (id == R.id.nav_upcoming) {
            Intent intent = new Intent(this, UpcomingPage.class);
            startActivity(intent);
        }

        else if(id == R.id.nav_history) {
            startActivity(new Intent(MainActivity.this, AddNotePage.class));
        }

        DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
    private Bitmap downloadImage(String fileName)
    {
        // Create a reference with an initial file path and name
        StorageReference imgRef =  mStorageReference.child("images/" + fileName);
        Task<byte[]> bytes = imgRef.getBytes(MEGABYTES);
        while(!bytes.isComplete());
        mBitmap = BitmapFactory.decodeByteArray(bytes.getResult(),0,bytes.getResult().length);
        return Bitmap.createScaledBitmap(mBitmap,
                (int) (mUserImage.getWidth()*0.8),
                (int) (mUserImage.getHeight()*0.8),
                true);
    }
}