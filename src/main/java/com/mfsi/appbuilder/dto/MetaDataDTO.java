package com.mfsi.appbuilder.dto;

public class MetaDataDTO {

    private String columnName;
    private String dataType;

    public MetaDataDTO() {
    }

    public MetaDataDTO(String columnName, String dataType) {
        this.columnName = columnName;
        this.dataType = dataType;
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
