package com.exchange.buy.sell.market.domain;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import java.util.Collection;

/**
 * Created by oleht on 13.07.2018
 */
@Entity
public class Role extends AbstractEntity<Byte>{

    @ManyToMany(mappedBy = "roles")
    private Collection<UserEntity> users;

    @ManyToMany
    @JoinTable(name = "roles_privileges", joinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "privilege_id", referencedColumnName = "id"))
    private Collection<Privilege> privileges;

    private String name;

    public Role() {
        super();
    }

    public Role(final String name) {
        super();
        this.name = name;
    }

    public Collection<UserEntity> getUsers() {
        return users;
    }

    public void setUsers(Collection<UserEntity> users) {
        this.users = users;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Collection<Privilege> getPrivileges() {
        return privileges;
    }

    public void setPrivileges(Collection<Privilege> privileges) {
        this.privileges = privileges;
    }
}
