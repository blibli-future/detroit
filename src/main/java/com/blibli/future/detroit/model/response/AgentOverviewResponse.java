package com.blibli.future.detroit.model.response;

import com.blibli.future.detroit.model.Review;
import com.blibli.future.detroit.model.User;

import java.util.HashMap;
import java.util.List;

public class AgentOverviewResponse {
    private String name;
    private HashMap<String, Integer> data;

    public AgentOverviewResponse(
        User agent,
        HashMap<String, Integer> data
    ) {
        this.name = agent.getFullname();
        this.data = data;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public HashMap<String, Integer> getData()


    {
        return data;
    }

    public void setData(HashMap<String, Integer> data) {
        this.data = data;
    }
}
