package com.scaner.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;

@Entity
public class Advert implements Serializable{

    @Id
    private String dataItemId;
    private String linkToAdImage;
    /**
     * pełny adres
     */
    private String address;
    private String city;
    private String district;
    private String street;
    private Date generateAdvertDt;
    private Integer propertyNumber;
    private String details;
    private String dataTrackingId;
    private String linkToAd;
    private String title;
    private Double price;
    private String currency;
    private String offerItemTitle;
    private Double pricePerMetre;
    private Double priceForPeriodOfPayment;
    private String periodOfPeyment;
    /**
     * rodzaj transkacji np. na wynajem
     */
    private String kindOfTransaction;
    private String propertyType;
    /**
     * powierzchnia mieszkalna
     */
    private Double livingArea;
    /**
     * opis nieruchomości ze strony głownej
     */
    private String descAdvert;
    private String unityArea;
    private String kindOfPlot;
    private Double Xside;
    private Double Yside;
    private String dealerType;
    private String fence;
    private String location;
    private String utilities;
    private String approachRoad;
    private String neighbourhood;
    private String linkFilm;
    private String linkVirtualWalking;
    private String linkDevelopmentPlan;
    private String link3dView;

    public Advert() {
    }

    public Date getGenerateAdvertDt() {
        return generateAdvertDt;
    }

    public void setGenerateAdvertDt(Date generateAdvertDt) {
        this.generateAdvertDt = generateAdvertDt;
    }

    public String getPeriodOfPeyment() {
        return periodOfPeyment;
    }

    public void setPeriodOfPeyment(String periodOfPeyment) {
        this.periodOfPeyment = periodOfPeyment;
    }

    public Double getPriceForPeriodOfPayment() {
        return priceForPeriodOfPayment;
    }

    public void setPriceForPeriodOfPayment(Double priceForPeriodOfPayment) {
        this.priceForPeriodOfPayment = priceForPeriodOfPayment;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getKindOfTransaction() {
        return kindOfTransaction;
    }

    public void setKindOfTransaction(String kindOfTransaction) {
        this.kindOfTransaction = kindOfTransaction;
    }

    public Double getLivingArea() {
        return livingArea;
    }

    public void setLivingArea(Double livingArea) {
        this.livingArea = livingArea;
    }

    public String getDescAdvert() {
        return descAdvert;
    }

    public void setDescAdvert(String descAdvert) {
        this.descAdvert = descAdvert;
    }

    public String getDataItemId() {
        return dataItemId;
    }

    public void setDataItemId(String dataItemId) {
        this.dataItemId = dataItemId;
    }

    public String getLinkToAdImage() {
        return linkToAdImage;
    }

    public void setLinkToAdImage(String linkToAdImage) {
        this.linkToAdImage = linkToAdImage;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public Integer getPropertyNumber() {
        return propertyNumber;
    }

    public void setPropertyNumber(Integer propertyNumber) {
        this.propertyNumber = propertyNumber;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getDataTrackingId() {
        return dataTrackingId;
    }

    public void setDataTrackingId(String dataTrackingId) {
        this.dataTrackingId = dataTrackingId;
    }

    public String getLinkToAd() {
        return linkToAd;
    }

    public void setLinkToAd(String linkToAd) {
        this.linkToAd = linkToAd;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getOfferItemTitle() {
        return offerItemTitle;
    }

    public void setOfferItemTitle(String offerItemTitle) {
        this.offerItemTitle = offerItemTitle;
    }

    public Double getPricePerMetre() {
        return pricePerMetre;
    }

    public void setPricePerMetre(Double pricePerMetre) {
        this.pricePerMetre = pricePerMetre;
    }

    public String getPropertyType() {
        return propertyType;
    }

    public void setPropertyType(String propertyType) {
        this.propertyType = propertyType;
    }

    public String getUnityArea() {
        return unityArea;
    }

    public void setUnityArea(String unityArea) {
        this.unityArea = unityArea;
    }

    public String getKindOfPlot() {
        return kindOfPlot;
    }

    public void setKindOfPlot(String kindOfPlot) {
        this.kindOfPlot = kindOfPlot;
    }

    public Double getXside() {
        return Xside;
    }

    public void setXside(Double xside) {
        Xside = xside;
    }

    public Double getYside() {
        return Yside;
    }

    public void setYside(Double yside) {
        Yside = yside;
    }

    public String getDealerType() {
        return dealerType;
    }

    public void setDealerType(String dealerType) {
        this.dealerType = dealerType;
    }

    public String getFence() {
        return fence;
    }

    public void setFence(String fence) {
        this.fence = fence;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getUtilities() {
        return utilities;
    }

    public void setUtilities(String utilities) {
        this.utilities = utilities;
    }

    public String getApproachRoad() {
        return approachRoad;
    }

    public void setApproachRoad(String approachRoad) {
        this.approachRoad = approachRoad;
    }

    public String getNeighbourhood() {
        return neighbourhood;
    }

    public void setNeighbourhood(String neighbourhood) {
        this.neighbourhood = neighbourhood;
    }

    public String getLinkFilm() {
        return linkFilm;
    }

    public void setLinkFilm(String linkFilm) {
        this.linkFilm = linkFilm;
    }

    public String getLinkVirtualWalking() {
        return linkVirtualWalking;
    }

    public void setLinkVirtualWalking(String linkVirtualWalking) {
        this.linkVirtualWalking = linkVirtualWalking;
    }

    public String getLinkDevelopmentPlan() {
        return linkDevelopmentPlan;
    }

    public void setLinkDevelopmentPlan(String linkDevelopmentPlan) {
        this.linkDevelopmentPlan = linkDevelopmentPlan;
    }

    public String getLink3dView() {
        return link3dView;
    }

    public void setLink3dView(String link3dView) {
        this.link3dView = link3dView;
    }


    @Override
    public String toString() {
        return "Advert{" +
                "dataItemId='" + dataItemId + '\'' +
                ", address='" + address + '\'' +
                ", city='" + city + '\'' +
                ", district='" + district + '\'' +
                ", street='" + street + '\'' +
                ", linkToAd='" + linkToAd + '\'' +
                ", price=" + price +
                ", currency='" + currency + '\'' +
                ", periodOfPeyment='" + periodOfPeyment + '\'' +
                ", kindOfTransaction='" + kindOfTransaction + '\'' +
                ", descAdvert='" + descAdvert + '\'' +
                '}';
    }
}
