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
public class Iptc4XmpCore extends XMPShema {

    public static final Iptc4XmpCore SHEMA = new Iptc4XmpCore();

    public static final String NAME = "IPTC";

    private Iptc4XmpCore() {
	super("http://iptc.org/std/Iptc4xmpCore/1.0/xmlns/", "Iptc4xmpCore", NAME);

    }

    @Override
    protected void initProperties() {
	ValueTypes.Structure cinfo = new ValueTypes.ContactInfo(null);
	Properties.CREATOR_CONTACT_INFO.delegate.set("ContactInfo", cinfo, "");

	Properties.CI_ADR_CITY.delegate.set("CiAdrCity", TypeV.Text, "City");
	Properties.CI_ADR_CITY.setMemberOf(cinfo);

	Properties.CI_ADR_COUNTRY.delegate.set("CiAdrCtry", TypeV.Text, "County");
	Properties.CI_ADR_COUNTRY.setMemberOf(cinfo);

	Properties.CI_ADR_EXTADR.delegate.set("CiAdrExtadr", TypeV.Text, "Address");
	Properties.CI_ADR_EXTADR.setMemberOf(cinfo);

	Properties.CI_ADR_PCODE.delegate.set("CiAdrPcode", TypeV.Text, "Postal Code");
	Properties.CI_ADR_PCODE.setMemberOf(cinfo);

	Properties.CI_ADR_REGION.delegate.set("CiAdrRegion", TypeV.Text, "State/province");
	Properties.CI_ADR_REGION.setMemberOf(cinfo);

	Properties.CI_EMAIL_WORK.delegate.set("CiEmailWork", TypeV.Text, "Email(s) separated by a comma");
	Properties.CI_EMAIL_WORK.setMemberOf(cinfo);

	Properties.CI_TEL_WORK.delegate.set("CiTelWork", TypeV.Text, "Phone(s) separated by a comma");
	Properties.CI_TEL_WORK.setMemberOf(cinfo);

	Properties.CI_URL_WORK.delegate.set("CiUrlWork", TypeV.Text, "Web URL(s) separated by a comma");
	Properties.CI_URL_WORK.setMemberOf(cinfo);

	Choice<ValueType> ccChoice = new ValueTypes.ClosedChoice<ValueTypes.Text>(TypeV.Text);
	Properties.COUNTRY_CODE.delegate.set("CountryCode", ccChoice, "ISO Country Code");

	Properties.INTELLECTUAL_GENRE.delegate.set("IntellectualGenre", TypeV.Text,
		"Describes the nature, intellectual or journalistic characteristic of a news object, not specifically its content.");

	Properties.LOCATION.delegate.set("Location", TypeV.Text, "Name of a location the content is focussing on");
	String sceneDesc = "Describes the scene of a photo content. Specifies one ore more terms from the IPTC \"Scene-NewsCodes\". Each Scene is represented as a string of 6 digits in an unordered list.";
	Properties.SCENE.delegate.set("Scene", EXTERNAL, ArrayType.Bag, TypeV.ClosedChoice, sceneDesc);
	String subjCodeDesc = "Specifies one or more Subjects from the IPTC \"Subject-NewsCodes\" taxonomy to categorize the content. Each Subject is represented as a string of 8 digits in an unordered list.";
	Properties.SUBJECT_CODE.delegate.set("SubjectCode", EXTERNAL, ArrayType.Bag, TypeV.ClosedChoice, subjCodeDesc);	
    }



    public XmpProperty[] properties() {
	return Properties.values();
    }

    public enum Properties implements XmpProperty {
	CREATOR_CONTACT_INFO, CI_ADR_CITY, CI_ADR_COUNTRY, CI_ADR_EXTADR, CI_ADR_PCODE, CI_ADR_REGION, CI_EMAIL_WORK, CI_TEL_WORK, CI_URL_WORK, COUNTRY_CODE, INTELLECTUAL_GENRE, LOCATION, SCENE, SUBJECT_CODE;

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
