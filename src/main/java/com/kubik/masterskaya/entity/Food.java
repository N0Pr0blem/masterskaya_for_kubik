package com.kubik.masterskaya.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity

@Table(name = "foods")
public class Food {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    private Integer price;
    private String url;
    private String time;
    private String type;
    private Integer kcal;

    @Override
    public String toString() {
        return "Food{" +
                "id=" + id +
                ", price=" + price +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", url='" + url + '\'' +
                ", time='" + time + '\'' +
                ", type='" + type + '\'' +
                ", kcal=" + kcal +
                '}';
    }
}

