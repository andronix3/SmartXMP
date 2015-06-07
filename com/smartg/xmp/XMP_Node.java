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

import java.util.Arrays;
import java.util.Enumeration;

import javax.swing.tree.TreeNode;

import com.adobe.xmp.impl.XMPNode;
import com.smartg.java.util.SafeIterator;

class XMP_Node implements TreeNode {

    XMPNode node;
    XMP_Node parent;
    int index;

    XMP_Node[] children;

    public XMP_Node(XMPNode node) {
	this.node = node;
	children = new XMP_Node[node.getChildrenLength()];
	for (int i = 0; i < children.length; i++) {
	    children[i] = new XMP_Node(node.getChild(i + 1));
	    children[i].parent = this;
	    children[i].index = i;
	}
    }

    @Override
    public String toString() {
	XMPNode p = node.getParent();
	if (p == null) {
	    return "ROOT NODE";
	}
	if (p.getOptions().isArray()) {
	    String name = getChild("name");
	    if (name == null || name == "") {
		name = getChild("type");
	    }
	    // if (name == null || name == "") {
	    String value = node.getValue();
	    if (value != null && !value.trim().isEmpty()) {
		name += " = " + value;
	    }

	    // }
	    return "[" + (index + 1) + "] " + name;
	}
	if (node.getOptions().isArray()) {
	    if (node.getOptions().isArrayOrdered()) {
		return getShortName(node) + ":Seq";
	    }
	    if (node.getOptions().isArrayAlternate()) {
		return getShortName(node) + ":Alt";
	    }
	    if (node.getOptions().isArrayAltText()) {
		return getShortName(node) + ":Alt";
	    }
	    return getShortName(node) + ":Bag";
	}
	String name = getShortName(node);
	if (name.length() > 0) {
	    return name + " = " + node.getValue();
	}
	return node.getValue();
    }

    private String getChild(String name) {
	for (int i = 0; i < children.length; i++) {
	    XMP_Node child = children[i];
	    if (name.equals(getShortName(child.node))) {
		return child.node.getValue();
	    }
	}
	return "";
    }

    String getShortName(XMPNode node) {
	String s = node.getName();
	if (s.startsWith("http://")) {
	    return "";
	}
	return s.substring(s.indexOf(':') + 1);
    }

    public Enumeration<XMP_Node> children() {
	return new SafeIterator<XMP_Node>(Arrays.asList(children).iterator());
    }

    public boolean getAllowsChildren() {
	return true;
    }

    public TreeNode getChildAt(int childIndex) {
	return children[childIndex];
    }

    public int getChildCount() {
	return children.length;
    }

    public int getIndex(TreeNode node) {
	for (int i = 0; i < children.length; i++) {
	    if (children[i].equals(node)) {
		return i;
	    }
	}
	return -1;
    }

    public TreeNode getParent() {
	return parent;
    }

    public boolean isLeaf() {
	return getChildCount() == 0;
    }
}