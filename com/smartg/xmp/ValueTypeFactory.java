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

import com.smartg.xmp.ValueType.ArrayType;
import com.smartg.xmp.ValueType.TypeV;
import com.smartg.xmp.ValueTypes.AgentName;
import com.smartg.xmp.ValueTypes.Array;
import com.smartg.xmp.ValueTypes.BeatSpliceStretch;
import com.smartg.xmp.ValueTypes.CFAPattern;
import com.smartg.xmp.ValueTypes.Colorant;
import com.smartg.xmp.ValueTypes.ContactInfo;
import com.smartg.xmp.ValueTypes.Date;
import com.smartg.xmp.ValueTypes.DeviceSettings;
import com.smartg.xmp.ValueTypes.Dimensions;
import com.smartg.xmp.ValueTypes.Flash;
import com.smartg.xmp.ValueTypes.Font;
import com.smartg.xmp.ValueTypes.GPSCoordinate;
import com.smartg.xmp.ValueTypes.Job;
import com.smartg.xmp.ValueTypes.MIMEType;
import com.smartg.xmp.ValueTypes.Marker;
import com.smartg.xmp.ValueTypes.Media;
import com.smartg.xmp.ValueTypes.OECF_SFR;
import com.smartg.xmp.ValueTypes.ProjectLink;
import com.smartg.xmp.ValueTypes.ProperName;
import com.smartg.xmp.ValueTypes.Rational;
import com.smartg.xmp.ValueTypes.Real;
import com.smartg.xmp.ValueTypes.ResampleStretch;
import com.smartg.xmp.ValueTypes.ResourceEvent;
import com.smartg.xmp.ValueTypes.ResourceRef;
import com.smartg.xmp.ValueTypes.SimpleString;
import com.smartg.xmp.ValueTypes.Text;
import com.smartg.xmp.ValueTypes.Thumbnail;
import com.smartg.xmp.ValueTypes.Time;
import com.smartg.xmp.ValueTypes.TimeCode;
import com.smartg.xmp.ValueTypes.TimeScaleStretch;
import com.smartg.xmp.ValueTypes.Uri;
import com.smartg.xmp.ValueTypes.Url;
import com.smartg.xmp.ValueTypes.Version;
import com.smartg.xmp.ValueTypes.XPath;

class ValueTypeFactory {

    static ValueTypeFactory instance = new ValueTypeFactory();

    static ValueTypeFactory getInstance() {
	return instance;
    }

    private ValueTypeFactory() {

    }

    ValueType create(PathElement parent, ArrayType arrayType, TypeV elementType) {
	return new Array(parent, arrayType, elementType);
    }
    
    ValueType create(PathElement parent, ArrayType arrayType, ValueType elementType) {
	return new Array(parent, arrayType, elementType);
    }
    

    ValueType create(PathElement parent, ArrayType arrayType) {
	return new Array(parent, arrayType);
    }

    ValueType create(PathElement parent, TypeV type) {
	ValueType valueType = create0(parent, type);
	if (valueType != null) {
	    if (type != valueType.type) {
		throw new RuntimeException("Wrong type created: \t requested type: " + type + " \t created type: " + valueType.type);
	    }
	}
	return valueType;
    }
    
    Class<? extends ValueType> getClass(TypeV type) {
	switch (type) {
	case AgentName:
	    return AgentName.class;
	case Array:
	    return Array.class;
	case Boolean:
	    return ValueTypes.Boolean.class;
	case OpenChoice:
	    return ValueTypes.OpenChoice.class;
	case ClosedChoice:
	    return ValueTypes.ClosedChoice.class;
	case Date:
	    return ValueTypes.Date.class;
	case GPSCoordinate:
	    return GPSCoordinate.class;
	case Integer:
	    return ValueTypes.Integer.class;
	case MIMEType:
	    return MIMEType.class;
	case ProperName:
	    return ProperName.class;
	case Rational:
	    return ValueTypes.Rational.class;
	case Real:
	    return ValueTypes.Real.class;
	case SimpleString:
	    return SimpleString.class;
	case Text:
	    return Text.class;
	case Uri:
	    return Uri.class;
	case Url:
	    return Url.class;
	case XPath:
	    return XPath.class;
	case BeatSpliceStretch:
	    return BeatSpliceStretch.class;
	case CFAPattern:
	    return CFAPattern.class;
	case Colorant:
	    return Colorant.class;
	case ContactInfo:
	    return ContactInfo.class;
	case DeviceSettings:
	    return DeviceSettings.class;
	case Dimensions:
	    return Dimensions.class;
	case Flash:
	    return Flash.class;
	case Font:
	    return Font.class;
	case Job:
	    return Job.class;
	case Marker:
	    return Marker.class;
	case Media:
	    return Media.class;
	case OECF_SFR:
	    return OECF_SFR.class;
	case ProjectLink:
	    return ProjectLink.class;
	case ResampleStretch:
	    return ResampleStretch.class;
	case ResourceEvent:
	    return ResourceEvent.class;
	case ResourceRef:
	    return ResourceRef.class;
	case Thumbnail:
	    return Thumbnail.class;
	case Time:
	    return Time.class;
	case TimeCode:
	    return TimeCode.class;
	case TimeScaleStretch:
	    return TimeScaleStretch.class;
	case Version:
	    return Version.class;
	}
	throw new RuntimeException("Unknown ValueType: " + type);

    }

    ValueType create0(PathElement parent, TypeV type) {
	switch (type) {
	case AgentName:
	    return new AgentName(parent);
	case Array:
	    throw new IllegalArgumentException();
	case Boolean:
	    return new ValueTypes.Boolean(parent);
	case OpenChoice:
	    return new ValueTypes.OpenChoice<ValueType>(parent, (ValueType)null);
	case ClosedChoice:
	    return new ValueTypes.ClosedChoice<ValueType>(parent, (ValueType)null);
	case Date:
	    return new Date(parent);
	case GPSCoordinate:
	    return new GPSCoordinate(parent);
	case Integer:
	    return new ValueTypes.Integer(parent);
	case MIMEType:
	    return new MIMEType(parent);
	case ProperName:
	    return new ProperName(parent);
	case Rational:
	    return new Rational(parent);
	case Real:
	    return new Real(parent);
	case SimpleString:
	    return new SimpleString(parent);
	case Text:
	    return new Text(parent);
	case Uri:
	    return new Uri(parent);
	case Url:
	    return new Url(parent);
	case XPath:
	    return new XPath(parent);
	case BeatSpliceStretch:
	    return new BeatSpliceStretch(parent);
	case CFAPattern:
	    return new CFAPattern(parent);
	case Colorant:
	    return new Colorant(parent);
	case ContactInfo:
	    return new ContactInfo(parent);
	case DeviceSettings:
	    return new DeviceSettings(parent);
	case Dimensions:
	    return new Dimensions(parent);
	case Flash:
	    return new Flash(parent);
	case Font:
	    return new Font(parent);
	case Job:
	    return new Job(parent);
	case Marker:
	    return new Marker(parent);
	case Media:
	    return new Media(parent);
	case OECF_SFR:
	    return new OECF_SFR(parent);
	case ProjectLink:
	    return new ProjectLink(parent);
	case ResampleStretch:
	    return new ResampleStretch(parent);
	case ResourceEvent:
	    return new ResourceEvent(parent);
	case ResourceRef:
	    return new ResourceRef(parent);
	case Thumbnail:
	    return new Thumbnail(parent);
	case Time:
	    return new Time(parent);
	case TimeCode:
	    return new TimeCode(parent);
	case TimeScaleStretch:
	    return new TimeScaleStretch(parent);
	case Version:
	    return new Version(parent);
	}
	throw new RuntimeException("Unknown ValueType: " + type);
    }
}
