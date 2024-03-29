package guru.sfg.brewery.domain.security;

import guru.sfg.brewery.domain.BaseEntity;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
public class PermissionsEntity extends BaseEntity {

    private String permission;

    @ManyToMany(mappedBy = "permission")
    private Set<Role> roles = new HashSet<>();

    public PermissionsEntity() {
    }

    public PermissionsEntity(String permission) {
        this.permission = permission;
    }

    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PermissionsEntity permissionsEntity = (PermissionsEntity) o;
        return Objects.equals(permission, permissionsEntity.permission);
    }

    @Override
    public int hashCode() {
        return Objects.hash(permission);
    }
}
