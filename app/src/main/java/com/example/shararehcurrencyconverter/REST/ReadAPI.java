package com.example.shararehcurrencyconverter.REST;

import android.os.AsyncTask;
import com.example.shararehcurrencyconverter.RecyclerView.CurrenciesAdapter;
import com.example.shararehcurrencyconverter.Model.Currency;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.List;


import javax.net.ssl.HttpsURLConnection;

public class ReadAPI extends AsyncTask<String, String, String> {

    String apiUrl = "https://revolut.duckdns.org/latest?base=eur";
    private CurrenciesAdapter mAdapter;
    List<Currency> currencies;
    List<Currency> updateList;

    public ReadAPI(List<Currency> currencies, CurrenciesAdapter mAdapter) {

        this.mAdapter = mAdapter;
        this.currencies = currencies;

    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

    }

    @Override
    protected String doInBackground(String... params) {
        String current = "";
        try {
            URL url;
            HttpsURLConnection urlConnection = null;
            try {
                url = new URL(apiUrl);

                urlConnection = (HttpsURLConnection) url
                        .openConnection();

                InputStream in = urlConnection.getInputStream();

                InputStreamReader isw = new InputStreamReader(in);

                int data = isw.read();
                while (data != -1) {
                    current += (char) data;
                    data = isw.read();
                    System.out.print(current);

                }
                return current;

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            return "Exception: " + e.getMessage();
        }
        return current;
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        if (result != null) {
            parsJason(result);
        }

    }

    private void parsJason(String result) {
        JsonObject jsonObject = new JsonParser().parse(result).getAsJsonObject();
        JsonObject rates = jsonObject.getAsJsonObject("rates");
        getBaseCurrency();
        for (String keyStr : rates.keySet()) {
            Object keyvalue = rates.get(keyStr);
            Object keyName = keyStr;
            if (keyName.toString().equals("TRY"))
                currencies.add(new Currency(keyName.toString(), keyName.toString(), keyName.toString().toLowerCase().concat("1"), keyvalue.toString(),keyvalue.toString()));
            else
                currencies.add(new Currency(keyName.toString(), keyName.toString(), keyName.toString().toLowerCase(), keyvalue.toString(),keyvalue.toString()));
            mAdapter.notifyDataSetChanged();
        }


    }

    private void getBaseCurrency() {
        currencies.add(new Currency("EUR", "EUR", ("EUR".toLowerCase()), "1.000","1.000"));
    }
}