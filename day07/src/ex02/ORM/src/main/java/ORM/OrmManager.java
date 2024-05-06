package ORM;

import org.reflections.Reflections;

import java.lang.annotation.AnnotationFormatError;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.sql.SQLException;

public class OrmManager {
    private final Connection connection;
    public OrmManager(Connection connection) {
        this.connection = connection;
    }

    public void createTable() {
        Reflections reflections = new Reflections("Models");
        Set<Class<?>> classSet = reflections.getTypesAnnotatedWith(OrmEntity.class);
        for (Class<?> element : classSet) {
            try {
                OrmEntity entity = element.getAnnotation(OrmEntity.class);
                String tableName = entity.table();
                Statement statement = connection.createStatement();
                StringBuilder query = new StringBuilder();
                query.append(String.format("CREATE TABLE IF NOT EXISTS %s (", tableName));
                List<String> columnDefinitions = new ArrayList<>();
                Field[] fields = element.getDeclaredFields();
                for (Field field : fields) {
                    if (field.isAnnotationPresent(OrmColumnId.class)) {
                        columnDefinitions.add(String.format("%s SERIAL PRIMARY KEY", field.getName()));
                    } else if (field.isAnnotationPresent(OrmColumn.class)) {
                        OrmColumn column = field.getAnnotation(OrmColumn.class);
                        String columnDefinition = String.format("%s %s", column.name(), getColumnType(field));
                        columnDefinitions.add(columnDefinition);
                    }
                }
                query.append(String.join(", ", columnDefinitions));
                query.append(");");
                statement.execute(query.toString());
                System.out.println(query);
            } catch (SQLException e) {
                System.err.println(e.getMessage());
            }
        }
    }
    private String getColumnType(Field field) {
        OrmColumn column = field.getAnnotation(OrmColumn.class);
        String res = "";
        if (column != null) {
            String fieldType = field.getType().getSimpleName();
            switch (fieldType) {
                case "String":
                    res = String.format("VARCHAR(%d)", column.length());
                    break;
                case "Integer":
                    res = "INTEGER";
                    break;
                case "Long":
                    res = "BIGINT";
                    break;
                case "Boolean":
                    res = "BOOLEAN";
                    break;
            }
        }
        return res;
    }
    public void save(Object entity) throws AnnotationFormatError, IllegalAccessException, SQLException {
        Class<?> entityClass = entity.getClass();
        if (!entityClass.isAnnotationPresent(OrmEntity.class)) {
            throw new AnnotationFormatError("Not an ORM entity");
        }
        Field[] fields = entityClass.getDeclaredFields();
        OrmEntity tmp = entityClass.getAnnotation(OrmEntity.class);
        StringBuilder query = new StringBuilder("INSERT INTO " + tmp.table() + "(");
        List<String> columnNames = new ArrayList<>();
        List<String> columnValues = new ArrayList<>();
        for (Field field : fields) {
            if (field.isAnnotationPresent(OrmColumn.class)) {
                OrmColumn column = field.getAnnotation(OrmColumn.class);
                columnNames.add(column.name());
                field.setAccessible(true);
                Object value = field.get(entity);
                columnValues.add(field.getType().getSimpleName().equals("String") ? "'" +
                        value.toString() + "'" : value.toString());
                field.setAccessible(false);
            }
        }
        query.append(String.join(", ", columnNames));
        query.append(") VALUES (");
        query.append(String.join(", ", columnValues));
        query.append(");");
        Statement statement = connection.createStatement();
        statement.execute(query.toString());
        System.out.println(query);
    }

    public void update(Object entity) throws SQLException, IllegalAccessException {
        Class<?> tmp = entity.getClass();
        Field[] fields = tmp.getDeclaredFields();
        if (!tmp.isAnnotationPresent(OrmEntity.class)) {
            System.err.println("Entity is not ORM annotated!");
            return;
        }
        StringBuilder query = new StringBuilder();
        query.append(String.format("UPDATE %s SET ", tmp.getAnnotation(OrmEntity.class).table()));
        List<String> updateClauses = new ArrayList<>();
        String idColumnName = null;
        Object idValue = null;
        for (Field field : fields) {
            if (field.isAnnotationPresent(OrmColumn.class)) {
                OrmColumn column = field.getAnnotation(OrmColumn.class);
                String columnName = column.name();
                field.setAccessible(true);
                Object value = field.get(entity);
                updateClauses.add(String.format("%s = '%s'", columnName, value));
                field.setAccessible(false);
            } else if (field.isAnnotationPresent(OrmColumnId.class)) {
                OrmColumnId columnId = field.getAnnotation(OrmColumnId.class);
                String columnName = columnId.name();
                field.setAccessible(true);
                Object value = field.get(entity);
                idColumnName = columnName;
                idValue = value;
                field.setAccessible(false);
            }
        }
        query.append(String.join(", ", updateClauses));
        query.append(String.format(" WHERE %s = %s;", idColumnName, idValue));
        if (idValue == null) {
            System.err.println("Cannot update entity without a valid identifier!");
            return;
        }
        System.out.println(query);
        Statement statement = connection.createStatement();
        statement.execute(query.toString());
    }

    public <T> T findById(Long id, Class<T> aClass) throws SQLException, InstantiationException, IllegalAccessException,
            NoSuchMethodException, InvocationTargetException {
        if (!aClass.isAnnotationPresent(OrmEntity.class)) {
            System.err.println("Class is not ORM annotated!");
            return null;
        }
        Field[] fields = aClass.getDeclaredFields();
        StringBuilder query = new StringBuilder("SELECT * FROM " + aClass.getAnnotation(OrmEntity.class).table() + " WHERE ");
        for (Field field : fields) {
            if (field.isAnnotationPresent(OrmColumnId.class)) {
                OrmColumnId ormColumnId = field.getAnnotation(OrmColumnId.class);
                query.append(String.format("%s = %s", ormColumnId.name(), id.toString()));
                break;
            }
        }
        Statement statement = connection.createStatement();
        ResultSet set = statement.executeQuery(query.toString());
        System.out.println(query);
        T object = aClass.getDeclaredConstructor().newInstance();
        if (!set.next()) {
            System.err.println("No such row");
            return null;
        }
        for (Field field : fields) {
            if (field.isAnnotationPresent(OrmColumnId.class)) {
                field.setAccessible(true);
                field.set(object, set.getLong(1));
                field.setAccessible(false);
            } else if (field.isAnnotationPresent(OrmColumn.class)) {
                OrmColumn ormColumn = field.getAnnotation(OrmColumn.class);
                field.setAccessible(true);
                field.set(object, set.getObject(ormColumn.name()));
                field.setAccessible(false);
            }
        }
        return object;
    }

}
