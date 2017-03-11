package com.blibli.future.detroit.model.request;

import java.util.List;

public class SimpleListRequest<T> {
    private List<T> list;

    public SimpleListRequest() {
    }

    public SimpleListRequest(List<T> list) {
        this.list = list;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }
}
