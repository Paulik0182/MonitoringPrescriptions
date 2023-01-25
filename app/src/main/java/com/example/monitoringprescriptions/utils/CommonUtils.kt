package com.example.monitoringprescriptions.utils

import android.content.Context
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.monitoringprescriptions.App
import com.example.monitoringprescriptions.R
import com.example.monitoringprescriptions.domain.ErrorMessage
import com.example.monitoringprescriptions.domain.TypeMedicine
import com.example.monitoringprescriptions.domain.UnitsMeasurement
import com.example.monitoringprescriptions.domain.entities.AppointmentEntity
import java.util.*


fun Calendar.toUserString(): String {
    return "${
        get(Calendar.DAY_OF_MONTH)
    } ${
        get(Calendar.MONDAY)
    } ${
        get(Calendar.YEAR)
    }"
}

// todo экстеншен toFloat (есть встроенный) лучше использовать если кидается исключение (Просто пример)
fun String.toFloatSafeOrNull(): Float? {
    return try {
        this.toFloat()
    } catch (ex: Exception) {
        null
    }
}

// todo экстеншен forEach для каждого Int (запускается от единицы до указанного значения)
inline fun Int.forEach(action: (Int) -> Unit) {
    for (i in 0 until this) action(i)
}

// экстеншен, get() постоянный
val Context.app get() = applicationContext as App


val Fragment.app get() = requireContext().app

/**
 * экстеншен (расширение обычной чужой функции). Можно указать mutable расширение и оно вернет
 * версию MutableLiveData это сделано чтобы во фрагменте случайно не изменить список (в этом
 * рельной безописности нет)
 */
fun <T> LiveData<T>.mutable(): MutableLiveData<T> {
    return this as MutableLiveData
}

// второй вариант
//val <T> LiveData<T>.mutable: MutableLiveData<T> get() = this as MutableLiveData<T>


/**
 * Два метода позволяющий сравнивать совпадающий день
 */
fun Calendar.dayIsEqual(otherCalendar: Calendar): Boolean {
    return get(Calendar.YEAR) == otherCalendar.get(Calendar.YEAR)
            && get(Calendar.DAY_OF_YEAR) == otherCalendar.get(Calendar.DAY_OF_YEAR)
}

fun Long.dayIsEqual(otherTimeMs: Long): Boolean {
    val firstCalendar = Calendar.getInstance()
    firstCalendar.timeInMillis = otherTimeMs
    val secondCalendar = Calendar.getInstance()
    secondCalendar.timeInMillis = this

    return firstCalendar.dayIsEqual(secondCalendar)
}

fun Context.toastMake(text: Int) {
    Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
}

fun Context.toastMake(text: String) {
    Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
}

fun View.toastMake(text: String) {
    Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
}

fun AppointmentEntity.getIdAsInt(): Int {
    return UUID.fromString(id).leastSignificantBits.toInt()
}

fun String.toUnitMeasurement(context: Context): UnitsMeasurement {
    return when (this) {
        context.getString(R.string.units_meas) -> UnitsMeasurement.UNITS_MEAS
        context.getString(R.string.pieces_unit_meas) -> UnitsMeasurement.PIECES
        context.getString(R.string.spoon_unit_meas) -> UnitsMeasurement.SPOON
        context.getString(R.string.milliliter_unit_meas) -> UnitsMeasurement.MILLILITER
        context.getString(R.string.gram_unit_meas) -> UnitsMeasurement.GRAM
        context.getString(R.string.package_unit_meas) -> UnitsMeasurement.PACKAGE
        context.getString(R.string.tube_unit_meas) -> UnitsMeasurement.TUBE
        context.getString(R.string.drop_unit_meas) -> UnitsMeasurement.DROP
        else -> throw IllegalStateException("Не получается распарсить единицу измерения из строки")
    }
}

fun UnitsMeasurement.toString(context: Context): String {
    return when (this) {
        UnitsMeasurement.UNITS_MEAS -> context.getString(R.string.units_meas)
        UnitsMeasurement.PIECES -> context.getString(R.string.pieces_unit_meas)
        UnitsMeasurement.SPOON -> context.getString(R.string.spoon_unit_meas)
        UnitsMeasurement.MILLILITER -> context.getString(R.string.milliliter_unit_meas)
        UnitsMeasurement.GRAM -> context.getString(R.string.gram_unit_meas)
        UnitsMeasurement.PACKAGE -> context.getString(R.string.package_unit_meas)
        UnitsMeasurement.TUBE -> context.getString(R.string.tube_unit_meas)
        UnitsMeasurement.DROP -> context.getString(R.string.drop_unit_meas)
    }
}

fun TypeMedicine.toString(context: Context): String {
    return when (this) {
        TypeMedicine.TYPE_MED -> context.getString(R.string.type_med)
        TypeMedicine.PILL -> context.getString(R.string.pill_type_med)
        TypeMedicine.SYRINGE -> context.getString(R.string.syringe_type_med)
        TypeMedicine.POWDER -> context.getString(R.string.powder_type_med)
        TypeMedicine.SUSPENSION -> context.getString(R.string.suspension_type_med)
        TypeMedicine.OINTMENT -> context.getString(R.string.ointment_type_med)
        TypeMedicine.TINCTURE -> context.getString(R.string.tincture_type_med)
        TypeMedicine.DROPS -> context.getString(R.string.drop_unit_meas)
        TypeMedicine.CANDLES -> context.getString(R.string.candles_type_med)
    }
}

fun String.toTypeMedicine(context: Context): TypeMedicine {
    return when (this) {
        context.getString(R.string.type_med) -> TypeMedicine.TYPE_MED
        context.getString(R.string.pill_type_med) -> TypeMedicine.PILL
        context.getString(R.string.syringe_type_med) -> TypeMedicine.SYRINGE
        context.getString(R.string.powder_type_med) -> TypeMedicine.POWDER
        context.getString(R.string.suspension_type_med) -> TypeMedicine.SUSPENSION
        context.getString(R.string.ointment_type_med) -> TypeMedicine.OINTMENT
        context.getString(R.string.tincture_type_med) -> TypeMedicine.TINCTURE
        context.getString(R.string.drop_unit_meas) -> TypeMedicine.DROPS
        context.getString(R.string.candles_type_med) -> TypeMedicine.CANDLES
        else -> throw IllegalStateException("Не получается распарсить тип лекарства из строки")
    }
}

fun ErrorMessage.toString(context: Context): String {
    return when (this) {
        ErrorMessage.RECEPTION_DAYS -> context.getString(R.string.reception_days_error)
        ErrorMessage.NAME_MEDICINE -> context.getString(R.string.name_medicine_error)
        ErrorMessage.TYPE_MEDICINE -> context.getString(R.string.type_medicine_error)
        ErrorMessage.DOSAGE -> context.getString(R.string.dosage_error)
        ErrorMessage.UNIT_MEASUREMENT -> context.getString(R.string.unit_measurement_error)
        ErrorMessage.DATE_ADMISSION -> context.getString(R.string.date_admission_error)
        ErrorMessage.NUMBER_RECEPTION -> context.getString(R.string.number_receptions_error)
        ErrorMessage.UNIT_ERROR -> context.getString(R.string.unit_error)
    }
}