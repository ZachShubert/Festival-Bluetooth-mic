package com.example.myapplication;

import static com.example.myapplication.R.layout.activity_blue_tooth;


import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;


import android.Manifest;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Set;



public class BlueToothActivity extends AppCompatActivity {

    private static final String TAG = "MyBluetooth";

    BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

    ArrayList<PairedDevices_Item> PairedDeviceList = new ArrayList<PairedDevices_Item>();
    ArrayList<BT_discovered_item> FoundDeviceList = new ArrayList<BT_discovered_item>();

    @RequiresApi(api = Build.VERSION_CODES.S)
    public void checkPermission() {
        if (ActivityCompat.checkSelfPermission(BlueToothActivity.this, Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                    BlueToothActivity.this,
                    new String[]{Manifest.permission.BLUETOOTH_CONNECT},
                    1
            );
        }
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_SCAN) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                    BlueToothActivity.this,
                    new String[]{Manifest.permission.BLUETOOTH_SCAN},
                    1
            );
        }
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_ADMIN) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                    BlueToothActivity.this,
                    new String[]{Manifest.permission.BLUETOOTH_ADMIN},
                    1
            );
        }
    }


    @RequiresApi(api = Build.VERSION_CODES.S)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(activity_blue_tooth);

        if (bluetoothAdapter == null) {
            AlertDialog.Builder alert = new AlertDialog.Builder(this);
            alert.setTitle("Bluetooth Error");
            alert.setMessage("This Device does not support bluetooth");
            alert.create().show();
        }
        if (!bluetoothAdapter.isEnabled()) {
            AlertDialog.Builder Popup = new AlertDialog.Builder(this);
            Popup.setTitle("Bluetooth");
            Popup.setMessage("Bluetooth is turned off.\nAllow App to turn on Bluetooth?");
            Popup.setPositiveButton("Allow", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    checkPermission();
                    bluetoothAdapter.enable();
                }
            });
            Popup.setNegativeButton("Deny", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    finish();
                }
            });
            Popup.create().show();
        }
        //Gets a list of the paired devices and send them to update the listview
        Get_Paired_Device_List();
        UpdateBTPairedList();

        checkPermission();
        IntentFilter filter0 = new IntentFilter(BluetoothAdapter.ACTION_DISCOVERY_STARTED);//(BluetoothDevice.ACTION_FOUND);
        registerReceiver(receiver0, filter0);
        IntentFilter filter1 = new IntentFilter();//BluetoothDevice.ACTION_FOUND);//(BluetoothDevice.ACTION_FOUND);
        filter1.addAction(BluetoothDevice.ACTION_FOUND);
        registerReceiver(receiver1, filter1);
        FoundDeviceList.add(new BT_discovered_item("registerReceiver(receiver, filter);", "1"));
        UpdateBTDiscoveredList();
    }


    //Takes a list of paired BT devices sorts them by name and prints them to listview
    public void UpdateBTPairedList() {
        ArrayList<String> list = new ArrayList<String>();
        for (int i = 0; i < PairedDeviceList.size(); i++) {
            list.add(PairedDeviceList.get(i).getName());
        }
        Collections.sort(list, String.CASE_INSENSITIVE_ORDER);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(BlueToothActivity.this,
                android.R.layout.simple_list_item_1,
                list);

        ListView listview = findViewById(R.id.Paired_BTDevicesList);
        listview.setAdapter(arrayAdapter);
    }

    public void UpdateBTDiscoveredList() {
        ArrayList<String> list = new ArrayList<String>();
        for (int i = 0; i < FoundDeviceList.size(); i++) {
            list.add(FoundDeviceList.get(i).getName());
        }
        Collections.sort(list, String.CASE_INSENSITIVE_ORDER);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(BlueToothActivity.this,
                android.R.layout.simple_list_item_1,
                list);

        ListView listview = findViewById(R.id.Discovered_BTDevicesList);
        listview.setAdapter(arrayAdapter);
    }

    //first checks for bluetooth permissions
    //Next get all paired devices, and creates empty string array list
    //If there are any paired devices places them within string array list
    //returns string array list
    @RequiresApi(api = Build.VERSION_CODES.S)
    private void Get_Paired_Device_List() {
        checkPermission();
        Set<BluetoothDevice> pairedDevices = bluetoothAdapter.getBondedDevices();
        if (pairedDevices.size() > 0) {
            // There are paired devices. Get the name and address of each paired device.
            for (BluetoothDevice device : pairedDevices) {
                PairedDeviceList.add(new PairedDevices_Item(device.getName(), device.getAddress()));
            }
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.S)
    public void scanOnclick(View view) {
        //checkPermission();
        //check for permissions and see if discovery is currently working, if so cancel it
//        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_SCAN) != PackageManager.PERMISSION_GRANTED) {
//            ActivityCompat.requestPermissions(
//                    BlueToothActivity.this,
//                    new String[]{Manifest.permission.BLUETOOTH_SCAN},
//                    1
//            );
//            return;
//        }
        if (ActivityCompat.checkSelfPermission(BlueToothActivity.this, Manifest.permission.BLUETOOTH_SCAN) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                    BlueToothActivity.this,
                    new String[]{Manifest.permission.BLUETOOTH_SCAN},
                    1
            );
            //return;
        }
        if (bluetoothAdapter.isDiscovering()) {
            bluetoothAdapter.cancelDiscovery();
            Log.d(TAG, "Discovery Canceled");
            UpdateBTDiscoveredList();
        }

        //start searching for discoverable BT devices
        if(bluetoothAdapter.startDiscovery()){
            Log.d(TAG, "Discovery Started");
        }
    }

    // When a device is found from discovery this function is called
    private final BroadcastReceiver receiver0 = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            Log.d(TAG, "onReceive: Broadcast ACTION_DISCOVERY_STARTED received");
        }
    };

    // When a device is found from discovery this function is called
    private final BroadcastReceiver receiver1 = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            Log.d(TAG, "onReceive: Broadcast ACTION_FOUND received");
            FoundDeviceList.add(new BT_discovered_item("device found", "1"));
            UpdateBTDiscoveredList();
            String action = intent.getAction();
            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                // Discovery has found a device. Get the BluetoothDevice
                // object and its info from the Intent.
                //BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                //checkPermission();
                //FoundDeviceList.add(new BT_discovered_item(device.getName(), device.getAddress()));
                //FoundDeviceList.add(new BT_discovered_item("ACTION_FOUND", "1"));
                UpdateBTDiscoveredList();
            }
        }
    };

    @RequiresApi(api = Build.VERSION_CODES.S)
    @Override
    protected void onDestroy() {
        super.onDestroy();
//       bluetoothAdapter.cancelDiscovery();
//       Don't forget to unregister the ACTION_FOUND receiver.
        unregisterReceiver(receiver0);
        unregisterReceiver(receiver1);
    }


//    public boolean createBond(BluetoothDevice btDevice)
//            throws Exception
//    {
//        Class class1 = Class.forName("android.bluetooth.BluetoothDevice");
//        Method createBondMethod = class1.getMethod("createBond");
//        Boolean returnValue = (Boolean) createBondMethod.invoke(btDevice);
//        return returnValue.booleanValue();
//    }
//
//    public void ConnectionClick(View view) {
//        UUID MY_UUID = UUID.randomUUID();
//        createBond(bt)
//
//    }
}

    /*
    1) Make sure bluetooth is enabled
    2) scan for devices/refresh list
    3) select the device and connect
    4)
     **/