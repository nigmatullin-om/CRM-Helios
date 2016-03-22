package com.becomejavasenior.dao.impl;

import com.becomejavasenior.model.DealStage;

public  class DealStageImpl {
   public static DealStage getDealStageById(int id){
      switch (id){
          case 1:
              return DealStage.PRIMARY_CONTACT;
          case 2:
              return DealStage.NEGOTIATION;
          case 3:
              return DealStage.DECIDING;
          case 4:
              return DealStage.AGREEMENT;
          case 5:
              return DealStage.FAILED_AND_CLOSED;
          default:
              return null;
      }
   }
}
