package in.ac.iiitd.iiitd_guesthouseapp.Activities;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.PorterDuff;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.tbuonomo.viewpagerdotsindicator.WormDotsIndicator;

import java.util.Timer;
import java.util.TimerTask;

import in.ac.iiitd.iiitd_guesthouseapp.Adapters.HomeActivityGridAdapter;
import in.ac.iiitd.iiitd_guesthouseapp.Adapters.HomeActivityViewPagerAdapter;
import in.ac.iiitd.iiitd_guesthouseapp.R;

public class HomeActivity extends AppCompatActivity {

    private final int images[] = {R.drawable.f, R.drawable.a, R.drawable.b, R.drawable.c, R.drawable.d};

    private ViewPager homeActivity_viewPager;
    private WormDotsIndicator wormDotsIndicator;
    private HomeActivityViewPagerAdapter homeActivityViewPagerAdapter;
    private RecyclerView homeActivityRecyclerView;
    private HomeActivityGridAdapter homeActivityGridAdapter;
    private String values[];
    GoogleSignInClient googleSignInClient;
    private TextView homeactivity_textView_welcome;
    private int gridImages[];



    //TODO be added in Menu Sign out, help, About Us, Gallery
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


        Toolbar toolbar = findViewById(R.id.homeactivity_toolbar);
        toolbar.setTitle(getString(R.string.homeActivity_toolBarName));
        toolbar.setBackgroundColor(getResources().getColor(R.color.white));
        toolbar.getOverflowIcon().setColorFilter(getResources().getColor(R.color.colorGreen), PorterDuff.Mode.SRC_ATOP);
        setSupportActionBar((toolbar));



        if (android.os.Build.VERSION.SDK_INT >= 21) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.white));
        }

        homeactivity_textView_welcome = findViewById(R.id.homeactivity_textView_welcome);

        homeActivity_viewPager = findViewById(R.id.homeactivity_viewpager);

        gridImages = new int[]
                {R.drawable.bookroom_home_gridicon,
                        R.mipmap.ic_launcher_round,
                        R.mipmap.ic_launcher_round,
                        R.drawable.feedback_home_gridicon};

        homeActivityViewPagerAdapter = new HomeActivityViewPagerAdapter(HomeActivity.this, images);
        homeActivity_viewPager.setAdapter(homeActivityViewPagerAdapter);

        homeActivityRecyclerView = findViewById(R.id.homeactivity_recyclerView);

        values = new String[]{
                this.getResources().getString(R.string.homeActivity_book_room),
                this.getResources().getString(R.string.homeActivity_facilities),
                this.getResources().getString(R.string.homeActivity_details),
                this.getResources().getString(R.string.homeActivity_feedback)
        };

        homeActivityGridAdapter = new HomeActivityGridAdapter(this, R.layout.homeactivity_grid_item, values, gridImages);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);

        homeActivityRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        homeActivityRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.HORIZONTAL));

        homeActivityRecyclerView.setLayoutManager(gridLayoutManager);
        homeActivityRecyclerView.setAdapter(homeActivityGridAdapter);

        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                homeActivity_viewPager.post(new Runnable() {

                    @Override
                    public void run() {
                        homeActivity_viewPager.setCurrentItem((homeActivity_viewPager.getCurrentItem() + 1) % images.length);
                    }
                });
            }
        };
        Timer timer = new Timer();
        timer.schedule(timerTask, 3000, 3000);

        wormDotsIndicator = (WormDotsIndicator) findViewById(R.id.homeactivity_dots_indicator);
        wormDotsIndicator.setViewPager(homeActivity_viewPager);
        // TODO Add name here from google login
        Resources res = getResources();
        String text = String.format(res.getString(R.string.homeActivity_welcomeMessage), getIntent().getStringExtra("name"));
        homeactivity_textView_welcome.setText(text);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_home, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //TODO to be decided for material toolbarmenu
        switch (item.getItemId()) {
            case R.id.menu_signout:
                setClient();
                FirebaseAuth.getInstance().signOut();
                googleSignInClient.signOut();
                startActivity(new Intent(this, LoginActivity.class));
                this.finish();
            /*case R.id.About:
                Toast.makeText(this, item.getTitle().toString(), Toast.LENGTH_SHORT).show();
                break;
            case R.id.Contact:
                Toast.makeText(this, "Contact here", Toast.LENGTH_SHORT).show();
                break;
            case R.id.Gallery:
                Toast.makeText(this, item.getTitle().toString(), Toast.LENGTH_SHORT).show();
                break;
            case R.id.Feedback:
                Toast.makeText(this, item.getTitle().toString(), Toast.LENGTH_SHORT).show();
                break;*/
        }
        return super.onOptionsItemSelected(item);
    }

    void setClient(){
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        googleSignInClient = GoogleSignIn.getClient(this, gso);
    }
}
