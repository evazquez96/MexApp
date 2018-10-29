package dream.logsys.com.logsysdream;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import logsys.dream.com.mx.fragments.ImageListFragment;
import logsys.dream.com.mx.fragments.ViajeActualFragment;

/**
 * A fragment with a Google +1 button.
 * Activities that contain this fragment must implement the
 * {@link NotificacionFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link NotificacionFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NotificacionFragment extends BaseFragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    // The request code must be 0 or greater.
    private static final int PLUS_ONE_REQUEST_CODE = 0;
    // The URL to +1.  Must be a valid URL.
    private final String PLUS_ONE_URL = "http://developer.android.com";
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public NotificacionFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment NotificacionFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static NotificacionFragment newInstance(String param1, String param2) {
        NotificacionFragment fragment = new NotificacionFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    static TabLayout tabLayout;

    @Override
    int get_ViewResourceId() {
        return R.layout.fragment_notificacion;
    }

    @Override
    String getNombreModulo()
    {
        return "Notificaciones";
    }

    static ViewPager viewPager;
FloatingActionButton btn;

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        View _view = getActivity().findViewById(R.id.drawer_layout);
        viewPager = _view.findViewById(R.id.viewpager);
        tabLayout = _view.findViewById(R.id.tabs);

        viewPager.setVisibility(View.VISIBLE);
        tabLayout.setVisibility(View.VISIBLE);

        btn = getView().findViewById(R.id.fab_nueva_solicitud);
        btn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Toast.makeText(getActivity(),"aaaa",Toast.LENGTH_LONG).show();
/*
                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                ft.replace(R.id.mainFrame, new NewNotification()).addToBackStack( "tag" );
                ft.commitNow();
                */
                //ft.commit();

            }
        });

        if (viewPager != null) {

            setupViewPager(viewPager);

            tabLayout.setupWithViewPager(viewPager);
        }

/*
        NotificationsDB dn = new NotificationsDB(globalVariable);
        Notification_TO notificacion = null;
        for(int i=0;i<3;i++)
        {
            notificacion = new Notification_TO();
            notificacion.setComentarios("Comentarios" + i);
            notificacion.setId(i);
            notificacion.setFecha("Fecha" + i);
            notificacion.setTipoNotificacion("Alerta");
            notificacion.setTipoNotificacion_id(i);

            System.out.println("Insertando....");
            dn.insertarNotificacion(notificacion);
        }


        System.out.println("Recuperando notificaciones");


        for (Notification_TO n: dn.obtenerNotificaciones()) {
            System.out.println("Notificacion recuperada:::::" + n);
        }
        */
    }

    @Override
    public void onRefresh() {
        super.onRefresh();
        progress.hide();
    }

    private void setupViewPager(ViewPager viewPager) {
        Adapter adapter = new Adapter(getFragmentManager());



        ImageListFragment fragment = new ImageListFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("type", 1);
        fragment.setArguments(bundle);
        adapter.addFragment(fragment, getString(R.string.item_principal_1));

        ViajeActualFragment viajeActualFragment = new ViajeActualFragment();
        bundle = new Bundle();
        bundle.putInt("type", 2);
        fragment.setArguments(bundle);
        adapter.addFragment(viajeActualFragment, getString(R.string.item_principal_2) + "111");

        viewPager.setAdapter(adapter);

    }

    @Override
    public void onResume() {
        super.onResume();

        // Refresh the state of the +1 button each time the activity receives focus.
        //mPlusOneButton.initialize(PLUS_ONE_URL, PLUS_ONE_REQUEST_CODE);
    }

    static class Adapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragments = new ArrayList<>();
        private final List<String> mFragmentTitles = new ArrayList<>();

        public Adapter(FragmentManager fm) {
            super(fm);
        }

        public void addFragment(Fragment fragment, String title) {
            mFragments.add(fragment);
            mFragmentTitles.add(title);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitles.get(position);
        }
    }

}
