package com.ragenotes.blacklist.service;

import com.ragenotes.blacklist.entity.ExportConsumer;
import com.ragenotes.blacklist.entity.entries.BlackListEntry;

import java.util.List;

public interface NotificationService {

    String NAME = "bl_NotificationService";

    void notifyNewEntry(BlackListEntry entry);

    void notifyReviewingEntry(BlackListEntry entry);

    void notifyAcceptingReadyEntry(BlackListEntry entry);

    void notifyAcceptedEntry(BlackListEntry entry);

    void notifyRejectedEntry(BlackListEntry entry);

    void notifyCustomMessage(String message);

    List<Integer> getVkConversations(ExportConsumer consumer);
}