/*
 * Copyright (c) Andrey Kuznetsov. All Rights Reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 *  o Redistributions of source code must retain the above copyright notice,
 *    this list of conditions and the following disclaimer.
 *
 *  o Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 *
 *  o Neither the name of imagero Andrey Kuznetsov nor the names of
 *    its contributors may be used to endorse or promote products derived
 *    from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS;
 * OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY,
 * WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE
 * OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE,
 * EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package com.smartg.xmp;

import java.util.Enumeration;

import com.smartg.xmp.ValueType.ArrayType;
import com.smartg.xmp.ValueType.TypeV;
import com.smartg.xmp.ValueTypes.Choice;
import com.smartg.xmp.ValueTypes.ClosedChoice;
import com.smartg.xmp.ValueTypes.Structure;
import com.smartg.xmp.ValueTypes.Text;

/**
 * @author Andrey Kuznetsov
 */
public class EXIFShema extends XMPShema {

    public static final XMPShema SHEMA = new EXIFShema();

    private static final String NAME = "EXIF";

    private EXIFShema() {
	super("http://ns.adobe.com/exif/1.0/", "exif", NAME);
    }

    @Override
    protected void initProperties() {
	ClosedChoice<Text> exifVersionChoice = new ClosedChoice<Text>(TypeV.Text);
	exifVersionChoice.add("0200", "must be \"0210\"");
	Properties.EXIF_VERSION.delegate.set("ExifVersion", INTERNAL, exifVersionChoice, "EXIF tag 36864, 0x9000. Version number.");

	ClosedChoice<Text> flashpixVersionChoice = new ClosedChoice<Text>(TypeV.Text);
	flashpixVersionChoice.add("0100", "must be \"0100\"");
	Properties.FLASHPIX_VERSION.delegate.set("FlashpixVersion", INTERNAL, flashpixVersionChoice, "EXIF tag 40960, 0xA000. Version of FlashPix.");

	ClosedChoice<Text> colorSpaceChoice = new ClosedChoice<Text>(TypeV.Integer);
	colorSpaceChoice.add("1", "sRGB").add("32786", "uncalibrated");
	Properties.COLOR_SPACE.delegate.set("ColorSpace", INTERNAL, colorSpaceChoice,
		"EXIF tag 40961, 0xA001. Color space information: 1 = sRGB, 32786 = uncalibrated");

	String compConfDesc = "EXIF tag 37121, 0x9101. Configuration of components in data: 4 5 6 0 (if RGB compressed data), 1 2 3 0 (other cases). 0 = does not exist 1 = Y 2 = Cb 3 = Cr 4 = R 5 = G 6 = B";
	Properties.COMPONENTS_CONFIGURATION.delegate.set("ComponentsConfiguration", INTERNAL, ArrayType.Seq, TypeV.Integer, compConfDesc);

	Properties.COMPRESSED_BITS_PER_PIXEL.delegate.set("CompressedBitsPerPixel", INTERNAL, TypeV.Rational,
		"EXIF tag 37122, 0x9102. Compression mode used for a compressed image is indicated in unit bits per pixel.");

	Properties.PIXEL_X_DIMENSION.delegate.set("PixelXDimension", INTERNAL, TypeV.Integer, "EXIF tag 40962, 0xA002. Valid image width, in pixels.");
	Properties.PIXEL_Y_DIMENSION.delegate.set("PixelYDimension", INTERNAL, TypeV.Integer, "EXIF tag 40963, 0xA003. Valid image height, in pixels.");
	Properties.MAKER_NOTE.delegate.set("MakerNote", INTERNAL, TypeV.Text,
		"EXIF tag 37500, 0x927C. Undefined information in EXIF, is of UNDEFINED type in EXIF spec.");
	Properties.USER_COMMENT.delegate.set("UserComment", ArrayType.LangAlt, TypeV.Text, "EXIF tag 37510, 0x9286. Comments from user.");
	Properties.RELATED_SOUND_FILE.delegate.set("RelatedSoundFile", INTERNAL, TypeV.Text,
		"EXIF tag 40964, 0xA004. An \"8.3\" file name for the related sound file.");

	String dtoDesc = "EXIF tags 36867, 0x9003 (primary) and 37521, 0x9291 (subseconds). Date and time when original image was generated, in ISO 8601 format. Includes the EXIF SubsecTimeOriginal data.";
	Properties.DATE_TIME_ORIGINAL.delegate.set("DateTimeOriginal", INTERNAL, TypeV.Date, dtoDesc);
	String dtddesc = "EXIF tag 36868, 0x9004 (primary) and 37522, 0x9292 (subseconds). Date and time when image was stored as digital data, can be the same as DateTimeOriginal if originally stored in digital form. Stored in ISO 8601 format. Includes the EXIF SubsecTimeDigitized data.";
	Properties.DATE_TIME_DIGITIZED.delegate.set("DateTimeDigitized", INTERNAL, TypeV.Date, dtddesc);
	Properties.EXPOSURE_TIME.delegate.set("ExposureTime", INTERNAL, TypeV.Rational, "EXIF tag 33434, 0x829A. Exposure time in seconds.");
	Properties.F_NUMBER.delegate.set("FNumber", INTERNAL, TypeV.Rational, "EXIF tag 33437, 0x829D. F number.");

	ClosedChoice<ValueTypes.Integer> expProgChoice = new ClosedChoice<ValueTypes.Integer>(TypeV.Integer);
	expProgChoice.add("0", "not defined").add("1", "Manual").add("2", "Normal program").add("3", "Aperture priority");
	expProgChoice.add("4", "Shutter priority").add("5", "Creative program").add("6", "Action program");
	expProgChoice.add("7", "Portrait mode").add("8", "Landscape mode");
	Properties.EXPOSURE_PROGRAM.delegate.set("ExposureProgram", INTERNAL, expProgChoice, "EXIF tag 34850, 0x8822. Class of program used for exposure");
	Properties.SPECTRAL_SENSITIVITY.delegate.set("SpectralSensitivity", INTERNAL, TypeV.Text,
		"EXIF tag 34852, 0x8824. Spectral sensitivity of each channel.");
	Properties.ISO_SPEED_RATINGS.delegate.set("ISOSpeedRatings", INTERNAL, ArrayType.Seq, TypeV.Integer,
		"EXIF tag 34855, 0x8827. ISO Speed and ISO Latitude of the input device as specified in ISO 12232.");
	Properties.OECF.delegate.set("OECF", INTERNAL, TypeV.OECF_SFR, "EXIF tag 34856, 0x8828. Opto-Electoric Conversion Function as specified in ISO 14524.");
	Properties.SHUTTER_SPEED_VALUE.delegate.set("ShutterSpeedValue", INTERNAL, TypeV.Rational,
		"EXIF tag 37377, 0x9201. Shutter speed, unit is APEX. See Annex C of the EXIF specification.");
	Properties.APERTURE_VALUE.delegate.set("ApertureValue", INTERNAL, TypeV.Rational, "EXIF tag 37378, 0x9202. Lens aperture, unit is APEX.");
	Properties.BRIGHTNESS_VALUE.delegate.set("BrightnessValue", INTERNAL, TypeV.Rational, "EXIF tag 37379, 0x9203. Brightness, unit is APEX.");
	Properties.EXPOSURE_BIAS_VALUE.delegate.set("ExposureBiasValue", INTERNAL, TypeV.Rational, "EXIF tag 37380, 0x9204. Exposure bias, unit is APEX.");
	Properties.MAX_APERTURE_VALUE.delegate.set("MaxApertureValue", INTERNAL, TypeV.Rational, "EXIF tag 37381, 0x9205. Smallest F number of lens, in APEX.");
	Properties.SUBJECT_DISTANCE.delegate.set("SubjectDistance", INTERNAL, TypeV.Rational, "EXIF tag 37382, 0x9206. DistanceUnit to subject, in meters.");

	ClosedChoice<ValueTypes.Integer> metModeChoice = new ClosedChoice<ValueTypes.Integer>(TypeV.Integer);
	metModeChoice.add("0", "unknown").add("1", "Average").add("2", "CenterWeightedAverage").add("3", "Spot").add(
		"4", "MultiSpot").add("5", "Pattern").add("6", "Partial").add("255", "other");
	Properties.METERING_MODE.delegate.set("MeteringMode", INTERNAL, metModeChoice, "EXIF tag 37383, 0x9207. Metering mode.");

	Choice<ValueType> lightSourceChoice = new ClosedChoice<ValueTypes.Integer>(TypeV.Integer).add("0", "unknown ").add("1", "Daylight ").add("2", "Fluorescent ").add("3", "Tungsten ")
		.add("17", "Standard light A ").add("18", "Standard light B ").add("19", "Standard light C ").add("20", "D55 ").add("21", "D65 ").add("22",
			"D75 ").add("255", "other");
	Properties.LIGHT_SOURCE.delegate.set("LightSource", INTERNAL, lightSourceChoice, "EXIF tag 37384, 0x9208. EXIF tag , 0x. Light source.");
	Properties.FLASH.delegate.set("Flash", INTERNAL, TypeV.Rational, "EXIF tag 37385, 0x9209. Strobe light (flash) source data.");
	Properties.FOCAL_LENGTH.delegate.set("FocalLength", INTERNAL, TypeV.Rational, "EXIF tag 37386, 0x920A. Focal length of the lens, in millimeters.");
	Properties.SUBJECT_AREA.delegate.set("SubjectArea", INTERNAL, ArrayType.Seq, TypeV.Integer,
		"EXIF tag 37396, 0x9214. The location and area of the main subject in the overall scene.");
	Properties.FLASH_ENERGY.delegate.set("FlashEnergy", INTERNAL, TypeV.Rational, "EXIF tag 41483, 0xA20B. Strobe energy during image capture.");
	Properties.SPATIAL_FREQUENCY_RESPONSE.delegate.set("SpatialFrequencyResponse", INTERNAL, TypeV.OECF_SFR,
		"EXIF tag 41484, 0xA20C. Input device spatial frequency table and SFR values as specified in ISO 12233.");
	Properties.FOCAL_PLANE_X_RESOLUTION.delegate.set("FocalPlaneXResolution", INTERNAL, TypeV.Rational,
		"EXIF tag 41486, 0xA20E. Horizontal focal resolution, measured pixels per unit.");
	Properties.FOCAL_PLANE_Y_RESOLUTION.delegate.set("FocalPlaneYResolution", INTERNAL, TypeV.Rational,
		"EXIF tag 41487, 0xA20F. Vertical focal resolution, measured in pixels per unit.");

	Choice<ValueType> fpruChoice = new ClosedChoice<ValueTypes.Integer>(TypeV.Integer).add("2", "inches").add("3", "centimeters");
	Properties.FOCAL_PLANE_RESOLUTION_UNIT.delegate.set("FocalPlaneResolutionUnit", INTERNAL, fpruChoice,
		"EXIF tag 41488, 0xA210. Unit used for FocalPlaneXResolution and FocalPlaneYResolution.");
	String subjLocDesc = "EXIF tag 41492, 0xA214. Location of the main subject of the scene. The first value is the horizontal pixel and the second value is the vertical pixel at which the main subject appears.";
	Properties.SUBJECT_LOCATION.delegate.set("SubjectLocation", INTERNAL, ArrayType.Seq, TypeV.Integer, subjLocDesc);
	Properties.EXPOSURE_INDEX.delegate.set("ExposureIndex", INTERNAL, TypeV.Rational, "EXIF tag 41493, 0xA215. Exposure index of input device.");

	Choice<ValueType> sensingMethodChoice = new ClosedChoice<ValueTypes.Integer>(TypeV.Integer).add("1", "Not defined ").add("2", "One-chip color area sensor ").add("3",
		"Two-chip color area sensor ").add("4", "Three-chip color area sensor ").add("5", "Color sequential area sensor ")
		.add("7", "Trilinear sensor ").add("8", "Color sequential linear sensor");
	Properties.SENSING_METHOD.delegate.set("SensingMethod", INTERNAL, sensingMethodChoice, "EXIF tag 41495, 0xA217. Image sensor type on input device.");

	Choice<ValueType> fsChoice = new ClosedChoice<ValueTypes.Integer>(TypeV.Integer).add("3", "DSC");
	Properties.FILE_SOURCE.delegate.set("FileSource", INTERNAL, fsChoice, "EXIF tag 41728, 0xA300. Indicates image source.");

	Choice<ValueType> sTypeChoice = new ClosedChoice<ValueTypes.Integer>(TypeV.Integer).add("1", "directly photographed image");
	Properties.SCENE_TYPE.delegate.set("SceneType", INTERNAL, sTypeChoice, "EXIF tag 41729, 0xA301. Indicates the type of scene.");
	Properties.CFA_PATTERN.delegate.set("CFAPattern", INTERNAL, TypeV.CFAPattern,
		"EXIF tag 41730, 0xA302. Color filter array geometric pattern of the image sense.");

	Choice<ValueType> crendChoice = new ClosedChoice<ValueTypes.Integer>(TypeV.Integer).add("0", "Normal process").add("1", "Custom process");
	Properties.CUSTOM_RENDERED.delegate.set("CustomRendered", INTERNAL, crendChoice,
		"EXIF tag 41985, 0xA401. Indicates the use of special processing on image data.");

	Choice<ValueType> expModeChoice = new ClosedChoice<ValueTypes.Integer>(TypeV.Integer).add("0", "Auto exposure").add("1", "Manual exposure").add("2", "Auto bracket");
	Properties.EXPOSURE_MODE.delegate.set("ExposureMode", INTERNAL, expModeChoice,
		"EXIF tag 41986, 0xA402. Indicates the exposure mode set when the image was shot.");

	Choice<ValueType> whiteBalanceChoice = new ClosedChoice<ValueTypes.Integer>(TypeV.Integer).add("0", "Auto white balance").add("1", "Manual white balance");
	Properties.WHITE_BALANCE.delegate.set("WhiteBalance", INTERNAL, whiteBalanceChoice,
		"EXIF tag 41987, 0xA403. Indicates the white balance mode set when the image was shot.");
	Properties.DIGITAL_ZOOM_RATIO.delegate.set("DigitalZoomRatio", INTERNAL, TypeV.Rational,
		"EXIF tag 41988, 0xA404. Indicates the digital zoom ratio when the image was shot.");
	String focLenDesc = "EXIF tag 41989, 0xA405. Indicates the equivalent focal length assuming a 35mm film camera, in mm. A value of 0 means the focal length is unknown. Note that this tag differs from the FocalLength tag.";
	Properties.FOCAL_LENGTH_IN_35_MM_FILM.delegate.set("FocalLengthIn35mmFilm", INTERNAL, TypeV.Integer, focLenDesc);

	Choice<ValueType> sctChoice = new ClosedChoice<ValueTypes.Integer>(TypeV.Integer).add("0", "Standard").add("1", "Landscape").add("2", "Portrait").add("3", "Night scene");
	Properties.SCENE_CAPTURE_TYPE.delegate.set("SceneCaptureType", INTERNAL, sctChoice,
		"EXIF tag 41990, 0xA406. Indicates the type of scene that was shot.");

	Choice<ValueType> gcChoice = new ClosedChoice<ValueTypes.Integer>(TypeV.Integer).add("0", "None").add("1", "Low gain up").add("2", "High gain up").add("3", "Low gain down").add("4",
		"High gain down");
	Properties.GAIN_CONTROL.delegate.set("GainControl", INTERNAL, gcChoice,
		"EXIF tag 41991, 0xA407. Indicates the degree of overall image gain adjustment.");

	Choice<ValueType> contrastChoice = new ClosedChoice<ValueTypes.Integer>(TypeV.Integer).add("0", "Normal").add("1", "Soft").add("2", "Hard");
	Properties.CONTRAST.delegate.set("Contrast", INTERNAL, contrastChoice,
		"EXIF tag 41992, 0xA408. Indicates the direction of contrast processing applied by the camera.");

	Choice<ValueType> satChoice = new ClosedChoice<ValueTypes.Integer>(TypeV.Integer).add("0", "Normal").add("1", "Low saturation").add("2", "High saturation");
	Properties.SATURATION.delegate.set("Saturation", INTERNAL, satChoice,
		"EXIF tag 41993, 0xA409. Indicates the direction of saturation processing applied by the camera.");

	Choice<ValueType> sharpChoice = new ClosedChoice<ValueTypes.Integer>(TypeV.Integer).add("0", "Normal").add("1", "Soft").add("2", "Hard");
	Properties.SHARPNESS.delegate.set("Sharpness", INTERNAL, sharpChoice,
		"EXIF tag 41994, 0xA40A. Indicates the direction of sharpness processing applied by the camera.");
	Properties.DEVICE_SETTING_DESCRIPTION.delegate.set("DeviceSettingDescription", INTERNAL, TypeV.DeviceSettings,
		"EXIF tag 41995, 0xA40B. indicates information on the picture-taking conditions of a particular camera model.");

	Choice<ValueType> subjDistRangeChoice = new ClosedChoice<ValueTypes.Integer>(TypeV.Integer).add("0", "Unknown").add("1", "Macro").add("2", "Close view").add("3", "Distant view");
	Properties.SUBJECT_DISTANCE_RANGE.delegate.set("SubjectDistanceRange", INTERNAL, subjDistRangeChoice,
		"EXIF tag 41996, 0xA40C. Indicates the distance to the subject.");
	String imgUniqIdDesc = "EXIF tag 42016, 0xA420. An identifier assigned uniquely to each image. It is recorded as a 32 character ASCII string, equivalent to hexadecimal notation and 128-bit fixed length.";
	Properties.IMAGE_UNIQUE_ID.delegate.set("ImageUniqueID", INTERNAL, TypeV.Text, imgUniqIdDesc);
	Properties.GPS_VERSION_ID.delegate.set("GPSVersionID", INTERNAL, TypeV.Text,
		"GPS tag 0, 0x00. A decimal encoding of each of the four EXIF bytes with period separators. The current value is \"2.0.0.0 \".");
	Properties.GPS_LATITUDE.delegate.set("GPSLatitude", INTERNAL, TypeV.GPSCoordinate,
		"GPS tag 2, 0x02 (position) and 1, 0x01 (North/South). Indicates latitude.");
	Properties.GPS_LONGITUDE.delegate.set("GPSLongitude", INTERNAL, TypeV.GPSCoordinate,
		"GPS tag 4, 0x04 (position) and 3, 0x03 (East/West). Indicates longitude.");
	Choice<ValueType> altRefChoice = new ClosedChoice<ValueTypes.Integer>(TypeV.Integer).add("0", "Above sea level").add("1", "Below sea level");
	Properties.GPS_ALTITUDE_REF.delegate.set("GPSAltitudeRef", INTERNAL, altRefChoice,
		"GPS tag 5, 0x5. Indicates whether the altitude is above or below sea level.");
	Properties.GPS_ALTITUDE.delegate.set("GPSAltitude", INTERNAL, TypeV.Rational, "GPS tag 6, 0x06. Indicates altitude in meters.");
	String gpsTimeStampDesc = "GPS tag 29 (date), 0x1D, and, and GPS tag 7 (time), 0x07. Time stamp of GPS data, in Coordinated Universal Time. NOTE:The GPSDateStamp tag is new in EXIF 2.2. The GPS timestamp in EXIF 2.1 does not include a date. The formatted XMP value in this case should still follow ISO 8601, namely \"HH:MM:SS.sss[tz]\", where SS.sss includes as many fractional second digits as necessary and [tz] is a time zone designator. If the denominator of the original fractional sections is not a power of 10, the recommendation is to use 6 fractional digits for microsecond resolution.";
	Properties.GPS_TIME_STAMP.delegate.set("GPSTimeStamp", INTERNAL, TypeV.Date, gpsTimeStampDesc);
	Properties.GPS_SATELLITES.delegate.set("GPSSatellites", INTERNAL, TypeV.Text, "GPS tag 8, 0x08. Satellite information, format is unspecified.");

	Choice<ValueType> statusChoice = new ClosedChoice<ValueTypes.Integer>(TypeV.Integer).add("A", "measurement in progress").add("V", "measurement is interoperability");
	Properties.GPS_STATUS.delegate.set("GPSStatus", INTERNAL, statusChoice, "GPS tag 9, 0x09. Status of GPS receiver at image creation time.");

	Choice<ValueType> measMoceChoice = new ClosedChoice<ValueTypes.Integer>(TypeV.Integer).add("2", "two-dimensional measurement").add("3", "three-dimensional measurement");
	Properties.GPS_MEASURE_MODE.delegate.set("GPSMeasure_mode", INTERNAL, measMoceChoice, "GPS tag 10, 0x0A. GPS measurement mode, Text type.");
	Properties.GPS_DOP.delegate.set("GPSDOP", INTERNAL, TypeV.Rational, "GPS tag 11, 0x0B. Degree of precision for GPS data.");

	Choice<ValueType> speedRefChoice = new ClosedChoice<ValueTypes.Text>(TypeV.Text).add("K", "kilometers per hour").add("M", "miles per hour").add("N", "knots");
	Properties.GPS_SPEED_REF.delegate.set("GPSSpeedRef", INTERNAL, speedRefChoice, "GPS tag 12, 0x0C. Units used to speed measurement.");
	Properties.GPS_SPEED.delegate.set("GPSSpeed", INTERNAL, TypeV.Rational, "GPS tag 13, 0x0D. Speed of GPS receiver movement.");

	Choice<ValueType> trackRefChoice = new ClosedChoice<ValueTypes.Text>(TypeV.Text).add("T", "true direction").add("M", "magnetic direction");
	Properties.GPS_TRACK_REF.delegate.set("GPSTrackRef", INTERNAL, trackRefChoice, "GPS tag 14, 0x0E. Reference for movement direction.");
	Properties.GPS_TRACK.delegate.set("GPSTrack", INTERNAL, TypeV.Rational, "GPS tag 15, 0x0F. Direction of GPS movement, values range from 0 to 359.99.");

	Choice<ValueType> imgDirRefChoice = new ClosedChoice<ValueTypes.Text>(TypeV.Text).add("T", "true direction").add("M", "magnetic direction");
	Properties.GPS_IMG_DIRECTION_REF.delegate.set("GPSImgDirectionRef", INTERNAL, imgDirRefChoice, "GPS tag 16, 0x10. Reference for movement direction.");
	Properties.GPS_IMG_DIRECTION.delegate.set("GPSImgDirection", INTERNAL, TypeV.Rational,
		"GPS tag 17, 0x11. Direction of image when captured, values range from 0 to 359.99.");
	Properties.GPS_MAP_DATUM.delegate.set("GPSMapDatum", INTERNAL, TypeV.Text, "GPS tag 18, 0x12. Geodetic survey data.");
	Properties.GPS_DEST_LATITUDE.delegate.set("GPSDestlLatitude", INTERNAL, TypeV.GPSCoordinate,
		"GPS tag 20, 0x14 (position) and 19, 0x13 (North/South). Indicates destination latitude.");
	Properties.GPS_DEST_LONGITUDE.delegate.set("GPSDestLongitude", INTERNAL, TypeV.GPSCoordinate,
		"GPS tag 22, 0x16 (position) and 21, 0x15 (East/West). Indicates destination longitude.");

	Choice<ValueType> bearingRefChoice = new ClosedChoice<ValueTypes.Text>(TypeV.Text).add("T", "true direction").add("M", "magnetic direction");
	Properties.GPS_DEST_BEARING_REF.delegate.set("GPSDestBearingRef", INTERNAL, bearingRefChoice, "GPS tag 23, 0x17. Reference for movement direction.");
	Properties.GPS_DEST_BEARING.delegate.set("GPSDestBearing", INTERNAL, TypeV.Rational, "GPS tag 24, 0x18. Destination bearing, values from 0 to 359.99.");

	Choice<ValueType> destDistRefChoice = new ClosedChoice<ValueTypes.Text>(TypeV.Text).add("K", "kilometers per hour").add("M", "miles per hour").add("N", "knots");
	Properties.GPS_DEST_DISTANCE_REF.delegate.set("GPSDestDistanceRef", INTERNAL, destDistRefChoice, "GPS tag 25, 0x19. Units used for speed measurement.");
	Properties.GPS_DEST_DISTANCE.delegate.set("GPSDestDistance", INTERNAL, TypeV.Rational, "GPS tag 26, 0x1A. DistanceUnit to destination.");
	Properties.GPS_PROCESSING_METHOD.delegate.set("GPSProcessingMethod", INTERNAL, TypeV.Text,
		"GPS tag 27, 0x1B. A character string recording the name of the method used for location finding.");
	Properties.GPS_AREA_INFORMATION.delegate.set("GPSAreaInformation", INTERNAL, TypeV.Text,
		"GPS tag 28, 0x1C. A character string recording the name of the GPS area.");

	Choice<ValueType> diffChoice = new ClosedChoice<ValueTypes.Integer>(TypeV.Integer).add("0", "Without correction").add("1", "Correction applied");
	Properties.GPS_DIFFERENTIAL.delegate.set("GPSDifferential", INTERNAL, diffChoice,
		"GPS tag 30, 0x1E. Indicates whether differential correction is applied to the GPS receiver.");
    }

    public XmpProperty[] properties() {
	return Properties.values();
    }

    public enum Properties implements XmpProperty {
	EXIF_VERSION, FLASHPIX_VERSION, COLOR_SPACE, COMPONENTS_CONFIGURATION,

	COMPRESSED_BITS_PER_PIXEL,

	PIXEL_X_DIMENSION, PIXEL_Y_DIMENSION,

	MAKER_NOTE, USER_COMMENT, RELATED_SOUND_FILE,

	DATE_TIME_ORIGINAL, DATE_TIME_DIGITIZED,

	EXPOSURE_TIME, F_NUMBER, EXPOSURE_PROGRAM, SPECTRAL_SENSITIVITY, ISO_SPEED_RATINGS,

	OECF, SHUTTER_SPEED_VALUE, APERTURE_VALUE, BRIGHTNESS_VALUE, EXPOSURE_BIAS_VALUE,

	MAX_APERTURE_VALUE, SUBJECT_DISTANCE, METERING_MODE, LIGHT_SOURCE, FLASH, FOCAL_LENGTH,

	SUBJECT_AREA, FLASH_ENERGY, SPATIAL_FREQUENCY_RESPONSE,

	FOCAL_PLANE_X_RESOLUTION, FOCAL_PLANE_Y_RESOLUTION, FOCAL_PLANE_RESOLUTION_UNIT,

	SUBJECT_LOCATION, EXPOSURE_INDEX,

	SENSING_METHOD, FILE_SOURCE, SCENE_TYPE, CFA_PATTERN, CUSTOM_RENDERED,

	EXPOSURE_MODE, WHITE_BALANCE, DIGITAL_ZOOM_RATIO, FOCAL_LENGTH_IN_35_MM_FILM,

	SCENE_CAPTURE_TYPE, GAIN_CONTROL, CONTRAST, SATURATION, SHARPNESS,

	DEVICE_SETTING_DESCRIPTION, SUBJECT_DISTANCE_RANGE, IMAGE_UNIQUE_ID, GPS_VERSION_ID,

	GPS_LATITUDE, GPS_LONGITUDE,

	GPS_ALTITUDE_REF, GPS_ALTITUDE, GPS_TIME_STAMP, GPS_SATELLITES, GPS_STATUS, GPS_MEASURE_MODE,

	GPS_DOP, GPS_SPEED_REF, GPS_SPEED, GPS_TRACK_REF, GPS_TRACK, GPS_IMG_DIRECTION_REF,

	GPS_IMG_DIRECTION, GPS_MAP_DATUM, GPS_DEST_LATITUDE, GPS_DEST_LONGITUDE,

	GPS_DEST_BEARING_REF, GPS_DEST_BEARING, GPS_DEST_DISTANCE_REF, GPS_DEST_DISTANCE, GPS_PROCESSING_METHOD,

	GPS_AREA_INFORMATION, GPS_DIFFERENTIAL

	;

	XmpPropertyImpl delegate = new XmpPropertyImpl();

	public XmpProperty addQualifier(XmpProperty q) {
	    return delegate.addQualifier(q);
	}

	public Category getCategory() {
	    return delegate.getCategory();
	}

	public String getDescription() {
	    return delegate.getDescription();
	}

	public Structure getMemberOf() {
	    return delegate.getMemberOf();
	}

	public String getNamespace() {
	    return delegate.getNamespace();
	}

	public String getPrefix() {
	    return delegate.getPrefix();
	}

	public String getProperty() {
	    return delegate.getProperty();
	}

	public XMPShema getShema() {
	    return delegate.getShema();
	}

	public ValueType getValueType() {
	    return delegate.getValueType();
	}

	public boolean hasQualifiers() {
	    return delegate.hasQualifiers();
	}

	public boolean isArray() {
	    return delegate.isArray();
	}

	public boolean isStructure() {
	    return delegate.isStructure();
	}

	public Enumeration<XmpProperty> qualifiers() {
	    return delegate.qualifiers();
	}

	public XmpProperty setMemberOf(Structure memberOf) {
	    return delegate.setMemberOf(memberOf);
	}

	public XmpProperty setValueType(ValueType type) {
	    return delegate.setValueType(type);
	}

	public XmpProperty setValueType(TypeV type) {
	    return delegate.setValueType(type);
	}

	public XmpProperty setValueType(ArrayType arrayType, TypeV elementType) {
	    return delegate.setValueType(arrayType, elementType);
	}

	public PathElement getChildAt(int index) {
	    return delegate.getChildAt(index);
	}

	public int getChildrenCount() {
	    return delegate.getChildrenCount();
	}

	public PathElement getParent() {
	    return delegate.getParent();
	}

	public void setShema(XMPShema shema) {
	    delegate.setShema(shema);
	}
    }

}
