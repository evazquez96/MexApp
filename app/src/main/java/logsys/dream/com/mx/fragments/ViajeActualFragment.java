package logsys.dream.com.mx.fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import dream.logsys.com.logsysdream.R;
import logsys.dream.com.mx.activities.LoginActivity;
import logsys.dream.com.mx.activities.MainActivity;
import logsys.dream.com.mx.contracts.viaje.to.viajeTO;
import logsys.dream.com.mx.db.ViajesDB;

/**
 * Created by JUAHERNA on 3/8/2018.
 */

public class ViajeActualFragment extends Fragment {
    private static MainActivity mActivity;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = (MainActivity) getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        RecyclerView rv = (RecyclerView) inflater.inflate(R.layout.layout_recylerview_list_1, container, false);
        setupRecyclerView(rv);
        return rv;
    }

    private void setupRecyclerView(RecyclerView recyclerView) {
        ViajesDB db = new ViajesDB(LoginActivity.getAppContext());
        viajeTO viaje = db.obtenerActual();

        viajeTO[] items=new viajeTO[]{
                viaje
        };

        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(new SimpleStringRecyclerViewAdapter(recyclerView, items));
    }

    public static class SimpleStringRecyclerViewAdapter
            extends RecyclerView.Adapter<SimpleStringRecyclerViewAdapter.ViewHolder> {

        private viajeTO[] mValues;
        private RecyclerView mRecyclerView;

        public static class ViewHolder extends RecyclerView.ViewHolder {
            public final View mView;
            public final ImageView img_navegar;
            public final ImageView img_navegar1;
            public final TextView tvsolicitud;
            public final TextView tvcliente;
            public final TextView tvcitacarga;
            public final TextView tvcitadescarga;
            public final TextView tvcp;
            public final TextView tvheader;
            public final TextView tvdestino;
            public final TextView tvdc;
            public final TextView tvdd;
            public final TextView tvorigen;
            public final TextView tvshipment;

            public ViewHolder(View view) {
                super(view);
                mView = view;
                img_navegar = view.findViewById(R.id.img_navegar);
                img_navegar1 = view.findViewById(R.id.img_navegar1);
                tvsolicitud = view.findViewById(R.id.tvheader1);
                tvcitacarga = view.findViewById(R.id.tvcitacarga1);
                tvcitadescarga = view.findViewById(R.id.tvcitadescarga1);
                tvcp = view.findViewById(R.id.tvcp1);
                tvheader = view.findViewById(R.id.tvheader);
                tvdestino = view.findViewById(R.id.tvorigentmp2);
                tvdc = view.findViewById(R.id.tvdc1);
                tvdd = view.findViewById(R.id.tvdd1);
                tvorigen = view.findViewById(R.id.tvorigentmp1);
                tvshipment = view.findViewById(R.id.tvshipment1);
                tvcliente = view.findViewById(R.id.tvcliente1);
                //setear coordenadas de gps
            }
        }

        public SimpleStringRecyclerViewAdapter(RecyclerView recyclerView, viajeTO[] viaje) {
            mValues = viaje;
            mRecyclerView = recyclerView;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.viaje_actual_item, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onViewRecycled(ViewHolder holder) {
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
        public void onBindViewHolder(final ViewHolder holder, final int position) {
            final viajeTO viaje = mValues[position];

            holder.tvsolicitud.setText("Solicitud: "+ viaje.getNumeroSolicitud());
            holder.tvcitacarga.setText(viaje.getCitaCarga());
            holder.tvcitadescarga.setText(viaje.getCitaDescarga());
            holder.tvcp.setText(viaje.getCp());
            holder.tvheader.setText(viaje.getOrigen() + " - " + viaje.getDestino());
            holder.tvdestino.setText(viaje.getDestino());
            holder.tvdc.setText(viaje.getDireccionCarga());
            holder.tvdd.setText(viaje.getDireccionDescarga());
            holder.tvorigen.setText(viaje.getOrigen());
            holder.tvshipment.setText(viaje.getShipment());
            holder.tvcliente.setText(viaje.getCliente());

            holder.img_navegar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    String intermedios = viaje.getIntermedios();
                    if(intermedios!=null && intermedios.length()>0)
                    {
                        intermedios = "&" + intermedios;
                    }
                    else
                        intermedios="";
                    //String uri = "https://www.google.com/maps/dir/?api=1&origin=" + viaje.getoLatitud() +"," + viaje.getoLongitud() + "&destination=" + viaje.getdLatitud()+ "," + viaje.getdLongitud() + intermedios + "&travelmode=driving&dir_action=navigate";
                    String uri = "https://www.google.com/maps/dir/?api=1&destination=" + viaje.getdLatitud()+ "," + viaje.getdLongitud() + intermedios + "&travelmode=driving&dir_action=navigate";
                    Log.d(":::::::::::::::::",uri);
                    Intent intent = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse(uri));
                    intent.setClassName("com.google.android.apps.maps", "com.google.android.maps.MapsActivity");
                    mActivity.startActivity(intent);
                    /* OK

            String uri = "https://www.google.com/maps/dir/?api=1&origin=19.376493,-99.254136&destination=19.755202,-99.177511&waypoints=19.366740, -99.238732%7C19.608414,-99.182855&travelmode=driving&dir_action=navigate";
            Intent intent = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse(uri));
            intent.setClassName("com.google.android.apps.maps", "com.google.android.maps.MapsActivity");
            startActivity(intent);
                    *
                     */
                    /*
                    Uri gmmIntentUri = Uri.parse("google.navigation:q=Cinépolis Patio Santa Fe");
                    //Uri gmmIntentUri = Uri.parse("google.navigation:q=[Capgemini]&daddr=[Cinépolis Patio Santa Fe] to:[Campos Deportivos de Santa Lucía, Avenida Tamaulipas, Zona Federal, Ciudad de México, CDMX] to: [tacos el borrego viudo]");
                    //Uri.parse("https://maps.google.ch/maps?saddr=[address1]&daddr=[address2] to:[address3] to: [address4]"));
                    Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                    mapIntent.setPackage("com.google.android.apps.maps");
                    mActivity.startActivity(mapIntent);
                    */
                }
            });



            holder.img_navegar1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String intermedios = viaje.getDintermedios();
                    if(intermedios!=null && intermedios.length()>0)
                    {
                        intermedios = "&" + intermedios;
                    }
                    else
                        intermedios="";
                    //String uri = "https://www.google.com/maps/dir/?api=1&origin=" + viaje.getDoLatitud() +"," + viaje.getDoLongitud() + "&destination=" + viaje.getDdLatitud()+ "," + viaje.getDdLongitud() + intermedios + "&travelmode=driving&dir_action=navigate";
                    String uri = "https://www.google.com/maps/dir/?api=1&destination=" + viaje.getDdLatitud()+ "," + viaje.getDdLongitud() + intermedios + "&travelmode=driving&dir_action=navigate";
                    Log.d(":::::::::::::::::",uri);
                    Intent intent = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse(uri));
                    intent.setClassName("com.google.android.apps.maps", "com.google.android.maps.MapsActivity");
                    mActivity.startActivity(intent);
                }});
            //holder.mtxt_nombre_viaje.setText(mValues[0].getNumeroSolicitud());
           /* FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) holder.mImageView.getLayoutParams();
            if (mRecyclerView.getLayoutManager() instanceof GridLayoutManager) {
                layoutParams.height = 200;
            } else if (mRecyclerView.getLayoutManager() instanceof StaggeredGridLayoutManager) {
                layoutParams.height = 600;
            } else {
                layoutParams.height = 800;
            }*/
           /*
            final Uri uri = Uri.parse(mValues[position]);
            holder.mImageView.setImageURI(uri);
            holder.mButtonGPS.setText(uri.toString());
*/
            /*
            holder.mLayoutItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mActivity, ItemDetailsActivity.class);
                    intent.putExtra(STRING_IMAGE_URI, mValues[position]);
                    intent.putExtra(STRING_IMAGE_POSITION, position);
                    mActivity.startActivity(intent);

                }
            });

            //Set click action for wishlist
            holder.mImageViewWishlist.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ImageUrlUtils imageUrlUtils = new ImageUrlUtils();
                    imageUrlUtils.addWishlistImageUri(mValues[position]);
                    holder.mImageViewWishlist.setImageResource(R.drawable.ic_favorite_black_18dp);
                    notifyDataSetChanged();
                    Toast.makeText(mActivity,"Item added to wishlist.",Toast.LENGTH_SHORT).show();

                }
            });
*/
        }

        @Override
        public int getItemCount() {
            return mValues.length;
        }
    }

}
