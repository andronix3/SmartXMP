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

import java.util.ArrayList;
import java.util.Enumeration;

import com.smartg.java.util.EmptyEnumeration;
import com.smartg.java.util.SafeIterator;

/**
 * @author Andrey Kuznetsov
 */
public class ValueType implements PathElement {

    PathElement parent;

    public static enum ArrayType {
	Seq, Bag, Alt, LangAlt
    }

    public static enum TypeV {
	AgentName, Array, Boolean, OpenChoice, ClosedChoice, Date, GPSCoordinate, Integer, MIMEType, ProperName, Rational, Real, SimpleString, Text, Uri, Url, XPath, ResourceRef, Colorant, Marker, Font, ResourceEvent, Dimensions, ContactInfo, Time, Thumbnail, TimeScaleStretch, TimeCode, ProjectLink, Media, BeatSpliceStretch, ResampleStretch, DeviceSettings, CFAPattern, OECF_SFR, Flash, Job, Version
    }

    public static Enumeration<ValueType> types() {
	return new VT_Enumeration();
    }

    static class VT_Enumeration implements Enumeration<ValueType> {

	ValueTypeFactory factory = ValueTypeFactory.getInstance();

	TypeV[] values = TypeV.values();
	int index;

	public boolean hasMoreElements() {
	    return index < values.length;
	}

	public ValueType nextElement() {
	    TypeV t = values[index++];
	    switch (t) {
	    case Array:
		return factory.create(null, ArrayType.Bag);
	    default:
		return factory.create(null, t);
	    }
	}
    }

    static void add(XMP_StringBuffer sb, XMP_HeaderConfiguration hc) {
	Enumeration<ValueType> types = types();
	while (types.hasMoreElements()) {
	    ValueType next = types.nextElement();
	    sb.append("<rdf:li rdf:parseType=\"Resource\">\n");
	    sb.append(next.toXMP(hc));
	    sb.append("</rdf:li>\n");
	}
    }

    public static String createDescription() {
	return createDescription(new XMP_HeaderConfiguration());
    }

    public static String createDescription(XMP_HeaderConfiguration hc) {
	StringBuffer sb = new StringBuffer();

	sb.append(hc.createHeader());

	sb.append("\n<rdf:Bag>\n");

	String s0 = hc.namePrefix + hc.valueTypes + ":" + hc.valueTypes;

	Enumeration<ValueType> types = types();
	while (types.hasMoreElements()) {
	    ValueType next = types.nextElement();
	    if (next != null) {
		sb.append("<rdf:li rdf:parseType=\"Resource\">\n");
		sb.append(next.toXMP(hc));
		sb.append("</rdf:li>\n");
	    }
	}

	sb.append("</rdf:Bag>\n </" + s0 + ">\n");

	sb.append(XMPShema.footer);

	return sb.toString();
    }

    public final TypeV type;
    public final String name;

    ArrayList<XmpProperty> qualifiers;

    protected ValueType(TypeV type) {
	this(null, "", type);
    }

    protected ValueType(PathElement parent, TypeV type) {
	this(parent, "", type);
    }

    protected ValueType(PathElement parent, String name, TypeV type) {
	this.name = name;
	this.type = type;
	this.parent = parent;
	// map.put(name, this);
    }

    public ValueType addQualifier(XmpProperty q) {
	if (qualifiers == null) {
	    qualifiers = new ArrayList<XmpProperty>();
	}
	qualifiers.add(q);
	return this;
    }

    public Enumeration<XmpProperty> qualifiers() {
	if (qualifiers != null) {
	    return new SafeIterator<XmpProperty>(qualifiers.iterator());
	}
	return new EmptyEnumeration<XmpProperty>();
    }

    public boolean hasQualifiers() {
	return qualifiers != null && qualifiers.size() > 0;
    }

    @Override
    public String toString() {
	if (name != null && !name.trim().isEmpty()) {
	    return name;
	}
	return type.toString();
    }

    public String toXMP(XMP_HeaderConfiguration hc) {
	XMP_StringBuffer sb = new XMP_StringBuffer(hc);
	// sb.append("<rdf:li rdf:parseType=\"Resource\">\n");
	if (name != null && !name.trim().isEmpty()) {
	    sb.addTag(hc.valueType, "name", name);
	}

	String typeString = type.toString();

	if (type == TypeV.Array) {
	    ValueTypes.Array a = (ValueTypes.Array) this;
	    typeString = a.arrayType + " " + a.valueType;
	}

	sb.addTag(hc.valueType, "type", typeString);

	// sb.append("</rdf:li>\n");

	return sb.toString();
    }

    static void init() {
	// if (!initDone) {
	// initDone = true;
	//
	// register("Text", new ValueType(TypeV.Text));
	// register("Colorant", new Colorant());
	// register("ContactInfo", new ContactInfo());
	// register("Boolean", new ValueType(TypeV.Boolean));
	// register("Real", new ValueType(TypeV.Real).addQualifier(new
	// Property("", "vQual", "vQual:binRep", get("Text"), Category.INTERNAL,
	// "The text is interpreted as:\r\n"
	// +
	// "std size,endian,hexadecimal_value. \"std\" is the standard name (\"IEEE754\") \"size\" is S for 32-bit and D for 64-bit "
	// +
	// "\"endian\" is L for little-endian, B for big-endian. For example: \"IEEE754D,L,3A4901F387D31108\"")));
	// register("Integer", new ValueType(TypeV.Integer));
	// register("Rational", new ValueType(TypeV.Rational));
	// register("Date", new ValueType(TypeV.Date));
	// register("Dimensions", new Dimensions());
	// register("MIMEType", new ValueType(TypeV.MIMEType));
	// register("ProperName", new ValueType(TypeV.ProperName));
	// register("Locale", new Choice(ChoiceType.Closed, "Locale",
	// get("Text")));
	// register("Uri", new ValueType(TypeV.Uri));
	// register("Url", new ValueType(TypeV.Url));
	// register("Font", new Font());
	// register("XPath", new ValueType(TypeV.XPath));
	// register("AgentName", new ValueType(TypeV.AgentName));
	// register("RenditionClass", new RenditionClass());
	// register("ResourceEvent", new ResourceEvent());
	// register("ResourceRef", new ResourceRef());
	// register("Version", new Structure("Version", "stVer",
	// "http://ns.adobe.com/xap/1.0/sType/Version#", new ValueType[] {
	// new ValueType("comments", TypeV.Text), new ResourceEvent("event"),
	// new ValueType("modifyDate", TypeV.Date),
	// new ValueType("modifier", TypeV.ProperName), new ValueType("version",
	// TypeV.Text) }));
	//
	// register("Job", new Structure("Job", "stJob",
	// "http://ns.adobe.com/xap/1.0/sType/Job#", new ValueType[] { new
	// ValueType("name", TypeV.Text),
	// new ValueType("id", TypeV.Text), new ValueType("url", TypeV.Url) }));
	//
	// register("GPSCoordinate", new ValueType(TypeV.GPSCoordinate));
	//
	// register("Flash", new Structure("Flash", "exif",
	// "http://ns.adobe.com/exif/1.0/", new ValueType[] {
	// new ValueType("Fired", TypeV.Boolean),
	// new Choice(ChoiceType.Closed, "Return", get("Integer")).add("0",
	// "no strobe return detection").add("2",
	// "strobe return light not detected")
	// .add("3", "strobe return light detected"),
	// new Choice(ChoiceType.Closed, "Mode", get("Integer")).add("0",
	// "unknown").add("1", "compulsory flash firing").add("2",
	// "compulsory flash suppression").add("3", "auto mode"), new
	// ValueType("Function", TypeV.Boolean),
	// new ValueType("RedEyeMode", TypeV.Boolean) }));
	//
	// register("OECF/SFR",
	// new Structure("OECF/SFR", "exif", "http://ns.adobe.com/exif/1.0/",
	// new ValueType[] { new ValueType("Columns", TypeV.Integer),
	// new ValueType("Rows", TypeV.Integer), new Array("Names", get("Text"),
	// ArrayType.Seq),
	// new Array("Values", get("Rational"), ArrayType.Seq) }));
	//
	// register("Thumbnail", new Thumbnail("Thumbnail"));
	//
	// register("CFAPattern", new Structure("CFAPattern", "exif",
	// "http://ns.adobe.com/exif/1.0/", new ValueType[] {
	// new ValueType("Columns", TypeV.Integer), new ValueType("Rows",
	// TypeV.Integer), new Array("Values", get("Integer"), ArrayType.Seq)
	// }));
	//
	// register("DeviceSettings", new Structure("DeviceSettings", "exif",
	// "http://ns.adobe.com/exif/1.0/", new ValueType[] {
	// new ValueType("Columns", TypeV.Integer), new ValueType("Rows",
	// TypeV.Integer), new Array("Settings", get("Text"), ArrayType.Seq)
	// }));
	//
	// register("resampleStretch", new Structure("resampleStretch", "xmpDM",
	// "http://ns.adobe.com/xmp/1.0/DynamicMedia/", new ValueType[] { new
	// Choice(
	// ChoiceType.Closed, "quality",
	// get("Text")).add("High").add("Medium").add("Low") }));
	//
	// register("Lang Alt", new Array(get("Text"),
	// ArrayType.LangAlt).addQualifier(new Property("xml:lang",
	// ValueType.get("Text"), Category.INTERNAL,
	// "A string that conforms to RFC 3066 notation")));
	//
	// register("beatSpliceStretch", new Structure("beatSpliceStretch",
	// "xmpDM", "http://ns.adobe.com/xmp/1.0/DynamicMedia/", new ValueType[]
	// {
	// new ValueType("useFileBeatsMarker", TypeV.Boolean), new
	// ValueType("riseInDecibel", TypeV.Real), new
	// Time("riseInTimeDuration") }));
	//
	// register("Marker", new Marker());
	//
	// register("Media", new Structure("Media", "xmpDM",
	// "http://ns.adobe.com/xmp/1.0/DynamicMedia/", new ValueType[] { new
	// ValueType("path", TypeV.Uri),
	// new ValueType("track", TypeV.Text), new Time("startTime"), new
	// Time("duration"), new ValueType("managed", TypeV.Boolean),
	// new ValueType("webStatement", TypeV.Uri) }));
	//
	// register("ProjectLink",
	// new Structure("ProjectLink", "xmpDM",
	// "http://ns.adobe.com/xmp/1.0/DynamicMedia/", new ValueType[] {
	// new Choice(ChoiceType.Closed, "type",
	// get("Text")).add("movie").add("still").add("audio").add("custom"),
	// new ValueType("path", TypeV.Uri) }));
	//
	// register("Time", new Time());
	//
	// register("TimeCode", new Structure("TimeCode", "xmpDM",
	// "http://ns.adobe.com/xmp/1.0/DynamicMedia/", new ValueType[] {
	// new ValueType("timeValue", TypeV.Text),
	// new Choice(ChoiceType.Closed, "timeFormat",
	// get("Text")).add("24Timecode").add("25Timecode").add("2997DropTimecode").add(
	// "2997NonDropTimecode").add("30Timecode").add("50Timecode").add("5994DropTimecode").add("5994NonDropTimecode").add("60Timecode")
	// .add("23976Timecode") }));
	//
	// register("timeScaleStretch", new Structure("timeScaleStretch",
	// "xmpDM", "http://ns.adobe.com/xmp/1.0/DynamicMedia", new ValueType[]
	// {
	// new Choice(ChoiceType.Closed,
	// get("Text")).add("High").add("Medium").add("Low"), new
	// ValueType("frameSize", TypeV.Real),
	// new ValueType("frameOverlappingPercentage", TypeV.Real) }));
	//
	// }
    }

    public PathElement getChildAt(int index) {
	return null;
    }

    public int getChildrenCount() {
	return 0;
    }

    public PathElement getParent() {
	return parent;
    }
}
