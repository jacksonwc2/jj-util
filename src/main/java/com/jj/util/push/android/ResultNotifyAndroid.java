package com.jj.util.push.android;

import java.util.List;

public class ResultNotifyAndroid {

    private Long multicast_id;

    private Long success;

    private Long failure;

    private Long canonical_ids;

    private List<ResultItem> results;

    public Long getSuccess() {
        return success;
    }

    public void setSuccess(Long success) {
        this.success = success;
    }

    public Long getFailure() {
        return failure;
    }

    public void setFailure(Long failure) {
        this.failure = failure;
    }

    public List<ResultItem> getResults() {
        return results;
    }

    public void setResults(List<ResultItem> results) {
        this.results = results;
    }

    public Long getMulticast_id() {
        return multicast_id;
    }

    public void setMulticast_id(Long multicast_id) {
        this.multicast_id = multicast_id;
    }

    public Long getCanonical_ids() {
        return canonical_ids;
    }

    public void setCanonical_ids(Long canonical_ids) {
        this.canonical_ids = canonical_ids;
    }

}
