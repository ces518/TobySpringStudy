package me.june;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Member {

    @Id
    Long id;

    @Column(length = 100)
    String name;

    @Column(nullable = false)
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
