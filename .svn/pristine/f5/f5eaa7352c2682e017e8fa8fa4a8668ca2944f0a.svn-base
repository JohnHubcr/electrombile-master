package com.zenchn.electrombile.eventBus;

/**
 * 作    者：wangr on 2017/2/28 22:41
 * 描    述：
 * 修订记录：
 */
public class EasyEvent<T> {

    private Integer tag;
    private T data;

    public EasyEvent(Integer tag) {
        this.tag = tag;
    }

    public EasyEvent(T data) {
        this.data = data;
    }

    public Integer getTag() {
        return tag;
    }

    public void setTag(Integer tag) {
        this.tag = tag;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "Event{" +
                "tag=" + tag +
                ", data=" + data.toString() +
                '}';
    }
}
