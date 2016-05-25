package cz.muni.fi.pb138.evidence.entities;

import java.util.Objects;

/**
 *
 * @author L
 */
public class Employee {
    
    private int personal_number;
    private String name;
    private String surname;
    private String address;
    private int postCode;
    private String city;
    private boolean active=true;

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getPostCode() {
        return postCode;
    }

    public void setPostCode(int postCode) {
        this.postCode = postCode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public int getPersonal_number() {
        return personal_number;
    }

    public void setPersonal_number(int personal_number) {
        this.personal_number = personal_number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 89 * hash + this.personal_number;
        hash = 89 * hash + Objects.hashCode(this.name);
        hash = 89 * hash + Objects.hashCode(this.surname);
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
        final Employee other = (Employee) obj;
        if (this.personal_number != other.personal_number) {
            return false;
        }
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        if (!Objects.equals(this.surname, other.surname)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Employee{" + "personal_number=" + personal_number + ", name=" + name + ", surname=" + surname + ", address=" + address + ", postCode=" + postCode + ", city=" + city + ", active=" + active + '}';
    }

}
