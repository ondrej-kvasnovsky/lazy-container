package org.vaadin.addons.lazycontainer;

/**
 * Should be implemented by search criteria that are usd by the lazy container.
 *
 * @author Ondrej Kvasnovsky
 */
public interface SearchCriteria {

    /**
     * Returns the last cound of fetched entities from database (lastCount is changed always when search criteria are
     * changed).
     *
     * @return last count from database
     */
    public int getLastCount();

    /**
     * Sets the last count of entities fetched from database (so we are not touching database whenever we need to know
     * the size of entities there).
     *
     * @param lastCount
     */
    public void setLastCount(int lastCount);

    /**
     * Tels whether the criteria are changed. If changed we need to fetch data again and therefore true is returned.
     *
     * @return true if dirty, otherwise false
     */
    public boolean isDirty();

    /**
     * We set dirty flag to true when the search criteria are changed (so reload of data from database can happen).
     *
     * @param dirty dirty flag
     */
    public void setDirty(boolean dirty);

    public String getFilter();

    public void setFilter(String filter);
}
