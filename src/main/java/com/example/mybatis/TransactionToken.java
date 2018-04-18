package com.example.mybatis;

import java.util.ArrayList;
import java.util.List;

public class TransactionToken {
    private long id = -1;
    private String transaction = "";
    private String token = "";
    private List<String> referenceIds = new ArrayList<>();

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTransaction() {
        return transaction;
    }

    public void setTransaction(String transaction) {
        this.transaction = transaction;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void addReferenceId(String referenceId) {
        referenceIds.add(referenceId);
    }

    public void removeReferenceId(String referenceId) {
        referenceIds.remove(referenceId);
    }

    public List<String> getReferenceIds() {
        return referenceIds;
    }

}
