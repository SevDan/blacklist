package com.ragenotes.blacklist.portal.controllers;

import com.google.common.base.Strings;
import com.haulmont.cuba.core.app.DataService;
import com.haulmont.cuba.core.app.importexport.EntityImportExportService;
import com.haulmont.cuba.core.global.LoadContext;
import com.haulmont.cuba.core.global.View;
import com.haulmont.cuba.core.global.ViewRepository;
import com.ragenotes.blacklist.entity.entries.BlackListEntry;
import com.ragenotes.blacklist.entity.entries.EntryStatus;
import com.ragenotes.blacklist.service.SearchService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

@RestController(value = PublicEntriesRestController.NAME)
@RequestMapping("/public-rest")
public class PublicEntriesRestController {

    public static final String NAME = "bl_PublicEntriesRestController";

    private static final Integer LIMIT = 200;

    @Inject
    private DataService dataService;
    @Inject
    private EntityImportExportService entityImportExportService;
    @Inject
    private ViewRepository viewRepository;
    @Inject
    private SearchService searchService;

    /**
     * Gets all time entries.
     *
     * @return entries JSON
     */
    @RequestMapping(value = "/all",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public String allEntries() {
        List<BlackListEntry> allEntries = searchService.getAllEntries(null);

        return serializeToJson(allEntries);
    }

    /**
     * FTS search.
     *
     * @param textPattern FTS-pattern
     * @return entries JSON
     */
    @RequestMapping(value = "/search",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public String searchEntries(@RequestParam("pattern") String textPattern) {
        List<BlackListEntry> entries = searchService.findEntries(textPattern, null);

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
    @RequestMapping(value = "/find",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public String findEntries(
            @RequestParam(value = "id", defaultValue = "", required = false) String id,
            @RequestParam(value = "nickName", defaultValue = "", required = false) String nickName,
            @RequestParam(value = "fullName", defaultValue = "", required = false) String fullName,
            @RequestParam(value = "firstName", defaultValue = "", required = false) String firstName,
            @RequestParam(value = "secondName", defaultValue = "", required = false) String secondName,
            @RequestParam(value = "lastName", defaultValue = "", required = false) String lastName,
            @RequestParam(value = "code", defaultValue = "", required = false) String code,
            @RequestParam(value = "number", defaultValue = "", required = false) String number
    ) {
        LoadContext.Query query = new LoadContext.Query("select ble " +
                "from bl_BlackListEntry ble " +
                "where ble.status = :acceptedStatus ");
        query.setParameter("acceptedStatus", EntryStatus.Accepted);
        appendExistsValue(query, "id", id);
        appendExistsValue(query, "nickName", nickName);
        appendExistsValue(query, "fullName", fullName);
        appendExistsValue(query, "firstName", firstName);
        appendExistsValue(query, "secondName", secondName);
        appendExistsValue(query, "lastName", lastName);
        appendExistsValue(query, "code", code);
        appendExistsValue(query, "number", number);
        query.setMaxResults(LIMIT);

        LoadContext<BlackListEntry> loadContext = new LoadContext<>(BlackListEntry.class);
        loadContext.setView(getBlackListLocalView());
        loadContext.setQuery(query);

        return serializeToJson(dataService.loadList(loadContext));
    }

    private String serializeToJson(Collection<BlackListEntry> entries) {
        return entityImportExportService.exportEntitiesToJSON(entries,
                getBlackListLocalView());
    }

    private void appendExistsValue(LoadContext.Query query,
                                   String parameterName,
                                   String parameterValue) {
        if (!Strings.isNullOrEmpty(parameterValue)) {
            String queryString = query.getQueryString();
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

    private View getBlackListLocalView() {
        return viewRepository.getView(BlackListEntry.class, View.LOCAL);
    }
}
