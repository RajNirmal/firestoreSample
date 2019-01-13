package nnr.app.search.com.searchapp2.Adapter;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import nnr.app.search.com.searchapp2.AppConstants;
import nnr.app.search.com.searchapp2.DataModel.DiseaseDetails;
import nnr.app.search.com.searchapp2.R;

public class SymptomAdapter extends RecyclerView.Adapter<SymptomAdapter.myViewHolder> {

    ArrayList<DiseaseDetails> dataSet ;
    public class myViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView diseaseName, diseaseSymptom, diseaseRemedy;
        CardView diseaseCard;
        public myViewHolder(View singleCard){
            super(singleCard);
            diseaseName = singleCard.findViewById(R.id.disease_name);
            diseaseSymptom = singleCard.findViewById(R.id.disease_symptom);
            diseaseRemedy = singleCard.findViewById(R.id.disease_remedy);
            diseaseCard = singleCard.findViewById(R.id.disease_card_view);
            diseaseCard.setOnClickListener(this);
            diseaseName.setOnClickListener(this);
            diseaseSymptom.setOnClickListener(this);
            diseaseRemedy.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            Log.i(AppConstants.APP_LOG_TAG,"Some kind of view is clicked");
        }


    }

    public SymptomAdapter(ArrayList<DiseaseDetails> diseaseDetails){
        dataSet = diseaseDetails;
    }
    @Override
    public void onBindViewHolder(myViewHolder holder, int position) {
        TextView diseaseName,diseaseSymptom, diseaseRemedy;
        diseaseName = holder.diseaseName;
        diseaseSymptom = holder.diseaseSymptom;
        diseaseRemedy = holder.diseaseRemedy;
        diseaseName.setText(dataSet.get(position).getDisease());
        diseaseSymptom.setText(dataSet.get(position).getSymptom());
        diseaseRemedy.setText(dataSet.get(position).getRemedy());
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }
    public void updateList(ArrayList<DiseaseDetails> newDataset){
        dataSet = newDataset;
        notifyDataSetChanged();
    }

    public ArrayList<DiseaseDetails> getDataSet(){
        return dataSet;
    }
    @Override
    public myViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View singleCard = LayoutInflater.from(parent.getContext()).inflate(R.layout.disease_single_item,parent,false);
        myViewHolder recyclerViewHolder = new myViewHolder(singleCard);
        return recyclerViewHolder;
    }
}

