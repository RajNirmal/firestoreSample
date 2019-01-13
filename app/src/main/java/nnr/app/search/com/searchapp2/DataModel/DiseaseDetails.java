package nnr.app.search.com.searchapp2.DataModel;

import java.util.ArrayList;
import java.util.List;

import nnr.app.search.com.searchapp2.AppConstants;
import nnr.app.search.com.searchapp2.AppUtil;

public class DiseaseDetails {
    String disease;
    ArrayList<String> symptom;
    ArrayList<String> remedy;

    public DiseaseDetails(String disease, ArrayList<String> symptom, ArrayList<String> remedy) {
        this.disease = disease;
        this.symptom = symptom;
        this.remedy = remedy;
    }

    public DiseaseDetails(){

    }
    public String getDisease() {
        return disease;
    }

    public void setDisease(String disease) {
        this.disease = disease;
    }

    public String getSymptom() {
        return AppUtil.getAppUtilInstance().getStringFromList(symptom);
    }

    public void setSymptom(ArrayList<String> symptom) {
        this.symptom = symptom;
    }

    public String getRemedy() {
        return AppUtil.getAppUtilInstance().getStringFromList(remedy);
    }

    public void setRemedy(ArrayList<String> remedy) {
        this.remedy = remedy;
    }

    public ArrayList<String> getSymptomArray(){
        return symptom;
    }

    public ArrayList<String> getRemedyArray(){
        return remedy;
    }
}
