package com.blibli.future.detroit.model;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
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
    @ManyToOne
    private AgentPosition agentPosition;

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

    public AgentPosition getAgentPosition() {
        return agentPosition;
    }

    public void setAgentPosition(AgentPosition agentPosition) {
        this.agentPosition = agentPosition;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AgentChannel that = (AgentChannel) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (users != null ? !users.equals(that.users) : that.users != null) return false;
        return agentPosition != null ? agentPosition.equals(that.agentPosition) : that.agentPosition == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (users != null ? users.hashCode() : 0);
        result = 31 * result + (agentPosition != null ? agentPosition.hashCode() : 0);
        return result;
    }
}
