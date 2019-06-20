package com.prodev.myapplication.models;

public class model {
    String industy,company,symbol,isin;
    String date,close,open;
    String date3,cost3,stock3,unit3,total3,current3,currentP3;
    String difference3,percent3,comp3;

     public model(String comp3, String date3, String cost3,
                  String stock3, String unit3, String total3,String currentP3, String current3,String difference3,String percent3){
        this.comp3 =comp3;
        this.date3=date3;
        this.cost3=cost3;
        this.stock3=stock3;
        this.unit3=unit3;
        this.total3=total3;
        this.current3=current3;
        this.difference3=difference3;
        this.percent3=percent3;
        this.currentP3=currentP3;

    }
    public model(){}

    public String getComp3() {
        return comp3;
    }

    public void setComp3(String comp3) {
        this.comp3 = comp3;
    }
    public String getCurrentP3() {
        return currentP3;
    }

    public void setCurrentP3(String currentP3) {
        this.currentP3 = currentP3;
    }


    public String getDate3() {
        return date3;
    }

    public void setDate3(String date3) {
        this.date3 = date3;
    }

    public String getCost3() {
        return cost3;
    }

    public void setCost3(String cost3) {
        this.cost3 = cost3;
    }

    public String getStock3() {
        return stock3;
    }

    public void setStock3(String stock3) {
        this.stock3 = stock3;
    }

    public String getUnit3() {
        return unit3;
    }

    public void setUnit3(String unit3) {
        this.unit3 = unit3;
    }

    public String getTotal3() {
        return total3;
    }

    public void setTotal3(String total3) {
        this.total3 = total3;
    }

    public String getCurrent3() {
        return current3;
    }

    public void setCurrent3(String current3) {
        this.current3 = current3;
    }

    public String getDifference3() {
        return difference3;
    }

    public void setDifference3(String difference3) {
        this.difference3 = difference3;
    }

    public String getPercent3() {
        return percent3;
    }

    public void setPercent3(String percent3) {
        this.percent3 = percent3;
    }

    //--------------------------------------------------------------------------------------------------------------

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getClose() {
        return close;
    }

    public void setClose(String close) {
        this.close = close;
    }

    public String getOpen() {
        return open;
    }

    public void setOpen(String open) {
        this.open = open;
    }
    //-------------------------------------------------------------------------------------------------------

    public String getIndusty() {
        return industy;
    }

    public void setIndusty(String industy) {
        this.industy = industy;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getIsin() {
        return isin;
    }

    public void setIsin(String isin) {
        this.isin = isin;
    }
}
