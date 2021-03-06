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
    @OneToMany(mappedBy = "agentChannel")
    @Where(clause = "deleted=false")
    private List<User> users;
    @ManyToOne
    @Where(clause = "deleted=false")
    private AgentPosition agentPosition;
    @OneToMany(mappedBy = "agentChannel")
    @Where(clause = "deleted=false")
    private List<Parameter> parameters;

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

    public List<Parameter> getParameters() {
        return parameters;
    }

    public void setParameters(List<Parameter> parameters) {
        this.parameters = parameters;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AgentChannel that = (AgentChannel) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (users != null ? !users.equals(that.users) : that.users != null) return false;
        if (agentPosition != null ? !agentPosition.equals(that.agentPosition) : that.agentPosition != null)
            return false;
        return parameters != null ? parameters.equals(that.parameters) : that.parameters == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (users != null ? users.hashCode() : 0);
        result = 31 * result + (agentPosition != null ? agentPosition.hashCode() : 0);
        result = 31 * result + (parameters != null ? parameters.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "AgentChannel{" +
            "id=" + id +
            ", name='" + name + '\'' +
            ", users=" + users +
            ", agentPosition=" + agentPosition +
//            ", parameters=" + parameters +
            '}';
    }
}
