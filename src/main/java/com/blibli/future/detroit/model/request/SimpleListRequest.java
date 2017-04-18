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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SimpleListRequest<?> that = (SimpleListRequest<?>) o;

        return list.containsAll(that.list);
    }

    @Override
    public int hashCode() {
        return list.hashCode();
    }
}
