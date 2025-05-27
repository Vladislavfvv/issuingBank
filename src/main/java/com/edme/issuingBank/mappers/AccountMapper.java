package com.edme.issuingBank.mappers;

import com.edme.issuingBank.dto.AccountDto;
import com.edme.issuingBank.models.Account;
import com.edme.issuingBank.models.Client;
import com.edme.issuingBank.models.Currency;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring",//автоматическое создание бина т.к. есть spring и ручное как написано ниже не надо
        uses = {
        Currency.class,
        AccountMapper.class,
        Client.class
})
public interface AccountMapper {
//    AccountMapper INSTANCE = Mappers.getMapper(AccountMapper.class); вручную создание бина

    @Mapping(target = "currencyId", source = "currency")
    @Mapping(target = "accountTypeId", source = "accountTypeId")
    @Mapping(target = "clientId", source = "client")
    AccountDto toDto(Account account);


    @Mapping(target = "currency", source = "currencyId", qualifiedByName = "mapCurrency")
    @Mapping(target = "account", source = "accountId", qualifiedByName = "mapAccount")
    @Mapping(target = "client", source = "clientId", qualifiedByName = "mapClient")
    Account toEntity(AccountDto dto);

    @Named("mapAccount")
    default Currency mapCurrency(Long id) {
        if (id == null) return null;
        Currency currency = new Currency();
        currency.setId(id);
        return currency;
    }

    @Named("mapAccount")
    default Account mapAccount(Long id) {
        if (id == null) return null;
        Account account = new Account();
        account.setId(id);
        return account;
    }

    @Named("mapAccount")
    default Client mapClient(Long id) {
        if (id == null) return null;
        Client client = new Client();
        client.setId(id);
        return client;
    }
}
