package org.vaadin.addons.lazycontainer;

import com.vaadin.data.util.BeanContainer;
import com.vaadin.data.util.BeanItem;
import com.vaadin.data.util.filter.SimpleStringFilter;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @author Ondrej Kvasnovsky
 */
public class LazyBeanContainer extends BeanContainer {

    private SearchCriteria criteria;
    private DAO dao;

    private List<OrderByColumn> orderByColumns = new ArrayList<OrderByColumn>();

    // min filter string length, after this length is exceeded database calls are allowed
    private int minFilterLength;

    public LazyBeanContainer(Class type, DAO dao, SearchCriteria criteria) {
        super(type);
        this.criteria = criteria;
        this.dao = dao;
        minFilterLength = 3;
    }

    @Override
    public int size() {
        filterStringToSearchCriteria();
        if (criteria.getLastCount() == 0 || criteria.isDirty()) {
            getCount();
        } else if (isFiltered() && criteria.getFilter() != null) {
            getCount();
        }
        return criteria.getLastCount();
    }

    private void getCount() {
        int count = dao.count(criteria);
        criteria.setDirty(false);
        criteria.setLastCount(count);
    }

    @Override
    public BeanItem getItem(Object itemId) {
        return new BeanItem(itemId);
    }

    @Override
    public List<?> getItemIds(int startIndex, int numberOfIds) {
        filterStringToSearchCriteria();
        List<?> items = null;
        if (isFiltered() && criteria.getFilter() != null) {
            items = findItems(startIndex, numberOfIds);
            criteria.setFilter(null);
        } else if (!isFiltered()) {
            items = findItems(startIndex, numberOfIds);
        }
        return items;
    }

    private List<?> findItems(int startIndex, int numberOfIds) {
        List<?> items;
        items = dao.find(criteria, startIndex, numberOfIds, orderByColumns);
        return items;
    }

    private void filterStringToSearchCriteria() {
        if (isFiltered()) {
            Set<Filter> filters = getFilters();
            for (Filter filter : filters) {
                if (filter instanceof SimpleStringFilter) {
                    SimpleStringFilter stringFilter = (SimpleStringFilter) filter;
                    String filterString = stringFilter.getFilterString();
                    if (filterString.length() > minFilterLength) {
                        criteria.setFilter(filterString);
                    } else {
                        criteria.setFilter(null);
                    }
                }
            }
        }
    }

    @Override
    public void sort(Object[] propertyIds, boolean[] ascending) {
        orderByColumns.clear();
        for (int i = 0; i < propertyIds.length; i++) {
            Object propertyId = propertyIds[i];
            OrderByColumn.Type type = ascending[i] ? OrderByColumn.Type.ASC : OrderByColumn.Type.DESC;
            String name = propertyId.toString();
            orderByColumns.add(new OrderByColumn(name, type));
        }
    }

    @Override
    public boolean containsId(Object itemId) {
        // we need this because of value change listener (otherwise selected item event won't be fired)
        return true;
    }

    public int getMinFilterLength() {
        return minFilterLength;
    }

    public void setMinFilterLength(int minFilterLength) {
        this.minFilterLength = minFilterLength;
    }
}
