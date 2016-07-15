package com.github.mkorman9.model;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "CAT")
//@JsonIgnoreProperties({"group"})
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE, region = "cats")
public class Cat implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ID", unique = true, nullable = false)
    private Long id;

    @Column(name = "ROLE_NAME", nullable = false)
    private String roleName;

    @Column(name = "NAME", nullable = false)
    private String name;

    @Column(name = "DUELS_WON")
    private Integer duelsWon;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "GROUP_ID")
    private CatsGroup group;

    public Cat() {
    }

    public Cat(String roleName, String name, Integer duelsWon) {
        this.roleName = roleName;
        this.name = name;
        this.duelsWon = duelsWon;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getDuelsWon() {
        return duelsWon;
    }

    public void setDuelsWon(Integer duelsWon) {
        this.duelsWon = duelsWon;
    }

    public CatsGroup getGroup() {
        return group;
    }

    public void setGroup(CatsGroup group) {
        this.group = group;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cat cat = (Cat) o;
        return Objects.equal(id, cat.id) &&
                Objects.equal(roleName, cat.roleName) &&
                Objects.equal(name, cat.name) &&
                Objects.equal(duelsWon, cat.duelsWon);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id, roleName, name, duelsWon);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("id", id)
                .add("roleName", roleName)
                .add("name", name)
                .add("duelsWon", duelsWon)
                .toString();
    }
}
