package case_study9;

public class TestCourse {
    public static void main(String[] args) {
    Course course1 = new Course("Data Structures");
    Course course2 = new Course("Database Systems");

    course1.addStudents("Peter Jones");
    course1.addStudents("Kim Smith");
    course1.addStudents("Anne Kennedy");

    course2.addStudents("Peter Jones");
    course2.addStudents("Steve Smith");

        System.out.println("Number of students in course1: "
        + course1.getNumberOfStudents());
        String[] students = course1.getStudents();
        for (int i = 0; i < course1.getNumberOfStudents(); i++) {
            System.out.print(students[i] + ", ");
        }

        System.out.println();
        System.out.println("Number of students in course2: "
                + course2.getNumberOfStudents());
    }
}
