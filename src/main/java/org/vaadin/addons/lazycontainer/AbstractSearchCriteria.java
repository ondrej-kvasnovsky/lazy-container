package org.vaadin.addons.lazycontainer;

/**
 * @author Ondrej Kvasnovsky
 */
public class AbstractSearchCriteria implements SearchCriteria {

    private int lastCount;
    private boolean dirty;
    private String filter;

    @Override
    public int getLastCount() {
        return lastCount;
    }

    @Override
    public void setLastCount(int lastCount) {
        this.lastCount = lastCount;
    }

    @Override
    public boolean isDirty() {
        return dirty;
    }

    @Override
    public void setDirty(boolean dirty) {
        this.dirty = dirty;
    }

    @Override
    public String getFilter() {
        return filter;
    }

    @Override
    public void setFilter(String filter) {
        this.filter = filter;
    }
}
