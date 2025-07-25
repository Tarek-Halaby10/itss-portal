package com.itss.portal.authentication.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.passay.*;

import java.util.List;
import java.util.stream.Collectors;

public class PasswordConstraintValidator
    implements ConstraintValidator<ValidPassword, String> {

    private final PasswordValidator validator = new PasswordValidator(List.of(
        // length 8–128
        new LengthRule(8, 128),
        // at least one upper-case character
        new CharacterRule(EnglishCharacterData.UpperCase, 1),
        // at least one digit
        new CharacterRule(EnglishCharacterData.Digit, 1),
        // no whitespace
        new WhitespaceRule()
    ));

    @Override
    public boolean isValid(String pwd, ConstraintValidatorContext ctx) {
        RuleResult result = validator.validate(new PasswordData(pwd));
        if (result.isValid()) {
            return true;
        }
        String messages = validator.getMessages(result)
            .stream().collect(Collectors.joining("; "));
        ctx.disableDefaultConstraintViolation();
        ctx.buildConstraintViolationWithTemplate(messages)
           .addConstraintViolation();
        return false;
    }
}
