package com.wu.myes.search;

import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchResponseSections;
import org.elasticsearch.action.search.ShardSearchFailure;
import org.elasticsearch.common.io.stream.StreamInput;
import org.elasticsearch.common.io.stream.StreamOutput;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.rest.RestStatus;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.profile.ProfileShardResult;
import org.elasticsearch.search.suggest.Suggest;

import java.io.IOException;
import java.util.Map;

public class MySearchResponse extends SearchResponse {

    private final SearchResponse searchResponse;

    public MySearchResponse(SearchResponse searchResponse) {
        this.searchResponse = searchResponse;
    }

    public MySearchResponse(SearchResponse searchResponse,SearchResponseSections internalResponse, String scrollId, int totalShards, int successfulShards, int skippedShards, long tookInMillis, ShardSearchFailure[] shardFailures, Clusters clusters) {
        super(internalResponse, scrollId, totalShards, successfulShards, skippedShards, tookInMillis, shardFailures, clusters);
        this.searchResponse =searchResponse;
    }

    @Override
    public RestStatus status() {
        return searchResponse.status();
    }

    @Override
    public SearchHits getHits() {
        return searchResponse.getHits();
    }

    @Override
    public Aggregations getAggregations() {
        return searchResponse.getAggregations();
    }

    @Override
    public Suggest getSuggest() {
        return searchResponse.getSuggest();
    }

    @Override
    public boolean isTimedOut() {
        return searchResponse.isTimedOut();
    }

    @Override
    public Boolean isTerminatedEarly() {
        return searchResponse.isTerminatedEarly();
    }

    @Override
    public int getNumReducePhases() {
        return searchResponse.getNumReducePhases();
    }

    @Override
    public TimeValue getTook() {
        return searchResponse.getTook();
    }

    @Override
    public int getTotalShards() {
        return searchResponse.getTotalShards();
    }

    @Override
    public int getSuccessfulShards() {
        return searchResponse.getSuccessfulShards();
    }

    @Override
    public int getSkippedShards() {
        return searchResponse.getSkippedShards();
    }

    @Override
    public int getFailedShards() {
        return searchResponse.getFailedShards();
    }

    @Override
    public ShardSearchFailure[] getShardFailures() {
        return searchResponse.getShardFailures();
    }

    @Override
    public String getScrollId() {
        return searchResponse.getScrollId();
    }

    @Override
    public void scrollId(String scrollId) {
        searchResponse.scrollId(scrollId);
    }

    @Override
    public Map<String, ProfileShardResult> getProfileResults() {
        return searchResponse.getProfileResults();
    }

    @Override
    public Clusters getClusters() {
        return searchResponse.getClusters();
    }

    @Override
    public XContentBuilder toXContent(XContentBuilder builder, Params params) throws IOException {
        return searchResponse.toXContent(builder, params);
    }

    @Override
    public XContentBuilder innerToXContent(XContentBuilder builder, Params params) throws IOException {
        return searchResponse.innerToXContent(builder, params);
    }

    @Override
    public void readFrom(StreamInput in) throws IOException {
        searchResponse.readFrom(in);
    }

    @Override
    public void writeTo(StreamOutput out) throws IOException {
        searchResponse.writeTo(out);
    }

    @Override
    public String toString() {
        return searchResponse.toString();
    }

    @Override
    public void remoteAddress(TransportAddress remoteAddress) {
        searchResponse.remoteAddress(remoteAddress);
    }

    @Override
    public TransportAddress remoteAddress() {
        return searchResponse.remoteAddress();
    }
}
