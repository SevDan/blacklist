package com.ragenotes.blacklist.service;

import com.haulmont.bali.util.Preconditions;
import com.haulmont.cuba.core.global.DataManager;
import com.haulmont.cuba.core.global.View;
import com.haulmont.fts.app.FtsService;
import com.haulmont.fts.global.SearchResult;
import com.ragenotes.blacklist.entity.entries.BlackListEntry;
import com.ragenotes.blacklist.entity.entries.EntryStatus;
import jdk.internal.jline.internal.Nullable;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service(SearchService.NAME)
public class SearchServiceBean implements SearchService {

    private static final Integer LIMIT = 1000;

    @Inject
    private FtsService ftsService;
    @Inject
    private DataManager dataManager;

    @Override
    public List<BlackListEntry> findEntries(String pattern, @Nullable String viewName) {
        Preconditions.checkNotEmptyString(pattern);

        SearchResult result = ftsService.search(pattern, Collections.singletonList("bl_BlackListEntry"));

        List<UUID> ids = result.getEntries("bl_BlackListEntry").stream()
                .map(e -> (UUID) e.getId())
                .collect(Collectors.toList());


        List<BlackListEntry> list = dataManager.load(BlackListEntry.class)
                .query("select ble " +
                        "   from bl_BlackListEntry ble " +
                        "   where ble.status = :accepted " +
                        "   and ble.id in :ids " +
                        "   order by ble.updateTs desc")
                .parameter("accepted", EntryStatus.Accepted)
                .parameter("ids", ids)
                .view(prepareView(viewName))
                .maxResults(LIMIT)
                .list();

        return list;
    }

    @Override
    public List<BlackListEntry> getAllEntries(@Nullable String viewName) {
        return dataManager.load(BlackListEntry.class)
                .query("select ble " +
                        "   from bl_BlackListEntry ble " +
                        "   where ble.status = :accepted " +
                        "   order by ble.updateTs desc")
                .parameter("accepted", EntryStatus.Accepted)
                .maxResults(LIMIT)
                .view(prepareView(viewName))
                .list();
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

    private String prepareView(String viewName) {
        if (viewName == null) return View.LOCAL;
        return viewName;
    }

}