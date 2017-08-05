package com.blibli.future.detroit.model;

import com.blibli.future.detroit.model.enums.ScoreType;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.io.Serializable;

@Entity
@SQLDelete(sql =
    "UPDATE score_summary " +
    "SET deleted = true " +
    "WHERE id = ?")
@Where(clause = "deleted=false")
public class ScoreSummary extends BaseModel implements Serializable{
    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private Float score;
    private ScoreType scoreType;
    private Long fkId;
    @ManyToOne
    @Where(clause = "deleted=false")
    private CutOffHistory cutOffHistory;
    @ManyToOne
    @Where(clause = "deleted=false")
    private User agent;

    public ScoreSummary() {

    }

    public ScoreSummary(String name, Float score, ScoreType scoreType, Long fkId, CutOffHistory cutOffHistory, User agent) {
        this.name = name;
        this.score = score;
        this.scoreType = scoreType;
        this.fkId = fkId;
        this.cutOffHistory = cutOffHistory;
        this.agent = agent;
    }

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

    public Float getScore() {
        return score;
    }

    public void setScore(Float score) {
        this.score = score;
    }

    public ScoreType getScoreType() {
        return scoreType;
    }

    public void setScoreType(ScoreType scoreType) {
        this.scoreType = scoreType;
    }

    public Long getFkId() {
        return fkId;
    }

    public void setFkId(Long fkId) {
        this.fkId = fkId;
    }

    public CutOffHistory getCutOffHistory() {
        return cutOffHistory;
    }

    public void setCutOffHistory(CutOffHistory cutOffHistory) {
        this.cutOffHistory = cutOffHistory;
    }

    public User getAgent() {
        return agent;
    }

    public void setAgent(User agent) {
        this.agent = agent;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ScoreSummary that = (ScoreSummary) o;

        if (!id.equals(that.id)) return false;
        if (!name.equals(that.name)) return false;
        if (!score.equals(that.score)) return false;
        if (scoreType != that.scoreType) return false;
        if (!cutOffHistory.equals(that.cutOffHistory)) return false;
        return agent != null ? agent.equals(that.agent) : that.agent == null;
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + name.hashCode();
        result = 31 * result + score.hashCode();
        result = 31 * result + scoreType.hashCode();
        result = 31 * result + cutOffHistory.hashCode();
        result = 31 * result + (agent != null ? agent.hashCode() : 0);
        return result;
    }
}
