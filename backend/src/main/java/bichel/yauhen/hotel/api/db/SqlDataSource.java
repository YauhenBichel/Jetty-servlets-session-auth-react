package bichel.yauhen.hotel.api.db;

public interface SqlDataSource {

    void loadDatabaseProperties(String propertyFile);
    String getJdbcUrl();
    String getDbUsername();
    String getDbPassword();
}
