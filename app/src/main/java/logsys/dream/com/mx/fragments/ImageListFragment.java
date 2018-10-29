package logsys.dream.com.mx.fragments;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.facebook.drawee.view.SimpleDraweeView;

import dream.logsys.com.logsysdream.R;
import logsys.dream.com.mx.activities.MainActivity;
import logsys.dream.com.mx.contracts.notifications.Notification_TO;
import logsys.dream.com.mx.db.NotificationsDB;
import logsys.dream.com.mx.startup.FrescoApplication;

/**
 * Created by JUAHERNA on 2/15/2018.
 */

public class ImageListFragment extends Fragment
        //implements        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener
{

    public static final String STRING_IMAGE_URI = "ImageUri";
    public static final String STRING_IMAGE_POSITION = "ImagePosition";
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

    /*
        @Override
        public void onConnected(Bundle connectionHint) {

        }
        */
/*
    @Override
    public void onConnectionSuspended(int i) {

    }
*/
    private void setupRecyclerView(RecyclerView recyclerView) {
      /*  if (ImageListFragment.this.getArguments().getInt("type") == 1) {
            recyclerView.setLayoutManager(new LinearLayoutManager(recyclerView.getContext()));
        } else if (ImageListFragment.this.getArguments().getInt("type") == 2) {
            StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
            recyclerView.setLayoutManager(layoutManager);
        } else {
            GridLayoutManager layoutManager = new GridLayoutManager(recyclerView.getContext(), 3);
            layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            recyclerView.setLayoutManager(layoutManager);
        }*/
        String[] items=new String[]{
                "https://static.pexels.com/photos/1543/landscape-nature-man-person-medium.jpg",
                "https://static.pexels.com/photos/211048/pexels-photo-211048-medium.jpeg",
                "https://static.pexels.com/photos/1778/numbers-time-watch-white-medium.jpg",
                "https://static.pexels.com/photos/111147/pexels-photo-111147-medium.jpeg",
                "https://static.pexels.com/photos/2713/wall-home-deer-medium.jpg",
                "https://static.pexels.com/photos/168575/pexels-photo-168575-medium.jpeg",
                "https://static.pexels.com/photos/213384/pexels-photo-213384-medium.jpeg",
                "https://static.pexels.com/photos/67442/pexels-photo-67442-medium.jpeg",
                "https://static.pexels.com/photos/159494/book-glasses-read-study-159494-medium.jpeg",
                "https://static.pexels.com/photos/1543/landscape-nature-man-person-medium.jpg",
                "https://static.pexels.com/photos/211048/pexels-photo-211048-medium.jpeg",
                "https://static.pexels.com/photos/2713/wall-home-deer-medium.jpg",
                "https://static.pexels.com/photos/177143/pexels-photo-177143-medium.jpeg",
                "https://static.pexels.com/photos/106936/pexels-photo-106936-medium.jpeg",
                "https://static.pexels.com/photos/177143/pexels-photo-177143-medium.jpeg",
                "https://static.pexels.com/photos/177143/pexels-photo-177143-medium.jpeg",
                "https://static.pexels.com/photos/177143/pexels-photo-177143-medium.jpeg",
                "https://static.pexels.com/photos/177143/pexels-photo-177143-medium.jpeg",
                "https://static.pexels.com/photos/177143/pexels-photo-177143-medium.jpeg",
                "https://static.pexels.com/photos/177143/pexels-photo-177143-medium.jpeg",
                "https://static.pexels.com/photos/177143/pexels-photo-177143-medium.jpeg",
                "https://static.pexels.com/photos/177143/pexels-photo-177143-medium.jpeg",
                "https://static.pexels.com/photos/177143/pexels-photo-177143-medium.jpeg",
                "https://static.pexels.com/photos/177143/pexels-photo-177143-medium.jpeg",
                "https://static.pexels.com/photos/177143/pexels-photo-177143-medium.jpeg"
        };

        NotificationsDB dn = new NotificationsDB((FrescoApplication) getActivity().getApplicationContext());
        Notification_TO notificacion = null;
        if(ImageListFragment.this.getArguments().getInt("type")==1)
        {
            items= new String[dn.obtenerNotificaciones().size()];
            int k=0;
            for (Notification_TO n: dn.obtenerNotificaciones()) {
                items[k] = String.valueOf(n.getId());
                k++;
            }
        }
        /*
        if (ImageListFragment.this.getArguments().getInt("type") == 1){
            items =ImageUrlUtils.getOffersUrls();
        }else if (ImageListFragment.this.getArguments().getInt("type") == 2){
            items =ImageUrlUtils.getElectronicsUrls();
        }else if (ImageListFragment.this.getArguments().getInt("type") == 3){
            items =ImageUrlUtils.getLifeStyleUrls();
        }else if (ImageListFragment.this.getArguments().getInt("type") == 4){
            items =ImageUrlUtils.getHomeApplianceUrls();
        }else if (ImageListFragment.this.getArguments().getInt("type") == 5){
            items =ImageUrlUtils.getBooksUrls();
        }else {
            items = ImageUrlUtils.getImageUrls();
        }
        */
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(new SimpleStringRecyclerViewAdapter(recyclerView, items));
    }

    /*
    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }
*/
    public static class SimpleStringRecyclerViewAdapter
            extends RecyclerView.Adapter<SimpleStringRecyclerViewAdapter.ViewHolder> {

        private String[] mValues;
        private RecyclerView mRecyclerView;

        public static class ViewHolder extends RecyclerView.ViewHolder {
            public final View mView;
            public final SimpleDraweeView mImageView;
            public final LinearLayout mLayoutItem;
            public final ImageView mImageViewWishlist;
            public final Button mButtonGPS;

            //btn_getGps
            public ViewHolder(View view) {
                super(view);
                mView = view;
                mImageView = (SimpleDraweeView) view.findViewById(R.id.image1);
                mLayoutItem = (LinearLayout) view.findViewById(R.id.layout_item);
                mImageViewWishlist = (ImageView) view.findViewById(R.id.ic_wishlist);
                mButtonGPS =view.findViewById(R.id.btn_getGps);

            }
        }

        public SimpleStringRecyclerViewAdapter(RecyclerView recyclerView, String[] items) {
            mValues = items;
            mRecyclerView = recyclerView;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onViewRecycled(ViewHolder holder) {
            if (holder.mImageView.getController() != null) {
                holder.mImageView.getController().onDetach();
            }
            if (holder.mImageView.getTopLevelDrawable() != null) {
                holder.mImageView.getTopLevelDrawable().setCallback(null);
//                ((BitmapDrawable) holder.mImageView.getTopLevelDrawable()).getBitmap().recycle();
            }
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, final int position) {
           /* FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) holder.mImageView.getLayoutParams();
            if (mRecyclerView.getLayoutManager() instanceof GridLayoutManager) {
                layoutParams.height = 200;
            } else if (mRecyclerView.getLayoutManager() instanceof StaggeredGridLayoutManager) {
                layoutParams.height = 600;
            } else {
                layoutParams.height = 800;
            }*/
            final Uri uri = Uri.parse(mValues[position]);
            holder.mImageView.setImageURI(uri);
            holder.mButtonGPS.setText(uri.toString());

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
