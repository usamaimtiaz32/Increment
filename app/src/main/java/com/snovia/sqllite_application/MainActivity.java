package com.snovia.sqllite_application;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.Toast;
import android.os.Bundle;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    Button buttonAdd, buttonViewAll;
    EditText editName, editAge;
    Switch switchIsActive;
    ListView listViewCustomer;
    ArrayAdapter<CustomerModel> arrayAdapter;
    DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonAdd = findViewById(R.id.buttonAdd);
        buttonViewAll = findViewById(R.id.buttonViewAll);
        editName = findViewById(R.id.editTextName);
        editAge = findViewById(R.id.editTextAge);
        switchIsActive = findViewById(R.id.switchCustomer);
        listViewCustomer = findViewById(R.id.listViewCustomer);
        RefreshData();

        buttonAdd.setOnClickListener(new View.OnClickListener() {
            CustomerModel customerModel;

            @Override
            public void onClick(View v) {
                try {
                    customerModel = new CustomerModel(editName.getText().toString(), Integer.parseInt(editAge.getText().toString()), switchIsActive.isChecked(), 1);
                   // Toast.makeText(MainActivity.this, customerModel.toString(), Toast.LENGTH_SHORT).show();
                }
                catch (Exception e){
                    Toast.makeText(MainActivity.this, "Error", Toast.LENGTH_SHORT).show();
                }
                DBHelper dbHelper = new DBHelper(MainActivity.this);
                boolean b = dbHelper.addCustomer(customerModel);
                RefreshData();
            }
        });

        buttonViewAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RefreshData();
            }
        });
        listViewCustomer.setOnItemClickListener(((parent, view, position, id) -> {
            CustomerModel customerModel=(CustomerModel) parent. getAdapter().getItem(position);
            dbHelper = new DBHelper(MainActivity.this);
            dbHelper.deleteCustomer(customerModel.getId());
            RefreshData();
        }));
    }

    private void RefreshData() {
        dbHelper= new DBHelper(MainActivity.this);
        List<CustomerModel> allCustomers= dbHelper.getAllRecord();
        // Toast.makeText(MainActivity.this, allCustomers.toString(), Toast.LENGTH_SHORT).show();
        arrayAdapter= new ArrayAdapter<CustomerModel>(MainActivity.this, android.R.layout.simple_list_item_1,
                allCustomers);
        listViewCustomer.setAdapter(arrayAdapter);
    }
}
