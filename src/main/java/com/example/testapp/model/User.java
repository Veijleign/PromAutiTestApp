package com.example.testapp.model;

import com.example.testapp.model.enums.Permission;
import com.example.testapp.model.enums.Role;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Table(name = "users")
public class User implements UserDetails {
    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private String email;
    private String password;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = false)
    @JoinColumn(name = "table_user_id")
    @JsonIgnore
    @Builder.Default
    private List<KanbanTable> kanbanTables = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = false)
    @JoinColumn(name = "user_task_id")
    @JsonIgnore
    @Builder.Default
    private List<TableTask> tableTasks = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToMany(mappedBy = "user")
    @ToString.Exclude
    @JsonIgnore
    private List<Token> tokens;

    public boolean hasPermissions(Permission permission) {
        return role.getPermissions().contains(permission);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return role.getAuthorities();
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}