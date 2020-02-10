package com.ragenotes.blacklist.core;

import com.haulmont.bali.util.Preconditions;
import com.haulmont.cuba.core.app.importexport.EntityImportExportService;
import com.haulmont.cuba.core.global.DataManager;
import com.haulmont.cuba.core.global.Metadata;
import com.haulmont.cuba.core.global.View;
import com.ragenotes.blacklist.entity.ExportConsumer;
import com.ragenotes.blacklist.entity.ExportConsumerType;
import com.ragenotes.blacklist.entity.ExportParam;
import com.ragenotes.blacklist.entity.entries.BlackListEntry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component(PostNotificationBean.NAME)
public class PostNotificationBean {

    public static final String NAME = "bl_PostNotificationBean";

    private static final String ENTRY_JSON_PARAM = "json_entry";
    private static final String MESSAGE_PARAM = "message";
    private static final String NOTIFICATION_TYPE_PARAM = "n-type";

    private static final String NEW_ENTRY_TYPE_PARAM = "new_entry_type"; // "new_entry"
    private static final String ACCEPTED_ENTRY_TYPE_PARAM = "accepted_entry_type"; // "accepted_entry"
    private static final String ACCEPTING_ENTRY_TYPE_PARAM = "accepting_entry_type"; // "accepting_entry"
    private static final String REJECTED_ENTRY_TYPE_PARAM = "rejected_entry_type"; // "rejected_entry"
    private static final String REVIEWING_ENTRY_TYPE_PARAM = "reviewing_entry_type"; // "reviewing_entry"
    private static final String MESSAGE_ENTRY_TYPE_PARAM = "msg_type"; // "dist_message"

    private static final String SERVER_TOKEN_HEADER = "server-token";
    private static final String NOTIFICATION_TYPE_HEADER = "notification-type";


    private static final Logger log = LoggerFactory.getLogger(PostNotificationBean.class);

    @Inject
    private EntityImportExportService entityImportExportService;

    @Inject
    private DataManager dataManager;
    @Inject
    private Metadata metadata;

    public void notifyNewEntry(BlackListEntry entry) {
        Preconditions.checkNotNullArgument(entry);

        List<ExportConsumer> postConsumers = getPostConsumers();

        String entryJson = convertEntryToJson(entry);

        for (ExportConsumer consumer : postConsumers) {
            if (!consumer.getIsNewConsumer()) {
                continue;
            }

            String url = consumer.getUrl();
            String token = consumer.getToken();

            List<ExportParam> params = getConsumerParams(consumer);

            String messageType = getMessageType(params, NEW_ENTRY_TYPE_PARAM,
                    "new_entry");

            Map<Object, Object> notificationModel = new HashMap<>();

            notificationModel.put(ENTRY_JSON_PARAM, entryJson);
            notificationModel.put(NOTIFICATION_TYPE_PARAM, messageType);

            sendMessage(url, messageType, token, notificationModel);
        }
    }

    public void notifyReviewingEntry(BlackListEntry entry) {
        Preconditions.checkNotNullArgument(entry);

        List<ExportConsumer> postConsumers = getPostConsumers();

        String entryJson = convertEntryToJson(entry);

        for (ExportConsumer consumer : postConsumers) {
            if (!consumer.getIsReviewingConsumer()) {
                continue;
            }

            String url = consumer.getUrl();
            String token = consumer.getToken();

            List<ExportParam> params = getConsumerParams(consumer);

            String messageType = getMessageType(params, REVIEWING_ENTRY_TYPE_PARAM,
                    "reviewing_entry");

            Map<Object, Object> notificationModel = new HashMap<>();

            notificationModel.put(ENTRY_JSON_PARAM, entryJson);
            notificationModel.put(NOTIFICATION_TYPE_PARAM, messageType);

            sendMessage(url, messageType, token, notificationModel);
        }
    }

    public void notifyAcceptingEntry(BlackListEntry entry) {
        Preconditions.checkNotNullArgument(entry);

        List<ExportConsumer> postConsumers = getPostConsumers();

        String entryJson = convertEntryToJson(entry);

        for (ExportConsumer consumer : postConsumers) {
            if (!consumer.getIsAcceptingConsumer()) {
                continue;
            }

            String url = consumer.getUrl();
            String token = consumer.getToken();

            List<ExportParam> params = getConsumerParams(consumer);

            String messageType = getMessageType(params, ACCEPTING_ENTRY_TYPE_PARAM,
                    "accepting_entry");

            Map<Object, Object> notificationModel = new HashMap<>();

            notificationModel.put(ENTRY_JSON_PARAM, entryJson);
            notificationModel.put(NOTIFICATION_TYPE_PARAM, messageType);

            sendMessage(url, messageType, token, notificationModel);
        }
    }

    public void notifyAcceptedEntry(BlackListEntry entry) {
        Preconditions.checkNotNullArgument(entry);

        List<ExportConsumer> postConsumers = getPostConsumers();

        String entryJson = convertEntryToJson(entry);

        for (ExportConsumer consumer : postConsumers) {
            if (!consumer.getIsAcceptedConsumer()) {
                continue;
            }

            String url = consumer.getUrl();
            String token = consumer.getToken();

            List<ExportParam> params = getConsumerParams(consumer);

            String messageType = getMessageType(params, ACCEPTED_ENTRY_TYPE_PARAM,
                    "accepted_entry");

            Map<Object, Object> notificationModel = new HashMap<>();

            notificationModel.put(ENTRY_JSON_PARAM, entryJson);
            notificationModel.put(NOTIFICATION_TYPE_PARAM, messageType);

            sendMessage(url, messageType, token, notificationModel);
        }
    }

    public void notifyRejectedEntry(BlackListEntry entry) {
        Preconditions.checkNotNullArgument(entry);

        List<ExportConsumer> postConsumers = getPostConsumers();

        String entryJson = convertEntryToJson(entry);

        for (ExportConsumer consumer : postConsumers) {
            if (!consumer.getIsRejectedConsumer()) {
                continue;
            }

            String url = consumer.getUrl();
            String token = consumer.getToken();

            List<ExportParam> params = getConsumerParams(consumer);

            String messageType = getMessageType(params, REJECTED_ENTRY_TYPE_PARAM,
                    "rejected_entry");

            Map<Object, Object> notificationModel = new HashMap<>();

            notificationModel.put(ENTRY_JSON_PARAM, entryJson);
            notificationModel.put(NOTIFICATION_TYPE_PARAM, messageType);

            sendMessage(url, messageType, token, notificationModel);
        }
    }

    public void notifyCustomMessage(String message) {
        Preconditions.checkNotEmptyString(message);

        List<ExportConsumer> postConsumers = getPostConsumers();

        for (ExportConsumer consumer : postConsumers) {
            if (!consumer.getIsDistributionConsumer()) {
                continue;
            }

            String url = consumer.getUrl();
            String token = consumer.getToken();

            List<ExportParam> params = getConsumerParams(consumer);

            String messageType = getMessageType(params, MESSAGE_ENTRY_TYPE_PARAM,
                    "dist_message");

            Map<Object, Object> notificationModel = new HashMap<>();

            notificationModel.put(MESSAGE_PARAM, message);
            notificationModel.put(NOTIFICATION_TYPE_PARAM, messageType);

            sendMessage(url, messageType, token, notificationModel);
        }
    }

    private List<ExportConsumer> getPostConsumers() {
        return dataManager.load(ExportConsumer.class)
                .query("select ec " +
                        "from bl_ExportConsumer ec " +
                        "where ec.type = :postType " +
                        "   and ec.enabled = true")
                .parameter("postType", ExportConsumerType.PostConsumer)
                .list();
    }

    private String convertEntryToJson(BlackListEntry entry) {
        return entityImportExportService.exportEntitiesToJSON(Collections.singleton(entry),
                metadata.getViewRepository().getView(BlackListEntry.class, View.LOCAL));
    }

    private void sendMessage(String url,
                             String notificationType,
                             String token,
                             Map<Object, Object> messageData) {
        HttpRequest request = HttpRequest.newBuilder()
                .POST(ofFormedData(messageData))
                .uri(URI.create(url))
                .setHeader("User-Agent", "BlackList Notification Bot")
                .header(SERVER_TOKEN_HEADER, token)
                .header(NOTIFICATION_TYPE_HEADER, notificationType)
                .build();

        sendPost(request);
    }

    public static HttpRequest.BodyPublisher ofFormedData(Map<Object, Object> data) {
        StringBuilder builder = new StringBuilder();
        for (Map.Entry<Object, Object> entry : data.entrySet()) {
            if (builder.length() > 0) {
                builder.append("&");
            }
            builder.append(URLEncoder.encode(entry.getKey().toString(), StandardCharsets.UTF_8));
            builder.append("=");
            builder.append(URLEncoder.encode(entry.getValue().toString(), StandardCharsets.UTF_8));
        }
        return HttpRequest.BodyPublishers.ofString(builder.toString());
    }

    private String getMessageType(List<ExportParam> params, String nameType, String defaultType) {
        return params.stream()
                .filter(param -> nameType.equals(param.getKey()))
                .findFirst()
                .map(ExportParam::getValue)
                .orElse(defaultType);
    }

    private void sendPost(HttpRequest request) {
        HttpClient httpClient = HttpClient.newBuilder()
                .followRedirects(HttpClient.Redirect.NEVER)
                .connectTimeout(Duration.ofSeconds(30L))
                .build();

        try {
            HttpResponse<Void> response = httpClient.send(request, HttpResponse.BodyHandlers.discarding());
            if (response.statusCode() != 200) {
                log.info("POST Consumer rejected notification with code {}", response.statusCode());
            }
        } catch (IOException | InterruptedException e) {
            log.warn("POST Consumer rejected notification");
        }
    }

    private List<ExportParam> getConsumerParams(ExportConsumer consumer) {
        return dataManager.load(ExportParam.class)
                .query("select ep " +
                        "from bl_ExportParam ep " +
                        "where ep.consumer = :consumer")
                .parameter("consumer", consumer)
                .list();
    }
}