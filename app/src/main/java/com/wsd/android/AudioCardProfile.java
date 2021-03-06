package com.wsd.android;

import android.content.SharedPreferences;
import android.util.Log;

/**
 * Created by jeff on 17-3-31.
 */

public class AudioCardProfile {
    private final String TAG = "Audio Card Profile";
    private SharedPreferences save_slot;

    private int[] mSavedSettings;

    private void micValueSetBackToDefault()
    {
        mSavedSettings[WsdAudioCardCtrl.SettingsName.MicInputL.ordinal()] = 64;
        mSavedSettings[WsdAudioCardCtrl.SettingsName.MicInputR.ordinal()] = 64;
        mSavedSettings[WsdAudioCardCtrl.SettingsName.MicInputAux.ordinal()] = 0;
        mSavedSettings[WsdAudioCardCtrl.SettingsName.MicInputGain.ordinal()] = 64;

        mSavedSettings[WsdAudioCardCtrl.SettingsName.BassCutSwitch.ordinal()] = 0;
        mSavedSettings[WsdAudioCardCtrl.SettingsName.BassCutFrequency.ordinal()] = 1;

        mSavedSettings[WsdAudioCardCtrl.SettingsName.NoiseSuppSwitch.ordinal()] = 0;
        mSavedSettings[WsdAudioCardCtrl.SettingsName.NoiseSuppThreshold.ordinal()] = 3;

        mSavedSettings[WsdAudioCardCtrl.SettingsName.CompressorSwitch.ordinal()] = 0;
        mSavedSettings[WsdAudioCardCtrl.SettingsName.CompressorSustain.ordinal()] = 7;
        mSavedSettings[WsdAudioCardCtrl.SettingsName.CompressorOutLevel.ordinal()] = 32;

        mSavedSettings[WsdAudioCardCtrl.SettingsName.FrequencyShift.ordinal()] = 0;

        mSavedSettings[WsdAudioCardCtrl.SettingsName.MicInputPhaseReverse.ordinal()] = 0;

        mSavedSettings[WsdAudioCardCtrl.SettingsName.MicPeqSwitch.ordinal()] = 0;

        mSavedSettings[WsdAudioCardCtrl.SettingsName.MicPeqBand1Type.ordinal()] = 0;
        mSavedSettings[WsdAudioCardCtrl.SettingsName.MicPeqBand7Type.ordinal()] = 0;

        mSavedSettings[WsdAudioCardCtrl.SettingsName.MicPeqBand1Freq.ordinal()] = 12;
        mSavedSettings[WsdAudioCardCtrl.SettingsName.MicPeqBand2Freq.ordinal()] = 18;
        mSavedSettings[WsdAudioCardCtrl.SettingsName.MicPeqBand3Freq.ordinal()] = 24;
        mSavedSettings[WsdAudioCardCtrl.SettingsName.MicPeqBand4Freq.ordinal()] = 30;
        mSavedSettings[WsdAudioCardCtrl.SettingsName.MicPeqBand5Freq.ordinal()] = 36;
        mSavedSettings[WsdAudioCardCtrl.SettingsName.MicPeqBand6Freq.ordinal()] = 42;
        mSavedSettings[WsdAudioCardCtrl.SettingsName.MicPeqBand7Freq.ordinal()] = 48;

        mSavedSettings[WsdAudioCardCtrl.SettingsName.MicPeqBand1Q.ordinal()] = 7;
        mSavedSettings[WsdAudioCardCtrl.SettingsName.MicPeqBand2Q.ordinal()] = 7;
        mSavedSettings[WsdAudioCardCtrl.SettingsName.MicPeqBand3Q.ordinal()] = 7;
        mSavedSettings[WsdAudioCardCtrl.SettingsName.MicPeqBand4Q.ordinal()] = 7;
        mSavedSettings[WsdAudioCardCtrl.SettingsName.MicPeqBand5Q.ordinal()] = 7;
        mSavedSettings[WsdAudioCardCtrl.SettingsName.MicPeqBand6Q.ordinal()] = 7;
        mSavedSettings[WsdAudioCardCtrl.SettingsName.MicPeqBand7Q.ordinal()] = 7;

        mSavedSettings[WsdAudioCardCtrl.SettingsName.MicPeqBand1Gain.ordinal()] = 0;
        mSavedSettings[WsdAudioCardCtrl.SettingsName.MicPeqBand2Gain.ordinal()] = 0;
        mSavedSettings[WsdAudioCardCtrl.SettingsName.MicPeqBand3Gain.ordinal()] = 0;
        mSavedSettings[WsdAudioCardCtrl.SettingsName.MicPeqBand4Gain.ordinal()] = 0;
        mSavedSettings[WsdAudioCardCtrl.SettingsName.MicPeqBand5Gain.ordinal()] = 0;
        mSavedSettings[WsdAudioCardCtrl.SettingsName.MicPeqBand6Gain.ordinal()] = 0;
        mSavedSettings[WsdAudioCardCtrl.SettingsName.MicPeqBand7Gain.ordinal()] = 0;
    }

    private void musicValueSetBackToDefault()
    {
        mSavedSettings[WsdAudioCardCtrl.SettingsName.MusicInputAux.ordinal()] = 0;
        mSavedSettings[WsdAudioCardCtrl.SettingsName.MusicInputGain.ordinal()] = 64;

        mSavedSettings[WsdAudioCardCtrl.SettingsName.KeyControlSwitch.ordinal()] = 0;
        mSavedSettings[WsdAudioCardCtrl.SettingsName.KeyControlPitch.ordinal()] = 0;
        mSavedSettings[WsdAudioCardCtrl.SettingsName.KeyControlPitchFine.ordinal()] = 0;

        mSavedSettings[WsdAudioCardCtrl.SettingsName.MusicInputPhaseReverse.ordinal()] = 0;

        mSavedSettings[WsdAudioCardCtrl.SettingsName.MusicGeqSwitch.ordinal()] = 0;
        mSavedSettings[WsdAudioCardCtrl.SettingsName.MusicGeqBand1Gain.ordinal()] = 0;
        mSavedSettings[WsdAudioCardCtrl.SettingsName.MusicGeqBand2Gain.ordinal()] = 0;
        mSavedSettings[WsdAudioCardCtrl.SettingsName.MusicGeqBand3Gain.ordinal()] = 0;
        mSavedSettings[WsdAudioCardCtrl.SettingsName.MusicGeqBand4Gain.ordinal()] = 0;
        mSavedSettings[WsdAudioCardCtrl.SettingsName.MusicGeqBand5Gain.ordinal()] = 0;
        mSavedSettings[WsdAudioCardCtrl.SettingsName.MusicGeqBand6Gain.ordinal()] = 0;
        mSavedSettings[WsdAudioCardCtrl.SettingsName.MusicGeqBand7Gain.ordinal()] = 0;
        mSavedSettings[WsdAudioCardCtrl.SettingsName.MusicGeqBand8Gain.ordinal()] = 0;
        mSavedSettings[WsdAudioCardCtrl.SettingsName.MusicGeqBand9Gain.ordinal()] = 0;
        mSavedSettings[WsdAudioCardCtrl.SettingsName.MusicGeqBand10Gain.ordinal()] = 0;
        mSavedSettings[WsdAudioCardCtrl.SettingsName.MusicGeqBand11Gain.ordinal()] = 0;
        mSavedSettings[WsdAudioCardCtrl.SettingsName.MusicGeqBand12Gain.ordinal()] = 0;
        mSavedSettings[WsdAudioCardCtrl.SettingsName.MusicGeqBand13Gain.ordinal()] = 0;
        mSavedSettings[WsdAudioCardCtrl.SettingsName.MusicGeqBand14Gain.ordinal()] = 0;
        mSavedSettings[WsdAudioCardCtrl.SettingsName.MusicGeqBand15Gain.ordinal()] = 0;
        mSavedSettings[WsdAudioCardCtrl.SettingsName.MusicGeqBand16Gain.ordinal()] = 0;
        mSavedSettings[WsdAudioCardCtrl.SettingsName.MusicGeqBand17Gain.ordinal()] = 0;
        mSavedSettings[WsdAudioCardCtrl.SettingsName.MusicGeqBand18Gain.ordinal()] = 0;
        mSavedSettings[WsdAudioCardCtrl.SettingsName.MusicGeqBand19Gain.ordinal()] = 0;
        mSavedSettings[WsdAudioCardCtrl.SettingsName.MusicGeqBand20Gain.ordinal()] = 0;
        mSavedSettings[WsdAudioCardCtrl.SettingsName.MusicGeqBand21Gain.ordinal()] = 0;
        mSavedSettings[WsdAudioCardCtrl.SettingsName.MusicGeqBand22Gain.ordinal()] = 0;
        mSavedSettings[WsdAudioCardCtrl.SettingsName.MusicGeqBand23Gain.ordinal()] = 0;
        mSavedSettings[WsdAudioCardCtrl.SettingsName.MusicGeqBand24Gain.ordinal()] = 0;
        mSavedSettings[WsdAudioCardCtrl.SettingsName.MusicGeqBand25Gain.ordinal()] = 0;
        mSavedSettings[WsdAudioCardCtrl.SettingsName.MusicGeqBand26Gain.ordinal()] = 0;
        mSavedSettings[WsdAudioCardCtrl.SettingsName.MusicGeqBand27Gain.ordinal()] = 0;
        mSavedSettings[WsdAudioCardCtrl.SettingsName.MusicGeqBand28Gain.ordinal()] = 0;
        mSavedSettings[WsdAudioCardCtrl.SettingsName.MusicGeqBand29Gain.ordinal()] = 0;
        mSavedSettings[WsdAudioCardCtrl.SettingsName.MusicGeqBand30Gain.ordinal()] = 0;
        mSavedSettings[WsdAudioCardCtrl.SettingsName.MusicGeqBand31Gain.ordinal()] = 0;
    }

    private void echoSetBackToDefault()
    {
        mSavedSettings[WsdAudioCardCtrl.SettingsName.EchoInput.ordinal()] = 0;
        mSavedSettings[WsdAudioCardCtrl.SettingsName.EchoOutput.ordinal()] = 64;

        mSavedSettings[WsdAudioCardCtrl.SettingsName.EchoLevel.ordinal()] = 127;
        mSavedSettings[WsdAudioCardCtrl.SettingsName.EchoTime.ordinal()] = 20;
        mSavedSettings[WsdAudioCardCtrl.SettingsName.EchoFeedbackLevel.ordinal()] = 50;
        mSavedSettings[WsdAudioCardCtrl.SettingsName.EchoTone.ordinal()] = 64;
        mSavedSettings[WsdAudioCardCtrl.SettingsName.EchoPan.ordinal()] = 64;
        mSavedSettings[WsdAudioCardCtrl.SettingsName.EchoPreDelay.ordinal()] = 0;

        mSavedSettings[WsdAudioCardCtrl.SettingsName.EchoTap1Time.ordinal()] = 63;
        mSavedSettings[WsdAudioCardCtrl.SettingsName.EchoTap2Time.ordinal()] = 63;
        mSavedSettings[WsdAudioCardCtrl.SettingsName.EchoTap3Time.ordinal()] = 63;
        mSavedSettings[WsdAudioCardCtrl.SettingsName.EchoTap1Level.ordinal()] = 0;
        mSavedSettings[WsdAudioCardCtrl.SettingsName.EchoTap2Level.ordinal()] = 0;
        mSavedSettings[WsdAudioCardCtrl.SettingsName.EchoTap3Level.ordinal()] = 0;
        mSavedSettings[WsdAudioCardCtrl.SettingsName.EchoTap1Pan.ordinal()] = 64;
        mSavedSettings[WsdAudioCardCtrl.SettingsName.EchoTap2Pan.ordinal()] = 64;
        mSavedSettings[WsdAudioCardCtrl.SettingsName.EchoTap3Pan.ordinal()] = 64;

        mSavedSettings[WsdAudioCardCtrl.SettingsName.EchoPhaseReverse.ordinal()] = 0;

        mSavedSettings[WsdAudioCardCtrl.SettingsName.EchoPeqSwitch.ordinal()] = 0;

        mSavedSettings[WsdAudioCardCtrl.SettingsName.EchoPeqBand1Type.ordinal()] = 0;
        mSavedSettings[WsdAudioCardCtrl.SettingsName.EchoPeqBand5Type.ordinal()] = 0;

        mSavedSettings[WsdAudioCardCtrl.SettingsName.EchoHighpassFreq.ordinal()] = 0;
        mSavedSettings[WsdAudioCardCtrl.SettingsName.EchoHighpassType.ordinal()] = 6;
        mSavedSettings[WsdAudioCardCtrl.SettingsName.EchoLowpassFreq.ordinal()] = 111;
        mSavedSettings[WsdAudioCardCtrl.SettingsName.EchoLowpassType.ordinal()] = 6;

        mSavedSettings[WsdAudioCardCtrl.SettingsName.EchoPeqBand1Freq.ordinal()] = 12;
        mSavedSettings[WsdAudioCardCtrl.SettingsName.EchoPeqBand2Freq.ordinal()] = 18;
        mSavedSettings[WsdAudioCardCtrl.SettingsName.EchoPeqBand3Freq.ordinal()] = 24;
        mSavedSettings[WsdAudioCardCtrl.SettingsName.EchoPeqBand4Freq.ordinal()] = 30;
        mSavedSettings[WsdAudioCardCtrl.SettingsName.EchoPeqBand5Freq.ordinal()] = 36;

        mSavedSettings[WsdAudioCardCtrl.SettingsName.EchoPeqBand1Q.ordinal()] = 7;
        mSavedSettings[WsdAudioCardCtrl.SettingsName.EchoPeqBand2Q.ordinal()] = 7;
        mSavedSettings[WsdAudioCardCtrl.SettingsName.EchoPeqBand3Q.ordinal()] = 7;
        mSavedSettings[WsdAudioCardCtrl.SettingsName.EchoPeqBand4Q.ordinal()] = 7;
        mSavedSettings[WsdAudioCardCtrl.SettingsName.EchoPeqBand5Q.ordinal()] = 7;

        mSavedSettings[WsdAudioCardCtrl.SettingsName.EchoPeqBand1Gain.ordinal()] = 0;
        mSavedSettings[WsdAudioCardCtrl.SettingsName.EchoPeqBand2Gain.ordinal()] = 0;
        mSavedSettings[WsdAudioCardCtrl.SettingsName.EchoPeqBand3Gain.ordinal()] = 0;
        mSavedSettings[WsdAudioCardCtrl.SettingsName.EchoPeqBand4Gain.ordinal()] = 0;
        mSavedSettings[WsdAudioCardCtrl.SettingsName.EchoPeqBand5Gain.ordinal()] = 0;
    }

    private void reverbSetBackToDefault()
    {
        mSavedSettings[WsdAudioCardCtrl.SettingsName.ReverbInput.ordinal()] = 0;
        mSavedSettings[WsdAudioCardCtrl.SettingsName.ReverbOutput.ordinal()] = 64;

        mSavedSettings[WsdAudioCardCtrl.SettingsName.ReverbType.ordinal()] = 2;
        mSavedSettings[WsdAudioCardCtrl.SettingsName.ReverbTime.ordinal()] = 10;
        mSavedSettings[WsdAudioCardCtrl.SettingsName.ReverbTone.ordinal()] = 64;
        mSavedSettings[WsdAudioCardCtrl.SettingsName.ReverbPreDelay.ordinal()] = 0;
        mSavedSettings[WsdAudioCardCtrl.SettingsName.EchoToReverb.ordinal()] = 0;

        mSavedSettings[WsdAudioCardCtrl.SettingsName.ReverbPhaseReverse.ordinal()] = 0;

        mSavedSettings[WsdAudioCardCtrl.SettingsName.ReverbPeqSwitch.ordinal()] = 0;

        mSavedSettings[WsdAudioCardCtrl.SettingsName.ReverbPeqBand1Type.ordinal()] = 0;
        mSavedSettings[WsdAudioCardCtrl.SettingsName.ReverbPeqBand5Type.ordinal()] = 0;

        mSavedSettings[WsdAudioCardCtrl.SettingsName.ReverbHighpassFreq.ordinal()] = 0;
        mSavedSettings[WsdAudioCardCtrl.SettingsName.ReverbHighpassType.ordinal()] = 6;
        mSavedSettings[WsdAudioCardCtrl.SettingsName.ReverbLowpassFreq.ordinal()] = 111;
        mSavedSettings[WsdAudioCardCtrl.SettingsName.ReverbLowpassType.ordinal()] = 6;

        mSavedSettings[WsdAudioCardCtrl.SettingsName.ReverbPeqBand1Freq.ordinal()] = 12;
        mSavedSettings[WsdAudioCardCtrl.SettingsName.ReverbPeqBand2Freq.ordinal()] = 18;
        mSavedSettings[WsdAudioCardCtrl.SettingsName.ReverbPeqBand3Freq.ordinal()] = 24;
        mSavedSettings[WsdAudioCardCtrl.SettingsName.ReverbPeqBand4Freq.ordinal()] = 30;
        mSavedSettings[WsdAudioCardCtrl.SettingsName.ReverbPeqBand5Freq.ordinal()] = 36;

        mSavedSettings[WsdAudioCardCtrl.SettingsName.ReverbPeqBand1Q.ordinal()] = 7;
        mSavedSettings[WsdAudioCardCtrl.SettingsName.ReverbPeqBand2Q.ordinal()] = 7;
        mSavedSettings[WsdAudioCardCtrl.SettingsName.ReverbPeqBand3Q.ordinal()] = 7;
        mSavedSettings[WsdAudioCardCtrl.SettingsName.ReverbPeqBand4Q.ordinal()] = 7;
        mSavedSettings[WsdAudioCardCtrl.SettingsName.ReverbPeqBand5Q.ordinal()] = 7;

        mSavedSettings[WsdAudioCardCtrl.SettingsName.ReverbPeqBand1Gain.ordinal()] = 0;
        mSavedSettings[WsdAudioCardCtrl.SettingsName.ReverbPeqBand2Gain.ordinal()] = 0;
        mSavedSettings[WsdAudioCardCtrl.SettingsName.ReverbPeqBand3Gain.ordinal()] = 0;
        mSavedSettings[WsdAudioCardCtrl.SettingsName.ReverbPeqBand4Gain.ordinal()] = 0;
        mSavedSettings[WsdAudioCardCtrl.SettingsName.ReverbPeqBand5Gain.ordinal()] = 0;
    }

    private void rearSetBackToDefault()
    {
        //rear
        mSavedSettings[WsdAudioCardCtrl.SettingsName.RearOutputDirect.ordinal()] = 127;
        mSavedSettings[WsdAudioCardCtrl.SettingsName.RearOutputEcho.ordinal()] = 127;
        mSavedSettings[WsdAudioCardCtrl.SettingsName.RearOutputReverb.ordinal()] = 127;
        mSavedSettings[WsdAudioCardCtrl.SettingsName.RearOutputMusic.ordinal()] = 127;
        mSavedSettings[WsdAudioCardCtrl.SettingsName.RearOutputGain.ordinal()] = 127;

        mSavedSettings[WsdAudioCardCtrl.SettingsName.RearOutputDelay.ordinal()] = 0;
        mSavedSettings[WsdAudioCardCtrl.SettingsName.RearOutputPhaseReverse.ordinal()] = 0;

        mSavedSettings[WsdAudioCardCtrl.SettingsName.RearOutputPeqSwitch.ordinal()] = 0;

        mSavedSettings[WsdAudioCardCtrl.SettingsName.RearOutputPeqBand1Type.ordinal()] = 0;
        mSavedSettings[WsdAudioCardCtrl.SettingsName.RearOutputPeqBand5Type.ordinal()] = 0;

        mSavedSettings[WsdAudioCardCtrl.SettingsName.RearOutputHighpassFreq.ordinal()] = 0;
        mSavedSettings[WsdAudioCardCtrl.SettingsName.RearOutputHighpassType.ordinal()] = 6;
        mSavedSettings[WsdAudioCardCtrl.SettingsName.RearOutputLowpassFreq.ordinal()] = 111;
        mSavedSettings[WsdAudioCardCtrl.SettingsName.RearOutputLowpassType.ordinal()] = 6;

        mSavedSettings[WsdAudioCardCtrl.SettingsName.RearOutputPeqBand1Freq.ordinal()] = 12;
        mSavedSettings[WsdAudioCardCtrl.SettingsName.RearOutputPeqBand2Freq.ordinal()] = 18;
        mSavedSettings[WsdAudioCardCtrl.SettingsName.RearOutputPeqBand3Freq.ordinal()] = 24;
        mSavedSettings[WsdAudioCardCtrl.SettingsName.RearOutputPeqBand4Freq.ordinal()] = 30;
        mSavedSettings[WsdAudioCardCtrl.SettingsName.RearOutputPeqBand5Freq.ordinal()] = 36;

        mSavedSettings[WsdAudioCardCtrl.SettingsName.RearOutputPeqBand1Q.ordinal()] = 7;
        mSavedSettings[WsdAudioCardCtrl.SettingsName.RearOutputPeqBand2Q.ordinal()] = 7;
        mSavedSettings[WsdAudioCardCtrl.SettingsName.RearOutputPeqBand3Q.ordinal()] = 7;
        mSavedSettings[WsdAudioCardCtrl.SettingsName.RearOutputPeqBand4Q.ordinal()] = 7;
        mSavedSettings[WsdAudioCardCtrl.SettingsName.RearOutputPeqBand5Q.ordinal()] = 7;

        mSavedSettings[WsdAudioCardCtrl.SettingsName.RearOutputPeqBand1Gain.ordinal()] = 0;
        mSavedSettings[WsdAudioCardCtrl.SettingsName.RearOutputPeqBand2Gain.ordinal()] = 0;
        mSavedSettings[WsdAudioCardCtrl.SettingsName.RearOutputPeqBand3Gain.ordinal()] = 0;
        mSavedSettings[WsdAudioCardCtrl.SettingsName.RearOutputPeqBand4Gain.ordinal()] = 0;
        mSavedSettings[WsdAudioCardCtrl.SettingsName.RearOutputPeqBand5Gain.ordinal()] = 0;
    }

    private void frontSetBackToDefault()
    {
        mSavedSettings[WsdAudioCardCtrl.SettingsName.FrontOutputDirect.ordinal()] = 127;
        mSavedSettings[WsdAudioCardCtrl.SettingsName.FrontOutputEcho.ordinal()] = 127;
        mSavedSettings[WsdAudioCardCtrl.SettingsName.FrontOutputReverb.ordinal()] = 127;
        mSavedSettings[WsdAudioCardCtrl.SettingsName.FrontOutputMusic.ordinal()] = 127;
        mSavedSettings[WsdAudioCardCtrl.SettingsName.FrontOutputGain.ordinal()] = 127;

        mSavedSettings[WsdAudioCardCtrl.SettingsName.FrontOutputDelay.ordinal()] = 0;
        mSavedSettings[WsdAudioCardCtrl.SettingsName.FrontOutputPhaseReverse.ordinal()] = 0;

        mSavedSettings[WsdAudioCardCtrl.SettingsName.FrontOutputPeqSwitch.ordinal()] = 0;

        mSavedSettings[WsdAudioCardCtrl.SettingsName.FrontOutputPeqBand1Type.ordinal()] = 0;
        mSavedSettings[WsdAudioCardCtrl.SettingsName.FrontOutputPeqBand5Type.ordinal()] = 0;

        mSavedSettings[WsdAudioCardCtrl.SettingsName.FrontOutputHighpassFreq.ordinal()] = 0;
        mSavedSettings[WsdAudioCardCtrl.SettingsName.FrontOutputHighpassType.ordinal()] = 6;
        mSavedSettings[WsdAudioCardCtrl.SettingsName.FrontOutputLowpassFreq.ordinal()] = 111;
        mSavedSettings[WsdAudioCardCtrl.SettingsName.FrontOutputLowpassType.ordinal()] = 6;

        mSavedSettings[WsdAudioCardCtrl.SettingsName.FrontOutputPeqBand1Freq.ordinal()] = 12;
        mSavedSettings[WsdAudioCardCtrl.SettingsName.FrontOutputPeqBand2Freq.ordinal()] = 18;
        mSavedSettings[WsdAudioCardCtrl.SettingsName.FrontOutputPeqBand3Freq.ordinal()] = 24;
        mSavedSettings[WsdAudioCardCtrl.SettingsName.FrontOutputPeqBand4Freq.ordinal()] = 30;
        mSavedSettings[WsdAudioCardCtrl.SettingsName.FrontOutputPeqBand5Freq.ordinal()] = 36;

        mSavedSettings[WsdAudioCardCtrl.SettingsName.FrontOutputPeqBand1Q.ordinal()] = 7;
        mSavedSettings[WsdAudioCardCtrl.SettingsName.FrontOutputPeqBand2Q.ordinal()] = 7;
        mSavedSettings[WsdAudioCardCtrl.SettingsName.FrontOutputPeqBand3Q.ordinal()] = 7;
        mSavedSettings[WsdAudioCardCtrl.SettingsName.FrontOutputPeqBand4Q.ordinal()] = 7;
        mSavedSettings[WsdAudioCardCtrl.SettingsName.FrontOutputPeqBand5Q.ordinal()] = 7;

        mSavedSettings[WsdAudioCardCtrl.SettingsName.FrontOutputPeqBand1Gain.ordinal()] = 0;
        mSavedSettings[WsdAudioCardCtrl.SettingsName.FrontOutputPeqBand2Gain.ordinal()] = 0;
        mSavedSettings[WsdAudioCardCtrl.SettingsName.FrontOutputPeqBand3Gain.ordinal()] = 0;
        mSavedSettings[WsdAudioCardCtrl.SettingsName.FrontOutputPeqBand4Gain.ordinal()] = 0;
        mSavedSettings[WsdAudioCardCtrl.SettingsName.FrontOutputPeqBand5Gain.ordinal()] = 0;
    }

    private void centerSetBackToDefault()
    {
        mSavedSettings[WsdAudioCardCtrl.SettingsName.CenterOutputDirect.ordinal()] = 127;
        mSavedSettings[WsdAudioCardCtrl.SettingsName.CenterOutputEcho.ordinal()] = 127;
        mSavedSettings[WsdAudioCardCtrl.SettingsName.CenterOutputReverb.ordinal()] = 127;
        mSavedSettings[WsdAudioCardCtrl.SettingsName.CenterOutputMusic.ordinal()] = 0;
        mSavedSettings[WsdAudioCardCtrl.SettingsName.CenterOutputGain.ordinal()] = 127;

        mSavedSettings[WsdAudioCardCtrl.SettingsName.CenterOutputDelay.ordinal()] = 0;
        mSavedSettings[WsdAudioCardCtrl.SettingsName.CenterOutputPhaseReverse.ordinal()] = 0;

        mSavedSettings[WsdAudioCardCtrl.SettingsName.CenterOutputPeqSwitch.ordinal()] = 0;

        mSavedSettings[WsdAudioCardCtrl.SettingsName.CenterOutputPeqBand1Type.ordinal()] = 0;
        mSavedSettings[WsdAudioCardCtrl.SettingsName.CenterOutputPeqBand5Type.ordinal()] = 0;

        mSavedSettings[WsdAudioCardCtrl.SettingsName.CenterOutputHighpassFreq.ordinal()] = 0;
        mSavedSettings[WsdAudioCardCtrl.SettingsName.CenterOutputHighpassType.ordinal()] = 6;
        mSavedSettings[WsdAudioCardCtrl.SettingsName.CenterOutputLowpassFreq.ordinal()] = 111;
        mSavedSettings[WsdAudioCardCtrl.SettingsName.CenterOutputLowpassType.ordinal()] = 6;

        mSavedSettings[WsdAudioCardCtrl.SettingsName.CenterOutputPeqBand1Freq.ordinal()] = 12;
        mSavedSettings[WsdAudioCardCtrl.SettingsName.CenterOutputPeqBand2Freq.ordinal()] = 18;
        mSavedSettings[WsdAudioCardCtrl.SettingsName.CenterOutputPeqBand3Freq.ordinal()] = 24;
        mSavedSettings[WsdAudioCardCtrl.SettingsName.CenterOutputPeqBand4Freq.ordinal()] = 30;
        mSavedSettings[WsdAudioCardCtrl.SettingsName.CenterOutputPeqBand5Freq.ordinal()] = 36;

        mSavedSettings[WsdAudioCardCtrl.SettingsName.CenterOutputPeqBand1Q.ordinal()] = 7;
        mSavedSettings[WsdAudioCardCtrl.SettingsName.CenterOutputPeqBand2Q.ordinal()] = 7;
        mSavedSettings[WsdAudioCardCtrl.SettingsName.CenterOutputPeqBand3Q.ordinal()] = 7;
        mSavedSettings[WsdAudioCardCtrl.SettingsName.CenterOutputPeqBand4Q.ordinal()] = 7;
        mSavedSettings[WsdAudioCardCtrl.SettingsName.CenterOutputPeqBand5Q.ordinal()] = 7;

        mSavedSettings[WsdAudioCardCtrl.SettingsName.CenterOutputPeqBand1Gain.ordinal()] = 0;
        mSavedSettings[WsdAudioCardCtrl.SettingsName.CenterOutputPeqBand2Gain.ordinal()] = 0;
        mSavedSettings[WsdAudioCardCtrl.SettingsName.CenterOutputPeqBand3Gain.ordinal()] = 0;
        mSavedSettings[WsdAudioCardCtrl.SettingsName.CenterOutputPeqBand4Gain.ordinal()] = 0;
        mSavedSettings[WsdAudioCardCtrl.SettingsName.CenterOutputPeqBand5Gain.ordinal()] = 0;
    }

    private void subWooferSetBackToDefault()
    {
        mSavedSettings[WsdAudioCardCtrl.SettingsName.SubwooferOutputDirect.ordinal()] = 127;
        mSavedSettings[WsdAudioCardCtrl.SettingsName.SubwooferOutputEcho.ordinal()] = 127;
        mSavedSettings[WsdAudioCardCtrl.SettingsName.SubwooferOutputReverb.ordinal()] = 127;
        mSavedSettings[WsdAudioCardCtrl.SettingsName.SubwooferOutputMusic.ordinal()] = 127;
        mSavedSettings[WsdAudioCardCtrl.SettingsName.SubwooferOutputGain.ordinal()] = 127;

        mSavedSettings[WsdAudioCardCtrl.SettingsName.SubwooferOutputDelay.ordinal()] = 0;
        mSavedSettings[WsdAudioCardCtrl.SettingsName.SubwooferOutputPhaseReverse.ordinal()] = 0;

        mSavedSettings[WsdAudioCardCtrl.SettingsName.SubwooferOutputPeqSwitch.ordinal()] = 1;

        mSavedSettings[WsdAudioCardCtrl.SettingsName.SubwooferOutputPeqBand1Type.ordinal()] = 0;
        mSavedSettings[WsdAudioCardCtrl.SettingsName.SubwooferOutputPeqBand5Type.ordinal()] = 0;

        mSavedSettings[WsdAudioCardCtrl.SettingsName.SubwooferOutputHighpassFreq.ordinal()] = 5;
        mSavedSettings[WsdAudioCardCtrl.SettingsName.SubwooferOutputHighpassType.ordinal()] = 6;
        mSavedSettings[WsdAudioCardCtrl.SettingsName.SubwooferOutputLowpassFreq.ordinal()] = 22;
        mSavedSettings[WsdAudioCardCtrl.SettingsName.SubwooferOutputLowpassType.ordinal()] = 6;

        mSavedSettings[WsdAudioCardCtrl.SettingsName.SubwooferOutputPeqBand1Freq.ordinal()] = 6;
        mSavedSettings[WsdAudioCardCtrl.SettingsName.SubwooferOutputPeqBand2Freq.ordinal()] = 10;
        mSavedSettings[WsdAudioCardCtrl.SettingsName.SubwooferOutputPeqBand3Freq.ordinal()] = 13;
        mSavedSettings[WsdAudioCardCtrl.SettingsName.SubwooferOutputPeqBand4Freq.ordinal()] = 16;
        mSavedSettings[WsdAudioCardCtrl.SettingsName.SubwooferOutputPeqBand5Freq.ordinal()] = 20;

        mSavedSettings[WsdAudioCardCtrl.SettingsName.SubwooferOutputPeqBand1Q.ordinal()] = 19;
        mSavedSettings[WsdAudioCardCtrl.SettingsName.SubwooferOutputPeqBand2Q.ordinal()] = 19;
        mSavedSettings[WsdAudioCardCtrl.SettingsName.SubwooferOutputPeqBand3Q.ordinal()] = 19;
        mSavedSettings[WsdAudioCardCtrl.SettingsName.SubwooferOutputPeqBand4Q.ordinal()] = 19;
        mSavedSettings[WsdAudioCardCtrl.SettingsName.SubwooferOutputPeqBand5Q.ordinal()] = 19;

        mSavedSettings[WsdAudioCardCtrl.SettingsName.SubwooferOutputPeqBand1Gain.ordinal()] = 0;
        mSavedSettings[WsdAudioCardCtrl.SettingsName.SubwooferOutputPeqBand2Gain.ordinal()] = 0;
        mSavedSettings[WsdAudioCardCtrl.SettingsName.SubwooferOutputPeqBand3Gain.ordinal()] = 0;
        mSavedSettings[WsdAudioCardCtrl.SettingsName.SubwooferOutputPeqBand4Gain.ordinal()] = 0;
        mSavedSettings[WsdAudioCardCtrl.SettingsName.SubwooferOutputPeqBand5Gain.ordinal()] = 0;

        //subwoofer compressor
        mSavedSettings[WsdAudioCardCtrl.SettingsName.OutputCompressorSwitch.ordinal()] = 1;
        mSavedSettings[WsdAudioCardCtrl.SettingsName.OutputCompressorAttack.ordinal()] = 80;
        mSavedSettings[WsdAudioCardCtrl.SettingsName.OutputCompressorRelease.ordinal()] = 5;
        mSavedSettings[WsdAudioCardCtrl.SettingsName.OutputCompressorThreshold.ordinal()] = 70;
        mSavedSettings[WsdAudioCardCtrl.SettingsName.OutputCompressorRatio.ordinal()] = 9;
        mSavedSettings[WsdAudioCardCtrl.SettingsName.OutputCompressorPostgain.ordinal()] = 5;

        mSavedSettings[WsdAudioCardCtrl.SettingsName.CenterSubwooferSwitchStereo.ordinal()] = 0;
    }



    public AudioCardProfile(SharedPreferences sp)
    {
        save_slot = sp;
        mSavedSettings = new int[WsdAudioCardCtrl.SettingsName.END.ordinal()+1];
        micValueSetBackToDefault();
        musicValueSetBackToDefault();
        echoSetBackToDefault();
        reverbSetBackToDefault();
        rearSetBackToDefault();
        frontSetBackToDefault();
        centerSetBackToDefault();
        subWooferSetBackToDefault();
    }

    public AudioCardProfile(AudioCardProfile source)
    {
        System.arraycopy(source.mSavedSettings, 0, this.mSavedSettings, 0, this.mSavedSettings.length);
    }

    public int set(WsdAudioCardCtrl.SettingsName enumName,int value)
    {
        if (enumName==WsdAudioCardCtrl.SettingsName.END) {
            Log.e(TAG,"enumName END illegal");
            return -1;
        }

        mSavedSettings[enumName.ordinal()] = value;
        return 0;
    }

    public int set(AudioCardProfile source)
    {
        System.arraycopy(source.mSavedSettings, 0, this.mSavedSettings, 0, this.mSavedSettings.length);
        return 0;
    }

    public int set(int idx,int value)
    {
        if (idx>=WsdAudioCardCtrl.SettingsName.END.ordinal() || idx<0)
        {
            Log.e(TAG,"idx illegal");
            return -1;
        }
        mSavedSettings[idx] = value;
        return 0;
    }

    public int get(WsdAudioCardCtrl.SettingsName enumName)
    {
        return mSavedSettings[enumName.ordinal()];
    }

    public int get(int idx)
    {
        if (idx>=WsdAudioCardCtrl.SettingsName.END.ordinal() || idx<0)
        {
            Log.e(TAG,"idx illegal");
            return -1;
        }

        return mSavedSettings[idx];
    }

    public int save()
    {
        if (save_slot!=null)
        {
            SharedPreferences.Editor editor = save_slot.edit();

            for (WsdAudioCardCtrl.SettingsName set : WsdAudioCardCtrl.SettingsName.values()) {
                //Log.e(TAG,"[save]"+  set.name() + "=" +  mSavedSettings[set.ordinal()]);
                editor.putInt(set.name(), mSavedSettings[set.ordinal()]);
            }

            editor.commit();
            return 0;
        }
        else
        {
            Log.e(TAG,"save_slot is null,so just do nothing");
            return -1;
        }
    }

    public int load()
    {
        if (record_exist())
        {
            for (WsdAudioCardCtrl.SettingsName set : WsdAudioCardCtrl.SettingsName.values()) {
                if (save_slot.getInt(set.name(), 255) != 255) {
                    mSavedSettings[set.ordinal()] = save_slot.getInt(set.name(), 255);
                    Log.e(TAG,"[load]" +  set.name() + "=" +  mSavedSettings[set.ordinal()]);
                }
            }
            return 0;
        }
        else
        {
            Log.e(TAG,"saved record is not exist,so just do nothing");
            return -1;
        }
    }

    public boolean record_exist()
    {
        if (save_slot==null) {
            Log.e(TAG, "save_slot is null,record not exist");
            return false;
        }

        if (save_slot.getInt(WsdAudioCardCtrl.SettingsName.END.name(), 255) != 255) {
            Log.e(TAG,"record exist");
            return true;
        } else {
            Log.e(TAG, "record not exist");
            return false;
        }
    }

    public void reset()
    {
        micValueSetBackToDefault();
        musicValueSetBackToDefault();
        echoSetBackToDefault();
        reverbSetBackToDefault();
        rearSetBackToDefault();
        frontSetBackToDefault();
        centerSetBackToDefault();
        subWooferSetBackToDefault();
    }

}
