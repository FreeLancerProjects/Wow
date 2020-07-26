package com.arab_developer.wow.activities_fragments.activity_home.activity_fragment_category;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.PorterDuff;
import android.location.Location;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;

import com.arab_developer.wow.R;
import com.arab_developer.wow.activities_fragments.activity_home.activity_map.MapActivity;
import com.arab_developer.wow.activities_fragments.activity_home.activity_reviews.ReviewsActivity;

import com.arab_developer.wow.adapters.SliderCatogryAdapter;
import com.arab_developer.wow.language.Language_Helper;
import com.arab_developer.wow.models.CategoryModel;
import com.arab_developer.wow.models.OrderIdDataModel;
import com.arab_developer.wow.models.SelectedLocation;
import com.arab_developer.wow.models.SingleCategoryModel;
import com.arab_developer.wow.models.UserModel;
import com.arab_developer.wow.preferences.Preferences;
import com.arab_developer.wow.remote.Api;
import com.arab_developer.wow.share.Common;
import com.arab_developer.wow.tags.Tags;
import com.arab_developer.wow.activities_fragments.activity_home.client_home.fragments.fragment_home.Fragment_Map;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.iarcuschin.simpleratingbar.SimpleRatingBar;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

import de.hdodenhof.circleimageview.CircleImageView;
import io.paperdb.Paper;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CategoryActivity extends AppCompatActivity {
    private CategoryModel.Data data;
    private String lang;
    private TextView tv_name, tv_content, tv_rate, tv_addess, tv_address, tv_time, tv_status;
    private CircleImageView imageView;
    private ImageView image_details;
    private SimpleRatingBar simpleRatingBar;
    private ConstraintLayout cons_add_coupon;
    private LinearLayout llreview;
    private ViewPager pager;
    private TabLayout tab;
    private int current_page = 0, NUM_PAGES;
    private SliderCatogryAdapter sliderCatogryAdapter;
    private LinearLayout ll_change,ll_choose_delivery_time;
    private SelectedLocation selectedLocation;
    private ImageView arrow1, arrow2, arrow3, imback;
    private ProgressBar progressBar;
    private Button btnOrderNow;
    public Location location = null;
    private long selected_time=0;
    private Fragment_Map fragment_map;
    private FragmentManager fragmentManager;
    private String [] timesList;
    private SingleCategoryModel singlecategory;
    private EditText edt_order_details;
    private Preferences preference;
    private UserModel userModel;
    private String order_details;
    private FloatingActionButton fab;
    private final int IMG1=3,IMG2=4;
    private Uri uri=null;
    private final String read_permission = Manifest.permission.READ_EXTERNAL_STORAGE;
    private final String write_permission = Manifest.permission.WRITE_EXTERNAL_STORAGE;
    private final String camera_permission = Manifest.permission.CAMERA;

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(Language_Helper.updateResources(base, Language_Helper.getLanguage(base)));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.slide_up, R.anim.slide_up);

        setContentView(R.layout.activity_category);
        getdatafromintent();
        initview();
        getsinglecat();
        change_slide_image();
    }

    private void initview() {
        preference=Preferences.getInstance();
        userModel=preference.getUserData(this);
        timesList = new String[]{getString(R.string.hour1),
                getString(R.string.hour2),
                getString(R.string.hour3),
                getString(R.string.day1),
                getString(R.string.day2),
                getString(R.string.day3)

        };
        Paper.init(this);
        lang = Paper.book().read("lang", Locale.getDefault().getLanguage());
        fragmentManager = getSupportFragmentManager();
        tv_name = findViewById(R.id.tv_name);
        tv_content = findViewById(R.id.tv_content);
        tv_rate = findViewById(R.id.tv_rate);
        tv_status = findViewById(R.id.tv_status);
        tv_time = findViewById(R.id.tv_time);
        simpleRatingBar = findViewById(R.id.rateBar);
        imageView = findViewById(R.id.image);
        pager = findViewById(R.id.pager);
        ll_change = findViewById(R.id.ll_change);
        ll_choose_delivery_time=findViewById(R.id.ll_time);
        tv_addess = findViewById(R.id.tv_address);
        fab = findViewById(R.id.fab);
        tv_address = findViewById(R.id.tv_address1);
        edt_order_details = findViewById(R.id.edt_order_details);
        llreview=findViewById(R.id.ll_review);
        arrow1 = findViewById(R.id.arrow1);
        arrow2 = findViewById(R.id.arrow2);
        arrow3 = findViewById(R.id.arrow3);
        imback = findViewById(R.id.image_back);
        progressBar = findViewById(R.id.progBarSlider);
        btnOrderNow = findViewById(R.id.btnOrderNow);
        cons_add_coupon = findViewById(R.id.cons_add_coupon);
        image_details = findViewById(R.id.image_details);
        cons_add_coupon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(userModel!=null){
                    if (userModel.getData().getUser_type().equals(Tags.TYPE_DELEGATE))
                    {
                        Common.CreateSignAlertDialog(CategoryActivity.this,getString(R.string.serv_aval_client));
                    }
                    else {
                        Intent intent=new Intent(CategoryActivity.this,Add_Coupon_Activity.class);
                        startActivityForResult(intent,2);}}
                else {
                    Common.CreateUserNotSignInAlertDialog(CategoryActivity.this);
                }
            }
        });
        llreview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent=new Intent(CategoryActivity.this, ReviewsActivity.class);
                intent.putExtra("datas",data);
                startActivityForResult(intent,2);

            }
        });
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                CreateImageAlertDialog();
            }
        });
        progressBar.getIndeterminateDrawable().setColorFilter(ContextCompat.getColor(this, R.color.colorPrimary), PorterDuff.Mode.SRC_IN);
        if (lang.equals("en")) {
            arrow1.setRotation(180.0f);
            arrow2.setRotation(180.0f);
            arrow3.setRotation(180.0f);

        } else {
            imback.setRotation(180.0f);
        }
        imback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        ll_choose_delivery_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CreateTimeDialog();
            }
        });
        tab = findViewById(R.id.tab);
        tab.setupWithViewPager(pager);
        ll_change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CategoryActivity.this, MapActivity.class);
                startActivityForResult(intent, 1);
            }
        });

        btnOrderNow.setOnClickListener(view -> {
//            Intent intent = new Intent(CategoryActivity.this, CompleteOrderActivity.class);
//            startActivity(intent);
            if(userModel!=null){
                if (userModel.getData().getUser_type().equals(Tags.TYPE_DELEGATE))
                {
                    Common.CreateSignAlertDialog(CategoryActivity.this,getString(R.string.serv_aval_client));
                }
                else {
                    CheckData();}}
            else {
                Common.CreateUserNotSignInAlertDialog(CategoryActivity.this);
            }

        });

    }
    private void CheckData() {

        order_details = edt_order_details.getText().toString().trim();
        if(!TextUtils.isEmpty(order_details)&&selectedLocation!=null&&!selectedLocation.getAddress().isEmpty()&&selected_time!=0)

        {
            edt_order_details.setError(null);
            tv_time.setError(null);
            tv_address.setError(null);
            Common.CloseKeyBoard(this, edt_order_details);
            /*if (TextUtils.isEmpty(delegate_id))
            {
                activity.DisplayFragmentDelegates(placeModel.getLat(),placeModel.getLng(),"reserve_order","","");

            }else
                {
                }*/


            if (uri==null)
            {
                sendOrder();

            }else
            {
                sendOrderWithImage();
            }

        }else {
            if (TextUtils.isEmpty(order_details)) {
                edt_order_details.setError(getString(R.string.field_req));

            } else {
                edt_order_details.setError(null);
            }

            if (selected_time == 0) {
                tv_time.setError(getString(R.string.field_req));

            } else {
                tv_time.setError(null);
            }

            if (selectedLocation == null) {
                tv_address.setError(getString(R.string.field_req));

            }
            else if(selectedLocation.getAddress().isEmpty()){
                tv_address.setError(getString(R.string.field_req));

            }
            else {
                tv_address.setError(null);
            }
        }    }
    private void getdatafromintent() {
        if (getIntent().getSerializableExtra("data") != null) {
            data = (CategoryModel.Data) getIntent().getSerializableExtra("data");
        }
    }

    private void CreateTimeDialog()
    {
        final AlertDialog dialog = new AlertDialog.Builder(this)
                .setCancelable(true)
                .create();

        View view  = LayoutInflater.from(this).inflate(R.layout.dialog_delivery_time,null);
        Button btn_select = view.findViewById(R.id.btn_select);
        Button btn_cancel = view.findViewById(R.id.btn_cancel);

        final NumberPicker numberPicker = view.findViewById(R.id.numberPicker);

        numberPicker.setMinValue(0);
        numberPicker.setMaxValue(timesList.length-1);
        numberPicker.setDisplayedValues(timesList);
        numberPicker.setWrapSelectorWheel(false);
        numberPicker.setValue(1);
        btn_select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String val = timesList[numberPicker.getValue()];
                tv_time.setText(val);
                setTime(numberPicker.getValue());
                dialog.dismiss();
            }
        });




        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.getWindow().getAttributes().windowAnimations = R.style.dialog_congratulation_animation;
        dialog.setView(view);
        dialog.show();
    }

    private void setTime(int value) {
        Calendar calendar = Calendar.getInstance(new Locale(lang));
        switch (value)
        {
            case 0:
                calendar.add(Calendar.HOUR_OF_DAY,1);
                break;
            case 1:
                calendar.add(Calendar.HOUR_OF_DAY,2);

                break;
            case 2:
                calendar.add(Calendar.HOUR_OF_DAY,3);

                break;
            case 3:
                calendar.add(Calendar.DAY_OF_MONTH,1);

                break;
            case 4:
                calendar.add(Calendar.DAY_OF_MONTH,2);

                break;
            case 5:
                calendar.add(Calendar.DAY_OF_MONTH,3);

                break;
        }

        selected_time = calendar.getTimeInMillis()/1000;
    }

    public void getsinglecat() {


        final ProgressDialog dialog = Common.createProgressDialog(this, getString(R.string.wait));
        dialog.show();
        Api.getService(Tags.base_url)
                .getsinglecat(lang, data.getCategory_id() + "", 1, 1)
                .enqueue(new Callback<SingleCategoryModel>() {
                    @Override
                    public void onResponse(Call<SingleCategoryModel> call, Response<SingleCategoryModel> response) {
                        dialog.dismiss();
                        if (response.isSuccessful() && response.body() != null && response.body().getData() != null && response.body().getData().size() > 0) {
                            update(response.body());


                        } else {
                            try {
                                Log.e("codesssss", response.code() + "" + response.errorBody().string());
                            } catch (Exception e) {
                                //  e.printStackTrace();
                            }

                        }
                    }

                    @Override
                    public void onFailure(Call<SingleCategoryModel> call, Throwable t) {
                        Log.e("codesssss", t.getMessage());


                    }
                });

    }

    private void update(SingleCategoryModel body) {
        this.singlecategory=body;
        if (body.getData().get(0).getWord().getContent() != null)
            tv_content.setText(body.getData().get(0).getWord().getContent() + "");
        tv_name.setText(body.getData().get(0).getWord().getTitle());
        Picasso.with(this).load(Tags.IMAGE_URL + body.getData().get(0).getLogo()).into(imageView);
        progressBar.setVisibility(View.GONE);
        if (body.getData().get(0).getMenus() != null && body.getData().get(0).getMenus().size() > 0) {
            NUM_PAGES = body.getData().get(0).getMenus().size();
            sliderCatogryAdapter = new SliderCatogryAdapter(body.getData().get(0).getMenus(), this);
            pager.setAdapter(sliderCatogryAdapter);
            pager.setVisibility(View.VISIBLE);
        } else {
            pager.setVisibility(View.GONE);
        }
        tv_rate.setText(body.getData().get(0).getRate() + "");
        simpleRatingBar.setIndicator(false);
        simpleRatingBar.setRating(body.getData().get(0).getRate());
         //   tv_time.setText(body.getData().get(0).getDays().get(0).getFrom_time() + ":" + body.getData().get(0).getDays().get(0).getTo_time());
        //    tv_status.setText(body.getData().get(0).getDays().get(0).getStatus());

    }

    private void change_slide_image() {
        final Handler handler = new Handler();
        final Runnable Update = new Runnable() {
            public void run() {
                if (current_page == NUM_PAGES) {
                    current_page = 0;
                }
                pager.setCurrentItem(current_page++, true);
            }
        };
        Timer swipeTimer = new Timer();
        swipeTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(Update);
            }
        }, 3000, 3000);
    }
    public void sendOrder()
    {
        //this.delegate_id = delegate_id;

        int coupon_id=0;
        if(userModel.getCoupon_data()!=null){
            coupon_id=userModel.getCoupon_data().getId();
        }
        else {
            coupon_id=-1;
        }
        final ProgressDialog dialog = Common.createProgressDialog(this,getString(R.string.wait));
        dialog.show();
        Api.getService(Tags.base_url)
                .sendOrder(userModel.getData().getUser_id(),selectedLocation.getAddress()+" "+selectedLocation.getAddress(),selectedLocation.getLat(),selectedLocation.getLng(),order_details,singlecategory.getData().get(0).getPlace_id()+"",singlecategory.getData().get(0).getAddress(),"1",singlecategory.getData().get(0).getGoogle_lat(),singlecategory.getData().get(0).getGoogle_long(),selected_time,coupon_id+"",singlecategory.getData().get(0).getCategory_id())
                .enqueue(new Callback<OrderIdDataModel>() {
                    @Override
                    public void onResponse(Call<OrderIdDataModel> call, Response<OrderIdDataModel> response) {
                        dialog.dismiss();
                        if (response.isSuccessful()&&response.body()!=null&&response.body().getData()!=null)
                        {
                            CreateAlertDialog(response.body().getData().getOrder_id());
                        }else
                        {
                            try {
                                Log.e("Error_code",response.code()+""+response.errorBody().string());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            Toast.makeText(CategoryActivity.this, R.string.failed, Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<OrderIdDataModel> call, Throwable t) {
                        try {
                            dialog.dismiss();
                            Toast.makeText(CategoryActivity.this, getString(R.string.something), Toast.LENGTH_SHORT).show();
                            Log.e("Error",t.getMessage());
                        }catch (Exception e){}
                    }
                });

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == RESULT_OK && data != null) {
            if (data.hasExtra("location")) {
                selectedLocation = (SelectedLocation) data.getSerializableExtra("location");
                if (selectedLocation != null) {
                    if(selectedLocation.getAddress()!=null&&!selectedLocation.getAddress().isEmpty()){
                        tv_address.setText(selectedLocation.getAddress());
                        tv_addess.setText(selectedLocation.getAddress().substring(0,selectedLocation.getAddress().length()/2 ));
                    }}
            }
        }
        else if(requestCode==2){
            userModel=preference.getUserData(this);
        }
        if (requestCode == IMG1 && resultCode == Activity.RESULT_OK && data!=null)
        {
            uri = data.getData();
            File file = new File(Common.getImagePath(this,uri));
            try {
                Picasso.with(this).load(file).into(image_details);

            }catch (Exception e)
            {
                Picasso.with(this).load(uri).into(image_details);
            }
            image_details.setVisibility(View.VISIBLE);

        }else if (requestCode == IMG2 && resultCode == Activity.RESULT_OK && data!=null)
        {
            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
            image_details.setImageBitmap(bitmap);
            uri = getUriFromBitmap(bitmap);
            image_details.setVisibility(View.VISIBLE);

        }

    }

    private Uri getUriFromBitmap(Bitmap bitmap)
    {
        String path = "";
        try {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG,100,outputStream);
            path = MediaStore.Images.Media.insertImage(this.getContentResolver(),bitmap,"title",null);
            return Uri.parse(path);

        }catch (SecurityException e)
        {
            Toast.makeText(this,getString(R.string.perm_image_denied), Toast.LENGTH_SHORT).show();

        }catch (Exception e)
        {
            Toast.makeText(this,getString(R.string.perm_image_denied), Toast.LENGTH_SHORT).show();

        }
        return null;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == IMG1)
        {
            if (grantResults.length>0)
            {
                if (grantResults[0]== PackageManager.PERMISSION_GRANTED)
                {
                    select_photo(1);
                }else
                {
                    Toast.makeText(this,getString(R.string.perm_image_denied), Toast.LENGTH_SHORT).show();
                }
            }
        }else if (requestCode == IMG2)
        {
            if (grantResults.length>0)
            {
                if (grantResults[0]==PackageManager.PERMISSION_GRANTED&&grantResults[1]==PackageManager.PERMISSION_GRANTED)
                {
                    select_photo(2);

                }else
                {
                    Toast.makeText(this,getString(R.string.perm_image_denied), Toast.LENGTH_SHORT).show();
                }
            }
        }


    }
    public  void CreateAlertDialog(String order_id)
    {


        final AlertDialog dialog = new AlertDialog.Builder(this)
                .setCancelable(true)
                .create();


        View view = LayoutInflater.from(this).inflate(R.layout.dialog_order_id,null);
        Button btn_follow = view.findViewById(R.id.btn_follow);
        Button btn_cancel = view.findViewById(R.id.btn_cancel);

        TextView tv_msg = view.findViewById(R.id.tv_msg);
        tv_msg.setText(getString(R.string.order_sent_successfully_order_number_is)+" #"+order_id);
        btn_follow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                //  activity.FollowOrder();
                finish();




            }
        });

        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                //   activity.Back();


            }
        });

        dialog.getWindow().getAttributes().windowAnimations= R.style.dialog_congratulation_animation;
        dialog.setCanceledOnTouchOutside(false);
        dialog.setView(view);
        dialog.show();
    }
    private void CreateImageAlertDialog()
    {

        final androidx.appcompat.app.AlertDialog dialog = new androidx.appcompat.app.AlertDialog.Builder(this)
                .setCancelable(true)
                .create();


        View view = LayoutInflater.from(this).inflate(R.layout.dialog_select_image,null);
        Button btn_camera = view.findViewById(R.id.btn_camera);
        Button btn_gallery = view.findViewById(R.id.btn_gallery);
        Button btn_cancel = view.findViewById(R.id.btn_cancel);



        btn_camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                Check_CameraPermission();

            }
        });

        btn_gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                Check_ReadPermission();



            }
        });

        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.getWindow().getAttributes().windowAnimations= R.style.dialog_congratulation_animation;
        dialog.setCanceledOnTouchOutside(false);
        dialog.setView(view);
        dialog.show();
    }
    private void Check_ReadPermission()
    {
        if (ContextCompat.checkSelfPermission(this,read_permission)!= PackageManager.PERMISSION_GRANTED )
        {
            ActivityCompat.requestPermissions(this,new String[]{read_permission},IMG1);
        }else
        {
            select_photo(1);

        }

    }


    private void Check_CameraPermission()
    {
        if (ContextCompat.checkSelfPermission(this,camera_permission)!= PackageManager.PERMISSION_GRANTED&&ContextCompat.checkSelfPermission(this,write_permission)!= PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(this,new String[]{camera_permission,write_permission},IMG2);
        }else
        {
            select_photo(2);

        }

    }
    private void select_photo(int type)
    {
        Intent  intent = new Intent();

        if (type == 1)
        {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT)
            {
                intent.setAction(Intent.ACTION_OPEN_DOCUMENT);
                intent.addFlags(Intent.FLAG_GRANT_PERSISTABLE_URI_PERMISSION);
            }else
            {
                intent.setAction(Intent.ACTION_GET_CONTENT);

            }

            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent.setType("image/*");
            startActivityForResult(intent,IMG1);

        }else if (type ==2)
        {
            try {
                intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent,IMG2);
            }catch (SecurityException e)
            {
                Toast.makeText(this,R.string.perm_image_denied, Toast.LENGTH_SHORT).show();
            }
            catch (Exception e)
            {
                Toast.makeText(this,R.string.perm_image_denied, Toast.LENGTH_SHORT).show();

            }



        }



    }
    public void sendOrderWithImage()
    {
        //this.delegate_id = delegate_id;
        int coupon_id=0;
        if(userModel.getCoupon_data()!=null){
            coupon_id=userModel.getCoupon_data().getId();
        }
        else {
            coupon_id=-1;
        }
        RequestBody user_id_part = Common.getRequestBodyText(userModel.getData().getUser_id());
        RequestBody client_address_part = Common.getRequestBodyText(selectedLocation.getAddress()+" "+selectedLocation.getAddress());
        RequestBody client_lat_part = Common.getRequestBodyText(String.valueOf(selectedLocation.getLat()));
        RequestBody client_lng_part = Common.getRequestBodyText(String.valueOf(selectedLocation.getLng()));
        RequestBody order_details_part = Common.getRequestBodyText(order_details);
        RequestBody place_id_part = Common.getRequestBodyText(singlecategory.getData().get(0).getPlace_id()+"");
        RequestBody place_name_part = Common.getRequestBodyText(singlecategory.getData().get(0).getCategory_id()+"");

        RequestBody place_address_part = Common.getRequestBodyText(selectedLocation.getAddress());
        RequestBody order_type_part = Common.getRequestBodyText("1");
        RequestBody place_lat_part = Common.getRequestBodyText(String.valueOf(selectedLocation.getLat()));
        RequestBody place_lng_part = Common.getRequestBodyText(String.valueOf(selectedLocation.getLng()));
        RequestBody selected_time_part = Common.getRequestBodyText(String.valueOf(selected_time));
        MultipartBody.Part image_part = Common.getMultiPart(this,uri,"order_image");
        RequestBody copun_part = Common.getRequestBodyText(coupon_id+"");

        final ProgressDialog dialog = Common.createProgressDialog(this,getString(R.string.wait));
        dialog.show();
        Api.getService(Tags.base_url)
                .sendOrderWithImage(user_id_part,client_address_part,client_lat_part,client_lng_part,order_details_part,place_id_part,place_name_part,place_address_part,order_type_part,place_lat_part,place_lng_part,selected_time_part,copun_part,image_part)
                .enqueue(new Callback<OrderIdDataModel>() {
                    @Override
                    public void onResponse(Call<OrderIdDataModel> call, Response<OrderIdDataModel> response) {
                        dialog.dismiss();
                        if (response.isSuccessful()&&response.body()!=null&&response.body().getData()!=null)
                        {
                            uri = null;
                            CreateAlertDialog(response.body().getData().getOrder_id());
                        }else
                        {
                            try {
                                Log.e("Error_code",response.code()+""+response.errorBody().string());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            Toast.makeText(CategoryActivity.this, R.string.failed, Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<OrderIdDataModel> call, Throwable t) {
                        try {
                            dialog.dismiss();
                            Toast.makeText(CategoryActivity.this, getString(R.string.something), Toast.LENGTH_SHORT).show();
                            Log.e("Error",t.getMessage());
                        }catch (Exception e){}
                    }
                });

    }
}
