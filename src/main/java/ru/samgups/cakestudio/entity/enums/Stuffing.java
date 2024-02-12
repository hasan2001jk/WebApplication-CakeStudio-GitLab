package ru.samgups.cakestudio.entity.enums;

public enum Stuffing {
    STUFFING_RAFFAELLO_PREMIUM("Раффаэлло premium"),
    STUFFING_OREO_PREMIUM("Орео premium"),
    STUFFING_MOUSSE_RASPBERRY_PREMIUM("Мусс-малина premium"),
    STUFFING_PRAGUE_PREMIUM("Прага premium"),
    STUFFING_THREE_CHOCOLATES_PREMIUM("Три шоколода premium"),
    STUFFING_TRUFFLE_PREMIUM("Трюфель premium"),
    STUFFING_ESTERHAZY_PREMIUM("Эстерхази premium"),
    STUFFING_MILK_GIRL_PREMIUM("Молочная девочка premium");

    private final String value;

    Stuffing(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

}
