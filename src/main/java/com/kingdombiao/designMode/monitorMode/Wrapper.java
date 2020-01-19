package com.kingdombiao.designMode.monitorMode;

/**
 * 描述:
 * 事件源：事件发生的地点
 *
 * @author biao
 * @create 2020-01-17 14:48
 */
public class Wrapper {
    private Object param;
    private Work work;
    private Listener listener;

    public Object getParam() {
        return param;
    }

    public void setParam(Object param) {
        this.param = param;
    }

    public Work getWork() {
        return work;
    }

    public void setWork(Work work) {
        this.work = work;
    }

    public Listener getListener() {
        return listener;
    }

    public void addListener(Listener listener) {
        this.listener = listener;
    }
}
