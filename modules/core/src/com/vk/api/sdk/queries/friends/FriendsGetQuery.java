package com.vk.api.sdk.queries.friends;

import com.vk.api.sdk.client.AbstractQueryBuilder;
import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.ServiceActor;
import com.vk.api.sdk.client.actors.UserActor;
import com.vk.api.sdk.objects.enums.FriendsNameCase;
import com.vk.api.sdk.objects.enums.FriendsOrder;
import com.vk.api.sdk.objects.friends.responses.GetResponse;
import com.vk.api.sdk.objects.users.Fields;

import java.util.Arrays;
import java.util.List;

/**
 * Query for Friends.get method
 */
public class FriendsGetQuery extends AbstractQueryBuilder<FriendsGetQuery, GetResponse> {
    /**
     * Creates a AbstractQueryBuilder instance that can be used to build api request with various parameters
     *
     * @param client VK API client
     * @param actor actor with access token
     */
    public FriendsGetQuery(VkApiClient client, UserActor actor) {
        super(client, "friends.get", GetResponse.class);
        accessToken(actor.getAccessToken());
    }

    /**
     * Creates a AbstractQueryBuilder instance that can be used to build api request with various parameters
     *
     * @param client VK API client
     * @param actor actor with access token
     */
    public FriendsGetQuery(VkApiClient client, ServiceActor actor) {
        super(client, "friends.get", GetResponse.class);
        accessToken(actor.getAccessToken());
        clientSecret(actor.getClientSecret());
    }

    /**
     * User ID. By default, the current user ID.
     *
     * @param value value of "user id" parameter.
     * @return a reference to this {@code AbstractQueryBuilder} object to fulfill the "Builder" pattern.
     */
    public FriendsGetQuery userId(Integer value) {
        return unsafeParam("user_id", value);
    }

    /**
     * Sort order: , 'name' — by name (enabled only if the 'fields' parameter is used), 'hints' — by rating, similar to how friends are sorted in My friends section, , This parameter is available only for [vk.com/dev/standalone|desktop applications].
     *
     * @param value value of "order" parameter.
     * @return a reference to this {@code AbstractQueryBuilder} object to fulfill the "Builder" pattern.
     */
    public FriendsGetQuery order(FriendsOrder value) {
        return unsafeParam("order", value);
    }

    /**
     * ID of the friend list returned by the [vk.com/dev/friends.getLists|friends.getLists] method to be used as the source. This parameter is taken into account only when the uid parameter is set to the current user ID. This parameter is available only for [vk.com/dev/standalone|desktop applications].
     *
     * @param value value of "list id" parameter. Minimum is 0.
     * @return a reference to this {@code AbstractQueryBuilder} object to fulfill the "Builder" pattern.
     */
    public FriendsGetQuery listId(Integer value) {
        return unsafeParam("list_id", value);
    }

    /**
     * Number of friends to return.
     *
     * @param value value of "count" parameter. Minimum is 0. By default 5000.
     * @return a reference to this {@code AbstractQueryBuilder} object to fulfill the "Builder" pattern.
     */
    public FriendsGetQuery count(Integer value) {
        return unsafeParam("count", value);
    }

    /**
     * Offset needed to return a specific subset of friends.
     *
     * @param value value of "offset" parameter. Minimum is 0.
     * @return a reference to this {@code AbstractQueryBuilder} object to fulfill the "Builder" pattern.
     */
    public FriendsGetQuery offset(Integer value) {
        return unsafeParam("offset", value);
    }

    /**
     * Case for declension of user name and surname: , 'nom' — nominative (default) , 'gen' — genitive , 'dat' — dative , 'acc' — accusative , 'ins' — instrumental , 'abl' — prepositional
     *
     * @param value value of "name case" parameter.
     * @return a reference to this {@code AbstractQueryBuilder} object to fulfill the "Builder" pattern.
     */
    public FriendsGetQuery nameCase(FriendsNameCase value) {
        return unsafeParam("name_case", value);
    }

    /**
     * Set ref
     *
     * @param value value of "ref" parameter.
     * @return a reference to this {@code AbstractQueryBuilder} object to fulfill the "Builder" pattern.
     */
    public FriendsGetQuery ref(String value) {
        return unsafeParam("ref", value);
    }

    /**
     * fields
     * Profile fields to return. Sample values: 'uid', 'first_name', 'last_name', 'nickname', 'sex', 'bdate' (birthdate), 'city', 'country', 'timezone', 'photo', 'photo_medium', 'photo_big', 'domain', 'has_mobile', 'rate', 'contacts', 'education'.
     *
     * @param value value of "fields" parameter.
     * @return a reference to this {@code AbstractQueryBuilder} object to fulfill the "Builder" pattern.
     */
    public FriendsGetQuery fields(Fields... value) {
        return unsafeParam("fields", value);
    }

    /**
     * Profile fields to return. Sample values: 'uid', 'first_name', 'last_name', 'nickname', 'sex', 'bdate' (birthdate), 'city', 'country', 'timezone', 'photo', 'photo_medium', 'photo_big', 'domain', 'has_mobile', 'rate', 'contacts', 'education'.
     *
     * @param value value of "fields" parameter.
     * @return a reference to this {@code AbstractQueryBuilder} object to fulfill the "Builder" pattern.
     */
    public FriendsGetQuery fields(List<Fields> value) {
        return unsafeParam("fields", value);
    }

    @Override
    protected FriendsGetQuery getThis() {
        return this;
    }

    @Override
    protected List<String> essentialKeys() {
        return Arrays.asList("access_token");
    }
}