package com.edme.issuingBank.mappers;

import com.edme.issuingBank.dto.AccountDto;
import com.edme.issuingBank.models.Account;
import com.edme.issuingBank.models.AccountType;
import com.edme.issuingBank.models.Client;
import com.edme.issuingBank.models.Currency;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring",//автоматическое создание бина т.к. есть spring и ручное как написано ниже не надо
        uses = {
        Currency.class,
        AccountType.class,
        Client.class
})
public interface AccountMapper {
//    AccountMapper INSTANCE = Mappers.getMapper(AccountMapper.class); вручную создание бина

    @Mapping(target = "currencyId", source = "currency")
    @Mapping(target = "accountTypeId", source = "accountTypeId")
    @Mapping(target = "clientId", source = "client")
    AccountDto toDto(Account account);


    @Mapping(target = "currency", source = "currencyId", qualifiedByName = "mapCurrency")
    @Mapping(target = "account", source = "accountTypeId", qualifiedByName = "mapAccountType")
    @Mapping(target = "client", source = "clientId", qualifiedByName = "mapClient")
    Account toEntity(AccountDto dto);

    @Named("mapCurrency")
    default Currency mapCurrency(Long id) {
        if (id == null) return null;
        Currency currency = new Currency();
        currency.setId(id);
        return currency;
    }

    @Named("mapAccountType")
    default AccountType mapAccountType(Long id) {
        if (id == null) return null;
        AccountType accountType = new AccountType();
        accountType.setId(id);
        return accountType;
    }

    @Named("mapClient")
    default Client mapClient(Long id) {
        if (id == null) return null;
        Client client = new Client();
        client.setId(id);
        return client;
    }
}
