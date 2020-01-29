package com.kingdombiao.rpc.serializer;

public class KryoDemo {
    public static void main(String[] args) {
        Student student = new Student();
        student.setName("tom");
        student.setAge(20);

        byte[] serialize = KryoSerializerFactory.getSerializer(Student.class).serialize(student);

        Student deserialize = KryoSerializerFactory.getSerializer(Student.class).deserialize(serialize);

        System.out.println(deserialize);

    }
}

class Student{
    private String name;
    private int age;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "student{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}
