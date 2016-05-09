package entities;

import java.util.Objects;

/**
 *
 * @author L
 */
public class Record {

    private String period;
    private int id;
    private Employee employee;
    private Work work;
    private int hours;

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public Work getWork() {
        return work;
    }

    public void setWork(Work work) {
        this.work = work;
    }

    public int getHours() {
        return hours;
    }

    public void setHours(int hours) {
        this.hours = hours;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 97 * hash + Objects.hashCode(this.period);
        hash = 97 * hash + Objects.hashCode(this.id);
        hash = 97 * hash + this.hours;
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
        final Record other = (Record) obj;
        if (this.hours != other.hours) {
            return false;
        }
        if (!Objects.equals(this.period, other.period)) {
            return false;
        }
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Record{" + "period=" + period + ", id=" + id + ", employee=" + employee + ", work=" + work + ", hours=" + hours + '}';
    }

}
