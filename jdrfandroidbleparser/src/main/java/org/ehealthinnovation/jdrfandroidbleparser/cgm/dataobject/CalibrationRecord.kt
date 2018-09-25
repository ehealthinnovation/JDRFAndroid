package org.ehealthinnovation.jdrfandroidbleparser.cgm.dataobject

import org.ehealthinnovation.jdrfandroidbleparser.encodedvalue.cgm.cgmcp.CalibrationStatus
import org.ehealthinnovation.jdrfandroidbleparser.encodedvalue.cgm.feature.CgmSampleLocation
import org.ehealthinnovation.jdrfandroidbleparser.encodedvalue.cgm.feature.CgmType
import java.util.*

class CalibrationRecord {
    /**
     * The Glucose Concentration of Calibration field is a SFLOAT value
     */
    var glucoseConcentrationOfCalibration: Float? = null
    /**
     * The Calibration Time represents the time the calibration value has been measured as relative
     * offset to the Session Start Time in minutes.
     */
    var calibrationTime: Int? = null

    /**
     * The type and location of the calibration value
     */
    var calibrationType: CgmType? = null
    var calibrationSampleLocation: CgmSampleLocation? = null
    /**
     * Upon a calibration, the Server may inform the Client about the next required calibration
     * time by a relative time value in minutes. The Next Calibration Time field is a 16-bit
     * unsigned integer value. Representing the relative offset to the Session Start Time when the
     * next calibration is required by the Server. A value of 0x0000 means that a calibration is
     * required instantly.
     */
    var nextCalibrationTime: Int? = null
    /**
     * The Calibration Data Record Number field is a 16-bit unsigned integer value, according to
     * the byte transmission order. Each Calibration record is identified
     * by a number. A get operation with an operand of 0xFFFF in this field will return the last
     * Calibration Data stored. A value of “0” in this field represents no calibration value is
     * stored. This field is ignored during a Set Glucose Calibration value procedure.
     */
    var calibrationDataRecordNumber: Int? = null
    /**
     * The Calibration Status field is an 8-bit field, representing the status of the calibration
     * procedure of the Server related to the Calibration Data Record. This field is ignored during
     * the Set Glucose Calibration value procedure. The value of this field represents the status
     * of the calibration process of the Server.
     */
    var calibrationStatusFlags: EnumSet<CalibrationStatus>? = null

    constructor(concentration:Float, calibrationTime: Int, typeSampleLocation: Int, nextCalibrationTime:Int,
                recordNumber:Int, calibrationStatus:Int){
        this.glucoseConcentrationOfCalibration = concentration
        this.calibrationTime = calibrationTime
        this.calibrationType = CgmType.fromKey(typeSampleLocation and 0x0F)
        this.calibrationSampleLocation = CgmSampleLocation.fromKey((typeSampleLocation and 0xF0) shr 4)
        this.nextCalibrationTime = nextCalibrationTime
        this.calibrationDataRecordNumber = recordNumber
        this.calibrationStatusFlags = CalibrationStatus.parseFlags(calibrationStatus)
    }

}