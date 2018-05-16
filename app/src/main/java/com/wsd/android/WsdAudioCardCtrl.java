/**
 Settings native interface
 **/

package com.wsd.android;

import android.util.Log;

import static com.wsd.android.WsdAudioCardCtrl.SettingsName.CenterOutputPeqBand1Freq;
import static com.wsd.android.WsdAudioCardCtrl.SettingsName.CenterOutputPeqBand1Gain;
import static com.wsd.android.WsdAudioCardCtrl.SettingsName.CenterOutputPeqBand1Q;
import static com.wsd.android.WsdAudioCardCtrl.SettingsName.EchoPeqBand1Freq;
import static com.wsd.android.WsdAudioCardCtrl.SettingsName.EchoPeqBand1Gain;
import static com.wsd.android.WsdAudioCardCtrl.SettingsName.EchoPeqBand1Q;
import static com.wsd.android.WsdAudioCardCtrl.SettingsName.EchoTap1Level;
import static com.wsd.android.WsdAudioCardCtrl.SettingsName.EchoTap1Pan;
import static com.wsd.android.WsdAudioCardCtrl.SettingsName.EchoTap1Time;
import static com.wsd.android.WsdAudioCardCtrl.SettingsName.FrontOutputPeqBand1Freq;
import static com.wsd.android.WsdAudioCardCtrl.SettingsName.FrontOutputPeqBand1Gain;
import static com.wsd.android.WsdAudioCardCtrl.SettingsName.FrontOutputPeqBand1Q;
import static com.wsd.android.WsdAudioCardCtrl.SettingsName.MicPeqBand1Freq;
import static com.wsd.android.WsdAudioCardCtrl.SettingsName.MicPeqBand1Gain;
import static com.wsd.android.WsdAudioCardCtrl.SettingsName.MicPeqBand1Q;
import static com.wsd.android.WsdAudioCardCtrl.SettingsName.MusicGeqBand1Gain;
import static com.wsd.android.WsdAudioCardCtrl.SettingsName.RearOutputDelay;
import static com.wsd.android.WsdAudioCardCtrl.SettingsName.RearOutputDirect;
import static com.wsd.android.WsdAudioCardCtrl.SettingsName.RearOutputEcho;
import static com.wsd.android.WsdAudioCardCtrl.SettingsName.RearOutputGain;
import static com.wsd.android.WsdAudioCardCtrl.SettingsName.RearOutputHighpassFreq;
import static com.wsd.android.WsdAudioCardCtrl.SettingsName.RearOutputHighpassType;
import static com.wsd.android.WsdAudioCardCtrl.SettingsName.RearOutputLowpassFreq;
import static com.wsd.android.WsdAudioCardCtrl.SettingsName.RearOutputLowpassType;
import static com.wsd.android.WsdAudioCardCtrl.SettingsName.RearOutputMusic;
import static com.wsd.android.WsdAudioCardCtrl.SettingsName.RearOutputPeqBand1Freq;
import static com.wsd.android.WsdAudioCardCtrl.SettingsName.RearOutputPeqBand1Gain;
import static com.wsd.android.WsdAudioCardCtrl.SettingsName.RearOutputPeqBand1Q;
import static com.wsd.android.WsdAudioCardCtrl.SettingsName.RearOutputPeqBand1Type;
import static com.wsd.android.WsdAudioCardCtrl.SettingsName.RearOutputPeqBand5Gain;
import static com.wsd.android.WsdAudioCardCtrl.SettingsName.RearOutputPeqBand5Type;
import static com.wsd.android.WsdAudioCardCtrl.SettingsName.RearOutputPeqSwitch;
import static com.wsd.android.WsdAudioCardCtrl.SettingsName.RearOutputPhaseReverse;
import static com.wsd.android.WsdAudioCardCtrl.SettingsName.RearOutputReverb;
import static com.wsd.android.WsdAudioCardCtrl.SettingsName.ReverbPeqBand1Freq;
import static com.wsd.android.WsdAudioCardCtrl.SettingsName.ReverbPeqBand1Gain;
import static com.wsd.android.WsdAudioCardCtrl.SettingsName.ReverbPeqBand1Q;
import static com.wsd.android.WsdAudioCardCtrl.SettingsName.SubwooferOutputPeqBand1Freq;
import static com.wsd.android.WsdAudioCardCtrl.SettingsName.SubwooferOutputPeqBand1Gain;
import static com.wsd.android.WsdAudioCardCtrl.SettingsName.SubwooferOutputPeqBand1Q;

public class WsdAudioCardCtrl {
    static {
        System.loadLibrary("wsdaudiocardctrl_jni");
    }

    private final String TAG = "Audio Card Ctrl";

    public static native int audioCardInit(String path);

    public static native int audioCardDeinit();

    public static native int micInputLevelSetup(int level);

    public static native int micLRLevelSetup(int L_level, int R_level);

    public static native int auxLevelSetup(int mic_level, int music_level);

    public static native int micBasscutEnable(int enable);

    public static native int micBasscutFrequencySetup(int frenquency);

    public static native int micNoiseSuppEnable(int enable);

    public static native int micNoiseThresholdSetup(int threshold);

    public static native int micCompressorEnable(int enable);

    public static native int micCompressorSetup(int suatain, int out_level);

    public static native int micFrequencyShiftSetup(int value);

    public static native int mic7bandPeqEnable(int enable);

    public static native int mic7bandPeqEdgeSetup(int left_type, int right_type);

    public static native int mic7bandPeqSetup(int band, int frequency, int Q, int gain);

    public static native int micDryOutputSetup(int level, int reverse);

    public static native int musicInputLevelSetup(int level);

    public static native int musicPhaseSetup(int reverse);

    public static native int musicKeyctrlEnable(int enable);

    public static native int musicKeyctrlSetup(int pitch, int pitch_fine);

    public static native int music31bandGeqEnable(int enable);

    public static native int music31bandGeqSetup(int band, int gain);

    public static native int reverbInputLevelSetup(int level);

    public static native int reverbOutputSetup(int level, int phase);

    public static native int reverbEffectsSetup(int type, int time, int tone_gain, int pre_delay);

    public static native int echoToReverbLevelSetup(int level);

    public static native int reverb5bandPeqEnable(int enable);

    public static native int reverb5bandPeqEdgeSetup(int left_type, int right_type);

    public static native int reverb5bandPeqSetup(int band, int frequency, int Q, int gain);

    public static native int reverbBandpassfilterSetup(int hpf_type, int hpf_frequency, int lpf_type, int lpf_frequency);

    public static native int echoInputLevelSetup(int level);

    public static native int echoOutputSetup(int level, int phase);

    public static native int echoEffectsSetup(int time, int level, int pan, int tone_gain, int feedback_level, int pre_delay);

    public static native int echoTapEffectsSetup(int idx, int time, int level, int pan);

    public static native int echo5bandPeqEnable(int enable);

    public static native int echo5bandPeqEdgeSetup(int left_type, int right_type);

    public static native int echo5bandPeqSetup(int band, int frequency, int Q, int gain);

    public static native int echoBandpassfilterSetup(int hpf_type, int hpf_frequency, int lpf_type, int lpf_frequency);

    public static int CHANNEL_REAR = 1;    //外放
    public static int CHANNEL_FRONT = 2;    //耳放
    public static int CHANNEL_SUBWOOFER = 3;    //录音右声道
    public static int CHANNEL_CENTER = 4;    //录音左声道

    public static native int mixerMixSetup(int channel, int direct_level, int echo_level, int reverb_level, int music_level);

    public static native int outputSetup(int channel, int level, int pre_delay, int phase);

    public static native int output5bandPeqEnable(int channel, int enable);

    public static native int output5bandPeqEdgeSetup(int channel, int left_type, int right_type);

    public static native int output5bandPeqSetup(int channel, int band, int frequency, int Q, int gain);

    public static native int outputBandpassfilterSetup(int channel, int hpf_type, int hpf_frequency, int lpf_type, int lpf_frequency);

    public static native int outputSubwooferCompressorEnable(int enable);

    public static native int outputSubwooferCompressorSetup(int attack, int release, int threshold, int ratio, int gain);

    public static native int mixerCenterSubwooferSwitchStereo(int enable);

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
        MicInputL, MicInputR, MicInputAux, MicInputGain,
        BassCutSwitch, BassCutFrequency,
        NoiseSuppSwitch, NoiseSuppThreshold,
        CompressorSwitch, CompressorSustain, CompressorOutLevel,
        FrequencyShift,
        MicInputPhaseReverse,
        MicPeqSwitch,
        MicPeqBand1Type, MicPeqBand7Type,
        MicPeqBand1Freq, MicPeqBand2Freq, MicPeqBand3Freq, MicPeqBand4Freq, MicPeqBand5Freq, MicPeqBand6Freq, MicPeqBand7Freq,
        MicPeqBand1Q, MicPeqBand2Q, MicPeqBand3Q, MicPeqBand4Q, MicPeqBand5Q, MicPeqBand6Q, MicPeqBand7Q,
        MicPeqBand1Gain, MicPeqBand2Gain, MicPeqBand3Gain, MicPeqBand4Gain, MicPeqBand5Gain, MicPeqBand6Gain, MicPeqBand7Gain,
        //echo
        EchoInput, EchoOutput,
        EchoLevel, EchoTime, EchoFeedbackLevel, EchoTone, EchoPan, EchoPreDelay,
        EchoTap1Level, EchoTap2Level, EchoTap3Level,
        EchoTap1Time, EchoTap2Time, EchoTap3Time,
        EchoTap1Pan, EchoTap2Pan, EchoTap3Pan,
        EchoPhaseReverse,
        EchoPeqSwitch,
        EchoPeqBand1Type, EchoPeqBand5Type,
        EchoHighpassFreq, EchoHighpassType, EchoLowpassFreq, EchoLowpassType,
        EchoPeqBand1Freq, EchoPeqBand2Freq, EchoPeqBand3Freq, EchoPeqBand4Freq, EchoPeqBand5Freq,
        EchoPeqBand1Q, EchoPeqBand2Q, EchoPeqBand3Q, EchoPeqBand4Q, EchoPeqBand5Q,
        EchoPeqBand1Gain, EchoPeqBand2Gain, EchoPeqBand3Gain, EchoPeqBand4Gain, EchoPeqBand5Gain,
        //reverb
        ReverbInput, ReverbOutput,
        ReverbType, ReverbTime, ReverbTone, ReverbPreDelay, EchoToReverb,
        ReverbPhaseReverse,
        ReverbPeqSwitch,
        ReverbPeqBand1Type, ReverbPeqBand5Type,
        ReverbHighpassFreq, ReverbHighpassType, ReverbLowpassFreq, ReverbLowpassType,
        ReverbPeqBand1Freq, ReverbPeqBand2Freq, ReverbPeqBand3Freq, ReverbPeqBand4Freq, ReverbPeqBand5Freq,
        ReverbPeqBand1Q, ReverbPeqBand2Q, ReverbPeqBand3Q, ReverbPeqBand4Q, ReverbPeqBand5Q,
        ReverbPeqBand1Gain, ReverbPeqBand2Gain, ReverbPeqBand3Gain, ReverbPeqBand4Gain, ReverbPeqBand5Gain,
        //music
        MusicInputAux, MusicInputGain,
        KeyControlSwitch, KeyControlPitch, KeyControlPitchFine,
        MusicInputPhaseReverse,
        MusicGeqSwitch,
        MusicGeqBand1Gain, MusicGeqBand2Gain, MusicGeqBand3Gain, MusicGeqBand4Gain, MusicGeqBand5Gain, MusicGeqBand6Gain, MusicGeqBand7Gain, MusicGeqBand8Gain,
        MusicGeqBand9Gain, MusicGeqBand10Gain, MusicGeqBand11Gain, MusicGeqBand12Gain, MusicGeqBand13Gain, MusicGeqBand14Gain, MusicGeqBand15Gain, MusicGeqBand16Gain,
        MusicGeqBand17Gain, MusicGeqBand18Gain, MusicGeqBand19Gain, MusicGeqBand20Gain, MusicGeqBand21Gain, MusicGeqBand22Gain, MusicGeqBand23Gain, MusicGeqBand24Gain,
        MusicGeqBand25Gain, MusicGeqBand26Gain, MusicGeqBand27Gain, MusicGeqBand28Gain, MusicGeqBand29Gain, MusicGeqBand30Gain, MusicGeqBand31Gain,
        //rear
        RearOutputDirect, RearOutputEcho, RearOutputReverb, RearOutputMusic, RearOutputGain,
        RearOutputDelay, RearOutputPhaseReverse,
        RearOutputPeqSwitch,
        RearOutputPeqBand1Type, RearOutputPeqBand5Type,
        RearOutputHighpassFreq, RearOutputHighpassType, RearOutputLowpassFreq, RearOutputLowpassType,
        RearOutputPeqBand1Freq, RearOutputPeqBand2Freq, RearOutputPeqBand3Freq, RearOutputPeqBand4Freq, RearOutputPeqBand5Freq,
        RearOutputPeqBand1Q, RearOutputPeqBand2Q, RearOutputPeqBand3Q, RearOutputPeqBand4Q, RearOutputPeqBand5Q,
        RearOutputPeqBand1Gain, RearOutputPeqBand2Gain, RearOutputPeqBand3Gain, RearOutputPeqBand4Gain, RearOutputPeqBand5Gain,
        //front
        FrontOutputDirect, FrontOutputEcho, FrontOutputReverb, FrontOutputMusic, FrontOutputGain,
        FrontOutputDelay, FrontOutputPhaseReverse,
        FrontOutputPeqSwitch,
        FrontOutputPeqBand1Type, FrontOutputPeqBand5Type,
        FrontOutputHighpassFreq, FrontOutputHighpassType, FrontOutputLowpassFreq, FrontOutputLowpassType,
        FrontOutputPeqBand1Freq, FrontOutputPeqBand2Freq, FrontOutputPeqBand3Freq, FrontOutputPeqBand4Freq, FrontOutputPeqBand5Freq,
        FrontOutputPeqBand1Q, FrontOutputPeqBand2Q, FrontOutputPeqBand3Q, FrontOutputPeqBand4Q, FrontOutputPeqBand5Q,
        FrontOutputPeqBand1Gain, FrontOutputPeqBand2Gain, FrontOutputPeqBand3Gain, FrontOutputPeqBand4Gain, FrontOutputPeqBand5Gain,
        //subwoofer
        SubwooferOutputDirect, SubwooferOutputEcho, SubwooferOutputReverb, SubwooferOutputMusic, SubwooferOutputGain,
        SubwooferOutputDelay, SubwooferOutputPhaseReverse,
        SubwooferOutputPeqSwitch,
        SubwooferOutputPeqBand1Type, SubwooferOutputPeqBand5Type,
        SubwooferOutputHighpassFreq, SubwooferOutputHighpassType, SubwooferOutputLowpassFreq, SubwooferOutputLowpassType,
        SubwooferOutputPeqBand1Freq, SubwooferOutputPeqBand2Freq, SubwooferOutputPeqBand3Freq, SubwooferOutputPeqBand4Freq, SubwooferOutputPeqBand5Freq,
        SubwooferOutputPeqBand1Q, SubwooferOutputPeqBand2Q, SubwooferOutputPeqBand3Q, SubwooferOutputPeqBand4Q, SubwooferOutputPeqBand5Q,
        SubwooferOutputPeqBand1Gain, SubwooferOutputPeqBand2Gain, SubwooferOutputPeqBand3Gain, SubwooferOutputPeqBand4Gain, SubwooferOutputPeqBand5Gain,
        //center
        CenterOutputDirect, CenterOutputEcho, CenterOutputReverb, CenterOutputMusic, CenterOutputGain,
        CenterOutputDelay, CenterOutputPhaseReverse,
        CenterOutputPeqSwitch,
        CenterOutputPeqBand1Type, CenterOutputPeqBand5Type,
        CenterOutputHighpassFreq, CenterOutputHighpassType, CenterOutputLowpassFreq, CenterOutputLowpassType,
        CenterOutputPeqBand1Freq, CenterOutputPeqBand2Freq, CenterOutputPeqBand3Freq, CenterOutputPeqBand4Freq, CenterOutputPeqBand5Freq,
        CenterOutputPeqBand1Q, CenterOutputPeqBand2Q, CenterOutputPeqBand3Q, CenterOutputPeqBand4Q, CenterOutputPeqBand5Q,
        CenterOutputPeqBand1Gain, CenterOutputPeqBand2Gain, CenterOutputPeqBand3Gain, CenterOutputPeqBand4Gain, CenterOutputPeqBand5Gain,
        //subwoofer compressor
        OutputCompressorSwitch, OutputCompressorAttack, OutputCompressorRelease, OutputCompressorThreshold, OutputCompressorRatio, OutputCompressorPostgain,

        CenterSubwooferSwitchStereo,

        END
    }

    public int getValueFromDsp(SettingsName name) {
        return getSetting(name.ordinal());
    }

    public int setValueToDsp(SettingsName name, int value)
    {
        int ret = -1;
        final int itemPerChannel = RearOutputPeqBand5Gain.ordinal() - RearOutputDirect.ordinal() + 1;
        switch (name)
        {
            //mic
            case MicInputL:
                ret = micLRLevelSetup(value, 0xff);
                break;
            case MicInputR:
                ret = micLRLevelSetup(0xff, value);
                break;
            case MicInputGain:
                ret = micInputLevelSetup(value);
                break;
            case MicInputAux:
                ret = auxLevelSetup(value, 0xff);
                break;
            case MusicInputAux:
                ret = auxLevelSetup(0xff, value);
                break;
            case BassCutSwitch:
                ret = micBasscutEnable(value);
                break;
            case BassCutFrequency:
                ret = micBasscutFrequencySetup(value);
                break;
            case NoiseSuppSwitch:
                ret = micNoiseSuppEnable(value);
                break;
            case NoiseSuppThreshold:
                ret = micNoiseThresholdSetup(value);
                break;
            case CompressorSwitch:
                ret = micCompressorEnable(value);
                break;
            case CompressorSustain:
                ret = micCompressorSetup(value, 0xff);
                break;
            case CompressorOutLevel:
                ret = micCompressorSetup(0xff, value);
                break;
            case FrequencyShift:
                ret = micFrequencyShiftSetup(value);
                break;
            case MicInputPhaseReverse:
                ret = micDryOutputSetup(0xff, value);
                break;
            case MicPeqSwitch:
                ret = mic7bandPeqEnable(value);
                break;
            case MicPeqBand1Type:
                ret = mic7bandPeqEdgeSetup(value, 0xff);
                break;
            case MicPeqBand7Type:
                ret = mic7bandPeqEdgeSetup(0xff, value);
                break;
            case MicPeqBand1Freq:
            case MicPeqBand2Freq:
            case MicPeqBand3Freq:
            case MicPeqBand4Freq:
            case MicPeqBand5Freq:
            case MicPeqBand6Freq:
            case MicPeqBand7Freq:
                ret = mic7bandPeqSetup(name.ordinal() - MicPeqBand1Freq.ordinal() + 1, value, 0xff, 0x7f);
                break;
            case MicPeqBand1Q:
            case MicPeqBand2Q:
            case MicPeqBand3Q:
            case MicPeqBand4Q:
            case MicPeqBand5Q:
            case MicPeqBand6Q:
            case MicPeqBand7Q:
                ret = mic7bandPeqSetup(name.ordinal() - MicPeqBand1Q.ordinal() + 1, 0xff, value, 0x7f);
                break;
            case MicPeqBand1Gain:
            case MicPeqBand2Gain:
            case MicPeqBand3Gain:
            case MicPeqBand4Gain:
            case MicPeqBand5Gain:
            case MicPeqBand6Gain:
            case MicPeqBand7Gain:
                ret = mic7bandPeqSetup(name.ordinal() - MicPeqBand1Gain.ordinal() + 1, 0xff, 0xff, value);
                break;

            //music
            case MusicInputGain:
                ret = musicInputLevelSetup(value);
                break;
            case KeyControlSwitch:
                ret = musicKeyctrlEnable(value);
                break;
            case KeyControlPitch:
                ret = musicKeyctrlSetup(value, 0x7f);
                break;
            case KeyControlPitchFine:
                ret = musicKeyctrlSetup(0x7f, value);
                break;
            case MusicInputPhaseReverse:
                ret = musicPhaseSetup(value);
                break;
            case MusicGeqSwitch:
                ret = music31bandGeqEnable(value);
                break;
            case MusicGeqBand1Gain:
            case MusicGeqBand2Gain:
            case MusicGeqBand3Gain:
            case MusicGeqBand4Gain:
            case MusicGeqBand5Gain:
            case MusicGeqBand6Gain:
            case MusicGeqBand7Gain:
            case MusicGeqBand8Gain:
            case MusicGeqBand9Gain:
            case MusicGeqBand10Gain:
            case MusicGeqBand11Gain:
            case MusicGeqBand12Gain:
            case MusicGeqBand13Gain:
            case MusicGeqBand14Gain:
            case MusicGeqBand15Gain:
            case MusicGeqBand16Gain:
            case MusicGeqBand17Gain:
            case MusicGeqBand18Gain:
            case MusicGeqBand19Gain:
            case MusicGeqBand20Gain:
            case MusicGeqBand21Gain:
            case MusicGeqBand22Gain:
            case MusicGeqBand23Gain:
            case MusicGeqBand24Gain:
            case MusicGeqBand25Gain:
            case MusicGeqBand26Gain:
            case MusicGeqBand27Gain:
            case MusicGeqBand28Gain:
            case MusicGeqBand29Gain:
            case MusicGeqBand30Gain:
            case MusicGeqBand31Gain:
                ret = music31bandGeqSetup(name.ordinal() - MusicGeqBand1Gain.ordinal() + 1, value);
                break;

            //echo
            case EchoInput:
                ret = echoInputLevelSetup(value);
                break;
            case EchoOutput:
                ret = echoOutputSetup(value, 0xff);
                break;
            case EchoPhaseReverse:
                ret = echoOutputSetup(0xff, value);
                break;
            case EchoTime:
                ret = echoEffectsSetup(value, 0xff, 0xff, 0xff, 0xff, 0xff);
                break;
            case EchoLevel:
                ret = echoEffectsSetup(0xff, value, 0xff, 0xff, 0xff, 0xff);
                break;
            case EchoFeedbackLevel:
                ret = echoEffectsSetup(0xff, 0xff, 0xff, 0xff, value, 0xff);
                break;
            case EchoTone:
                ret = echoEffectsSetup(0xff, 0xff, 0xff, value, 0xff, 0xff);
                break;
            case EchoPan:
                ret = echoEffectsSetup(0xff, 0xff, value, 0xff, 0xff, 0xff);
                break;
            case EchoPreDelay:
                ret = echoEffectsSetup(0xff, 0xff, 0xff, 0xff, 0xff, value);
                break;
            case EchoTap1Time:
            case EchoTap2Time:
            case EchoTap3Time:
                ret = echoTapEffectsSetup(name.ordinal() - EchoTap1Time.ordinal() + 1, value, 0xff, 0xff);
                break;
            case EchoTap1Level:
            case EchoTap2Level:
            case EchoTap3Level:
                ret = echoTapEffectsSetup(name.ordinal() - EchoTap1Level.ordinal() + 1, 0xff, value, 0xff);
                break;
            case EchoTap1Pan:
            case EchoTap2Pan:
            case EchoTap3Pan:
                ret = echoTapEffectsSetup(name.ordinal() - EchoTap1Pan.ordinal() + 1, 0xff, 0xff, value);
                break;


            case EchoPeqSwitch:
                ret = echo5bandPeqEnable(value);
                break;
            case EchoPeqBand1Type:
                ret = echo5bandPeqEdgeSetup(value, 0xff);
                break;
            case EchoPeqBand5Type:
                ret = echo5bandPeqEdgeSetup(0xff, value);
                break;
            case EchoPeqBand1Freq:
            case EchoPeqBand2Freq:
            case EchoPeqBand3Freq:
            case EchoPeqBand4Freq:
            case EchoPeqBand5Freq:
                ret = echo5bandPeqSetup(name.ordinal() - EchoPeqBand1Freq.ordinal() + 1, value, 0xff, 0x7f);
                break;
            case EchoPeqBand1Q:
            case EchoPeqBand2Q:
            case EchoPeqBand3Q:
            case EchoPeqBand4Q:
            case EchoPeqBand5Q:
                ret = echo5bandPeqSetup(name.ordinal() - EchoPeqBand1Q.ordinal() + 1, 0xff, value, 0x7f);
                break;
            case EchoPeqBand1Gain:
            case EchoPeqBand2Gain:
            case EchoPeqBand3Gain:
            case EchoPeqBand4Gain:
            case EchoPeqBand5Gain:
                ret = echo5bandPeqSetup(name.ordinal() - EchoPeqBand1Gain.ordinal() + 1, 0xff, 0xff, value);
                break;
            case EchoHighpassFreq:
                ret = echoBandpassfilterSetup(0xff, value, 0xff, 0xff);
                break;
            case EchoHighpassType:
                ret = echoBandpassfilterSetup(value, 0xff, 0xff, 0xff);
                break;
            case EchoLowpassFreq:
                ret = echoBandpassfilterSetup(0xff, 0xff, 0xff, value);
                break;
            case EchoLowpassType:
                ret = echoBandpassfilterSetup(0xff, 0xff, value, 0xff);
                break;

            //reverb
            case ReverbInput:
                ret = reverbInputLevelSetup(value);
                break;
            case ReverbOutput:
                ret = reverbOutputSetup(value, 0xff);
                break;
            case ReverbPhaseReverse:
                ret = reverbOutputSetup(0xff, value);
                break;
            case ReverbTime:
                ret = reverbEffectsSetup(value, 0xff, 0xff, 0xff);
                break;
            case ReverbType:
                ret = reverbEffectsSetup(0xff, value, 0xff, 0xff);
                break;
            case ReverbTone:
                ret = reverbEffectsSetup(0xff, 0xff, value, 0xff);
                break;
            case ReverbPreDelay:
                ret = reverbEffectsSetup(0xff, 0xff, 0xff, value);
                break;
            case EchoToReverb:
                ret = echoToReverbLevelSetup(value);
                break;
            case ReverbPeqSwitch:
                ret = reverb5bandPeqEnable(value);
                break;
            case ReverbPeqBand1Type:
                ret = reverb5bandPeqEdgeSetup(value, 0xff);
                break;
            case ReverbPeqBand5Type:
                ret = reverb5bandPeqEdgeSetup(0xff, value);
                break;
            case ReverbPeqBand1Freq:
            case ReverbPeqBand2Freq:
            case ReverbPeqBand3Freq:
            case ReverbPeqBand4Freq:
            case ReverbPeqBand5Freq:
                ret = reverb5bandPeqSetup(name.ordinal() - ReverbPeqBand1Freq.ordinal() + 1, value, 0xff, 0x7f);
                break;
            case ReverbPeqBand1Q:
            case ReverbPeqBand2Q:
            case ReverbPeqBand3Q:
            case ReverbPeqBand4Q:
            case ReverbPeqBand5Q:
                ret = reverb5bandPeqSetup(name.ordinal() - ReverbPeqBand1Q.ordinal() + 1, 0xff, value, 0x7f);
                break;
            case ReverbPeqBand1Gain:
            case ReverbPeqBand2Gain:
            case ReverbPeqBand3Gain:
            case ReverbPeqBand4Gain:
            case ReverbPeqBand5Gain:
                ret = reverb5bandPeqSetup(name.ordinal() - ReverbPeqBand1Gain.ordinal() + 1, 0xff, 0xff, value);
                break;
            case ReverbHighpassFreq:
                ret = reverbBandpassfilterSetup(0xff, value, 0xff, 0xff);
                break;
            case ReverbHighpassType:
                ret = reverbBandpassfilterSetup(value, 0xff, 0xff, 0xff);
                break;
            case ReverbLowpassFreq:
                ret = reverbBandpassfilterSetup(0xff, 0xff, 0xff, value);
                break;
            case ReverbLowpassType:
                ret = reverbBandpassfilterSetup(0xff, 0xff, value, 0xff);
                break;

            //output
            case RearOutputDirect:
            case FrontOutputDirect:
            case SubwooferOutputDirect:
            case CenterOutputDirect:
                ret = mixerMixSetup((name.ordinal() - RearOutputDirect.ordinal()) / itemPerChannel + 1, value, 0xff, 0xff, 0xff);
                break;
            case RearOutputEcho:
            case FrontOutputEcho:
            case SubwooferOutputEcho:
            case CenterOutputEcho:
                ret = mixerMixSetup((name.ordinal() - RearOutputEcho.ordinal()) / itemPerChannel + 1, 0xff, value, 0xff, 0xff);
                break;
            case RearOutputReverb:
            case FrontOutputReverb:
            case SubwooferOutputReverb:
            case CenterOutputReverb:
                ret = mixerMixSetup((name.ordinal() - RearOutputReverb.ordinal()) / itemPerChannel + 1, 0xff, 0xff, value, 0xff);
                break;
            case RearOutputMusic:
            case FrontOutputMusic:
            case SubwooferOutputMusic:
            case CenterOutputMusic:
                ret = mixerMixSetup((name.ordinal() - RearOutputMusic.ordinal()) / itemPerChannel + 1, 0xff, 0xff, 0xff, value);
                break;
            case RearOutputGain:
            case FrontOutputGain:
            case SubwooferOutputGain:
            case CenterOutputGain:
                ret = outputSetup((name.ordinal() - RearOutputGain.ordinal()) / itemPerChannel + 1, value, 0xff, 0xff);
                break;
            case RearOutputDelay:
            case FrontOutputDelay:
            case SubwooferOutputDelay:
            case CenterOutputDelay:
                ret = outputSetup((name.ordinal() - RearOutputDelay.ordinal()) / itemPerChannel + 1, 0xff, value, 0xff);
                break;
            case RearOutputPhaseReverse:
            case FrontOutputPhaseReverse:
            case SubwooferOutputPhaseReverse:
            case CenterOutputPhaseReverse:
                ret = outputSetup((name.ordinal() - RearOutputPhaseReverse.ordinal()) / itemPerChannel + 1, 0xff, 0xff, value);
                break;
            case RearOutputPeqSwitch:
            case FrontOutputPeqSwitch:
            case SubwooferOutputPeqSwitch:
            case CenterOutputPeqSwitch:
                ret = output5bandPeqEnable((name.ordinal() - RearOutputPeqSwitch.ordinal()) / itemPerChannel + 1, value);
                break;
            case RearOutputPeqBand1Type:
            case FrontOutputPeqBand1Type:
            case SubwooferOutputPeqBand1Type:
            case CenterOutputPeqBand1Type:
                ret = output5bandPeqEdgeSetup((name.ordinal() - RearOutputPeqBand1Type.ordinal()) / itemPerChannel + 1, value, 0xff);
                break;
            case RearOutputPeqBand5Type:
            case FrontOutputPeqBand5Type:
            case SubwooferOutputPeqBand5Type:
            case CenterOutputPeqBand5Type:
                ret = output5bandPeqEdgeSetup((name.ordinal() - RearOutputPeqBand5Type.ordinal()) / itemPerChannel + 1, 0xff, value);
                break;
            case RearOutputHighpassFreq:
            case FrontOutputHighpassFreq:
            case SubwooferOutputHighpassFreq:
            case CenterOutputHighpassFreq:
                ret = outputBandpassfilterSetup((name.ordinal() - RearOutputHighpassFreq.ordinal()) / itemPerChannel + 1, 0xff, value, 0xff, 0xff);
                break;
            case RearOutputHighpassType:
            case FrontOutputHighpassType:
            case SubwooferOutputHighpassType:
            case CenterOutputHighpassType:
                ret = outputBandpassfilterSetup((name.ordinal() - RearOutputHighpassType.ordinal()) / itemPerChannel + 1, value, 0xff, 0xff, 0xff);
                break;
            case RearOutputLowpassFreq:
            case FrontOutputLowpassFreq:
            case SubwooferOutputLowpassFreq:
            case CenterOutputLowpassFreq:
                ret = outputBandpassfilterSetup((name.ordinal() - RearOutputLowpassFreq.ordinal()) / itemPerChannel + 1, 0xff, 0xff, 0xff, value);
                break;
            case RearOutputLowpassType:
            case FrontOutputLowpassType:
            case SubwooferOutputLowpassType:
            case CenterOutputLowpassType:
                ret = outputBandpassfilterSetup((name.ordinal() - RearOutputLowpassType.ordinal()) / itemPerChannel + 1, 0xff, 0xff, value, 0xff);
                break;
            case RearOutputPeqBand1Freq:
            case RearOutputPeqBand2Freq:
            case RearOutputPeqBand3Freq:
            case RearOutputPeqBand4Freq:
            case RearOutputPeqBand5Freq:
                ret = output5bandPeqSetup(1, name.ordinal() - RearOutputPeqBand1Freq.ordinal() + 1, value, 0xff, 0x7f);
                break;
            case FrontOutputPeqBand1Freq:
            case FrontOutputPeqBand2Freq:
            case FrontOutputPeqBand3Freq:
            case FrontOutputPeqBand4Freq:
            case FrontOutputPeqBand5Freq:
                ret = output5bandPeqSetup(2, name.ordinal() - FrontOutputPeqBand1Freq.ordinal() + 1, value, 0xff, 0x7f);
                break;
            case SubwooferOutputPeqBand1Freq:
            case SubwooferOutputPeqBand2Freq:
            case SubwooferOutputPeqBand3Freq:
            case SubwooferOutputPeqBand4Freq:
            case SubwooferOutputPeqBand5Freq:
                ret = output5bandPeqSetup(3, name.ordinal() - SubwooferOutputPeqBand1Freq.ordinal() + 1, value, 0xff, 0x7f);
                break;
            case CenterOutputPeqBand1Freq:
            case CenterOutputPeqBand2Freq:
            case CenterOutputPeqBand3Freq:
            case CenterOutputPeqBand4Freq:
            case CenterOutputPeqBand5Freq:
                ret = output5bandPeqSetup(4, name.ordinal() - CenterOutputPeqBand1Freq.ordinal() + 1, value, 0xff, 0x7f);
                break;
            case RearOutputPeqBand1Q:
            case RearOutputPeqBand2Q:
            case RearOutputPeqBand3Q:
            case RearOutputPeqBand4Q:
            case RearOutputPeqBand5Q:
                ret = output5bandPeqSetup(1, name.ordinal() - RearOutputPeqBand1Q.ordinal() + 1, 0xff, value, 0x7f);
                break;
            case FrontOutputPeqBand1Q:
            case FrontOutputPeqBand2Q:
            case FrontOutputPeqBand3Q:
            case FrontOutputPeqBand4Q:
            case FrontOutputPeqBand5Q:
                ret = output5bandPeqSetup(2, name.ordinal() - FrontOutputPeqBand1Q.ordinal() + 1, 0xff, value, 0x7f);
                break;
            case SubwooferOutputPeqBand1Q:
            case SubwooferOutputPeqBand2Q:
            case SubwooferOutputPeqBand3Q:
            case SubwooferOutputPeqBand4Q:
            case SubwooferOutputPeqBand5Q:
                ret = output5bandPeqSetup(3, name.ordinal() - SubwooferOutputPeqBand1Q.ordinal() + 1, 0xff, value, 0x7f);
                break;
            case CenterOutputPeqBand1Q:
            case CenterOutputPeqBand2Q:
            case CenterOutputPeqBand3Q:
            case CenterOutputPeqBand4Q:
            case CenterOutputPeqBand5Q:
                ret = output5bandPeqSetup(4, name.ordinal() - CenterOutputPeqBand1Q.ordinal() + 1, 0xff, value, 0x7f);
                break;
            case RearOutputPeqBand1Gain:
            case RearOutputPeqBand2Gain:
            case RearOutputPeqBand3Gain:
            case RearOutputPeqBand4Gain:
            case RearOutputPeqBand5Gain:
                ret = output5bandPeqSetup(1, name.ordinal() - RearOutputPeqBand1Gain.ordinal() + 1, 0xff, 0xff, value);
                break;
            case FrontOutputPeqBand1Gain:
            case FrontOutputPeqBand2Gain:
            case FrontOutputPeqBand3Gain:
            case FrontOutputPeqBand4Gain:
            case FrontOutputPeqBand5Gain:
                ret = output5bandPeqSetup(2, name.ordinal() - FrontOutputPeqBand1Gain.ordinal() + 1, 0xff, 0xff, value);
                break;
            case SubwooferOutputPeqBand1Gain:
            case SubwooferOutputPeqBand2Gain:
            case SubwooferOutputPeqBand3Gain:
            case SubwooferOutputPeqBand4Gain:
            case SubwooferOutputPeqBand5Gain:
                ret = output5bandPeqSetup(3, name.ordinal() - SubwooferOutputPeqBand1Gain.ordinal() + 1, 0xff, 0xff, value);
                break;
            case CenterOutputPeqBand1Gain:
            case CenterOutputPeqBand2Gain:
            case CenterOutputPeqBand3Gain:
            case CenterOutputPeqBand4Gain:
            case CenterOutputPeqBand5Gain:
                ret = output5bandPeqSetup(4, name.ordinal() - CenterOutputPeqBand1Gain.ordinal() + 1, 0xff, 0xff, value);
                break;

            case OutputCompressorSwitch:
                ret = outputSubwooferCompressorEnable(value);
                break;
            case OutputCompressorAttack:
                ret = outputSubwooferCompressorSetup(value, 0xff, 0xff, 0xff, 0xff);
                break;
            case OutputCompressorRelease:
                ret = outputSubwooferCompressorSetup(0xff, value, 0xff, 0xff, 0xff);
                break;
            case OutputCompressorThreshold:
                ret = outputSubwooferCompressorSetup(0xff, 0xff, value, 0xff, 0xff);
                break;
            case OutputCompressorRatio:
                ret = outputSubwooferCompressorSetup(0xff, 0xff, 0xff, value, 0xff);
                break;
            case OutputCompressorPostgain:
                ret = outputSubwooferCompressorSetup(0xff, 0xff, 0xff, 0xff, value);
                break;
            case CenterSubwooferSwitchStereo:
                ret = mixerCenterSubwooferSwitchStereo(value);
                break;
            default:
                Log.e(TAG,"need to add missing case");
                ret = -1;
                break;
        }
        return ret;
    }
}
