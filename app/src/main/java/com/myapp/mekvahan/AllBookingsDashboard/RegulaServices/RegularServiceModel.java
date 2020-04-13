package com.myapp.mekvahan.AllBookingsDashboard.RegulaServices;

import java.io.Serializable;
import java.util.List;

public class RegularServiceModel implements Serializable {

    private String bookingId;
    private String id, pickupAddress, dropAddress, serviceDate, serviceTime, pickupDateTime, dropDateTime;
    private String details, customerMobile;
    private int isPaymentDone;
    int serviceStatus = 0;

    private String vehicleModel, vehicleType, vehilceCompany, vehicleImage, vehilceLogo, licencePlate;
    private Double subTotal = 0d, additionalCharges = 0d, gst = 0d;
    private String createdAt, serviceName, pin, lat, lng;

    private List<String> actionList;

    public RegularServiceModel(String bookingId, String id, String pickupAddress, String lat, String lng, String dropAddress, String serviceDate, String serviceTime, String pickupDateTime, String dropDateTime, String details, String customerMobile, int isPaymentDone, int serviceStatus, String vehicleModel, String vehicleType, String vehilceCompany, String vehicleImage, String vehilceLogo, String licencePlate, Double subTotal, Double additionalCharges,
                               Double gst, String createdAt, String serviceName, String pin, List<String> actionList) {
        this.bookingId = bookingId;
        this.id = id;
        this.pickupAddress = pickupAddress;
        this.lat = lat;
        this.lng = lng;
        this.dropAddress = dropAddress;
        this.serviceDate = serviceDate;
        this.serviceTime = serviceTime;
        this.pickupDateTime = pickupDateTime;
        this.dropDateTime = dropDateTime;
        this.details = details;
        this.customerMobile = customerMobile;
        this.isPaymentDone = isPaymentDone;
        this.serviceStatus = serviceStatus;
        this.vehicleModel = vehicleModel;
        this.vehicleType = vehicleType;
        this.vehilceCompany = vehilceCompany;
        this.vehicleImage = vehicleImage;
        this.vehilceLogo = vehilceLogo;
        this.licencePlate = licencePlate;
        this.subTotal = subTotal;
        this.additionalCharges = additionalCharges;
        this.gst = gst;
        this.createdAt = createdAt;
        this.serviceName = serviceName;
        this.pin = pin;
        this.actionList = actionList;
    }

    public String getBookingId() {
        return bookingId;
    }

    public String getId() {
        return id;
    }

    public String getPickupAddress() {
        return pickupAddress;
    }

    public String getDropAddress() {
        return dropAddress;
    }

    public String getServiceDate() {
        return serviceDate;
    }

    public String getServiceTime() {
        return serviceTime;
    }

    public String getPickupDateTime() {
        return pickupDateTime;
    }

    public String getDropDateTime() {
        return dropDateTime;
    }

    public String getDetails() {
        return details;
    }

    public String getCustomerMobile() {
        return customerMobile;
    }

    public int getIsPaymentDone() {
        return isPaymentDone;
    }

    public int getServiceStatus() {
        return serviceStatus;
    }

    public String getVehicleModel() {
        return vehicleModel;
    }

    public String getVehicleType() {
        return vehicleType;
    }

    public String getVehilceCompany() {
        return vehilceCompany;
    }

    public String getVehicleImage() {
        return vehicleImage;
    }

    public String getVehilceLogo() {
        return vehilceLogo;
    }

    public String getLicencePlate() {
        return licencePlate;
    }

    public Double getSubTotal() {
        return subTotal;
    }

    public Double getAdditionalCharges() {
        return additionalCharges;
    }

    public Double getGst() {
        return gst;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public String getServiceName() {
        return serviceName;
    }

    public String getPin() {
        return pin;
    }

    public List<String> getActionList() {
        return actionList;
    }


    public String getLat() {
        return lat;
    }

    public String getLng() {
        return lng;
    }
}
