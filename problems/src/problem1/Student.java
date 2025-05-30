package problem1;

public class Student {
    private String name;
    private int age;
    private String studentID;

    public Student(String name, int age, String studentID) {
        this.name = name;
        this.age = age;
        this.studentID = studentID;
    }

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

    public String getStudentID() {
        return studentID;
    }

    public void setStudentID(String studentID) {
        this.studentID = studentID;
    }

    public String introduce() {
        return "My name is " + name + ", age " + age + ", student ID " + studentID;
    }

    public static void main(String[] args) {
        Student student = new Student("Jenira", 18, "2462710034L");

        System.out.println("# Student Information #");
        System.out.println("Name: " + student.getName() + "\nAge: " + student.getAge() + "\nStudent ID: " + student.getStudentID());

        student.setName("Aza");
        student.setAge(19);
        student.setStudentID("2462710002L");

        System.out.println(student.introduce());
    }
}
