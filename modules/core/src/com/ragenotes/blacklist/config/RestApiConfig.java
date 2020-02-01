package com.ragenotes.blacklist.config;

import com.haulmont.cuba.core.config.Config;
import com.haulmont.cuba.core.config.Property;
import com.haulmont.cuba.core.config.Source;
import com.haulmont.cuba.core.config.SourceType;
import com.haulmont.cuba.core.config.defaults.Default;
import com.haulmont.cuba.core.config.defaults.DefaultInteger;
import com.haulmont.cuba.core.config.defaults.DefaultLong;

@Source(type = SourceType.DATABASE)
public interface RestApiConfig extends Config {

    @Property("bl-rest.restLimit")
    @DefaultInteger(200)
    Integer getRestLimit();

    void setRestLimit(Integer restLimit);

    @Property("bl-public.publicLimit")
    @DefaultInteger(100)
    Integer getPublicLimit();

    void setPublicLimit(Integer publicLimit);

    @Property("bl-rest.searchLimit")
    @DefaultInteger(200)
    Integer getSearchLimit();

    void setSearchLimit(Integer searchLimit);

    @Property("bl-rest.restDateFormat")
    @Default("yyyy-MM-dd")
    String getRestDateFormat();

    void setRestDateFormat(String restDateFormat);

}
