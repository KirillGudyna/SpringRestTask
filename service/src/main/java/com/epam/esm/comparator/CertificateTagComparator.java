package com.epam.esm.comparator;

import com.epam.esm.model.entity.GiftCertificate;

import java.util.Comparator;
import java.util.Optional;

public class CertificateTagComparator {
    private static final String DESC = "desc";
    private static final String ASC = "asc";

    private CertificateTagComparator() {
    }

    public static Optional<Comparator<GiftCertificate>> provide(String type, String direction) {
        Optional<Comparator<GiftCertificate>> optional;
        if (type != null) {
            try {
                CertificateTagComparator.SortType sortType = CertificateTagComparator.SortType.valueOf(type.toUpperCase());
                if (direction != null) {
                    if (direction.equals(DESC)) {
                        optional = Optional.of(sortType.getComparatorDesc());
                    } else if (direction.equals(ASC)) {
                        optional = Optional.of(sortType.getComparatorAsc());
                    } else {
                        optional = Optional.empty();
                    }
                } else {
                    optional = Optional.of(sortType.getComparatorAsc());
                }
            } catch (IllegalArgumentException var4) {
                optional = Optional.empty();
            }
        } else {
            optional = Optional.empty();
        }

        return optional;
    }

    private enum SortType {
        NAME((c1, c2) -> {
            return c1.getName().compareTo(c2.getName());
        }),
        CREATE_DATE((c1, c2) -> {
            return c1.getCreateDate().compareTo(c2.getCreateDate());
        }),
        LAST_UPDATE_DATE((c1, c2) -> {
            return c1.getLastUpdateDate().compareTo(c2.getLastUpdateDate());
        }),
        PRICE((c1, c2) -> {
            return c1.getPrice() - c2.getPrice();
        }),
        DURATION((c1, c2) -> {
            return c1.getDuration() - c2.getDuration();
        });

        private final Comparator<GiftCertificate> comparator;

        SortType(Comparator<GiftCertificate> comparator) {
            this.comparator = comparator;
        }

        public Comparator<GiftCertificate> getComparatorAsc() {
            return this.comparator;
        }

        public Comparator<GiftCertificate> getComparatorDesc() {
            return this.comparator.reversed();
        }
    }
}