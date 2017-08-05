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
    "UPDATE agent_position " +
    "SET deleted = true " +
    "WHERE id = ?")
@Where(clause = "deleted=false")
public class AgentPosition extends BaseModel implements Serializable {
    @Id
    @GeneratedValue
    private Long id;
    private String name;
    @OneToMany(mappedBy = "agent_position")
    @Where(clause = "deleted=false")
    private List<User> users;
    @OneToMany(mappedBy = "agent_position")
    @Where(clause = "deleted=false")
    private List<AgentChannel> agentChannels;

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

    public List<AgentChannel> getAgentChannels() {
        return agentChannels;
    }

    public void setAgentChannels(List<AgentChannel> agentChannels) {
        this.agentChannels = agentChannels;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AgentPosition that = (AgentPosition) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (users != null ? !users.equals(that.users) : that.users != null) return false;
        return agentChannels != null ? agentChannels.equals(that.agentChannels) : that.agentChannels == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (users != null ? users.hashCode() : 0);
        result = 31 * result + (agentChannels != null ? agentChannels.hashCode() : 0);
        return result;
    }
}
