package com.arab_developer.wow.adapters;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.arab_developer.wow.R;
import com.arab_developer.wow.models.PhotosModel;
import com.squareup.picasso.Picasso;

import java.util.List;

public class SliderStoreDetailsAdapter extends PagerAdapter {
    private List<PhotosModel> imgList;
    private Context context;

    public SliderStoreDetailsAdapter(List<PhotosModel> imgList, Context context) {
        this.imgList = imgList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return imgList.size();
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View view = LayoutInflater.from(context).inflate(R.layout.slider,container,false);
        ImageView image = view.findViewById(R.id.image);
        String url = "https://maps.googleapis.com/maps/api/place/photo?maxwidth=400&photoreference="+imgList.get(position).getPhoto_reference()+"&key=AIzaSyC4l5QxL27z4w0uuD_5X3g0IRhaUdvb0Q4";
        Uri path = Uri.parse(url);
        Picasso.with(context).load(path).fit().into(image);
        container.addView(view);
        return view;

    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View)object);
    }
}
