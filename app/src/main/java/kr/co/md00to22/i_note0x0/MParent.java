package kr.co.md00to22.i_note0x0;

public class MParent extends Member {
    //교사
//    private String id;
//    private int mIdentifyCode;
//    private String password;
//    private String name;
//    private int memberGrade;
//    private int organization_code;
//    String deviceToken;
//    int isApproved;

    private String childrenCode;
    private int[] childrenCodeArray;


    public MParent(String mail, String password, String name, int memberGrade, int organizationCode, int mIdentifyCode, int childCode) {
        this.setId(mail);
        this.setPassword(password);
        this.setName(name);
        this.setMemberGrade(memberGrade);
        this.setOrganization_code(organizationCode);
        setmIdentifyCode(mIdentifyCode);


        if (childCode==0) this.childrenCode="";
        else this.childrenCode=childCode+";";
        splitChildrenCodes();

    }

    public MParent(String mail, String password, String name, int memberGrade, int organizationCode,  int mIdentifyCode, String childCode) {
        this.setId(mail);
        this.setPassword(password);
        this.setName(name);
        this.setMemberGrade(memberGrade);
        this.setOrganization_code(organizationCode);
        setmIdentifyCode(mIdentifyCode);


        if (childCode.equals("")) this.childrenCode="";
        else this.childrenCode=childCode+";";
        splitChildrenCodes();

    }

    private void splitChildrenCodes(){
        if (!childrenCode.contains(";")) return;

      String[] arr=childrenCode.split(";");
      childrenCodeArray=new int[arr.length-1];

      for (int i=0; i<arr.length-1; i++){
          childrenCodeArray[i]=Integer.parseInt(arr[i]);
      }

    }

    public int addChildrenCode(int childCode) {
        if (this.childrenCode.contains(childrenCode)) return G.FAIL;
        else {
            this.childrenCode += childCode+";";
            splitChildrenCodes();
            return G.SUCCESS;
        }
    }

    public int deleteChildrenCode(int childcode) {
        if (!this.childrenCode.contains(childcode+"")) return G.FAIL;
        else {
            this.childrenCode.replaceAll(childcode+"", "");
            splitChildrenCodes();
            return G.SUCCESS;
        }
    }


    public String getChildrenCode() {
        return childrenCode;
    }

    public void setChildrenCode(String childrenCode) {
        this.childrenCode = childrenCode;
    }

    public int[] getChildrenCodeArray() {
        return childrenCodeArray;
    }

    public void setChildrenCodeArray(int[] childrenCodeArray) {
        this.childrenCodeArray = childrenCodeArray;
    }
}