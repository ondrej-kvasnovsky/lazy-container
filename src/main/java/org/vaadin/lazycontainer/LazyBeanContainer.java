package org.vaadin.lazycontainer;

import com.vaadin.data.util.BeanContainer;
import com.vaadin.data.util.BeanItem;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Ondrej Kvasnovsky
 */
public class LazyBeanContainer extends BeanContainer {

    private SearchCriteria criteria;
    private DAO dao;

    List<OrderByColumn> orderByColumns = new ArrayList<OrderByColumn>();

    public LazyBeanContainer(Class type, DAO dao, SearchCriteria criteria) {
        super(type);
        this.criteria = criteria;
        this.dao = dao;
    }

    @Override
    public int size() {
        if (criteria.getLastCount() == 0 || criteria.isDirty()) {
            int count = dao.count(criteria);
            criteria.setDirty(false);
            criteria.setLastCount(count);
        }
        return criteria.getLastCount();
    }

    @Override
    public BeanItem getItem(Object itemId) {
        return new BeanItem(itemId);
    }

    @Override
    public List<?> getItemIds(int startIndex, int numberOfIds) {
        List<?> items = dao.find(criteria, startIndex, numberOfIds, orderByColumns);
        return items;
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
}
