package com.ragenotes.blacklist.service;

import com.ragenotes.blacklist.entity.entries.BlackListEntry;
import jdk.internal.jline.internal.Nullable;

import java.util.List;

public interface SearchService {
    String NAME = "bl_SearchService";

    List<BlackListEntry> findEntries(String pattern, @Nullable String viewName);

    List<BlackListEntry> getAllEntries(@Nullable String viewName);

    @Nullable
    BlackListEntry getEntry(String number, @Nullable String viewName);
}