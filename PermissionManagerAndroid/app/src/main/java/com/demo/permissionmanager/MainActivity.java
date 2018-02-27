//  The MIT License (MIT)

//  Copyright (c) 2018 Intuz Pvt Ltd.

//  Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files
//  (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify,
//  merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is
//  furnished to do so, subject to the following conditions:

//  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
//  MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE
//  LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN
//  CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.

package com.demo.permissionmanager;

import android.Manifest;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.permissionmanagerlib.Permission;
import com.permissionmanagerlib.PermissionManagerInstance;
import com.permissionmanagerlib.PermissionManagerListener;


public class MainActivity extends AppCompatActivity {
    private TextView txtDesc;
    private Button btnClick;
    private PermissionManagerInstance mPermissionManagerInstance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txtDesc = (TextView) findViewById(R.id.txtDesc);
        btnClick = (Button) findViewById(R.id.btnClick);

        //initialize permission manager instance
        mPermissionManagerInstance = new PermissionManagerInstance(this);

        // check for permission whether it is granted or not
        if (mPermissionManagerInstance.checkPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) &&
                mPermissionManagerInstance.checkPermission(Manifest.permission.ACCESS_FINE_LOCATION)) {
            btnClick.setVisibility(View.GONE);
            txtDesc.setText("All permissions are granted.");
        } else if (!mPermissionManagerInstance.checkPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) &&
                !mPermissionManagerInstance.checkPermission(Manifest.permission.ACCESS_FINE_LOCATION)) {
            btnClick.setVisibility(View.VISIBLE);
            txtDesc.setText("Permissions are not granted.");
        } else if (mPermissionManagerInstance.checkPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) &&
                !mPermissionManagerInstance.checkPermission(Manifest.permission.ACCESS_FINE_LOCATION)) {
            btnClick.setVisibility(View.VISIBLE);
            txtDesc.setText("Storage permission is granted \nLocation permission is not granted");
        } else if (!mPermissionManagerInstance.checkPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) &&
                mPermissionManagerInstance.checkPermission(Manifest.permission.ACCESS_FINE_LOCATION)) {
            btnClick.setVisibility(View.VISIBLE);
            txtDesc.setText("Location permission is granted \nStorage permission is not granted");
        }


        btnClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //array of permissions to be requested
                String[] permissions = new String[]{
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.ACCESS_FINE_LOCATION
                };

                mPermissionManagerInstance.requestForPermissions(
                        permissions,
                        new PermissionManagerListener() {
                            @Override
                            public void permissionCallback(String[] permissions, Permission[] grantResults,
                                                           boolean allGranted) {
                                //check allGranted value to check weather all permission is granted
                                //check individual permission result from grantResults array
                                if (allGranted) {
                                    btnClick.setVisibility(View.GONE);
                                    txtDesc.setText("All permissions are granted.");
                                } else if (grantResults[0] != Permission.GRANTED &&
                                        grantResults[1] != Permission.GRANTED) {
                                    btnClick.setVisibility(View.VISIBLE);
                                    txtDesc.setText("Permissions are not granted.");

                                } else if (grantResults[0] == Permission.GRANTED &&
                                        grantResults[1] != Permission.GRANTED) {
                                    btnClick.setVisibility(View.VISIBLE);
                                    txtDesc.setText("Storage permission is granted " +
                                            "\nLocation permission is not granted");

                                } else if (grantResults[0] != Permission.GRANTED) {
                                    btnClick.setVisibility(View.VISIBLE);
                                    txtDesc.setText("Location permission is granted " +
                                            "\nStorage permission is not granted");
                                }
                            }
                        });
            }
        });
    }
}
