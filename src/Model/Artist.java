package Model;

public class Artist {
    private int ID;
    private String name;
    private int birthYear;

    public Artist(int id, String name, int birthYear) {
        this.ID = id;
        this.name = name;
        this.birthYear = birthYear;
    }

    public Artist(String name, int birthYear) {
        this.name = name;
        this.birthYear = birthYear;
    }

    @Override
    public String toString() {
        return this.getName();
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getBirthYear() {
        return birthYear;
    }

    public void setBirthYear(int birthYear) {
        this.birthYear = birthYear;
    }
}
