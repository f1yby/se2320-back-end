package com.example.back_end.entity;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "user", schema = "zlm")
public class UserEntity {
    @Basic
    @Column(name = "name")
    private String name;
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private int id;
    @Basic
    @Column(name = "password")
    private String password;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<FavoriteEntity> favoriteHouses;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<FavoriteEntity> getFavoriteHouses() {
        return favoriteHouses;
    }

    public void setFavoriteHouses(List<FavoriteEntity> favoriteHouses) {
        this.favoriteHouses = favoriteHouses;
    }
}
