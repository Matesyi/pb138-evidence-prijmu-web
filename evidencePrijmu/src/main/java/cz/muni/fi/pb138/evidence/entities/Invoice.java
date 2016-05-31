package cz.muni.fi.pb138.evidence.entities;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 *
 * @author L
 */
public class Invoice {

    private long id;
    private int month;
    private int year;
    private String employer;
    private Employee employee;
    private Map<Work,Integer> works = new HashMap<>();

    public String getEmployer() {
        return employer;
    }

    public void setEmployer(String employer) {
        this.employer = employer;
    }

    public Map<Work, Integer> getWorks() {
        return works;
    }

    public void setWorks(Map<Work, Integer> works) {
        this.works = works;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 23 * hash + (int) (this.id ^ (this.id >>> 32));
        hash = 23 * hash + this.month;
        hash = 23 * hash + this.year;
        hash = 23 * hash + Objects.hashCode(this.employer);
        hash = 23 * hash + Objects.hashCode(this.employee);
        hash = 23 * hash + Objects.hashCode(this.works);
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
        final Invoice other = (Invoice) obj;
        if (this.id != other.id) {
            return false;
        }
        if (this.month != other.month) {
            return false;
        }
        if (this.year != other.year) {
            return false;
        }
        if (!Objects.equals(this.employer, other.employer)) {
            return false;
        }
        if (!Objects.equals(this.employee, other.employee)) {
            return false;
        }
        if (!Objects.equals(this.works, other.works)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Invoice{" + "id=" + id + ", month=" + month + ", year=" + year + ", employer=" + employer + ", employee=" + employee + ", works=" + works + '}';
    }

}
