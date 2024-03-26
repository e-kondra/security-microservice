package com.autoservice.security.business.mappers;

import com.autoservice.security.business.repository.TokenDAO;
import com.autoservice.security.models.Role;
import com.autoservice.security.models.Token;
import com.autoservice.security.models.TokenType;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;

@Mapper(componentModel = "spring", uses = {
       UserMapStructMapper.class})
public interface TokenMapStructMapper {

    @Mappings({
            @Mapping(target = "tokenType", source = "tokenType", qualifiedByName = "tokenType")
    })
    TokenDAO tokenToTokenDAO (Token token);
    @Mappings({
            @Mapping(target = "tokenType", source = "tokenType", qualifiedByName = "tokenType")
    })
    Token tokenDAOToToken(TokenDAO tokenDAO);

    @Named("tokenType")
    default String enumTokenTypeToStringTokenType(TokenType tokenType){
        return tokenType.toString();
    }

    @Named("tokenType")
    default TokenType StringTokenTypeToEnumTokenType (String tokenTypeString){
        return TokenType.valueOf(tokenTypeString);
    }
}
