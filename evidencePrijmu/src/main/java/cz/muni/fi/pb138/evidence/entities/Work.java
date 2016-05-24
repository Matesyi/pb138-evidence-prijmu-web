package cz.muni.fi.pb138.evidence.entities;

import java.util.Objects;

/**
 *
 * @author L
 */
public class Work {

    private String type;
    private String rate;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 97 * hash + Objects.hashCode(this.type);
        hash = 97 * hash + Objects.hashCode(this.rate);
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
        if (!Objects.equals(this.type, other.type)) {
            return false;
        }
        return Objects.equals(this.rate, other.rate);
    }

    @Override
    public String toString() {
        return "Work{" + "type=" + type + ", rate=" + rate + '}';
    }

}
