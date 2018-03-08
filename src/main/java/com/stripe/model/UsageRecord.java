package com.stripe.model;

import com.stripe.exception.*;
import com.stripe.net.APIResource;
import com.stripe.net.RequestOptions;

import java.util.Map;

public class UsageRecord extends APIResource implements HasId {
    String id;
    String object;
    Boolean livemode;
    Long quantity;
    String subscriptionItem;
    Long timestamp;

    @Override
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getObject() {
        return object;
    }

    public void setObject(String object) {
        this.object = object;
    }

    public Boolean getLivemode() {
        return livemode;
    }

    public void setLivemode(Boolean livemode) {
        this.livemode = livemode;
    }

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

    public String getSubscriptionItem() {
        return subscriptionItem;
    }

    public void setSubscriptionItem(String subscriptionItem) {
        this.subscriptionItem = subscriptionItem;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public static UsageRecord create(String subscriptionItem, Map<String, Object> params, RequestOptions options)
            throws AuthenticationException, InvalidRequestException,
            APIConnectionException, CardException, APIException {
        return request(RequestMethod.POST, subresourceURL(SubscriptionItem.class, subscriptionItem, UsageRecord.class), params, UsageRecord.class, options);
    }
}