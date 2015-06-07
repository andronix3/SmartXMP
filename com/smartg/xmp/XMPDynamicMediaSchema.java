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
public class XMPDynamicMediaSchema extends XMPShema {

    public static final XMPDynamicMediaSchema SCHEMA = new XMPDynamicMediaSchema();

    public static final String NAME = "XMPDynamicMediaSchema";

    private XMPDynamicMediaSchema() {
	super("http://ns.adobe.com/xmp/1.0/DynamicMedia/", "xmpDM", NAME);
    }
    
    @Override
    protected void initProperties() {
	Properties.PROJECT_REF.delegate.set("projectRef", INTERNAL, TypeV.ProjectLink, "A reference to the project that created this file.");

	ValueType rateChoice = new ValueTypes.OpenChoice<ValueTypes.Text>(TypeV.Text).add("24").add("NTSC").add("PAL");
	Properties.VIDEO_FRAME_RATE.delegate.set("videoFrameRate", INTERNAL, rateChoice, "The video frame rate.");

	Properties.VIDEO_FRAME_SIZE.delegate.set("videoFrameSize", INTERNAL, TypeV.Dimensions, "The frame size");
	Properties.VIDEO_PIXEL_ASPECT_RATIO.delegate.set("videoPixelAspectRatio", INTERNAL, TypeV.Rational, "The aspect ratio, expressed as ht/wd");

	ValueType pixelDepthChoice = new ValueTypes.ClosedChoice<ValueTypes.Text>(TypeV.Text).add("8Int").add("16Int").add("32Int").add("32Float");
	Properties.VIDEO_PIXEL_DEPTH.delegate.set("videoPixelDepth", INTERNAL, pixelDepthChoice,
		"The size in bits of each color component of a pixel. Standard Windows 32-bit pixels have 8 bits per component.");

	ValueType colorSpaceChoice = new ValueTypes.ClosedChoice<ValueTypes.Text>(TypeV.Text).add("sRGB").add("CCIR-601").add("CCIR-709");
	Properties.VIDEO_COLOR_SPACE.delegate.set("videoColorSpace", INTERNAL, colorSpaceChoice, "The color space");

	ValueType alphaModeChoice = new ValueTypes.ClosedChoice<ValueTypes.Text>(TypeV.Text).add("straight").add("pre-multiplied");
	Properties.VIDEO_ALPHA_MODE.delegate.set("videoAlphaMode", EXTERNAL, alphaModeChoice, "The alpha mode. straight or pre-multiplied");

	Properties.VIDEO_ALPHA_PREMULTIPLE_COLOR.delegate.set("videoAlphaPremultipleColor", EXTERNAL, TypeV.Colorant,
		"A color in CMYK or RGB to be used as the pre-multiple color when alpha mode is pre-multiplied");

	Properties.VIDEO_ALPHA_UNITYIS_TRANSPARENT.delegate.set("videoAlphaUnityIsTransparent", INTERNAL, TypeV.Boolean,
		"When true, unity is clear, when false, it is opaque");

	Properties.VIDEO_COMPRESSOR.delegate.set("videoCompressor", INTERNAL, TypeV.Text, "Video compression used.");

	ValueType fieldOrderChoice = new ValueTypes.ClosedChoice<ValueTypes.Text>(TypeV.Text).add("Upper").add("Lower").add("Progressive");
	Properties.VIDEO_FIELD_ORDER.delegate.set("videoFieldOrder", INTERNAL, fieldOrderChoice, "The field order for video.");

	ValueType pullDownChoice = new ValueTypes.ClosedChoice<ValueTypes.Text>(TypeV.Text).add("WSSWW").add("SSWWW").add("SWWWS").add("WWWSS").add("WWSSW").add("WSSWW_24p")
		.add("SSWWW_24p").add("SWWWS_24p").add("WWWSS_24p").add("WWSSW_24p");
	Properties.PULL_DOWN.delegate.set("pullDown", EXTERNAL, pullDownChoice, "The sampling phase of film to be converted to video (pull-down)");

	Properties.AUDIO_SAMPLE_RATE.delegate.set("audioSampleRate", INTERNAL, TypeV.Text, "The audio sample rate. Common values are 32000, 41100 and 48000.");

	ValueType sampleTypeChoice = new ValueTypes.ClosedChoice<ValueTypes.Text>(TypeV.Text).add("8Int").add("16Int").add("32Int").add("32Float");
	Properties.AUDIO_SAMPLE_TYPE.delegate.set("audioSampleType", INTERNAL, sampleTypeChoice, "The audio sample type");

	ValueType channelTypeChoice = new ValueTypes.ClosedChoice<ValueTypes.Text>(TypeV.Text).add("Mono").add("Stereo").add("5.1").add("7.1");
	Properties.AUDIO_CHANNEL_TYPE.delegate.set("audioChannelType", INTERNAL, channelTypeChoice, "The audio channel type");

	Properties.AUDIO_COMPRESSOR.delegate.set("audioCompressor", INTERNAL, TypeV.Text, "Audio compression used.");
	Properties.SPEAKER_PLACEMENT.delegate.set("speakerPlacement", EXTERNAL, TypeV.Text, "A description of the speaker angles from center front in degrees.");
	Properties.FILE_DATA_RATE.delegate.set("fileDataRate", INTERNAL, TypeV.Rational, "The file data rate in megabytes per second.");
	Properties.TAPE_NAME.delegate.set("tapeName", EXTERNAL, TypeV.Text, "The name of the tape from which the clip was captured.");
	Properties.ALT_TAPE_NAME.delegate.set("altTapeName", EXTERNAL, TypeV.Text, "An alternative tape name.");
	Properties.START_TIMECODE.delegate.set("startTimecode", INTERNAL, TypeV.TimeCode, "The timecode of the first frame of video in the file");
	Properties.ALT_TIMECODE.delegate.set("altTimecode", INTERNAL, TypeV.TimeCode, "A timecode set by the user.");
	Properties.DURATION.delegate.set("duration", INTERNAL, TypeV.Time, "The duration of the media file.");
	Properties.SCENE.delegate.set("scene", EXTERNAL, TypeV.Text, "The name of the scene.");
	Properties.SHOT_NAME.delegate.set("shotName", EXTERNAL, TypeV.Text, "The name of the shot or take.");
	Properties.SHOT_DATE.delegate.set("shotDate", EXTERNAL, TypeV.Date, "The date and time when the video was shot.");
	Properties.SHOT_LOCATION.delegate.set("shotLocation", EXTERNAL, TypeV.Text, "The name of the location where the video was shot.");
	Properties.LOG_COMMENT.delegate.set("logComment", EXTERNAL, TypeV.Text, "User’s log comments.");
	Properties.MARKERS.delegate.set("markers", INTERNAL, ArrayType.Seq, TypeV.Marker, "An ordered list of markers.");
	Properties.CONTRIBUTED_MEDIA.delegate.set("contributedMedia", INTERNAL, ArrayType.Bag, TypeV.Media,
		"An unordered list of all media used to create this media.");
	Properties.ABS_PEAK_AUDIO_FILE_PATH.delegate.set("absPeakAudioFilePath", INTERNAL, TypeV.Uri,
		"The absolute path to the file’s peak audio file. If empty, no peak file exists.");
	Properties.VIDEO_MOD_DATE.delegate.set("videoModDate", INTERNAL, TypeV.Date, "The date and time when the video was last modified.");
	Properties.AUDIO_MOD_DATE.delegate.set("audioModDate", INTERNAL, TypeV.Date, "The date and time when the audio was last modified.");
	Properties.METADATA_MOD_DATE.delegate.set("metadataModDate", INTERNAL, TypeV.Date, "The date and time when the metadata was last modified.");
	Properties.ARTIST.delegate.set("artist", EXTERNAL, TypeV.Text, "The name of the artist or artists.");
	Properties.ALBUM.delegate.set("album", EXTERNAL, TypeV.Text, "The name of the album.");
	Properties.TRACK_NUMBER.delegate.set("trackNumber", EXTERNAL, TypeV.Integer,
		"A numeric value indicating the order of the audio file within its original recording.");
	Properties.GENRE.delegate.set("genre", EXTERNAL, TypeV.Text, "The name of the genre.");
	Properties.COPYRIGHT.delegate.set("copyright", EXTERNAL, TypeV.Text, "The copyright information.");
	Properties.RELEASE_DATE.delegate.set("releaseDate", EXTERNAL, TypeV.Date, "The date the title was released.");
	Properties.COMPOSER.delegate.set("composer", EXTERNAL, TypeV.Text, "The composer’s name.");
	Properties.ENGINEER.delegate.set("engineer", EXTERNAL, TypeV.Text, "The engineer’s name.");
	Properties.TEMPO.delegate.set("tempo", EXTERNAL, TypeV.Real, "The audio’s tempo.");
	Properties.INSTRUMENT.delegate.set("instrument", EXTERNAL, TypeV.Text, "The musical instrument.");
	Properties.INTRO_TIME.delegate.set("introTime", EXTERNAL, TypeV.Time, "The duration of lead time for queuing music.");
	Properties.OUT_CUE.delegate.set("outCue", EXTERNAL, TypeV.Time, "The time at which to fade out.");
	Properties.RELATIVE_TIMESTAMP.delegate.set("relativeTimestamp", INTERNAL, TypeV.Time, "The start time of the media inside the audio project.");
	Properties.LOOP.delegate.set("loop", INTERNAL, TypeV.Boolean, "When true, the clip can be looped seemlessly.");
	Properties.NUMBER_OF_BEATS.delegate.set("numberOfBeats", INTERNAL, TypeV.Real, "The number of beats.");

	Choice<ValueType> keyChoice = new ValueTypes.ClosedChoice<ValueTypes.Text>(TypeV.Text);
	keyChoice.add("C").add("C#").add("D").add("D#").add("E").add("F").add("F#").add("G").add("G#").add("A").add("A#").add("B");
	Properties.KEY.delegate.set("key", INTERNAL, keyChoice, "The audio’s musical key.");

	Choice<ValueType> stretchModeChoice = new ValueTypes.ClosedChoice<ValueTypes.Text>(TypeV.Text);
	stretchModeChoice.add("Fixed length").add("Time-Scale").add("Resample").add("Beat Splice").add("Hybrid");
	Properties.STRETCH_MODE.delegate.set("stretchMode", INTERNAL, stretchModeChoice, "The audio stretch mode.");

	Properties.TIME_SCALE_PARAMS.delegate.set("timeScaleParams", INTERNAL, TypeV.TimeScaleStretch, "Additional parameters for Time-Scale stretch mode.");
	Properties.RESAMPLE_PARAMS.delegate.set("resampleParams", INTERNAL, TypeV.ResampleStretch, "Additional parameters for Resample stretch mode.");
	Properties.BEAT_SPLICE_PARAMS.delegate.set("beatSpliceParams", INTERNAL, TypeV.BeatSpliceStretch, "Additional parameters for Beat Splice stretch mode.");

	Choice<ValueType> timeSignChoice = new ValueTypes.ClosedChoice<ValueTypes.Text>(TypeV.Text);
	timeSignChoice.add("2/4").add("3/4").add("4/4").add("5/4").add("7/4").add("6/8").add("9/8").add("12/8").add("other");
	Properties.TIME_SIGNATURE.delegate.set("timeSignature", INTERNAL, timeSignChoice, "The time signature of the music.");

	ValueType scaleTypeChoice = new ValueTypes.ClosedChoice<ValueTypes.Text>(TypeV.Text).add("Major").add("Minor,").add("Both").add("Neither");
	Properties.SCALE_TYPE.delegate.set("scaleType", INTERNAL, scaleTypeChoice, "The musical scale used in the music.");	
    }

    public XmpProperty[] properties() {
	return Properties.values();
    }

    public enum Properties implements XmpProperty {
	PROJECT_REF, VIDEO_FRAME_RATE, VIDEO_FRAME_SIZE, VIDEO_PIXEL_ASPECT_RATIO, VIDEO_PIXEL_DEPTH, VIDEO_COLOR_SPACE,

	VIDEO_ALPHA_MODE, VIDEO_ALPHA_PREMULTIPLE_COLOR, VIDEO_ALPHA_UNITYIS_TRANSPARENT, VIDEO_COMPRESSOR, VIDEO_FIELD_ORDER,

	PULL_DOWN, AUDIO_SAMPLE_RATE, AUDIO_SAMPLE_TYPE, AUDIO_CHANNEL_TYPE, AUDIO_COMPRESSOR, SPEAKER_PLACEMENT, FILE_DATA_RATE,

	TAPE_NAME, ALT_TAPE_NAME, START_TIMECODE, ALT_TIMECODE, DURATION, SCENE, SHOT_NAME, SHOT_DATE, SHOT_LOCATION, LOG_COMMENT,

	MARKERS, CONTRIBUTED_MEDIA, ABS_PEAK_AUDIO_FILE_PATH, VIDEO_MOD_DATE, AUDIO_MOD_DATE, METADATA_MOD_DATE, ARTIST, ALBUM,

	TRACK_NUMBER, GENRE, COPYRIGHT, RELEASE_DATE, COMPOSER, ENGINEER, TEMPO, INSTRUMENT, INTRO_TIME, OUT_CUE, RELATIVE_TIMESTAMP,

	LOOP, NUMBER_OF_BEATS, KEY, STRETCH_MODE, TIME_SCALE_PARAMS, RESAMPLE_PARAMS, BEAT_SPLICE_PARAMS, TIME_SIGNATURE, SCALE_TYPE;

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
