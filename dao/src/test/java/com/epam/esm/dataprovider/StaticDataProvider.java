package com.epam.esm.dataprovider;

import com.epam.esm.model.entity.GiftCertificate;
import com.epam.esm.model.entity.Tag;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class StaticDataProvider {
    public static final String ISO_DATE = "2020-12-16T14:48Z";
    public static final Tag TAG;
    public static final Tag ADDING_TAG;
    public static final GiftCertificate ADDING_CERTIFICATE;
    public static final GiftCertificate UPDATING_GIFT_CERTIFICATE;

    public StaticDataProvider() {
    }

    static {
        ADDING_CERTIFICATE = new GiftCertificate(
                null,
                "English courses",
                "English courses in school of foreign languages SkyEng",
                BigDecimal.valueOf(250.00),
                180,
                "2021-01-13T12:42Z",
                "2021-01-13T12:42Z",
                new ArrayList<>(List.of(new Tag(1L, "Activity")))
        );
        UPDATING_GIFT_CERTIFICATE = new GiftCertificate(
                1L,
                "Spanish courses",
                "Spanish courses in school of foreign languages SkySpain",
                BigDecimal.valueOf(255.00),
                180,
                "2021-01-13T12:42Z",
                "2021-01-13T12:42Z",
                List.of(new Tag(1L, "Activity"), new Tag(8L, "Relaxation")));
        TAG = new Tag(1L, "Knitting");
        ADDING_TAG = new Tag();
        ADDING_TAG.setName("Knitting");
    }
}