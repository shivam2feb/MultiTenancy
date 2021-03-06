package com.mfsi.appbuilder.dto;

import java.util.List;

public class TableDetailsDTO {

    private String tableName;
    private DBDetailsDTO dbDetailsDTO;
    private List<MetaDataDTO> metaDataDTOs;
    private Boolean createFlow;

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public DBDetailsDTO getDbDetailsDTO() {
        return dbDetailsDTO;
    }

    public void setDbDetailsDTO(DBDetailsDTO dbDetailsDTO) {
        this.dbDetailsDTO = dbDetailsDTO;
    }

    public List<MetaDataDTO> getMetaDataDTOs() {
        return metaDataDTOs;
    }

    public void setMetaDataDTOs(List<MetaDataDTO> metaDataDTOs) {
        this.metaDataDTOs = metaDataDTOs;
    }

    public Boolean isCreateFlow() {
        return createFlow;
    }

    public void setCreateFlow(Boolean createFlow) {
        this.createFlow = createFlow;
    }
}
