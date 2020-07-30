package com.skyward.controlcpuload;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

/**
 * @author uidp5296
 */
public class MainActivity extends AppCompatActivity {
    private EditText mEtNumberOfCore;
    private EditText mEtThreadOfPerCore;
    private EditText mEtCpuUsage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mEtNumberOfCore = findViewById(R.id.et_number_of_core);
        mEtThreadOfPerCore = findViewById(R.id.et_number_of_thread);
        mEtCpuUsage = findViewById(R.id.et_cpu_usage);
    }

    private boolean isWorking = false;

    private static final String TAG = "MainActivity";

    public void start(View view) {
        if (isWorking) {
            Toast.makeText(MainActivity.this, "请先点击停止，停止之前的工作，再点击开始！", Toast.LENGTH_LONG).show();
            return;
        }
        int numberOfCore = Integer.parseInt(mEtNumberOfCore.getText().toString().trim());
        int threadOfPerCore = Integer.parseInt(mEtThreadOfPerCore.getText().toString().trim());
        int cpuUsage = Integer.parseInt(mEtCpuUsage.getText().toString().trim());
        if (numberOfCore < 0) {
            Toast.makeText(MainActivity.this, "CPU核心数不能小于零", Toast.LENGTH_SHORT).show();
            return;
        }
        if (threadOfPerCore < 0) {
            Toast.makeText(MainActivity.this, "单核线程数不能小于零", Toast.LENGTH_SHORT).show();
            return;
        }
        if (cpuUsage < 0 || cpuUsage > 100) {
            Toast.makeText(MainActivity.this, "请输入正确的CPU使用率", Toast.LENGTH_SHORT).show();
            return;
        }

        ControlCpuLoadUtils.start(numberOfCore, threadOfPerCore, cpuUsage);
        isWorking = true;
    }

    public void stop(View view) {
        if (isWorking) {
            ControlCpuLoadUtils.stop();
            isWorking = false;
        }
    }
}
