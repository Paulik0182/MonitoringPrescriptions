package com.example.monitoringprescriptions.domain

enum class UnitsMeasurement {
    UNITS_MEAS, // ед. изм.
    PIECES, // штуки
    SPOON, // ложка
    MILLILITER, // миллилитр
    GRAM, // грамм
    PACKAGE, // пакет
    TUBE, // тюбик
    DROP // капля
}

//enum class UnitsMeasurement(val stringRes: Int) {
//    PIECES(R.string.pieces_unit_meas), // штуки
//    SPOON(R.string.spoon_unit_meas), // ложка
//    MILLILITER(R.string.milliliter_unit_meas), // миллилитр
//    GRAM(R.string.gram_unit_meas), // грамм
//    PACKAGE(R.string.package_unit_meas), // пакет
//    TUBE(R.string.tube_unit_meas), // тюбик
//    DROP(R.string.drop_unit_meas) // капля
//}