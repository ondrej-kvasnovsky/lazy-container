# Lazy containers for Vaadin 7

Note: there is just one type of container at this moment. It is LazyBeanContainer that extends BeanContainer. You can create new request in case you need other type of container or just fork the project and create it + make a new pull request so other people can love you.

## Maven Dependency

```xml
<dependency>
   <groupId>org.vaadin.addons</groupId>
   <artifactId>lazy-container</artifactId>
   <version>0.0.3</version>
</dependency>

<repository>
   <id>qiiip-repo</id>
   <url>http://qiiip.org/mavenRepo</url>
</repository>
```

## Table with Lazy Container

Let's explain how to fetch data from database into a domain object and then display it in a table in the following lines.

Domain object will be represented by User class with two fields (firstName and surname).

```java
public class User {

    private String firstName;
    private String surname;

    // setters and getters for all the fields
}
```

Now we need access to the database. So we make some kind of DAO (Data Access Object) class that will do the dirty work (making e.g. SQL query and fetching data from DB).
```java
public class UserDAO implements DAO<User> {

    @Override
    public int count(SearchCriteria criteria) {
        int count = 10;
        // TODO: here we fetch count of rows from database (based on the search criteria)
        return count;
    }

    @Override
    public List<User> find(SearchCriteria criteria, int startIndex, int offset, List<OrderByColumn> columns) {
        List<User> res = new ArrayList();
        // TODO: here we fetch rows from database (based on startIndex, offset and search criteria)
        return res;
    }
}
```

Also we need to make the search criteria that will represent e.g. user's input from user interface. This class needs to extend AbstractSearchCriteria (or implement SearchCriteria) because we need info about last count and whether the search criteria are dirty.
All that is needed because of performance optimalization (that way we avoid additional calls to the database).
```java
public class UserSearchCriteria extends AbstractSearchCriteria {

    private String searchText;

    // setters and getters for all the fields
}
```

The last step is to create new LazyBeanContainer and set the newly created container to the table.
```java
public class MyVaadinUI extends UI {

    @Override
    protected void init(VaadinRequest request) {
        final VerticalLayout layout = new VerticalLayout();
        layout.setMargin(true);
        setContent(layout);

        Table table = new Table();
        table.setWidth("500px");
        LazyBeanContainer dataSource = new LazyBeanContainer(User.class, new UserDAO(), new UserSearchCriteria());
        table.setContainerDataSource(dataSource);
        layout.addComponent(table);
    }

}
```

## ComboBox with Lazy Container

Let's use the same domain classes from the previous example. Note that database calls will be started after we type 3 and more letters to the combo box field (this can be changed by using setMinFilterLength(int) method).

```java
public class MyVaadinUI extends UI {

    @Override
    protected void init(VaadinRequest request) {
        final VerticalLayout layout = new VerticalLayout();
        layout.setMargin(true);
        setContent(layout);

        ComboBox comboBox = new ComboBox("Combobox:");
        LazyBeanContainer dataSource = new LazyBeanContainer(User.class, new UserDAO(), new UserSearchCriteria());
        comboBox.setContainerDataSource(dataSource);
        comboBox.setItemCaptionPropertyId("firstName");
        layout.addComponent(comboBox);
    }

}
```





