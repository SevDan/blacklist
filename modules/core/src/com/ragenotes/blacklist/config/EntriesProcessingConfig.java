package com.ragenotes.blacklist.config;

import com.haulmont.cuba.core.config.Config;
import com.haulmont.cuba.core.config.Property;
import com.haulmont.cuba.core.config.Source;
import com.haulmont.cuba.core.config.SourceType;
import com.haulmont.cuba.core.config.defaults.Default;
import com.haulmont.cuba.core.config.defaults.DefaultLong;

@Source(type = SourceType.DATABASE)
public interface EntriesProcessingConfig extends Config {

    @Property("bl.reviewingQuorum")
    @DefaultLong(4)
    Long getReviewingQuorum();

    void setReviewingQuorum();
}
