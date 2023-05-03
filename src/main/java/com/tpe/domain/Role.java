package com.tpe.domain;

import com.tpe.domain.enums.UserRole;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tbl_role")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Enumerated: name'e enum'dan atama yapilsin
    @Enumerated(EnumType.STRING) // db'ye index(0, 1) degil string olarak kaydet
    @Column(length = 30, nullable = false)
    private UserRole name;

    @Override
    public String toString() {
        return "Role{" +
                ", name=" + name +
                '}';
    }
}
