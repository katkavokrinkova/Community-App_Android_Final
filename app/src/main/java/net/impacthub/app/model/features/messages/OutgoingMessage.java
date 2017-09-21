
/*
 * Copyright (c) 2017 Lightful. All Rights Reserved.
 *
 * Save to the extent permitted by law, you may not use, copy, modify,
 * distribute or create derivative works of this material or any part
 * of it without the prior written consent of Lightful.
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 */

package net.impacthub.app.model.features.messages;

import com.google.gson.GsonBuilder;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

@SuppressWarnings("FieldCanBeLocal")
public class OutgoingMessage {

    @SerializedName("body")
    @Expose
    private final String body;
    @SerializedName("recipients")
    @Expose
    private final List<String> recipients;
    @SerializedName("inReplyTo")
    @Expose
    private final String inReplyTo;

    private OutgoingMessage(Builder builder) {
        body = builder.body;
        recipients = builder.recipients;
        inReplyTo = builder.inReplyTo;
    }

    public JSONObject toJson() {
        try {
            return new JSONObject(new GsonBuilder().create().toJson(this));
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

    public static final class Builder {
        private String body;
        private List<String> recipients;
        private String inReplyTo;

        public Builder() {
        }

        public Builder body(String body) {
            this.body = body;
            return this;
        }

        public Builder recipients(List<String> recipients) {
            this.recipients = recipients;
            return this;
        }

        public Builder inReplyTo(String inReplyTo) {
            this.inReplyTo = inReplyTo;
            return this;
        }

        public OutgoingMessage build() {
            return new OutgoingMessage(this);
        }
    }
}
