package com.scaner.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

@Entity
public class Advert implements Comparable<Advert>{

    @Id
    private String dataItemId;
    private String affordableInd;
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
    private Integer numberOfRooms;
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
    /**
     * nasze piętro
     */
    private String accurateFloor;
    /**
     * wszystkie piętra
     */
    private Integer allFloor;
    private String unityArea;
    private Double deposit;
    private String kindOfPlot;
    private Double Xside;
    private Double Yside;
    private String dealerType;
    private String fence;
    private String depositeCurency;
    private String location;
    private String approachRoad;
    private String heating;
    private String levelFinishingBuilding;
    private String neighbourhood;
    private String linkFilm;
    private String linkVirtualWalking;
    private String linkDevelopmentPlan;
    private String link3dView;
    private String typeOfDevelopment;
    private Date dateBuild;
    private String conditionOfProperty;
    private String typeOfWindow;
    private String typeOfBuildingMaterial;
    private Date dateOfAvailability;
    private String alsoForStudent;
    private String equipment;
    private String utilities;
    private String additionalInformation;
    private String security;
    private Double latitude;
    private Double longitude;

    public Advert() {
    }

    public String getAffordableInd() {
        return affordableInd;
    }

    public void setAffordableInd(String affordableInd) {
        this.affordableInd = affordableInd;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public String getSecurity() {
        return security;
    }

    public void setSecurity(String security) {
        this.security = security;
    }

    public String getEquipment() {
        return equipment;
    }

    public void setEquipment(String equipment) {
        this.equipment = equipment;
    }

    public String getAdditionalInformation() {
        return additionalInformation;
    }

    public void setAdditionalInformation(String additionalInformation) {
        this.additionalInformation = additionalInformation;
    }

    public String getAlsoForStudent() {
        return alsoForStudent;
    }

    public void setAlsoForStudent(String alsoForStudent) {
        this.alsoForStudent = alsoForStudent;
    }

    public Date getDateOfAvailability() {
        return dateOfAvailability;
    }

    public void setDateOfAvailability(Date dateOfAvailability) {
        this.dateOfAvailability = dateOfAvailability;
    }

    public String getTypeOfWindow() {
        return typeOfWindow;
    }

    public void setTypeOfWindow(String typeOfWindow) {
        this.typeOfWindow = typeOfWindow;
    }

    public String getTypeOfBuildingMaterial() {
        return typeOfBuildingMaterial;
    }

    public void setTypeOfBuildingMaterial(String typeOfBuildingMaterial) {
        this.typeOfBuildingMaterial = typeOfBuildingMaterial;
    }

    public String getConditionOfProperty() {
        return conditionOfProperty;
    }

    public void setConditionOfProperty(String conditionOfProperty) {
        this.conditionOfProperty = conditionOfProperty;
    }

    public Date getDateBuild() {
        return dateBuild;
    }

    public void setDateBuild(Date dateBuild) {
        this.dateBuild = dateBuild;
    }

    public String getHeating() {
        return heating;
    }

    public void setHeating(String heating) {
        this.heating = heating;
    }

    public String getLevelFinishingBuilding() {
        return levelFinishingBuilding;
    }

    public void setLevelFinishingBuilding(String levelFinishingBuilding) {
        this.levelFinishingBuilding = levelFinishingBuilding;
    }

    public String getTypeOfDevelopment() {
        return typeOfDevelopment;
    }

    public void setTypeOfDevelopment(String typeOfDevelopment) {
        this.typeOfDevelopment = typeOfDevelopment;
    }

    public String getDepositeCurency() {
        return depositeCurency;
    }

    public void setDepositeCurency(String depositeCurency) {
        this.depositeCurency = depositeCurency;
    }

    public Double getDeposit() {
        return deposit;
    }

    public void setDeposit(Double deposit) {
        this.deposit = deposit;
    }

    public String getAccurateFloor() {
        return accurateFloor;
    }

    public void setAccurateFloor(String accurateFloor) {
        this.accurateFloor = accurateFloor;
    }

    public Integer getAllFloor() {
        return allFloor;
    }

    public void setAllFloor(Integer allFloor) {
        this.allFloor = allFloor;
    }

    public Integer getNumberOfRooms() {
        return numberOfRooms;
    }

    public void setNumberOfRooms(Integer numberOfRooms) {
        this.numberOfRooms = numberOfRooms;
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

    @Override
    public int compareTo(Advert o) {
        return -1;
    }
}
