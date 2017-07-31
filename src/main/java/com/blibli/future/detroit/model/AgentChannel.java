package com.blibli.future.detroit.model;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.io.Serializable;
import java.util.List;

@Entity
@SQLDelete(sql =
   "UPDATE agent_channel " +
   "SET deleted = true " +
   "WHERE id = ?")
@Where(clause = "deleted=false")
public class AgentChannel extends BaseModel implements Serializable {
    @Id
    @GeneratedValue
    private Long id;
    private String name;
    @OneToMany
    private List<User> users;

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

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AgentChannel agentChannel = (AgentChannel) o;

        if (id != null ? !id.equals(agentChannel.id) : agentChannel.id != null) return false;
        if (name != null ? !name.equals(agentChannel.name) : agentChannel.name != null) return false;
        return users != null ? users.equals(agentChannel.users) : agentChannel.users == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (users != null ? users.hashCode() : 0);
        return result;
    }
}
