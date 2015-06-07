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

public class ValueTypes {

    public static class Text extends ValueType {

	public Text(PathElement parent) {
	    this(parent, "Text");
	}

	public Text(PathElement parent, String name) {
	    super(parent, name, TypeV.Text);
	}
    }

    public static class SimpleString extends ValueType {

	public SimpleString(PathElement parent) {
	    this(parent, "SimpleString");
	}

	public SimpleString(PathElement parent, String name) {
	    super(parent, name, TypeV.SimpleString);
	}
    }

    public static class Boolean extends ValueType {

	public Boolean(PathElement parent) {
	    this(parent, "Boolean");
	}

	public Boolean(PathElement parent, String name) {
	    super(parent, name, TypeV.Boolean);
	}
    }

    public static class Integer extends ValueType {

	public Integer(PathElement parent) {
	    this(parent, "Integer");
	}

	public Integer(PathElement parent, String name) {
	    super(parent, name, TypeV.Integer);
	}
    }

    public static class Date extends ValueType {

	public Date(PathElement parent) {
	    this(parent, "Date");
	}

	public Date(PathElement parent, String name) {
	    super(parent, name, TypeV.Date);
	}
    }

    public static class MIMEType extends ValueType {

	public MIMEType(PathElement parent) {
	    this(parent, "MIMEType");
	}

	public MIMEType(PathElement parent, String name) {
	    super(parent, name, TypeV.MIMEType);
	}

    }

    public static class ProperName extends ValueType {

	public ProperName(PathElement parent) {
	    this(parent, "ProperName");
	}

	public ProperName(PathElement parent, String name) {
	    super(parent, name, TypeV.ProperName);
	}
    }

    public static class Locale extends ClosedChoice<Text> {

	public Locale(PathElement parent) {
	    this(parent, "Locale");
	}

	public Locale(PathElement parent, String choiceName) {
	    super(parent, choiceName);
	}
    }

    public static class Rational extends ValueType {

	public Rational(PathElement parent) {
	    this(parent, "Rational");
	}

	public Rational(PathElement parent, String name) {
	    super(parent, name, TypeV.Rational);
	}
    }

    public static class Uri extends ValueType {

	public Uri(PathElement parent) {
	    this(parent, "Uri");
	}

	public Uri(PathElement parent, String name) {
	    super(parent, name, TypeV.Uri);
	}
    }

    public static class Url extends ValueType {

	public Url(PathElement parent) {
	    this(parent, "Url");
	}

	public Url(PathElement parent, String name) {
	    super(parent, name, TypeV.Url);
	}
    }

    public static class XPath extends ValueType {

	public XPath(PathElement parent) {
	    this(parent, "XPath");
	}

	public XPath(PathElement parent, String name) {
	    super(parent, name, TypeV.XPath);
	}
    }

    public static class AgentName extends ValueType {

	public AgentName(PathElement parent) {
	    this(parent, "AgentName");
	}

	public AgentName(PathElement parent, String name) {
	    super(parent, name, TypeV.AgentName);
	}
    }

    public static class GPSCoordinate extends ValueType {

	public GPSCoordinate(PathElement parent) {
	    this(parent, "GPSCoordinate");
	}

	public GPSCoordinate(PathElement parent, String name) {
	    super(parent, name, TypeV.GPSCoordinate);
	}
    }

    public static class Real extends ValueType {

	public Real(PathElement parent) {
	    this(parent, "Real");
	}

	public Real(PathElement parent, String name) {
	    super(parent, name, TypeV.Real);
	    addQualifier(new XmpPropertyImpl("", "vQual", "vQual:binRep", Category.INTERNAL, "The text is interpreted as:"
		    + "std size,endian,hexadecimal_value. \"std\" is the standard name (\"IEEE754\") \"size\" is S for 32-bit and D for 64-bit "
		    + "\"endian\" is L for little-endian, B for big-endian. For example: \"IEEE754D,L,3A4901F387D31108\"").setValueType(TypeV.Text));
	}
    }

    static abstract class Choice<E extends ValueType> extends ValueType {
	ValueType valueType = new Text(this);

	ValueType[] values = new ValueType[0];
	String[] descriptions = new String[0];

	protected Choice(TypeV type) {
	    super(type);
	}

	protected Choice(TypeV type, TypeV t) {
	    this(null, "", type, t);
	}

	protected Choice(PathElement parent, TypeV type, TypeV t) {
	    this(parent, "", type, t);
	}

	protected Choice(PathElement parent, String name, TypeV type, TypeV t) {
	    super(parent, name, type);
	    this.valueType = ValueTypeFactory.getInstance().create(this, t);
	}

	protected Choice(PathElement parent, TypeV type) {
	    super(parent, type);
	}

	protected Choice(PathElement parent, TypeV type, ValueType valueType) {
	    this(parent, "", type, valueType);
	}

	protected Choice(PathElement parent, String name, TypeV type, ValueType valueType) {
	    super(parent, name, type);
	    this.valueType = valueType;
	    if (valueType != null) {
		valueType.parent = this;
	    }
	}

	public Choice(PathElement parent, TypeV type, String choiceName) {
	    super(parent, choiceName, type);
	}

	protected Choice<E> setValueType(ValueType type) {
	    this.valueType = type;
	    this.valueType.parent = this;
	    return this;
	}

	protected Choice<E> setValueType(TypeV t) {
	    this.valueType = ValueTypeFactory.getInstance().create(this, t);
	    return this;
	}

	Choice<E> add(ValueType value) {
	    return add(value, "");
	}

	Choice<E> add(String value) {
	    return add(value, "");
	}

	Choice<E> add(String value, String description) {
	    return add(new ValueTypes.Text(this, value), description);
	}

	Choice<E> add(ValueType value, String description) {
	    value.parent = this;
	    ValueType[] v = new ValueType[values.length + 1];
	    String[] d = new String[descriptions.length + 1];
	    for (int i = 0; i < values.length; i++) {
		v[i] = values[i];
		d[i] = descriptions[i];
	    }
	    v[values.length] = value;
	    d[descriptions.length] = description;
	    values = v;
	    descriptions = d;
	    return this;
	}

	@Override
	public String toXMP(XMP_HeaderConfiguration hc) {
	    XMP_StringBuffer sb = new XMP_StringBuffer(hc);
	    // sb.append("<rdf:li rdf:parseType=\"Resource\">\n");

	    sb.addTag(hc.valueType, "name", name);

	    String elemType = "";
	    if (valueType != null) {
		elemType = "" + type + " of " + valueType;
		sb.addTag(hc.valueType, "type", elemType);
	    }

	    if (values != null && values.length > 0) {
		sb.startTag(hc.namePrefix + hc.choice + ":values>\n");
		sb.append("<rdf:Bag>\n");
		for (int i = 0; i < values.length; i++) {
		    sb.append("<rdf:li rdf:parseType=\"Resource\">\n");

		    sb.addTag(hc.choice, "value", values[i].toString());

		    String d = descriptions[i];
		    if (d != null && !d.trim().isEmpty()) {
			sb.addTag(hc.choice, "description", d);
		    }
		    sb.append("</rdf:li>\n");
		}
		sb.append("</rdf:Bag>\n");
		sb.endTag(hc.namePrefix + hc.choice + ":values>\n");
	    }
	    // sb.append("</rdf:li>\n");

	    return sb.toString();
	}

	@Override
	public PathElement getChildAt(int index) {
	    return values[index];
	}

	@Override
	public int getChildrenCount() {
	    return values.length;
	}
	
	

    }

    public static class OpenChoice<E> extends Choice<ValueType> {

	public OpenChoice(PathElement parent, String choiceName) {
	    super(parent, TypeV.OpenChoice, choiceName);
	}

	public OpenChoice(PathElement parent, ValueType valueType) {
	    super(parent, "OpenChoice", TypeV.OpenChoice, valueType);
	}

	public OpenChoice(TypeV t) {
	    super(null, "OpenChoice", TypeV.OpenChoice, t);
	}
    }

    public static class ClosedChoice<E> extends Choice<ValueType> {

	public ClosedChoice(PathElement parent, String choiceName) {
	    super(parent, TypeV.ClosedChoice, choiceName);
	}

	public ClosedChoice(PathElement parent, ValueType valueType) {
	    super(parent, "ClosedChoice", TypeV.ClosedChoice, valueType);
	}

	public ClosedChoice(PathElement parent, TypeV type, String choiceName) {
	    super(parent, choiceName, TypeV.ClosedChoice, type);
	}

	public ClosedChoice(PathElement parent, TypeV t) {
	    super(parent, "ClosedChoice", TypeV.ClosedChoice, t);
	}

	public ClosedChoice(TypeV t) {
	    super(null, "ClosedChoice", TypeV.ClosedChoice, t);
	}

	// public ClosedChoice() {
	// super(TypeV.ClosedChoice);
	// }
    }

    static class ResourceRef extends Structure {

	public ResourceRef(PathElement parent) {
	    this(parent, "ResourceRef");
	}

	public ResourceRef(PathElement parent, String name) {
	    super(TypeV.ResourceRef, parent, name, "stRef", "http://ns.adobe.com/xap/1.0/sType/ResourceRef#");
	    addType(new Uri(this, "instanceID"));
	    addType(new Uri(this, "documentID"));
	    addType(new Text(this, "versionID"));
	    addType(new RenditionClass(this, "renditionClass"));
	    addType(new Text(this, "renditionParams"));
	    addType(new AgentName(this, "manager"));
	    addType(new Text(this, "managerVariant"));
	    addType(new Uri(this, "manageTo"));
	    addType(new Uri(this, "manageUI"));
	}
    }

    public static class Colorant extends Structure {

	public Colorant(PathElement parent) {
	    this(parent, "Colorant");
	}

	public Colorant(PathElement parent, String name) {
	    super(TypeV.Colorant, parent, name, "xapG", "http://ns.adobe.com/xap/1.0/g/");

	    addType(new Text(this, "swatchName"));

	    ClosedChoice<Text> mode = new ClosedChoice<Text>(this, "mode");
	    mode.add("CMYK", "CMYK").add("RGB", "RGB").add("LAB", "LAB");
	    addType(mode);

	    ClosedChoice<Text> typeChoice = new ClosedChoice<Text>(this, "type");
	    typeChoice.add("PROCESS", "PROCESS").add("SPOT", "SPOT");
	    addType(typeChoice);

	    addType(new Real(this, "cyan"));
	    addType(new Real(this, "magenta"));
	    addType(new Real(this, "yellow"));
	    addType(new Real(this, "black"));
	    addType(new Integer(this, "red"));
	    addType(new Integer(this, "green"));
	    addType(new Integer(this, "blue"));
	    addType(new Real(this, "L"));
	    addType(new Integer(this, "A"));
	    addType(new Integer(this, "B"));
	}
    }

    public static class Marker extends Structure {

	public Marker(PathElement parent) {
	    this(parent, "Marker");
	}

	public Marker(PathElement parent, String name) {
	    super(TypeV.Marker, parent, name, "xmpDM", "http://ns.adobe.com/xmp/1.0/DynamicMedia/");
	    addType(new Time(this, "startTime"));
	    addType(new Time(this, "duration"));
	    addType(new Text(this, "comment"));
	    addType(new Text(this, "name"));
	    addType(new Uri(this, "location"));
	    addType(new Text(this, "target"));

	    ClosedChoice<Text> choice = new ClosedChoice<Text>(this, TypeV.Text);
	    choice.add("Chapter", "").add("Cue", "").add("Beat", "").add("Track", "").add("Index", "");
	    addType(choice);
	}
    }

    public static class Font extends Structure {

	public Font(PathElement parent) {
	    this(parent, "Font");
	}

	public Font(PathElement parent, String name) {
	    super(TypeV.Font, parent, name, "stFnt", "http:ns.adobe.com/xap/1.0/sType/Font#");
	    addType(new Text(this, "fontName"));
	    addType(new Text(this, "fontFamily"));
	    addType(new Text(this, "fontFace"));

	    OpenChoice<Text> fontType = new OpenChoice<Text>(this, "fontType");
	    fontType.add("TrueType,", "TrueType").add("Type 1", "Type 1").add("Open Type", "Open Type");
	    addType(fontType);

	    addType(new SimpleString(this, "versionString")); // The
	    // version
	    // string:
	    // /version for Type1 fonts
	    // nameId 5 for Apple True Type and OpenType
	    // /CIDFontVersion for CID fonts
	    // The empty string for bitmap fonts
	    addType(new Boolean(this, "composite"));
	    addType(new SimpleString(this, "fontFileName"));
	    Array array = new Array(this, ValueType.ArrayType.Seq);
	    array.setValueType(new SimpleString(array, "childFontFiles"));
	    addType(array);
	}
    }

    public static class Array extends ValueType {
	ValueType valueType;
	ValueType.ArrayType arrayType;

	public ValueType getValueType() {
	    return valueType;
	}

	public ValueType.ArrayType getArrayType() {
	    return arrayType;
	}

	public Array(String arrayType, String elementType) {
	    this(null, ArrayType.valueOf(arrayType), TypeV.valueOf(elementType));
	}

	public Array(PathElement parent, ValueType.ArrayType arrayType) {
	    this(parent, "Array", arrayType);
	}

	public Array(PathElement parent, ValueType.ArrayType arrayType, ValueType elementType) {
	    this(parent, "Array", arrayType, elementType);
	}

	public Array(PathElement parent, String arrayName, ValueType.ArrayType arrayType, ValueType elementType) {
	    super(parent, arrayName, TypeV.Array);
	    this.arrayType = arrayType;
	    this.valueType = elementType;
	}

	public Array(PathElement parent, String arrayName, ValueType.ArrayType arrayType) {
	    super(parent, arrayName, TypeV.Array);
	    this.arrayType = arrayType;
	}

	public Array(PathElement parent, ValueType.ArrayType arrayType, TypeV elementType) {
	    this(parent, "Array", arrayType, elementType);
	}

	public Array(PathElement parent, String arrayName, ValueType.ArrayType arrayType, TypeV elementType) {
	    super(parent, arrayName, TypeV.Array);
	    this.arrayType = arrayType;
	    this.valueType = ValueTypeFactory.getInstance().create(this, elementType);
	}

	protected void setValueType(ValueType type) {
	    this.valueType = type;
	}

	// @Override
	// public String toString() {
	// if (valueType.type == TypeV.Structure) {
	// return arrayType + " " + valueType.name;
	// } else {
	// return arrayType + " " + valueType;
	// }
	// }
    }

    public static class LangAlt extends Array {

	public LangAlt(PathElement parent) {
	    super(parent, ArrayType.LangAlt, TypeV.Text);
	    addQualifier(new XmpPropertyImpl("xml:lang", Category.INTERNAL, "A string that conforms to RFC 3066 notation").setValueType(TypeV.Text));
	}

    }

    public static class RenditionClass extends OpenChoice<Text> {
	public RenditionClass(PathElement parent) {
	    this(parent, "RenditionClass");
	}

	public RenditionClass(PathElement parent, String name) {
	    super(parent, name);
	    add("default", "The master document; no additional tokens allowed.");
	    add("thumbnail", "A simplified or reduced preview of a version.");
	    add("screen", "Screen resolution or Web rendition.");
	    add("proof", "A review proof.");
	    add("draft", "A review rendition.");
	    add("low-res", "A low resolution, full size stand-in.");
	}
    }

    public static class ResourceEvent extends Structure {
	public ResourceEvent(PathElement parent) {
	    this(parent, "ResourceEvent");
	}

	public ResourceEvent(PathElement parent, String structName) {
	    super(TypeV.ResourceEvent, parent, structName, "stEvt", "http://ns.adobe.com/xap/1.0/sType/ResourceEvent#");
	    OpenChoice<Text> action = new OpenChoice<Text>(this, "action");
	    action.add("converted", "").add("copied", "").add("created", "");
	    action.add("cropped", "").add("edited", "").add("filtered", "");
	    action.add("formatted", "").add("version_updated", "");
	    action.add("printed", "").add("published", "").add("managed", "");
	    action.add("produced", "").add("resized", "");

	    addType(action);
	    addType(new Uri(this, "instanceID"));
	    addType(new Text(this, "parameters"));
	    addType(new AgentName(this, "softwareAgent"));
	    addType(new Date(this, "when"));
	}
    }

    public static class Dimensions extends Structure {
	public Dimensions(PathElement parent) {
	    this(parent, "Dimensions");
	}

	public Dimensions(PathElement parent, String structName) {
	    super(TypeV.Dimensions, parent, structName, "stDim", "http://ns.adobe.com/xap/1.0/sType/Dimensions#");
	    addType(new Text(this, "w"));
	    addType(new Text(this, "h"));
	    addType(new OpenChoice<Text>(this, "units"));
	}
    }

    public static class ContactInfo extends Structure {
	public ContactInfo(PathElement parent) {
	    this(parent, "ContactInfo");
	}

	public ContactInfo(PathElement parent, String structName) {
	    super(TypeV.ContactInfo, parent, structName, "Iptc4xmpCore", "http://iptc.org/std/Iptc4xmpCore/1.0/xmlns/");
	    addType(new Text(this, "CiAdrCity"));
	    addType(new Text(this, "CiAdrCtry"));
	    addType(new Text(this, "CiAdrExtadr"));
	    addType(new Text(this, "CiAdrPcode"));
	    addType(new Text(this, "CiAdrRegion"));
	    addType(new Text(this, "CiEmailWork"));
	    addType(new Text(this, "CiTelWork"));
	    addType(new Text(this, "CiUrlWork"));
	}
    }

    public static class Time extends Structure {
	public Time(PathElement parent) {
	    this(parent, "Time");
	}

	public Time(PathElement parent, String structName) {
	    super(TypeV.Time, parent, structName, "xmpDM", "http://ns.adobe.com/xmp/1.0/DynamicMedia/");
	    addType(new Integer(this, "value"));
	    addType(new Rational(this, "scale"));
	}
    }

    public static class Thumbnail extends Structure {

	public Thumbnail(PathElement parent) {
	    this(parent, "Thumbnail");
	}

	public Thumbnail(PathElement parent, String name) {
	    super(TypeV.Thumbnail, parent, name, "xapGImg", "http://ns.adobe.com/xap/1.0/g/img/");
	    addType(new Integer(this, "width"));
	    addType(new Integer(this, "height"));
	    addType(new OpenChoice<Text>(this, "format").add("JPEG", "JPEG"));
	    addType(new Text(this, "image"));
	}
    }

    public static class Structure extends ValueType {
	protected ArrayList<ValueType> types = new ArrayList<ValueType>();

	String prefix;
	String uri;

	protected Structure(TypeV type, PathElement parent, String structName, String prefix, String uri) {
	    super(parent, structName, type);
	    this.prefix = prefix;
	    this.uri = uri;
	}

	protected void addType(ValueType type) {
	    this.types.add(type);
	}

	@Override
	public String toXMP(XMP_HeaderConfiguration hc) {
	    XMP_StringBuffer sb = new XMP_StringBuffer(hc);
	    // sb.append("<rdf:li rdf:parseType=\"Resource\">\n");

	    sb.addTag(hc.valueType, "name", name);

	    sb.addTag(hc.valueType, "type", type.toString());

	    sb.addTag(hc.shema, "prefix", prefix);

	    sb.addTag(hc.shema, "uri", uri);

	    if (types.size() > 0) {

		sb.startTag(hc.namePrefix + hc.choice + ":values>");
		sb.append("<rdf:Bag>\n");
		for (int i = 0; i < types.size(); i++) {
		    sb.append("<rdf:li rdf:parseType=\"Resource\">\n");
		    sb.append(types.get(i).toXMP(hc));
		    sb.append("</rdf:li>\n");
		}
		sb.append("</rdf:Bag>\n");
		sb.endTag(hc.namePrefix + hc.choice + ":values>");
	    }

	    // sb.append("</rdf:li>\n");
	    return sb.toString();
	}
    }

    static class TimeScaleStretch extends Structure {
	public TimeScaleStretch(PathElement parent) {
	    super(TypeV.TimeScaleStretch, parent, "timeScaleStretch", "xmpDM", "http://ns.adobe.com/xmp/1.0/DynamicMedia");

	    addType(new ClosedChoice<Text>(this, TypeV.Text).add("High").add("Medium").add("Low"));
	    addType(new Real(this, "frameSize"));
	    addType(new Real(this, "frameOverlappingPercentage"));
	}
    }

    static class TimeCode extends Structure {
	public TimeCode(PathElement parent) {
	    super(TypeV.TimeCode, parent, "TimeCode", "xmpDM", "http://ns.adobe.com/xmp/1.0/DynamicMedia/");

	    addType(new Text(this, "timeValue"));
	    addType(new ClosedChoice<Text>(this, "timeFormat").add("24Timecode").add("25Timecode").add("2997DropTimecode").add("2997NonDropTimecode").add(
		    "30Timecode").add("50Timecode").add("5994DropTimecode").add("5994NonDropTimecode").add("60Timecode").add("23976Timecode"));
	}
    }

    static class ProjectLink extends Structure {
	public ProjectLink(PathElement parent) {
	    super(TypeV.ProjectLink, parent, "ProjectLink", "xmpDM", "http://ns.adobe.com/xmp/1.0/DynamicMedia/");

	    addType(new ClosedChoice<Text>(this, "type").add("movie").add("still").add("audio").add("custom"));
	    addType(new Uri(this, "path"));
	}
    }

    static class Media extends Structure {
	public Media(PathElement parent) {
	    super(TypeV.Media, parent, "Media", "xmpDM", "http://ns.adobe.com/xmp/1.0/DynamicMedia/");
	    addType(new Uri(this, "path"));
	    addType(new Text(this, "track"));
	    addType(new Time(this, "startTime"));
	    addType(new Time(this, "duration"));
	    addType(new Boolean(this, "managed"));
	    addType(new Uri(this, "webStatement"));
	}
    }

    static class BeatSpliceStretch extends Structure {
	public BeatSpliceStretch(PathElement parent) {
	    super(TypeV.BeatSpliceStretch, parent, "beatSpliceStretch", "xmpDM", "http://ns.adobe.com/xmp/1.0/DynamicMedia/");

	    addType(new Boolean(this, "useFileBeatsMarker"));
	    addType(new Real(this, "riseInDecibel"));
	    addType(new Time(this, "riseInTimeDuration"));
	}
    }

    static class ResampleStretch extends Structure {
	public ResampleStretch(PathElement parent) {
	    super(TypeV.ResampleStretch, parent, "resampleStretch", "xmpDM", "http://ns.adobe.com/xmp/1.0/DynamicMedia/");
	    addType(new ClosedChoice<Text>(this, "quality").add("High").add("Medium").add("Low"));
	}
    }

    static class DeviceSettings extends Structure {
	public DeviceSettings(PathElement parent) {
	    super(TypeV.DeviceSettings, parent, "DeviceSettings", "exif", "http://ns.adobe.com/exif/1.0/");
	    addType(new Integer(this, "Columns"));
	    addType(new Integer(this, "Rows"));
	    Array array = new Array(this, "Settings", ArrayType.Seq);
	    array.setValueType(new Text(array));
	    addType(array);
	}
    }

    static class CFAPattern extends Structure {
	public CFAPattern(PathElement parent) {
	    super(TypeV.CFAPattern, parent, "CFAPattern", "exif", "http://ns.adobe.com/exif/1.0/");
	    addType(new Integer(this, "Columns"));
	    addType(new Integer(this, "Rows"));
	    Array array = new Array(this, "Values", ArrayType.Seq);
	    array.setValueType(new Integer(array));
	    addType(array);
	}
    }

    static class OECF_SFR extends Structure {
	public OECF_SFR(PathElement parent) {
	    super(TypeV.OECF_SFR, parent, "OECF/SFR", "exif", "http://ns.adobe.com/exif/1.0/");
	    addType(new Integer(this, "Columns"));
	    addType(new Integer(this, "Rows"));

	    Array names = new Array(this, "Names", ArrayType.Seq);
	    names.setValueType(new Text(names));
	    addType(names);

	    Array values = new Array(this, "Values", ArrayType.Seq);
	    values.setValueType(new Rational(values));
	    addType(values);
	}
    }

    static class Flash extends Structure {
	public Flash(PathElement parent) {
	    super(TypeV.Flash, parent, "Flash", "exif", "http://ns.adobe.com/exif/1.0/");
	    addType(new Boolean(this, "Fired"));
	    ClosedChoice<Integer> returnChoice = new ClosedChoice<Integer>(this, "Return");

	    returnChoice.add(new Integer(returnChoice, "0"), "no strobe return detection").add(new Integer(returnChoice, "2"),
		    "strobe return light not detected").add(new Integer(returnChoice, "3"), "strobe return light detected");
	    addType(returnChoice);
	    ClosedChoice<Integer> modeChoice = new ClosedChoice<Integer>(this, TypeV.Integer, "Mode");
	    modeChoice.setValueType(new Integer(modeChoice));
	    modeChoice.add(new Integer(modeChoice, "0"), "unknown").add(new Integer(modeChoice, "1"), "compulsory flash firing").add(
		    new Integer(modeChoice, "2"), "compulsory flash suppression").add(new Integer(modeChoice, "3"), "auto mode");
	    addType(modeChoice);
	    addType(new Boolean(this, "Function"));
	    addType(new Boolean(this, "RedEyeMode"));
	}
    }

    static class Job extends Structure {
	public Job(PathElement parent) {
	    super(TypeV.Job, parent, "Job", "stJob", "http://ns.adobe.com/xap/1.0/sType/Job#");
	    addType(new Text(this, "name"));
	    addType(new Text(this, "id"));
	    addType(new Uri(this, "url"));
	}
    }

    static class Version extends Structure {
	public Version(PathElement parent) {
	    super(TypeV.Version, parent, "Version", "stVer", "http://ns.adobe.com/xap/1.0/sType/Version#");
	    addType(new Text(this, "comments"));
	    addType(new ResourceEvent(this, "event"));
	    addType(new Date(this, "modifyDate"));
	    addType(new ProperName(this, "modifier"));
	    addType(new Text(this, "version"));
	}
    }
}
