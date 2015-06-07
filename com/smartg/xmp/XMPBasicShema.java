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
import com.smartg.xmp.ValueTypes.Structure;

/**
 * @author Andrey Kuznetsov
 */
public class XMPBasicShema extends XMPShema {

    public static final XMPBasicShema SHEMA = new XMPBasicShema();

    public static final String NAME = "XMPBasic";

    private XMPBasicShema() {
	super("http://ns.adobe.com/xap/1.0/", "xmp", NAME);
    }
    
    @Override
    protected void initProperties() {
	Properties.ADVISORY.delegate.set("Advisory", EXTERNAL, ArrayType.Bag, TypeV.XPath, "An unordered array specifying properties that were edited outside "
		+ "the authoring application. Each item should contain a single namespace and XPath separated by one ASCII space (U+0020).");

	Properties.BASEURL.delegate.set("BaseURL", INTERNAL, TypeV.Url, "The base URL for relative URLs in the document content. If this document contains "
		+ "Internet links, and those links are relative, they are relative to this base URL."
		+ "This property provides a standard way for embedded relative URLs "
		+ "to be interpreted by tools. Web authoring tools should set the value based on their notion of where URLs will be interpreted.");

	Properties.CREATE_DATE.delegate.set("CreateDate", EXTERNAL, TypeV.Date, "The date and time the resource was originally created.");

	Properties.CREATOR_TOOL.delegate.set("CreatorTool", INTERNAL, TypeV.AgentName, "The name of the first known tool used to create the resource. "
		+ "If history is present in the metadata, this value should be equivalent to that of xmpMM:History’s softwareAgent property.");

	Properties.IDENTIFIER.delegate.set("Identifier", EXTERNAL, ArrayType.Bag, TypeV.Text,
		"An unordered array of text strings that unambiguously identify the resource within "
			+ "a given context. An array item may be qualified with xmpidq:Scheme to denote the "
			+ "formal identification system to which that identifier conforms.NOTE:"
			+ "The dc:identifier property is not used because it lacks a defined scheme "
			+ "qualifier and has been defined in the XMP Specification as a simple (single-valued) property.");

	Properties.IDENTIFIER.getValueType().addQualifier(
		new XmpPropertyImpl("http://ns.adobe.com/xmp/Identifier/qual/1.0/", "xmpidq", "xmpidq:Scheme", EXTERNAL,
			"The name of the formal identification system used in the value of the associated xmp:Identifier item").setValueType(TypeV.Text));

	Properties.LABEL.delegate.set("Label", INTERNAL, ArrayType.Bag, TypeV.Text,
		"An unordered array of text strings that unambiguously identify the resource within a given context. An array "
			+ "item may be qualified with xmpidq:Scheme to denote the formal identification system to which that identifier conforms.");

	Properties.METADATA_DATE.delegate.set("MetadataDate", INTERNAL, TypeV.Date,
		"The date and time that any metadata for this resource was last changed. It should be the same as or more recent than xmp:ModifyDate.");

	Properties.MODIFY_DATE.delegate.set("ModifyDate", INTERNAL, TypeV.Date, "The date and time the resource was last modified. "
		+ "NOTE:The value of this property is not necessarily the same as the file’s system "
		+ "modification date because it is set before the file is saved.");

	Properties.NICKNAME.delegate.set("Nickname", EXTERNAL, TypeV.Text, "A short informal name for the resource.");

	ValueType ratingChoice = new ClosedChoice<ValueTypes.Integer>(TypeV.Integer);
	Properties.RATING.delegate.set("Rating", EXTERNAL, ratingChoice,
		"A number that indicates a document’s status relative to other documents, used to organize documents in a file "
			+ "browser. Values are user-defined within an applicationdefined range.");

	Properties.THUMBNAILS.delegate.set("Thumbnails", INTERNAL, ArrayType.Alt, TypeV.Thumbnail,
		"An alternative array of thumbnail images for a file, which can differ in characteristics such as size or image encoding.");	
    }



    public XmpProperty[] properties() {
	return Properties.values();
    }

    public enum Properties implements XmpProperty {
	ADVISORY, BASEURL, CREATE_DATE, CREATOR_TOOL, IDENTIFIER, LABEL, METADATA_DATE, MODIFY_DATE, NICKNAME, RATING, THUMBNAILS;

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
