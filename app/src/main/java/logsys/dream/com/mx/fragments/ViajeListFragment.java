package logsys.dream.com.mx.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import dream.logsys.com.logsysdream.R;
import logsys.dream.com.mx.activities.ConsultaViajeActivity;
import logsys.dream.com.mx.activities.MainActivity;
import logsys.dream.com.mx.contracts.viaje.to.viajeTO;
import logsys.dream.com.mx.db.ViajesDB;
import logsys.dream.com.mx.startup.FrescoApplication;

/**
 * Created by JUAHERNA on 2/25/2018.
 */

public class ViajeListFragment extends Fragment
{
    private static MainActivity mActivity;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = (MainActivity) getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        RecyclerView rv = (RecyclerView) inflater.inflate(R.layout.layout_recylerview_list, container, false);
        setupRecyclerView(rv);
        return rv;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    private void setupRecyclerView(RecyclerView recyclerView) {
        ViajesDB vdb = new ViajesDB((FrescoApplication) getActivity().getApplicationContext());
        viajeTO[] viajes = new viajeTO[vdb.countRows("Viajes")];

        if(ViajeListFragment.this.getArguments().getInt("type") == 2)
        {
            List<viajeTO> r = vdb.obtenerNotificaciones("Cerrado");
            viajes = r.toArray(new viajeTO[r.size()]);
        }
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(new ViajesRecyclerViewAdapter(recyclerView,(viajes)));
    }

    public static class ViajesRecyclerViewAdapter
            extends RecyclerView.Adapter<ViajesRecyclerViewAdapter.ViewHolder> {

        private viajeTO[] mViajes;

        public static class ViewHolder extends RecyclerView.ViewHolder {
            public final View mView;

            public final TextView mNombreViaje;
            public final TextView mCliente;
            public final TextView mComentarios;
            public final TextView mViajeSolicitud;

            public ViewHolder(View view) {
                super(view);

                mView = view;
                mNombreViaje = view.findViewById(R.id.txt_nombre_viaje);
                mCliente = view.findViewById(R.id.txt_cliente_viaje);
                mComentarios = view.findViewById(R.id.txt_viaje_comentarios);
                mViajeSolicitud = view.findViewById(R.id.txt_viaje_solicitud);
            }
        }

        public ViajesRecyclerViewAdapter(RecyclerView recyclerView, viajeTO[] viajes) {
            mViajes = viajes;



        }

        @Override
        public ViajesRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.viaje_item, parent, false);




            return new ViajesRecyclerViewAdapter.ViewHolder(view);
        }

        @Override
        public void onViewRecycled(ViajesRecyclerViewAdapter.ViewHolder holder) {
            /*
            if (holder.mImageView.getController() != null) {
                holder.mImageView.getController().onDetach();
            }
            if (holder.mImageView.getTopLevelDrawable() != null) {
                holder.mImageView.getTopLevelDrawable().setCallback(null);
//                ((BitmapDrawable) holder.mImageView.getTopLevelDrawable()).getBitmap().recycle();
            }
            */
        }

        @Override
        public void onBindViewHolder(final ViajesRecyclerViewAdapter.ViewHolder holder, final int position) {
            try {
                holder.mNombreViaje.setText(mViajes[position].getOrigen() + " - " + mViajes[position].getDestino());
                holder.mCliente.setText(mViajes[position].getCliente());
                holder.mComentarios.setText(mViajes[position].getCitaCarga());
                holder.mViajeSolicitud.setText("Solicitud: " + mViajes[position].getNumeroSolicitud());


                holder.mView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //Toast.makeText(view.getContext(),"Click",Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(view.getContext(), ConsultaViajeActivity.class);
                        intent.putExtra("id", mViajes[position].getId());
                        mActivity.startActivity(intent);
                    }
                });

           /* FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) holder.mImageView.getLayoutParams();
            if (mRecyclerView.getLayoutManager() instanceof GridLayoutManager) {
                layoutParams.height = 200;
            } else if (mRecyclerView.getLayoutManager() instanceof StaggeredGridLayoutManager) {
                layoutParams.height = 600;
            } else {
                layoutParams.height = 800;
            }*/
            }catch (Exception ex) {

            }
        }

        @Override
        public int getItemCount() {
            return mViajes.length;
        }



    }


}
