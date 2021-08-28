package com.myapplicationdev.android.c302_php_mysql_magic_assignment;

public class Card {

    private int cardId;
    private String cardName;
    private int colourId;
    private Double price;

    public Card(int cardId, String cardName, int colourId, Double price) {
        this.cardId = cardId;
        this.cardName = cardName;
        this.colourId = colourId;
        this.price = price;
    }

    public int getCardId() {
        return cardId;
    }

    public void setCardId(int cardId) {
        this.cardId = cardId;
    }

    public String getCardName() {
        return cardName;
    }

    public void setCardName(String cardName) {
        this.cardName = cardName;
    }

    public int getColourId() {
        return colourId;
    }

    public void setColourId(int colourId) {
        this.colourId = colourId;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
}
