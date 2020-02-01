package com.ragenotes.blacklist.portal.controllers;

import com.google.common.base.Strings;
import com.haulmont.cuba.core.app.DataService;
import com.haulmont.cuba.core.app.importexport.EntityImportExportService;
import com.haulmont.cuba.core.global.LoadContext;
import com.haulmont.cuba.core.global.View;
import com.haulmont.cuba.core.global.ViewRepository;
import com.ragenotes.blacklist.entity.entries.BlackListEntry;
import com.ragenotes.blacklist.entity.entries.EntryStatus;
import com.ragenotes.blacklist.service.RestApiService;
import com.ragenotes.blacklist.service.SearchService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.util.Collection;
import java.util.Set;
import java.util.UUID;

@RestController(value = PublicEntriesRestController.NAME)
@RequestMapping("/public-rest")
public class PublicEntriesRestController {

    public static final String NAME = "bl_PublicEntriesRestController";

    @Inject
    private DataService dataService;
    @Inject
    private EntityImportExportService entityImportExportService;
    @Inject
    private ViewRepository viewRepository;
    @Inject
    private SearchService searchService;
    @Inject
    private RestApiService restApiService;

    /**
     * Gets all time entries.
     *
     * @return entries JSON
     */
    @RequestMapping(path = "/all",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public String allEntries() {
        Set<BlackListEntry> allEntries = searchService.getAllEntries(null);

        return serializeToJson(allEntries);
    }

    /**
     * Gets all time entries with constraints.
     *
     * @param createDateFrom start create date
     * @param updateDateFrom start update date
     * @param number         start number
     * @param count          max count (maximum value configured by rest config)
     * @return entries JSON
     */
    @RequestMapping(path = "/all/batch",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public String allFrom(
            @RequestParam(name = "createDate", defaultValue = "", required = false) String createDateFrom,
            @RequestParam(name = "updateDate", defaultValue = "", required = false) String updateDateFrom,
            @RequestParam(name = "number", defaultValue = "", required = false) String number,
            @RequestParam(name = "count", defaultValue = "", required = false) String count
    ) {
        Set<BlackListEntry> entriesBatch = searchService.getAllEntriesBatched(createDateFrom,
                updateDateFrom,
                number,
                count,
                null);

        return serializeToJson(entriesBatch);
    }

    /**
     * FTS search.
     *
     * @param textPattern FTS-pattern
     * @return entries JSON
     */
    @RequestMapping(path = "/search",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public String searchEntries(
            @RequestParam(name = "pattern", defaultValue = "") String textPattern
    ) {
        Set<BlackListEntry> entries = searchService.searchEntries(textPattern, null);

        return serializeToJson(entries);
    }

    /**
     * FTS search by related entities.
     *
     * @param textPattern FTS-pattern
     * @return entries JSON
     */
    @RequestMapping(path = "/search/related",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public String searchEntriesByRelated(
            @RequestParam(name = "pattern", defaultValue = "") String textPattern
    ) {
        Set<BlackListEntry> entries = searchService.searchEntryByRelated(textPattern, null);

        return serializeToJson(entries);
    }

    /**
     * Find entries by string param.
     *
     * @param nickName   nick name
     * @param fullName   full name
     * @param firstName  first name
     * @param secondName second name
     * @param lastName   last name
     * @param code       code
     * @param number     number
     * @return entries JSON
     */
    @RequestMapping(path = "/find",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public String findEntries(
            @RequestParam(name = "id", defaultValue = "", required = false) String id,
            @RequestParam(name = "nickName", defaultValue = "", required = false) String nickName,
            @RequestParam(name = "fullName", defaultValue = "", required = false) String fullName,
            @RequestParam(name = "firstName", defaultValue = "", required = false) String firstName,
            @RequestParam(name = "secondName", defaultValue = "", required = false) String secondName,
            @RequestParam(name = "lastName", defaultValue = "", required = false) String lastName,
            @RequestParam(name = "code", defaultValue = "", required = false) String code,
            @RequestParam(name = "number", defaultValue = "", required = false) String number
    ) {
        LoadContext.Query query = new LoadContext.Query("select ble " +
                "   from bl_BlackListEntry ble " +
                "   where ble.status = :acceptedStatus ");
        query.setParameter("acceptedStatus", EntryStatus.Accepted);
        appendExistsValueInFindQuery(query, "id", id);
        appendExistsValueInFindQuery(query, "nickName", nickName);
        appendExistsValueInFindQuery(query, "fullName", fullName);
        appendExistsValueInFindQuery(query, "firstName", firstName);
        appendExistsValueInFindQuery(query, "secondName", secondName);
        appendExistsValueInFindQuery(query, "lastName", lastName);
        appendExistsValueInFindQuery(query, "code", code);
        appendExistsValueInFindQuery(query, "number", number);
        query.setMaxResults(restApiService.getRestLimit());

        LoadContext<BlackListEntry> loadContext = new LoadContext<>(BlackListEntry.class);
        loadContext.setView(getBlackListLocalView());
        loadContext.setQuery(query);

        return serializeToJson(dataService.loadList(loadContext));
    }

    /**
     * Find entries by related entries with string param.
     *
     * @param contact  contact
     * @param playerIp player IP
     * @param history  history
     * @return entries JSON
     */
    @RequestMapping(path = "/find/related",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public String findEntriesByRelated(
            @RequestParam(name = "contact", defaultValue = "", required = false) String contact,
            @RequestParam(name = "playerIp", defaultValue = "", required = false) String playerIp,
            @RequestParam(name = "history", defaultValue = "", required = false) String history
    ) {
        LoadContext.Query query = new LoadContext.Query("select ble " +
                "   from bl_BlackListEntry ble " +
                "   where ble.status = :acceptedStatus ");
        query.setParameter("acceptedStatus", EntryStatus.Accepted);
        appendExistsValueInRelatedFindQuery(query, "ble", "contacts", contact);
        appendExistsValueInRelatedFindQuery(query, "ble", "playerIps", playerIp);
        appendExistsValueInRelatedFindQuery(query, "ble", "histories", history);
        query.setMaxResults(restApiService.getRestLimit());

        LoadContext<BlackListEntry> loadContext = new LoadContext<>(BlackListEntry.class);
        loadContext.setView(getBlackListLocalView());
        loadContext.setQuery(query);

        return serializeToJson(dataService.loadList(loadContext));
    }

    private String serializeToJson(Collection<BlackListEntry> entries) {
        return entityImportExportService.exportEntitiesToJSON(entries,
                getBlackListLocalView());
    }

    private void appendExistsValueInFindQuery(LoadContext.Query query,
                                              String parameterName,
                                              String parameterValue) {
        String queryString = query.getQueryString();
        if (!Strings.isNullOrEmpty(parameterValue)) {
            query.setQueryString(queryString +
                    " and ble." + parameterName + " = :" + parameterName);
            switch (parameterName) {
                case "number": {
                    query.setParameter(parameterName, Long.valueOf(parameterValue));
                    break;
                }
                case "id": {
                    try {
                        query.setParameter(parameterName, UUID.fromString(parameterValue));
                    } catch (IllegalArgumentException e) {
                        query.setParameter(parameterName, null);
                    }
                    break;
                }
                default: {
                    query.setParameter(parameterName, parameterValue);
                    break;
                }
            }
        }
    }

    private void appendExistsValueInRelatedFindQuery(LoadContext.Query query,
                                                     String tableName,
                                                     String relatedColumnName,
                                                     String parameterValue) {
        String queryString = query.getQueryString();
        if (!Strings.isNullOrEmpty(parameterValue)) {
            switch (relatedColumnName) {
                case "contacts": {
                    query.setQueryString(queryString +
                            " and exists (" +
                            "   select c " +
                            "   from " + tableName + "." + relatedColumnName + " c " +
                            "   where c.value = :" + relatedColumnName + ") ");
                    query.setParameter(relatedColumnName, parameterValue);
                    break;
                }
                case "histories": {
                    query.setQueryString(queryString +
                            " and exists (" +
                            "   select he " +
                            "   from " + tableName + "." + relatedColumnName + " he " +
                            "   where he.name = :" + relatedColumnName + ") ");
                    query.setParameter(relatedColumnName, parameterValue);
                    break;
                }
                case "playerIps": {
                    query.setQueryString(queryString +
                            " and exists (" +
                            "   select i " +
                            "   from " + tableName + "." + relatedColumnName + " i " +
                            "   where i.ip = :" + relatedColumnName + ") ");
                    query.setParameter(relatedColumnName, parameterValue);
                    break;
                }
            }
        }
    }

    private View getBlackListLocalView() {
        return viewRepository.getView(BlackListEntry.class, View.LOCAL);
    }
}
