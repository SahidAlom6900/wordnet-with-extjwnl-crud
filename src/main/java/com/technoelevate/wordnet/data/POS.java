package com.technoelevate.wordnet.data;

import net.sf.extjwnl.util.ResourceBundleSet;

public enum POS {

    CUSTOM_POS(1, "c", "custom_POS");


    /**
     * Return the <code>POS</code> whose key matches <var>key</var>,
     * or null if the key does not match any POS.
     *
     * @param key key for POS
     * @return POS
     */
    public static POS getPOSForKey(String key) {
        if (CUSTOM_POS.getKey().equals(key)) {
            return POS.CUSTOM_POS;
        }
        return null;
    }

    /**
     * Return the <code>POS</code> whose key matches <var>key</var>,
     * or null if the key does not match any POS.
     *
     * @param key POS key
     * @return POS
     */
    public static POS getPOSForKey(char key) {
        if (key == CUSTOM_POS.getKey().charAt(0)) {
            return POS.CUSTOM_POS;
        }
        return null;
    }

    /**
     * Return the <code>POS</code> whose id matches <var>id</var>,
     * or null if the id does not match any POS.
     *
     * @param id id for POS
     * @return POS
     */
    public static POS getPOSForId(int id) {
        if (CUSTOM_POS.getId() == id) {
            return POS.CUSTOM_POS;
        }
        return null;
    }


    private final transient String label;
    private final transient int id;
    private final transient String key;

    POS(int id, String key, String label) {
        this.id = id;
        this.key = key;
        this.label = label;
    }

    @Override
    public String toString() {
        return ResourceBundleSet.insertParams("[POS: {0}]", new String[] {getLabel()});
    }

    /**
     * Return a label intended for textual presentation.
     *
     * @return a label intended for textual presentation
     */
    public String getLabel() {
        return label;
    }

    /**
     * Returns the key for this POS.
     *
     * @return key for this POS
     */
    public String getKey() {
        return key;
    }

    /**
     * Returns the id for this POS.
     *
     * @return id for this POS
     */
    public int getId() {
        return id;
    }
}