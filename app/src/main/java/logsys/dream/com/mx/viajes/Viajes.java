package logsys.dream.com.mx.viajes;

import android.net.Uri;
import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import dream.logsys.com.logsysdream.R;
import logsys.dream.com.mx.viajes.fragments.ViajeActual;
import logsys.dream.com.mx.viajes.fragments.ViajePreasignado;
import logsys.dream.com.mx.viajes.fragments.ViajesTerminados;

public class Viajes extends AppCompatActivity implements ViajePreasignado.OnFragmentInteractionListener,ViajeActual.OnFragmentInteractionListener,ViajesTerminados.OnFragmentInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viajes);

        TabLayout tabLayout = (TabLayout)findViewById(R.id.tablayout);
        tabLayout.addTab(tabLayout.newTab().setText("Viajes Preasignados"));
        tabLayout.addTab(tabLayout.newTab().setText("Viaje Actual"));
        tabLayout.addTab(tabLayout.newTab().setText("Viajes Terminados"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        final ViewPager viewPager = (ViewPager)findViewById(R.id.pager);
        final PageAdapter adapter = new PageAdapter(getSupportFragmentManager(),tabLayout.getTabCount());
        viewPager.setAdapter(adapter);
        viewPager.setOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
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

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
