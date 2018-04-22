package com.rock.model.base;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.rock.model.jpa.AbstractEntity;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@JsonIgnoreProperties(value = {"hibernateLazyInitializer", "handler", "updateSelective"}, allowSetters = true)
@Entity
@Data
@Table(name = "action_log")
public class ActionLog extends AbstractEntity {

    @Column(name = "client_ip")
    private String clientIp;

    @Column(name = "url")
    private String url;

    @Column(name = "class_name")
    private String className;

    @Column(name = "method_name")
    private String methodName;

    @Column(name = "params")
    private String params;

    @Column(name = "operator")
    private String operator;

    @Column(name = "description")
    private String description;

    @Column(name = "module_id")
    private String moduleId;

    @Column(name = "response_data")
    private String responseData;

}
