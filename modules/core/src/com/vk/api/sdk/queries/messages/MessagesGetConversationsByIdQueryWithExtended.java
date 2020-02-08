package com.vk.api.sdk.queries.messages;

import com.vk.api.sdk.client.AbstractQueryBuilder;
import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.GroupActor;
import com.vk.api.sdk.client.actors.UserActor;
import com.vk.api.sdk.objects.base.UserGroupFields;
import com.vk.api.sdk.objects.messages.responses.GetConversationsByIdExtendedResponse;

import java.util.Arrays;
import java.util.List;

/**
 * Query for Messages.getConversationsById method
 */
public class MessagesGetConversationsByIdQueryWithExtended extends AbstractQueryBuilder<MessagesGetConversationsByIdQueryWithExtended, GetConversationsByIdExtendedResponse> {
    /**
     * Creates a AbstractQueryBuilder instance that can be used to build api request with various parameters
     *
     * @param client VK API client
     * @param actor actor with access token
     * @param peerIds value of "peer ids" parameter.
     */
    public MessagesGetConversationsByIdQueryWithExtended(VkApiClient client, UserActor actor,
            Integer... peerIds) {
        super(client, "messages.getConversationsById", GetConversationsByIdExtendedResponse.class);
        accessToken(actor.getAccessToken());
        peerIds(peerIds);
        extended(true);
    }

    /**
     * Creates a AbstractQueryBuilder instance that can be used to build api request with various parameters
     *
     * @param client VK API client
     * @param actor actor with access token
     * @param peerIds value of "peer ids" parameter.
     */
    public MessagesGetConversationsByIdQueryWithExtended(VkApiClient client, UserActor actor,
            List<Integer> peerIds) {
        super(client, "messages.getConversationsById", GetConversationsByIdExtendedResponse.class);
        accessToken(actor.getAccessToken());
        peerIds(peerIds);
        extended(true);
    }

    /**
     * Creates a AbstractQueryBuilder instance that can be used to build api request with various parameters
     *
     * @param client VK API client
     * @param actor actor with access token
     * @param peerIds value of "peer ids" parameter.
     */
    public MessagesGetConversationsByIdQueryWithExtended(VkApiClient client, GroupActor actor,
            Integer... peerIds) {
        super(client, "messages.getConversationsById", GetConversationsByIdExtendedResponse.class);
        accessToken(actor.getAccessToken());
        groupId(actor.getGroupId());
        peerIds(peerIds);
        extended(true);
    }

    /**
     * Creates a AbstractQueryBuilder instance that can be used to build api request with various parameters
     *
     * @param client VK API client
     * @param actor actor with access token
     * @param peerIds value of "peer ids" parameter.
     */
    public MessagesGetConversationsByIdQueryWithExtended(VkApiClient client, GroupActor actor,
            List<Integer> peerIds) {
        super(client, "messages.getConversationsById", GetConversationsByIdExtendedResponse.class);
        accessToken(actor.getAccessToken());
        groupId(actor.getGroupId());
        peerIds(peerIds);
        extended(true);
    }

    /**
     * Return extended properties
     *
     * @param value value of "extended" parameter.
     * @return a reference to this {@code AbstractQueryBuilder} object to fulfill the "Builder" pattern.
     */
    protected MessagesGetConversationsByIdQueryWithExtended extended(Boolean value) {
        return unsafeParam("extended", value);
    }

    /**
     * Group ID (for group messages with group access token)
     *
     * @param value value of "group id" parameter. Minimum is 0.
     * @return a reference to this {@code AbstractQueryBuilder} object to fulfill the "Builder" pattern.
     */
    public MessagesGetConversationsByIdQueryWithExtended groupId(Integer value) {
        return unsafeParam("group_id", value);
    }

    /**
     * peer_ids
     * Destination IDs. "For user: 'User ID', e.g. '12345'. For chat: '2000000000' + 'chat_id', e.g. '2000000001'. For community: '- community ID', e.g. '-12345'. "
     *
     * @param value value of "peer ids" parameter.
     * @return a reference to this {@code AbstractQueryBuilder} object to fulfill the "Builder" pattern.
     */
    protected MessagesGetConversationsByIdQueryWithExtended peerIds(Integer... value) {
        return unsafeParam("peer_ids", value);
    }

    /**
     * Destination IDs. "For user: 'User ID', e.g. '12345'. For chat: '2000000000' + 'chat_id', e.g. '2000000001'. For community: '- community ID', e.g. '-12345'. "
     *
     * @param value value of "peer ids" parameter.
     * @return a reference to this {@code AbstractQueryBuilder} object to fulfill the "Builder" pattern.
     */
    protected MessagesGetConversationsByIdQueryWithExtended peerIds(List<Integer> value) {
        return unsafeParam("peer_ids", value);
    }

    /**
     * fields
     * Profile and communities fields to return.
     *
     * @param value value of "fields" parameter.
     * @return a reference to this {@code AbstractQueryBuilder} object to fulfill the "Builder" pattern.
     */
    public MessagesGetConversationsByIdQueryWithExtended fields(UserGroupFields... value) {
        return unsafeParam("fields", value);
    }

    /**
     * Profile and communities fields to return.
     *
     * @param value value of "fields" parameter.
     * @return a reference to this {@code AbstractQueryBuilder} object to fulfill the "Builder" pattern.
     */
    public MessagesGetConversationsByIdQueryWithExtended fields(List<UserGroupFields> value) {
        return unsafeParam("fields", value);
    }

    @Override
    protected MessagesGetConversationsByIdQueryWithExtended getThis() {
        return this;
    }

    @Override
    protected List<String> essentialKeys() {
        return Arrays.asList("peer_ids", "access_token");
    }
}
