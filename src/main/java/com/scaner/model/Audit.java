package com.scaner.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Audit {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String datItemId;
    private String linkToAd;
    private String statusExistingInd;

    public Audit() {
    }

    public String getStatusExistingInd() {
        return statusExistingInd;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setStatusExistingInd(String statusExistingInd) {
        this.statusExistingInd = statusExistingInd;
    }

    public String getDatItemId() {
        return datItemId;
    }

    public void setDatItemId(String datItemId) {
        this.datItemId = datItemId;
    }

    public String getLinkToAd() {
        return linkToAd;
    }

    public void setLinkToAd(String linkToAd) {
        this.linkToAd = linkToAd;
    }
}
