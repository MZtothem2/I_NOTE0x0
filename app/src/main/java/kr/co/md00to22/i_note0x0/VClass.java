package kr.co.md00to22.i_note0x0;

public class VClass {
    private int class_code;
    private String class_name;
    private int class_year;
    private int class_age;

    private int[] classTcodes;

    public VClass(String className, int classYear, int age, int class_code, String classTeachers) {
        this.class_name = className;
        this.class_year = classYear;
        this.class_age = age;
        this.class_code=class_code;


        //DB에서 받아온 담임교사코드는 구분자로 연결되어있음. - 분리하기
        classTcodes=splitTcodes(classTeachers);
    }


    public VClass(String test, String testClassName, int testClassYear, int testAge, int testclassTeacher, int class_code) {
        this.class_name = testClassName;
        this.class_year = testClassYear;
        this.class_age = testAge;
        this.class_code=class_code;
//
//        this.classChildren =new ArrayList<>();
//
//        this.classTeachers = new ArrayList<>();
//        classTeachers.add(testclassTeacher);
    }

    private int[] splitTcodes(String classTeachers){
        if (classTeachers==null || classTeachers.equals("") ) return null;

        if (classTeachers.contains(";")) {
            String[] teachers = classTeachers.split(";");
            int[] classTCodes = new int[teachers.length];


            for (int i = 0; i < teachers.length; i++) {
                classTCodes[i] = Integer.parseInt(teachers[i]);
            }
            return classTCodes;
        }else return null;
    }

    public int getClass_code() {
        return class_code;
    }

    public void setClass_code(int index) {
//        //67(c)+연령+DB인덱스
//        String tmpC="67"+class_age+""+index;
//        this.class_code=Long.parseLong(tmpC);
        this.class_code=index;
    }

    public String getClass_name() {
        return class_name;
    }

    public void setClass_name(String class_name) {
        this.class_name = class_name;
    }

    public int getClass_year() {
        return class_year;
    }

    public void setClass_year(int class_year) {
        this.class_year = class_year;
    }

    public int getClass_age() {
        return class_age;
    }

    public void setClass_age(int class_age) {
        this.class_age = class_age;
    }

    public int[] getClassTcodes() {
        return classTcodes;
    }

    public void setClassTcodes(int[] classTcodes) {
        this.classTcodes = classTcodes;
    }

    //
//    public ArrayList<Integer> getClassTeachers() {
//        return classTeachers;
//    }
//
//    public void setClassTeachers(ArrayList<Integer> classTeachers) {
//        this.classTeachers = classTeachers;
//    }
//
//    public ArrayList<Integer> getClassChildren() {
//        return classChildren;
//    }
//
//    public void setClassChildren(ArrayList<Integer> classChildren) {
//        this.classChildren = classChildren;
//    }
//
//    public void addChildToClass(Integer childCode){
//        classChildren.add(childCode);
//    }
}
