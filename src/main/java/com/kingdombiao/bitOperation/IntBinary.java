package com.kingdombiao.bitOperation;

/**
 * 位运算
 */
public class IntBinary {
    public static void main(String[] args) {

        System.out.println("the 4 is : "+Integer.toBinaryString(4));
        System.out.println("the 6 is : "+Integer.toBinaryString(6));

        //位与(与运算) & 即(1&1=1,1&0=0,0&0=0)  可以用来判断是否有当前权限
        System.out.println("the (4&6) is : "+Integer.toBinaryString(4&6));

        //位或(或运算) | 即(1|1=1,1|0=1,0|0=0)  可以用来增加该权限
        System.out.println("the (4|6) is : "+Integer.toBinaryString(4|6));

        //位非(非运算) ~ 即(~1=0,~0=1)  结合与运算可以删除该权限
        System.out.println("the ~4 is "+Integer.toBinaryString(~4));

        //位异或(异或运算) ^ 即(1^1=0 1^0=1 0^0=0)
        System.out.println("the (4^6) is "+Integer.toBinaryString(4^6));


        // <<有符号左移 >>有符号的右移  >>>无符号右移

        //取模的操作 a % (2^n) 等价于 a&(2^n-1)
        System.out.println("the 129 % 16 is "+(129%16)+ " or "+(129&(16-1)));
    }
}
