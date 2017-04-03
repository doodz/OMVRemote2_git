package Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.dev.doods.omvremote2.R;

import java.util.List;

import Models.Datum;

/**
 * Created by thiba on 30/08/2016.
 */
public class ServicesAdapter extends ArrayAdapter<Datum> {

    public ServicesAdapter(Context context, List<Datum> Datums)
    {
        super(context,0,Datums);

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.row_services,parent, false);
        }

        ServicesViewHolder viewHolder = (ServicesViewHolder) convertView.getTag();
        if(viewHolder == null){
            viewHolder = new ServicesViewHolder();
            viewHolder.title = (TextView) convertView.findViewById(R.id.Datum_Title);
            viewHolder.name = (TextView) convertView.findViewById(R.id.Datum_Name);
            viewHolder.enable = (ImageView) convertView.findViewById(R.id.Datum_Enabled);
            viewHolder.running = (ImageView) convertView.findViewById(R.id.Datum_Running);
            convertView.setTag(viewHolder);
        }

        //getItem(position) va récupérer l'item [position] de la List<Tweet> tweets
        Datum data = getItem(position);

        //il ne reste plus qu'à remplir notre vue
        viewHolder.title.setText(data.getTitle());

        viewHolder.name.setText(String.format(" (%s) ",data.getName()));
        int resource = data.getEnabled()? R.drawable.ic_round_green_24dp :R.drawable.ic_round_red_24dp;
        viewHolder.enable.setImageResource(resource);
        resource = data.getRunning()? R.drawable.ic_round_green_24dp :R.drawable.ic_round_red_24dp;
        viewHolder.running.setImageResource(resource);
        return convertView;
    }

    private class ServicesViewHolder{
        public TextView title;
        public TextView name;
        public ImageView enable;
        public ImageView running;

    }
}
