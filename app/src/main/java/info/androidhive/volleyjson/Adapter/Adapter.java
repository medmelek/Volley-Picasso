package info.androidhive.volleyjson.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import java.util.List;

import info.androidhive.volleyjson.R;
import info.androidhive.volleyjson.app.modeltmp;

/**
 * Created by Med Melek on 27/11/2016.
 */

public class Adapter extends  RecyclerView.Adapter<Adapter.TmpViewHolder>{

private Context context;
private List<modeltmp>TmpList;

public Adapter (Context context, List<modeltmp> tmpList) {
        this.context = context;
        this.TmpList =tmpList;
        }

        @Override
        public Adapter.TmpViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.model, parent, false);

                return new TmpViewHolder(itemView);
        }

        public class TmpViewHolder extends RecyclerView.ViewHolder {
                TextView valeurtmp;
                TextView datetmp;
                ImageView imagetmp;
                public TmpViewHolder(View itemView) {
                        super(itemView);
                        valeurtmp = (TextView) itemView.findViewById(R.id.valeurtmp);
                        datetmp = (TextView) itemView.findViewById(R.id.datetmp);
                        imagetmp = (ImageView) itemView.findViewById(R.id.imagetmp);
                }
        }

        @Override
        public void onBindViewHolder(TmpViewHolder holder, int position) {

                modeltmp tmp = TmpList.get(position);

                holder.valeurtmp.setText(tmp.getValeurtmp());

                holder.datetmp.setText(tmp.getDatetmp());

                Picasso.with(context).load(tmp.getImagetmp()).error(R.drawable.notwifi).into(holder.imagetmp);

        }

        @Override
        public int getItemCount() {
                return TmpList.size();
        }



}
