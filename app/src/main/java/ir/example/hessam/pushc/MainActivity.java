package ir.example.hessam.pushc;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.hessam.pushc.R;

import ir.mci.push.MrPush;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MrPush.Init(this,"ade5787ce999a5aa1d17c9ce3b85e524");




    }
}
