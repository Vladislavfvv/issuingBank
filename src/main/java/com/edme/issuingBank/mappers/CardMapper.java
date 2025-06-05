package com.edme.issuingBank.mappers;

import com.edme.issuingBank.dto.CardDto;
import com.edme.issuingBank.dto.ClientDto;
import com.edme.issuingBank.dto.PaymentSystemDto;
import com.edme.issuingBank.models.*;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", uses = {
        CardStatusMapper.class,
        PaymentSystemMapper.class,
        AccountMapper.class,
        ClientMapper.class
})
public interface CardMapper {
    // CardMapper INSTANCE = Mappers.getMapper(CardMapper.class); //нужно только при ручном маппинге

//    @Mapping(target = "cardStatusId", source = "cardStatus")
//    @Mapping(target = "paymentSystemId", source = "paymentSystem")
//    @Mapping(target = "accountId", source = "account")
//    @Mapping(target = "client", source = "client")
    CardDto toDto(Card card);


//    @Mapping(target = "cardStatus", source = "cardStatus", qualifiedByName = "mapCardStatus")
//    @Mapping(target = "paymentSystem", source = "paymentSystem", qualifiedByName = "mapPaymentSystem")
//    @Mapping(target = "account", source = "account", qualifiedByName = "mapAccount")
//    @Mapping(target = "client", source = "client", qualifiedByName = "mapClient")
    Card toEntity(CardDto dto);

//    @Named("mapCardStatus")
//    default CardStatus mapCardStatus(Long id) {
//        if (id == null) {
//            return null;
//        }
//        CardStatus cardStatus = new CardStatus();
//        cardStatus.setId(id);
//        return cardStatus;
//    }
//
//    @Named("mapPaymentSystem")
//    default PaymentSystem mapPaymentSystem(PaymentSystemDto dto) {
//        if (dto == null) {
//            return null;
//        }
//        PaymentSystem paymentSystem = new PaymentSystem();
//        paymentSystem.setId(dto.getId());
//        return paymentSystem;
//    }
//
//    @Named("mapAccount")
//    default Account mapAccount(Long id) {
//        if (id == null) return null;
//        Account account = new Account();
//        account.setId(id);
//        return account;
//    }
//
//    @Named("mapClient")
//    default Client mapClient(ClientDto dto) {
//        if (dto == null) {
//            return null;
//        }
//        Client client = new Client();
//        client.setId(dto.getId());
//        return client;
//    }


}
