package com.kingdombiao.lock;

import org.I0Itec.zkclient.ZkClient;

/**
 * 描述:
 * ${DESCRIPTION}
 *
 * @author biao
 * @create 2019-09-25 13:50
 */
public abstract class ZkAbstractLock extends AbstractLock {

    private static final String ZK_CONNECTSTRING="127.0.0.1:2181";

    protected ZkClient zkClient=new ZkClient(ZK_CONNECTSTRING);

    protected static final String PATH="/lock";

    protected static final String PATH2="/lockPlus";


}
