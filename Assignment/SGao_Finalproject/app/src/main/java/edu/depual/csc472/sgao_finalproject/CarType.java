package edu.depual.csc472.sgao_finalproject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 * <p/>
 * TODO: Replace all uses of this class before publishing your app.
 */
public class CarType {

    public static List<Type> ITEMS = new ArrayList<Type>();

    public static Map<String, Type> ITEM_MAP = new HashMap<String, Type>();

    static {
        int i = 0;
        for (Car.Type t : Car.Type.values()) {
            addItem(new Type(i + "", t.toString()));
            i++;
        }
    }

    private static void addItem(Type item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.id, item);
    }

    public static class Type {
        public String id;
        public String content;

        public Type(String id, String content) {
            this.id = id;
            this.content = content;
        }

        @Override
        public String toString() {
            return content;
        }
    }
}
