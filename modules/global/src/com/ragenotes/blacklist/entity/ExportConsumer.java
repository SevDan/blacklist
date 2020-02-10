package com.ragenotes.blacklist.entity;

import com.haulmont.chile.core.annotations.NamePattern;
import com.haulmont.cuba.core.entity.StandardEntity;
import com.haulmont.cuba.core.entity.annotation.OnDelete;
import com.haulmont.cuba.core.global.DeletePolicy;

import javax.persistence.*;
import java.util.List;

@NamePattern("%s|name")
@Table(name = "BL_EXPORT_CONSUMER")
@Entity(name = "bl_ExportConsumer")
public class ExportConsumer extends StandardEntity {

    private static final long serialVersionUID = -1020825975091196471L;

    @Column(name = "NAME")
    private String name;

    @Column(name = "URL", length = 512)
    private String url;

    @Column(name = "TYPE", length = 50)
    private String type;

    @Column(name = "TOKEN", length = 2048)
    private String token;

    @Column(name = "ENABLED")
    private Boolean enabled;

    @Column(name = "IS_NEW_CONSUMER")
    private Boolean isNewConsumer;

    @Column(name = "IS_REVIEWING_CONSUMER")
    private Boolean isReviewingConsumer;

    @Column(name = "IS_READY_TO_ACCEPTING_CONSUMER")
    private Boolean isReadyToAcceptingConsumer;

    @Column(name = "IS_ACCEPTING_CONSUMER")
    private Boolean isAcceptingConsumer;

    @Column(name = "IS_ACCEPTED_CONSUMER")
    private Boolean isAcceptedConsumer;

    @Column(name = "IS_REJECTED_CONSUMER")
    private Boolean isRejectedConsumer;

    @Column(name = "IS_DISTRIBUTION_CONSUMER")
    private Boolean isDistributionConsumer;

    @OneToMany(mappedBy = "consumer")
    @OnDelete(DeletePolicy.CASCADE)
    private List<ExportParam> params;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public ExportConsumerType getType() {
        return type == null ? null : ExportConsumerType.fromId(type);
    }

    public void setType(ExportConsumerType type) {
        this.type = type == null ? null : type.getId();
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Boolean getIsNewConsumer() {
        return isNewConsumer;
    }

    public void setIsNewConsumer(Boolean newConsumer) {
        isNewConsumer = newConsumer;
    }

    public Boolean getIsReviewingConsumer() {
        return isReviewingConsumer;
    }

    public void setIsReviewingConsumer(Boolean reviewingConsumer) {
        isReviewingConsumer = reviewingConsumer;
    }

    public Boolean getIsReadyToAcceptingConsumer() {
        return isReadyToAcceptingConsumer;
    }

    public void setIsReadyToAcceptingConsumer(Boolean readyToAcceptingConsumer) {
        isReadyToAcceptingConsumer = readyToAcceptingConsumer;
    }

    public Boolean getIsAcceptingConsumer() {
        return isAcceptingConsumer;
    }

    public void setIsAcceptingConsumer(Boolean acceptingConsumer) {
        isAcceptingConsumer = acceptingConsumer;
    }

    public Boolean getIsAcceptedConsumer() {
        return isAcceptedConsumer;
    }

    public void setIsAcceptedConsumer(Boolean acceptedConsumer) {
        isAcceptedConsumer = acceptedConsumer;
    }

    public Boolean getIsRejectedConsumer() {
        return isRejectedConsumer;
    }

    public void setIsRejectedConsumer(Boolean rejectedConsumer) {
        isRejectedConsumer = rejectedConsumer;
    }

    public List<ExportParam> getParams() {
        return params;
    }

    public void setParams(List<ExportParam> params) {
        this.params = params;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public Boolean getIsDistributionConsumer() {
        return isDistributionConsumer;
    }

    public void setIsDistributionConsumer(Boolean isDistributionConsumer) {
        this.isDistributionConsumer = isDistributionConsumer;
    }
}