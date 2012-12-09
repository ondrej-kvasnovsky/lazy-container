package org.vaadin.addons.lazycontainer;

/**
 * @author Ondrej Kvasnovsky
 */
public class OrderByColumn {

    public enum Type {
        ASC, DESC
    }

    private String name;
    private Type type;

    public OrderByColumn(String name, Type type) {
        this.name = name;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }
}
