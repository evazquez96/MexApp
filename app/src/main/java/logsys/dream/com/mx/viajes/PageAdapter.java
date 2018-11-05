package logsys.dream.com.mx.viajes;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import logsys.dream.com.mx.viajes.fragments.ViajeActual;
import logsys.dream.com.mx.viajes.fragments.ViajePreasignado;
import logsys.dream.com.mx.viajes.fragments.ViajesTerminados;

public class PageAdapter extends FragmentStatePagerAdapter {

    int noOfTabs;

    public PageAdapter(FragmentManager fm, int numberOfTabs) {
        super(fm);
        this.noOfTabs = numberOfTabs;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                ViajePreasignado vPreasignado = new ViajePreasignado();
                return vPreasignado;
            case 1:
                ViajeActual vActual = new ViajeActual();
                return vActual;
            case 2:
                ViajesTerminados vTerminados = new ViajesTerminados();
                return vTerminados;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return noOfTabs;
    }
}
