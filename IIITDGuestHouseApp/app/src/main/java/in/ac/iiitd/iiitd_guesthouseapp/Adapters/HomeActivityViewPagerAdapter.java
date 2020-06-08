package in.ac.iiitd.iiitd_guesthouseapp.Adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.squareup.picasso.Picasso;

import in.ac.iiitd.iiitd_guesthouseapp.R;

public class HomeActivityViewPagerAdapter extends PagerAdapter
{
    private LayoutInflater inflater;
    private Activity activity;
    private int images[];

    public HomeActivityViewPagerAdapter(Activity activity, int[] images)
    {
        this.activity = activity;
        this.images = images;
    }

    @Override
    public int getCount() {
        return images.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object o) {
        return view==o;
    }

    public Object instantiateItem(ViewGroup container, int position)
    {
        inflater = (LayoutInflater)activity.getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView = inflater.inflate(R.layout.homeactivity_viewpager_item,container,false);

        ImageView homeactivity_imageView_main_image = itemView.findViewById(R.id.homeactivity_imageView_main_image);

        try{
            Picasso.get()
                    .load(images[position])
                    .resize(homeactivity_imageView_main_image.getWidth(), 1000)
                    .centerCrop()
                    .into(homeactivity_imageView_main_image);
        }
        catch (Exception ex)
        {
            Toast.makeText(activity, " " + ex, Toast.LENGTH_SHORT).show();
        }

        container.addView(itemView);
        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        ((ViewPager) container).removeView((View)object);
    }
}
