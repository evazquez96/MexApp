package dream.logsys.com.logsysdream;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

import logsys.dream.com.mx.fragments.ViajeActualFragment;
import logsys.dream.com.mx.fragments.ViajeListFragment;
import logsys.dream.com.mx.fragments.Viajepre;
import logsys.dream.com.mx.repos.RepViajes;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ViajeFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ViajeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ViajeFragment extends BaseFragment  {


    static ViewPager viewPager;
    static TabLayout tabLayout;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    private Button btn_navegar;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    //private OnFragmentInteractionListener mListener;

    public ViajeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ViajeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ViajeFragment newInstance(String param1, String param2) {
        ViajeFragment fragment = new ViajeFragment();
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

    @Override
    int get_ViewResourceId() {
        return R.layout.fragment_viaje;
    }

    @Override
    String getNombreModulo()
    {
        return "Viajes";
    }


    private PostTaskViajes ViajesTask = null;
    @Override
    public void onRefresh() {
        super.onRefresh();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run()
            {
                ViajesTask = new PostTaskViajes();
                ViajesTask.execute((Void) null);
            }
        },2000);
    }


    public class PostTaskViajes extends AsyncTask<Void, Void, Boolean> {

        PostTaskViajes() {
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            // TODO: attempt authentication against a network service.

            RepViajes rep = new RepViajes();
            rep.inicializarRegistros();

            return true ;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            if (viewPager != null) {
                TabLayout.Tab tab = tabLayout.getTabAt(tabLayout.getSelectedTabPosition());
                setupViewPager(viewPager);
                tabLayout.setupWithViewPager(viewPager);
                tab.select();
            }
            Log.d("ViajeFragment onRefresh","onRefresh");
            progress.hide();
        }

        @Override
        protected void onCancelled() {
            ViajesTask = null;
        }
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        View _view = getActivity().findViewById(R.id.drawer_layout);
        viewPager = _view.findViewById(R.id.viewpager);
        tabLayout = _view.findViewById(R.id.tabs);
        tabLayout.setVisibility(View.VISIBLE);
/*
        btn_navegar = _view.findViewById(R.id.btn_navegar);

        btn_navegar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navegar();
            }
        });
*/
        if (viewPager != null) {
            setupViewPager(viewPager);
            tabLayout.setupWithViewPager(viewPager);
        }
    }

private void navegar()
{
    Uri gmmIntentUri = Uri.parse("google.navigation:q=Cinépolis Patio Santa Fe");
    //Uri gmmIntentUri = Uri.parse("google.navigation:q=[Capgemini]&daddr=[Cinépolis Patio Santa Fe] to:[Campos Deportivos de Santa Lucía, Avenida Tamaulipas, Zona Federal, Ciudad de México, CDMX] to: [tacos el borrego viudo]");
    //Uri.parse("https://maps.google.ch/maps?saddr=[address1]&daddr=[address2] to:[address3] to: [address4]"));
    Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
    mapIntent.setPackage("com.google.android.apps.maps");
    startActivity(mapIntent);
}

    private void setupViewPager(ViewPager viewPager) {
        //FragmentManager a = getFragmentManager();
        Adapter adapter = new Adapter(getChildFragmentManager());

        Viajepre fragment1 = new Viajepre();
        Bundle bundle = new Bundle();
        bundle.putInt("type", 1);
        fragment1.setArguments(bundle);
        adapter.addFragment(fragment1, "Preasignados");

        ViajeActualFragment fragment3= new ViajeActualFragment();
        bundle  = new Bundle();
        bundle.putInt("type", 3);
        fragment3.setArguments(bundle);
        adapter.addFragment(fragment3, getString(R.string.tab_viaje_curso));


        ViajeListFragment fragment = new ViajeListFragment();
        bundle = new Bundle();
        bundle.putInt("type", 2);
        fragment.setArguments(bundle);
        adapter.addFragment(fragment, getString(R.string.tab_viaje_histrico));

        viewPager.setAdapter(adapter);

    }

    /*
    @Override
    public void showEmptyState(boolean empty) {

        //mRecyclerView.setVisibility(empty ? View.GONE : View.VISIBLE);
        //mNoMessagesView.setVisibility(empty ? View.VISIBLE : View.GONE);
    }

    interface View extends BaseView<Presenter>{


        //void showNotifications(ArrayList<PushNotification> notifications);

        void showEmptyState(boolean empty);

        //void popPushNotification(PushNotification pushMessage);
    }

    interface Presenter extends BasePresenter{

    }

    public interface BasePresenter {
        //void start();
    }

    public interface BaseView<T> {
        //void setPresenter(T presenter);
    }
*/

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
