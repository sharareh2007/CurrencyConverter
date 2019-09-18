package com.example.shararehcurrencyconverter.Model;

public class Currency {
    private String currencyCode;
    private String currencyName;
    private String currencyRate;
    private String currencyImage;
    private String currencyRateEditText;


    public Currency(String currencyCode, String currencyName, String currencyImage, String currencyRate , String currencyRateEditText) {
        this.currencyCode = currencyCode;
        this.currencyName = currencyName;
        this.currencyRate = currencyRate;
        this.currencyImage = currencyImage;
        this.currencyRateEditText = currencyRateEditText;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public String getCurrencyName() {
        return currencyName;
    }

    public String getCurrencyRate() {
        return currencyRate;
    }

    public String getCurrencyImage() {
        return currencyImage;
    }

    public String getCurrencyRateEditText() {
        return  currencyRateEditText ;
    }

    public void setEditTextValue(String currencyRate) {

        this.currencyRateEditText = currencyRate;
    }
}
