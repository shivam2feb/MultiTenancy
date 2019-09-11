package com.mfsi.appbuilder.dto;

public class MetaDataDTO {

    private String columnName;
    private String dataType;
    private boolean primaryKey;

    public MetaDataDTO(String columnName, String dataType, String columnType) {
        this.columnName = columnName;
        this.dataType = dataType;
        if (columnType.equals("PRI")) {
            this.primaryKey = true;
        }
    }

    public boolean getPrimaryKey() {
        return primaryKey;
    }

    public MetaDataDTO() {
    }

    public void setPrimaryKey(boolean primaryKey) {
        this.primaryKey = primaryKey;
    }

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }
}
