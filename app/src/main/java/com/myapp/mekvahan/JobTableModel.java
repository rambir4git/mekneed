package com.myapp.mekvahan;

public class JobTableModel {
    String id;
    String type;
    String bookingId;
    String title;
    String priceOriginal;
    String isNegotiable;
    String negotiableAmount;
    String negStatus;
    String description;
    String settledPrice;
    String serviceStatus;
    String expiryDate;
    String imageOne;
    String imageTwo;
    String imageThree;
    Boolean isConfirmed;
    Boolean isAccepted;
    Boolean isRejected;


    public JobTableModel(String id, String type, String bookingId, String title, String priceOriginal, String isNegotiable,
                         String negotiableAmount, String negStatus, String description, String settledPrice, String serviceStatus,
                         String expiryDate, String imageOne, String imageTwo, String imageThree, Boolean isConfirmed, Boolean isAccepted, Boolean isRejected) {
        this.id = id;
        this.type = type;
        this.bookingId = bookingId;
        this.title = title;
        this.priceOriginal = priceOriginal;
        this.isNegotiable = isNegotiable;
        this.negotiableAmount = negotiableAmount;
        this.negStatus = negStatus;
        this.description = description;
        this.settledPrice = settledPrice;
        this.serviceStatus = serviceStatus;
        this.expiryDate = expiryDate;
        this.imageOne = imageOne;
        this.imageTwo = imageTwo;
        this.imageThree = imageThree;
        this.isConfirmed = isConfirmed;
        this.isAccepted = isAccepted;
        this.isRejected = isRejected;
    }


    public String getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
