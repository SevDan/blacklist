package com.ragenotes.blacklist.service;

import com.haulmont.bali.util.Preconditions;
import com.ragenotes.blacklist.core.PostNotificationBean;
import com.ragenotes.blacklist.core.VkNotificationBean;
import com.ragenotes.blacklist.core.VkWallNotificationBean;
import com.ragenotes.blacklist.entity.ExportConsumer;
import com.ragenotes.blacklist.entity.ExportConsumerType;
import com.ragenotes.blacklist.entity.entries.BlackListEntry;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service(NotificationService.NAME)
public class NotificationServiceBean implements NotificationService {

    @Inject
    private VkNotificationBean vkNotificationBean;
    @Inject
    private VkWallNotificationBean vkWallNotificationBean;
    @Inject
    private PostNotificationBean postNotificationBean;

    @Override
    public void notifyNewEntry(BlackListEntry entry) {
        Preconditions.checkNotNullArgument(entry);

        vkNotificationBean.notifyNewEntry(entry);
        vkWallNotificationBean.notifyNewEntry(entry);
        postNotificationBean.notifyNewEntry(entry);
    }

    @Override
    public void notifyReviewingEntry(BlackListEntry entry) {
        Preconditions.checkNotNullArgument(entry);

        vkNotificationBean.notifyReviewingEntry(entry);
        vkWallNotificationBean.notifyReviewingEntry(entry);
        postNotificationBean.notifyReviewingEntry(entry);
    }

    @Override
    public void notifyAcceptingReadyEntry(BlackListEntry entry) {
        Preconditions.checkNotNullArgument(entry);

        vkNotificationBean.notifyAcceptingEntry(entry);
        vkWallNotificationBean.notifyAcceptingEntry(entry);
        postNotificationBean.notifyReviewingEntry(entry);
    }

    @Override
    public void notifyAcceptedEntry(BlackListEntry entry) {
        Preconditions.checkNotNullArgument(entry);

        vkNotificationBean.notifyAcceptedEntry(entry);
        vkWallNotificationBean.notifyAcceptedEntry(entry);
        postNotificationBean.notifyAcceptedEntry(entry);
    }

    @Override
    public void notifyRejectedEntry(BlackListEntry entry) {
        Preconditions.checkNotNullArgument(entry);

        vkNotificationBean.notifyRejectedEntry(entry);
        vkWallNotificationBean.notifyRejectedEntry(entry);
        postNotificationBean.notifyRejectedEntry(entry);
    }

    @Override
    public void notifyCustomMessage(String message) {
        Preconditions.checkNotEmptyString(message);

        vkNotificationBean.notifyCustomMessage(message);
        vkWallNotificationBean.notifyCustomMessage(message);
        postNotificationBean.notifyCustomMessage(message);
    }

    @Override
    public List<Integer> getVkConversations(ExportConsumer consumer) {
        Preconditions.checkNotNullArgument(consumer);
        if(consumer.getType() != ExportConsumerType.VK) return new ArrayList<>();

        return vkNotificationBean.getDialogs(consumer);
    }
}