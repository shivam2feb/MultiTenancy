package com.mfsi.appbuilder.model;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;


@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "entityName",
    "isPrimaryKey",
    "relationship",
    "dataType",
    "columnName"
})
public class Parameter {

    @JsonProperty("entityName")
    private String entityName;
    @JsonProperty("isPrimaryKey")
    private Boolean isPrimaryKey;
    @JsonProperty("relationship")
    private String relationship;
    @JsonProperty("dataType")
    private Object dataType = null;
    @JsonProperty("columnName")
    private String columnName;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    @JsonProperty("entityName")
    public String getEntityName() {
        return entityName;
    }

    @JsonProperty("entityName")
    public void setEntityName(String entityName) {
        this.entityName = entityName;
    }

    @JsonProperty("isPrimaryKey")
    public Boolean getIsPrimaryKey() {
        return isPrimaryKey;
    }

    @JsonProperty("isPrimaryKey")
    public void setIsPrimaryKey(Boolean isPrimaryKey) {
        this.isPrimaryKey = isPrimaryKey;
    }

    @JsonProperty("relationship")
    public String getRelationship() {
        return relationship;
    }

    @JsonProperty("relationship")
    public void setRelationship(String relationship) {
        this.relationship = relationship;
    }

    @JsonProperty("dataType")
    public Object getDataType() {
        return dataType;
    }

    @JsonProperty("dataType")
    public void setDataType(Object dataType) {
        this.dataType = dataType;
    }

    @JsonProperty("columnName")
    public String getColumnName() {
        return columnName;
    }

    @JsonProperty("columnName")
    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
