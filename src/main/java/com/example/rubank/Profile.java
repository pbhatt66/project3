package com.example.rubank;

/**
 * Profile class represents a holder's profile as an object with a first name, last name, and date of birth.
 *
 * @author Pranay Bhatt and Fiona Wang
 */
public class Profile implements Comparable<Profile> {
    private String fname;
    private String lname;
    private Date dob;

    /**
     * Constructs a Profile object with the given first name, last name, and date of birth.
     * @param fname the first name
     * @param lname the last name
     * @param dob the date of birth
     */
    public Profile(String fname, String lname, Date dob) {
        this.fname = fname;
        this.lname = lname;
        this.dob = dob;
    }

    /**
     * Returns first name.
     * @return first name
     */
    public String getFname() {
        return fname;
    }

    /**
     * Returns last name.
     * @return last name
     */
    public String getLname() {
        return lname;
    }

    /**
     * Returns a string representation of the profile.
     * @return a string representation of the profile
     */
    @Override
    public String toString() {
        return fname + " " + lname + " " + dob.toString();
    }

    /**
     * Compares this profile to another profile. Order of significance: last name, first name
     * @param profile the other profile
     * @return a negative integer if this profile is alphabetically before the other profile,
     * a positive integer if this date is alphabetically after the other profile, and 0 if the profiles are equal
     */
    @Override
    public int compareTo(Profile profile) {
        if (this.lname.compareTo(profile.lname) == 0) {
            if (this.fname.compareTo(profile.fname) == 0) {
                return this.dob.compareTo(profile.dob);
            }
            return this.fname.compareTo(profile.fname);
        }
        return this.lname.compareTo(profile.lname);
    }

    /**
     * Checks to see if two profiles are equal, based on Profile object.
     * @param obj the other profile
     * @return true if the profiles are equal, false otherwise
     */
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Profile) {
            Profile profile = (Profile) obj;
            return this.compareTo(profile) == 0;
        }
        return false;
    }

}