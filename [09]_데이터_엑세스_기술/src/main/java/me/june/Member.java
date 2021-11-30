package me.june;

public class Member {

    Long id;
    String name;
    double point;

    public Member() {
    }

    public Member(Long id, String name, double point) {
        this.id = id;
        this.name = name;
        this.point = point;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPoint() {
        return point;
    }

    public void setPoint(double point) {
        this.point = point;
    }
}
