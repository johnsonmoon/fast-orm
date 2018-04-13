package com.github.johnsonmoon.fastorm.core.util;

import com.github.johnsonmoon.fastorm.core.common.DatabaseType;

/**
 * Created by johnsonmoon at 2018/4/8 16:56.
 */
public class StringUtils {
    /**
     * Get name with `` format (Avoid key words in database)
     * <p>
     * <pre>
     *     example:
     *      name --> `name`
     *      name as n --> `name` as n
     *      student s --> `student` s
     *      COUNT(*) AS count --> COUNT(*) AS count [keep it]
     * </pre>
     *
     * @param name name
     * @return `name`
     */
    public static String getSureName(String name) {
        if (name.contains("(") && name.contains(")"))
            return name;
        if (name.contains(" ")) {
            String[] array = name.split(" ");
            if (array.length > 0) {
                return name.replace(array[0], String.format(DatabaseType.getKeywordAvoid(), array[0]));
            }
        }
        return String.format(DatabaseType.getKeywordAvoid(), name);
    }

    /**
     * Get name with ``(``) format (Avoid key words in database)
     * <pre>
     *     example:
     *      student(name) --> `student`(`name`)
     * </pre>
     *
     * @param name name
     * @return split sure name
     */
    public static String getSplitSureName(String name) {
        if (!name.contains("(") || !name.contains(")")) {
            return name;
        }
        String table = name.substring(0, name.indexOf("(")).trim();
        String field = name.substring(name.indexOf("(") + 1, name.lastIndexOf(")")).trim();
        return String.format(DatabaseType.getKeywordAvoid(), table) + "(" + String.format(DatabaseType.getKeywordAvoid(), field) + ")";
    }

    /**
     * Target str contains any search str.
     *
     * @param target target str
     * @param search search str array
     * @return true/false
     */
    public static boolean containsAny(String target, String... search) {
        for (String s : search) {
            if (target.contains(s)) {
                return true;
            }
        }
        return false;
    }
}
