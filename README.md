Lazy containers for Vaadin 7
============================

Maven Dependency

```xml
<dependency>
   <groupId>org.vaadin.addons</groupId>
   <artifactId>lazy-container</artifactId>
   <version>0.0.1</version>
</dependency>

<repository>
   <id>qiiip-repo</id>
   <url>http://qiiip.org/mavenRepo</url>
</repository>
```

How to fetch data from database into a domain object and then display it in a table? Let's explain how it works in the following example.

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

Also we need to make the search criteria that will represent e.g. user's input from user interface. This class needs to implement SearchCriteria because we need info about last count and whether the search criteria are dirty. All that is needed because of performance optimalization (that way we avoid additional calls to the database).
```java
public class UserSearchCriteria implements SearchCriteria {

    private int lastCount;
    private boolean dirty;

    private String searchText;

    // setters and getters for all the fields
}
```

The last step is to create new LazyBeanContainer and set that to the table.
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




