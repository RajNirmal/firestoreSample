package nnr.app.search.com.searchapp2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

public class HomeActivity extends Activity {
    TextView nextButton;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(AppUtil.getAppUtilInstance().isUserAcceptedTerms(getApplicationContext())){
            startNextActivity();
        }
        setContentView(R.layout.activity_home);
        initView();
    }

    private void initView(){
        nextButton = findViewById(R.id.next_button);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppUtil.getAppUtilInstance().setUserAcceptedTerms(getApplicationContext());
                startNextActivity();
            }
        });
    }

    private void startNextActivity(){
        Intent searchIntent = new Intent(this, FragmentHolder.class);
        getApplicationContext().startActivity(searchIntent);
        finish();
    }
}
