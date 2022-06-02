package com.github.youssefagagg.springbootappsample.domin;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "`user`")
@Getter
@Setter
@ToString
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    private Long id;
    @NotBlank(message = "username cannot be blank")
    @Size(min = 4, max = 50, message="Username should be at least 4 and at most 50 character")
    @Column(length = 50, unique = true, nullable = false)
    private String username;
    @Email
    @NotBlank(message = "email cannot be blank")
    @Column(length = 254, unique = true, nullable = false)
    private String email;
    @JsonIgnore
    @ToString.Exclude
    @NotBlank(message = "password cannot be blank")
    private String password;
    @NotNull
    @Column(nullable = false)
    private boolean activated = false;
    @NotBlank(message = "first name cannot be blank")
    @Column(name = "first_name", length = 50)
    private String firstName;
    @NotBlank(message = "last name cannot be blank")
    @Column(name = "last_name", length = 50)
    private String lastName;
//    @JsonIgnore
    @ManyToMany
    @JoinTable(
            name = "`user_authority`",
            joinColumns = { @JoinColumn(name = "user_id", referencedColumnName = "id") },
            inverseJoinColumns = { @JoinColumn(name = "authority_name", referencedColumnName = "name") }
    )
    @ToString.Exclude
    private Set<Authority> authorities = new HashSet<>();


    @Column(name = "activation_key")
    @JsonIgnore
    private String activationKey;


    @Column(name = "reset_key")
    @JsonIgnore
    private String resetKey;



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        User user = (User) o;
        return id != null && Objects.equals(id, user.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
