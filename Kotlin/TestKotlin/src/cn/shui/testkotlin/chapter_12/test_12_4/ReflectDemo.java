package cn.shui.testkotlin.chapter_12.test_12_4;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

public class ReflectDemo {
    public static void main(String[] args) {
        StudentServiceImpl studentService = new StudentServiceImpl();
        studentService.save(new Student("Bob", 20));
        studentService.findStudents("Jack", 20);

        // 反射API调用示例
        final Class<? extends StudentServiceImpl> studentServiceClass = studentService.getClass();
        Class<?>[] classes = studentServiceClass.getDeclaredClasses();
        Annotation[] annotations = studentServiceClass.getAnnotations();
        ClassLoader classLoader = studentServiceClass.getClassLoader(); // Returns the class loader for the class
        Field[] fields = studentServiceClass.getDeclaredFields(); // 获取类成员变量
        Method[] methods = studentServiceClass.getDeclaredMethods(); // 获取类成员方法
        try {
            for (Method method : methods) {
                System.out.println(method.getName());
            }

            String name1 = methods[0].getName(); // findStudents
            System.out.println(name1);
            List<Student> list = (List<Student>) methods[0].invoke(studentService, "Jack", 20);
            System.out.println(list);

            String name2 = methods[1].getName(); // save
            System.out.println(name2);
            int ret = (int) methods[1].invoke(studentService, new Student("Jack", 20));
            System.out.println(ret);
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}

interface StudentService<T> {
    List<T> findStudents(String name, Integer age);
}

abstract class BaseService<T> {
    abstract int save(T t);
}

class StudentServiceImpl extends BaseService<Student> implements StudentService<Student> {

    @Override
    public List<Student> findStudents(String name, Integer age) {
        return Arrays.asList(new Student("Jack", 20), new Student("Rose", 20));
    }

    @Override
    int save(Student student) {
        return 0;
    }
}

class Student {
    String name;
    Integer age;

    public Student(String name, Integer age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "Student{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}