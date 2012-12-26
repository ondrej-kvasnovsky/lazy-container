package org.vaadin.addons.lazycontainer;

/**
 * @author Ondrej Kvasnovsky
 */
public class AbstractSearchCriteria implements SearchCriteria {

    private int lastCount;
    private boolean dirty;

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
}
