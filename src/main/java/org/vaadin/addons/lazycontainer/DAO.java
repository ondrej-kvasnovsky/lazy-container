package org.vaadin.addons.lazycontainer;

import java.util.List;

/**
 * @author Ondrej Kvasnovsky
 */
public interface DAO <MODEL> {

    int count(SearchCriteria criteria);
    List<MODEL> find(SearchCriteria criteria, int startIndex, int offset, List<OrderByColumn> columns);

}
