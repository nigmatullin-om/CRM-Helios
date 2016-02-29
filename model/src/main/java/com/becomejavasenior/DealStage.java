package com.becomejavasenior;

import java.io.Serializable;

enum DealStage implements Serializable {
    PRIMARY_CONTACT,
    NEGOTIATION,
    DECIDING,
    AGREEMENT,
    SUCCESS,
    FAILED_AND_CLOSED
}
