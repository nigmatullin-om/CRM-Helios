package com.becomejavasenior.model;

import java.io.Serializable;

public enum DealStage implements Serializable {
    PRIMARY_CONTACT,
    NEGOTIATION,
    DECIDING,
    AGREEMENT,
    SUCCESS,
    FAILED_AND_CLOSED
}
