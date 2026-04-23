package com.example.catalog.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

// @ConfigurationProperties binds all properties under the "catalog" prefix
// to the fields of this class at application startup.
// The field names use camelCase in Java; Spring Boot automatically maps
// kebab-case property names (page-size-default) to camelCase (pageSizeDefault).
@ConfigurationProperties(prefix = "catalog")
public class CatalogProperties {
    private int pageSizeDefault = 20; // default value if the property is absent
    private int pageSizeMax = 100;
    private String environmentLabel = "unknown";

    // TODO 6: Generate getters and setters for all three fields.
    // Spring Boot's binding mechanism requires standard JavaBean setters to
    // write the values, and getters for other classes to read them.
    // Use IntelliJ's generator: Alt+Insert (Windows/Linux) or Cmd+N (Mac)
    // -> Getter and Setter -> select all three fields.

    public int getPageSizeDefault() { return pageSizeDefault; }
    public void setPageSizeDefault(int pageSizeDefault) { this.pageSizeDefault = pageSizeDefault; }

    public int getPageSizeMax() { return pageSizeMax; }
    public void setPageSizeMax(int pageSizeMax) { this.pageSizeMax = pageSizeMax; }

    public String getEnvironmentLabel() { return environmentLabel; }
    public void setEnvironmentLabel(String environmentLabel) { this.environmentLabel = environmentLabel; }
}
