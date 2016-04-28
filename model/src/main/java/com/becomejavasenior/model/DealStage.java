package com.becomejavasenior.model;

import java.io.Serializable;

public enum DealStage implements Serializable {
    PRIMARY_CONTACT,
    NEGOTIATION,
    DECIDING,
    AGREEMENT,
    SUCCESS,
    FAILED_AND_CLOSED;

    public static DealStage getDealStageById(int id) {
        return DealStage.values()[id - 1];
    }

    public static int getIdForDealStage(DealStage dealStage) {
        return dealStage.ordinal() + 1;
    }
}
