import java.util.ArrayList;
import java.util.Iterator;

public class MyFactory {
    private ArrayList<String> typeNameList = null;
    private ArrayList<UserType> userTypeList = null;

    public MyFactory() {
        userTypeList = new ArrayList<UserType>();
        FractionType fractionType = new FractionType();
        userTypeList.add(fractionType);
        IntType intType = new IntType();
        userTypeList.add(intType);
        typeNameList = new ArrayList<String>();
        typeNameList.add(fractionType.typeName());
    }

    public ArrayList<String> getTypeNameList(){
        return typeNameList;
    }

    public UserType getBuilderByName(String name){
        Iterator<UserType> userTypeIterator = userTypeList.iterator();
        while (userTypeIterator.hasNext()) {
            UserType userType = userTypeIterator.next();
            if(userType.typeName() == name) {
                return userType;
            }
        }
        return null;
    }

}
