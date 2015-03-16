package youngam.bsuir;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import youngam.bsuir.man.WorkoutManActivity;
import youngam.bsuir.woman.WorkoutWomanActivity;


public class MainActivity extends Activity implements View.OnClickListener {
    private Button man;
    private Button woman;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        man = (Button) findViewById(R.id.manButton);
        man.setOnClickListener(this);
        woman = (Button) findViewById(R.id.womanButton);
        woman.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.manButton:
                Intent manIntent = new Intent(this, WorkoutManActivity.class);
                startActivity(manIntent);
                break;
            case R.id.womanButton:
                Intent womanIntent = new Intent(this, WorkoutWomanActivity.class);
                startActivity(womanIntent);
                break;
        }

    }
}
