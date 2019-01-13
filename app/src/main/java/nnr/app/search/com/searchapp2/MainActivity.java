package nnr.app.search.com.searchapp2;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.MultiAutoCompleteTextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MainActivity extends AppCompatActivity {

    Button clickerButton;
    MultiAutoCompleteTextView autoCompleteTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        clickerButton = findViewById(R.id.click_button);
        autoCompleteTextView = findViewById(R.id.multiAutoCompleteTextView);
        FirebaseApp.initializeApp(this);
        final FirebaseFirestore db = FirebaseFirestore.getInstance();
        clickerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),"Showing toast",Toast.LENGTH_SHORT).show();
//                getDataFromFirebase(db);
                getQueriedDataFromFirebase(db);
            }
        });
        Log.i(AppConstants.APP_LOG_TAG,"Finished oncreate");
        getQueriedDataFromFirebase(db);
    }

    private void getDataFromFirebase(FirebaseFirestore db){
        db.collection(AppConstants.FIRESTORE_COLLECTION_NAME).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    Toast.makeText(getApplicationContext(),"Task finished successfully",Toast.LENGTH_SHORT).show();
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        Log.i(AppConstants.APP_LOG_TAG, document.getId() + " => " + document.getData());
                    }

                }else{
                    Toast.makeText(getApplicationContext(),"Task finished successfully",Toast.LENGTH_SHORT).show();
                    Log.i(AppConstants.APP_LOG_TAG,"Error while getting data from firestore "+task.getException());
                }
            }
        });

    }

    private void getQueriedDataFromFirebase(FirebaseFirestore db){
        Query symptomQuery = db.collection(AppConstants.FIRESTORE_COLLECTION_NAME_2);
        //        Log.i(AppConstants.APP_LOG_TAG," The query running is = "+symptomQuery.)
        symptomQuery.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    Toast.makeText(getApplicationContext(),"Task finished successfully",Toast.LENGTH_SHORT).show();
                    Log.i(AppConstants.APP_LOG_TAG,"Successfully executed the query = "+task.getResult().getQuery().toString());
                    ArrayList symptomList = new ArrayList<String>();
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        Log.i(AppConstants.APP_LOG_TAG, document.getId() + " => " + document.getData());
                        List<String> symptom_data = (List<String>)document.get(AppConstants.DATA_SYMPTOMPS);
                        for(String item : symptom_data) {
                            Log.i(AppConstants.APP_LOG_TAG, item);
                            symptomList.add(item);
                        }
                    }
                    setupAutoCompleteTextView(symptomList);
                }else{
                    Toast.makeText(getApplicationContext(),"Task did not finish fully error occured",Toast.LENGTH_SHORT).show();
                    Log.i(AppConstants.APP_LOG_TAG,"Error while getting data from firestore "+task.getException());
                }
            }
        });
    }

    private void setupAutoCompleteTextView(ArrayList<String> autoCompleteSuggestions){
        Set<String> autoCompleteSet = new HashSet<>(autoCompleteSuggestions);
        autoCompleteSuggestions.clear();
        autoCompleteSuggestions.addAll(autoCompleteSet);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(MainActivity.this,android.R.layout.simple_dropdown_item_1line,autoCompleteSuggestions);
        autoCompleteTextView.setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer());
        autoCompleteTextView.setAdapter(adapter);

    }
}
