package com.app.security.entity;

import javax.persistence.*;
import javax.persistence.Id;

@Entity<#if tableName??>(name="${tableName}")</#if>
public class User {

    @Id
    <#if idColumn??>@Column(name="${idColumn}")</#if>
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

	<#if userNameColumn??>@Column(name="${userNameColumn}")</#if>
    private String username;

	<#if passwordColumn??>@Column(name="${passwordColumn}")</#if>
    private String password;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
