package in.ac.iiitd.iiitd_guesthouseapp.Adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import in.ac.iiitd.iiitd_guesthouseapp.Activities.BookingActivity;
import in.ac.iiitd.iiitd_guesthouseapp.R;

public class HomeActivityGridAdapter extends RecyclerView.Adapter<HomeActivityGridAdapter.HomeActivityGridHolder>
{
    private Context context;
    private int resource;
    private String values[];
    private int images[];

    String text= null;

    public HomeActivityGridAdapter(Context context, int resource, String[] values, int[] images)
    {
        this.context = context;
        this.resource = resource;
        this.values = values;
        this.images = images;
    }

    @NonNull
    @Override
    public HomeActivityGridHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(resource, viewGroup, false);
        HomeActivityGridHolder homeActivityGridHolder = new HomeActivityGridHolder(view);
        return homeActivityGridHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull HomeActivityGridHolder homeActivityGridHolder, final int position)
    {
        try{
            Picasso.get()
                    .load(images[position])
                    .into(homeActivityGridHolder.imgViewIcon);
        }
        catch (Exception ex)
        {
            Toast.makeText(context, "Error: Failed to Load Images", Toast.LENGTH_SHORT).show();
        }

        homeActivityGridHolder.tvIconName.setText(values[position]);

        homeActivityGridHolder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent intent;

                switch (position)
                {
                    //Book Room
                    case 0:
                        openUserTypeDialog();
                        break;

                    case 1:
                        break;

                    case 2:
                        break;

                    case 3:
                        break;

                }

            }
        });
    }

    @Override
    public int getItemCount()
    {
        return values.length;
    }


    public class HomeActivityGridHolder extends RecyclerView.ViewHolder {
        public ImageView imgViewIcon;
        public TextView tvIconName;
        public LinearLayout linearLayout;

        public HomeActivityGridHolder(View itemView) {
            super(itemView);

            imgViewIcon = itemView.findViewById(R.id.imageViewIcon);
            tvIconName = itemView.findViewById(R.id.textViewIconName);

            linearLayout = itemView.findViewById(R.id.homeactivity_linearlayoutRecyclerView);

        }
    }

    public void openUserTypeDialog()
    {
        final Intent intentUserType = new Intent(context, BookingActivity.class);
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("Faculty");
        arrayList.add("Student");
        arrayList.add("Delegate");
        arrayList.add("Government Visitor");
        arrayList.add("Office Staff");

        final CharSequence[] items = arrayList.toArray(new CharSequence[arrayList.size()]);

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Choose Your Role");
        builder.setItems(items,
                new DialogInterface.OnClickListener(){
                    public void onClick(DialogInterface dialog, int position) {
                        switch (position)
                        {
                            case 0:
                                text = items[position].toString();
                                intentUserType.putExtra("keyusertype", text);
                                context.startActivity(intentUserType);
                                break;
                            case 1:
                                text = items[position].toString();
                                intentUserType.putExtra("keyusertype", text);
                                break;
                            case 2:
                                text = items[position].toString();
                                intentUserType.putExtra("keyusertype", text);
                                break;
                            case 3:
                                text = items[position].toString();
                                intentUserType.putExtra("keyusertype", text);
                                break;
                            case 4:
                                text = items[position].toString();
                                intentUserType.putExtra("keyusertype", text);

                        }
                    }
                });
        builder.create().show();
    }

}
