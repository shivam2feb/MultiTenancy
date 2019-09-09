package com.mfsi.appbuilder.master.document;

import com.mfsi.appbuilder.dto.DBDetailsDTO;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Document
public class Project {
	
	@Id
	private String _id;
	
	private String projectName;
	private List<Map<String,String>> apiInfo=new ArrayList<>();
	
    @LastModifiedDate
    private Date lastUpdatedOn;
    private String userId;
    private DBDetailsDTO dbDetailsDTO;
	private Boolean wantSecurity = true;

	/**
	 * @return the wantSecurity
	 */
	public Boolean getWantSecurity() {
		return wantSecurity;
	}

	/**
	 * @param wantSecurity the wantSecurity to set
	 */
	public void setWantSecurity(Boolean wantSecurity) {
		this.wantSecurity = wantSecurity;
	}

	public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
	}

	public String get_id() {
		return _id;
	}

	public void set_id(String _id) {
		this._id = _id;
	}

	public String getProjectName() {
		return projectName;
	}
	
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public List<Map<String, String>> getApiInfo() {
		return apiInfo;
	}

	public void setApiInfo(List<Map<String, String>> apiInfo) {
		this.apiInfo = apiInfo;
	}

    public String getLastUpdatedOn() {
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm");
        return sdf.format(lastUpdatedOn);
	}

    public void setLastUpdatedOn(Date lastUpdatedOn) {
		this.lastUpdatedOn = lastUpdatedOn;
	}

    public DBDetailsDTO getDbDetailsDTO() {
        return dbDetailsDTO;
    }

    public void setDbDetailsDTO(DBDetailsDTO dbDetailsDTO) {
        this.dbDetailsDTO = dbDetailsDTO;
	}
}