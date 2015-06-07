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
import com.smartg.xmp.ValueTypes.Structure;

/**
 * @author Andrey Kuznetsov
 */
public class PhotoshopShema extends XMPShema {
    public static final String NAME = "Photoshop";
    public static final PhotoshopShema SHEMA = new PhotoshopShema();

    private PhotoshopShema() {
	super("http://ns.adobe.com/photoshop/1.0/", "photoshop", NAME);
    }

    @Override
    protected void initProperties() {
	Properties.AUTHORS_POSITION.delegate.set("AuthorsPosition", EXTERNAL, TypeV.Text, "By-Line title");
	Properties.CAPTION_WRITER.delegate.set("CaptionWriter", EXTERNAL, TypeV.ProperName, "Writer/editor");
	Properties.CATEGORY.delegate.set("Category", EXTERNAL, TypeV.Text, "Category. Limited to 3 7-bit ASCII characters");
	Properties.CITY.delegate.set("City", EXTERNAL, TypeV.Text, "City");
	Properties.COUNTRY.delegate.set("Country", EXTERNAL, TypeV.Text, "Country/primary location");
	Properties.CREDIT.delegate.set("Credit", EXTERNAL, TypeV.Text, "Credit");
	Properties.DATE_CREATED.delegate.set("DateCreated", EXTERNAL, TypeV.Date,
		"The date the intellectual content of the document was created (rather than the creation date of the physical representation), "
			+ "following IIM conventions. For example, a photo taken during the American Civil War "
			+ "would have a creation date during that epoch (1861-1865) rather than the date the photo was digitized for archiving.");

	Properties.HEADLINE.delegate.set("Headline", EXTERNAL, TypeV.Text, "Headline");
	Properties.INSTRUCTIONS.delegate.set("Instructions", EXTERNAL, TypeV.Text, "Special instructions");
	Properties.SOURCE.delegate.set("Source", EXTERNAL, TypeV.Text, "Source");
	Properties.STATE.delegate.set("State", EXTERNAL, TypeV.Text, "Province/state");
	Properties.SUPPLEMENTAL_CATEGORIE.delegate.set("SupplementalCategories", EXTERNAL, ArrayType.Bag, TypeV.Text, "Supplemental categories");
	Properties.TRANSMISSION_REFERENCE.delegate.set("TransmissionReference", EXTERNAL, TypeV.Text, "Original transmission reference");
	Properties.URGENCY.delegate.set("Urgency", EXTERNAL, TypeV.Text, "Urgency. Valid range is 1-8.");	
    }

    public XmpProperty[] properties() {
	return Properties.values();
    }

    public enum Properties implements XmpProperty {
	AUTHORS_POSITION, CAPTION_WRITER, CATEGORY, CITY, COUNTRY, CREDIT, DATE_CREATED, HEADLINE, INSTRUCTIONS, SOURCE, STATE, SUPPLEMENTAL_CATEGORIE, TRANSMISSION_REFERENCE, URGENCY;

	private XmpPropertyImpl delegate = new XmpPropertyImpl();

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
