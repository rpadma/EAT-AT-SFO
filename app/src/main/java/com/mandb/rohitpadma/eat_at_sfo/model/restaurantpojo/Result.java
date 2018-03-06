package com.mandb.rohitpadma.eat_at_sfo.model.restaurantpojo;

import com.mandb.rohitpadma.eat_at_sfo.model.markerpojo.Geometry;

/**
 * Created by rohitpadma on 3/6/18.
 */

public class Result {

    private String icon;

    private String place_id;

    private Reviews[] reviews;

    private String scope;

    private String website;

    private String international_phone_number;

    private String adr_address;

    private String url;

    private String reference;

    private Geometry geometry;

    private Opening_hours opening_hours;

    private String utc_offset;

    private String id;

    private Photos[] photos;

    private String vicinity;

    private Address_components[] address_components;

    private String name;

    private String formatted_address;

    private String formatted_phone_number;

    private String rating;

    private String[] types;

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getPlace_id() {
        return place_id;
    }

    public void setPlace_id(String place_id) {
        this.place_id = place_id;
    }

    public Reviews[] getReviews() {
        return reviews;
    }

    public void setReviews(Reviews[] reviews) {
        this.reviews = reviews;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getInternational_phone_number() {
        return international_phone_number;
    }

    public void setInternational_phone_number(String international_phone_number) {
        this.international_phone_number = international_phone_number;
    }

    public String getAdr_address() {
        return adr_address;
    }

    public void setAdr_address(String adr_address) {
        this.adr_address = adr_address;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public Geometry getGeometry() {
        return geometry;
    }

    public void setGeometry(Geometry geometry) {
        this.geometry = geometry;
    }

    public Opening_hours getOpening_hours() {
        return opening_hours;
    }

    public void setOpening_hours(Opening_hours opening_hours) {
        this.opening_hours = opening_hours;
    }

    public String getUtc_offset() {
        return utc_offset;
    }

    public void setUtc_offset(String utc_offset) {
        this.utc_offset = utc_offset;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Photos[] getPhotos() {
        return photos;
    }

    public void setPhotos(Photos[] photos) {
        this.photos = photos;
    }

    public String getVicinity() {
        return vicinity;
    }

    public void setVicinity(String vicinity) {
        this.vicinity = vicinity;
    }

    public Address_components[] getAddress_components() {
        return address_components;
    }

    public void setAddress_components(Address_components[] address_components) {
        this.address_components = address_components;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFormatted_address() {
        return formatted_address;
    }

    public void setFormatted_address(String formatted_address) {
        this.formatted_address = formatted_address;
    }

    public String getFormatted_phone_number() {
        return formatted_phone_number;
    }

    public void setFormatted_phone_number(String formatted_phone_number) {
        this.formatted_phone_number = formatted_phone_number;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String[] getTypes() {
        return types;
    }

    public void setTypes(String[] types) {
        this.types = types;
    }
}
