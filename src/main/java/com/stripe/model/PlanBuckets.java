package com.stripe.model;

public class PlanBuckets {
    Long bucketSize;
    String mode;

    public Long getBucketSize() {
        return bucketSize;
    }

    public void setBucketSize(Long bucketSize) {
        this.bucketSize = bucketSize;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }
}
