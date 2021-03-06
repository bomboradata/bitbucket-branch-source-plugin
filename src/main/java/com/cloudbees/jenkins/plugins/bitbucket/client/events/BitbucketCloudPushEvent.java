/*
 * The MIT License
 *
 * Copyright (c) 2016, CloudBees, Inc.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.cloudbees.jenkins.plugins.bitbucket.client.events;

import com.cloudbees.jenkins.plugins.bitbucket.api.BitbucketPushEvent;
import com.cloudbees.jenkins.plugins.bitbucket.api.BitbucketRepository;
import com.cloudbees.jenkins.plugins.bitbucket.client.repository.BitbucketCloudRepository;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class BitbucketCloudPushEvent implements BitbucketPushEvent {

    private BitbucketCloudRepository repository;

    @JsonProperty
    private Push push;

    @Override
    public BitbucketRepository getRepository() {
        return repository;
    }

    public void setRepository(BitbucketCloudRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<ChangeImpl> getChanges() {
        return push == null || push.changes == null
                ? Collections.<ChangeImpl>emptyList()
                : Collections.unmodifiableList(push.changes);
    }

    public void setChanges(List<ChangeImpl> changes) {
        this.push = new Push();
        this.push.changes = changes != null ? new ArrayList<>(changes) : new ArrayList<ChangeImpl>();
    }

    public static class Push {
        @JsonProperty
        private List<ChangeImpl> changes;

    }

    public static class ChangeImpl implements Change {
        private ReferenceImpl newRef;
        private ReferenceImpl oldRef;
        private boolean created;
        private boolean closed;

        @Override
        public ReferenceImpl getNew() {
            return newRef;
        }

        public void setNew(ReferenceImpl newRef) {
            this.newRef = newRef;
        }

        @Override
        public ReferenceImpl getOld() {
            return oldRef;
        }

        public void setOld(ReferenceImpl oldRef) {
            this.oldRef = oldRef;
        }

        @Override
        public boolean isCreated() {
            return created;
        }

        public void setCreated(boolean created) {
            this.created = created;
        }

        @Override
        public boolean isClosed() {
            return closed;
        }

        public void setClosed(boolean closed) {
            this.closed = closed;
        }

    }

    public static class ReferenceImpl implements Reference {
        private Date date;
        private String type;
        private String name;
        private TargetImpl target;

        @Override
        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        @Override
        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        @Override
        public TargetImpl getTarget() {
            return target;
        }

        public void setTarget(TargetImpl target) {
            this.target = target;
        }

        @Override
        public Date getDate() {
            return date != null ? (Date) date.clone() : null;
        }

        public void setDate(Date date) {
            this.date = (date != null ? (Date) date.clone() : null);
        }
    }

    public static class TargetImpl implements Target {
        private String hash;
        private Date date;

        @Override
        public String getHash() {
            return hash;
        }

        public void setHash(String hash) {
            this.hash = hash;
        }

        @Override
        public Date getDate() {
            return date != null ? (Date) date.clone() : null;
        }

        public void setDate(Date date) {
            this.date = (date != null ? (Date) date.clone() : null);
        }
    }

}
