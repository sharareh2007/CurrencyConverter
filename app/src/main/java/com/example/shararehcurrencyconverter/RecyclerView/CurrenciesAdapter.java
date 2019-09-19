package com.example.shararehcurrencyconverter.RecyclerView;

import android.content.Context;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.InputType;
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
    static double SourceRate;
   //List<Currency> updatedCurrency = new ArrayList<>();

    public class CustomViewHolder extends RecyclerView.ViewHolder {
        public TextView currencyCode, currencyName;
        public EditText currencyRate;
        public ImageView currencyImage;


        public CustomViewHolder(View view) {
            super(view);
            currencyCode = view.findViewById(R.id.currencyCode);
            currencyName = view.findViewById(R.id.currencyName);
            currencyRate = view.findViewById(R.id.currencyRate);
            currencyRate.setInputType(InputType.TYPE_CLASS_NUMBER);
            currencyImage = (ImageView) view.findViewById(R.id.currencyImage);
            final CustomViewHolder holder = this;
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

            currencyRate.addTextChangedListener(new MyTextWatcher(holder));
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

        //  notifyDataSetChanged();
        //   holder.currencyRate.addTextChangedListener(new MyTextWatcher(holder));

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

            //updatedCurrency.clear();
            //  if (!charSequence.toString().isEmpty()) {
            double sourcePrice = Double.parseDouble(String.valueOf(charSequence));
            //finalprice = (int) (Math.round(finalprice * 10000)) / 10000.0;
            currencies.get(this.holder.getAdapterPosition()).setEditTextValue(String.format("%.4f", sourcePrice));
            SourceRate = Double.parseDouble(String.valueOf(currencies.get(this.holder.getAdapterPosition()).getCurrencyRate()));
            // updatedCurrency.add(new Currency(String.valueOf(currencies.get(this.holder.getAdapterPosition()).getCurrencyCode()), String.valueOf(currencies.get(this.holder.getAdapterPosition()).getCurrencyName()), String.valueOf(currencies.get(this.holder.getAdapterPosition()).getCurrencyImage()), String.valueOf(currencies.get(this.holder.getAdapterPosition()).getCurrencyRate()),String.format("%.2f", sourcePrice)));
            for (int j = 0; j < currencies.size(); j++) {
                // if (j != this.holder.getAdapterPosition()) {
                double DestinationRate = Double.parseDouble(String.valueOf(currencies.get(j).getCurrencyRate()));
                double finalprice = (sourcePrice / SourceRate) * DestinationRate;
                // String ttests = Double.toString(finalprice);
                String ttests = String.format("%.4f", finalprice);
                //updatedCurrency.add(new Currency(String.valueOf(currencies.get(j).getCurrencyCode()), String.valueOf(currencies.get(j).getCurrencyName()), String.valueOf(currencies.get(j).getCurrencyImage()), String.valueOf(currencies.get(j).getCurrencyRate()), ttests));
                currencies.get(j).setEditTextValue(ttests);
                //    }
            }
            //  notifyDataSetChanged();
            // }
        }

        @Override
        public void afterTextChanged(Editable editable) {

        }

    }

}