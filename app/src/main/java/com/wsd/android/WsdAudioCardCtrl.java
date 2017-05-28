/**
 Settings native interface
 **/

package com.wsd.android;

public class WsdAudioCardCtrl {
    static {
        System.loadLibrary("wsdaudiocardctrl_jni");
    }

    public static native int audioCardInit();
    public static native int audioCardDeinit();

    public static native int micInputLevelSetup(int level);
    public static native int micLRLevelSetup(int L_level,int R_level);
    public static native int auxLevelSetup(int mic_level,int music_level);

    public static native int micBasscutEnable(int enable);
    public static native int micBasscutFrequencySetup(int frenquency);

    public static native int micNoiseSuppEnable(int enable);
    public static native int micNoiseThresholdSetup(int threshold);

    public static native int micCompressorEnable(int enable);
    public static native int micCompressorSetup(int suatain,int out_level);

    public static native int micFrequencyShiftSetup(int value);

    public static native int mic7bandPeqEnable(int enable);
    public static native int mic7bandPeqEdgeSetup(int left_type,int right_type);
    public static native int mic7bandPeqSetup(int band,int frequency,int Q,int gain);
    public static native int micDryOutputSetup(int level,int reverse);

    public static native int musicInputLevelSetup(int level);
    public static native int musicPhaseSetup(int reverse);
    public static native int musicKeyctrlEnable(int enable);
    public static native int musicKeyctrlSetup(int pitch, int pitch_fine);
    public static native int music31bandGeqEnable(int enable);
    public static native int music31bandGeqSetup(int band,int gain);

    public static native int reverbInputLevelSetup(int level);
    public static native int reverbOutputSetup(int level,int phase);
    public static native int reverbEffectsSetup(int type,int time,int tone_gain,int pre_delay);
    public static native int echoToReverbLevelSetup(int level);
    public static native int reverb5bandPeqEnable(int enable);
    public static native int reverb5bandPeqEdgeSetup(int left_type,int right_type);
    public static native int reverb5bandPeqSetup(int band,int frequency,int Q,int gain);
    public static native int reverbBandpassfilterSetup(int hpf_type,int hpf_frequency,int lpf_type,int lpf_frequency);

    public static native int echoInputLevelSetup(int level);
    public static native int echoOutputSetup(int level,int phase);
    public static native int echoEffectsSetup(int time,int level,int pan,int tone_gain,int feedback_level,int pre_delay);
    public static native int echoTapEffectsSetup(int idx,int time,int level,int pan);
    public static native int echo5bandPeqEnable(int enable);
    public static native int echo5bandPeqEdgeSetup(int left_type,int right_type);
    public static native int echo5bandPeqSetup(int band,int frequency,int Q,int gain);
    public static native int echoBandpassfilterSetup(int hpf_type,int hpf_frequency,int lpf_type,int lpf_frequency);

    public static int CHANNEL_REAR       = 1;    //外放
    public static int CHANNEL_FRONT      = 2;    //耳放
    public static int CHANNEL_SUBWOOFER  = 3;    //录音右声道
    public static int CHANNEL_CENTER     = 4;    //录音左声道
    public static native int mixerMixSetup(int channel,int direct_level,int echo_level,int reverb_level,int music_level);
    public static native int outputSetup(int channel,int level,int pre_delay,int phase);
    public static native int output5bandPeqEnable(int channel,int enable);
    public static native int output5bandPeqEdgeSetup(int channel,int left_type,int right_type);
    public static native int output5bandPeqSetup(int channel,int band,int frequency,int Q,int gain);
    public static native int outputBandpassfilterSetup(int channel,int hpf_type,int hpf_frequency,int lpf_type,int lpf_frequency);

    public static native int outputSubwoofferCompressorEnable(int enable);
    public static native int outputSubwoofferCompressorSetup(int attack,int release,int threshold,int ratio,int gain);

    public static native int cardReset();

    public static int SLOT_1 = 1;
    public static int SLOT_2 = 2;
    public static int SLOT_3 = 3;
    public static int SLOT_4 = 4;
    public static int SLOT_5 = 5;
    public static int SLOT_6 = 6;
    public static native int parameterSave(int slot);
    public static native int parameterLoad(int slot);

    public static int MIXER_BLOCK = 0;
    public static int MIC_EFFECTS_BLOCK = 1;
    public static int ECHO_REVERB__BLOCK = 2;
    public static int MUSIC_EFFECTS_BLOCK = 3;
    public static int OUTPUT_BLOCK = 4;
    public static native int parameter2memory(int block_idx);

    private static native int getSetting(int idx);

    public enum SettingsName {
        //mic
        MicInputL, MicInputR, MicInputAux,MicInputGain,
        BassCutSwitch, BassCutFrequency,
        NoiseSuppSwitch,NoiseSuppThreshold,
        CompressorSwitch,CompressorSustain,CompressorOutLevel,
        FrequencyShift,
        MicInputPhaseReverse,
        MicPeqSwitch,
        MicPeqBand1Type,MicPeqBand7Type,
        MicPeqBand1Freq,MicPeqBand2Freq,MicPeqBand3Freq,MicPeqBand4Freq,MicPeqBand5Freq,MicPeqBand6Freq,MicPeqBand7Freq,
        MicPeqBand1Q,MicPeqBand2Q,MicPeqBand3Q,MicPeqBand4Q,MicPeqBand5Q,MicPeqBand6Q,MicPeqBand7Q,
        MicPeqBand1Gain,MicPeqBand2Gain,MicPeqBand3Gain,MicPeqBand4Gain,MicPeqBand5Gain,MicPeqBand6Gain,MicPeqBand7Gain,
        //music
        MusicInputAux,MusicInputGain,
        KeyControlSwitch, KeyControlPitch,KeyControlPitchFine,
        MusicInputPhaseReverse,
        MusicGeqSwitch,
        MusicGeqBand1Gain,MusicGeqBand2Gain,MusicGeqBand3Gain,MusicGeqBand4Gain,MusicGeqBand5Gain,MusicGeqBand6Gain,MusicGeqBand7Gain,MusicGeqBand8Gain,
        MusicGeqBand9Gain,MusicGeqBand10Gain,MusicGeqBand11Gain,MusicGeqBand12Gain,MusicGeqBand13Gain,MusicGeqBand14Gain,MusicGeqBand15Gain,MusicGeqBand16Gain,
        MusicGeqBand17Gain,MusicGeqBand18Gain,MusicGeqBand19Gain,MusicGeqBand20Gain,MusicGeqBand21Gain,MusicGeqBand22Gain,MusicGeqBand23Gain,MusicGeqBand24Gain,
        MusicGeqBand25Gain,MusicGeqBand26Gain,MusicGeqBand27Gain,MusicGeqBand28Gain,MusicGeqBand29Gain,MusicGeqBand30Gain,MusicGeqBand31Gain,
        //echo
        EchoInput,EchoOutput,
        EchoLevel,EchoTime,EchoFeedbackLevel,EchoTone,EchoPan,EchoPreDelay,
        EchoTap1Time,EchoTap2Time,EchoTap3Time,
        EchoTap1Level,EchoTap2Level,EchoTap3Level,
        EchoTap1Pan,EchoTap2Pan,EchoTap3Pan,
        EchoPhaseReverse,
        EchoPeqSwitch,
        EchoPeqBand1Type,EchoPeqBand5Type,
        EchoHighpassFreq,EchoHighpassType,EchoLowpassFreq,EchoLowpassType,
        EchoPeqBand1Freq,EchoPeqBand2Freq,EchoPeqBand3Freq,EchoPeqBand4Freq,EchoPeqBand5Freq,
        EchoPeqBand1Q,EchoPeqBand2Q,EchoPeqBand3Q,EchoPeqBand4Q,EchoPeqBand5Q,
        EchoPeqBand1Gain,EchoPeqBand2Gain,EchoPeqBand3Gain,EchoPeqBand4Gain,EchoPeqBand5Gain,
        //reverb
        ReverbInput,ReverbOutput,
        ReverbType,ReverbTime,ReverbTone,ReverbPreDelay,EchoToReverb,
        ReverbPhaseReverse,
        ReverbPeqSwitch,
        ReverbPeqBand1Type,ReverbPeqBand5Type,
        ReverbHighpassFreq,ReverbHighpassType,ReverbLowpassFreq,ReverbLowpassType,
        ReverbPeqBand1Freq,ReverbPeqBand2Freq,ReverbPeqBand3Freq,ReverbPeqBand4Freq,ReverbPeqBand5Freq,
        ReverbPeqBand1Q,ReverbPeqBand2Q,ReverbPeqBand3Q,ReverbPeqBand4Q,ReverbPeqBand5Q,
        ReverbPeqBand1Gain,ReverbPeqBand2Gain,ReverbPeqBand3Gain,ReverbPeqBand4Gain,ReverbPeqBand5Gain,
        //rear
        RearOutputDirect,RearOutputEcho,RearOutputReverb,RearOutputMusic,RearOutputGain,
        RearOutputDelay,RearOutputPhaseReverse,
        RearOutputPeqSwitch,
        RearOutputPeqBand1Type,RearOutputPeqBand5Type,
        RearOutputHighpassFreq,RearOutputHighpassType,RearOutputLowpassFreq,RearOutputLowpassType,
        RearOutputPeqBand1Freq,RearOutputPeqBand2Freq,RearOutputPeqBand3Freq,RearOutputPeqBand4Freq,RearOutputPeqBand5Freq,
        RearOutputPeqBand1Q,RearOutputPeqBand2Q,RearOutputPeqBand3Q,RearOutputPeqBand4Q,RearOutputPeqBand5Q,
        RearOutputPeqBand1Gain,RearOutputPeqBand2Gain,RearOutputPeqBand3Gain,RearOutputPeqBand4Gain,RearOutputPeqBand5Gain,
        //front
        FrontOutputDirect,FrontOutputEcho,FrontOutputReverb,FrontOutputMusic,FrontOutputGain,
        FrontOutputDelay,FrontOutputPhaseReverse,
        FrontOutputPeqSwitch,
        FrontOutputPeqBand1Type,FrontOutputPeqBand5Type,
        FrontOutputHighpassFreq,FrontOutputHighpassType,FrontOutputLowpassFreq,FrontOutputLowpassType,
        FrontOutputPeqBand1Freq,FrontOutputPeqBand2Freq,FrontOutputPeqBand3Freq,FrontOutputPeqBand4Freq,FrontOutputPeqBand5Freq,
        FrontOutputPeqBand1Q,FrontOutputPeqBand2Q,FrontOutputPeqBand3Q,FrontOutputPeqBand4Q,FrontOutputPeqBand5Q,
        FrontOutputPeqBand1Gain,FrontOutputPeqBand2Gain,FrontOutputPeqBand3Gain,FrontOutputPeqBand4Gain,FrontOutputPeqBand5Gain,
        //subwoofer
        SubwooferOutputDirect,SubwooferOutputEcho,SubwooferOutputReverb,SubwooferOutputMusic,SubwooferOutputGain,
        SubwooferOutputDelay,SubwooferOutputPhaseReverse,
        SubwooferOutputPeqSwitch,
        SubwooferOutputPeqBand1Type,SubwooferOutputPeqBand5Type,
        SubwooferOutputHighpassFreq,SubwooferOutputHighpassType,SubwooferOutputLowpassFreq,SubwooferOutputLowpassType,
        SubwooferOutputPeqBand1Freq,SubwooferOutputPeqBand2Freq,SubwooferOutputPeqBand3Freq,SubwooferOutputPeqBand4Freq,SubwooferOutputPeqBand5Freq,
        SubwooferOutputPeqBand1Q,SubwooferOutputPeqBand2Q,SubwooferOutputPeqBand3Q,SubwooferOutputPeqBand4Q,SubwooferOutputPeqBand5Q,
        SubwooferOutputPeqBand1Gain,SubwooferOutputPeqBand2Gain,SubwooferOutputPeqBand3Gain,SubwooferOutputPeqBand4Gain,SubwooferOutputPeqBand5Gain,
        //center
        CenterOutputDirect,CenterOutputEcho,CenterOutputReverb,CenterOutputMusic,CenterOutputGain,
        CenterOutputDelay,CenterOutputPhaseReverse,
        CenterOutputPeqSwitch,
        CenterOutputPeqBand1Type,CenterOutputPeqBand5Type,
        CenterOutputHighpassFreq,CenterOutputHighpassType,CenterOutputLowpassFreq,CenterOutputLowpassType,
        CenterOutputPeqBand1Freq,CenterOutputPeqBand2Freq,CenterOutputPeqBand3Freq,CenterOutputPeqBand4Freq,CenterOutputPeqBand5Freq,
        CenterOutputPeqBand1Q,CenterOutputPeqBand2Q,CenterOutputPeqBand3Q,CenterOutputPeqBand4Q,CenterOutputPeqBand5Q,
        CenterOutputPeqBand1Gain,CenterOutputPeqBand2Gain,CenterOutputPeqBand3Gain,CenterOutputPeqBand4Gain,CenterOutputPeqBand5Gain,
        //subwoofer compressor
        OutputCompressorSwitch,OutputCompressorAttack,OutputCompressorRelease,OutputCompressorThreshold,OutputCompressorRatio,OutputCompressorPostgain,

        END
    }
    public int getValueOfSetting(SettingsName name)
    {
        return getSetting(name.ordinal());
    }
}
