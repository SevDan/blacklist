package com.ragenotes.blacklist.core;

import com.google.common.base.Strings;
import com.haulmont.bali.util.Preconditions;
import com.haulmont.cuba.core.global.DataManager;
import com.haulmont.cuba.core.global.TemplateHelper;
import com.ragenotes.blacklist.entity.ExportConsumer;
import com.ragenotes.blacklist.entity.ExportConsumerType;
import com.ragenotes.blacklist.entity.ExportParam;
import com.ragenotes.blacklist.entity.entries.BlackListEntry;

import com.ragenotes.blacklist.entity.entries.EntryStatus;
import com.vk.api.sdk.client.TransportClient;
import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.GroupActor;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.httpclient.HttpTransportClient;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.util.*;
import java.util.stream.Collectors;

@Component(VkNotificationBean.NAME)
public class VkNotificationBean {

    public static final String NAME = "bl_VkNotificationBean";

    private static final String RECEIVER_PARAM = "receiver_id";
    private static final String GROUP_ID_PARAM = "group_id";
    private static final String NEW_MSG_PARAM = "new_message_format";
    private static final String ALL_MSG_PARAM ="message_format";
    private static final String REVIEWING_MSG_PARAM = "reviewing_message_format";
    private static final String ACCEPTING_MSG_PARAM = "accepting_message_format";
    private static final String ACCEPTED_MSG_PARAM = "accepting_message_format";
    private static final String REJECTED_MSG_PARAM = "accepting_message_format";

    private final TransportClient transportClient = new HttpTransportClient();
    private final VkApiClient vkApiClient = new VkApiClient(transportClient);

    @Inject
    private DataManager dataManager;

    public void notifyNewEntry(BlackListEntry entry) {
        Preconditions.checkNotNullArgument(entry);

        List<ExportConsumer> vkConsumers = getVkConsumers();

        for (ExportConsumer consumer : vkConsumers) {
            if (!consumer.getIsNewConsumer()) {
                continue;
            }

            processConsumerMessage(consumer, entry, NEW_MSG_PARAM);
        }
    }

    public void notifyReviewingEntry(BlackListEntry entry) {
        Preconditions.checkNotNullArgument(entry);

        List<ExportConsumer> vkConsumers = getVkConsumers();

        for (ExportConsumer consumer : vkConsumers) {
            if (!consumer.getIsReviewingConsumer()) {
                continue;
            }

            processConsumerMessage(consumer, entry, REVIEWING_MSG_PARAM);
        }
    }

    public void notifyAcceptingEntry(BlackListEntry entry) {
        Preconditions.checkNotNullArgument(entry);

        List<ExportConsumer> vkConsumers = getVkConsumers();

        for (ExportConsumer consumer : vkConsumers) {
            if (!consumer.getIsAcceptingConsumer()) {
                continue;
            }

            processConsumerMessage(consumer, entry, ACCEPTING_MSG_PARAM);
        }
    }

    public void notifyAcceptedEntry(BlackListEntry entry) {
        Preconditions.checkNotNullArgument(entry);

        List<ExportConsumer> vkConsumers = getVkConsumers();

        for (ExportConsumer consumer : vkConsumers) {
            if (!consumer.getIsAcceptedConsumer()) {
                continue;
            }

            processConsumerMessage(consumer, entry, ACCEPTED_MSG_PARAM);
        }
    }

    public void notifyRejectedEntry(BlackListEntry entry) {
        Preconditions.checkNotNullArgument(entry);

        List<ExportConsumer> vkConsumers = getVkConsumers();

        for (ExportConsumer consumer : vkConsumers) {
            if (!consumer.getIsRejectedConsumer()) {
                continue;
            }

            processConsumerMessage(consumer, entry, REJECTED_MSG_PARAM);
        }
    }

    public void notifyCustomMessage(String message) {
        Preconditions.checkNotEmptyString(message);

        List<ExportConsumer> vkConsumers = getVkConsumers();

        for (ExportConsumer consumer : vkConsumers) {
            processConsumerMessage(consumer, message);
        }
    }

    public List<Integer> getDialogs(ExportConsumer consumer) {
        Preconditions.checkNotNullArgument(consumer);

        Integer groupId = getGroupId(getConsumerParams(consumer));
        return getConversations(groupId, consumer.getToken());
    }

    private void processConsumerMessage(ExportConsumer consumer,
                                        BlackListEntry entry,
                                        String messageFormatParamName) {
        String token = consumer.getToken();

        List<ExportParam> params = getConsumerParams(consumer);

        String message = buildMessage(entry, messageFormatParamName, params);

        Integer groupId = getGroupId(params);

        if (groupId < 0) {
            return;
        }

        List<Integer> receivers = getReceiverIds(params);

        for (Integer receiver : receivers) {
            sendMessage(groupId, token, message, receiver);
        }
    }

    private void processConsumerMessage(ExportConsumer consumer, String message) {
        String token = consumer.getToken();

        List<ExportParam> params = getConsumerParams(consumer);

        Integer groupId = getGroupId(params);

        if (groupId < 0) {
            return;
        }

        List<Integer> receivers = getReceiverIds(params);

        for (Integer receiver : receivers) {
            sendMessage(groupId, token, message, receiver);
        }
    }

    private List<Integer> getReceiverIds(List<ExportParam> params) {
        List<String> receivers = params.stream()
                .filter(param -> RECEIVER_PARAM.equals(param.getKey()))
                .map(ExportParam::getValue)
                .collect(Collectors.toList());

        List<Integer> result = new ArrayList<>();

        for (String receiver : receivers) {
            try {
                Integer receiverId = Integer.parseInt(receiver);
                result.add(receiverId);
            } catch (NumberFormatException e) {
                continue;
            }
        }

        return result;
    }

    private Integer getGroupId(List<ExportParam> params) {
        return Integer.parseInt(params.stream()
                .filter(param -> GROUP_ID_PARAM.equals(param.getKey()))
                .findFirst()
                .map(ExportParam::getValue)
                .orElse("-1"));
    }

    private String buildMessage(BlackListEntry entry, String paramName, List<ExportParam> params) {
        String formatString = params.stream()
                .filter(param -> paramName.equals(param.getKey()))
                .findFirst()
                .map(ExportParam::getValue)
                .orElse(null);

        if(formatString == null) {
            formatString = params.stream()
                    .filter(param -> ALL_MSG_PARAM.equals(param.getKey()))
                    .findFirst()
                    .map(ExportParam::getValue)
                    .orElse("[WARNING] No message template!");
        }

        String fullName = entry.getFullName();
        String code = entry.getCode();
        Long number = entry.getNumber();
        String nickName = entry.getNickName();
        EntryStatus status = entry.getStatus();

        String entryStatusRU = "";
        switch (status) {
            case Accepted: {
                entryStatusRU = "Принятая запись";
                break;
            }
            case Voting: {
                entryStatusRU = "Предложенная запись";
                break;
            }
            case Accepting: {
                entryStatusRU = "Запись принимается";
                break;
            }
            case Reviewing: {
                entryStatusRU = "Запись оценивается";
                break;
            }
            case Rejected: {
                entryStatusRU = "Запись отклонена";
                break;
            }
            default: {
                entryStatusRU = "[unknown]";
            }
        }

        Map<String, Object> model = new HashMap<>();
        model.put("fullName", Strings.nullToEmpty(fullName));
        model.put("code", Strings.nullToEmpty(code));
        model.put("number", number != null ? number : "");
        model.put("nickName", Strings.nullToEmpty(nickName));
        model.put("status", Strings.nullToEmpty(status.getId()));
        model.put("status_RU", Strings.nullToEmpty(entryStatusRU));
        model.put("entry", entry);

        return TemplateHelper.processTemplate(formatString, model);
    }

    private List<ExportParam> getConsumerParams(ExportConsumer consumer) {
        return dataManager.load(ExportParam.class)
                .query("select ep " +
                        "from bl_ExportParam ep " +
                        "where ep.consumer = :consumer")
                .parameter("consumer", consumer)
                .list();
    }

    private List<ExportConsumer> getVkConsumers() {
        return dataManager.load(ExportConsumer.class)
                .query("select ec " +
                        "from bl_ExportConsumer ec " +
                        "where ec.type = :vkType " +
                        "   and ec.enabled = true")
                .parameter("vkType", ExportConsumerType.VK)
                .list();
    }

    private List<Integer> getConversations(Integer groupId, String accessToken) {
        GroupActor actor = new GroupActor(groupId, accessToken);
        List<Integer> result = new ArrayList<>();
        try {
            result = vkApiClient.messages()
                    .getConversations(actor)
                    .execute()
                    .getItems()
                    .stream()
                    .map(e -> e.getConversation().getPeer().getId())
                    .collect(Collectors.toList());
        } catch (ApiException | ClientException e) {
            e.printStackTrace();
        }
        return result;
    }

    private void sendMessage(Integer groupId,
                             String accessToken,
                             String message,
                             Integer peerId) {
        GroupActor actor = new GroupActor(groupId, accessToken);
        try {
            vkApiClient.messages()
                    .send(actor)
                    .message(message)
                    .peerId(peerId)
                    .randomId(new Random().nextInt())
                    .execute();
        } catch (ApiException | ClientException e) {
            e.printStackTrace();
        }
    }
}