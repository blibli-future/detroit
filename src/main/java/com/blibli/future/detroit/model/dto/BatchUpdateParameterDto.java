package com.blibli.future.detroit.model.dto;

import com.blibli.future.detroit.model.request.NewParameterRequest;

import java.util.List;

public class BatchUpdateParameterDto {
    Long channelId;
    List<NewParameterRequest> parameterList;

    public Long getChannelId() {
        return channelId;
    }

    public void setChannelId(Long channelId) {
        this.channelId = channelId;
    }

    public List<NewParameterRequest> getParameterList() {
        return parameterList;
    }

    public void setParameterList(List<NewParameterRequest> parameterList) {
        this.parameterList = parameterList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BatchUpdateParameterDto that = (BatchUpdateParameterDto) o;

        if (channelId != null ? !channelId.equals(that.channelId) : that.channelId != null) return false;
        return parameterList != null ? parameterList.equals(that.parameterList) : that.parameterList == null;
    }

    @Override
    public int hashCode() {
        int result = channelId != null ? channelId.hashCode() : 0;
        result = 31 * result + (parameterList != null ? parameterList.hashCode() : 0);
        return result;
    }
}
