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
import com.smartg.xmp.ValueTypes.Structure;

/**
 * @author Andrey Kuznetsov
 */
public class TIFFShema extends XMPShema {
    public static final String NAME = "TIFF";
    public static final TIFFShema SHEMA = new TIFFShema();

    private TIFFShema() {
	super("http://ns.adobe.com/tiff/1.0/", "tiff", NAME);
    }

    @Override
    protected void initProperties() {
	Properties.IMAGE_WIDTH.delegate.set("ImageWidth", INTERNAL, TypeV.Integer, "TIFF tag 256, 0x100. Image width in pixels.");
	Properties.IMAGE_LENGTH.delegate.set("ImageLength", INTERNAL, TypeV.Integer, "TIFF tag 257, 0x101. Image height in pixels.");
	Properties.BITS_PER_SAMPLE.delegate.set("BitsPerSample", INTERNAL, ArrayType.Seq, TypeV.Integer,
		"TIFF tag 258, 0x102. Number of bits per component in each channel.");

	Choice<ValueType> compChoice = new ValueTypes.ClosedChoice<ValueTypes.Integer>(TypeV.Integer).add("1", "uncompressed").add("6", "JPEG");
	Properties.COMPRESSION.delegate.set("Compression", INTERNAL, compChoice, "TIFF tag 259, 0x103. Compression scheme:1 = uncompressed; 6 = JPEG.");

	Choice<ValueType> piChoice = new ValueTypes.ClosedChoice<ValueTypes.Integer>(TypeV.Integer).add("2", "RGB").add("6", "YCbCr");
	Properties.PHOTOMETRIC_INTERPRETATION.delegate.set("PhotometricInterpretation", INTERNAL, piChoice, "TIFF tag 262, 0x106. Pixel Composition.");

	Choice<ValueType> orientChoice = new ValueTypes.ClosedChoice<ValueTypes.Integer>(TypeV.Integer);
	orientChoice.add("1", "0th row at top, 0th column at left").add("2", "0th row at top, 0th column at right");
	orientChoice.add("3", "0th row at bottom, 0th column at right").add("4", "0th row at bottom, 0th column at left");
	orientChoice.add("5", "0th row at left, 0th column at top").add("6", "0th row at right, 0th column at top");
	orientChoice.add("7", "0th row at right, 0th column at bottom").add("8", "0th row at left, 0th column at bottom");
	Properties.ORIENTATION.delegate.set("Orientation", INTERNAL, orientChoice, "TIFF tag 274, 0x112. Orientation.");

	Properties.SAMPLES_PER_PIXEL.delegate.set("SamplesPerPixel", INTERNAL, TypeV.Integer, "TIFF tag 277, 0x115. Number of components per pixel.");

	Choice<ValueType> pcChoice = new ValueTypes.ClosedChoice<ValueTypes.Integer>(TypeV.Integer).add("1", "chunky").add("2", "planar");
	Properties.PLANAR_CONFIGURATION.delegate.set("PlanarConfiguration", INTERNAL, pcChoice, "TIFF tag 284, 0x11C. Data layout:1 = chunky; 2 = planar.");

	Choice<ValueType> yccSubChoice = new ValueTypes.ClosedChoice<ValueTypes.Array>(null, new ValueTypes.Array(null, ArrayType.Seq, TypeV.Integer));
	yccSubChoice.add("[2, 1]", "YCbCr 4:2:2").add("[2, 2]", "YCbCr 4:2:0");
	Properties.YCC_SUBSAMPLING.delegate.set("YCbCrSubSampling", INTERNAL, yccSubChoice, "TIFF tag 530, 0x212. Sampling ratio of chrominance components.");

	Choice<ValueType> yccPosChoice = new ValueTypes.ClosedChoice<ValueTypes.Integer>(TypeV.Integer).add("1", "centered").add("2", "co-sited");
	Properties.YCC_POSITIONING.delegate.set("YCbCrPositioning", INTERNAL, yccPosChoice,
		"TIFF tag 531, 0x213. BufferPosition of chrominance vs. luminance components.");

	Properties.X_RESOLUTION.delegate.set("XResolution", INTERNAL, TypeV.Rational, "TIFF tag 282, 0x11A. Horizontal resolution in pixels per unit.");
	Properties.Y_RESOLUTION.delegate.set("YResolution", INTERNAL, TypeV.Rational, "TIFF tag 283, 0x11B. Vertical resolution in pixels per unit.");

	Choice<ValueType> resUnitChoice = new ValueTypes.ClosedChoice<ValueTypes.Integer>(TypeV.Integer).add("2", "inches").add("3", "centimeters");
	Properties.RESOLUTION_UNIT.delegate.set("ResolutionUnit", INTERNAL, resUnitChoice, "TIFF tag 296, 0x128. Unit used for XResolution and YResolution.");

	Properties.TRANSFER_FUNCTION.delegate.set("TransferFunction", INTERNAL, ArrayType.Seq, TypeV.Integer,
		"TIFF tag 301, 0x12D. Transfer function for image described in tabular style with 3 * 256 entries.");

	Properties.WHITE_POINT.delegate.set("WhitePoint", INTERNAL, ArrayType.Seq, TypeV.Rational, "TIFF tag 318, 0x13E. Chromaticity of white point.");
	Properties.PRIMARY_CHROMATICITIES.delegate.set("PrimaryChromaticities", INTERNAL, ArrayType.Seq, TypeV.Rational,
		"TIFF tag 319, 0x13F. Chromaticity of the three primary colors.");
	Properties.YCC_COEFFICIENTS.delegate.set("YCbCrCoefficients", INTERNAL, ArrayType.Seq, TypeV.Rational,
		"TIFF tag 529, 0x211. Matrix coefficients for RGB to YCbCr transformation.");
	Properties.REFERENCE_BLACK_WHITE.delegate.set("ReferenceBlackWhite", INTERNAL, ArrayType.Seq, TypeV.Rational,
		"TIFF tag 532, 0x214. Reference black and white point values.");
	Properties.DATE_TIME.delegate.set("DateTime", INTERNAL, TypeV.Date, "TIFF tag 306, 0x132 (primary) and EXIF tag 37520, 0x9290 (subseconds). "
		+ "Date and time of image creation (no time zone in EXIF), stored in ISO 8601 format, not the original EXIF format. "
		+ "This property includes the value for the EXIF SubsecTime attribute. NOTE:This property is stored in XMP as xmp:ModifyDate.");

	Properties.IMAGE_DESCRIPTION.delegate.set("ImageDescription", INTERNAL, ArrayType.LangAlt, TypeV.Text,
		"TIFF tag 270, 0x10E. Description of the image. NOTE:This property is stored in XMP as dc:description.");

	Properties.MAKE.delegate.set("Make", INTERNAL, TypeV.ProperName, "TIFF tag 271, 0x10F. Manufacturer of recording equipment.");
	Properties.MODEL.delegate.set("Model", INTERNAL, TypeV.ProperName, "TIFF tag 272, 0x110. Model name or number of equipment.");
	Properties.SOFTWARE.delegate.set("Software", INTERNAL, TypeV.AgentName,
		"TIFF tag 305, 0x131. Software or firmware used to generate image. NOTE:This property is stored in XMP as xmp:CreatorTool.");
	Properties.ARTIST.delegate.set("Artist", INTERNAL, TypeV.ProperName, "TIFF tag 315, 0x13B. Camera owner, photographer or image creator. "
		+ "NOTE: Each entry in EXIF string should become an individual entry in the dc:creator property. "
		+ "When round-tripping, each entry in the dc:creator property should be separated by a semicolon.");

	Properties.COPYRIGHT.delegate.set("Copyright", INTERNAL, ArrayType.LangAlt, TypeV.Text,
		"TIFF tag 33432, 0x8298. Copyright information. NOTE:This property is stored in XMP as dc:rights.");
    }


    public XmpProperty[] properties() {
	return Properties.values();
    }

    public enum Properties implements XmpProperty {
	IMAGE_WIDTH, IMAGE_LENGTH, BITS_PER_SAMPLE, COMPRESSION, PHOTOMETRIC_INTERPRETATION, ORIENTATION, SAMPLES_PER_PIXEL, PLANAR_CONFIGURATION, YCC_SUBSAMPLING, YCC_POSITIONING, X_RESOLUTION, Y_RESOLUTION, RESOLUTION_UNIT, TRANSFER_FUNCTION, WHITE_POINT, PRIMARY_CHROMATICITIES, YCC_COEFFICIENTS, REFERENCE_BLACK_WHITE, DATE_TIME, IMAGE_DESCRIPTION, MAKE, MODEL, SOFTWARE, ARTIST, COPYRIGHT

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
