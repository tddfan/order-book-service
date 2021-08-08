package com.coinbase.orderbookservice.bean;

import java.math.BigDecimal;

public class SnapshotMessage extends Message {

    private BigDecimal[][] bids;
    private BigDecimal[][] asks;

    public SnapshotMessage() {
    }

    public BigDecimal[][] getBids() {
        return bids;
    }

    public void setBids(BigDecimal[][] bids) {
        this.bids = bids;
    }

    public BigDecimal[][] getAsks() {
        return asks;
    }

    public void setAsks(BigDecimal[][] asks) {
        this.asks = asks;
    }
}
