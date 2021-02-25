package com.epam.esm.exception;

import com.epam.esm.util.ErrorCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
public class ExceptionProvider {

    private final ResourceBundleMessageSource messageSource;

    @Autowired
    public ExceptionProvider(ResourceBundleMessageSource messageSource) {
        this.messageSource = messageSource;
    }

    public WrongParameterFormatException wrongParameterFormatException(ErrorCode error) {
        Locale locale = LocaleContextHolder.getLocale();
        String message = messageSource.getMessage(error.getMessageKey(), null, locale);
        return new WrongParameterFormatException(message, error.getMessageCode());
    }

    public GiftEntityNotFoundException giftEntityNotFoundException(ErrorCode error) {
        Locale locale = LocaleContextHolder.getLocale();
        String message = messageSource.getMessage(error.getMessageKey(), null, locale);
        return new GiftEntityNotFoundException(message, error.getMessageCode());
    }
}
