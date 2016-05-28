package cz.muni.fi.pb138.evidence.entities;

import java.util.Objects;

/**
 *
 * @author L
 */
public class Work {

    private int workID;

    public int getWorkID() {
        return workID;
    }

    public void setWorkID(int workID) {
        this.workID = workID;
    }
    private String work_type;
    private int price;

    public String getWork_type() {
        return work_type;
    }

    public void setWork_type(String work_type) {
        this.work_type = work_type;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 53 * hash + this.workID;
        hash = 53 * hash + Objects.hashCode(this.work_type);
        hash = 53 * hash + this.price;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Work other = (Work) obj;
        if (this.workID != other.workID) {
            return false;
        }
        if (this.price != other.price) {
            return false;
        }
        if (!Objects.equals(this.work_type, other.work_type)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Work{" + "workID=" + workID + ", work_type=" + work_type + ", price=" + price + '}';
    }

}
