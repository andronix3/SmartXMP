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
public class MediaManagement extends XMPShema {

    public static final MediaManagement SHEMA = new MediaManagement();

    public static final String NAME = "MediaManagement";

    private MediaManagement() {
	super("http://ns.adobe.com/xap/1.0/mm/", "xmpMM", NAME);
    }

    @Override
    protected void initProperties() {
	Properties.DERIVED_FROM.delegate.set("DerivedFrom", INTERNAL, TypeV.ResourceRef, "A reference to the original document from which this one is derived. "
		+ "It is a minimal reference; missing components can be assumed to be unchanged. "
		+ "For example, a new version might only need to specify the instance ID and version number of the previous version, "
		+ "or a rendition might only need to specify the instance ID and rendition class of the original.");

	Properties.HISTORY.delegate.set("History", INTERNAL, ArrayType.Seq, TypeV.ResourceEvent, "An ordered array of high-level user actions"
		+ " that resulted in this resource. "
		+ "It is intended to give human readers a general indication of the steps taken to make the changes from the previous version to this one. "
		+ "The list should be at an abstract level; it is not intended to be an exhaustive keystroke or other detailed history.");

	Properties.DOCUMENT_ID.delegate.set("DocumentID", INTERNAL, TypeV.Uri,
		"The common identifier for all versions and renditions of a document. It should be based on a UUID;");

	Properties.INSTANCE_ID.delegate.set("InstanceID", INTERNAL, TypeV.Uri, "An identifier for a specific incarnation of a "
		+ "document, updated each time a file is saved. It should be based on a UUID; see Document and Instance IDs below.");

	Properties.MANAGED_FROM.delegate.set("ManagedFrom", INTERNAL, TypeV.ResourceRef, "A reference to the document as it was prior to becoming managed. "
		+ "It is set when a managed document is introduced to an asset management system that does not currently own it. "
		+ "It may or may not include references to different management systems.");

	Properties.MANAGER.delegate.set("Manager", INTERNAL, TypeV.AgentName, "The name of the asset management system that manages this resource. "
		+ "Along with xmpMM: ManagerVariant, it tells applications which asset management system to contact concerning this document.");

	Properties.MANAGE_TO.delegate.set("ManageTo", INTERNAL, TypeV.Uri, "A URI identifying the managed resource to the asset management system; "
		+ "the presence of this property is the formal indication that this resource is managed. "
		+ "The form and content of this URI is private to the asset management system.");

	Properties.MANAGE_URI.delegate
		.set("ManageURI", INTERNAL, TypeV.Uri, "A URI that can be used to access information about the managed resource through a web browser. "
			+ "It might require a custom browser plugin.");

	Properties.MANAGER_VARIANT.delegate.set("ManagerVariant", INTERNAL, TypeV.Text, "Specifies a particular variant of the asset management system. "
		+ "The format of this property is private to the specific asset management system.");

	Properties.RENDITION_CLASS.delegate.set("RenditionClass", INTERNAL, new ValueTypes.RenditionClass(null), "The rendition class name for this resource. "
		+ "This property should be absent or set to default for a document version that is not a derived rendition.");

	Properties.RENDITION_PARAMS.delegate.set("RenditionParams", INTERNAL, TypeV.Text,
		"Can be used to provide additional rendition parameters that are too complex or verbose to encode in xmpMM: RenditionClass.");

	Properties.VERSION_ID.delegate.set("VersionID", INTERNAL, TypeV.Text, "The document version identifier for this resource. "
		+ "Each version of a document gets a new identifier, usually simply by incrementing integers 1, 2, 3 . . . and so on. "
		+ "Media management systems can have other conventions or support branching which requires a more complex scheme.");

	Properties.VERSIONS.delegate.set("Versions", INTERNAL, ArrayType.Seq, TypeV.Version, "The version history associated with this resource. "
		+ "Entry [1] is the oldest known version for this document, entry [last()] is the most recent version. "
		+ "Typically, a media management system would fill in the version information in the metadata on check-in. "
		+ "It is not guaranteed that a complete history of versions from the first to this one will be present in the xmpMM:Versions property. "
		+ "Interior version information can be compressed or eliminated and the version history can be truncated at some point.");

	Properties.LAST_URL.delegate.set("LastURL", INTERNAL, TypeV.Url, "Deprecated for privacy protection.");

	Properties.RENDITION_OF.delegate.set("RenditionOf", INTERNAL, TypeV.ResourceRef,
		"Deprecated in favor of xmpMM:DerivedFrom. A reference to the document of which this is a rendition.");

	Properties.SAVE_ID.delegate.set("SaveID", INTERNAL, TypeV.Integer, "Deprecated. Previously used only to support the xmpMM:LastURL property.");	
    }



    public XmpProperty[] properties() {
	return Properties.values();
    }

    public enum Properties implements XmpProperty {
	DERIVED_FROM, HISTORY, DOCUMENT_ID, INSTANCE_ID, MANAGED_FROM, MANAGER, MANAGE_TO, MANAGE_URI, MANAGER_VARIANT, RENDITION_CLASS, RENDITION_PARAMS, VERSION_ID, VERSIONS, LAST_URL, RENDITION_OF, SAVE_ID;

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
