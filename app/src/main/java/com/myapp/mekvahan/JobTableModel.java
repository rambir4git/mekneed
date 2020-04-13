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

    public String getBookingId() {
        return bookingId;
    }

    public void setBookingId(String bookingId) {
        this.bookingId = bookingId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPriceOriginal() {
        return priceOriginal;
    }

    public void setPriceOriginal(String priceOriginal) {
        this.priceOriginal = priceOriginal;
    }

    public String getIsNegotiable() {
        return isNegotiable;
    }

    public void setIsNegotiable(String isNegotiable) {
        this.isNegotiable = isNegotiable;
    }

    public String getNegotiableAmount() {
        return negotiableAmount;
    }

    public void setNegotiableAmount(String negotiableAmount) {
        this.negotiableAmount = negotiableAmount;
    }

    public String getNegStatus() {
        return negStatus;
    }

    public void setNegStatus(String negStatus) {
        this.negStatus = negStatus;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSettledPrice() {
        return settledPrice;
    }

    public void setSettledPrice(String settledPrice) {
        this.settledPrice = settledPrice;
    }

    public String getServiceStatus() {
        return serviceStatus;
    }

    public void setServiceStatus(String serviceStatus) {
        this.serviceStatus = serviceStatus;
    }

    public String getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
    }

    public String getImageOne() {
        return imageOne;
    }

    public void setImageOne(String imageOne) {
        this.imageOne = imageOne;
    }

    public String getImageTwo() {
        return imageTwo;
    }

    public void setImageTwo(String imageTwo) {
        this.imageTwo = imageTwo;
    }

    public String getImageThree() {
        return imageThree;
    }

    public void setImageThree(String imageThree) {
        this.imageThree = imageThree;
    }

    public Boolean getConfirmed() {
        return isConfirmed;
    }

    public void setConfirmed(Boolean confirmed) {
        isConfirmed = confirmed;
    }

    public Boolean getAccepted() {
        return isAccepted;
    }

    public void setAccepted(Boolean accepted) {
        isAccepted = accepted;
    }

    public Boolean getRejected() {
        return isRejected;
    }

    public void setRejected(Boolean rejected) {
        isRejected = rejected;
    }
}
