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
import com.smartg.xmp.ValueTypes.ClosedChoice;
import com.smartg.xmp.ValueTypes.OpenChoice;
import com.smartg.xmp.ValueTypes.Structure;

/**
 * CameraRaw shema.
 * 
 * @author Andrey Kuznetsov
 */
public class CameraRawShema extends XMPShema {

    public static final CameraRawShema SHEMA = new CameraRawShema();

    public static final String NAME = "CameraRawShema";

    private CameraRawShema() {
	super("http://ns.adobe.com/camera-rawsettings/1.0/", "crs", NAME);
    }

    @Override
    protected void initProperties() {
	Properties.AUTO_BRIGHTNESS.delegate.set("AutoBrightness", TypeV.Boolean, "When true, 'Brightness' is automatically adjusted.");
	Properties.AUTO_CONTRAST.delegate.set("AutoContrast", TypeV.Boolean, "When true, 'Contrast' is automatically adjusted.");
	Properties.AUTO_EXPOSURE.delegate.set("AutoExposure", TypeV.Boolean, "When true, 'Exposure' is automatically adjusted.");
	Properties.AUTO_SHADOWS.delegate.set("AutoShadows", TypeV.Boolean, "When true, 'Shadows' is automatically adjusted.");
	Properties.BLUE_HUE.delegate.set("BlueHue", TypeV.Integer, "'Blue Hue' setting. Range -100 to 100.");
	Properties.BLUE_SATURATION.delegate.set("BlueSaturation", TypeV.Integer, "'Blue Saturation' setting. Range -100 to 100.");
	Properties.BRIGHTNESS.delegate.set("Brightness", TypeV.Integer, "'Brightness' setting. Range 0 to 150.");
	Properties.CAMERA_PROFILE.delegate.set("CameraProfile", TypeV.Text, "'Camera Profile' setting.");
	Properties.CHROMATIC_ABERRATION_B.delegate.set("ChromaticAberrationB", TypeV.Integer,
		"'Chomatic Aberration, Fix Blue/Yellow Fringe' setting. Range -100 to +100.");
	Properties.CHROMATIC_ABERRATION_R.delegate.set("ChromaticAberrationR", TypeV.Integer,
		"'Chomatic Aberration, Fix Red/Cyan Fringe' setting. Range -100 to +100.");
	Properties.COLOR_NOISE_REDUCTION.delegate.set("ColorNoiseReduction", TypeV.Integer, "'Color Noise Reduction' setting. Range 0 to +100.");
	Properties.CONTRAST.delegate.set("Contrast", TypeV.Integer, "'Contrast' setting. Range -50 to +100.");
	Properties.CROP_TOP.delegate.set("CropTop", TypeV.Real, "When HasCrop is true, top of crop rectangle.");
	Properties.CROP_LEFT.delegate.set("CropLeft", TypeV.Real, "When HasCrop is true, left of crop rectangle.");
	Properties.CROP_BOTTOM.delegate.set("CropBottom", TypeV.Real, "When HasCrop is true, bottom of crop rectangle.");
	Properties.CROP_RIGHT.delegate.set("CropRight", TypeV.Real, "When HasCrop is true, right of crop rectangle.");
	Properties.CROP_ANGLE.delegate.set("CropAngle", TypeV.Real, "When HasCrop is true, angle of crop rectangle.");
	Properties.CROP_WIDTH.delegate.set("CropWidth", TypeV.Real, "Width of resulting cropped image in 'CropUnits' units.");
	Properties.CROP_HEIGHT.delegate.set("CropHeight", TypeV.Real, "Height of resulting cropped image in 'CropUnits' units.");
	Properties.CROP_UNITS.delegate.set("CropUnits", TypeV.Integer, "Units for CropWidth and CropHeight. One of: 0=pixels 1=inches 2=cm.");
	Properties.EXPOSURE.delegate.set("Exposure", TypeV.Real, "'Exposure' setting. Range -4.0 to +4.0.");
	Properties.GREEN_HUE.delegate.set("GreenHue", TypeV.Integer, "'Green Hue' setting. Range -100 to +100.");
	Properties.GREEN_SATURATION.delegate.set("GreenSaturation", TypeV.Integer, "'Green Saturation' setting. Range -100 to +100.");
	Properties.HAS_CROP.delegate.set("HasCrop", TypeV.Boolean, "When true, image has a cropping rectangle.");
	Properties.HAS_SETTINGS.delegate.set("HasSettings", TypeV.Boolean, "When true, non-default camera raw settings.");
	Properties.LUMINANCE_SMOOTHING.delegate.set("LuminanceSmoothing", TypeV.Integer, "'Luminance Smoothing' setting. Range 0 to +100.");
	Properties.RAW_FILE_NAME.delegate.set("RawFileName", INTERNAL, TypeV.Text, "File name of raw file (not a complete path).");

	Properties.RED_HUE.delegate.set("RedHue", TypeV.Integer, "'Red Hue' setting. Range -100 to +100.");
	Properties.RED_SATURATION.delegate.set("RedSaturation", TypeV.Integer, "'Red Saturation' setting. Range -100 to +100.");
	Properties.SATURATION.delegate.set("Saturation", TypeV.Integer, "'Saturation' setting. Range -100 to +100.");
	Properties.SHADOWS.delegate.set("Shadows", TypeV.Integer, "'Shadows' setting. Range 0 to +100.");
	Properties.SHADOW_TINT.delegate.set("ShadowTint", TypeV.Integer, "'ShadowTint' setting. Range -100 to +100.");
	Properties.SHARPNESS.delegate.set("Sharpness", TypeV.Integer, "'Sharpness' setting. Range 0 to +100.");
	Properties.TEMPERATURE.delegate.set("Temperature", TypeV.Integer, "'Temperature' setting. Range 2000 to 50000.");
	Properties.TINT.delegate.set("Tint", TypeV.Integer, "'Tint' setting. Range -150 to +150.");
	Properties.TONE_CURVE.delegate.set("ToneCurve", ArrayType.Seq, TypeV.Integer, "Array of points (Integer, Integer) defining a 'Tone Curve'.");

	OpenChoice<ValueTypes.Text> toneCurveChoice = new OpenChoice<ValueTypes.Text>(TypeV.Text);
	toneCurveChoice.add("Linear", "").add("Medium Contrast", "").add("Strong Contrast", "").add("Custom", "");
	Properties.TONE_CURVE_NAME.delegate.set("ToneCurveName", toneCurveChoice, "'Tint' setting. Range -150 to +150.");

	Properties.VERSION.delegate.set("Version", INTERNAL, TypeV.Text, "Version of Camera Raw plugin.");
	Properties.VIGNETTE_AMOUNT.delegate.set("VignetteAmount", TypeV.Integer, "Vignetting Amount' setting. Range -100 to +100.");
	Properties.VIGNETTE_MIDPOINT.delegate.set("VignetteMidpoint", TypeV.Integer, "Vignetting Midpoint' setting. Range 0 to +100.");

	ClosedChoice<ValueTypes.Text> whiteBalanceChoice = new ClosedChoice<ValueTypes.Text>(TypeV.Text);
	whiteBalanceChoice.add("As Shot", "").add("Auto", "").add("Daylight", "");
	whiteBalanceChoice.add("Cloudy", "").add("Shade", "").add("Tungsten", "");
	whiteBalanceChoice.add("Fluorescent", "").add("Flash", "").add("Custom", "");

	Properties.WHITE_BALANCE.delegate.set("WhiteBalance", whiteBalanceChoice, "'White Balance' setting.");	
    }


    public XmpProperty[] properties() {
	return Properties.values();
    }

    public enum Properties implements XmpProperty {
	AUTO_BRIGHTNESS, AUTO_CONTRAST, AUTO_EXPOSURE, AUTO_SHADOWS, BLUE_HUE, BLUE_SATURATION, BRIGHTNESS, CAMERA_PROFILE, CHROMATIC_ABERRATION_B, CHROMATIC_ABERRATION_R, COLOR_NOISE_REDUCTION, CONTRAST, CROP_TOP, CROP_LEFT, CROP_BOTTOM, CROP_RIGHT, CROP_ANGLE, CROP_WIDTH, CROP_HEIGHT, CROP_UNITS, EXPOSURE, GREEN_HUE, GREEN_SATURATION, HAS_CROP, HAS_SETTINGS, LUMINANCE_SMOOTHING, RAW_FILE_NAME, RED_HUE, RED_SATURATION, SATURATION, SHADOWS, SHADOW_TINT, SHARPNESS, TEMPERATURE, TINT, TONE_CURVE, TONE_CURVE_NAME, VERSION, VIGNETTE_AMOUNT, VIGNETTE_MIDPOINT, WHITE_BALANCE;

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
