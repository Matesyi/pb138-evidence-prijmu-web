/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.muni.fi.pb138.evidence.servlet;

/**
 *
 * @author Miloš Šilhár
 */
public class Employee {
    public int personal_number;
    public String name;
    public String surname;

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + this.personal_number;
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
        return true;
    }
}
