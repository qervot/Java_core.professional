package lesson7.DbAnnotation;

@XTable(tableName = "Students_2")
public class Student {
    @XField
    String name;

    @XField
    String sex;

    @XField
    String grp;

    @XField
    int cource;

    @XField
    int score;

    @XField
    String surname;
}
