package Parser;

import java.util.*;

/**
 * @author: Louis Lee
 */

public class LanguageSetting {

    private String myLanguage;
    private ResourceBundle myResources;
    private Map<String, String> translationMap;

    public LanguageSetting(String language){
        myLanguage = language;
        myResources = ResourceBundle.getBundle(myLanguage);
        translationMap = convertResourceBundleToMap(myResources);
    }

    private static Map<String, String> convertResourceBundleToMap(ResourceBundle resource) {
        Map<String, String> map = new HashMap<>();
        Enumeration<String> keys = resource.getKeys();
        while (keys.hasMoreElements()) {
            String key = keys.nextElement();
            if(resource.getString(key).contains("|")){
                String[] splitString = resource.getString(key).split("\\|");
                map.put(splitString[0], key);
                map.put(splitString[1], key);
            }
            else{
                map.put(resource.getString(key), key);
            }
        }
        System.out.println(map);
        return map;
    }

    //TODO: What do we do if there's an error. We just return semi-translated list? This method should probably throw an error
    //TODO: must deal with user defined commands
    public String[] translateCommand(String[] listOfWords){
        String[] newList = new String[listOfWords.length];

        for (int i = 0; i < listOfWords.length; i++) {
            if(isNumeric(listOfWords[i])){
                newList[i] = listOfWords[i];
            }
            else{
                newList[i] = translationMap.get(listOfWords[i]);
            }
        }
        return newList;
    }

    public static boolean isNumeric(String strNum) {
        try {
            double d = Double.parseDouble(strNum);
        } catch (NumberFormatException | NullPointerException nfe) {
            return false;
        }
        return true;
    }

    public Map<String, String> makeReflectionMap() {
        ResourceBundle englishProperty = ResourceBundle.getBundle("English");
        Map<String, String> map = new HashMap<>();
        Enumeration<String> keys = englishProperty.getKeys();
        while (keys.hasMoreElements()) {
            String key = keys.nextElement();
            if (englishProperty.getString(key).contains("|")) {
                map.put(englishProperty.getString(key).split("\\|")[0], key);
                map.put(englishProperty.getString(key).split("\\|")[1], key);
            }

        }
        return map;

    }
}
