package com.example.testmediaplayer;

import android.content.SharedPreferences;
import android.util.Log;

import com.wsd.android.WsdAudioCardCtrl;

/**
 * Created by jeff on 17-3-31.
 */

public class AudioCardProfile {
    private final String TAG = "Audio Card Profile";
    private SharedPreferences save_slot;
    private WsdAudioCardCtrl dspControl;

    private int[] mSavedSettings;


    private void micValueSetBackToDefault()
    {
        mSavedSettings[WsdAudioCardCtrl.SettingsName.MicInputL.ordinal()] = 64;
        mSavedSettings[WsdAudioCardCtrl.SettingsName.MicInputR.ordinal()] = 64;
        mSavedSettings[WsdAudioCardCtrl.SettingsName.MicInputAux.ordinal()] = 0;
        mSavedSettings[WsdAudioCardCtrl.SettingsName.MicInputGain.ordinal()] = 64;

        mSavedSettings[WsdAudioCardCtrl.SettingsName.BassCutSwitch.ordinal()] = 0;
        mSavedSettings[WsdAudioCardCtrl.SettingsName.BassCutFrequency.ordinal()] = 0;

        mSavedSettings[WsdAudioCardCtrl.SettingsName.NoiseSuppSwitch.ordinal()] = 0;
        mSavedSettings[WsdAudioCardCtrl.SettingsName.NoiseSuppThreshold.ordinal()] = 3;

        mSavedSettings[WsdAudioCardCtrl.SettingsName.CompressorSwitch.ordinal()] = 0;
        mSavedSettings[WsdAudioCardCtrl.SettingsName.CompressorSustain.ordinal()] = 7;
        mSavedSettings[WsdAudioCardCtrl.SettingsName.CompressorOutLevel.ordinal()] = 32;

        mSavedSettings[WsdAudioCardCtrl.SettingsName.FrequencyShift.ordinal()] = 10;

        mSavedSettings[WsdAudioCardCtrl.SettingsName.MicInputPhaseReverse.ordinal()] = 0;

        mSavedSettings[WsdAudioCardCtrl.SettingsName.MicPeqSwitch.ordinal()] = 0;

        mSavedSettings[WsdAudioCardCtrl.SettingsName.MicPeqBand1Type.ordinal()] = 0;
        mSavedSettings[WsdAudioCardCtrl.SettingsName.MicPeqBand7Type.ordinal()] = 0;

        mSavedSettings[WsdAudioCardCtrl.SettingsName.MicPeqBand1Freq.ordinal()] = 10;
        mSavedSettings[WsdAudioCardCtrl.SettingsName.MicPeqBand2Freq.ordinal()] = 16;
        mSavedSettings[WsdAudioCardCtrl.SettingsName.MicPeqBand3Freq.ordinal()] = 22;
        mSavedSettings[WsdAudioCardCtrl.SettingsName.MicPeqBand4Freq.ordinal()] = 28;
        mSavedSettings[WsdAudioCardCtrl.SettingsName.MicPeqBand5Freq.ordinal()] = 34;
        mSavedSettings[WsdAudioCardCtrl.SettingsName.MicPeqBand6Freq.ordinal()] = 40;
        mSavedSettings[WsdAudioCardCtrl.SettingsName.MicPeqBand7Freq.ordinal()] = 46;

        mSavedSettings[WsdAudioCardCtrl.SettingsName.MicPeqBand1Q.ordinal()] = 7;
        mSavedSettings[WsdAudioCardCtrl.SettingsName.MicPeqBand2Q.ordinal()] = 7;
        mSavedSettings[WsdAudioCardCtrl.SettingsName.MicPeqBand3Q.ordinal()] = 7;
        mSavedSettings[WsdAudioCardCtrl.SettingsName.MicPeqBand4Q.ordinal()] = 7;
        mSavedSettings[WsdAudioCardCtrl.SettingsName.MicPeqBand5Q.ordinal()] = 7;
        mSavedSettings[WsdAudioCardCtrl.SettingsName.MicPeqBand6Q.ordinal()] = 7;
        mSavedSettings[WsdAudioCardCtrl.SettingsName.MicPeqBand7Q.ordinal()] = 7;

        mSavedSettings[WsdAudioCardCtrl.SettingsName.MicPeqBand1Gain.ordinal()] = 60;
        mSavedSettings[WsdAudioCardCtrl.SettingsName.MicPeqBand2Gain.ordinal()] = 60;
        mSavedSettings[WsdAudioCardCtrl.SettingsName.MicPeqBand3Gain.ordinal()] = 60;
        mSavedSettings[WsdAudioCardCtrl.SettingsName.MicPeqBand4Gain.ordinal()] = 60;
        mSavedSettings[WsdAudioCardCtrl.SettingsName.MicPeqBand5Gain.ordinal()] = 60;
        mSavedSettings[WsdAudioCardCtrl.SettingsName.MicPeqBand6Gain.ordinal()] = 60;
        mSavedSettings[WsdAudioCardCtrl.SettingsName.MicPeqBand7Gain.ordinal()] = 60;
    }

    private void musicValueSetBackToDefault()
    {
        mSavedSettings[WsdAudioCardCtrl.SettingsName.MusicInputAux.ordinal()] = 0;
        mSavedSettings[WsdAudioCardCtrl.SettingsName.MusicInputGain.ordinal()] = 64;

        mSavedSettings[WsdAudioCardCtrl.SettingsName.KeyControlSwitch.ordinal()] = 0;
        mSavedSettings[WsdAudioCardCtrl.SettingsName.KeyControlPitch.ordinal()] = 12;
        mSavedSettings[WsdAudioCardCtrl.SettingsName.KeyControlPitchFine.ordinal()] = 50;

        mSavedSettings[WsdAudioCardCtrl.SettingsName.MusicInputPhaseReverse.ordinal()] = 0;

        mSavedSettings[WsdAudioCardCtrl.SettingsName.MusicGeqSwitch.ordinal()] = 0;
        mSavedSettings[WsdAudioCardCtrl.SettingsName.MusicGeqBand1Gain.ordinal()] = 60;
        mSavedSettings[WsdAudioCardCtrl.SettingsName.MusicGeqBand2Gain.ordinal()] = 60;
        mSavedSettings[WsdAudioCardCtrl.SettingsName.MusicGeqBand3Gain.ordinal()] = 60;
        mSavedSettings[WsdAudioCardCtrl.SettingsName.MusicGeqBand4Gain.ordinal()] = 60;
        mSavedSettings[WsdAudioCardCtrl.SettingsName.MusicGeqBand5Gain.ordinal()] = 60;
        mSavedSettings[WsdAudioCardCtrl.SettingsName.MusicGeqBand6Gain.ordinal()] = 60;
        mSavedSettings[WsdAudioCardCtrl.SettingsName.MusicGeqBand7Gain.ordinal()] = 60;
        mSavedSettings[WsdAudioCardCtrl.SettingsName.MusicGeqBand8Gain.ordinal()] = 60;
        mSavedSettings[WsdAudioCardCtrl.SettingsName.MusicGeqBand9Gain.ordinal()] = 60;
        mSavedSettings[WsdAudioCardCtrl.SettingsName.MusicGeqBand10Gain.ordinal()] = 60;
        mSavedSettings[WsdAudioCardCtrl.SettingsName.MusicGeqBand11Gain.ordinal()] = 60;
        mSavedSettings[WsdAudioCardCtrl.SettingsName.MusicGeqBand12Gain.ordinal()] = 60;
        mSavedSettings[WsdAudioCardCtrl.SettingsName.MusicGeqBand13Gain.ordinal()] = 60;
        mSavedSettings[WsdAudioCardCtrl.SettingsName.MusicGeqBand14Gain.ordinal()] = 60;
        mSavedSettings[WsdAudioCardCtrl.SettingsName.MusicGeqBand15Gain.ordinal()] = 60;
        mSavedSettings[WsdAudioCardCtrl.SettingsName.MusicGeqBand16Gain.ordinal()] = 60;
        mSavedSettings[WsdAudioCardCtrl.SettingsName.MusicGeqBand17Gain.ordinal()] = 60;
        mSavedSettings[WsdAudioCardCtrl.SettingsName.MusicGeqBand18Gain.ordinal()] = 60;
        mSavedSettings[WsdAudioCardCtrl.SettingsName.MusicGeqBand19Gain.ordinal()] = 60;
        mSavedSettings[WsdAudioCardCtrl.SettingsName.MusicGeqBand20Gain.ordinal()] = 60;
        mSavedSettings[WsdAudioCardCtrl.SettingsName.MusicGeqBand21Gain.ordinal()] = 60;
        mSavedSettings[WsdAudioCardCtrl.SettingsName.MusicGeqBand22Gain.ordinal()] = 60;
        mSavedSettings[WsdAudioCardCtrl.SettingsName.MusicGeqBand23Gain.ordinal()] = 60;
        mSavedSettings[WsdAudioCardCtrl.SettingsName.MusicGeqBand24Gain.ordinal()] = 60;
        mSavedSettings[WsdAudioCardCtrl.SettingsName.MusicGeqBand25Gain.ordinal()] = 60;
        mSavedSettings[WsdAudioCardCtrl.SettingsName.MusicGeqBand26Gain.ordinal()] = 60;
        mSavedSettings[WsdAudioCardCtrl.SettingsName.MusicGeqBand27Gain.ordinal()] = 60;
        mSavedSettings[WsdAudioCardCtrl.SettingsName.MusicGeqBand28Gain.ordinal()] = 60;
        mSavedSettings[WsdAudioCardCtrl.SettingsName.MusicGeqBand29Gain.ordinal()] = 60;
        mSavedSettings[WsdAudioCardCtrl.SettingsName.MusicGeqBand30Gain.ordinal()] = 60;
        mSavedSettings[WsdAudioCardCtrl.SettingsName.MusicGeqBand31Gain.ordinal()] = 60;
    }

    private void echoSetBackToDefault()
    {
        mSavedSettings[WsdAudioCardCtrl.SettingsName.EchoInput.ordinal()] = 0;
        mSavedSettings[WsdAudioCardCtrl.SettingsName.EchoOutput.ordinal()] = 64;

        mSavedSettings[WsdAudioCardCtrl.SettingsName.EchoLevel.ordinal()] = 127;
        mSavedSettings[WsdAudioCardCtrl.SettingsName.EchoTime.ordinal()] = 20;
        mSavedSettings[WsdAudioCardCtrl.SettingsName.EchoFeedbackLevel.ordinal()] = 50;
        mSavedSettings[WsdAudioCardCtrl.SettingsName.EchoTone.ordinal()] = 7;
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

        mSavedSettings[WsdAudioCardCtrl.SettingsName.EchoPeqBand1Freq.ordinal()] = 10;
        mSavedSettings[WsdAudioCardCtrl.SettingsName.EchoPeqBand2Freq.ordinal()] = 16;
        mSavedSettings[WsdAudioCardCtrl.SettingsName.EchoPeqBand3Freq.ordinal()] = 22;
        mSavedSettings[WsdAudioCardCtrl.SettingsName.EchoPeqBand4Freq.ordinal()] = 28;
        mSavedSettings[WsdAudioCardCtrl.SettingsName.EchoPeqBand5Freq.ordinal()] = 34;

        mSavedSettings[WsdAudioCardCtrl.SettingsName.EchoPeqBand1Q.ordinal()] = 7;
        mSavedSettings[WsdAudioCardCtrl.SettingsName.EchoPeqBand2Q.ordinal()] = 7;
        mSavedSettings[WsdAudioCardCtrl.SettingsName.EchoPeqBand3Q.ordinal()] = 7;
        mSavedSettings[WsdAudioCardCtrl.SettingsName.EchoPeqBand4Q.ordinal()] = 7;
        mSavedSettings[WsdAudioCardCtrl.SettingsName.EchoPeqBand5Q.ordinal()] = 7;

        mSavedSettings[WsdAudioCardCtrl.SettingsName.EchoPeqBand1Gain.ordinal()] = 60;
        mSavedSettings[WsdAudioCardCtrl.SettingsName.EchoPeqBand2Gain.ordinal()] = 60;
        mSavedSettings[WsdAudioCardCtrl.SettingsName.EchoPeqBand3Gain.ordinal()] = 60;
        mSavedSettings[WsdAudioCardCtrl.SettingsName.EchoPeqBand4Gain.ordinal()] = 60;
        mSavedSettings[WsdAudioCardCtrl.SettingsName.EchoPeqBand5Gain.ordinal()] = 60;
    }

    private void reverbSetBackToDefault()
    {
        mSavedSettings[WsdAudioCardCtrl.SettingsName.ReverbInput.ordinal()] = 0;
        mSavedSettings[WsdAudioCardCtrl.SettingsName.ReverbOutput.ordinal()] = 64;

        mSavedSettings[WsdAudioCardCtrl.SettingsName.ReverbType.ordinal()] = 2;
        mSavedSettings[WsdAudioCardCtrl.SettingsName.ReverbTime.ordinal()] = 10;
        mSavedSettings[WsdAudioCardCtrl.SettingsName.ReverbTone.ordinal()] = 7;
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

        mSavedSettings[WsdAudioCardCtrl.SettingsName.ReverbPeqBand1Freq.ordinal()] = 10;
        mSavedSettings[WsdAudioCardCtrl.SettingsName.ReverbPeqBand2Freq.ordinal()] = 16;
        mSavedSettings[WsdAudioCardCtrl.SettingsName.ReverbPeqBand3Freq.ordinal()] = 22;
        mSavedSettings[WsdAudioCardCtrl.SettingsName.ReverbPeqBand4Freq.ordinal()] = 28;
        mSavedSettings[WsdAudioCardCtrl.SettingsName.ReverbPeqBand5Freq.ordinal()] = 34;

        mSavedSettings[WsdAudioCardCtrl.SettingsName.ReverbPeqBand1Q.ordinal()] = 7;
        mSavedSettings[WsdAudioCardCtrl.SettingsName.ReverbPeqBand2Q.ordinal()] = 7;
        mSavedSettings[WsdAudioCardCtrl.SettingsName.ReverbPeqBand3Q.ordinal()] = 7;
        mSavedSettings[WsdAudioCardCtrl.SettingsName.ReverbPeqBand4Q.ordinal()] = 7;
        mSavedSettings[WsdAudioCardCtrl.SettingsName.ReverbPeqBand5Q.ordinal()] = 7;

        mSavedSettings[WsdAudioCardCtrl.SettingsName.ReverbPeqBand1Gain.ordinal()] = 60;
        mSavedSettings[WsdAudioCardCtrl.SettingsName.ReverbPeqBand2Gain.ordinal()] = 60;
        mSavedSettings[WsdAudioCardCtrl.SettingsName.ReverbPeqBand3Gain.ordinal()] = 60;
        mSavedSettings[WsdAudioCardCtrl.SettingsName.ReverbPeqBand4Gain.ordinal()] = 60;
        mSavedSettings[WsdAudioCardCtrl.SettingsName.ReverbPeqBand5Gain.ordinal()] = 60;
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

        mSavedSettings[WsdAudioCardCtrl.SettingsName.RearOutputPeqBand1Freq.ordinal()] = 10;
        mSavedSettings[WsdAudioCardCtrl.SettingsName.RearOutputPeqBand2Freq.ordinal()] = 16;
        mSavedSettings[WsdAudioCardCtrl.SettingsName.RearOutputPeqBand3Freq.ordinal()] = 22;
        mSavedSettings[WsdAudioCardCtrl.SettingsName.RearOutputPeqBand4Freq.ordinal()] = 28;
        mSavedSettings[WsdAudioCardCtrl.SettingsName.RearOutputPeqBand5Freq.ordinal()] = 34;

        mSavedSettings[WsdAudioCardCtrl.SettingsName.RearOutputPeqBand1Q.ordinal()] = 7;
        mSavedSettings[WsdAudioCardCtrl.SettingsName.RearOutputPeqBand2Q.ordinal()] = 7;
        mSavedSettings[WsdAudioCardCtrl.SettingsName.RearOutputPeqBand3Q.ordinal()] = 7;
        mSavedSettings[WsdAudioCardCtrl.SettingsName.RearOutputPeqBand4Q.ordinal()] = 7;
        mSavedSettings[WsdAudioCardCtrl.SettingsName.RearOutputPeqBand5Q.ordinal()] = 7;

        mSavedSettings[WsdAudioCardCtrl.SettingsName.RearOutputPeqBand1Gain.ordinal()] = 60;
        mSavedSettings[WsdAudioCardCtrl.SettingsName.RearOutputPeqBand2Gain.ordinal()] = 60;
        mSavedSettings[WsdAudioCardCtrl.SettingsName.RearOutputPeqBand3Gain.ordinal()] = 60;
        mSavedSettings[WsdAudioCardCtrl.SettingsName.RearOutputPeqBand4Gain.ordinal()] = 60;
        mSavedSettings[WsdAudioCardCtrl.SettingsName.RearOutputPeqBand5Gain.ordinal()] = 60;
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

        mSavedSettings[WsdAudioCardCtrl.SettingsName.FrontOutputPeqBand1Freq.ordinal()] = 10;
        mSavedSettings[WsdAudioCardCtrl.SettingsName.FrontOutputPeqBand2Freq.ordinal()] = 16;
        mSavedSettings[WsdAudioCardCtrl.SettingsName.FrontOutputPeqBand3Freq.ordinal()] = 22;
        mSavedSettings[WsdAudioCardCtrl.SettingsName.FrontOutputPeqBand4Freq.ordinal()] = 28;
        mSavedSettings[WsdAudioCardCtrl.SettingsName.FrontOutputPeqBand5Freq.ordinal()] = 34;

        mSavedSettings[WsdAudioCardCtrl.SettingsName.FrontOutputPeqBand1Q.ordinal()] = 7;
        mSavedSettings[WsdAudioCardCtrl.SettingsName.FrontOutputPeqBand2Q.ordinal()] = 7;
        mSavedSettings[WsdAudioCardCtrl.SettingsName.FrontOutputPeqBand3Q.ordinal()] = 7;
        mSavedSettings[WsdAudioCardCtrl.SettingsName.FrontOutputPeqBand4Q.ordinal()] = 7;
        mSavedSettings[WsdAudioCardCtrl.SettingsName.FrontOutputPeqBand5Q.ordinal()] = 7;

        mSavedSettings[WsdAudioCardCtrl.SettingsName.FrontOutputPeqBand1Gain.ordinal()] = 60;
        mSavedSettings[WsdAudioCardCtrl.SettingsName.FrontOutputPeqBand2Gain.ordinal()] = 60;
        mSavedSettings[WsdAudioCardCtrl.SettingsName.FrontOutputPeqBand3Gain.ordinal()] = 60;
        mSavedSettings[WsdAudioCardCtrl.SettingsName.FrontOutputPeqBand4Gain.ordinal()] = 60;
        mSavedSettings[WsdAudioCardCtrl.SettingsName.FrontOutputPeqBand5Gain.ordinal()] = 60;
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

        mSavedSettings[WsdAudioCardCtrl.SettingsName.CenterOutputPeqBand1Freq.ordinal()] = 10;
        mSavedSettings[WsdAudioCardCtrl.SettingsName.CenterOutputPeqBand2Freq.ordinal()] = 16;
        mSavedSettings[WsdAudioCardCtrl.SettingsName.CenterOutputPeqBand3Freq.ordinal()] = 22;
        mSavedSettings[WsdAudioCardCtrl.SettingsName.CenterOutputPeqBand4Freq.ordinal()] = 28;
        mSavedSettings[WsdAudioCardCtrl.SettingsName.CenterOutputPeqBand5Freq.ordinal()] = 34;

        mSavedSettings[WsdAudioCardCtrl.SettingsName.CenterOutputPeqBand1Q.ordinal()] = 7;
        mSavedSettings[WsdAudioCardCtrl.SettingsName.CenterOutputPeqBand2Q.ordinal()] = 7;
        mSavedSettings[WsdAudioCardCtrl.SettingsName.CenterOutputPeqBand3Q.ordinal()] = 7;
        mSavedSettings[WsdAudioCardCtrl.SettingsName.CenterOutputPeqBand4Q.ordinal()] = 7;
        mSavedSettings[WsdAudioCardCtrl.SettingsName.CenterOutputPeqBand5Q.ordinal()] = 7;

        mSavedSettings[WsdAudioCardCtrl.SettingsName.CenterOutputPeqBand1Gain.ordinal()] = 60;
        mSavedSettings[WsdAudioCardCtrl.SettingsName.CenterOutputPeqBand2Gain.ordinal()] = 60;
        mSavedSettings[WsdAudioCardCtrl.SettingsName.CenterOutputPeqBand3Gain.ordinal()] = 60;
        mSavedSettings[WsdAudioCardCtrl.SettingsName.CenterOutputPeqBand4Gain.ordinal()] = 60;
        mSavedSettings[WsdAudioCardCtrl.SettingsName.CenterOutputPeqBand5Gain.ordinal()] = 60;
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

        mSavedSettings[WsdAudioCardCtrl.SettingsName.SubwooferOutputPeqBand1Freq.ordinal()] = 4;
        mSavedSettings[WsdAudioCardCtrl.SettingsName.SubwooferOutputPeqBand2Freq.ordinal()] = 8;
        mSavedSettings[WsdAudioCardCtrl.SettingsName.SubwooferOutputPeqBand3Freq.ordinal()] = 11;
        mSavedSettings[WsdAudioCardCtrl.SettingsName.SubwooferOutputPeqBand4Freq.ordinal()] = 14;
        mSavedSettings[WsdAudioCardCtrl.SettingsName.SubwooferOutputPeqBand5Freq.ordinal()] = 18;

        mSavedSettings[WsdAudioCardCtrl.SettingsName.SubwooferOutputPeqBand1Q.ordinal()] = 19;
        mSavedSettings[WsdAudioCardCtrl.SettingsName.SubwooferOutputPeqBand2Q.ordinal()] = 19;
        mSavedSettings[WsdAudioCardCtrl.SettingsName.SubwooferOutputPeqBand3Q.ordinal()] = 19;
        mSavedSettings[WsdAudioCardCtrl.SettingsName.SubwooferOutputPeqBand4Q.ordinal()] = 19;
        mSavedSettings[WsdAudioCardCtrl.SettingsName.SubwooferOutputPeqBand5Q.ordinal()] = 19;

        mSavedSettings[WsdAudioCardCtrl.SettingsName.SubwooferOutputPeqBand1Gain.ordinal()] = 60;
        mSavedSettings[WsdAudioCardCtrl.SettingsName.SubwooferOutputPeqBand2Gain.ordinal()] = 60;
        mSavedSettings[WsdAudioCardCtrl.SettingsName.SubwooferOutputPeqBand3Gain.ordinal()] = 60;
        mSavedSettings[WsdAudioCardCtrl.SettingsName.SubwooferOutputPeqBand4Gain.ordinal()] = 60;
        mSavedSettings[WsdAudioCardCtrl.SettingsName.SubwooferOutputPeqBand5Gain.ordinal()] = 60;

        //subwoofer compressor
        mSavedSettings[WsdAudioCardCtrl.SettingsName.OutputCompressorSwitch.ordinal()] = 1;
        mSavedSettings[WsdAudioCardCtrl.SettingsName.OutputCompressorAttack.ordinal()] = 80;
        mSavedSettings[WsdAudioCardCtrl.SettingsName.OutputCompressorRelease.ordinal()] = 5;
        mSavedSettings[WsdAudioCardCtrl.SettingsName.OutputCompressorThreshold.ordinal()] = 70;
        mSavedSettings[WsdAudioCardCtrl.SettingsName.OutputCompressorRatio.ordinal()] = 9;
        mSavedSettings[WsdAudioCardCtrl.SettingsName.OutputCompressorPostgain.ordinal()] = 5;
    }

    public AudioCardProfile(SharedPreferences sp)
    {
        save_slot = sp;
        dspControl = null;
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

    public AudioCardProfile(SharedPreferences sp,WsdAudioCardCtrl wsd)
    {
        save_slot = sp;
        dspControl = wsd;
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

    public int set(WsdAudioCardCtrl.SettingsName enumName,int value)
    {
        if (enumName!=null) {
            mSavedSettings[enumName.ordinal()] = value;
            return 0;
        } else {
            Log.e(TAG,"no location to store value,because enumName is null");
            return -1;
        }
    }

    public int set(AudioCardProfile source)
    {
        System.arraycopy(source.mSavedSettings, 0, this.mSavedSettings, 0, this.mSavedSettings.length);
        return 0;
    }

    public int set(int idx,int value)
    {
        if (idx<WsdAudioCardCtrl.SettingsName.END.ordinal()) {
            mSavedSettings[idx] = value;
            return 0;
        }
        else
        {
            Log.e(TAG,"no location to store value,because idx:" + idx + "out of the settings END");
            return -1;
        }
    }

    public int get(WsdAudioCardCtrl.SettingsName enumName)
    {
        if (enumName!=null) {
            return mSavedSettings[enumName.ordinal()];
        }
        Log.e(TAG,"the setting enumName is null");
        return -1;
    }

    public int get(int idx)
    {
        if (idx<WsdAudioCardCtrl.SettingsName.END.ordinal()) {
            return mSavedSettings[idx];
        }

        Log.e(TAG,"the setting idx:" + idx + "not found");
        return -1;
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
        if (save_slot!=null && record_exist())
        {
            for (WsdAudioCardCtrl.SettingsName set : WsdAudioCardCtrl.SettingsName.values()) {
                if (save_slot.getInt(set.name(), -1) != -1) {
                    mSavedSettings[set.ordinal()] = save_slot.getInt(set.name(), -1);
                    //Log.e(TAG,"[load]" +  set.name() + "=" +  mSavedSettings[set.ordinal()]);
                }
            }
            return 0;
        }
        else
        {
            Log.e(TAG,"save_slot is null || saved record is not exist,so just do nothing");
            return -1;
        }
    }

    public boolean record_exist()
    {
        if (save_slot!=null)
        {
            if (save_slot.getInt(WsdAudioCardCtrl.SettingsName.END.name(), -1) != -1) {
                Log.e(TAG,"record exist");
                return true;
            }
            Log.e(TAG,"record not exist");
            return false;
        }
        else
        {
            Log.e(TAG,"save_slot is null,record not exist");
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

    public void setToDsp()
    {
        int a,b,c,d,e,f;
        if (dspControl==null)
        {
            Log.e(TAG,"dspControl is null,do nothing");
            return;
        }
        //mic
        a = this.get(WsdAudioCardCtrl.SettingsName.MicInputL.ordinal());
        b = this.get(WsdAudioCardCtrl.SettingsName.MicInputR.ordinal());
        dspControl.micLRLevelSetup(a,b);

        a = this.get(WsdAudioCardCtrl.SettingsName.MicInputGain.ordinal());
        dspControl.micInputLevelSetup(a);

        a = this.get(WsdAudioCardCtrl.SettingsName.MicInputAux.ordinal());
        b = this.get(WsdAudioCardCtrl.SettingsName.MusicInputAux.ordinal());
        dspControl.auxLevelSetup(a,b);

        a = this.get(WsdAudioCardCtrl.SettingsName.BassCutSwitch.ordinal());
        b = this.get(WsdAudioCardCtrl.SettingsName.BassCutFrequency.ordinal());
        dspControl.micBasscutEnable(a);
        dspControl.micBasscutFrequencySetup(b+1);

        a = this.get(WsdAudioCardCtrl.SettingsName.NoiseSuppSwitch.ordinal());
        b = this.get(WsdAudioCardCtrl.SettingsName.NoiseSuppThreshold.ordinal());
        dspControl.micNoiseSuppEnable(a);
        dspControl.micNoiseThresholdSetup(b);

        a = this.get(WsdAudioCardCtrl.SettingsName.CompressorSwitch.ordinal());
        b = this.get(WsdAudioCardCtrl.SettingsName.CompressorSustain.ordinal());
        c = this.get(WsdAudioCardCtrl.SettingsName.CompressorOutLevel.ordinal());
        dspControl.micCompressorEnable(a);
        dspControl.micCompressorSetup(b,c);

        a = this.get(WsdAudioCardCtrl.SettingsName.FrequencyShift.ordinal());
        dspControl.micFrequencyShiftSetup(a-10);

        a = this.get(WsdAudioCardCtrl.SettingsName.MicInputPhaseReverse.ordinal());
        dspControl.micDryOutputSetup(0xff,a);

        a = this.get(WsdAudioCardCtrl.SettingsName.MicPeqSwitch.ordinal());
        dspControl.mic7bandPeqEnable(a);

        a = this.get(WsdAudioCardCtrl.SettingsName.MicPeqBand1Type.ordinal());
        b = this.get(WsdAudioCardCtrl.SettingsName.MicPeqBand7Type.ordinal());
        dspControl.mic7bandPeqEdgeSetup(a,b);

        for (int i=0;i<7;i++) {
            a = this.get(WsdAudioCardCtrl.SettingsName.MicPeqBand1Freq.ordinal()+i);
            b = this.get(WsdAudioCardCtrl.SettingsName.MicPeqBand1Q.ordinal()+i);
            c = this.get(WsdAudioCardCtrl.SettingsName.MicPeqBand1Gain.ordinal()+i);
            dspControl.mic7bandPeqSetup(i+1,a+2,b,c-60);
        }

        //music
        a = this.get(WsdAudioCardCtrl.SettingsName.MusicInputGain.ordinal());
        dspControl.musicInputLevelSetup(a);

        a = this.get(WsdAudioCardCtrl.SettingsName.KeyControlSwitch.ordinal());
        b = this.get(WsdAudioCardCtrl.SettingsName.KeyControlPitch.ordinal());
        c = this.get(WsdAudioCardCtrl.SettingsName.KeyControlPitchFine.ordinal());
        dspControl.musicKeyctrlEnable(a);
        dspControl.musicKeyctrlSetup(b-12,c-50);

        a = this.get(WsdAudioCardCtrl.SettingsName.MusicInputPhaseReverse.ordinal());
        dspControl.musicPhaseSetup(a);

        a = this.get(WsdAudioCardCtrl.SettingsName.MusicGeqSwitch.ordinal());
        dspControl.music31bandGeqEnable(a);

        for (int i=0;i<31;i++) {
            a = this.get(WsdAudioCardCtrl.SettingsName.MusicGeqBand1Gain.ordinal()+i);
            dspControl.music31bandGeqSetup(i+1,a-60);
        }

        //echo
        a = this.get(WsdAudioCardCtrl.SettingsName.EchoInput.ordinal());
        dspControl.echoInputLevelSetup(a);

        a = this.get(WsdAudioCardCtrl.SettingsName.EchoOutput.ordinal());
        b = this.get(WsdAudioCardCtrl.SettingsName.EchoPhaseReverse.ordinal());
        dspControl.echoOutputSetup(a,b);

        a = this.get(WsdAudioCardCtrl.SettingsName.EchoTime.ordinal());
        b = this.get(WsdAudioCardCtrl.SettingsName.EchoLevel.ordinal());
        c = this.get(WsdAudioCardCtrl.SettingsName.EchoFeedbackLevel.ordinal());
        d = this.get(WsdAudioCardCtrl.SettingsName.EchoTone.ordinal());
        e = this.get(WsdAudioCardCtrl.SettingsName.EchoPan.ordinal());
        f = this.get(WsdAudioCardCtrl.SettingsName.EchoPreDelay.ordinal());
        dspControl.echoEffectsSetup(a,b,e,d+57,c,f);

        for (int i=0;i<3;i++) {
            a = this.get(WsdAudioCardCtrl.SettingsName.EchoTap1Time.ordinal()+i);
            b = this.get(WsdAudioCardCtrl.SettingsName.EchoTap1Level.ordinal()+i);
            c = this.get(WsdAudioCardCtrl.SettingsName.EchoTap1Pan.ordinal()+i);
            dspControl.echoTapEffectsSetup(i+1,a,b,c);
        }

        a = this.get(WsdAudioCardCtrl.SettingsName.EchoPeqSwitch.ordinal());
        dspControl.echo5bandPeqEnable(a);

        a = this.get(WsdAudioCardCtrl.SettingsName.EchoPeqBand1Type.ordinal());
        b = this.get(WsdAudioCardCtrl.SettingsName.EchoPeqBand5Type.ordinal());
        dspControl.echo5bandPeqEdgeSetup(a,b);

        for (int i=0;i<5;i++) {
            a = this.get(WsdAudioCardCtrl.SettingsName.EchoPeqBand1Freq.ordinal()+i);
            b = this.get(WsdAudioCardCtrl.SettingsName.EchoPeqBand1Q.ordinal()+i);
            c = this.get(WsdAudioCardCtrl.SettingsName.EchoPeqBand1Gain.ordinal()+i);
            dspControl.echo5bandPeqSetup(i+1,a+2,b,c-60);
        }

        a = this.get(WsdAudioCardCtrl.SettingsName.EchoHighpassFreq.ordinal());
        b = this.get(WsdAudioCardCtrl.SettingsName.EchoHighpassType.ordinal());
        c = this.get(WsdAudioCardCtrl.SettingsName.EchoLowpassFreq.ordinal());
        d = this.get(WsdAudioCardCtrl.SettingsName.EchoLowpassType.ordinal());
        dspControl.echoBandpassfilterSetup(b,a,d,c);

        //reverb
        a = this.get(WsdAudioCardCtrl.SettingsName.ReverbInput.ordinal());
        dspControl.reverbInputLevelSetup(a);

        a = this.get(WsdAudioCardCtrl.SettingsName.ReverbOutput.ordinal());
        b = this.get(WsdAudioCardCtrl.SettingsName.ReverbPhaseReverse.ordinal());
        dspControl.reverbOutputSetup(a,b);

        a = this.get(WsdAudioCardCtrl.SettingsName.ReverbTime.ordinal());
        b = this.get(WsdAudioCardCtrl.SettingsName.ReverbType.ordinal());
        c = this.get(WsdAudioCardCtrl.SettingsName.ReverbTone.ordinal());
        d = this.get(WsdAudioCardCtrl.SettingsName.ReverbPreDelay.ordinal());
        dspControl.reverbEffectsSetup(b,a,c+57,d);

        a = this.get(WsdAudioCardCtrl.SettingsName.EchoToReverb.ordinal());
        dspControl.echoToReverbLevelSetup(a);

        a = this.get(WsdAudioCardCtrl.SettingsName.ReverbPeqSwitch.ordinal());
        dspControl.reverb5bandPeqEnable(a);

        a = this.get(WsdAudioCardCtrl.SettingsName.ReverbPeqBand1Type.ordinal());
        b = this.get(WsdAudioCardCtrl.SettingsName.ReverbPeqBand5Type.ordinal());
        dspControl.reverb5bandPeqEdgeSetup(a,b);

        for (int i=0;i<5;i++) {
            a = this.get(WsdAudioCardCtrl.SettingsName.ReverbPeqBand1Freq.ordinal()+i);
            b = this.get(WsdAudioCardCtrl.SettingsName.ReverbPeqBand1Q.ordinal()+i);
            c = this.get(WsdAudioCardCtrl.SettingsName.ReverbPeqBand1Gain.ordinal()+i);
            dspControl.reverb5bandPeqSetup(i+1,a+2,b,c-60);
        }

        a = this.get(WsdAudioCardCtrl.SettingsName.ReverbHighpassFreq.ordinal());
        b = this.get(WsdAudioCardCtrl.SettingsName.ReverbHighpassType.ordinal());
        c = this.get(WsdAudioCardCtrl.SettingsName.ReverbLowpassFreq.ordinal());
        d = this.get(WsdAudioCardCtrl.SettingsName.ReverbLowpassType.ordinal());
        dspControl.reverbBandpassfilterSetup(b,a,d,c);

        //output
        final int itemPerChannel= WsdAudioCardCtrl.SettingsName.RearOutputPeqBand5Gain.ordinal() - WsdAudioCardCtrl.SettingsName.RearOutputDirect.ordinal() + 1;
        for (int i=0;i<4;i++) {
            a = this.get(WsdAudioCardCtrl.SettingsName.RearOutputDirect.ordinal() + i*itemPerChannel);
            b = this.get(WsdAudioCardCtrl.SettingsName.RearOutputEcho.ordinal() + i*itemPerChannel);
            c = this.get(WsdAudioCardCtrl.SettingsName.RearOutputReverb.ordinal() + i*itemPerChannel);
            d = this.get(WsdAudioCardCtrl.SettingsName.RearOutputMusic.ordinal() + i*itemPerChannel);
            dspControl.mixerMixSetup(i+1,a,b,c,d);

            a = this.get(WsdAudioCardCtrl.SettingsName.RearOutputGain.ordinal() + i*itemPerChannel);
            b = this.get(WsdAudioCardCtrl.SettingsName.RearOutputDelay.ordinal() + i*itemPerChannel);
            c = this.get(WsdAudioCardCtrl.SettingsName.RearOutputPhaseReverse.ordinal() + i*itemPerChannel);
            dspControl.outputSetup(i+1,a,b,c);

            a = this.get(WsdAudioCardCtrl.SettingsName.RearOutputPeqSwitch.ordinal() + i*itemPerChannel);
            dspControl.output5bandPeqEnable(i+1,a);

            a = this.get(WsdAudioCardCtrl.SettingsName.RearOutputPeqBand1Type.ordinal()+ i*itemPerChannel);
            b = this.get(WsdAudioCardCtrl.SettingsName.RearOutputPeqBand5Type.ordinal()+ i*itemPerChannel);
            dspControl.output5bandPeqEdgeSetup(i+1,a,b);

            for (int j=0;j<5;j++) {
                a = this.get(WsdAudioCardCtrl.SettingsName.RearOutputPeqBand1Freq.ordinal() + i*itemPerChannel +j);
                b = this.get(WsdAudioCardCtrl.SettingsName.RearOutputPeqBand1Q.ordinal() + i*itemPerChannel +j);
                c = this.get(WsdAudioCardCtrl.SettingsName.RearOutputPeqBand1Gain.ordinal() + i*itemPerChannel +j);
                dspControl.output5bandPeqSetup(i + 1, j + 1, a + 2, b, c - 60);
            }

            a = this.get(WsdAudioCardCtrl.SettingsName.RearOutputHighpassFreq.ordinal() + i*itemPerChannel);
            b = this.get(WsdAudioCardCtrl.SettingsName.RearOutputHighpassType.ordinal() + i*itemPerChannel);
            c = this.get(WsdAudioCardCtrl.SettingsName.RearOutputLowpassFreq.ordinal() + i*itemPerChannel);
            d = this.get(WsdAudioCardCtrl.SettingsName.RearOutputLowpassType.ordinal() + i*itemPerChannel);
            dspControl.outputBandpassfilterSetup(i+1, b,a,d,c);

        }

        a = this.get(WsdAudioCardCtrl.SettingsName.OutputCompressorSwitch.ordinal());
        dspControl.outputSubwoofferCompressorEnable(a);

        a = this.get(WsdAudioCardCtrl.SettingsName.OutputCompressorAttack.ordinal());
        b = this.get(WsdAudioCardCtrl.SettingsName.OutputCompressorRelease.ordinal());
        c = this.get(WsdAudioCardCtrl.SettingsName.OutputCompressorThreshold.ordinal());
        d = this.get(WsdAudioCardCtrl.SettingsName.OutputCompressorRatio.ordinal());
        e = this.get(WsdAudioCardCtrl.SettingsName.OutputCompressorPostgain.ordinal());
        dspControl.outputSubwoofferCompressorSetup(a,b,c,d,e);
    }

    public void setToDspFast(AudioCardProfile old)
    {
        int a,b,c,d,e,f;
        int a1,b1,c1,d1,e1,f1;
        if (dspControl==null)
        {
            Log.e(TAG,"dspControl is null,do nothing");
            return;
        }
        //mic
        a = this.get(WsdAudioCardCtrl.SettingsName.MicInputL.ordinal());
        b = this.get(WsdAudioCardCtrl.SettingsName.MicInputR.ordinal());
        a1 = old.get(WsdAudioCardCtrl.SettingsName.MicInputL.ordinal());
        b1 = old.get(WsdAudioCardCtrl.SettingsName.MicInputR.ordinal());
        if (a!=a1 || b!=b1) dspControl.micLRLevelSetup(a, b);

        a = this.get(WsdAudioCardCtrl.SettingsName.MicInputGain.ordinal());
        a1 = old.get(WsdAudioCardCtrl.SettingsName.MicInputGain.ordinal());
        if (a!=a1) dspControl.micInputLevelSetup(a);

        a = this.get(WsdAudioCardCtrl.SettingsName.MicInputAux.ordinal());
        b = this.get(WsdAudioCardCtrl.SettingsName.MusicInputAux.ordinal());
        a1 = old.get(WsdAudioCardCtrl.SettingsName.MicInputAux.ordinal());
        b1 = old.get(WsdAudioCardCtrl.SettingsName.MusicInputAux.ordinal());
        if (a!=a1 || b!=b1) dspControl.auxLevelSetup(a, b);

        a = this.get(WsdAudioCardCtrl.SettingsName.BassCutSwitch.ordinal());
        b = this.get(WsdAudioCardCtrl.SettingsName.BassCutFrequency.ordinal());
        a1 = old.get(WsdAudioCardCtrl.SettingsName.BassCutSwitch.ordinal());
        b1 = old.get(WsdAudioCardCtrl.SettingsName.BassCutFrequency.ordinal());
        if (a!=a1) dspControl.micBasscutEnable(a);
        if (b!=b1) dspControl.micBasscutFrequencySetup(b + 1);

        a = this.get(WsdAudioCardCtrl.SettingsName.NoiseSuppSwitch.ordinal());
        b = this.get(WsdAudioCardCtrl.SettingsName.NoiseSuppThreshold.ordinal());
        a1 = old.get(WsdAudioCardCtrl.SettingsName.NoiseSuppSwitch.ordinal());
        b1 = old.get(WsdAudioCardCtrl.SettingsName.NoiseSuppThreshold.ordinal());
        if (a!=a1) dspControl.micNoiseSuppEnable(a);
        if (b!=b1) dspControl.micNoiseThresholdSetup(b);

        a = this.get(WsdAudioCardCtrl.SettingsName.CompressorSwitch.ordinal());
        b = this.get(WsdAudioCardCtrl.SettingsName.CompressorSustain.ordinal());
        c = this.get(WsdAudioCardCtrl.SettingsName.CompressorOutLevel.ordinal());
        a1 = old.get(WsdAudioCardCtrl.SettingsName.CompressorSwitch.ordinal());
        b1 = old.get(WsdAudioCardCtrl.SettingsName.CompressorSustain.ordinal());
        c1 = old.get(WsdAudioCardCtrl.SettingsName.CompressorOutLevel.ordinal());
        if (a!=a1) dspControl.micCompressorEnable(a);
        if (c!=c1 || b!=b1) dspControl.micCompressorSetup(b,c);

        a = this.get(WsdAudioCardCtrl.SettingsName.FrequencyShift.ordinal());
        a1 = old.get(WsdAudioCardCtrl.SettingsName.FrequencyShift.ordinal());
        if (a!=a1) dspControl.micFrequencyShiftSetup(a-10);

        a = this.get(WsdAudioCardCtrl.SettingsName.MicInputPhaseReverse.ordinal());
        a1 = old.get(WsdAudioCardCtrl.SettingsName.MicInputPhaseReverse.ordinal());
        if (a!=a1) dspControl.micDryOutputSetup(0xff,a);

        a = this.get(WsdAudioCardCtrl.SettingsName.MicPeqSwitch.ordinal());
        a1 = old.get(WsdAudioCardCtrl.SettingsName.MicPeqSwitch.ordinal());
        if (a!=a1) dspControl.mic7bandPeqEnable(a);

        a = this.get(WsdAudioCardCtrl.SettingsName.MicPeqBand1Type.ordinal());
        a1 = old.get(WsdAudioCardCtrl.SettingsName.MicPeqBand1Type.ordinal());
        b = this.get(WsdAudioCardCtrl.SettingsName.MicPeqBand7Type.ordinal());
        b1 = old.get(WsdAudioCardCtrl.SettingsName.MicPeqBand7Type.ordinal());
        if (a!=a1||b!=b1) dspControl.mic7bandPeqEdgeSetup(a,b);

        for (int i=0;i<7;i++) {
            a = this.get(WsdAudioCardCtrl.SettingsName.MicPeqBand1Freq.ordinal()+i);
            b = this.get(WsdAudioCardCtrl.SettingsName.MicPeqBand1Q.ordinal()+i);
            c = this.get(WsdAudioCardCtrl.SettingsName.MicPeqBand1Gain.ordinal()+i);
            a1 = old.get(WsdAudioCardCtrl.SettingsName.MicPeqBand1Freq.ordinal()+i);
            b1 = old.get(WsdAudioCardCtrl.SettingsName.MicPeqBand1Q.ordinal()+i);
            c1 = old.get(WsdAudioCardCtrl.SettingsName.MicPeqBand1Gain.ordinal()+i);
            if (c!=c1 || b!=b1 || a!=a1) dspControl.mic7bandPeqSetup(i+1,a+2,b,c-60);
        }

        //music
        a = this.get(WsdAudioCardCtrl.SettingsName.MusicInputGain.ordinal());
        a1 = old.get(WsdAudioCardCtrl.SettingsName.MusicInputGain.ordinal());
        if (a!=a1) dspControl.musicInputLevelSetup(a);

        a = this.get(WsdAudioCardCtrl.SettingsName.KeyControlSwitch.ordinal());
        b = this.get(WsdAudioCardCtrl.SettingsName.KeyControlPitch.ordinal());
        c = this.get(WsdAudioCardCtrl.SettingsName.KeyControlPitchFine.ordinal());
        a1 = old.get(WsdAudioCardCtrl.SettingsName.KeyControlSwitch.ordinal());
        b1 = old.get(WsdAudioCardCtrl.SettingsName.KeyControlPitch.ordinal());
        c1 = old.get(WsdAudioCardCtrl.SettingsName.KeyControlPitchFine.ordinal());
        if (a!=a1) dspControl.musicKeyctrlEnable(a);
        if (c!=c1 || b!=b1) dspControl.musicKeyctrlSetup(b-12,c-50);

        a = this.get(WsdAudioCardCtrl.SettingsName.MusicInputPhaseReverse.ordinal());
        a1 = old.get(WsdAudioCardCtrl.SettingsName.MusicInputPhaseReverse.ordinal());
        if (a!=a1) dspControl.musicPhaseSetup(a);

        a = this.get(WsdAudioCardCtrl.SettingsName.MusicGeqSwitch.ordinal());
        a1 = old.get(WsdAudioCardCtrl.SettingsName.MusicGeqSwitch.ordinal());
        if (a!=a1) dspControl.music31bandGeqEnable(a);

        for (int i=0;i<31;i++) {
            a = this.get(WsdAudioCardCtrl.SettingsName.MusicGeqBand1Gain.ordinal()+i);
            a1 = old.get(WsdAudioCardCtrl.SettingsName.MusicGeqBand1Gain.ordinal()+i);
            if (a!=a1) dspControl.music31bandGeqSetup(i+1,a-60);
        }

        //echo
        a = this.get(WsdAudioCardCtrl.SettingsName.EchoInput.ordinal());
        a1 = old.get(WsdAudioCardCtrl.SettingsName.EchoInput.ordinal());
        if (a!=a1) dspControl.echoInputLevelSetup(a);

        a = this.get(WsdAudioCardCtrl.SettingsName.EchoOutput.ordinal());
        b = this.get(WsdAudioCardCtrl.SettingsName.EchoPhaseReverse.ordinal());
        a1 = old.get(WsdAudioCardCtrl.SettingsName.EchoOutput.ordinal());
        b1 = old.get(WsdAudioCardCtrl.SettingsName.EchoPhaseReverse.ordinal());
        if (a!=a1 || b!=b1) dspControl.echoOutputSetup(a,b);

        a = this.get(WsdAudioCardCtrl.SettingsName.EchoTime.ordinal());
        b = this.get(WsdAudioCardCtrl.SettingsName.EchoLevel.ordinal());
        c = this.get(WsdAudioCardCtrl.SettingsName.EchoFeedbackLevel.ordinal());
        d = this.get(WsdAudioCardCtrl.SettingsName.EchoTone.ordinal());
        e = this.get(WsdAudioCardCtrl.SettingsName.EchoPan.ordinal());
        f = this.get(WsdAudioCardCtrl.SettingsName.EchoPreDelay.ordinal());
        a1 = old.get(WsdAudioCardCtrl.SettingsName.EchoTime.ordinal());
        b1 = old.get(WsdAudioCardCtrl.SettingsName.EchoLevel.ordinal());
        c1 = old.get(WsdAudioCardCtrl.SettingsName.EchoFeedbackLevel.ordinal());
        d1 = old.get(WsdAudioCardCtrl.SettingsName.EchoTone.ordinal());
        e1 = old.get(WsdAudioCardCtrl.SettingsName.EchoPan.ordinal());
        f1 = old.get(WsdAudioCardCtrl.SettingsName.EchoPreDelay.ordinal());
        if (a!=a1||b!=b1||c!=c1||d!=d1||e!=e1||f!=f1) dspControl.echoEffectsSetup(a,b,e,d+57,c,f);

        for (int i=0;i<3;i++) {
            a = this.get(WsdAudioCardCtrl.SettingsName.EchoTap1Time.ordinal()+i);
            b = this.get(WsdAudioCardCtrl.SettingsName.EchoTap1Level.ordinal()+i);
            c = this.get(WsdAudioCardCtrl.SettingsName.EchoTap1Pan.ordinal()+i);
            a1 = old.get(WsdAudioCardCtrl.SettingsName.EchoTap1Time.ordinal()+i);
            b1 = old.get(WsdAudioCardCtrl.SettingsName.EchoTap1Level.ordinal()+i);
            c1 = old.get(WsdAudioCardCtrl.SettingsName.EchoTap1Pan.ordinal()+i);
            if (a!=a1||b!=b1||c!=c1) dspControl.echoTapEffectsSetup(i+1,a,b,c);
        }

        a = this.get(WsdAudioCardCtrl.SettingsName.EchoPeqSwitch.ordinal());
        a1 = old.get(WsdAudioCardCtrl.SettingsName.EchoPeqSwitch.ordinal());
        if (a!=a1) dspControl.echo5bandPeqEnable(a);

        a = this.get(WsdAudioCardCtrl.SettingsName.EchoPeqBand1Type.ordinal());
        a1 = old.get(WsdAudioCardCtrl.SettingsName.EchoPeqBand1Type.ordinal());
        b = this.get(WsdAudioCardCtrl.SettingsName.EchoPeqBand5Type.ordinal());
        b1 = old.get(WsdAudioCardCtrl.SettingsName.EchoPeqBand5Type.ordinal());
        if (a!=a1||b!=b1) dspControl.echo5bandPeqEdgeSetup(a,b);

        for (int i=0;i<5;i++) {
            a = this.get(WsdAudioCardCtrl.SettingsName.EchoPeqBand1Freq.ordinal()+i);
            b = this.get(WsdAudioCardCtrl.SettingsName.EchoPeqBand1Q.ordinal()+i);
            c = this.get(WsdAudioCardCtrl.SettingsName.EchoPeqBand1Gain.ordinal()+i);
            a1 = old.get(WsdAudioCardCtrl.SettingsName.EchoPeqBand1Freq.ordinal()+i);
            b1 = old.get(WsdAudioCardCtrl.SettingsName.EchoPeqBand1Q.ordinal()+i);
            c1 = old.get(WsdAudioCardCtrl.SettingsName.EchoPeqBand1Gain.ordinal()+i);
            if (a!=a1||b!=b1||c!=c1) dspControl.echo5bandPeqSetup(i+1,a+2,b,c-60);
        }

        a = this.get(WsdAudioCardCtrl.SettingsName.EchoHighpassFreq.ordinal());
        b = this.get(WsdAudioCardCtrl.SettingsName.EchoHighpassType.ordinal());
        c = this.get(WsdAudioCardCtrl.SettingsName.EchoLowpassFreq.ordinal());
        d = this.get(WsdAudioCardCtrl.SettingsName.EchoLowpassType.ordinal());
        a1 = old.get(WsdAudioCardCtrl.SettingsName.EchoHighpassFreq.ordinal());
        b1 = old.get(WsdAudioCardCtrl.SettingsName.EchoHighpassType.ordinal());
        c1 = old.get(WsdAudioCardCtrl.SettingsName.EchoLowpassFreq.ordinal());
        d1 = old.get(WsdAudioCardCtrl.SettingsName.EchoLowpassType.ordinal());
        if (a!=a1||b!=b1||c!=c1||d!=d1) dspControl.echoBandpassfilterSetup(b,a,d,c);

        //reverb
        a = this.get(WsdAudioCardCtrl.SettingsName.ReverbInput.ordinal());
        a1 = old.get(WsdAudioCardCtrl.SettingsName.ReverbInput.ordinal());
        if (a!=a1) dspControl.reverbInputLevelSetup(a);

        a = this.get(WsdAudioCardCtrl.SettingsName.ReverbOutput.ordinal());
        b = this.get(WsdAudioCardCtrl.SettingsName.ReverbPhaseReverse.ordinal());
        a1 = old.get(WsdAudioCardCtrl.SettingsName.ReverbOutput.ordinal());
        b1 = old.get(WsdAudioCardCtrl.SettingsName.ReverbPhaseReverse.ordinal());
        if (a!=a1||b!=b1) dspControl.reverbOutputSetup(a,b);

        a = this.get(WsdAudioCardCtrl.SettingsName.ReverbTime.ordinal());
        b = this.get(WsdAudioCardCtrl.SettingsName.ReverbType.ordinal());
        c = this.get(WsdAudioCardCtrl.SettingsName.ReverbTone.ordinal());
        d = this.get(WsdAudioCardCtrl.SettingsName.ReverbPreDelay.ordinal());
        a1 = old.get(WsdAudioCardCtrl.SettingsName.ReverbTime.ordinal());
        b1 = old.get(WsdAudioCardCtrl.SettingsName.ReverbType.ordinal());
        c1 = old.get(WsdAudioCardCtrl.SettingsName.ReverbTone.ordinal());
        d1 = old.get(WsdAudioCardCtrl.SettingsName.ReverbPreDelay.ordinal());
        if (a!=a1||b!=b1||c!=c1||d!=d1) dspControl.reverbEffectsSetup(b,a,c+57,d);

        a = this.get(WsdAudioCardCtrl.SettingsName.EchoToReverb.ordinal());
        a1 = old.get(WsdAudioCardCtrl.SettingsName.EchoToReverb.ordinal());
        if (a!=a1) dspControl.echoToReverbLevelSetup(a);

        a = this.get(WsdAudioCardCtrl.SettingsName.ReverbPeqSwitch.ordinal());
        a1 = old.get(WsdAudioCardCtrl.SettingsName.ReverbPeqSwitch.ordinal());
        if (a!=a1) dspControl.reverb5bandPeqEnable(a);

        a = this.get(WsdAudioCardCtrl.SettingsName.ReverbPeqBand1Type.ordinal());
        a1 = old.get(WsdAudioCardCtrl.SettingsName.ReverbPeqBand1Type.ordinal());
        b = this.get(WsdAudioCardCtrl.SettingsName.ReverbPeqBand5Type.ordinal());
        b1 = old.get(WsdAudioCardCtrl.SettingsName.ReverbPeqBand5Type.ordinal());
        if (a!=a1||b!=b1) dspControl.reverb5bandPeqEdgeSetup(a,b);

        for (int i=0;i<5;i++) {
            a = this.get(WsdAudioCardCtrl.SettingsName.ReverbPeqBand1Freq.ordinal()+i);
            b = this.get(WsdAudioCardCtrl.SettingsName.ReverbPeqBand1Q.ordinal()+i);
            c = this.get(WsdAudioCardCtrl.SettingsName.ReverbPeqBand1Gain.ordinal()+i);
            a1 = old.get(WsdAudioCardCtrl.SettingsName.ReverbPeqBand1Freq.ordinal()+i);
            b1 = old.get(WsdAudioCardCtrl.SettingsName.ReverbPeqBand1Q.ordinal()+i);
            c1 = old.get(WsdAudioCardCtrl.SettingsName.ReverbPeqBand1Gain.ordinal()+i);
            if (a!=a1||b!=b1||c!=c1) dspControl.reverb5bandPeqSetup(i+1,a+2,b,c-60);
        }

        a = this.get(WsdAudioCardCtrl.SettingsName.ReverbHighpassFreq.ordinal());
        b = this.get(WsdAudioCardCtrl.SettingsName.ReverbHighpassType.ordinal());
        c = this.get(WsdAudioCardCtrl.SettingsName.ReverbLowpassFreq.ordinal());
        d = this.get(WsdAudioCardCtrl.SettingsName.ReverbLowpassType.ordinal());
        a1 = old.get(WsdAudioCardCtrl.SettingsName.ReverbHighpassFreq.ordinal());
        b1 = old.get(WsdAudioCardCtrl.SettingsName.ReverbHighpassType.ordinal());
        c1 = old.get(WsdAudioCardCtrl.SettingsName.ReverbLowpassFreq.ordinal());
        d1 = old.get(WsdAudioCardCtrl.SettingsName.ReverbLowpassType.ordinal());
        if (a!=a1||b!=b1||c!=c1||d!=d1) dspControl.reverbBandpassfilterSetup(b,a,d,c);

        //output
        final int itemPerChannel= WsdAudioCardCtrl.SettingsName.RearOutputPeqBand5Gain.ordinal() - WsdAudioCardCtrl.SettingsName.RearOutputDirect.ordinal() + 1;
        for (int i=0;i<4;i++) {
            a = this.get(WsdAudioCardCtrl.SettingsName.RearOutputDirect.ordinal() + i*itemPerChannel);
            b = this.get(WsdAudioCardCtrl.SettingsName.RearOutputEcho.ordinal() + i*itemPerChannel);
            c = this.get(WsdAudioCardCtrl.SettingsName.RearOutputReverb.ordinal() + i*itemPerChannel);
            d = this.get(WsdAudioCardCtrl.SettingsName.RearOutputMusic.ordinal() + i*itemPerChannel);
            a1 = old.get(WsdAudioCardCtrl.SettingsName.RearOutputDirect.ordinal() + i*itemPerChannel);
            b1 = old.get(WsdAudioCardCtrl.SettingsName.RearOutputEcho.ordinal() + i*itemPerChannel);
            c1 = old.get(WsdAudioCardCtrl.SettingsName.RearOutputReverb.ordinal() + i*itemPerChannel);
            d1 = old.get(WsdAudioCardCtrl.SettingsName.RearOutputMusic.ordinal() + i*itemPerChannel);
            if (a!=a1||b!=b1||c!=c1||d!=d1) dspControl.mixerMixSetup(i+1,a,b,c,d);

            a = this.get(WsdAudioCardCtrl.SettingsName.RearOutputGain.ordinal() + i*itemPerChannel);
            b = this.get(WsdAudioCardCtrl.SettingsName.RearOutputDelay.ordinal() + i*itemPerChannel);
            c = this.get(WsdAudioCardCtrl.SettingsName.RearOutputPhaseReverse.ordinal() + i*itemPerChannel);
            a1 = old.get(WsdAudioCardCtrl.SettingsName.RearOutputGain.ordinal() + i*itemPerChannel);
            b1 = old.get(WsdAudioCardCtrl.SettingsName.RearOutputDelay.ordinal() + i*itemPerChannel);
            c1 = old.get(WsdAudioCardCtrl.SettingsName.RearOutputPhaseReverse.ordinal() + i*itemPerChannel);
            if (a!=a1||b!=b1||c!=c1) dspControl.outputSetup(i+1,a,b,c);

            a = this.get(WsdAudioCardCtrl.SettingsName.RearOutputPeqSwitch.ordinal() + i*itemPerChannel);
            a1 = old.get(WsdAudioCardCtrl.SettingsName.RearOutputPeqSwitch.ordinal() + i*itemPerChannel);
            if (a!=a1) dspControl.output5bandPeqEnable(i+1,a);

            a = this.get(WsdAudioCardCtrl.SettingsName.RearOutputPeqBand1Type.ordinal() + i*itemPerChannel);
            a1 = old.get(WsdAudioCardCtrl.SettingsName.RearOutputPeqBand1Type.ordinal() + i*itemPerChannel);
            b = this.get(WsdAudioCardCtrl.SettingsName.RearOutputPeqBand5Type.ordinal() + i*itemPerChannel);
            b1 = old.get(WsdAudioCardCtrl.SettingsName.RearOutputPeqBand5Type.ordinal() + i*itemPerChannel);
            if(a!=a1||b!=b1) dspControl.output5bandPeqEdgeSetup(i+1,a,b);

            for (int j=0;j<5;j++) {
                a = this.get(WsdAudioCardCtrl.SettingsName.RearOutputPeqBand1Freq.ordinal() + i*itemPerChannel +j);
                b = this.get(WsdAudioCardCtrl.SettingsName.RearOutputPeqBand1Q.ordinal() + i*itemPerChannel +j);
                c = this.get(WsdAudioCardCtrl.SettingsName.RearOutputPeqBand1Gain.ordinal() + i*itemPerChannel +j);
                a1 = old.get(WsdAudioCardCtrl.SettingsName.RearOutputPeqBand1Freq.ordinal() + i*itemPerChannel +j);
                b1 = old.get(WsdAudioCardCtrl.SettingsName.RearOutputPeqBand1Q.ordinal() + i*itemPerChannel +j);
                c1 = old.get(WsdAudioCardCtrl.SettingsName.RearOutputPeqBand1Gain.ordinal() + i*itemPerChannel +j);
                if (a!=a1||b!=b1||c!=c1) dspControl.output5bandPeqSetup(i + 1, j + 1, a + 2, b, c - 60);
            }

            a = this.get(WsdAudioCardCtrl.SettingsName.RearOutputHighpassFreq.ordinal() + i*itemPerChannel);
            b = this.get(WsdAudioCardCtrl.SettingsName.RearOutputHighpassType.ordinal() + i*itemPerChannel);
            c = this.get(WsdAudioCardCtrl.SettingsName.RearOutputLowpassFreq.ordinal() + i*itemPerChannel);
            d = this.get(WsdAudioCardCtrl.SettingsName.RearOutputLowpassType.ordinal() + i*itemPerChannel);
            a1 = old.get(WsdAudioCardCtrl.SettingsName.RearOutputHighpassFreq.ordinal() + i*itemPerChannel);
            b1 = old.get(WsdAudioCardCtrl.SettingsName.RearOutputHighpassType.ordinal() + i*itemPerChannel);
            c1 = old.get(WsdAudioCardCtrl.SettingsName.RearOutputLowpassFreq.ordinal() + i*itemPerChannel);
            d1 = old.get(WsdAudioCardCtrl.SettingsName.RearOutputLowpassType.ordinal() + i*itemPerChannel);
            if (a!=a1||b!=b1||c!=c1||d!=d1) dspControl.outputBandpassfilterSetup(i+1, b,a,d,c);

        }

        a = this.get(WsdAudioCardCtrl.SettingsName.OutputCompressorSwitch.ordinal());
        a1 = old.get(WsdAudioCardCtrl.SettingsName.OutputCompressorSwitch.ordinal());
        if (a!=a1)dspControl.outputSubwoofferCompressorEnable(a);

        a = this.get(WsdAudioCardCtrl.SettingsName.OutputCompressorAttack.ordinal());
        b = this.get(WsdAudioCardCtrl.SettingsName.OutputCompressorRelease.ordinal());
        c = this.get(WsdAudioCardCtrl.SettingsName.OutputCompressorThreshold.ordinal());
        d = this.get(WsdAudioCardCtrl.SettingsName.OutputCompressorRatio.ordinal());
        e = this.get(WsdAudioCardCtrl.SettingsName.OutputCompressorPostgain.ordinal());
        a1 = old.get(WsdAudioCardCtrl.SettingsName.OutputCompressorAttack.ordinal());
        b1 = old.get(WsdAudioCardCtrl.SettingsName.OutputCompressorRelease.ordinal());
        c1 = old.get(WsdAudioCardCtrl.SettingsName.OutputCompressorThreshold.ordinal());
        d1 = old.get(WsdAudioCardCtrl.SettingsName.OutputCompressorRatio.ordinal());
        e1 = old.get(WsdAudioCardCtrl.SettingsName.OutputCompressorPostgain.ordinal());
        if (a!=a1||b!=b1||c!=c1||d!=d1||e!=e1) dspControl.outputSubwoofferCompressorSetup(a,b,c,d,e);
    }

    public void syncSettingsWithDsp()
    {
        if (dspControl!=null)
        {
            for (WsdAudioCardCtrl.SettingsName set : WsdAudioCardCtrl.SettingsName.values()) {
                if (set.ordinal()!=WsdAudioCardCtrl.SettingsName.END.ordinal()) {
                    int tmp = dspControl.getValueOfSetting(set);
                    if (tmp!=255) {
                        mSavedSettings[set.ordinal()] = tmp;
                        //Log.e(TAG,"[from DSP]"+  set.name() + "=" +  mSavedSettings[set.ordinal()]);
                    } else {
                        Log.e(TAG,"[from DSP] could not get value of"+  set.name());
                    }
                }
            }
        }
        else
        {
            Log.e(TAG,"dspControl is null,so just do nothing");
        }
    }
}
