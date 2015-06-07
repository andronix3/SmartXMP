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

import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeModel;

import com.adobe.xmp.XMPException;
import com.adobe.xmp.XMPMeta;
import com.adobe.xmp.XMPMetaFactory;
import com.adobe.xmp.impl.XMPMetaImpl;
import com.adobe.xmp.impl.XMPNode;
import com.adobe.xmp.options.SerializeOptions;

public class XMP {

    public static enum Shema implements XMP_Shema {
	AUX(XMP_AuxShema.SHEMA), BASIC(XMPBasicShema.SHEMA), BASIC_JOB_TICKET(BasicJobTicket.SHEMA), CAMERA_RAW_SHEMA(CameraRawShema.SHEMA), DUBLIN_CORE(
		DublinCore.SHEMA), DYNAMIC_MEDIA(XMPDynamicMediaSchema.SCHEMA), EXIF(EXIFShema.SHEMA), IPTC_4_XMP_CORE(Iptc4XmpCore.SHEMA), MEDIA_MANAGEMENT(
		MediaManagement.SHEMA), PAGED_TEXT(PagedText.SHEMA), PDF(PDFShema.PDF_SHEMA), PHOTOSHOP(PhotoshopShema.SHEMA), RIGHTS_MANAGEMENT(
		RightsManagement.SHEMA), TIFF(TIFFShema.SHEMA);

	private Shema(XMP_Shema delegate) {
	    this.delegate = delegate;
	}

	private XMP_Shema delegate;

	public String getName() {
	    return delegate.getName();
	}

	public String getNamespace() {
	    return delegate.getNamespace();
	}

	public String getPrefix() {
	    return delegate.getPrefix();
	}

	public XmpProperty getProperty(String name) {
	    return delegate.getProperty(name);
	}

	public XmpProperty[] properties() {
	    return delegate.properties();
	}

	public Enumeration<String> propertyNames() {
	    return delegate.propertyNames();
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
    }

    public static XMPMeta toXMP(XMPCollection collection) {
	return (XMPMeta) collection.xmpMeta.clone();
    }

    /**
     * Convert given XMPMeta to String.
     * 
     * @param meta
     *            XMPMeta
     * @param compact
     *            if true then compact XMP form used
     * @param omitPacketWrapper
     *            if true then no packet generated
     * @return String
     * @throws XMPException
     */
    public static String toString(XMPMeta meta, boolean compact, boolean omitPacketWrapper) throws XMPException {
	int options = 0;
	if (compact) {
	    options |= SerializeOptions.USE_COMPACT_FORMAT;
	}
	if (omitPacketWrapper) {
	    options |= SerializeOptions.OMIT_PACKET_WRAPPER;
	}
	return toString(meta, options);
    }

    /**
     * Convert given XMPMeta to String using default options.
     * 
     * @param meta
     *            XMPMeta
     * @return String
     * @throws XMPException
     */
    public static String toString(XMPMeta meta) throws XMPException {
	return toString(meta, 0);
    }

    /**
     * Convert given XMPMeta to String.
     * 
     * @param meta
     *            XMPMeta
     * @param options
     * @return String
     * @see SerializeOptions
     * @throws XMPException
     */
    public static String toString(XMPMeta meta, int options) throws XMPException {
	return XMPMetaFactory.serializeToString(meta, new SerializeOptions(options));
    }

    /**
     * create TreeModel from XMP
     * 
     * @param meta
     *            XMPMeta
     * @return
     */
    public static TreeModel createTreeModel(XMPMeta meta) {
	if (meta instanceof XMPMetaImpl) {
	    XMPNode root = ((XMPMetaImpl) meta).getRoot();
	    return new DefaultTreeModel(new XMP_Node(root));
	}
	throw new IllegalArgumentException("XMPMetaImpl object expected");
    }
}
