package com.example.shararehcurrencyconverter.RecyclerView;

import android.content.Context;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.shararehcurrencyconverter.Model.Currency;
import com.example.shararehcurrencyconverter.R;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class CurrenciesAdapter extends RecyclerView.Adapter<CurrenciesAdapter.CustomViewHolder> {
    private final Context context;
    private List<Currency> currencies;

    List<Currency> updatedCurrency = new ArrayList<>();
    public class CustomViewHolder extends RecyclerView.ViewHolder {
        public TextView currencyCode, currencyName;
        public EditText currencyRate;
        public ImageView currencyImage;


        public CustomViewHolder(View view) {
            super(view);
            currencyCode = view.findViewById(R.id.currencyCode);
            currencyName = view.findViewById(R.id.currencyName);
            currencyRate = view.findViewById(R.id.currencyRate);
            currencyImage = (ImageView) view.findViewById(R.id.currencyImage);
            final CustomViewHolder test = this;
            view.clearFocus();
            currencyRate.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {

                    currencyRate.setFocusableInTouchMode(true);
                    currencyRate.requestFocus();
                    currencyRate.setFocusable(true);
                    InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);


                    return true;
                }
            });


        }

    }

    public CurrenciesAdapter(List<Currency> currencies, Context context) {
        this.currencies = currencies;
        this.context = context;
    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.currency_list, parent, false);

        return new CustomViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(CustomViewHolder holder, int position) {
        Currency currency = currencies.get(position);
        holder.currencyCode.setText(currency.getCurrencyCode());
        holder.currencyName.setText(currency.getCurrencyName());
        holder.currencyRate.setText(String.valueOf(currency.getCurrencyRateEditText()));

        holder.currencyRate.addTextChangedListener(new MyTextWatcher(holder));
        int resourceID = context.getResources().getIdentifier(currency.getCurrencyImage(), "drawable", context.getPackageName());
        holder.currencyImage.setImageResource(resourceID);
    }

    public int getItemCount() {
        return currencies.size();
    }

    public class MyTextWatcher implements TextWatcher {
        CustomViewHolder holder;

        public MyTextWatcher(CustomViewHolder holder) {
            this.holder = holder;

        }


        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

          //  if (!charSequence.toString().isEmpty()) {
                double finalprice = Double.parseDouble(String.valueOf(charSequence));
                finalprice = (int) (Math.round(finalprice * 10000)) / 10000.0;
               currencies.get(this.holder.getAdapterPosition()).setEditTextValue(Double.toString(finalprice));
                double Rate1 = Double.parseDouble(String.valueOf(currencies.get(this.holder.getAdapterPosition()).getCurrencyRate()));

                for (int j = 0; j < currencies.size(); j++) {
                  //  if (j != this.holder.getAdapterPosition()) {
                        double Rate2 = Double.parseDouble(String.valueOf(currencies.get(j).getCurrencyRate()));

                        finalprice = (int) (Math.round((finalprice / Rate2) * Rate1 * 10000)) / 10000.0;
                        String ttests = Double.toString(finalprice);
                     //   currencies.get(j).setEditTextValue(ttests);
             //       }

                }


           // }
        }

        @Override
        public void afterTextChanged(Editable editable) {

        }

    }

}