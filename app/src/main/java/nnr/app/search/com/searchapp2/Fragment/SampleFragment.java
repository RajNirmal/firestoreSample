package nnr.app.search.com.searchapp2.Fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.MultiAutoCompleteTextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import nnr.app.search.com.searchapp2.Adapter.SymptomAdapter;
import nnr.app.search.com.searchapp2.AppConstants;
import nnr.app.search.com.searchapp2.DataModel.DiseaseDetails;
import nnr.app.search.com.searchapp2.R;

public class SampleFragment extends Fragment {

    MultiAutoCompleteTextView autoCompleteTextView;
    Button searchButton;
    RecyclerView diseaseView;
    SymptomAdapter adapter;
    FirebaseFirestore db;
    ArrayList<DiseaseDetails> originalDataSet;
    public SampleFragment(){

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        db = FirebaseFirestore.getInstance();
        getAllDataFromFirebase(db);
    }

    @Override
    public void onStart() {
        super.onStart();
        searchButton.setOnClickListener(multiSearchRequestListener);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_search,container,false);
        autoCompleteTextView = rootView.findViewById(R.id.symptom_search_bar);
        searchButton = rootView.findViewById(R.id.symptom_search_button);
        diseaseView = rootView.findViewById(R.id.disease_recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this.getActivity());
        diseaseView.setLayoutManager(layoutManager);
        diseaseView.setItemAnimator(new DefaultItemAnimator());
        diseaseView.setHasFixedSize(false);
        return rootView;
    }

    private void getAllDataFromFirebase(FirebaseFirestore db){
        Query symptomQuery = db.collection(AppConstants.FIRESTORE_COLLECTION_NAME_2);
        final ArrayList<DiseaseDetails> dataSet = new ArrayList<>();
        symptomQuery.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    Toast.makeText(getActivity().getApplicationContext(),"Task finished successfully",Toast.LENGTH_SHORT).show();
                    Log.i(AppConstants.APP_LOG_TAG,"Successfully executed the query = "+task.getResult().getQuery().toString());
                    ArrayList symptomList = new ArrayList<String>();
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        Log.i(AppConstants.APP_LOG_TAG, document.getId() + " => " + document.getData());
                        ArrayList<String> symptomData = (ArrayList<String>)document.get(AppConstants.DATA_SYMPTOMPS);
                        String diseaseName = (String)document.get(AppConstants.DATA_DISEASE);
                        ArrayList<String> remedyData = (ArrayList<String>)document.get(AppConstants.DATA_REMEDY);
                        dataSet.add(new DiseaseDetails(diseaseName, symptomData,remedyData));
                        if (!symptomData.isEmpty()) {
                            for (String item : symptomData) {
                                Log.i(AppConstants.APP_LOG_TAG, item);
                                symptomList.add(item);
                            }
                        }
                    }
                    adapter = new SymptomAdapter(dataSet);
                    originalDataSet = dataSet;
                    diseaseView.setAdapter(adapter);
                    setupAutoCompleteTextView(symptomList);
                }else{
                    Toast.makeText(getActivity().getApplicationContext(),"Task did not finish fully error occured",Toast.LENGTH_SHORT).show();
                    Log.i(AppConstants.APP_LOG_TAG,"Error while getting data from firestore "+task.getException());
                }
            }
        });
    }

    private void setupAutoCompleteTextView(ArrayList<String> autoCompleteSuggestions){
        Set<String> autoCompleteSet = new HashSet<>(autoCompleteSuggestions);
        autoCompleteSuggestions.clear();
        autoCompleteSuggestions.addAll(autoCompleteSet);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_dropdown_item_1line, autoCompleteSuggestions);
        autoCompleteTextView.setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer());
        autoCompleteTextView.setAdapter(adapter);

    }


    View.OnClickListener multiSearchRequestListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            String searchText = autoCompleteTextView.getText().toString();
            Log.i(AppConstants.APP_LOG_TAG,"The entered text is "+searchText);
            String[] searchItems = searchText.split(", ");
            for(String i : searchItems) {
                Log.i(AppConstants.APP_LOG_TAG, i);
            }
            int len = (searchItems.length > 3)?3:searchItems.length;
            searchInternally(searchItems);
        }
    };

    private void searchInternally(String[] searchQuery){
        ArrayList<DiseaseDetails> finalData = new ArrayList<>();
        for(DiseaseDetails details : originalDataSet){
            ArrayList<String> symptomData = details.getSymptomArray();
            boolean flag = true;
            for (String i : searchQuery){
                if(!symptomData.contains(i)){
                    flag = false;
                }
            }
            if(flag){
                finalData.add(details);
            }
        }
        adapter.updateList(finalData);
    }
}
