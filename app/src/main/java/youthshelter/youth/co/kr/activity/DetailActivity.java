package youthshelter.youth.co.kr.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import java.util.ArrayList;

import youthshelter.youth.co.kr.R;
import youthshelter.youth.co.kr.adapter.ImageViewPagerAdapter;
import youthshelter.youth.co.kr.components.CircleAnimIndicator;
import youthshelter.youth.co.kr.data.YouthCenter;

public class DetailActivity extends AppCompatActivity {
    private ViewPager viewPager;
    private YouthCenter center;

    private ArrayList<String> numberList;

    private CircleAnimIndicator circleAnimIndicator;

    private TextView shelter_info_detail_TextView;
    private TextView shelter_location_detail_TextView;
    private TextView shelter_name_detail_TextView;
    private TextView shelter_tel_detail_TextView;
    private TextView shelter_website_detail_TextView;
    private TextView shelter_like_detail_TextView;

    private LinearLayout shelter_tel_detail_LinearLayout;
    private LinearLayout shelter_website_detail_LinearLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        center = (YouthCenter) getIntent().getSerializableExtra("center");
        Log.i("tttttt", "~~" + center.toString());
        shelter_info_detail_TextView = (TextView) findViewById(R.id.shelter_info_detail_TextView);
        shelter_location_detail_TextView = (TextView) findViewById(R.id.shelter_location_detail_TextView);
        shelter_name_detail_TextView = (TextView) findViewById(R.id.shelter_name_detail_TextView);
        shelter_tel_detail_TextView = (TextView) findViewById(R.id.shelter_tel_detail_TextView);
        shelter_website_detail_TextView = (TextView) findViewById(R.id.shelter_website_detail_TextView);
        shelter_like_detail_TextView = (TextView) findViewById(R.id.shelter_like_detail_TextView);

        shelter_tel_detail_LinearLayout = (LinearLayout) findViewById(R.id.shelter_tel_detail_LinearLayout);
        shelter_website_detail_LinearLayout = (LinearLayout) findViewById(R.id.shelter_website_detail_LinearLayout);

        shelter_name_detail_TextView.setText(center.getName());
        shelter_location_detail_TextView.setText(center.getAddress());
        shelter_tel_detail_TextView.setText(center.getPhone());
        shelter_like_detail_TextView.setText(Integer.toString(center.getLike()));
        String info = "※소개\n" + center.getIntroduction() + "\n\n※정보\n" + center.getBonus() + "\n\n※평일\n" + center.getWeekday() + "\n\n※토요일\n" + center.getSaturday() + "\n\n※일요일\n" + center.getSunday();
        shelter_info_detail_TextView.setText(info);
        shelter_tel_detail_LinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:" + shelter_tel_detail_TextView.getText()));
                startActivity(intent);
            }
        });

        shelter_website_detail_LinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (center == null || center.getHomepage() == null) {
                    Toast.makeText(getApplicationContext(), "웹사이트를 지원하지 않는 장소 입니다.", Toast.LENGTH_SHORT).show();
                } else
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(center.getHomepage())));
            }
        });

        viewPager = (ViewPager) findViewById(R.id.imageViewPager);
        viewPager.setOffscreenPageLimit(10);
        circleAnimIndicator = (CircleAnimIndicator) findViewById(R.id.circleAnimIndicator);

        initIndicaotor(center.getCount());

        ImageViewPagerAdapter viewPagerAdapter = new ImageViewPagerAdapter(getSupportFragmentManager(), this, center.getImage(), center.getCount(), center.getFormat());
        viewPager.setAdapter(viewPagerAdapter);
        viewPager.addOnPageChangeListener(mOnPageChangeListener);
    }

    private void initIndicaotor(int size) {

        //원사이의 간격
        circleAnimIndicator.setItemMargin(15);
        //애니메이션 속도
        circleAnimIndicator.setAnimDuration(300);
        //indecator 생성
        circleAnimIndicator.createDotPanel(size, R.drawable.indicator_non, R.drawable.indicator_on);
    }

    /**
     * ViewPager 전환시 호출되는 메서드
     */
    private ViewPager.OnPageChangeListener mOnPageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        }

        @Override
        public void onPageSelected(int position) {
            circleAnimIndicator.selectDot(position);
        }

        @Override
        public void onPageScrollStateChanged(int state) {
        }
    };

}
