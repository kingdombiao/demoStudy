package com.kingdombiao.bitOperation;

/**
 * 权限操作
 */
public class Permission {

    // 目前拥有的权限
    private static int per;

    // 是否允许查询，二进制第1位，0表示否，1表示是
    public static final int ALLOW_SELECT = 1 << 0; // 0001  = 1

    // 是否允许新增，二进制第2位，0表示否，1表示是
    public static final int ALLOW_INSERT = 1 << 1; // 0010  = 2

    // 是否允许修改，二进制第3位，0表示否，1表示是
    public static final int ALLOW_UPDATE = 1 << 2; // 0100  =4

    // 是否允许删除，二进制第4位，0表示否，1表示是
    public static final int ALLOW_DELETE = 1 << 3; // 1000  = 8


    //增加权限（一个或多个）
    public static void addPer(int permission) {
        per |= permission;

    }

    //删除权限（一个或多个）
    public static void delPer(int permission) {
        per &= ~permission;
    }

    //判断是否拥有该权限
    public static boolean isAllow(int permission) {
        return (per & permission) == permission;
    }

    public static void main(String[] args) {

        //增加查询、插入、修改、删除权限
        Permission.addPer(ALLOW_SELECT|ALLOW_INSERT|ALLOW_UPDATE|ALLOW_DELETE);
        System.out.println("select = "+Permission.isAllow(ALLOW_SELECT));
        System.out.println("update = "+Permission.isAllow(ALLOW_UPDATE));
        System.out.println("insert = "+Permission.isAllow(ALLOW_INSERT));
        System.out.println("delete = "+Permission.isAllow(ALLOW_DELETE));

        //删除 插入、修改、删除 权限
        Permission.delPer(ALLOW_INSERT|ALLOW_UPDATE|ALLOW_DELETE);
        System.out.println("select = "+Permission.isAllow(ALLOW_SELECT));
        System.out.println("update = "+Permission.isAllow(ALLOW_UPDATE));
        System.out.println("insert = "+Permission.isAllow(ALLOW_INSERT));
        System.out.println("delete = "+Permission.isAllow(ALLOW_DELETE));
    }

}
