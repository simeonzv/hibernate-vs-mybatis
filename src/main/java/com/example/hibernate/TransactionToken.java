package com.example.hibernate;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Table(name = "trans_token")
@Entity
public class TransactionToken {
    @Id
    @GeneratedValue
    private long id = -1;
    private String transaction = "";
    private String token = "";

    @ElementCollection(fetch = FetchType.EAGER)
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
