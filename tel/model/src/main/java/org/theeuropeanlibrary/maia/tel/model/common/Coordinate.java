package org.theeuropeanlibrary.maia.tel.model.common;

import org.theeuropeanlibrary.maia.common.FieldId;

/**
 * A point on Earth, with coordinates expressed in decimal degrees (WGS 84)
 *
 * @author Nuno Freire (nfreire@gmail.com)
 * @date 2 de Nov de 2011
 */
public class Coordinate {

    /**
     * Latitude coordinate in decimal degrees (WGS 84)
     */
    @FieldId(1)
    private double latitude;

    /**
     * Longitude coordinate in decimal degrees (WGS 84)
     */
    @FieldId(2)
    private double longitude;

    /**
     * Creates a new instance of this class.
     */
    public Coordinate() {
        super();
    }

    /**
     * Creates a new instance of this class.
     *
     * @param latitude Latitude coordinate in decimal degrees (WGS 84)
     * @param longitude Longitude coordinate in decimal degrees (WGS 84)
     */
    public Coordinate(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    /**
     * @return Latitude coordinate in decimal degrees (WGS 84)
     */
    public double getLatitude() {
        return latitude;
    }

    /**
     * @return Longitude coordinate in decimal degrees (WGS 84)
     */
    public double getLongitude() {
        return longitude;
    }

    /**
     * @param latitude Latitude coordinate in decimal degrees (WGS 84)
     */
    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    /**
     * @param longitude Longitude coordinate in decimal degrees (WGS 84)
     */
    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        long temp;
        temp = Double.doubleToLongBits(latitude);
        result = prime * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(longitude);
        result = prime * result + (int) (temp ^ (temp >>> 32));
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!super.equals(obj)) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        Coordinate other = (Coordinate) obj;
        if (Double.doubleToLongBits(latitude) != Double.doubleToLongBits(other.latitude)) {
            return false;
        }
        if (Double.doubleToLongBits(longitude) != Double.doubleToLongBits(other.longitude)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Coordinate{" + "latitude=" + latitude + ", longitude=" + longitude + '}';
    }

}
