package com.wsd.android;

import android.content.SharedPreferences;
import android.util.Log;

/**
 * Created by jeff on 17-5-28.
 */

public class AudioCardSettings {
    private final String TAG = "Audio Card Settings";
    private AudioCardProfile mProfile;
    private WsdAudioCardCtrl mDspCtrl;

    public AudioCardSettings(SharedPreferences sp)
    {
        mDspCtrl = new WsdAudioCardCtrl();
        mProfile = new AudioCardProfile(sp);
        mDspCtrl.audioCardInit("/dev/ttyUSB1");
    }

    public int saveToDspSlot(int slotIdx)
    {
        if (slotIdx<WsdAudioCardCtrl.SLOT_1 || slotIdx>WsdAudioCardCtrl.SLOT_6)
        {
            Log.e(TAG,"slotIdx illegal");
            return -1;
        }

        if(mDspCtrl.parameterSave(slotIdx)!=0)
        {
            Log.e(TAG,"parameterSave fail");
            return -1;
        }
        return 0;
    }

    public int loadFromDspSlot(int slotIdx)
    {
        if (slotIdx<WsdAudioCardCtrl.SLOT_1 || slotIdx>WsdAudioCardCtrl.SLOT_6)
        {
            Log.e(TAG,"slotIdx illegal");
            return -1;
        }

        if (mDspCtrl.parameterLoad(slotIdx)!=0)
        {
            Log.e(TAG,"parameterLoad fail");
            return -1;
        }

        try {
        Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        syncWithDsp();
        return 0;
    }

    public int set(WsdAudioCardCtrl.SettingsName enumName,int value)
    {
        if (enumName==WsdAudioCardCtrl.SettingsName.END) {
            Log.e(TAG,"enumName END illegal");
            return -1;
        }

        mProfile.set(enumName,value);
        mDspCtrl.setValueToDsp(enumName,value);
        return 0;
    }

    public int set(int idx,int value)
    {
        if (idx>=WsdAudioCardCtrl.SettingsName.END.ordinal() || idx<0)
        {
            Log.e(TAG,"idx illegal");
            return -1;
        }

        mProfile.set(idx,value);
        mDspCtrl.setValueToDsp(WsdAudioCardCtrl.SettingsName.values()[idx],value);
        return 0;
    }

    public int get(WsdAudioCardCtrl.SettingsName enumName)
    {
        return mProfile.get(enumName);
    }

    public int get(int idx)
    {
        if (idx>=WsdAudioCardCtrl.SettingsName.END.ordinal() || idx<0)
        {
            Log.e(TAG,"idx illegal");
            return -1;
        }

        return mProfile.get(idx);
    }

    public AudioCardProfile getProfile()
    {
        return mProfile;
    }

    public void setToDsp(AudioCardProfile source)
    {
        int a,b,c,d,e,f;
        int a1,b1,c1,d1,e1,f1;

        //mic
        a = mProfile.get(WsdAudioCardCtrl.SettingsName.MicInputL.ordinal());
        b = mProfile.get(WsdAudioCardCtrl.SettingsName.MicInputR.ordinal());
        a1 = source.get(WsdAudioCardCtrl.SettingsName.MicInputL.ordinal());
        b1 = source.get(WsdAudioCardCtrl.SettingsName.MicInputR.ordinal());
        if (a!=a1 || b!=b1) mDspCtrl.micLRLevelSetup(a, b);

        a = mProfile.get(WsdAudioCardCtrl.SettingsName.MicInputGain.ordinal());
        a1 = source.get(WsdAudioCardCtrl.SettingsName.MicInputGain.ordinal());
        if (a!=a1) mDspCtrl.micInputLevelSetup(a);

        a = mProfile.get(WsdAudioCardCtrl.SettingsName.MicInputAux.ordinal());
        b = mProfile.get(WsdAudioCardCtrl.SettingsName.MusicInputAux.ordinal());
        a1 = source.get(WsdAudioCardCtrl.SettingsName.MicInputAux.ordinal());
        b1 = source.get(WsdAudioCardCtrl.SettingsName.MusicInputAux.ordinal());
        if (a!=a1 || b!=b1) mDspCtrl.auxLevelSetup(a, b);

        a = mProfile.get(WsdAudioCardCtrl.SettingsName.BassCutSwitch.ordinal());
        b = mProfile.get(WsdAudioCardCtrl.SettingsName.BassCutFrequency.ordinal());
        a1 = source.get(WsdAudioCardCtrl.SettingsName.BassCutSwitch.ordinal());
        b1 = source.get(WsdAudioCardCtrl.SettingsName.BassCutFrequency.ordinal());
        if (a!=a1) mDspCtrl.micBasscutEnable(a);
        if (b!=b1) mDspCtrl.micBasscutFrequencySetup(b + 1);

        a = mProfile.get(WsdAudioCardCtrl.SettingsName.NoiseSuppSwitch.ordinal());
        b = mProfile.get(WsdAudioCardCtrl.SettingsName.NoiseSuppThreshold.ordinal());
        a1 = source.get(WsdAudioCardCtrl.SettingsName.NoiseSuppSwitch.ordinal());
        b1 = source.get(WsdAudioCardCtrl.SettingsName.NoiseSuppThreshold.ordinal());
        if (a!=a1) mDspCtrl.micNoiseSuppEnable(a);
        if (b!=b1) mDspCtrl.micNoiseThresholdSetup(b);

        a = mProfile.get(WsdAudioCardCtrl.SettingsName.CompressorSwitch.ordinal());
        b = mProfile.get(WsdAudioCardCtrl.SettingsName.CompressorSustain.ordinal());
        c = mProfile.get(WsdAudioCardCtrl.SettingsName.CompressorOutLevel.ordinal());
        a1 = source.get(WsdAudioCardCtrl.SettingsName.CompressorSwitch.ordinal());
        b1 = source.get(WsdAudioCardCtrl.SettingsName.CompressorSustain.ordinal());
        c1 = source.get(WsdAudioCardCtrl.SettingsName.CompressorOutLevel.ordinal());
        if (a!=a1) mDspCtrl.micCompressorEnable(a);
        if (c!=c1 || b!=b1) mDspCtrl.micCompressorSetup(b,c);

        a = mProfile.get(WsdAudioCardCtrl.SettingsName.FrequencyShift.ordinal());
        a1 = source.get(WsdAudioCardCtrl.SettingsName.FrequencyShift.ordinal());
        if (a!=a1) mDspCtrl.micFrequencyShiftSetup(a-10);

        a = mProfile.get(WsdAudioCardCtrl.SettingsName.MicInputPhaseReverse.ordinal());
        a1 = source.get(WsdAudioCardCtrl.SettingsName.MicInputPhaseReverse.ordinal());
        if (a!=a1) mDspCtrl.micDryOutputSetup(0xff,a);

        a = mProfile.get(WsdAudioCardCtrl.SettingsName.MicPeqSwitch.ordinal());
        a1 = source.get(WsdAudioCardCtrl.SettingsName.MicPeqSwitch.ordinal());
        if (a!=a1) mDspCtrl.mic7bandPeqEnable(a);

        a = mProfile.get(WsdAudioCardCtrl.SettingsName.MicPeqBand1Type.ordinal());
        a1 = source.get(WsdAudioCardCtrl.SettingsName.MicPeqBand1Type.ordinal());
        b = mProfile.get(WsdAudioCardCtrl.SettingsName.MicPeqBand7Type.ordinal());
        b1 = source.get(WsdAudioCardCtrl.SettingsName.MicPeqBand7Type.ordinal());
        if (a!=a1||b!=b1) mDspCtrl.mic7bandPeqEdgeSetup(a,b);

        for (int i=0;i<7;i++) {
            a = mProfile.get(WsdAudioCardCtrl.SettingsName.MicPeqBand1Freq.ordinal()+i);
            b = mProfile.get(WsdAudioCardCtrl.SettingsName.MicPeqBand1Q.ordinal()+i);
            c = mProfile.get(WsdAudioCardCtrl.SettingsName.MicPeqBand1Gain.ordinal()+i);
            a1 = source.get(WsdAudioCardCtrl.SettingsName.MicPeqBand1Freq.ordinal()+i);
            b1 = source.get(WsdAudioCardCtrl.SettingsName.MicPeqBand1Q.ordinal()+i);
            c1 = source.get(WsdAudioCardCtrl.SettingsName.MicPeqBand1Gain.ordinal()+i);
            if (c!=c1 || b!=b1 || a!=a1) mDspCtrl.mic7bandPeqSetup(i+1,a+2,b,c-60);
        }

        //music
        a = mProfile.get(WsdAudioCardCtrl.SettingsName.MusicInputGain.ordinal());
        a1 = source.get(WsdAudioCardCtrl.SettingsName.MusicInputGain.ordinal());
        if (a!=a1) mDspCtrl.musicInputLevelSetup(a);

        a = mProfile.get(WsdAudioCardCtrl.SettingsName.KeyControlSwitch.ordinal());
        b = mProfile.get(WsdAudioCardCtrl.SettingsName.KeyControlPitch.ordinal());
        c = mProfile.get(WsdAudioCardCtrl.SettingsName.KeyControlPitchFine.ordinal());
        a1 = source.get(WsdAudioCardCtrl.SettingsName.KeyControlSwitch.ordinal());
        b1 = source.get(WsdAudioCardCtrl.SettingsName.KeyControlPitch.ordinal());
        c1 = source.get(WsdAudioCardCtrl.SettingsName.KeyControlPitchFine.ordinal());
        if (a!=a1) mDspCtrl.musicKeyctrlEnable(a);
        if (c!=c1 || b!=b1) mDspCtrl.musicKeyctrlSetup(b-12,c-50);

        a = mProfile.get(WsdAudioCardCtrl.SettingsName.MusicInputPhaseReverse.ordinal());
        a1 = source.get(WsdAudioCardCtrl.SettingsName.MusicInputPhaseReverse.ordinal());
        if (a!=a1) mDspCtrl.musicPhaseSetup(a);

        a = mProfile.get(WsdAudioCardCtrl.SettingsName.MusicGeqSwitch.ordinal());
        a1 = source.get(WsdAudioCardCtrl.SettingsName.MusicGeqSwitch.ordinal());
        if (a!=a1) mDspCtrl.music31bandGeqEnable(a);

        for (int i=0;i<31;i++) {
            a = mProfile.get(WsdAudioCardCtrl.SettingsName.MusicGeqBand1Gain.ordinal()+i);
            a1 = source.get(WsdAudioCardCtrl.SettingsName.MusicGeqBand1Gain.ordinal()+i);
            if (a!=a1) mDspCtrl.music31bandGeqSetup(i+1,a-60);
        }

        //echo
        a = mProfile.get(WsdAudioCardCtrl.SettingsName.EchoInput.ordinal());
        a1 = source.get(WsdAudioCardCtrl.SettingsName.EchoInput.ordinal());
        if (a!=a1) mDspCtrl.echoInputLevelSetup(a);

        a = mProfile.get(WsdAudioCardCtrl.SettingsName.EchoOutput.ordinal());
        b = mProfile.get(WsdAudioCardCtrl.SettingsName.EchoPhaseReverse.ordinal());
        a1 = source.get(WsdAudioCardCtrl.SettingsName.EchoOutput.ordinal());
        b1 = source.get(WsdAudioCardCtrl.SettingsName.EchoPhaseReverse.ordinal());
        if (a!=a1 || b!=b1) mDspCtrl.echoOutputSetup(a,b);

        a = mProfile.get(WsdAudioCardCtrl.SettingsName.EchoTime.ordinal());
        b = mProfile.get(WsdAudioCardCtrl.SettingsName.EchoLevel.ordinal());
        c = mProfile.get(WsdAudioCardCtrl.SettingsName.EchoFeedbackLevel.ordinal());
        d = mProfile.get(WsdAudioCardCtrl.SettingsName.EchoTone.ordinal());
        e = mProfile.get(WsdAudioCardCtrl.SettingsName.EchoPan.ordinal());
        f = mProfile.get(WsdAudioCardCtrl.SettingsName.EchoPreDelay.ordinal());
        a1 = source.get(WsdAudioCardCtrl.SettingsName.EchoTime.ordinal());
        b1 = source.get(WsdAudioCardCtrl.SettingsName.EchoLevel.ordinal());
        c1 = source.get(WsdAudioCardCtrl.SettingsName.EchoFeedbackLevel.ordinal());
        d1 = source.get(WsdAudioCardCtrl.SettingsName.EchoTone.ordinal());
        e1 = source.get(WsdAudioCardCtrl.SettingsName.EchoPan.ordinal());
        f1 = source.get(WsdAudioCardCtrl.SettingsName.EchoPreDelay.ordinal());
        if (a!=a1||b!=b1||c!=c1||d!=d1||e!=e1||f!=f1) mDspCtrl.echoEffectsSetup(a,b,e,d+57,c,f);

        for (int i=0;i<3;i++) {
            a = mProfile.get(WsdAudioCardCtrl.SettingsName.EchoTap1Time.ordinal()+i);
            b = mProfile.get(WsdAudioCardCtrl.SettingsName.EchoTap1Level.ordinal()+i);
            c = mProfile.get(WsdAudioCardCtrl.SettingsName.EchoTap1Pan.ordinal()+i);
            a1 = source.get(WsdAudioCardCtrl.SettingsName.EchoTap1Time.ordinal()+i);
            b1 = source.get(WsdAudioCardCtrl.SettingsName.EchoTap1Level.ordinal()+i);
            c1 = source.get(WsdAudioCardCtrl.SettingsName.EchoTap1Pan.ordinal()+i);
            if (a!=a1||b!=b1||c!=c1) mDspCtrl.echoTapEffectsSetup(i+1,a,b,c);
        }

        a = mProfile.get(WsdAudioCardCtrl.SettingsName.EchoPeqSwitch.ordinal());
        a1 = source.get(WsdAudioCardCtrl.SettingsName.EchoPeqSwitch.ordinal());
        if (a!=a1) mDspCtrl.echo5bandPeqEnable(a);

        a = mProfile.get(WsdAudioCardCtrl.SettingsName.EchoPeqBand1Type.ordinal());
        a1 = source.get(WsdAudioCardCtrl.SettingsName.EchoPeqBand1Type.ordinal());
        b = mProfile.get(WsdAudioCardCtrl.SettingsName.EchoPeqBand5Type.ordinal());
        b1 = source.get(WsdAudioCardCtrl.SettingsName.EchoPeqBand5Type.ordinal());
        if (a!=a1||b!=b1) mDspCtrl.echo5bandPeqEdgeSetup(a,b);

        for (int i=0;i<5;i++) {
            a = mProfile.get(WsdAudioCardCtrl.SettingsName.EchoPeqBand1Freq.ordinal()+i);
            b = mProfile.get(WsdAudioCardCtrl.SettingsName.EchoPeqBand1Q.ordinal()+i);
            c = mProfile.get(WsdAudioCardCtrl.SettingsName.EchoPeqBand1Gain.ordinal()+i);
            a1 = source.get(WsdAudioCardCtrl.SettingsName.EchoPeqBand1Freq.ordinal()+i);
            b1 = source.get(WsdAudioCardCtrl.SettingsName.EchoPeqBand1Q.ordinal()+i);
            c1 = source.get(WsdAudioCardCtrl.SettingsName.EchoPeqBand1Gain.ordinal()+i);
            if (a!=a1||b!=b1||c!=c1) mDspCtrl.echo5bandPeqSetup(i+1,a+2,b,c-60);
        }

        a = mProfile.get(WsdAudioCardCtrl.SettingsName.EchoHighpassFreq.ordinal());
        b = mProfile.get(WsdAudioCardCtrl.SettingsName.EchoHighpassType.ordinal());
        c = mProfile.get(WsdAudioCardCtrl.SettingsName.EchoLowpassFreq.ordinal());
        d = mProfile.get(WsdAudioCardCtrl.SettingsName.EchoLowpassType.ordinal());
        a1 = source.get(WsdAudioCardCtrl.SettingsName.EchoHighpassFreq.ordinal());
        b1 = source.get(WsdAudioCardCtrl.SettingsName.EchoHighpassType.ordinal());
        c1 = source.get(WsdAudioCardCtrl.SettingsName.EchoLowpassFreq.ordinal());
        d1 = source.get(WsdAudioCardCtrl.SettingsName.EchoLowpassType.ordinal());
        if (a!=a1||b!=b1||c!=c1||d!=d1) mDspCtrl.echoBandpassfilterSetup(b,a,d,c);

        //reverb
        a = mProfile.get(WsdAudioCardCtrl.SettingsName.ReverbInput.ordinal());
        a1 = source.get(WsdAudioCardCtrl.SettingsName.ReverbInput.ordinal());
        if (a!=a1) mDspCtrl.reverbInputLevelSetup(a);

        a = mProfile.get(WsdAudioCardCtrl.SettingsName.ReverbOutput.ordinal());
        b = mProfile.get(WsdAudioCardCtrl.SettingsName.ReverbPhaseReverse.ordinal());
        a1 = source.get(WsdAudioCardCtrl.SettingsName.ReverbOutput.ordinal());
        b1 = source.get(WsdAudioCardCtrl.SettingsName.ReverbPhaseReverse.ordinal());
        if (a!=a1||b!=b1) mDspCtrl.reverbOutputSetup(a,b);

        a = mProfile.get(WsdAudioCardCtrl.SettingsName.ReverbTime.ordinal());
        b = mProfile.get(WsdAudioCardCtrl.SettingsName.ReverbType.ordinal());
        c = mProfile.get(WsdAudioCardCtrl.SettingsName.ReverbTone.ordinal());
        d = mProfile.get(WsdAudioCardCtrl.SettingsName.ReverbPreDelay.ordinal());
        a1 = source.get(WsdAudioCardCtrl.SettingsName.ReverbTime.ordinal());
        b1 = source.get(WsdAudioCardCtrl.SettingsName.ReverbType.ordinal());
        c1 = source.get(WsdAudioCardCtrl.SettingsName.ReverbTone.ordinal());
        d1 = source.get(WsdAudioCardCtrl.SettingsName.ReverbPreDelay.ordinal());
        if (a!=a1||b!=b1||c!=c1||d!=d1) mDspCtrl.reverbEffectsSetup(b,a,c+57,d);

        a = mProfile.get(WsdAudioCardCtrl.SettingsName.EchoToReverb.ordinal());
        a1 = source.get(WsdAudioCardCtrl.SettingsName.EchoToReverb.ordinal());
        if (a!=a1) mDspCtrl.echoToReverbLevelSetup(a);

        a = mProfile.get(WsdAudioCardCtrl.SettingsName.ReverbPeqSwitch.ordinal());
        a1 = source.get(WsdAudioCardCtrl.SettingsName.ReverbPeqSwitch.ordinal());
        if (a!=a1) mDspCtrl.reverb5bandPeqEnable(a);

        a = mProfile.get(WsdAudioCardCtrl.SettingsName.ReverbPeqBand1Type.ordinal());
        a1 = source.get(WsdAudioCardCtrl.SettingsName.ReverbPeqBand1Type.ordinal());
        b = mProfile.get(WsdAudioCardCtrl.SettingsName.ReverbPeqBand5Type.ordinal());
        b1 = source.get(WsdAudioCardCtrl.SettingsName.ReverbPeqBand5Type.ordinal());
        if (a!=a1||b!=b1) mDspCtrl.reverb5bandPeqEdgeSetup(a,b);

        for (int i=0;i<5;i++) {
            a = mProfile.get(WsdAudioCardCtrl.SettingsName.ReverbPeqBand1Freq.ordinal()+i);
            b = mProfile.get(WsdAudioCardCtrl.SettingsName.ReverbPeqBand1Q.ordinal()+i);
            c = mProfile.get(WsdAudioCardCtrl.SettingsName.ReverbPeqBand1Gain.ordinal()+i);
            a1 = source.get(WsdAudioCardCtrl.SettingsName.ReverbPeqBand1Freq.ordinal()+i);
            b1 = source.get(WsdAudioCardCtrl.SettingsName.ReverbPeqBand1Q.ordinal()+i);
            c1 = source.get(WsdAudioCardCtrl.SettingsName.ReverbPeqBand1Gain.ordinal()+i);
            if (a!=a1||b!=b1||c!=c1) mDspCtrl.reverb5bandPeqSetup(i+1,a+2,b,c-60);
        }

        a = mProfile.get(WsdAudioCardCtrl.SettingsName.ReverbHighpassFreq.ordinal());
        b = mProfile.get(WsdAudioCardCtrl.SettingsName.ReverbHighpassType.ordinal());
        c = mProfile.get(WsdAudioCardCtrl.SettingsName.ReverbLowpassFreq.ordinal());
        d = mProfile.get(WsdAudioCardCtrl.SettingsName.ReverbLowpassType.ordinal());
        a1 = source.get(WsdAudioCardCtrl.SettingsName.ReverbHighpassFreq.ordinal());
        b1 = source.get(WsdAudioCardCtrl.SettingsName.ReverbHighpassType.ordinal());
        c1 = source.get(WsdAudioCardCtrl.SettingsName.ReverbLowpassFreq.ordinal());
        d1 = source.get(WsdAudioCardCtrl.SettingsName.ReverbLowpassType.ordinal());
        if (a!=a1||b!=b1||c!=c1||d!=d1) mDspCtrl.reverbBandpassfilterSetup(b,a,d,c);

        //output
        final int itemPerChannel= WsdAudioCardCtrl.SettingsName.RearOutputPeqBand5Gain.ordinal() - WsdAudioCardCtrl.SettingsName.RearOutputDirect.ordinal() + 1;
        for (int i=0;i<4;i++) {
            a = mProfile.get(WsdAudioCardCtrl.SettingsName.RearOutputDirect.ordinal() + i*itemPerChannel);
            b = mProfile.get(WsdAudioCardCtrl.SettingsName.RearOutputEcho.ordinal() + i*itemPerChannel);
            c = mProfile.get(WsdAudioCardCtrl.SettingsName.RearOutputReverb.ordinal() + i*itemPerChannel);
            d = mProfile.get(WsdAudioCardCtrl.SettingsName.RearOutputMusic.ordinal() + i*itemPerChannel);
            a1 = source.get(WsdAudioCardCtrl.SettingsName.RearOutputDirect.ordinal() + i*itemPerChannel);
            b1 = source.get(WsdAudioCardCtrl.SettingsName.RearOutputEcho.ordinal() + i*itemPerChannel);
            c1 = source.get(WsdAudioCardCtrl.SettingsName.RearOutputReverb.ordinal() + i*itemPerChannel);
            d1 = source.get(WsdAudioCardCtrl.SettingsName.RearOutputMusic.ordinal() + i*itemPerChannel);
            if (a!=a1||b!=b1||c!=c1||d!=d1) mDspCtrl.mixerMixSetup(i+1,a,b,c,d);

            a = mProfile.get(WsdAudioCardCtrl.SettingsName.RearOutputGain.ordinal() + i*itemPerChannel);
            b = mProfile.get(WsdAudioCardCtrl.SettingsName.RearOutputDelay.ordinal() + i*itemPerChannel);
            c = mProfile.get(WsdAudioCardCtrl.SettingsName.RearOutputPhaseReverse.ordinal() + i*itemPerChannel);
            a1 = source.get(WsdAudioCardCtrl.SettingsName.RearOutputGain.ordinal() + i*itemPerChannel);
            b1 = source.get(WsdAudioCardCtrl.SettingsName.RearOutputDelay.ordinal() + i*itemPerChannel);
            c1 = source.get(WsdAudioCardCtrl.SettingsName.RearOutputPhaseReverse.ordinal() + i*itemPerChannel);
            if (a!=a1||b!=b1||c!=c1) mDspCtrl.outputSetup(i+1,a,b,c);

            a = mProfile.get(WsdAudioCardCtrl.SettingsName.RearOutputPeqSwitch.ordinal() + i*itemPerChannel);
            a1 = source.get(WsdAudioCardCtrl.SettingsName.RearOutputPeqSwitch.ordinal() + i*itemPerChannel);
            if (a!=a1) mDspCtrl.output5bandPeqEnable(i+1,a);

            a = mProfile.get(WsdAudioCardCtrl.SettingsName.RearOutputPeqBand1Type.ordinal() + i*itemPerChannel);
            a1 = source.get(WsdAudioCardCtrl.SettingsName.RearOutputPeqBand1Type.ordinal() + i*itemPerChannel);
            b = mProfile.get(WsdAudioCardCtrl.SettingsName.RearOutputPeqBand5Type.ordinal() + i*itemPerChannel);
            b1 = source.get(WsdAudioCardCtrl.SettingsName.RearOutputPeqBand5Type.ordinal() + i*itemPerChannel);
            if(a!=a1||b!=b1) mDspCtrl.output5bandPeqEdgeSetup(i+1,a,b);

            for (int j=0;j<5;j++) {
                a = mProfile.get(WsdAudioCardCtrl.SettingsName.RearOutputPeqBand1Freq.ordinal() + i*itemPerChannel +j);
                b = mProfile.get(WsdAudioCardCtrl.SettingsName.RearOutputPeqBand1Q.ordinal() + i*itemPerChannel +j);
                c = mProfile.get(WsdAudioCardCtrl.SettingsName.RearOutputPeqBand1Gain.ordinal() + i*itemPerChannel +j);
                a1 = source.get(WsdAudioCardCtrl.SettingsName.RearOutputPeqBand1Freq.ordinal() + i*itemPerChannel +j);
                b1 = source.get(WsdAudioCardCtrl.SettingsName.RearOutputPeqBand1Q.ordinal() + i*itemPerChannel +j);
                c1 = source.get(WsdAudioCardCtrl.SettingsName.RearOutputPeqBand1Gain.ordinal() + i*itemPerChannel +j);
                if (a!=a1||b!=b1||c!=c1) mDspCtrl.output5bandPeqSetup(i + 1, j + 1, a + 2, b, c - 60);
            }

            a = mProfile.get(WsdAudioCardCtrl.SettingsName.RearOutputHighpassFreq.ordinal() + i*itemPerChannel);
            b = mProfile.get(WsdAudioCardCtrl.SettingsName.RearOutputHighpassType.ordinal() + i*itemPerChannel);
            c = mProfile.get(WsdAudioCardCtrl.SettingsName.RearOutputLowpassFreq.ordinal() + i*itemPerChannel);
            d = mProfile.get(WsdAudioCardCtrl.SettingsName.RearOutputLowpassType.ordinal() + i*itemPerChannel);
            a1 = source.get(WsdAudioCardCtrl.SettingsName.RearOutputHighpassFreq.ordinal() + i*itemPerChannel);
            b1 = source.get(WsdAudioCardCtrl.SettingsName.RearOutputHighpassType.ordinal() + i*itemPerChannel);
            c1 = source.get(WsdAudioCardCtrl.SettingsName.RearOutputLowpassFreq.ordinal() + i*itemPerChannel);
            d1 = source.get(WsdAudioCardCtrl.SettingsName.RearOutputLowpassType.ordinal() + i*itemPerChannel);
            if (a!=a1||b!=b1||c!=c1||d!=d1) mDspCtrl.outputBandpassfilterSetup(i+1, b,a,d,c);

        }

        a = mProfile.get(WsdAudioCardCtrl.SettingsName.OutputCompressorSwitch.ordinal());
        a1 = source.get(WsdAudioCardCtrl.SettingsName.OutputCompressorSwitch.ordinal());
        if (a!=a1)mDspCtrl.outputSubwoofferCompressorEnable(a);

        a = mProfile.get(WsdAudioCardCtrl.SettingsName.OutputCompressorAttack.ordinal());
        b = mProfile.get(WsdAudioCardCtrl.SettingsName.OutputCompressorRelease.ordinal());
        c = mProfile.get(WsdAudioCardCtrl.SettingsName.OutputCompressorThreshold.ordinal());
        d = mProfile.get(WsdAudioCardCtrl.SettingsName.OutputCompressorRatio.ordinal());
        e = mProfile.get(WsdAudioCardCtrl.SettingsName.OutputCompressorPostgain.ordinal());
        a1 = source.get(WsdAudioCardCtrl.SettingsName.OutputCompressorAttack.ordinal());
        b1 = source.get(WsdAudioCardCtrl.SettingsName.OutputCompressorRelease.ordinal());
        c1 = source.get(WsdAudioCardCtrl.SettingsName.OutputCompressorThreshold.ordinal());
        d1 = source.get(WsdAudioCardCtrl.SettingsName.OutputCompressorRatio.ordinal());
        e1 = source.get(WsdAudioCardCtrl.SettingsName.OutputCompressorPostgain.ordinal());
        if (a!=a1||b!=b1||c!=c1||d!=d1||e!=e1) mDspCtrl.outputSubwoofferCompressorSetup(a,b,c,d,e);

        mProfile = source;
        return;
    }

    public void syncWithDsp()
    {
        for (WsdAudioCardCtrl.SettingsName set : WsdAudioCardCtrl.SettingsName.values()) {
            if (set.ordinal()!=WsdAudioCardCtrl.SettingsName.END.ordinal()) {
                int tmp = mDspCtrl.getValueFromDsp(set);
                if (tmp!=255) {
                    mProfile.set(set,tmp);
                    Log.e(TAG,"[from DSP]"+  set.name() + "=" +  mProfile.get(set));
                } else {
                    Log.e(TAG,"[from DSP] could not get value of "+  set.name());
                }
            }
        }
    }

    public void resetToDefault()
    {
        mDspCtrl.cardReset();
        mProfile.reset();
    }
}
