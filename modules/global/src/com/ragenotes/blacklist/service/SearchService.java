package com.ragenotes.blacklist.service;

import com.ragenotes.blacklist.entity.entries.BlackListEntry;
import jdk.internal.jline.internal.Nullable;

import java.util.List;
import java.util.Set;

public interface SearchService {
    String NAME = "bl_SearchService";

    Set<BlackListEntry> searchEntries(String pattern, @Nullable String viewName);

    Set<BlackListEntry> searchEntryByRelated(String pattern, @Nullable String viewName);

    Set<BlackListEntry> getAllEntries(@Nullable String viewName);

    Set<BlackListEntry> getAllEntriesBatched(
            @Nullable String createDateFrom,
            @Nullable String updateDateFrom,
            @Nullable String number,
            @Nullable String maxCount,
            @Nullable String viewName);

    @Nullable
    BlackListEntry getEntry(String number, @Nullable String viewName);
}