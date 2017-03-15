package com.github.mkorman9.entity;

import lombok.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "CAT")
//@JsonIgnoreProperties({"group"})
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE, region = "cats")
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(of = {"name"})
@ToString
public class Cat implements Serializable {
    @Id
    @GeneratedValue
    @Column(name = "ID", unique = true, nullable = false)
    private Long id;

    @Column(name = "ROLE_NAME", nullable = false)
    private String roleName;

    @Column(name = "NAME", nullable = false)
    private String name;

    @Column(name = "DUELS_WON")
    private Integer duelsWon;

    @ManyToOne
    @JoinColumn(name = "GROUP_ID")
    private CatsGroup group;

    public Cat(String roleName, String name, Integer duelsWon) {
        this.roleName = roleName;
        this.name = name;
        this.duelsWon = duelsWon;
    }
}
