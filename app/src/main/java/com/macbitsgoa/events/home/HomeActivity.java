package com.macbitsgoa.events.home;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.navigation.NavigationView;
import com.macbitsgoa.events.BuildConfig;
import com.macbitsgoa.events.Nights.NightsActivity;
import com.macbitsgoa.events.Nights.NightsFragment;
import com.macbitsgoa.events.R;
import com.macbitsgoa.events.SocialActivity;
import com.macbitsgoa.events.aboutfest.AboutEventActivity;
import com.macbitsgoa.events.aboutmac.AboutMacActivity;
import com.macbitsgoa.events.eateries.EateriesCardFragment;
import com.macbitsgoa.events.speakers.SpeakersActivity;
import com.macbitsgoa.events.speakers.SpeakersFragment;
import com.macbitsgoa.events.sponsors.SponsorsFragment;
import com.macbitsgoa.events.maps.MapCardFragment;
import com.macbitsgoa.events.maps.MapsActivity;
import com.macbitsgoa.events.timeline.TimelineCardFragment;
import com.macbitsgoa.events.timer.TimerCardFragment;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.browser.customtabs.CustomTabsIntent;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import static com.macbitsgoa.events.Events.playStoreLink;
import static com.macbitsgoa.events.Utilities.EVENTS;
import static com.macbitsgoa.events.Utilities.MIME_TYPE_PLAINTEXT;
import static com.macbitsgoa.events.timeline.TimelineActivity.launchTimeLineActivity;

/**
 * Main Activity of the App.
 *
 * @author Rushikesh Jogdand [rushikeshjogdand1@gmail.com]
 */
@SuppressLint("GoogleAppIndexingApiWarning")
public class HomeActivity extends AppCompatActivity implements
        NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawerLayout;


    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        populateFeatures();
    }

    private void populateFeatures() {
        final FragmentManager featuresFragManager = getSupportFragmentManager();
        final FragmentTransaction fragmentTransaction = featuresFragManager.beginTransaction();
        for (final Fragment fragment : featuresFragManager.getFragments()) {
            if (fragment instanceof HomeCardInterface) {
                fragmentTransaction.remove(fragment);
            }
        }
        // example use of build config variable

        if (BuildConfig.timer) {
            fragmentTransaction.add(R.id.ll_home,
                    new TimerCardFragment(),
                    "timer"
            );
        }

        if (BuildConfig.speakers) {
            fragmentTransaction.add(R.id.ll_home,
                    new SpeakersFragment(),
                    "speakers"
            );
        }


        if (BuildConfig.nights) {
            fragmentTransaction.add(R.id.ll_home,
                    new NightsFragment(),
                    "nights"
            );
        }



        /*if (BuildConfig.feed) {
            fragmentTransaction.add(R.id.ll_home,
                    new FeedCardFragment(),
                    "feed"
            );
        }*/

        if (BuildConfig.festmerch) {
            fragmentTransaction.add(R.id.ll_home,
                    new com.macbitsgoa.events.festmerch.FestMerchFragment(),
                    "fest merchandise");
        }

        if (BuildConfig.eateries) {
            fragmentTransaction.add(R.id.ll_home,
                    new EateriesCardFragment(),
                    "exhibition"
            );
        }
        if (BuildConfig.timeline) {
            fragmentTransaction.add(R.id.ll_home,
                    new TimelineCardFragment(),
                    getString(R.string.timeline)
            );
        }
        if (BuildConfig.shouldShowMapOnHome) {
            fragmentTransaction.add(R.id.ll_home,
                    new MapCardFragment(),
                    "map");
        }

        if (BuildConfig.sponsors) {
            fragmentTransaction.add(R.id.ll_home,
                    new SponsorsFragment(),
                    "sponsors");
        }


        fragmentTransaction.commit();
    }

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        initViews();

        drawerLayout = findViewById(R.id.drawer_layout);


        final NavigationView navigationView;
        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        final Toolbar toolbar = findViewById(R.id.activity_main_toolbar);
        setSupportActionBar(toolbar);

        final ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_menu_black_24dp);
    }

    private void initViews() {
        setContentView(R.layout.activity_home);
        drawerLayout = findViewById(R.id.drawer_layout);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull final MenuItem menuItem) {

        switch (menuItem.getItemId()) {

            case (R.id.nav_share_app): {
                final Intent shareIntent = new Intent();
                shareIntent.setAction(Intent.ACTION_SEND);
                shareIntent.putExtra(Intent.EXTRA_TEXT,
                        getString(R.string.spam_text) + " " + playStoreLink);

                shareIntent.setType(MIME_TYPE_PLAINTEXT);
                startActivity(Intent.createChooser(shareIntent,
                        getString(R.string.choose_share_medium_prompt)));

                break;
            }

            case (R.id.nav_goa): {
                final CustomTabsIntent intent = new CustomTabsIntent.Builder().build();
                intent.launchUrl(HomeActivity.this, Uri.parse("https://www.justdial.com/Goa/Restaurants-in-Zuarinagar"));
                break;
            }

            case (R.id.nav_events): {
                final CustomTabsIntent intent = new CustomTabsIntent.Builder().build();
                intent.launchUrl(HomeActivity.this, Uri.parse("https://www.bits-goa.ac.in/NFCFA2019/goal.html"));

                break;
            }
            case (R.id.nav_maps): {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW,
                        Uri.parse("https://www.google.com/maps/search/?api=1&query=47.5951518,-122.3316393"));
                startActivity(browserIntent);

                /*final Intent mapIntent = new Intent(HomeActivity.this, MapsActivity.class);
                startActivity(mapIntent);*/
                break;

            }

            case (R.id.nav_registration): {
                final CustomTabsIntent intent = new CustomTabsIntent.Builder().build();
                intent.launchUrl(HomeActivity.this, Uri.parse(EVENTS));
                //Uri uri = Uri.parse(EVENTS);
                //startActivity(new Intent(Intent.ACTION_VIEW, uri));
                break;
            }

            case (R.id.nav_nights): {
                startActivity(new Intent(HomeActivity.this, NightsActivity.class));
                break;

            }

            case (R.id.nav_about_event): {
                //https://www.bits-goa.ac.in/NFCFA2019/goal.html
                startActivity(new Intent(HomeActivity.this, AboutEventActivity.class));
                break;
            }

            case (R.id.nav_about_developers): {
                startActivity(new Intent(HomeActivity.this, AboutMacActivity.class));
                break;
            }

            case (R.id.nav_timeline): {
                final CustomTabsIntent intent = new CustomTabsIntent.Builder().build();
                intent.launchUrl(HomeActivity.this, Uri.parse("https://www.bits-goa.ac.in/NFCFA2019/Download/NFCFA2019_Program%20details_Final.pdf"));
                /*Intent browserIntent = new Intent(Intent.ACTION_VIEW,
                        Uri.parse("https://www.bits-goa.ac.in/NFCFA2019/Download/NFCFA2019_Program%20details_Final.pdf"));
                startActivity(browserIntent);*/
                //launchTimeLineActivity(this);
                break;
            }
            case (R.id.nav_speakers): {
                startActivity(new Intent(HomeActivity.this, SpeakersActivity.class));
                break;
            }
            case (R.id.nav_social): {
                startActivity(new Intent(HomeActivity.this, SocialActivity.class));
                break;
            }


            default: {
                break;
            }
        }

        final DrawerLayout drawerLayout = findViewById(R.id.drawer_layout);
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {

        switch (item.getItemId()) {
            case (android.R.id.home): {
                drawerLayout.openDrawer(GravityCompat.START);
                return true;
            }

            default: {
                return super.onOptionsItemSelected(item);
            }
        }
    }

}
