package com.becomejavasenior.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public enum DealStage implements Serializable {
    PRIMARY_CONTACT("первичный контакт"),
    NEGOTIATION("переговоры"),
    DECIDING("принимают решение"),
    AGREEMENT("согласование договора"),
    SUCCESS("успешно реализовано"),
    FAILED_AND_CLOSED("закрыто и не реализовано");

    private final String name;

    DealStage(String name) { this.name = name;
    }

    public static DealStage getDealStageById(int id){
        for(DealStage dealStage : DealStage.values()){
            if(dealStage.ordinal() == id){
                return dealStage;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return name;
    }

    public static List<String> getAllDealNames(){
        ArrayList<String> allDealNames = new ArrayList<>();
        for(DealStage dealStage : DealStage.values()) allDealNames.add(dealStage.name);
        return allDealNames;
    }
}
