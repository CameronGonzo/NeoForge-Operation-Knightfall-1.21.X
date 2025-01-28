package net.uhhitscam.starwars.util;

public class GuiUtil {
    //Thanks to iamkaf vvv
    public static String toReadableSentence(String snakeCase) {
        if (snakeCase == null || snakeCase.isEmpty()) return "";
        StringBuilder result = new StringBuilder();
        for (String word : snakeCase.split("_")) {
            result.append(Character.toUpperCase(word.charAt(0)))
                    .append(word.substring(1).toLowerCase())
                    .append(" ");
        }
        return result.toString().trim();
    }


}
