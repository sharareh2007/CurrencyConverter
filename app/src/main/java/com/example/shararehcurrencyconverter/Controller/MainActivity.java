package com.example.shararehcurrencyconverter.Controller;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.shararehcurrencyconverter.Model.Rate;
import com.example.shararehcurrencyconverter.RecyclerView.CurrenciesAdapter;
import com.example.shararehcurrencyconverter.RecyclerView.DividerItemDecoration;
import com.example.shararehcurrencyconverter.RecyclerView.RecyclerItemListener;
import com.example.shararehcurrencyconverter.Model.Currency;
import com.example.shararehcurrencyconverter.R;
import com.example.shararehcurrencyconverter.REST.ReadAPI;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class MainActivity extends AppCompatActivity {
    List<Currency> currencies = new ArrayList<>();
    List<Rate> rates = new ArrayList<>();
    Context context;

    private RecyclerView recyclerView;
    private CurrenciesAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.recycler_view);
        mAdapter = new CurrenciesAdapter(currencies, rates, this,recyclerView);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);
        context = this;
        new ReadAPI(currencies, rates, mAdapter, false).execute();
        Thread t = new Thread() {
            @Override
            public void run() {
                while (!isInterrupted()) {
                    try {
                        Thread.sleep(10000);  //1000ms = 1 sec
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                new ReadAPI(currencies, rates, mAdapter, true).execute();
                                mAdapter = new CurrenciesAdapter(currencies, rates, context,recyclerView);
                                recyclerView.setAdapter(mAdapter);
                                mAdapter.notifyDataSetChanged();
                            }
                        });

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };

        t.start();


        recyclerView.setHasFixedSize(true);

        recyclerView.addOnItemTouchListener(new RecyclerItemListener(getApplicationContext(),
                recyclerView, new RecyclerItemListener.RecyclerTouchListener() {
            @Override
            public void onClickItem(View v, int position) {
                recyclerView.setFocusable(true);
                recyclerView.setFocusable(true);
                Toast.makeText(getApplicationContext(), "Clicked: " +
                        currencies.get(position).getCurrencyCode(), Toast.LENGTH_SHORT).show();
                Collections.swap(currencies, position, 0);
                mAdapter.notifyDataSetChanged();

            }

            @Override
            public void onLongClickItem(View v, int position) {
                Toast.makeText(getApplicationContext(), "Long Pressed: " +
                        currencies.get(position).getCurrencyCode(), Toast.LENGTH_SHORT).show();

            }
        }));

        recyclerView.addItemDecoration(
                new DividerItemDecoration(ContextCompat.getDrawable(getApplicationContext(), R.drawable.item_seperator)));
    }

}