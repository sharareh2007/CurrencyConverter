package com.example.shararehcurrencyconverter.RecyclerView;

import android.content.Context;
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
import com.example.shararehcurrencyconverter.Model.Rate;
import com.example.shararehcurrencyconverter.R;

import java.util.List;

public class CurrenciesAdapter extends RecyclerView.Adapter<CurrenciesAdapter.CustomViewHolder> {
    private final Context context;
    private List<Currency> currencies;
    private List<Rate> rates;
    static double SourceRate;
    private RecyclerView recyclerView;


    public class CustomViewHolder extends RecyclerView.ViewHolder {
        public TextView currencyCode, currencyName;
        public EditText currencyRate;
        public ImageView currencyImage;


        public CustomViewHolder(View view) {
            super(view);
            currencyCode = view.findViewById(R.id.currencyCode);
            currencyName = view.findViewById(R.id.currencyName);
            currencyRate = view.findViewById(R.id.currencyRate);
            // currencyRate.setInputType(InputType.TYPE_CLASS_NUMBER);
            currencyImage = (ImageView) view.findViewById(R.id.currencyImage);

            final CustomViewHolder holder = this;
            view.clearFocus();
            currencyRate.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                   // currencyRate.setFocusableInTouchMode(true);
                    currencyRate.requestFocus();
                    currencyRate.post(new Runnable() {
                        @Override
                        public void run() {
                            currencyRate.setSelection(currencyRate.length());
                        }
                    });

                   // currencyRate.setFocusable(true);
                    InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);


                    return true;
                }
            });



            currencyRate.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View view, boolean hasFocus) {
                    if (hasFocus) {
                        currencyRate.addTextChangedListener(new MyTextWatcher(holder));
                }

            }});

        }

    }

    public CurrenciesAdapter(List<Currency> currencies, List<Rate> rates, Context context, RecyclerView recyclerView) {
        this.currencies = currencies;
        this.rates = rates;
        this.context = context;
        this.recyclerView = recyclerView;
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
            if (charSequence != "") {
            double sourcePrice = Double.parseDouble(String.valueOf(charSequence));
            SourceRate = Double.parseDouble(String.valueOf(rates.get(this.holder.getAdapterPosition()).getCurrencyRate()));

                for (int j = 0; j < currencies.size(); j++) {
                double DestinationRate = Double.parseDouble(String.valueOf(rates.get(j).getCurrencyRate()));
                double finalprice = (sourcePrice / SourceRate) * DestinationRate;
                    // String ttests = String.format("%.4f", finalprice);
                    currencies.get(j).setEditTextValue(String.valueOf(finalprice));
                }

                recyclerView.post(new Runnable() {
                    @Override
                    public void run() {
                        notifyDataSetChanged();
                    }
                });
        }
        }
        @Override
        public void afterTextChanged(Editable editable) {

        }

    }

}