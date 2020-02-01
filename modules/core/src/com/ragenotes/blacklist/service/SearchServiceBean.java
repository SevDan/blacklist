package com.ragenotes.blacklist.service;

import com.google.common.base.Strings;
import com.haulmont.bali.util.Preconditions;
import com.haulmont.cuba.core.config.type.DateFactory;
import com.haulmont.cuba.core.entity.StandardEntity;
import com.haulmont.cuba.core.global.DataManager;
import com.haulmont.cuba.core.global.FluentLoader;
import com.haulmont.cuba.core.global.View;
import com.haulmont.fts.app.FtsService;
import com.haulmont.fts.global.SearchResult;
import com.ragenotes.blacklist.config.RestApiConfig;
import com.ragenotes.blacklist.entity.entries.BlackListEntry;
import com.ragenotes.blacklist.entity.entries.EntryStatus;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import javax.inject.Inject;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Service(SearchService.NAME)
public class SearchServiceBean implements SearchService {

    private static final String blackListEntryEntityName = "bl_BlackListEntry";
    private static final String contactEntityName = "bl_Contact";
    private static final String playerIpEntityName = "bl_PlayerIP";
    private static final String historyEntityName = "bl_History";

    @Inject
    private FtsService ftsService;
    @Inject
    private DataManager dataManager;
    @Inject
    private RestApiConfig restApiConfig;

    @Override
    public Set<BlackListEntry> searchEntries(String pattern, @Nullable String viewName) {
        Preconditions.checkNotEmptyString(pattern);

        SearchResult result = ftsService.search(pattern, Collections.singletonList(blackListEntryEntityName));

        List<UUID> ids = getEntitiesIdsFromSearchResult(result, blackListEntryEntityName);

        return findEntriesByIds(ids, viewName);
    }

    @Override
    public Set<BlackListEntry> searchEntryByRelated(String pattern, @Nullable String viewName) {
        Preconditions.checkNotEmptyString(pattern);

        SearchResult result = ftsService.search(pattern, Arrays.asList(
                blackListEntryEntityName,
                contactEntityName,
                historyEntityName,
                playerIpEntityName
        ));

        List<UUID> entriesIds = getEntitiesIdsFromSearchResult(result, blackListEntryEntityName);
        List<UUID> contactsIds = getEntitiesIdsFromSearchResult(result, contactEntityName);
        List<UUID> historiesIds = getEntitiesIdsFromSearchResult(result, historyEntityName);
        List<UUID> playerIpIds = getEntitiesIdsFromSearchResult(result, playerIpEntityName);

        Set<BlackListEntry> blackListEntries = findEntriesByIds(entriesIds, viewName);

        blackListEntries.addAll(findEntriesByRelatedIds("contacts", contactsIds, viewName));
        blackListEntries.addAll(findEntriesByRelatedIds("histories", historiesIds, viewName));
        blackListEntries.addAll(findEntriesByRelatedIds("playerIps", playerIpIds, viewName));

        return blackListEntries.stream()
                .sorted(Comparator.comparing(StandardEntity::getUpdateTs))
                .limit(restApiConfig.getSearchLimit())
                .collect(Collectors.toSet());
    }

    @Override
    public Set<BlackListEntry> getAllEntries(@Nullable String viewName) {
        return new HashSet<>(dataManager.load(BlackListEntry.class)
                .query("select ble " +
                        "   from bl_BlackListEntry ble " +
                        "   where ble.status = :accepted " +
                        "   order by ble.updateTs desc")
                .parameter("accepted", EntryStatus.Accepted)
                .maxResults(restApiConfig.getSearchLimit())
                .view(prepareView(viewName))
                .list());
    }

    @Override
    public Set<BlackListEntry> getAllEntriesBatched(
            @Nullable String createDateFrom,
            @Nullable String updateDateFrom,
            @Nullable String number,
            @Nullable String maxCount,
            @Nullable String viewName) {

        Integer maxCountValue = null;
        if (!Strings.isNullOrEmpty(maxCount)) {
            maxCountValue = Integer.valueOf(maxCount);
        }

        Long numberValue = null;
        if (!Strings.isNullOrEmpty(number)) {
            numberValue = Long.valueOf(number);
        }

        Date createDateValue = null;
        if (!Strings.isNullOrEmpty(createDateFrom)) {
            try {
                createDateValue = new SimpleDateFormat(restApiConfig.getRestDateFormat())
                        .parse(createDateFrom);
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
        }

        Date updateDateValue = null;
        if (!Strings.isNullOrEmpty(updateDateFrom)) {
            try {
                updateDateValue = new SimpleDateFormat(restApiConfig.getRestDateFormat())
                        .parse(updateDateFrom);
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
        }

        return new HashSet<>(executeBatchedQuery(numberValue,
                createDateValue,
                updateDateValue,
                maxCountValue,
                viewName));
    }

    @Nullable
    @Override
    public BlackListEntry getEntry(String number, @Nullable String viewName) {
        Preconditions.checkNotEmptyString(number);
        Long numberParam;
        try {
            numberParam = Long.valueOf(number);
        } catch (Exception e) {
            return null;
        }

        return dataManager.load(BlackListEntry.class)
                .query("select ble " +
                        "   from bl_BlackListEntry ble " +
                        "   where ble.status = :accepted " +
                        "   and ble.number = :number ")
                .parameter("accepted", EntryStatus.Accepted)
                .parameter("number", numberParam)
                .view(prepareView(viewName))
                .optional().orElse(null);
    }

    private List<BlackListEntry> executeBatchedQuery(Long number,
                                                     Date createDateFrom,
                                                     Date updateDateFrom,
                                                     Integer maxCount,
                                                     String viewName) {
        FluentLoader.ByQuery<BlackListEntry, UUID> query = dataManager.load(BlackListEntry.class)
                .query("select ble " +
                        "   from bl_BlackListEntry ble " +
                        "   where ble.status = :accepted " +
                        (number != null ? " and ble.number >= :number " : "") +
                        (createDateFrom != null ? " and ble.createTs >= :createTs " : "") +
                        (updateDateFrom != null ? " and ble.updateTs >= :updateTs" : "") +
                        "   order by ble.updateTs, ble.number, ble.updateTs desc");
        query.parameter("accepted", EntryStatus.Accepted);

        if (number != null) query.parameter("number", number);
        if (createDateFrom != null) query.parameter("createTs", createDateFrom);
        if (updateDateFrom != null) query.parameter("updateTs", updateDateFrom);
        if (maxCount != null) {
            query.maxResults(maxCount);
        } else {
            query.maxResults(restApiConfig.getPublicLimit());
        }
        query.view(prepareView(viewName));

        return query.list();
    }

    private Set<BlackListEntry> findEntriesByIds(List<UUID> entriesIds, String viewName) {
        return new HashSet<>(dataManager.load(BlackListEntry.class)
                .query("select distinct ble " +
                        "   from bl_BlackListEntry ble " +
                        "   where ble.status = :accepted " +
                        "   and ble.id in :ids " +
                        "   order by ble.updateTs desc")
                .parameter("accepted", EntryStatus.Accepted)
                .parameter("ids", entriesIds)
                .view(prepareView(viewName))
                .maxResults(restApiConfig.getSearchLimit())
                .list());
    }

    private Set<BlackListEntry> findEntriesByRelatedIds(String relatedColumn,
                                                        List<UUID> relatedIds,
                                                        String viewName) {
        return new HashSet<>(dataManager.load(BlackListEntry.class)
                .query("select distinct ble " +
                        "   from bl_BlackListEntry ble, in(ble." + relatedColumn + ") re " +
                        "   where ble.status = :accepted " +
                        "   and re.id in :ids " +
                        "   order by ble.updateTs desc")
                .parameter("accepted", EntryStatus.Accepted)
                .parameter("ids", relatedIds)
                .view(prepareView(viewName))
                .maxResults(restApiConfig.getSearchLimit())
                .list());
    }

    private List<UUID> getEntitiesIdsFromSearchResult(SearchResult result, String entityName) {
        return result.getEntries(entityName).stream()
                .map(e -> (UUID) e.getId())
                .collect(Collectors.toList());
    }

    private String prepareView(String viewName) {
        if (viewName == null) return View.LOCAL;
        return viewName;
    }

}