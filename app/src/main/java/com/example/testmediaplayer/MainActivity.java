package com.example.testmediaplayer;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;
import com.wsd.android.WsdAudioCardCtrl;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.graphics.PixelFormat;
import android.media.AudioFormat;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnErrorListener;   
import android.media.MediaRecorder;
import android.media.AudioManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.os.Parcel;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;


@TargetApi(16)
public class MainActivity extends Activity implements
        OnTouchListener,
        OnClickListener,
        OnCompletionListener,
        OnErrorListener {

    private static final String TAG = "TESTMEDIAPLAYER";

    private String active_usb_storage_path = "/mnt/sda/sda1";
    private String active_data_storage_path = "/sdcard";
    private static String mRecordFile = "/myrecord.wav";
    private static String RecTempFile = "/temp.raw";

    private SurfaceView mMediaPlayerView = null;
    private MediaPlayer mMediaPlayer = null;

    private TextView mSongNameView = null;
    private TextView msongsName = null;

    private List<String> videoPathList = null;

    private Button mPlayBtn = null;
    private Button mCircelBtn = null;
    private Button mSuspendBtn = null;
    private Button mStopBtn = null;
    private Button mMuteSwitchBtn = null;

    private WavRecorder mWavRecorder = null;

    private Button mRecordStartBtn = null;
    private Button mRecordStopBtn = null;
    private Button mRecordPlayBtn = null;
    private Button mRecordPlayLeftBtn = null;
    private Button mRecordPlayRightBtn = null;
    private Button mRecordSaveBtn = null;
    private ProgressDialog mRecordSaveProgress;
    private ProgressDialog mUsbSaveProgress;

    // volume
    private int mSelectedAudioTrackIndex = 0;

    // volume : 0~100
    private int mDefaultMusicVolume = 50;

    // video index
    private int videoIndex = 0;

    // mute
    private int mMute = 0;
    private int mVolumeBeforeMute = 0;

    private boolean tpause = true;

    // record
    private int mRecordStartStop = 0;

    private WsdAudioCardCtrl mAudioCardCtrl;
    private AudioCardProfile mCardSettings;
    private AudioCardProfile mCardProfile;
    private AudioCardProfile mCardProfile1;
    private AudioCardProfile mCardProfile2;
    private AudioCardProfile mCardProfile3;
    private AudioCardProfile mCardProfile4;
    private AudioCardProfile mCardProfileProfessional;
    private AudioCardProfile mCardProfileStandard;
    private AudioCardProfile mCardProfileRecording;
    private AudioCardProfile mCardProfileEnjoy;

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setVolumeControlStream(AudioManager.STREAM_MUSIC);
        AudioManager audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, 15, AudioManager.FLAG_REMOVE_SOUND_AND_VIBRATE);

        mMediaPlayerView = (SurfaceView) findViewById(R.id.mediaPlayerView);
//        mMediaPlayerView.setOnClickListener(this);

        mSongNameView = (TextView) this.findViewById(R.id.SongNameView);
        msongsName = (TextView) this.findViewById(R.id.songsName);

        mPlayBtn = (Button) this.findViewById(R.id.PlayBtn);
        mPlayBtn.setOnClickListener(this);

        mCircelBtn = (Button) this.findViewById(R.id.CircelBtn);
        mCircelBtn.setOnClickListener(this);

        mSuspendBtn = (Button) this.findViewById(R.id.SuspendBtn);
        mSuspendBtn.setOnClickListener(this);

        mStopBtn = (Button) this.findViewById(R.id.StopBtn);
        mStopBtn.setOnClickListener(this);

        mMuteSwitchBtn = (Button) this.findViewById(R.id.MuteSwitchBtn);
        mMuteSwitchBtn.setOnClickListener(this);

        mRecordStartBtn = (Button) this.findViewById(R.id.RecordStartBtn);
        mRecordStartBtn.setOnClickListener(this);

        mRecordStopBtn = (Button) this.findViewById(R.id.RecordStopBtn);
        mRecordStopBtn.setOnClickListener(this);

        mRecordPlayBtn = (Button) this.findViewById(R.id.RecordPlayBtn);
        mRecordPlayBtn.setOnClickListener(this);

        mRecordPlayLeftBtn = (Button) this.findViewById(R.id.btn_rec_play_L);
        mRecordPlayLeftBtn.setOnClickListener(this);

        mRecordSaveBtn = (Button) this.findViewById(R.id.btn_rec_save);
        mRecordSaveBtn.setOnClickListener(this);

        mRecordPlayRightBtn = (Button) this.findViewById(R.id.btn_rec_play_R);
        mRecordPlayRightBtn.setOnClickListener(this);

        getVideoPathFromUSB();

        active_data_storage_path = Environment.getExternalStorageDirectory().getPath();

        Context ctx = this;
        SharedPreferences settings = ctx.getSharedPreferences("SETTINGS", MODE_PRIVATE);
        SharedPreferences profile = ctx.getSharedPreferences("PROFILE", MODE_PRIVATE);
        SharedPreferences profile1 = ctx.getSharedPreferences("PROFILE1", MODE_PRIVATE);
        SharedPreferences profile2 = ctx.getSharedPreferences("PROFILE2", MODE_PRIVATE);
        SharedPreferences profile3 = ctx.getSharedPreferences("PROFILE3", MODE_PRIVATE);
        SharedPreferences profile4 = ctx.getSharedPreferences("PROFILE4", MODE_PRIVATE);
        SharedPreferences profileProfessional = ctx.getSharedPreferences("PROFESSIONAL", MODE_PRIVATE);
        SharedPreferences profileStandard = ctx.getSharedPreferences("STANDARD", MODE_PRIVATE);
        SharedPreferences profileRecording = ctx.getSharedPreferences("RECORDING", MODE_PRIVATE);
        SharedPreferences profileEnjoy = ctx.getSharedPreferences("ENJOY", MODE_PRIVATE);

        mAudioCardCtrl = new WsdAudioCardCtrl();
        mAudioCardCtrl.audioCardInit();
        mCardSettings = new AudioCardProfile(settings,mAudioCardCtrl);
        mCardProfile = new AudioCardProfile(profile,mAudioCardCtrl);
        mCardProfile1 = new AudioCardProfile(profile1,mAudioCardCtrl);
        mCardProfile2 = new AudioCardProfile(profile2,mAudioCardCtrl);
        mCardProfile3 = new AudioCardProfile(profile3,mAudioCardCtrl);
        mCardProfile4 = new AudioCardProfile(profile4,mAudioCardCtrl);
        mCardProfileProfessional = new AudioCardProfile(profileProfessional,mAudioCardCtrl);
        mCardProfileStandard = new AudioCardProfile(profileStandard,mAudioCardCtrl);
        mCardProfileRecording = new AudioCardProfile(profileRecording,mAudioCardCtrl);
        mCardProfileEnjoy = new AudioCardProfile(profileEnjoy,mAudioCardCtrl);

        mCardSettings.syncSettingsWithDsp();

        setupMicInputPanel();
        setupMusicInputPanel();
        setupReverbPanel();
        setupEchoPanel();
        setupOutputPanel();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    private static final float mixerDbMap[] = {-45.0f,-42.1f,-36.1f,-32.5f,-30.0f,-28.1f,-26.5f,-25.2f,
            -24.0f,-23.0f,-22.1f,-21.2f,-20.5f,-19.8f,-19.2f,-18.6f,
            -18.0f,-17.5f,-17.0f,-16.5f,-16.1f,-15.6f,-15.2f,-14.8f,
            -14.5f,-14.1f,-13.8f,-13.4f,-13.1f,-12.8f,-12.5f,-12.2f,
            -12.0f,-11.7f,-11.4f,-11.2f,-11.0f,-10.7f,-10.5f,-10.3f,
            -10.0f,-9.82f,-9.61f,-9.41f,-9.21f,-9.01f,-8.82f,-8.63f,
            -8.45f,-8.27f,-8.10f,-7.92f,-7.76f,-7.59f,-7.43f,-7.27f,
            -7.11f,-6.96f,-6.81f,-6.66f,-6.51f,-6.37f,-6.23f,-6.09f,
            -5.95f,-5.82f,-5.69f,-5.55f,-5.43f,-5.30f,-5.17f,-5.05f,
            -4.93f,-4.81f,-4.69f,-4.57f,-4.46f,-4.35f,-4.23f,-4.12f,
            -4.01f,-3.91f,-3.80f,-3.69f,-3.59f,-3.49f,-3.39f,-3.29f,
            -3.19f,-3.09f,-2.99f,-2.90f,-2.80f,-2.71f,-2.61f,-2.52f,
            -2.43f,-2.34f,-2.25f,-2.16f,-2.08f,-1.99f,-1.90f,-1.82f,
            -1.74f,-1.65f,-1.57f,-1.49f,-1.41f,-1.33f,-1.25f,-1.17f,
            -1.09f,-1.01f,-0.94f,-0.86f,-0.79f,-0.71f,-0.64f,-0.57f,
            -0.49f,-0.42f,-0.35f,-0.28f,-0.21f,-0.14f,-0.07f,0.00f};

    private static final String hzMap[] = {"20","22","25","28","32","36","40","45","50","56",
            "63","71","80","90","100","112","125","140","160","180",
            "200","224","250","280","315","355","400","450","500","560",
            "630","710","800","900","1.0k","1.12k","1.25k","1.4k","1.6k","1.8k",
            "2.0k","2.24k","2.5k","2.8k","3.15k","3.55k","4.0k","4.5k","5.0k","5.6k",
            "6.3k","7.1k","8.0","9.0k","10.0k","11.2k","12.5k","14.0k","16.0k","18.0k","20.0k"
    };

    private static final String qMap[] = {"0.3","0.4","0.5","0.6","0.7","0.8","0.9","1.0","1.1","1.2",
            "1.4","1.6","1.8","2.0","2.2","2.5","2.8","3.1","3.5","4.0",
            "4.5","5.0","5.6","6.3","7.1","8.0","9.0","10.0","11.2","12.5",
            "14.0","16.0"
    };

    private static final String highCutFreqMap[] = {"Flat","20.0Hz","21.0Hz","22.0Hz","23.5Hz","25.0Hz","26.5Hz","28.0Hz","30.0Hz","31.5Hz",
            "33.5Hz","35.0Hz","37.5Hz","40.0Hz","42.0Hz","45.0Hz","47.0Hz","50.0Hz","53.0Hz","56.0Hz",
            "60.0Hz","63.0Hz","66.0Hz","70.0Hz","75.0Hz","80.0Hz","85.0Hz","90.0Hz","95.0Hz","100Hz",
            "105Hz","112Hz","120Hz","125Hz","130Hz","140Hz","150Hz","160Hz","170Hz","180Hz",
            "190Hz","200Hz","210Hz","220Hz","235Hz","250Hz","265Hz","280Hz","300Hz","315Hz",
            "335Hz","350Hz","375Hz","400Hz","420Hz","450Hz","470Hz","500Hz","530Hz","560Hz",
            "600Hz","630Hz","660Hz","700Hz","750Hz","800Hz","850Hz","900Hz","950Hz","1.00kHz"};

    private static final String lowCutFreqMap[] = {"35.0Hz","37.5Hz","40.0Hz","42.0Hz","45.0Hz","47.0Hz","50.0Hz","53.0Hz","56.0Hz","60.0Hz",
            "63.0Hz","66.0Hz","70.0Hz","75.0Hz","80.0Hz","85.0Hz","90.0Hz","95.0Hz","100Hz","105Hz",
            "112Hz","120Hz","125Hz","130Hz","140Hz","150Hz","160Hz","170Hz","180Hz","190Hz",
            "200Hz","210Hz","220Hz","235Hz","250Hz","265Hz","280Hz","300Hz","315Hz","335Hz",
            "350Hz","375Hz","400Hz","420Hz","450Hz","470Hz","500Hz","530Hz","560Hz","600Hz",
            "630Hz","660Hz","700Hz","750Hz","800Hz","850Hz","900Hz","950Hz","1.00kHz","1.05kHz",
            "1.12kHz","1.20kHz","1.25kHz","1.30kHz","1.40kHz","1.50kHz","1.60kHz","1.70kHz","1.80kHz","1.90kHz",
            "2.00kHz","2.10kHz","2.20kHz","2.35kHz","2.50kHz","2.65kHz","2.80kHz","3.00kHz","3.15kHz","3.35kHz",
            "3.50kHz","3.75kHz","4.00kHz","4.20kHz","4.50kHz","4.70kHz","5.00kHz","5.30kHz","5.60kHz","6.00kHz",
            "6.30kHz","6.60kHz","7.00kHz","7.50kHz","8.00kHz","8.50kHz","9.00kHz","9.50kHz","10.0kHz","10.5kHz",
            "11.2kHz","12.0kHz","12.5kHz","13.0kHz","14.0kHz","15.0kHz","16.0kHz","17.0kHz","18.0kHz","19.0kHz",
            "20.0kHz","Flat"};

    private static final String cutTypeMap[] = {"Btrwth-6dB","Bessel-12dB","Btrwth-12dB","Bessel-18dB","Btrwth-18dB","Bessel-24dB","Btrwth-24dB","Lw-Rly-24dB","Bessel-48dB","Btrwth-48dB","Lw-Rly-48dB"};

    private static final String compressorRatioMap[] = {"1.00->1","1.12->1","1.25->1","1.40->1","1.60->1","1.80->1","2.00->1","2.50->1","3.20->1","4.00->1","5.60->1","8.00->1","16,0->1","infin->1"};

    private int[] micPeqFreqTextViewID = new int[]
            {       R.id.MicPeqBand1FreqTextView,R.id.MicPeqBand2FreqTextView, R.id.MicPeqBand3FreqTextView,R.id.MicPeqBand4FreqTextView,R.id.MicPeqBand5FreqTextView,R.id.MicPeqBand6FreqTextView,
                    R.id.MicPeqBand7FreqTextView
            };

    private int[] micPeqFreqSeekBarID = new int[]
            {       R.id.MicPeqBand1FreqSeekBar,R.id.MicPeqBand2FreqSeekBar, R.id.MicPeqBand3FreqSeekBar,R.id.MicPeqBand4FreqSeekBar,R.id.MicPeqBand5FreqSeekBar,R.id.MicPeqBand6FreqSeekBar,
                    R.id.MicPeqBand7FreqSeekBar
            };
    private TextView[] micPeqFreqTextView = new TextView[7];
    private SeekBar[] micPeqFreqSeekBar = new SeekBar[7];

    private int[] micPeqQTextViewID = new int[]
            {       R.id.MicPeqBand1QTextView,R.id.MicPeqBand2QTextView, R.id.MicPeqBand3QTextView,R.id.MicPeqBand4QTextView,R.id.MicPeqBand5QTextView,R.id.MicPeqBand6QTextView,
                    R.id.MicPeqBand7QTextView
            };

    private int[] micPeqQSeekBarID = new int[]
            {       R.id.MicPeqBand1QSeekBar,R.id.MicPeqBand2QSeekBar, R.id.MicPeqBand3QSeekBar,R.id.MicPeqBand4QSeekBar,R.id.MicPeqBand5QSeekBar,R.id.MicPeqBand6QSeekBar,
                    R.id.MicPeqBand7QSeekBar
            };
    private TextView[] micPeqQTextView = new TextView[7];
    private SeekBar[] micPeqQSeekBar = new SeekBar[7];

    private int[] micPeqGainTextViewID = new int[]
            {       R.id.MicPeqBand1GainTextView,R.id.MicPeqBand2GainTextView, R.id.MicPeqBand3GainTextView,R.id.MicPeqBand4GainTextView,R.id.MicPeqBand5GainTextView,R.id.MicPeqBand6GainTextView,
                    R.id.MicPeqBand7GainTextView
            };

    private int[] micPeqGainSeekBarID = new int[]
            {       R.id.MicPeqBand1GainSeekBar,R.id.MicPeqBand2GainSeekBar, R.id.MicPeqBand3GainSeekBar,R.id.MicPeqBand4GainSeekBar,R.id.MicPeqBand5GainSeekBar,R.id.MicPeqBand6GainSeekBar,
                    R.id.MicPeqBand7GainSeekBar
            };
    private TextView[] micPeqGainTextView = new TextView[7];
    private SeekBar[] micPeqGainSeekBar = new SeekBar[7];


    private int[] musicGeqTextViewID = new int[]
            {       R.id.MusicGeqBand1GainTextView,R.id.MusicGeqBand2GainTextView, R.id.MusicGeqBand3GainTextView,R.id.MusicGeqBand4GainTextView,R.id.MusicGeqBand5GainTextView,R.id.MusicGeqBand6GainTextView,
                    R.id.MusicGeqBand7GainTextView,R.id.MusicGeqBand8GainTextView, R.id.MusicGeqBand9GainTextView,R.id.MusicGeqBand10GainTextView,R.id.MusicGeqBand11GainTextView,R.id.MusicGeqBand12GainTextView,
                    R.id.MusicGeqBand13GainTextView,R.id.MusicGeqBand14GainTextView, R.id.MusicGeqBand15GainTextView,R.id.MusicGeqBand16GainTextView,R.id.MusicGeqBand17GainTextView,R.id.MusicGeqBand18GainTextView,
                    R.id.MusicGeqBand19GainTextView,R.id.MusicGeqBand20GainTextView, R.id.MusicGeqBand21GainTextView,R.id.MusicGeqBand22GainTextView,R.id.MusicGeqBand23GainTextView,R.id.MusicGeqBand24GainTextView,
                    R.id.MusicGeqBand25GainTextView,R.id.MusicGeqBand26GainTextView, R.id.MusicGeqBand27GainTextView,R.id.MusicGeqBand28GainTextView,R.id.MusicGeqBand29GainTextView,R.id.MusicGeqBand30GainTextView,R.id.MusicGeqBand31GainTextView
            };

    private int[] musicGeqSeekBarID = new int[]
            {       R.id.MusicGeqBand1GainSeekBar,R.id.MusicGeqBand2GainSeekBar, R.id.MusicGeqBand3GainSeekBar,R.id.MusicGeqBand4GainSeekBar,R.id.MusicGeqBand5GainSeekBar,R.id.MusicGeqBand6GainSeekBar,
                    R.id.MusicGeqBand7GainSeekBar,R.id.MusicGeqBand8GainSeekBar, R.id.MusicGeqBand9GainSeekBar,R.id.MusicGeqBand10GainSeekBar,R.id.MusicGeqBand11GainSeekBar,R.id.MusicGeqBand12GainSeekBar,
                    R.id.MusicGeqBand13GainSeekBar,R.id.MusicGeqBand14GainSeekBar, R.id.MusicGeqBand15GainSeekBar,R.id.MusicGeqBand16GainSeekBar,R.id.MusicGeqBand17GainSeekBar,R.id.MusicGeqBand18GainSeekBar,
                    R.id.MusicGeqBand19GainSeekBar,R.id.MusicGeqBand20GainSeekBar, R.id.MusicGeqBand21GainSeekBar,R.id.MusicGeqBand22GainSeekBar,R.id.MusicGeqBand23GainSeekBar,R.id.MusicGeqBand24GainSeekBar,
                    R.id.MusicGeqBand25GainSeekBar,R.id.MusicGeqBand26GainSeekBar, R.id.MusicGeqBand27GainSeekBar,R.id.MusicGeqBand28GainSeekBar,R.id.MusicGeqBand29GainSeekBar,R.id.MusicGeqBand30GainSeekBar,R.id.MusicGeqBand31GainSeekBar
            };
    private TextView[] musicGeqTextView = new TextView[31];
    private SeekBar[] musicGeqSeekBar = new SeekBar[31];



    private int[] reverbPeqFreqTextViewID = new int[]
            {       R.id.ReverbPeqBand1FreqTextView,R.id.ReverbPeqBand2FreqTextView, R.id.ReverbPeqBand3FreqTextView,R.id.ReverbPeqBand4FreqTextView,R.id.ReverbPeqBand5FreqTextView
            };

    private int[] reverbPeqFreqSeekBarID = new int[]
            {       R.id.ReverbPeqBand1FreqSeekBar,R.id.ReverbPeqBand2FreqSeekBar, R.id.ReverbPeqBand3FreqSeekBar,R.id.ReverbPeqBand4FreqSeekBar,R.id.ReverbPeqBand5FreqSeekBar
            };
    private TextView[] reverbPeqFreqTextView = new TextView[5];
    private SeekBar[] reverbPeqFreqSeekBar = new SeekBar[5];

    private int[] reverbPeqQTextViewID = new int[]
            {       R.id.ReverbPeqBand1QTextView,R.id.ReverbPeqBand2QTextView, R.id.ReverbPeqBand3QTextView,R.id.ReverbPeqBand4QTextView,R.id.ReverbPeqBand5QTextView
            };

    private int[] reverbPeqQSeekBarID = new int[]
            {       R.id.ReverbPeqBand1QSeekBar,R.id.ReverbPeqBand2QSeekBar, R.id.ReverbPeqBand3QSeekBar,R.id.ReverbPeqBand4QSeekBar,R.id.ReverbPeqBand5QSeekBar
            };
    private TextView[] reverbPeqQTextView = new TextView[5];
    private SeekBar[] reverbPeqQSeekBar = new SeekBar[5];

    private int[] reverbPeqGainTextViewID = new int[]
            {       R.id.ReverbPeqBand1GainTextView,R.id.ReverbPeqBand2GainTextView, R.id.ReverbPeqBand3GainTextView,R.id.ReverbPeqBand4GainTextView,R.id.ReverbPeqBand5GainTextView
            };

    private int[] reverbPeqGainSeekBarID = new int[]
            {       R.id.ReverbPeqBand1GainSeekBar,R.id.ReverbPeqBand2GainSeekBar, R.id.ReverbPeqBand3GainSeekBar,R.id.ReverbPeqBand4GainSeekBar,R.id.ReverbPeqBand5GainSeekBar
            };
    private TextView[] reverbPeqGainTextView = new TextView[5];
    private SeekBar[] reverbPeqGainSeekBar = new SeekBar[5];


    private int[] echoTapTimeSeekBarID = new int[]
            {       R.id.Tap1TimeSeekBar,R.id.Tap2TimeSeekBar, R.id.Tap3TimeSeekBar};
    private int[] echoTapTimeTextViewID = new int[]
            {       R.id.Tap1TimeTextView,R.id.Tap2TimeTextView, R.id.Tap3TimeTextView};
    private TextView[] echoTapTimeTextView = new TextView[3];
    private SeekBar[] echoTapTimeSeekBar = new SeekBar[3];

    private int[] echoTapLevelSeekBarID = new int[]
            {       R.id.Tap1LevelSeekBar,R.id.Tap2LevelSeekBar, R.id.Tap3LevelSeekBar};
    private int[] echoTapLevelTextViewID = new int[]
            {       R.id.Tap1LevelTextView,R.id.Tap2LevelTextView, R.id.Tap3LevelTextView};
    private TextView[] echoTapLevelTextView = new TextView[3];
    private SeekBar[] echoTapLevelSeekBar = new SeekBar[3];

    private int[] echoTapPanSeekBarID = new int[]
            {       R.id.Tap1PanSeekBar,R.id.Tap2PanSeekBar, R.id.Tap3PanSeekBar};
    private int[] echoTapPanTextViewID = new int[]
            {       R.id.Tap1PanTextView,R.id.Tap2PanTextView, R.id.Tap3PanTextView};
    private TextView[] echoTapPanTextView = new TextView[3];
    private SeekBar[] echoTapPanSeekBar = new SeekBar[3];


    private int[] echoPeqFreqTextViewID = new int[]
            {       R.id.EchoPeqBand1FreqTextView,R.id.EchoPeqBand2FreqTextView, R.id.EchoPeqBand3FreqTextView,R.id.EchoPeqBand4FreqTextView,R.id.EchoPeqBand5FreqTextView
            };

    private int[] echoPeqFreqSeekBarID = new int[]
            {       R.id.EchoPeqBand1FreqSeekBar,R.id.EchoPeqBand2FreqSeekBar, R.id.EchoPeqBand3FreqSeekBar,R.id.EchoPeqBand4FreqSeekBar,R.id.EchoPeqBand5FreqSeekBar
            };
    private TextView[] echoPeqFreqTextView = new TextView[5];
    private SeekBar[] echoPeqFreqSeekBar = new SeekBar[5];

    private int[] echoPeqQTextViewID = new int[]
            {       R.id.EchoPeqBand1QTextView,R.id.EchoPeqBand2QTextView, R.id.EchoPeqBand3QTextView,R.id.EchoPeqBand4QTextView,R.id.EchoPeqBand5QTextView
            };

    private int[] echoPeqQSeekBarID = new int[]
            {       R.id.EchoPeqBand1QSeekBar,R.id.EchoPeqBand2QSeekBar, R.id.EchoPeqBand3QSeekBar,R.id.EchoPeqBand4QSeekBar,R.id.EchoPeqBand5QSeekBar
            };
    private TextView[] echoPeqQTextView = new TextView[5];
    private SeekBar[] echoPeqQSeekBar = new SeekBar[5];

    private int[] echoPeqGainTextViewID = new int[]
            {       R.id.EchoPeqBand1GainTextView,R.id.EchoPeqBand2GainTextView, R.id.EchoPeqBand3GainTextView,R.id.EchoPeqBand4GainTextView,R.id.EchoPeqBand5GainTextView
            };

    private int[] echoPeqGainSeekBarID = new int[]
            {       R.id.EchoPeqBand1GainSeekBar,R.id.EchoPeqBand2GainSeekBar, R.id.EchoPeqBand3GainSeekBar,R.id.EchoPeqBand4GainSeekBar,R.id.EchoPeqBand5GainSeekBar
            };
    private TextView[] echoPeqGainTextView = new TextView[5];
    private SeekBar[] echoPeqGainSeekBar = new SeekBar[5];

    private int[] outputPeqFreqTextViewID = new int[]
            {       R.id.OutputPeqBand1FreqTextView,R.id.OutputPeqBand2FreqTextView, R.id.OutputPeqBand3FreqTextView,R.id.OutputPeqBand4FreqTextView,R.id.OutputPeqBand5FreqTextView
            };

    private int[] outputPeqFreqSeekBarID = new int[]
            {       R.id.OutputPeqBand1FreqSeekBar,R.id.OutputPeqBand2FreqSeekBar, R.id.OutputPeqBand3FreqSeekBar,R.id.OutputPeqBand4FreqSeekBar,R.id.OutputPeqBand5FreqSeekBar
            };
    private TextView[] outputPeqFreqTextView = new TextView[5];
    private SeekBar[] outputPeqFreqSeekBar = new SeekBar[5];

    private int[] outputPeqQTextViewID = new int[]
            {       R.id.OutputPeqBand1QTextView,R.id.OutputPeqBand2QTextView, R.id.OutputPeqBand3QTextView,R.id.OutputPeqBand4QTextView,R.id.OutputPeqBand5QTextView
            };

    private int[] outputPeqQSeekBarID = new int[]
            {       R.id.OutputPeqBand1QSeekBar,R.id.OutputPeqBand2QSeekBar, R.id.OutputPeqBand3QSeekBar,R.id.OutputPeqBand4QSeekBar,R.id.OutputPeqBand5QSeekBar
            };
    private TextView[] outputPeqQTextView = new TextView[5];
    private SeekBar[] outputPeqQSeekBar = new SeekBar[5];

    private int[] outputPeqGainTextViewID = new int[]
            {       R.id.OutputPeqBand1GainTextView,R.id.OutputPeqBand2GainTextView, R.id.OutputPeqBand3GainTextView,R.id.OutputPeqBand4GainTextView,R.id.OutputPeqBand5GainTextView
            };

    private int[] outputPeqGainSeekBarID = new int[]
            {       R.id.OutputPeqBand1GainSeekBar,R.id.OutputPeqBand2GainSeekBar, R.id.OutputPeqBand3GainSeekBar,R.id.OutputPeqBand4GainSeekBar,R.id.OutputPeqBand5GainSeekBar
            };
    private TextView[] outputPeqGainTextView = new TextView[5];
    private SeekBar[] outputPeqGainSeekBar = new SeekBar[5];


    private void onSeekBarDataChanged(final TextView changedView, final String value) {
        runOnUiThread(new Runnable() {
            public void run() {
                if (changedView != null) {
                    String tmpSplitString[] = changedView.getText().toString().split(":");
                    changedView.setText(tmpSplitString[0] + ":" + value);
                }
            }
        });
    }

    private int findArrayIdx(int[] array,int target) {
        int current_idx = 0;
        for (int i=0; i<array.length; i++)
        {
            if (array[i] == target) {
                current_idx=i;
            }
        }
        return current_idx;
    }

    private void setupMicInputPanel() {
        ((Button) this.findViewById(R.id.btn_mic_input)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                LayoutInflater layoutInflater = LayoutInflater.from(MainActivity.this);
                View micInputView = layoutInflater.inflate(R.layout.view_mic_input, null);
                builder.setView(micInputView);
                builder.setTitle("Mic Input Panel");
                builder.show();

                int tmpProgress;

                final TextView micInputLTextView = (TextView)micInputView.findViewById(R.id.MicInputLTextView);
                SeekBar micInputLSeekBar = (SeekBar)micInputView.findViewById(R.id.MicInputLSeekBar);
                micInputLSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {}
                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {}
                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                        if (fromUser) {
                            //Log.i(TAG, seekBar.toString() + "onProgressChanged " + progress);
                            seekBar.setFocusable(true);
                            seekBar.setFocusableInTouchMode(true);
                            seekBar.requestFocus();
                            seekBar.requestFocusFromTouch();

                            String appendString;
                            if (progress==0) {
                                appendString = " Mute";
                            } else {
                                appendString = String.format(" %.1fdB",Math.floor((mixerDbMap[progress] + 6.0f)*10)/10);
                            }
                            onSeekBarDataChanged(micInputLTextView,appendString);
                            mAudioCardCtrl.micLRLevelSetup(progress,0xff);
                            mCardSettings.set(WsdAudioCardCtrl.SettingsName.MicInputL,progress);
                        }
                    }
                });
                tmpProgress = mCardSettings.get(WsdAudioCardCtrl.SettingsName.MicInputL);
                if (tmpProgress!=-1)
                {
                    String appendString;
                    if (tmpProgress==0) {
                        appendString = " Mute";
                    } else {
                        appendString = String.format(" %.1fdB",Math.floor((mixerDbMap[tmpProgress] + 6.0f)*10)/10);
                    }
                    onSeekBarDataChanged(micInputLTextView,appendString);
                    micInputLSeekBar.setProgress(tmpProgress);
                }

                final TextView micInputRTextView = (TextView)micInputView.findViewById(R.id.MicInputRTextView);
                SeekBar micInputRSeekBar = (SeekBar)micInputView.findViewById(R.id.MicInputRSeekBar);
                micInputRSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {}
                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {}
                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                        if (fromUser) {
                            //Log.i(TAG, seekBar.toString() + "onProgressChanged " + progress);
                            seekBar.setFocusable(true);
                            seekBar.setFocusableInTouchMode(true);
                            seekBar.requestFocus();
                            seekBar.requestFocusFromTouch();

                            String appendString;
                            if (progress==0) {
                                appendString = " Mute";
                            } else {
                                appendString = String.format(" %.1fdB",Math.floor((mixerDbMap[progress] + 6.0f)*10)/10);
                            }
                            onSeekBarDataChanged(micInputRTextView,appendString);
                            mAudioCardCtrl.micLRLevelSetup(0xff,progress);
                            mCardSettings.set(WsdAudioCardCtrl.SettingsName.MicInputR,progress);
                        }
                    }
                });
                tmpProgress = mCardSettings.get(WsdAudioCardCtrl.SettingsName.MicInputR);
                if (tmpProgress!=-1)
                {
                    String appendString;
                    if (tmpProgress==0) {
                        appendString = " Mute";
                    } else {
                        appendString = String.format(" %.1fdB",Math.floor((mixerDbMap[tmpProgress] + 6.0f)*10)/10);
                    }
                    onSeekBarDataChanged(micInputRTextView,appendString);
                    micInputRSeekBar.setProgress(tmpProgress);
                }

                final TextView micInputAuxTextView = (TextView)micInputView.findViewById(R.id.MicInputAuxTextView);
                SeekBar micInputAuxSeekBar = (SeekBar)micInputView.findViewById(R.id.MicInputAuxSeekBar);
                micInputAuxSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {}
                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {}
                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                        if (fromUser) {
                            //Log.i(TAG, seekBar.toString() + "onProgressChanged " + progress);
                            seekBar.setFocusable(true);
                            seekBar.setFocusableInTouchMode(true);
                            seekBar.requestFocus();
                            seekBar.requestFocusFromTouch();

                            String appendString;
                            if (progress==0) {
                                appendString = " Mute";
                            } else {
                                appendString = String.format(" %.1fdB",Math.floor((mixerDbMap[progress] + 6.0f)*10)/10);
                            }
                            onSeekBarDataChanged(micInputAuxTextView,appendString);
                            mAudioCardCtrl.auxLevelSetup(progress,0xff);
                            mCardSettings.set(WsdAudioCardCtrl.SettingsName.MicInputAux,progress);
                        }
                    }
                });
                tmpProgress = mCardSettings.get(WsdAudioCardCtrl.SettingsName.MicInputAux);
                if (tmpProgress!=-1)
                {
                    String appendString;
                    if (tmpProgress==0) {
                        appendString = " Mute";
                    } else {
                        appendString = String.format(" %.1fdB",Math.floor((mixerDbMap[tmpProgress] + 6.0f)*10)/10);
                    }
                    onSeekBarDataChanged(micInputAuxTextView,appendString);
                    micInputAuxSeekBar.setProgress(tmpProgress);
                }


                final TextView micInputGainTextView = (TextView)micInputView.findViewById(R.id.MicInputGainTextView);
                SeekBar micInputGainSeekBar = (SeekBar)micInputView.findViewById(R.id.MicInputGainSeekBar);
                micInputGainSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {}
                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {}
                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                        if (fromUser) {
                            //Log.i(TAG, seekBar.toString() + "onProgressChanged " + progress);
                            seekBar.setFocusable(true);
                            seekBar.setFocusableInTouchMode(true);
                            seekBar.requestFocus();
                            seekBar.requestFocusFromTouch();

                            String appendString;
                            if (progress==0) {
                                appendString = " Mute";
                            } else {
                                appendString = String.format(" %.1fdB",Math.floor((mixerDbMap[progress] + 6.0f)*10)/10);
                            }
                            onSeekBarDataChanged(micInputGainTextView,appendString);
                            mAudioCardCtrl.micInputLevelSetup(progress);
                            mCardSettings.set(WsdAudioCardCtrl.SettingsName.MicInputGain,progress);
                        }
                    }
                });
                tmpProgress = mCardSettings.get(WsdAudioCardCtrl.SettingsName.MicInputGain);
                if (tmpProgress!=-1)
                {
                    String appendString;
                    if (tmpProgress==0) {
                        appendString = " Mute";
                    } else {
                        appendString = String.format(" %.1fdB",Math.floor((mixerDbMap[tmpProgress] + 6.0f)*10)/10);
                    }
                    onSeekBarDataChanged(micInputGainTextView,appendString);
                    micInputGainSeekBar.setProgress(tmpProgress);
                }

                CheckBox bassCutSwitchCheckBox = (CheckBox)micInputView.findViewById(R.id.BassCutSwitchCheckBox);
                final TextView bassCutFrequencyTextView = (TextView)micInputView.findViewById(R.id.BassCutFrequencyTextView);
                SeekBar bassCutFrequencySeekBar = (SeekBar)micInputView.findViewById(R.id.BassCutFrequencySeekBar);

                bassCutSwitchCheckBox.setOnCheckedChangeListener(new CheckBox.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton button, boolean isChecked){
                        int enable = isChecked?1:0;
                        mAudioCardCtrl.micBasscutEnable(enable);
                        mCardSettings.set(WsdAudioCardCtrl.SettingsName.BassCutSwitch,enable);
                    }
                });
                tmpProgress = mCardSettings.get(WsdAudioCardCtrl.SettingsName.BassCutSwitch);
                if (tmpProgress!=-1)
                {
                    bassCutSwitchCheckBox.setChecked(tmpProgress==0?false:true);
                }

                bassCutFrequencySeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {}
                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {}
                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                        if (fromUser) {
                            //Log.i(TAG, seekBar.toString() + "onProgressChanged " + progress);
                            seekBar.setFocusable(true);
                            seekBar.setFocusableInTouchMode(true);
                            seekBar.requestFocus();
                            seekBar.requestFocusFromTouch();

                            String appendString;
                            if (progress==0) {
                                appendString = " pass";
                            } else {
                                appendString = " " + hzMap[progress-1] + "Hz";
                            }
                            onSeekBarDataChanged(bassCutFrequencyTextView,appendString);
                            mAudioCardCtrl.micBasscutFrequencySetup(progress+1);
                            mCardSettings.set(WsdAudioCardCtrl.SettingsName.BassCutFrequency,progress);
                        }
                    }
                });
                tmpProgress = mCardSettings.get(WsdAudioCardCtrl.SettingsName.BassCutFrequency);
                if (tmpProgress!=-1)
                {
                    String appendString;
                    if (tmpProgress==0) {
                        appendString = " pass";
                    } else {
                        appendString = " " + hzMap[tmpProgress-1] + "Hz";
                    }
                    onSeekBarDataChanged(bassCutFrequencyTextView,appendString);
                    bassCutFrequencySeekBar.setProgress(tmpProgress);
                }



                CheckBox noiseSuppSwitchCheckBox = (CheckBox)micInputView.findViewById(R.id.NoiseSuppSwitchCheckBox);
                final TextView noiseSuppThresholdTextView = (TextView)micInputView.findViewById(R.id.NoiseSuppThresholdTextView);
                SeekBar noiseSuppThresholdSeekBar = (SeekBar)micInputView.findViewById(R.id.NoiseSuppThresholdSeekBar);

                noiseSuppSwitchCheckBox.setOnCheckedChangeListener(new CheckBox.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton button, boolean isChecked) {
                        int enable = isChecked?1:0;
                        mAudioCardCtrl.micNoiseSuppEnable(enable);
                        mCardSettings.set(WsdAudioCardCtrl.SettingsName.NoiseSuppSwitch,enable);
                    }
                });
                tmpProgress = mCardSettings.get(WsdAudioCardCtrl.SettingsName.NoiseSuppSwitch);
                if (tmpProgress!=-1)
                {
                    noiseSuppSwitchCheckBox.setChecked(tmpProgress==0?false:true);
                }

                noiseSuppThresholdSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {}
                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {}
                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                        if (fromUser) {
                            //Log.i(TAG, seekBar.toString() + "onProgressChanged " + progress);
                            seekBar.setFocusable(true);
                            seekBar.setFocusableInTouchMode(true);
                            seekBar.requestFocus();
                            seekBar.requestFocusFromTouch();

                            String appendString = String.format(" %d",progress);
                            onSeekBarDataChanged(noiseSuppThresholdTextView,appendString);
                            mAudioCardCtrl.micNoiseThresholdSetup(progress);
                            mCardSettings.set(WsdAudioCardCtrl.SettingsName.NoiseSuppThreshold,progress);
                        }
                    }
                });
                tmpProgress = mCardSettings.get(WsdAudioCardCtrl.SettingsName.NoiseSuppThreshold);
                if (tmpProgress!=-1)
                {
                    String appendString = String.format(" %d",tmpProgress);
                    onSeekBarDataChanged(noiseSuppThresholdTextView,appendString);
                    noiseSuppThresholdSeekBar.setProgress(tmpProgress);
                }

                CheckBox compressorSwitchCheckbox = (CheckBox)micInputView.findViewById(R.id.CompressorSwitchCheckbox);
                final TextView compressorSustainTextView = (TextView)micInputView.findViewById(R.id.CompressorSustainTextView);
                SeekBar compressorSustainSeekBar = (SeekBar)micInputView.findViewById(R.id.CompressorSustainSeekBar);
                final TextView compressorOutLevelTextView = (TextView)micInputView.findViewById(R.id.CompressorOutLevelTextView);
                SeekBar compressorOutLevelSeekBar = (SeekBar)micInputView.findViewById(R.id.CompressorOutLevelSeekBar);

                compressorSwitchCheckbox.setOnCheckedChangeListener(new CheckBox.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton button, boolean isChecked) {
                        int enable = isChecked?1:0;
                        mAudioCardCtrl.micCompressorEnable(enable);
                        mCardSettings.set(WsdAudioCardCtrl.SettingsName.CompressorSwitch,enable);
                    }
                });
                tmpProgress = mCardSettings.get(WsdAudioCardCtrl.SettingsName.CompressorSwitch);
                if (tmpProgress!=-1)
                {
                    compressorSwitchCheckbox.setChecked(tmpProgress==0?false:true);
                }

                compressorSustainSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {}
                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {}
                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                        if (fromUser) {
                            //Log.i(TAG, seekBar.toString() + "onProgressChanged " + progress);
                            seekBar.setFocusable(true);
                            seekBar.setFocusableInTouchMode(true);
                            seekBar.requestFocus();
                            seekBar.requestFocusFromTouch();

                            String appendString = String.format(" %d",progress);
                            onSeekBarDataChanged(compressorSustainTextView,appendString);
                            mAudioCardCtrl.micCompressorSetup(progress,0xff);
                            mCardSettings.set(WsdAudioCardCtrl.SettingsName.CompressorSustain,progress);
                        }
                    }
                });
                tmpProgress = mCardSettings.get(WsdAudioCardCtrl.SettingsName.CompressorSustain);
                if (tmpProgress!=-1)
                {
                    String appendString = String.format(" %d",tmpProgress);
                    onSeekBarDataChanged(compressorSustainTextView,appendString);
                    compressorSustainSeekBar.setProgress(tmpProgress);
                }

                compressorOutLevelSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {}
                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {}
                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                        if (fromUser) {
                            //Log.i(TAG, seekBar.toString() + "onProgressChanged " + progress);
                            seekBar.setFocusable(true);
                            seekBar.setFocusableInTouchMode(true);
                            seekBar.requestFocus();
                            seekBar.requestFocusFromTouch();

                            String appendString = String.format(" %d",progress);
                            onSeekBarDataChanged(compressorOutLevelTextView,appendString);
                            mAudioCardCtrl.micCompressorSetup(0xff,progress);
                            mCardSettings.set(WsdAudioCardCtrl.SettingsName.CompressorOutLevel,progress);
                        }
                    }
                });
                tmpProgress = mCardSettings.get(WsdAudioCardCtrl.SettingsName.CompressorOutLevel);
                if (tmpProgress!=-1)
                {
                    String appendString = String.format(" %d",tmpProgress);
                    onSeekBarDataChanged(compressorOutLevelTextView,appendString);
                    compressorOutLevelSeekBar.setProgress(tmpProgress);
                }

                final TextView frequencyShiftTextView = (TextView)micInputView.findViewById(R.id.FrequencyShiftTextView);
                SeekBar frequencyShiftValueSeekBar = (SeekBar)micInputView.findViewById(R.id.FrequencyShiftValueSeekBar);

                frequencyShiftValueSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {}
                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {}
                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                        if (fromUser) {
                            //Log.i(TAG, seekBar.toString() + "onProgressChanged " + progress);
                            seekBar.setFocusable(true);
                            seekBar.setFocusableInTouchMode(true);
                            seekBar.requestFocus();
                            seekBar.requestFocusFromTouch();

                            String appendString = String.format(" %d",progress-10);
                            onSeekBarDataChanged(frequencyShiftTextView,appendString);
                            mAudioCardCtrl.micFrequencyShiftSetup(progress-10);
                            mCardSettings.set(WsdAudioCardCtrl.SettingsName.FrequencyShift,progress);
                        }
                    }
                });
                tmpProgress = mCardSettings.get(WsdAudioCardCtrl.SettingsName.FrequencyShift);
                if (tmpProgress!=-1)
                {
                    String appendString = String.format(" %d",tmpProgress-10);
                    onSeekBarDataChanged(frequencyShiftTextView,appendString);
                    frequencyShiftValueSeekBar.setProgress(tmpProgress);
                }

                CheckBox micInputPhaseReverseCheckbox = (CheckBox)micInputView.findViewById(R.id.MicInputPhaseReverseCheckbox);
                micInputPhaseReverseCheckbox.setOnCheckedChangeListener(new CheckBox.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton button, boolean isChecked) {
                        int reverse = isChecked?1:0;
                        mAudioCardCtrl.micDryOutputSetup(0xff,reverse);
                        mCardSettings.set(WsdAudioCardCtrl.SettingsName.MicInputPhaseReverse,reverse);
                    }
                });
                tmpProgress = mCardSettings.get(WsdAudioCardCtrl.SettingsName.MicInputPhaseReverse);
                if (tmpProgress!=-1)
                {
                    micInputPhaseReverseCheckbox.setChecked(tmpProgress==0?false:true);
                }

                CheckBox micPeqSwitchCheckBox = (CheckBox)micInputView.findViewById(R.id.MicPeqSwitchCheckBox);
                micPeqSwitchCheckBox.setOnCheckedChangeListener(new CheckBox.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton button, boolean isChecked) {
                        int enable = isChecked?1:0;
                        mAudioCardCtrl.mic7bandPeqEnable(enable);
                        mCardSettings.set(WsdAudioCardCtrl.SettingsName.MicPeqSwitch,enable);
                    }
                });
                tmpProgress = mCardSettings.get(WsdAudioCardCtrl.SettingsName.MicPeqSwitch);
                if (tmpProgress!=-1)
                {
                    micPeqSwitchCheckBox.setChecked(tmpProgress==0?false:true);
                }

                for(int i=0;i<7;i++){
                    micPeqGainTextView[i] = (TextView)micInputView.findViewById(micPeqGainTextViewID[i]);
                    micPeqGainSeekBar[i] = (SeekBar)micInputView.findViewById(micPeqGainSeekBarID[i]);
                    micPeqGainSeekBar[i].setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                        @Override
                        public void onStopTrackingTouch(SeekBar seekBar) {}
                        @Override
                        public void onStartTrackingTouch(SeekBar seekBar) {}
                        @Override
                        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                            if (fromUser) {
                                //Log.i(TAG, seekBar.toString() + "onProgressChanged " + progress);
                                seekBar.setFocusable(true);
                                seekBar.setFocusableInTouchMode(true);
                                seekBar.requestFocus();
                                seekBar.requestFocusFromTouch();

                                int currentSeekBarId = seekBar.getId();
                                int seekBarIdx = findArrayIdx(micPeqGainSeekBarID,currentSeekBarId);
                                String appendString = String.format(" %.1fdB",(float)(progress-60)/5);
                                onSeekBarDataChanged(micPeqGainTextView[seekBarIdx],appendString);
                                mAudioCardCtrl.mic7bandPeqSetup(seekBarIdx+1,0xff,0xff,progress-60);
                                mCardSettings.set(WsdAudioCardCtrl.SettingsName.MicPeqBand1Gain.ordinal()+seekBarIdx,progress);
                            }
                        }
                    });
                    tmpProgress = mCardSettings.get(WsdAudioCardCtrl.SettingsName.MicPeqBand1Gain.ordinal()+i);
                    if (tmpProgress!=-1) {
                        micPeqGainSeekBar[i].setProgress(tmpProgress);
                        String appendString = String.format(" %.1fdB",(float)(tmpProgress-60)/5);
                        onSeekBarDataChanged(micPeqGainTextView[i],appendString);
                    }

                    micPeqFreqTextView[i] = (TextView)micInputView.findViewById(micPeqFreqTextViewID[i]);
                    micPeqFreqSeekBar[i] = (SeekBar)micInputView.findViewById(micPeqFreqSeekBarID[i]);
                    micPeqFreqSeekBar[i].setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                        @Override
                        public void onStopTrackingTouch(SeekBar seekBar) {}
                        @Override
                        public void onStartTrackingTouch(SeekBar seekBar) {}
                        @Override
                        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                            if (fromUser) {
                                //Log.i(TAG, seekBar.toString() + "onProgressChanged " + progress);
                                seekBar.setFocusable(true);
                                seekBar.setFocusableInTouchMode(true);
                                seekBar.requestFocus();
                                seekBar.requestFocusFromTouch();

                                int currentSeekBarId = seekBar.getId();
                                int seekBarIdx = findArrayIdx(micPeqFreqSeekBarID,currentSeekBarId);
                                String appendString = " " + hzMap[progress] + "Hz";
                                onSeekBarDataChanged(micPeqFreqTextView[seekBarIdx],appendString);
                                mAudioCardCtrl.mic7bandPeqSetup(seekBarIdx+1,progress+2,0xff,0x7f);
                                mCardSettings.set(WsdAudioCardCtrl.SettingsName.MicPeqBand1Freq.ordinal()+seekBarIdx,progress);
                            }
                        }
                    });
                    tmpProgress = mCardSettings.get(WsdAudioCardCtrl.SettingsName.MicPeqBand1Freq.ordinal()+i);
                    if (tmpProgress!=-1) {
                        micPeqFreqSeekBar[i].setProgress(tmpProgress);
                        String appendString = " " + hzMap[tmpProgress] + "Hz";
                        onSeekBarDataChanged(micPeqFreqTextView[i],appendString);
                    }

                    micPeqQTextView[i] = (TextView)micInputView.findViewById(micPeqQTextViewID[i]);
                    micPeqQSeekBar[i] = (SeekBar)micInputView.findViewById(micPeqQSeekBarID[i]);
                    micPeqQSeekBar[i].setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                        @Override
                        public void onStopTrackingTouch(SeekBar seekBar) {}
                        @Override
                        public void onStartTrackingTouch(SeekBar seekBar) {}
                        @Override
                        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                            if (fromUser) {
                                //Log.i(TAG, seekBar.toString() + "onProgressChanged " + progress);
                                seekBar.setFocusable(true);
                                seekBar.setFocusableInTouchMode(true);
                                seekBar.requestFocus();
                                seekBar.requestFocusFromTouch();

                                int CurrentSeekBarId = seekBar.getId();
                                int seekBarIdx = findArrayIdx(micPeqQSeekBarID,CurrentSeekBarId);
                                String appendString = " " + qMap[progress];
                                onSeekBarDataChanged(micPeqQTextView[seekBarIdx],appendString);
                                mAudioCardCtrl.mic7bandPeqSetup(seekBarIdx+1,0xff,progress,0x7f);
                                mCardSettings.set(WsdAudioCardCtrl.SettingsName.MicPeqBand1Q.ordinal()+seekBarIdx,progress);
                            }
                        }
                    });
                    tmpProgress = mCardSettings.get(WsdAudioCardCtrl.SettingsName.MicPeqBand1Q.ordinal()+i);
                    if (tmpProgress!=-1) {
                        micPeqQSeekBar[i].setProgress(tmpProgress);
                        String appendString = " " + qMap[tmpProgress];
                        onSeekBarDataChanged(micPeqQTextView[i],appendString);
                    }
                }
            }
        });
    }

    private void setupMusicInputPanel() {
        ((Button) this.findViewById(R.id.btn_music_input)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                LayoutInflater layoutInflater = LayoutInflater.from(MainActivity.this);
                View musicInputView = layoutInflater.inflate(R.layout.view_music_input, null);
                builder.setView(musicInputView);
                builder.setTitle("Music Input Panel");
                builder.show();
                int tmpProgress;

                final TextView musicInputAuxTextView = (TextView)musicInputView.findViewById(R.id.MusicInputAuxTextView);
                SeekBar musicInputAuxSeekBar = (SeekBar)musicInputView.findViewById(R.id.MusicInputAuxSeekBar);
                musicInputAuxSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {}
                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {}
                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                        if (fromUser) {
                            //Log.i(TAG, seekBar.toString() + "onProgressChanged " + progress);
                            seekBar.setFocusable(true);
                            seekBar.setFocusableInTouchMode(true);
                            seekBar.requestFocus();
                            seekBar.requestFocusFromTouch();

                            String appendString;
                            if (progress==0) {
                                appendString = " Mute";
                            } else {
                                appendString = String.format(" %.1fdB",Math.floor((mixerDbMap[progress]*10)/10));
                            }
                            onSeekBarDataChanged(musicInputAuxTextView,appendString);
                            mAudioCardCtrl.auxLevelSetup(0xff,progress);
                            mCardSettings.set(WsdAudioCardCtrl.SettingsName.MusicInputAux,progress);
                        }
                    }
                });
                tmpProgress = mCardSettings.get(WsdAudioCardCtrl.SettingsName.MusicInputAux);
                if (tmpProgress!=-1)
                {
                    String appendString;
                    if (tmpProgress==0) {
                        appendString = " Mute";
                    } else {
                        appendString = String.format(" %.1fdB",Math.floor((mixerDbMap[tmpProgress]*10)/10));
                    }
                    onSeekBarDataChanged(musicInputAuxTextView,appendString);
                    musicInputAuxSeekBar.setProgress(tmpProgress);
                }

                final TextView musicInputGainTextView = (TextView)musicInputView.findViewById(R.id.MusicInputGainTextView);
                SeekBar musicInputGainSeekBar = (SeekBar)musicInputView.findViewById(R.id.MusicInputGainSeekBar);
                musicInputGainSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {}
                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {}
                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                        if (fromUser) {
                            //Log.i(TAG, seekBar.toString() + "onProgressChanged " + progress);
                            seekBar.setFocusable(true);
                            seekBar.setFocusableInTouchMode(true);
                            seekBar.requestFocus();
                            seekBar.requestFocusFromTouch();

                            String appendString;
                            if (progress==0) {
                                appendString = " Mute";
                            } else {
                                appendString = String.format(" %.1fdB",mixerDbMap[progress] + 6.0f);
                            }
                            onSeekBarDataChanged(musicInputGainTextView,appendString);
                            mAudioCardCtrl.musicInputLevelSetup(progress);
                            mCardSettings.set(WsdAudioCardCtrl.SettingsName.MusicInputGain,progress);
                        }
                    }
                });
                tmpProgress = mCardSettings.get(WsdAudioCardCtrl.SettingsName.MusicInputGain);
                if (tmpProgress!=-1)
                {
                    String appendString;
                    if (tmpProgress==0) {
                        appendString = " Mute";
                    } else {
                        appendString = String.format(" %.1fdB",Math.floor(((mixerDbMap[tmpProgress] + 6.0f)*10)/10));
                    }
                    onSeekBarDataChanged(musicInputGainTextView,appendString);
                    musicInputGainSeekBar.setProgress(tmpProgress);
                }

                CheckBox keyControlSwitchCheckbox = (CheckBox)musicInputView.findViewById(R.id.KeyControlSwitchCheckbox);
                final TextView keyControlPitchTextView = (TextView)musicInputView.findViewById(R.id.KeyControlPitchTextView);
                SeekBar keyControlPitchSeekBar = (SeekBar)musicInputView.findViewById(R.id.KeyControlPitchSeekBar);
                final TextView keyControlPitchFineTextView = (TextView)musicInputView.findViewById(R.id.KeyControlPitchFineTextView);
                SeekBar keyControlPitchFineSeekBar = (SeekBar)musicInputView.findViewById(R.id.KeyControlPitchFineSeekBar);
                CheckBox musicInputPhaseReverseCheckbox = (CheckBox)musicInputView.findViewById(R.id.MusicInputPhaseReverseCheckbox);
                musicInputPhaseReverseCheckbox.setOnCheckedChangeListener(new CheckBox.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton button, boolean isChecked) {
                        int reverse = isChecked?1:0;
                        mAudioCardCtrl.musicPhaseSetup(reverse);
                        mCardSettings.set(WsdAudioCardCtrl.SettingsName.MusicInputPhaseReverse,reverse);
                    }
                });
                tmpProgress = mCardSettings.get(WsdAudioCardCtrl.SettingsName.MusicInputPhaseReverse);
                if (tmpProgress!=-1)
                {
                    musicInputPhaseReverseCheckbox.setChecked(tmpProgress==0?false:true);
                }

                keyControlSwitchCheckbox.setOnCheckedChangeListener(new CheckBox.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton button, boolean isChecked) {
                        int enable = isChecked?1:0;
                        mAudioCardCtrl.musicKeyctrlEnable(enable);
                        mCardSettings.set(WsdAudioCardCtrl.SettingsName.KeyControlSwitch,enable);
                    }
                });
                tmpProgress = mCardSettings.get(WsdAudioCardCtrl.SettingsName.KeyControlSwitch);
                if (tmpProgress!=-1)
                {
                    keyControlSwitchCheckbox.setChecked(tmpProgress==0?false:true);
                }

                keyControlPitchSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {}
                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {}
                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                        if (fromUser) {
                            //Log.i(TAG, seekBar.toString() + "onProgressChanged " + progress);
                            seekBar.setFocusable(true);
                            seekBar.setFocusableInTouchMode(true);
                            seekBar.requestFocus();
                            seekBar.requestFocusFromTouch();

                            String appendString = String.format(" %d",progress-12);
                            onSeekBarDataChanged(keyControlPitchTextView,appendString);
                            mAudioCardCtrl.musicKeyctrlSetup(progress-12,0x7f);
                            mCardSettings.set(WsdAudioCardCtrl.SettingsName.KeyControlPitch,progress);
                        }
                    }
                });
                tmpProgress = mCardSettings.get(WsdAudioCardCtrl.SettingsName.KeyControlPitch);
                if (tmpProgress!=-1)
                {
                    String appendString = String.format(" %d",tmpProgress-12);
                    onSeekBarDataChanged(keyControlPitchTextView,appendString);
                    keyControlPitchSeekBar.setProgress(tmpProgress);
                }

                keyControlPitchFineSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {}
                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {}
                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                        if (fromUser) {
                            //Log.i(TAG, seekBar.toString() + "onProgressChanged " + progress);
                            seekBar.setFocusable(true);
                            seekBar.setFocusableInTouchMode(true);
                            seekBar.requestFocus();
                            seekBar.requestFocusFromTouch();

                            String appendString = String.format(" %d",(progress-50));
                            onSeekBarDataChanged(keyControlPitchFineTextView,appendString);
                            mAudioCardCtrl.musicKeyctrlSetup(0x7f,progress-50);
                            mCardSettings.set(WsdAudioCardCtrl.SettingsName.KeyControlPitchFine,progress);
                        }
                    }
                });
                tmpProgress = mCardSettings.get(WsdAudioCardCtrl.SettingsName.KeyControlPitchFine);
                if (tmpProgress!=-1)
                {
                    String appendString = String.format(" %d",(tmpProgress-50));
                    onSeekBarDataChanged(keyControlPitchFineTextView,appendString);
                    keyControlPitchFineSeekBar.setProgress(tmpProgress);
                }

                CheckBox musicGeqSwitchCheckBox = (CheckBox)musicInputView.findViewById(R.id.MusicGeqSwitchCheckBox);
                musicGeqSwitchCheckBox.setOnCheckedChangeListener(new CheckBox.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton button, boolean isChecked) {
                        int enable = isChecked?1:0;
                        mAudioCardCtrl.music31bandGeqEnable(enable);
                        mCardSettings.set(WsdAudioCardCtrl.SettingsName.MusicGeqSwitch,enable);
                    }
                });
                tmpProgress = mCardSettings.get(WsdAudioCardCtrl.SettingsName.MusicGeqSwitch);
                if (tmpProgress!=-1)
                {
                    musicGeqSwitchCheckBox.setChecked(tmpProgress==0?false:true);
                }

                for(int i=0;i<31;i++){
                    musicGeqTextView[i] = (TextView)musicInputView.findViewById(musicGeqTextViewID[i]);
                    musicGeqSeekBar[i] = (SeekBar)musicInputView.findViewById(musicGeqSeekBarID[i]);

                    musicGeqSeekBar[i].setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                        @Override
                        public void onStopTrackingTouch(SeekBar seekBar) {}
                        @Override
                        public void onStartTrackingTouch(SeekBar seekBar) {}
                        @Override
                        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                            if (fromUser) {
                                //Log.i(TAG, seekBar.toString() + "onProgressChanged " + progress);
                                seekBar.setFocusable(true);
                                seekBar.setFocusableInTouchMode(true);
                                seekBar.requestFocus();
                                seekBar.requestFocusFromTouch();

                                int CurrentSeekBarId = seekBar.getId();
                                int SeekBarIdx = findArrayIdx(musicGeqSeekBarID,CurrentSeekBarId);
                                String appendString = String.format(" %.1fdB",(float)(progress-60)/5);
                                onSeekBarDataChanged(musicGeqTextView[SeekBarIdx],appendString);
                                mAudioCardCtrl.music31bandGeqSetup(SeekBarIdx+1,progress-60);
                                mCardSettings.set(WsdAudioCardCtrl.SettingsName.MusicGeqBand1Gain.ordinal()+SeekBarIdx,progress);
                            }
                        }
                    });
                    tmpProgress = mCardSettings.get(WsdAudioCardCtrl.SettingsName.MusicGeqBand1Gain.ordinal()+i);
                    if (tmpProgress!=-1)
                    {
                        String appendString = String.format(" %.1fdB",(float)(tmpProgress-60)/5);
                        onSeekBarDataChanged(musicGeqTextView[i],appendString);
                        musicGeqSeekBar[i].setProgress(tmpProgress);
                    }

                }
            }
        });

    }

    private void setupEchoPanel() {
        ((Button) this.findViewById(R.id.btn_echo)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                LayoutInflater layoutInflater = LayoutInflater.from(MainActivity.this);
                View currentView = layoutInflater.inflate(R.layout.view_echo, null);
                builder.setView(currentView);
                builder.setTitle("Echo Panel");
                builder.show();

                int tmpProgress;

                final TextView echoInputTextView = (TextView) currentView.findViewById(R.id.EchoInputTextView);
                SeekBar echoInputSeekBar = (SeekBar) currentView.findViewById(R.id.EchoInputSeekBar);
                echoInputSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {
                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {
                    }

                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                        if (fromUser) {
                            //Log.i(TAG, seekBar.toString() + "onProgressChanged " + progress);
                            seekBar.setFocusable(true);
                            seekBar.setFocusableInTouchMode(true);
                            seekBar.requestFocus();
                            seekBar.requestFocusFromTouch();

                            String appendString;
                            if (progress == 0) {
                                appendString = " Mute";
                            } else {
                                appendString = String.format(" %.1fdB",Math.floor((mixerDbMap[progress]*10)/10));
                            }
                            onSeekBarDataChanged(echoInputTextView, appendString);
                            mAudioCardCtrl.echoInputLevelSetup(progress);
                            mCardSettings.set(WsdAudioCardCtrl.SettingsName.EchoInput,progress);
                        }
                    }
                });
                tmpProgress = mCardSettings.get(WsdAudioCardCtrl.SettingsName.EchoInput);
                if (tmpProgress!=-1)
                {
                    String appendString;
                    if (tmpProgress==0) {
                        appendString = " Mute";
                    } else {
                        appendString = String.format(" %.1fdB",Math.floor((mixerDbMap[tmpProgress]*10)/10));
                    }
                    onSeekBarDataChanged(echoInputTextView,appendString);
                    echoInputSeekBar.setProgress(tmpProgress);
                }

                final TextView echoOutputTextView = (TextView) currentView.findViewById(R.id.EchoOutputTextView);
                SeekBar echoOutputSeekBar = (SeekBar) currentView.findViewById(R.id.EchoOutputSeekBar);
                echoOutputSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {
                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {
                    }

                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                        if (fromUser) {
                            //Log.i(TAG, seekBar.toString() + "onProgressChanged " + progress);
                            seekBar.setFocusable(true);
                            seekBar.setFocusableInTouchMode(true);
                            seekBar.requestFocus();
                            seekBar.requestFocusFromTouch();

                            String appendString;
                            if (progress == 0) {
                                appendString = " Mute";
                            } else {
                                appendString = String.format(" %.1fdB",Math.floor((mixerDbMap[progress]*10)/10));
                            }
                            onSeekBarDataChanged(echoOutputTextView, appendString);
                            mAudioCardCtrl.echoOutputSetup(progress, 0xff);
                            mCardSettings.set(WsdAudioCardCtrl.SettingsName.EchoOutput,progress);
                        }
                    }
                });
                tmpProgress = mCardSettings.get(WsdAudioCardCtrl.SettingsName.EchoOutput);
                if (tmpProgress!=-1)
                {
                    String appendString;
                    if (tmpProgress==0) {
                        appendString = " Mute";
                    } else {
                        appendString = String.format(" %.1fdB",Math.floor((mixerDbMap[tmpProgress]*10)/10));
                    }
                    onSeekBarDataChanged(echoOutputTextView,appendString);
                    echoOutputSeekBar.setProgress(tmpProgress);
                }

                final TextView echoLevelTextView = (TextView) currentView.findViewById(R.id.EchoLevelTextView);
                SeekBar echoLevelSeekBar = (SeekBar) currentView.findViewById(R.id.EchoLevelSeekBar);
                echoLevelSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {
                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {
                    }

                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                        if (fromUser) {
                            //Log.i(TAG, seekBar.toString() + "onProgressChanged " + progress);
                            seekBar.setFocusable(true);
                            seekBar.setFocusableInTouchMode(true);
                            seekBar.requestFocus();
                            seekBar.requestFocusFromTouch();

                            String appendString;
                            if (progress == 0) {
                                appendString = " Mute";
                            } else {
                                appendString = String.format(" %.1fdB",Math.floor((mixerDbMap[progress]*10)/10));
                            }
                            onSeekBarDataChanged(echoLevelTextView, appendString);
                            mAudioCardCtrl.echoEffectsSetup(0xff,progress,0xff, 0xff, 0xff,0xff);
                            mCardSettings.set(WsdAudioCardCtrl.SettingsName.EchoLevel,progress);
                        }
                    }
                });
                tmpProgress = mCardSettings.get(WsdAudioCardCtrl.SettingsName.EchoLevel);
                if (tmpProgress!=-1)
                {
                    String appendString;
                    if (tmpProgress==0) {
                        appendString = " Mute";
                    } else {
                        appendString = String.format(" %.1fdB",Math.floor((mixerDbMap[tmpProgress]*10)/10));
                    }
                    onSeekBarDataChanged(echoLevelTextView,appendString);
                    echoLevelSeekBar.setProgress(tmpProgress);
                }

                final TextView echoTimeTextView = (TextView) currentView.findViewById(R.id.EchoTimeTextView);
                SeekBar echoTimeSeekBar = (SeekBar) currentView.findViewById(R.id.EchoTimeSeekBar);
                echoTimeSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {
                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {
                    }

                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                        if (fromUser) {
                            //Log.i(TAG, seekBar.toString() + "onProgressChanged " + progress);
                            seekBar.setFocusable(true);
                            seekBar.setFocusableInTouchMode(true);
                            seekBar.requestFocus();
                            seekBar.requestFocusFromTouch();

                            String appendString = String.format(" %dms", progress*10);
                            onSeekBarDataChanged(echoTimeTextView, appendString);
                            mAudioCardCtrl.echoEffectsSetup(progress,0xff,0xff,0xff,0xff,0xff);
                            mCardSettings.set(WsdAudioCardCtrl.SettingsName.EchoTime,progress);
                        }
                    }
                });
                tmpProgress = mCardSettings.get(WsdAudioCardCtrl.SettingsName.EchoTime);
                if (tmpProgress!=-1)
                {
                    String appendString = String.format(" %dms", tmpProgress*10);
                    onSeekBarDataChanged(echoTimeTextView, appendString);
                    echoTimeSeekBar.setProgress(tmpProgress);
                }

                final TextView echoPanTextView = (TextView) currentView.findViewById(R.id.EchoPanTextView);
                SeekBar echoPanSeekBar = (SeekBar) currentView.findViewById(R.id.EchoPanSeekBar);
                echoPanSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {
                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {
                    }

                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                        if (fromUser) {
                            //Log.i(TAG, seekBar.toString() + "onProgressChanged " + progress);
                            seekBar.setFocusable(true);
                            seekBar.setFocusableInTouchMode(true);
                            seekBar.requestFocus();
                            seekBar.requestFocusFromTouch();

                            String appendString = "0";
                            if (progress<64) {
                                appendString = " " + "L" + (64-progress);
                            } else if (progress>64) {
                                appendString = " " + (progress-64) + "R" ;
                            } else {
                                appendString = "0";
                            }
                            onSeekBarDataChanged(echoPanTextView, appendString);
                            mAudioCardCtrl.echoEffectsSetup(0xff,0xff,progress,0xff,0xff,0xff);
                            mCardSettings.set(WsdAudioCardCtrl.SettingsName.EchoPan,progress);
                        }
                    }
                });
                tmpProgress = mCardSettings.get(WsdAudioCardCtrl.SettingsName.EchoPan);
                if (tmpProgress!=-1)
                {
                    String appendString = "0";
                    if (tmpProgress<64) {
                        appendString = " " + "L" + (64-tmpProgress);
                    } else if (tmpProgress>64) {
                        appendString = " " + (tmpProgress-64) + "R" ;
                    } else {
                        appendString = "0";
                    }
                    onSeekBarDataChanged(echoPanTextView, appendString);
                    echoPanSeekBar.setProgress(tmpProgress);
                }

                final TextView echoFeedbackLevelTextView = (TextView) currentView.findViewById(R.id.EchoFeedbackLevelTextView);
                SeekBar echoFeedbackLevelSeekBar = (SeekBar) currentView.findViewById(R.id.EchoFeedbackLevelSeekBar);
                echoFeedbackLevelSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {
                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {
                    }

                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                        if (fromUser) {
                            //Log.i(TAG, seekBar.toString() + "onProgressChanged " + progress);
                            seekBar.setFocusable(true);
                            seekBar.setFocusableInTouchMode(true);
                            seekBar.requestFocus();
                            seekBar.requestFocusFromTouch();

                            String appendString = String.format(" %d", progress);
                            onSeekBarDataChanged(echoFeedbackLevelTextView, appendString);
                            mAudioCardCtrl.echoEffectsSetup(0xff,0xff,0xff,0xff,progress,0xff);
                            mCardSettings.set(WsdAudioCardCtrl.SettingsName.EchoFeedbackLevel,progress);
                        }
                    }
                });
                tmpProgress = mCardSettings.get(WsdAudioCardCtrl.SettingsName.EchoFeedbackLevel);
                if (tmpProgress!=-1)
                {
                    String appendString = String.format(" %d", tmpProgress);
                    onSeekBarDataChanged(echoFeedbackLevelTextView, appendString);
                    echoFeedbackLevelSeekBar.setProgress(tmpProgress);
                }

                final TextView echoToneTextView = (TextView) currentView.findViewById(R.id.EchoToneTextView);
                SeekBar echoToneSeekBar = (SeekBar) currentView.findViewById(R.id.EchoToneSeekBar);
                echoToneSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {
                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {
                    }

                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                        if (fromUser) {
                            //Log.i(TAG, seekBar.toString() + "onProgressChanged " + progress);
                            seekBar.setFocusable(true);
                            seekBar.setFocusableInTouchMode(true);
                            seekBar.requestFocus();
                            seekBar.requestFocusFromTouch();

                            String appendString = String.format(" %d", progress-7);
                            onSeekBarDataChanged(echoToneTextView, appendString);
                            mAudioCardCtrl.echoEffectsSetup(0xff, 0xff,0xff, progress+57,0xff,0xff);
                            mCardSettings.set(WsdAudioCardCtrl.SettingsName.EchoTone,progress);
                        }
                    }
                });
                tmpProgress = mCardSettings.get(WsdAudioCardCtrl.SettingsName.EchoTone);
                if (tmpProgress!=-1)
                {
                    String appendString = String.format(" %d", tmpProgress-7);
                    onSeekBarDataChanged(echoToneTextView, appendString);
                    echoToneSeekBar.setProgress(tmpProgress);
                }

                final TextView echoPreDelayTextView = (TextView) currentView.findViewById(R.id.EchoPreDelayTextView);
                SeekBar echoPreDelaySeekBar = (SeekBar) currentView.findViewById(R.id.EchoPreDelaySeekBar);
                echoPreDelaySeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {
                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {
                    }

                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                        if (fromUser) {
                            //Log.i(TAG, seekBar.toString() + "onProgressChanged " + progress);
                            seekBar.setFocusable(true);
                            seekBar.setFocusableInTouchMode(true);
                            seekBar.requestFocus();
                            seekBar.requestFocusFromTouch();

                            String appendString = String.format(" %.1fms", (float)progress/2);
                            onSeekBarDataChanged(echoPreDelayTextView, appendString);
                            mAudioCardCtrl.echoEffectsSetup(0xff, 0xff, 0xff,0xff,0xff,progress);
                            mCardSettings.set(WsdAudioCardCtrl.SettingsName.EchoPreDelay,progress);
                        }
                    }
                });
                tmpProgress = mCardSettings.get(WsdAudioCardCtrl.SettingsName.EchoPreDelay);
                if (tmpProgress!=-1)
                {
                    String appendString = String.format(" %.1fms", (float)tmpProgress/2);
                    onSeekBarDataChanged(echoPreDelayTextView, appendString);
                    echoPreDelaySeekBar.setProgress(tmpProgress);
                }

                CheckBox echoPhaseReverseCheckbox = (CheckBox) currentView.findViewById(R.id.EchoPhaseReverseCheckbox);
                echoPhaseReverseCheckbox.setOnCheckedChangeListener(new CheckBox.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton button, boolean isChecked) {
                        int reverse = isChecked ? 1 : 0;
                        mAudioCardCtrl.echoOutputSetup(0xff, reverse);
                        mCardSettings.set(WsdAudioCardCtrl.SettingsName.EchoPhaseReverse,reverse);
                    }
                });
                tmpProgress = mCardSettings.get(WsdAudioCardCtrl.SettingsName.EchoPhaseReverse);
                if (tmpProgress!=-1)
                {
                    echoPhaseReverseCheckbox.setChecked(tmpProgress==0?false:true);
                }

                final TextView echoHighpassFreqTextView = (TextView) currentView.findViewById(R.id.EchoHighpassFreqTextView);
                SeekBar echoHighpassFreqSeekBar = (SeekBar) currentView.findViewById(R.id.EchoHighpassFreqSeekBar);
                echoHighpassFreqSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {
                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {
                    }

                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                        if (fromUser) {
                            //Log.i(TAG, seekBar.toString() + "onProgressChanged " + progress);
                            seekBar.setFocusable(true);
                            seekBar.setFocusableInTouchMode(true);
                            seekBar.requestFocus();
                            seekBar.requestFocusFromTouch();

                            String appendString = " " + highCutFreqMap[progress];
                            onSeekBarDataChanged(echoHighpassFreqTextView, appendString);
                            mAudioCardCtrl.echoBandpassfilterSetup(0xff, progress, 0xff, 0xff);
                            mCardSettings.set(WsdAudioCardCtrl.SettingsName.EchoHighpassFreq,progress);
                        }
                    }
                });
                tmpProgress = mCardSettings.get(WsdAudioCardCtrl.SettingsName.EchoHighpassFreq);
                if (tmpProgress!=-1)
                {
                    String appendString = " " + highCutFreqMap[tmpProgress];
                    onSeekBarDataChanged(echoHighpassFreqTextView, appendString);
                    echoHighpassFreqSeekBar.setProgress(tmpProgress);
                }

                final TextView echoHighpassTypeTextView = (TextView) currentView.findViewById(R.id.EchoHighpassTypeTextView);
                SeekBar echoHighpassTypeSeekBar = (SeekBar) currentView.findViewById(R.id.EchoHighpassTypeSeekBar);
                echoHighpassTypeSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {
                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {
                    }

                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                        if (fromUser) {
                            //Log.i(TAG, seekBar.toString() + "onProgressChanged " + progress);
                            seekBar.setFocusable(true);
                            seekBar.setFocusableInTouchMode(true);
                            seekBar.requestFocus();
                            seekBar.requestFocusFromTouch();

                            String appendString = " " + cutTypeMap[progress];
                            onSeekBarDataChanged(echoHighpassTypeTextView, appendString);
                            mAudioCardCtrl.echoBandpassfilterSetup(progress, 0xff, 0xff, 0xff);
                            mCardSettings.set(WsdAudioCardCtrl.SettingsName.EchoHighpassType,progress);
                        }
                    }
                });
                tmpProgress = mCardSettings.get(WsdAudioCardCtrl.SettingsName.EchoHighpassType);
                if (tmpProgress!=-1)
                {
                    String appendString = " " + cutTypeMap[tmpProgress];
                    onSeekBarDataChanged(echoHighpassTypeTextView, appendString);
                    echoHighpassTypeSeekBar.setProgress(tmpProgress);
                }

                final TextView echoLowpassFreqTextView = (TextView) currentView.findViewById(R.id.EchoLowpassFreqTextView);
                SeekBar echoLowpassFreqSeekBar = (SeekBar) currentView.findViewById(R.id.EchoLowpassFreqSeekBar);
                echoLowpassFreqSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {
                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {
                    }

                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                        if (fromUser) {
                            //Log.i(TAG, seekBar.toString() + "onProgressChanged " + progress);
                            seekBar.setFocusable(true);
                            seekBar.setFocusableInTouchMode(true);
                            seekBar.requestFocus();
                            seekBar.requestFocusFromTouch();

                            String appendString = " " + lowCutFreqMap[progress];
                            onSeekBarDataChanged(echoLowpassFreqTextView, appendString);
                            mAudioCardCtrl.echoBandpassfilterSetup(0xff, 0xff, 0xff, progress);
                            mCardSettings.set(WsdAudioCardCtrl.SettingsName.EchoLowpassFreq,progress);
                        }
                    }
                });
                tmpProgress = mCardSettings.get(WsdAudioCardCtrl.SettingsName.EchoLowpassFreq);
                if (tmpProgress!=-1)
                {
                    String appendString = " " + lowCutFreqMap[tmpProgress];
                    onSeekBarDataChanged(echoLowpassFreqTextView, appendString);
                    echoLowpassFreqSeekBar.setProgress(tmpProgress);
                }

                final TextView echoLowpassTypeTextView = (TextView) currentView.findViewById(R.id.EchoLowpassTypeTextView);
                SeekBar echoLowpassTypeSeekBar = (SeekBar) currentView.findViewById(R.id.EchoLowpassTypeSeekBar);
                echoLowpassTypeSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {
                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {
                    }

                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                        if (fromUser) {
                            //Log.i(TAG, seekBar.toString() + "onProgressChanged " + progress);
                            seekBar.setFocusable(true);
                            seekBar.setFocusableInTouchMode(true);
                            seekBar.requestFocus();
                            seekBar.requestFocusFromTouch();

                            String appendString = " " + cutTypeMap[progress];
                            onSeekBarDataChanged(echoLowpassTypeTextView, appendString);
                            mAudioCardCtrl.echoBandpassfilterSetup(0xff, 0xff, progress, 0xff);
                            mCardSettings.set(WsdAudioCardCtrl.SettingsName.EchoLowpassType,progress);
                        }
                    }
                });
                tmpProgress = mCardSettings.get(WsdAudioCardCtrl.SettingsName.EchoLowpassType);
                if (tmpProgress!=-1)
                {
                    String appendString = " " + cutTypeMap[tmpProgress];
                    onSeekBarDataChanged(echoLowpassTypeTextView, appendString);
                    echoLowpassTypeSeekBar.setProgress(tmpProgress);
                }

                CheckBox echoPeqSwitchCheckBox = (CheckBox) currentView.findViewById(R.id.EchoPeqSwitchCheckBox);
                echoPeqSwitchCheckBox.setOnCheckedChangeListener(new CheckBox.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton button, boolean isChecked) {
                        int enable = isChecked ? 1 : 0;
                        mAudioCardCtrl.echo5bandPeqEnable(enable);
                        mCardSettings.set(WsdAudioCardCtrl.SettingsName.EchoPeqSwitch,enable);
                    }
                });
                tmpProgress = mCardSettings.get(WsdAudioCardCtrl.SettingsName.EchoPeqSwitch);
                if (tmpProgress!=-1)
                {
                    echoPeqSwitchCheckBox.setChecked(tmpProgress==0?false:true);
                }


                for (int i = 0; i < 3; i++) {
                    echoTapTimeTextView[i] = (TextView) currentView.findViewById(echoTapTimeTextViewID[i]);
                    echoTapTimeSeekBar[i] = (SeekBar) currentView.findViewById(echoTapTimeSeekBarID[i]);
                    echoTapTimeSeekBar[i].setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                        @Override
                        public void onStopTrackingTouch(SeekBar seekBar) {
                        }

                        @Override
                        public void onStartTrackingTouch(SeekBar seekBar) {
                        }

                        @Override
                        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                            if (fromUser) {
                                //Log.i(TAG, seekBar.toString() + "onProgressChanged " + progress);
                                seekBar.setFocusable(true);
                                seekBar.setFocusableInTouchMode(true);
                                seekBar.requestFocus();
                                seekBar.requestFocusFromTouch();

                                int currentSeekBarId = seekBar.getId();
                                int seekBarIdx = findArrayIdx(echoTapTimeSeekBarID, currentSeekBarId);
                                double f = 0.781*(double)(progress+1);
                                BigDecimal b = new BigDecimal(f);
                                double f1 = b.setScale(1, RoundingMode.HALF_UP).doubleValue();
                                String appendString = String.format(" %.1f%%", f1);
                                onSeekBarDataChanged(echoTapTimeTextView[seekBarIdx], appendString);
                                mAudioCardCtrl.echoTapEffectsSetup(seekBarIdx + 1, progress, 0xff, 0xff);
                                mCardSettings.set(WsdAudioCardCtrl.SettingsName.EchoTap1Time.ordinal() + seekBarIdx, progress);
                            }
                        }
                    });
                    tmpProgress = mCardSettings.get(WsdAudioCardCtrl.SettingsName.EchoTap1Time.ordinal() + i);
                    if (tmpProgress != -1) {
                        echoTapTimeSeekBar[i].setProgress(tmpProgress);
                        double f = 0.781*(double)(tmpProgress+1);
                        BigDecimal b = new BigDecimal(f);
                        double f1 = b.setScale(1, RoundingMode.HALF_UP).doubleValue();
                        String appendString = String.format(" %.1f%%", f1);
                        onSeekBarDataChanged(echoTapTimeTextView[i], appendString);
                    }

                    echoTapLevelTextView[i] = (TextView) currentView.findViewById(echoTapLevelTextViewID[i]);
                    echoTapLevelSeekBar[i] = (SeekBar) currentView.findViewById(echoTapLevelSeekBarID[i]);
                    echoTapLevelSeekBar[i].setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                        @Override
                        public void onStopTrackingTouch(SeekBar seekBar) {
                        }

                        @Override
                        public void onStartTrackingTouch(SeekBar seekBar) {
                        }

                        @Override
                        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                            if (fromUser) {
                                //Log.i(TAG, seekBar.toString() + "onProgressChanged " + progress);
                                seekBar.setFocusable(true);
                                seekBar.setFocusableInTouchMode(true);
                                seekBar.requestFocus();
                                seekBar.requestFocusFromTouch();

                                int currentSeekBarId = seekBar.getId();
                                int seekBarIdx = findArrayIdx(echoTapLevelSeekBarID, currentSeekBarId);
                                String appendString = String.format(" %d", progress);
                                onSeekBarDataChanged(echoTapLevelTextView[seekBarIdx], appendString);
                                mAudioCardCtrl.echoTapEffectsSetup(seekBarIdx + 1, 0xff,progress, 0xff);
                                mCardSettings.set(WsdAudioCardCtrl.SettingsName.EchoTap1Level.ordinal() + seekBarIdx, progress);
                            }
                        }
                    });
                    tmpProgress = mCardSettings.get(WsdAudioCardCtrl.SettingsName.EchoTap1Level.ordinal() + i);
                    if (tmpProgress != -1) {
                        echoTapLevelSeekBar[i].setProgress(tmpProgress);
                        String appendString = String.format(" %d", tmpProgress);
                        onSeekBarDataChanged(echoTapLevelTextView[i], appendString);
                    }

                    echoTapPanTextView[i] = (TextView) currentView.findViewById(echoTapPanTextViewID[i]);
                    echoTapPanSeekBar[i] = (SeekBar) currentView.findViewById(echoTapPanSeekBarID[i]);
                    echoTapPanSeekBar[i].setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                        @Override
                        public void onStopTrackingTouch(SeekBar seekBar) {
                        }

                        @Override
                        public void onStartTrackingTouch(SeekBar seekBar) {
                        }

                        @Override
                        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                            if (fromUser) {
                                //Log.i(TAG, seekBar.toString() + "onProgressChanged " + progress);
                                seekBar.setFocusable(true);
                                seekBar.setFocusableInTouchMode(true);
                                seekBar.requestFocus();
                                seekBar.requestFocusFromTouch();

                                int currentSeekBarId = seekBar.getId();
                                int seekBarIdx = findArrayIdx(echoTapPanSeekBarID, currentSeekBarId);
                                String appendString = "0";
                                if (progress<64) {
                                    appendString = " " + "L" + (64-progress);
                                } else if (progress>64) {
                                    appendString = " " + (progress-64) + "R" ;
                                } else {
                                    appendString = "0";
                                }
                                onSeekBarDataChanged(echoTapPanTextView[seekBarIdx], appendString);
                                mAudioCardCtrl.echoTapEffectsSetup(seekBarIdx + 1, 0xff, 0xff, progress);
                                mCardSettings.set(WsdAudioCardCtrl.SettingsName.EchoTap1Pan.ordinal() + seekBarIdx, progress);
                            }
                        }
                    });
                    tmpProgress = mCardSettings.get(WsdAudioCardCtrl.SettingsName.EchoTap1Pan.ordinal() + i);
                    if (tmpProgress != -1) {
                        echoTapPanSeekBar[i].setProgress(tmpProgress);
                        String appendString = "0";
                        if (tmpProgress<64) {
                            appendString = " " + "L" + (64-tmpProgress);
                        } else if (tmpProgress>64) {
                            appendString = " " + (tmpProgress-64) + "R" ;
                        } else {
                            appendString = "0";
                        }
                        onSeekBarDataChanged(echoTapPanTextView[i], appendString);
                    }

                }

                for (int i = 0; i < 5; i++) {
                    echoPeqGainTextView[i] = (TextView) currentView.findViewById(echoPeqGainTextViewID[i]);
                    echoPeqGainSeekBar[i] = (SeekBar) currentView.findViewById(echoPeqGainSeekBarID[i]);
                    echoPeqGainSeekBar[i].setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                        @Override
                        public void onStopTrackingTouch(SeekBar seekBar) {
                        }

                        @Override
                        public void onStartTrackingTouch(SeekBar seekBar) {
                        }

                        @Override
                        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                            if (fromUser) {
                                //Log.i(TAG, seekBar.toString() + "onProgressChanged " + progress);
                                seekBar.setFocusable(true);
                                seekBar.setFocusableInTouchMode(true);
                                seekBar.requestFocus();
                                seekBar.requestFocusFromTouch();

                                int currentSeekBarId = seekBar.getId();
                                int seekBarIdx = findArrayIdx(echoPeqGainSeekBarID, currentSeekBarId);
                                String appendString = String.format(" %.1fdB", (float) (progress - 60) / 5);
                                onSeekBarDataChanged(echoPeqGainTextView[seekBarIdx], appendString);
                                mAudioCardCtrl.echo5bandPeqSetup(seekBarIdx + 1, 0xff, 0xff, progress - 60);
                                mCardSettings.set(WsdAudioCardCtrl.SettingsName.EchoPeqBand1Gain.ordinal()+seekBarIdx,progress);
                            }
                        }
                    });
                    tmpProgress = mCardSettings.get(WsdAudioCardCtrl.SettingsName.EchoPeqBand1Gain.ordinal()+i);
                    if (tmpProgress!=-1) {
                        echoPeqGainSeekBar[i].setProgress(tmpProgress);
                        String appendString = String.format(" %.1fdB",(float)(tmpProgress-60)/5);
                        onSeekBarDataChanged(echoPeqGainTextView[i],appendString);
                    }

                    echoPeqFreqTextView[i] = (TextView) currentView.findViewById(echoPeqFreqTextViewID[i]);
                    echoPeqFreqSeekBar[i] = (SeekBar) currentView.findViewById(echoPeqFreqSeekBarID[i]);
                    echoPeqFreqSeekBar[i].setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                        @Override
                        public void onStopTrackingTouch(SeekBar seekBar) {
                        }

                        @Override
                        public void onStartTrackingTouch(SeekBar seekBar) {
                        }

                        @Override
                        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                            if (fromUser) {
                                //Log.i(TAG, seekBar.toString() + "onProgressChanged " + progress);
                                seekBar.setFocusable(true);
                                seekBar.setFocusableInTouchMode(true);
                                seekBar.requestFocus();
                                seekBar.requestFocusFromTouch();

                                int currentSeekBarId = seekBar.getId();
                                int seekBarIdx = findArrayIdx(echoPeqFreqSeekBarID, currentSeekBarId);
                                String appendString = " " + hzMap[progress] + "Hz";
                                onSeekBarDataChanged(echoPeqFreqTextView[seekBarIdx], appendString);
                                mAudioCardCtrl.echo5bandPeqSetup(seekBarIdx + 1, progress + 2, 0xff, 0x7f);
                                mCardSettings.set(WsdAudioCardCtrl.SettingsName.EchoPeqBand1Freq.ordinal()+seekBarIdx,progress);
                            }
                        }
                    });
                    tmpProgress = mCardSettings.get(WsdAudioCardCtrl.SettingsName.EchoPeqBand1Freq.ordinal()+i);
                    if (tmpProgress!=-1) {
                        echoPeqFreqSeekBar[i].setProgress(tmpProgress);
                        String appendString = " " + hzMap[tmpProgress] + "Hz";
                        onSeekBarDataChanged(echoPeqFreqTextView[i],appendString);
                    }

                    echoPeqQTextView[i] = (TextView) currentView.findViewById(echoPeqQTextViewID[i]);
                    echoPeqQSeekBar[i] = (SeekBar) currentView.findViewById(echoPeqQSeekBarID[i]);
                    echoPeqQSeekBar[i].setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                        @Override
                        public void onStopTrackingTouch(SeekBar seekBar) {
                        }

                        @Override
                        public void onStartTrackingTouch(SeekBar seekBar) {
                        }

                        @Override
                        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                            if (fromUser) {
                                //Log.i(TAG, seekBar.toString() + "onProgressChanged " + progress);
                                seekBar.setFocusable(true);
                                seekBar.setFocusableInTouchMode(true);
                                seekBar.requestFocus();
                                seekBar.requestFocusFromTouch();

                                int CurrentSeekBarId = seekBar.getId();
                                int seekBarIdx = findArrayIdx(echoPeqQSeekBarID, CurrentSeekBarId);
                                String appendString = " " + qMap[progress];
                                onSeekBarDataChanged(echoPeqQTextView[seekBarIdx], appendString);
                                mAudioCardCtrl.echo5bandPeqSetup( seekBarIdx + 1, 0xff, progress, 0x7f);
                                mCardSettings.set(WsdAudioCardCtrl.SettingsName.EchoPeqBand1Q.ordinal()+seekBarIdx,progress);
                            }
                        }
                    });
                    tmpProgress = mCardSettings.get(WsdAudioCardCtrl.SettingsName.EchoPeqBand1Q.ordinal()+i);
                    if (tmpProgress!=-1) {
                        echoPeqQSeekBar[i].setProgress(tmpProgress);
                        String appendString = " " + qMap[tmpProgress];
                        onSeekBarDataChanged(echoPeqQTextView[i],appendString);
                    }
                }
            }
        });
    }

    private void setupReverbPanel() {
        ((Button) this.findViewById(R.id.btn_reverb)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                LayoutInflater layoutInflater = LayoutInflater.from(MainActivity.this);
                View currentView = layoutInflater.inflate(R.layout.view_reverb, null);
                builder.setView(currentView);
                builder.setTitle("Reverb Panel");
                builder.show();

                int tmpProgress;

                final TextView reverbInputTextView = (TextView) currentView.findViewById(R.id.ReverbInputTextView);
                SeekBar reverbInputSeekBar = (SeekBar) currentView.findViewById(R.id.ReverbInputSeekBar);
                reverbInputSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {
                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {
                    }

                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                        if (fromUser) {
                            //Log.i(TAG, seekBar.toString() + "onProgressChanged " + progress);
                            seekBar.setFocusable(true);
                            seekBar.setFocusableInTouchMode(true);
                            seekBar.requestFocus();
                            seekBar.requestFocusFromTouch();

                            String appendString;
                            if (progress == 0) {
                                appendString = " Mute";
                            } else {
                                appendString = String.format(" %.1fdB",Math.floor((mixerDbMap[progress]*10)/10));
                            }
                            onSeekBarDataChanged(reverbInputTextView, appendString);
                            mAudioCardCtrl.reverbInputLevelSetup(progress);
                            mCardSettings.set(WsdAudioCardCtrl.SettingsName.ReverbInput, progress);
                        }
                    }
                });
                tmpProgress = mCardSettings.get(WsdAudioCardCtrl.SettingsName.ReverbInput);
                if (tmpProgress!=-1)
                {
                    String appendString;
                    if (tmpProgress == 0) {
                        appendString = " Mute";
                    } else {
                        appendString = String.format(" %.1fdB",Math.floor((mixerDbMap[tmpProgress]*10)/10));
                    }
                    onSeekBarDataChanged(reverbInputTextView, appendString);
                    reverbInputSeekBar.setProgress(tmpProgress);
                }

                final TextView reverbOutputTextView = (TextView) currentView.findViewById(R.id.ReverbOutputTextView);
                SeekBar reverbOutputSeekBar = (SeekBar) currentView.findViewById(R.id.ReverbOutputSeekBar);
                reverbOutputSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {
                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {
                    }

                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                        if (fromUser) {
                            //Log.i(TAG, seekBar.toString() + "onProgressChanged " + progress);
                            seekBar.setFocusable(true);
                            seekBar.setFocusableInTouchMode(true);
                            seekBar.requestFocus();
                            seekBar.requestFocusFromTouch();

                            String appendString;
                            if (progress == 0) {
                                appendString = " Mute";
                            } else {
                                appendString = String.format(" %.1fdB",Math.floor((mixerDbMap[progress]*10)/10));
                            }
                            onSeekBarDataChanged(reverbOutputTextView, appendString);
                            mAudioCardCtrl.reverbOutputSetup(progress, 0xff);
                            mCardSettings.set(WsdAudioCardCtrl.SettingsName.ReverbOutput, progress);
                        }
                    }
                });
                tmpProgress = mCardSettings.get(WsdAudioCardCtrl.SettingsName.ReverbOutput);
                if (tmpProgress!=-1)
                {
                    String appendString;
                    if (tmpProgress == 0) {
                        appendString = " Mute";
                    } else {
                        appendString = String.format(" %.1fdB",Math.floor((mixerDbMap[tmpProgress]*10)/10));
                    }
                    onSeekBarDataChanged(reverbOutputTextView, appendString);
                    reverbOutputSeekBar.setProgress(tmpProgress);
                }

                final TextView reverbTypeTextView = (TextView) currentView.findViewById(R.id.ReverbTypeTextView);
                SeekBar reverbTypeSeekBar = (SeekBar) currentView.findViewById(R.id.ReverbTypeSeekBar);
                reverbTypeSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {
                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {
                    }

                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                        if (fromUser) {
                            //Log.i(TAG, seekBar.toString() + "onProgressChanged " + progress);
                            seekBar.setFocusable(true);
                            seekBar.setFocusableInTouchMode(true);
                            seekBar.requestFocus();
                            seekBar.requestFocusFromTouch();

                            String appendString = "";
                            switch(progress)
                            {
                                case 0:
                                    appendString = " 房间混响";
                                    break;
                                case 1:
                                    appendString = " 大厅混响1";
                                    break;
                                case 2:
                                    appendString = " 大厅混响2";
                                    break;
                                case 3:
                                    appendString = " 板混响";
                                    break;
                                default:
                            }

                            onSeekBarDataChanged(reverbTypeTextView, appendString);
                            mAudioCardCtrl.reverbEffectsSetup(progress,0xff, 0xff, 0xff);
                            mCardSettings.set(WsdAudioCardCtrl.SettingsName.ReverbType, progress);
                        }
                    }
                });
                tmpProgress = mCardSettings.get(WsdAudioCardCtrl.SettingsName.ReverbType);
                if (tmpProgress!=-1)
                {
                    String appendString = "";
                    switch(tmpProgress)
                    {
                        case 0:
                            appendString = " 房间混响";
                            break;
                        case 1:
                            appendString = " 大厅混响1";
                            break;
                        case 2:
                            appendString = " 大厅混响2";
                            break;
                        case 3:
                            appendString = " 板混响";
                            break;
                        default:
                            appendString = " 房间混响";
                            break;
                    }

                    onSeekBarDataChanged(reverbTypeTextView, appendString);
                    reverbTypeSeekBar.setProgress(tmpProgress);
                }

                final TextView reverbTimeTextView = (TextView) currentView.findViewById(R.id.ReverbTimeTextView);
                SeekBar reverbTimeSeekBar = (SeekBar) currentView.findViewById(R.id.ReverbTimeSeekBar);
                reverbTimeSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {
                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {
                    }

                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                        if (fromUser) {
                            //Log.i(TAG, seekBar.toString() + "onProgressChanged " + progress);
                            seekBar.setFocusable(true);
                            seekBar.setFocusableInTouchMode(true);
                            seekBar.requestFocus();
                            seekBar.requestFocusFromTouch();

                            String appendString = String.format(" %dms", progress);
                            onSeekBarDataChanged(reverbTimeTextView, appendString);
                            mAudioCardCtrl.reverbEffectsSetup(0xff, progress, 0xff,0xff);
                            mCardSettings.set(WsdAudioCardCtrl.SettingsName.ReverbTime, progress);
                        }
                    }
                });
                tmpProgress = mCardSettings.get(WsdAudioCardCtrl.SettingsName.ReverbTime);
                if (tmpProgress!=-1)
                {
                    String appendString = String.format(" %dms", tmpProgress);
                    onSeekBarDataChanged(reverbTimeTextView, appendString);
                    reverbTimeSeekBar.setProgress(tmpProgress);
                }

                final TextView reverbToneTextView = (TextView) currentView.findViewById(R.id.ReverbToneTextView);
                SeekBar reverbToneSeekBar = (SeekBar) currentView.findViewById(R.id.ReverbToneSeekBar);
                reverbToneSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {
                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {
                    }

                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                        if (fromUser) {
                            //Log.i(TAG, seekBar.toString() + "onProgressChanged " + progress);
                            seekBar.setFocusable(true);
                            seekBar.setFocusableInTouchMode(true);
                            seekBar.requestFocus();
                            seekBar.requestFocusFromTouch();

                            String appendString = String.format(" %d", progress-7);
                            onSeekBarDataChanged(reverbToneTextView, appendString);
                            mAudioCardCtrl.reverbEffectsSetup(0xff, 0xff, progress+57,0xff);
                            mCardSettings.set(WsdAudioCardCtrl.SettingsName.ReverbTone, progress);
                        }
                    }
                });
                tmpProgress = mCardSettings.get(WsdAudioCardCtrl.SettingsName.ReverbTone);
                if (tmpProgress!=-1)
                {
                    String appendString = String.format(" %d", tmpProgress-7);
                    onSeekBarDataChanged(reverbToneTextView, appendString);
                    reverbToneSeekBar.setProgress(tmpProgress);
                }

                final TextView reverbPreDelayTextView = (TextView) currentView.findViewById(R.id.ReverbPreDelayTextView);
                SeekBar reverbPreDelaySeekBar = (SeekBar) currentView.findViewById(R.id.ReverbPreDelaySeekBar);
                reverbPreDelaySeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {
                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {
                    }

                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                        if (fromUser) {
                            //Log.i(TAG, seekBar.toString() + "onProgressChanged " + progress);
                            seekBar.setFocusable(true);
                            seekBar.setFocusableInTouchMode(true);
                            seekBar.requestFocus();
                            seekBar.requestFocusFromTouch();

                            String appendString = String.format(" %dms", progress);
                            onSeekBarDataChanged(reverbPreDelayTextView, appendString);
                            mAudioCardCtrl.reverbEffectsSetup(0xff, 0xff, 0xff,progress);
                            mCardSettings.set(WsdAudioCardCtrl.SettingsName.ReverbPreDelay, progress);
                        }
                    }
                });
                tmpProgress = mCardSettings.get(WsdAudioCardCtrl.SettingsName.ReverbPreDelay);
                if (tmpProgress!=-1)
                {
                    String appendString = String.format(" %dms", tmpProgress);
                    onSeekBarDataChanged(reverbPreDelayTextView, appendString);
                    reverbPreDelaySeekBar.setProgress(tmpProgress);
                }

                final TextView echoToReverbTextView = (TextView)currentView.findViewById(R.id.EchoToReverbTextView);
                SeekBar echoToReverbSeekBar = (SeekBar)currentView.findViewById(R.id.EchoToReverbSeekBar);
                echoToReverbSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {}
                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {}
                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                        if (fromUser) {
                            //Log.i(TAG, seekBar.toString() + "onProgressChanged " + progress);
                            seekBar.setFocusable(true);
                            seekBar.setFocusableInTouchMode(true);
                            seekBar.requestFocus();
                            seekBar.requestFocusFromTouch();

                            String appendString;
                            if (progress == 0) {
                                appendString = " Mute";
                            } else {
                                appendString = String.format(" %.1fdB",Math.floor((mixerDbMap[progress]*10)/10));
                            }
                            onSeekBarDataChanged(echoToReverbTextView, appendString);
                            mAudioCardCtrl.echoToReverbLevelSetup(progress);
                            mCardSettings.set(WsdAudioCardCtrl.SettingsName.EchoToReverb, progress);
                        }
                    }
                });
                tmpProgress = mCardSettings.get(WsdAudioCardCtrl.SettingsName.EchoToReverb);
                if (tmpProgress!=-1)
                {
                    String appendString;
                    if (tmpProgress == 0) {
                        appendString = " Mute";
                    } else {
                        appendString = String.format(" %.1fdB",Math.floor((mixerDbMap[tmpProgress]*10)/10));
                    }
                    onSeekBarDataChanged(echoToReverbTextView, appendString);
                    echoToReverbSeekBar.setProgress(tmpProgress);
                }

                CheckBox reverbPhaseReverseCheckbox = (CheckBox) currentView.findViewById(R.id.ReverbPhaseReverseCheckbox);
                reverbPhaseReverseCheckbox.setOnCheckedChangeListener(new CheckBox.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton button, boolean isChecked) {
                        int reverse = isChecked ? 1 : 0;
                        mAudioCardCtrl.reverbOutputSetup(0xff, reverse);
                        mCardSettings.set(WsdAudioCardCtrl.SettingsName.ReverbPhaseReverse,reverse);
                    }
                });
                tmpProgress = mCardSettings.get(WsdAudioCardCtrl.SettingsName.ReverbPhaseReverse);
                if (tmpProgress!=-1)
                {
                    reverbPhaseReverseCheckbox.setChecked(tmpProgress==0?false:true);
                }

                final TextView reverbHighpassFreqTextView = (TextView) currentView.findViewById(R.id.ReverbHighpassFreqTextView);
                SeekBar reverbHighpassFreqSeekBar = (SeekBar) currentView.findViewById(R.id.ReverbHighpassFreqSeekBar);
                reverbHighpassFreqSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {
                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {
                    }

                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                        if (fromUser) {
                            //Log.i(TAG, seekBar.toString() + "onProgressChanged " + progress);
                            seekBar.setFocusable(true);
                            seekBar.setFocusableInTouchMode(true);
                            seekBar.requestFocus();
                            seekBar.requestFocusFromTouch();

                            String appendString = " " + highCutFreqMap[progress];
                            onSeekBarDataChanged(reverbHighpassFreqTextView, appendString);
                            mAudioCardCtrl.reverbBandpassfilterSetup(0xff, progress, 0xff, 0xff);
                            mCardSettings.set(WsdAudioCardCtrl.SettingsName.ReverbHighpassFreq,progress);
                        }
                    }
                });
                tmpProgress = mCardSettings.get(WsdAudioCardCtrl.SettingsName.ReverbHighpassFreq);
                if (tmpProgress!=-1)
                {
                    String appendString = " " + highCutFreqMap[tmpProgress];
                    onSeekBarDataChanged(reverbHighpassFreqTextView, appendString);
                    reverbHighpassFreqSeekBar.setProgress(tmpProgress);
                }

                final TextView reverbHighpassTypeTextView = (TextView) currentView.findViewById(R.id.ReverbHighpassTypeTextView);
                SeekBar reverbHighpassTypeSeekBar = (SeekBar) currentView.findViewById(R.id.ReverbHighpassTypeSeekBar);
                reverbHighpassTypeSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {
                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {
                    }

                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                        if (fromUser) {
                            //Log.i(TAG, seekBar.toString() + "onProgressChanged " + progress);
                            seekBar.setFocusable(true);
                            seekBar.setFocusableInTouchMode(true);
                            seekBar.requestFocus();
                            seekBar.requestFocusFromTouch();

                            String appendString = " " + cutTypeMap[progress];
                            onSeekBarDataChanged(reverbHighpassTypeTextView, appendString);
                            mAudioCardCtrl.reverbBandpassfilterSetup(progress, 0xff, 0xff, 0xff);
                            mCardSettings.set(WsdAudioCardCtrl.SettingsName.ReverbHighpassType,progress);
                        }
                    }
                });
                tmpProgress = mCardSettings.get(WsdAudioCardCtrl.SettingsName.ReverbHighpassType);
                if (tmpProgress!=-1)
                {
                    String appendString = " " + cutTypeMap[tmpProgress];
                    onSeekBarDataChanged(reverbHighpassTypeTextView, appendString);
                    reverbHighpassTypeSeekBar.setProgress(tmpProgress);
                }

                final TextView reverbLowpassFreqTextView = (TextView) currentView.findViewById(R.id.ReverbLowpassFreqTextView);
                SeekBar reverbLowpassFreqSeekBar = (SeekBar) currentView.findViewById(R.id.ReverbLowpassFreqSeekBar);
                reverbLowpassFreqSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {
                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {
                    }

                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                        if (fromUser) {
                            //Log.i(TAG, seekBar.toString() + "onProgressChanged " + progress);
                            seekBar.setFocusable(true);
                            seekBar.setFocusableInTouchMode(true);
                            seekBar.requestFocus();
                            seekBar.requestFocusFromTouch();

                            String appendString = " " + lowCutFreqMap[progress];
                            onSeekBarDataChanged(reverbLowpassFreqTextView, appendString);
                            mAudioCardCtrl.reverbBandpassfilterSetup(0xff, 0xff, 0xff, progress);
                            mCardSettings.set(WsdAudioCardCtrl.SettingsName.ReverbLowpassFreq,progress);
                        }
                    }
                });
                tmpProgress = mCardSettings.get(WsdAudioCardCtrl.SettingsName.ReverbLowpassFreq);
                if (tmpProgress!=-1)
                {
                    String appendString = " " + lowCutFreqMap[tmpProgress];
                    onSeekBarDataChanged(reverbLowpassFreqTextView, appendString);
                    reverbLowpassFreqSeekBar.setProgress(tmpProgress);
                }

                final TextView reverbLowpassTypeTextView = (TextView) currentView.findViewById(R.id.ReverbLowpassTypeTextView);
                SeekBar reverbLowpassTypeSeekBar = (SeekBar) currentView.findViewById(R.id.ReverbLowpassTypeSeekBar);
                reverbLowpassTypeSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {
                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {
                    }

                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                        if (fromUser) {
                            //Log.i(TAG, seekBar.toString() + "onProgressChanged " + progress);
                            seekBar.setFocusable(true);
                            seekBar.setFocusableInTouchMode(true);
                            seekBar.requestFocus();
                            seekBar.requestFocusFromTouch();

                            String appendString = " " + cutTypeMap[progress];
                            onSeekBarDataChanged(reverbLowpassTypeTextView, appendString);
                            mAudioCardCtrl.reverbBandpassfilterSetup(0xff, 0xff, progress, 0xff);
                            mCardSettings.set(WsdAudioCardCtrl.SettingsName.ReverbLowpassType,progress);
                        }
                    }
                });
                tmpProgress = mCardSettings.get(WsdAudioCardCtrl.SettingsName.ReverbLowpassType);
                if (tmpProgress!=-1)
                {
                    String appendString = " " + cutTypeMap[tmpProgress];
                    onSeekBarDataChanged(reverbLowpassTypeTextView, appendString);
                    reverbLowpassTypeSeekBar.setProgress(tmpProgress);
                }

                CheckBox reverbPeqSwitchCheckBox = (CheckBox) currentView.findViewById(R.id.ReverbPeqSwitchCheckBox);
                reverbPeqSwitchCheckBox.setOnCheckedChangeListener(new CheckBox.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton button, boolean isChecked) {
                        int enable = isChecked ? 1 : 0;
                        mAudioCardCtrl.reverb5bandPeqEnable(enable);
                        mCardSettings.set(WsdAudioCardCtrl.SettingsName.ReverbPeqSwitch,enable);
                    }
                });
                tmpProgress = mCardSettings.get(WsdAudioCardCtrl.SettingsName.ReverbPeqSwitch);
                if (tmpProgress!=-1)
                {
                    reverbPeqSwitchCheckBox.setChecked(tmpProgress==0?false:true);
                }

                for (int i = 0; i < 5; i++) {
                    reverbPeqGainTextView[i] = (TextView) currentView.findViewById(reverbPeqGainTextViewID[i]);
                    reverbPeqGainSeekBar[i] = (SeekBar) currentView.findViewById(reverbPeqGainSeekBarID[i]);
                    reverbPeqGainSeekBar[i].setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                        @Override
                        public void onStopTrackingTouch(SeekBar seekBar) {
                        }

                        @Override
                        public void onStartTrackingTouch(SeekBar seekBar) {
                        }

                        @Override
                        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                            if (fromUser) {
                                //Log.i(TAG, seekBar.toString() + "onProgressChanged " + progress);
                                seekBar.setFocusable(true);
                                seekBar.setFocusableInTouchMode(true);
                                seekBar.requestFocus();
                                seekBar.requestFocusFromTouch();

                                int currentSeekBarId = seekBar.getId();
                                int seekBarIdx = findArrayIdx(reverbPeqGainSeekBarID, currentSeekBarId);
                                String appendString = String.format(" %.1fdB", (float) (progress - 60) / 5);
                                onSeekBarDataChanged(reverbPeqGainTextView[seekBarIdx], appendString);
                                mAudioCardCtrl.reverb5bandPeqSetup(seekBarIdx + 1, 0xff, 0xff, progress - 60);
                                mCardSettings.set(WsdAudioCardCtrl.SettingsName.ReverbPeqBand1Gain.ordinal()+seekBarIdx,progress);
                            }
                        }
                    });
                    tmpProgress = mCardSettings.get(WsdAudioCardCtrl.SettingsName.ReverbPeqBand1Gain.ordinal()+i);
                    if (tmpProgress!=-1) {
                        reverbPeqGainSeekBar[i].setProgress(tmpProgress);
                        String appendString = String.format(" %.1fdB",(float)(tmpProgress-60)/5);
                        onSeekBarDataChanged(reverbPeqGainTextView[i],appendString);
                    }

                    reverbPeqFreqTextView[i] = (TextView) currentView.findViewById(reverbPeqFreqTextViewID[i]);
                    reverbPeqFreqSeekBar[i] = (SeekBar) currentView.findViewById(reverbPeqFreqSeekBarID[i]);
                    reverbPeqFreqSeekBar[i].setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                        @Override
                        public void onStopTrackingTouch(SeekBar seekBar) {
                        }

                        @Override
                        public void onStartTrackingTouch(SeekBar seekBar) {
                        }

                        @Override
                        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                            if (fromUser) {
                                //Log.i(TAG, seekBar.toString() + "onProgressChanged " + progress);
                                seekBar.setFocusable(true);
                                seekBar.setFocusableInTouchMode(true);
                                seekBar.requestFocus();
                                seekBar.requestFocusFromTouch();

                                int currentSeekBarId = seekBar.getId();
                                int seekBarIdx = findArrayIdx(reverbPeqFreqSeekBarID, currentSeekBarId);
                                String appendString = " " + hzMap[progress] + "Hz";
                                onSeekBarDataChanged(reverbPeqFreqTextView[seekBarIdx], appendString);
                                mAudioCardCtrl.reverb5bandPeqSetup(seekBarIdx + 1, progress + 2, 0xff, 0x7f);
                                mCardSettings.set(WsdAudioCardCtrl.SettingsName.ReverbPeqBand1Freq.ordinal()+seekBarIdx,progress);
                            }
                        }
                    });
                    tmpProgress = mCardSettings.get(WsdAudioCardCtrl.SettingsName.ReverbPeqBand1Freq.ordinal()+i);
                    if (tmpProgress!=-1) {
                        reverbPeqFreqSeekBar[i].setProgress(tmpProgress);
                        String appendString = " " + hzMap[tmpProgress] + "Hz";
                        onSeekBarDataChanged(reverbPeqFreqTextView[i],appendString);
                    }

                    reverbPeqQTextView[i] = (TextView) currentView.findViewById(reverbPeqQTextViewID[i]);
                    reverbPeqQSeekBar[i] = (SeekBar) currentView.findViewById(reverbPeqQSeekBarID[i]);
                    reverbPeqQSeekBar[i].setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                        @Override
                        public void onStopTrackingTouch(SeekBar seekBar) {
                        }

                        @Override
                        public void onStartTrackingTouch(SeekBar seekBar) {
                        }

                        @Override
                        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                            if (fromUser) {
                                //Log.i(TAG, seekBar.toString() + "onProgressChanged " + progress);
                                seekBar.setFocusable(true);
                                seekBar.setFocusableInTouchMode(true);
                                seekBar.requestFocus();
                                seekBar.requestFocusFromTouch();

                                int CurrentSeekBarId = seekBar.getId();
                                int seekBarIdx = findArrayIdx(reverbPeqQSeekBarID, CurrentSeekBarId);
                                String appendString = " " + qMap[progress];
                                onSeekBarDataChanged(reverbPeqQTextView[seekBarIdx], appendString);
                                mAudioCardCtrl.reverb5bandPeqSetup( seekBarIdx + 1, 0xff, progress, 0x7f);
                                mCardSettings.set(WsdAudioCardCtrl.SettingsName.ReverbPeqBand1Q.ordinal()+seekBarIdx,progress);
                            }
                        }
                    });
                    tmpProgress = mCardSettings.get(WsdAudioCardCtrl.SettingsName.ReverbPeqBand1Q.ordinal()+i);
                    if (tmpProgress!=-1) {
                        reverbPeqQSeekBar[i].setProgress(tmpProgress);
                        String appendString = " " + qMap[tmpProgress];
                        onSeekBarDataChanged(reverbPeqQTextView[i],appendString);
                    }
                }
            }
        });
    }

    private void prepareOutputPanel(final int channel,View currentOutputView) {

        final int settingsPerChannel=WsdAudioCardCtrl.SettingsName.RearOutputPeqBand5Gain.ordinal() - WsdAudioCardCtrl.SettingsName.RearOutputDirect.ordinal() + 1;
        int tmpProgress = -1;

        final TextView outputDirectTextView = (TextView)currentOutputView.findViewById(R.id.OutputDirectTextView);
        SeekBar outputDirectSeekBar = (SeekBar)currentOutputView.findViewById(R.id.OutputDirectSeekBar);
        outputDirectSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    //Log.i(TAG, seekBar.toString() + "onProgressChanged " + progress);
                    seekBar.setFocusable(true);
                    seekBar.setFocusableInTouchMode(true);
                    seekBar.requestFocus();
                    seekBar.requestFocusFromTouch();

                    String appendString;
                    if (progress==0) {
                        appendString = " Mute";
                    } else {
                        appendString = String.format(" %.1fdB",Math.floor((mixerDbMap[progress]*10)/10));
                    }
                    onSeekBarDataChanged(outputDirectTextView,appendString);
                    mAudioCardCtrl.mixerMixSetup(channel,progress,0xff,0xff,0xff);
                    mCardSettings.set(WsdAudioCardCtrl.SettingsName.RearOutputDirect.ordinal() + settingsPerChannel*(channel-1),progress);
                }
            }
        });
        tmpProgress = mCardSettings.get(WsdAudioCardCtrl.SettingsName.RearOutputDirect.ordinal() + settingsPerChannel*(channel-1));
        if (tmpProgress!=-1) {
            String appendString;
            if (tmpProgress==0) {
                appendString = " Mute";
            } else {
                appendString = String.format(" %.1fdB",Math.floor((mixerDbMap[tmpProgress]*10)/10));
            }
            onSeekBarDataChanged(outputDirectTextView,appendString);
            outputDirectSeekBar.setProgress(tmpProgress);
        }

        final TextView outputEchoTextView = (TextView)currentOutputView.findViewById(R.id.OutputEchoTextView);
        SeekBar outputEchoSeekBar = (SeekBar)currentOutputView.findViewById(R.id.OutputEchoSeekBar);
        outputEchoSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    //Log.i(TAG, seekBar.toString() + "onProgressChanged " + progress);
                    seekBar.setFocusable(true);
                    seekBar.setFocusableInTouchMode(true);
                    seekBar.requestFocus();
                    seekBar.requestFocusFromTouch();

                    String appendString;
                    if (progress==0) {
                        appendString = " Mute";
                    } else {
                        appendString = String.format(" %.1fdB",Math.floor((mixerDbMap[progress]*10)/10));
                    }
                    onSeekBarDataChanged(outputEchoTextView,appendString);
                    mAudioCardCtrl.mixerMixSetup(channel,0xff,progress,0xff,0xff);
                    mCardSettings.set(WsdAudioCardCtrl.SettingsName.RearOutputEcho.ordinal() + settingsPerChannel*(channel-1),progress);
                }
            }
        });
        tmpProgress = mCardSettings.get(WsdAudioCardCtrl.SettingsName.RearOutputEcho.ordinal() + settingsPerChannel*(channel-1));
        if (tmpProgress!=-1) {
            String appendString;
            if (tmpProgress==0) {
                appendString = " Mute";
            } else {
                appendString = String.format(" %.1fdB",Math.floor((mixerDbMap[tmpProgress]*10)/10));
            }
            onSeekBarDataChanged(outputEchoTextView,appendString);
            outputEchoSeekBar.setProgress(tmpProgress);
        }

        final TextView outputReverbTextView = (TextView)currentOutputView.findViewById(R.id.OutputReverbTextView);
        SeekBar outputReverbSeekBar = (SeekBar)currentOutputView.findViewById(R.id.OutputReverbSeekBar);
        outputReverbSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    //Log.i(TAG, seekBar.toString() + "onProgressChanged " + progress);
                    seekBar.setFocusable(true);
                    seekBar.setFocusableInTouchMode(true);
                    seekBar.requestFocus();
                    seekBar.requestFocusFromTouch();

                    String appendString;
                    if (progress==0) {
                        appendString = " Mute";
                    } else {
                        appendString = String.format(" %.1fdB",Math.floor((mixerDbMap[progress]*10)/10));
                    }
                    onSeekBarDataChanged(outputReverbTextView,appendString);
                    mAudioCardCtrl.mixerMixSetup(channel,0xff,0xff,progress,0xff);
                    mCardSettings.set(WsdAudioCardCtrl.SettingsName.RearOutputReverb.ordinal() + settingsPerChannel*(channel-1),progress);
                }
            }
        });
        tmpProgress = mCardSettings.get(WsdAudioCardCtrl.SettingsName.RearOutputReverb.ordinal() + settingsPerChannel*(channel-1));
        if (tmpProgress!=-1) {
            String appendString;
            if (tmpProgress==0) {
                appendString = " Mute";
            } else {
                appendString = String.format(" %.1fdB",Math.floor((mixerDbMap[tmpProgress]*10)/10));
            }
            onSeekBarDataChanged(outputReverbTextView,appendString);
            outputReverbSeekBar.setProgress(tmpProgress);
        }

        final TextView outputMusicTextView = (TextView)currentOutputView.findViewById(R.id.OutputMusicTextView);
        SeekBar outputMusicSeekBar = (SeekBar)currentOutputView.findViewById(R.id.OutputMusicSeekBar);
        outputMusicSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    //Log.i(TAG, seekBar.toString() + "onProgressChanged " + progress);
                    seekBar.setFocusable(true);
                    seekBar.setFocusableInTouchMode(true);
                    seekBar.requestFocus();
                    seekBar.requestFocusFromTouch();

                    String appendString;
                    if (progress==0) {
                        appendString = " Mute";
                    } else {
                        appendString = String.format(" %.1fdB",Math.floor((mixerDbMap[progress]*10)/10));
                    }
                    onSeekBarDataChanged(outputMusicTextView,appendString);
                    mAudioCardCtrl.mixerMixSetup(channel,0xff,0xff,0xff,progress);
                    mCardSettings.set(WsdAudioCardCtrl.SettingsName.RearOutputMusic.ordinal() + settingsPerChannel*(channel-1),progress);
                }
            }
        });
        tmpProgress = mCardSettings.get(WsdAudioCardCtrl.SettingsName.RearOutputMusic.ordinal() + settingsPerChannel*(channel-1));
        if (tmpProgress!=-1) {
            String appendString;
            if (tmpProgress==0) {
                appendString = " Mute";
            } else {
                appendString = String.format(" %.1fdB",Math.floor((mixerDbMap[tmpProgress]*10)/10));
            }
            onSeekBarDataChanged(outputMusicTextView,appendString);
            outputMusicSeekBar.setProgress(tmpProgress);
        }

        final TextView outputGainTextView = (TextView)currentOutputView.findViewById(R.id.OutputGainTextView);
        SeekBar outputGainSeekBar = (SeekBar)currentOutputView.findViewById(R.id.OutputGainSeekBar);
        outputGainSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    //Log.i(TAG, seekBar.toString() + "onProgressChanged " + progress);
                    seekBar.setFocusable(true);
                    seekBar.setFocusableInTouchMode(true);
                    seekBar.requestFocus();
                    seekBar.requestFocusFromTouch();

                    String appendString;
                    if (progress==0) {
                        appendString = " Mute";
                    } else {
                        appendString = String.format(" %.1fdB",Math.floor((mixerDbMap[progress]*10)/10));
                    }
                    onSeekBarDataChanged(outputGainTextView,appendString);
                    mAudioCardCtrl.outputSetup(channel,progress,0xff,0xff);
                    mCardSettings.set(WsdAudioCardCtrl.SettingsName.RearOutputGain.ordinal() + settingsPerChannel*(channel-1),progress);
                }
            }
        });
        tmpProgress = mCardSettings.get(WsdAudioCardCtrl.SettingsName.RearOutputGain.ordinal() + settingsPerChannel*(channel-1));
        if (tmpProgress!=-1) {
            String appendString;
            if (tmpProgress==0) {
                appendString = " Mute";
            } else {
                appendString = String.format(" %.1fdB",Math.floor((mixerDbMap[tmpProgress]*10)/10));
            }
            onSeekBarDataChanged(outputGainTextView,appendString);
            outputGainSeekBar.setProgress(tmpProgress);
        }

        final TextView outputDelayTextView = (TextView)currentOutputView.findViewById(R.id.OutputDelayTextView);
        SeekBar outputDelaySeekBar = (SeekBar)currentOutputView.findViewById(R.id.OutputDelaySeekBar);
        outputDelaySeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    //Log.i(TAG, seekBar.toString() + "onProgressChanged " + progress);
                    seekBar.setFocusable(true);
                    seekBar.setFocusableInTouchMode(true);
                    seekBar.requestFocus();
                    seekBar.requestFocusFromTouch();

                    String appendString = String.format(" %.1fms",(float)progress/2);
                    onSeekBarDataChanged(outputDelayTextView,appendString);
                    mAudioCardCtrl.outputSetup(channel,0xff,progress,0xff);
                    mCardSettings.set(WsdAudioCardCtrl.SettingsName.RearOutputDelay.ordinal() + settingsPerChannel*(channel-1),progress);
                }
            }
        });
        tmpProgress = mCardSettings.get(WsdAudioCardCtrl.SettingsName.RearOutputDelay.ordinal() + settingsPerChannel*(channel-1));
        if (tmpProgress!=-1) {
            String appendString = String.format(" %.1fms",(float)tmpProgress/2);
            onSeekBarDataChanged(outputDelayTextView,appendString);
            outputDelaySeekBar.setProgress(tmpProgress);
        }

        CheckBox outputPhaseReverseCheckbox = (CheckBox)currentOutputView.findViewById(R.id.OutputPhaseReverseCheckbox);
        outputPhaseReverseCheckbox.setOnCheckedChangeListener(new CheckBox.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton button, boolean isChecked){
                int reverse = isChecked?1:0;
                mAudioCardCtrl.outputSetup(channel,0xff,0xff,reverse);
                mCardSettings.set(WsdAudioCardCtrl.SettingsName.RearOutputPhaseReverse.ordinal() + settingsPerChannel*(channel-1),reverse);
            }
        });
        tmpProgress = mCardSettings.get(WsdAudioCardCtrl.SettingsName.RearOutputPhaseReverse.ordinal() + settingsPerChannel*(channel-1));
        if (tmpProgress!=-1) {
            outputPhaseReverseCheckbox.setChecked(tmpProgress==0?false:true);
        }

        CheckBox outputCompressorSwitchCheckbox = (CheckBox)currentOutputView.findViewById(R.id.OutputCompressorSwitchCheckbox);
        final TextView outputCompressorAttackTextView = (TextView)currentOutputView.findViewById(R.id.OutputCompressorAttackTextView);
        SeekBar outputCompressorAttackSeekBar = (SeekBar)currentOutputView.findViewById(R.id.OutputCompressorAttackSeekBar);
        final TextView outputCompressorReleaseTextView = (TextView)currentOutputView.findViewById(R.id.OutputCompressorReleaseTextView);
        SeekBar outputCompressorReleaseSeekBar = (SeekBar)currentOutputView.findViewById(R.id.OutputCompressorReleaseSeekBar);
        final TextView outputCompressorThresholdTextView = (TextView)currentOutputView.findViewById(R.id.OutputCompressorThresholdTextView);
        SeekBar outputCompressorThresholdSeekBar = (SeekBar)currentOutputView.findViewById(R.id.OutputCompressorThresholdSeekBar);
        final TextView outputCompressorRatioTextView = (TextView)currentOutputView.findViewById(R.id.OutputCompressorRatioTextView);
        SeekBar outputCompressorRatioSeekBar = (SeekBar)currentOutputView.findViewById(R.id.OutputCompressorRatioSeekBar);
        final TextView outputCompressorPostgainTextView = (TextView)currentOutputView.findViewById(R.id.OutputCompressorPostgainTextView);
        SeekBar outputCompressorPostgainSeekBar = (SeekBar)currentOutputView.findViewById(R.id.OutputCompressorPostgainSeekBar);

        outputCompressorSwitchCheckbox.setOnCheckedChangeListener(new CheckBox.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton button, boolean isChecked){
                int enable = isChecked?1:0;
                mAudioCardCtrl.outputSubwoofferCompressorEnable(enable);
                mCardSettings.set(WsdAudioCardCtrl.SettingsName.OutputCompressorSwitch.ordinal(),enable);
            }
        });
        tmpProgress = mCardSettings.get(WsdAudioCardCtrl.SettingsName.OutputCompressorSwitch.ordinal());
        if (tmpProgress!=-1) {
            outputCompressorSwitchCheckbox.setChecked(tmpProgress==0?false:true);
        }

        outputCompressorAttackSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    //Log.i(TAG, seekBar.toString() + "onProgressChanged " + progress);
                    seekBar.setFocusable(true);
                    seekBar.setFocusableInTouchMode(true);
                    seekBar.requestFocus();
                    seekBar.requestFocusFromTouch();

                    String appendString = " " + progress + "ms";
                    onSeekBarDataChanged(outputCompressorAttackTextView,appendString);
                    mAudioCardCtrl.outputSubwoofferCompressorSetup(progress,0xff,0xff,0xff,0xff);
                    mCardSettings.set(WsdAudioCardCtrl.SettingsName.OutputCompressorAttack.ordinal(),progress);
                }
            }
        });
        tmpProgress = mCardSettings.get(WsdAudioCardCtrl.SettingsName.OutputCompressorAttack.ordinal());
        if (tmpProgress!=-1) {
            String appendString = " " + tmpProgress + "ms";
            onSeekBarDataChanged(outputCompressorAttackTextView,appendString);
            outputCompressorAttackSeekBar.setProgress(tmpProgress);
        }

        outputCompressorReleaseSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    //Log.i(TAG, seekBar.toString() + "onProgressChanged " + progress);
                    seekBar.setFocusable(true);
                    seekBar.setFocusableInTouchMode(true);
                    seekBar.requestFocus();
                    seekBar.requestFocusFromTouch();

                    String appendString = " " + progress + "ms";
                    onSeekBarDataChanged(outputCompressorReleaseTextView,appendString);
                    mAudioCardCtrl.outputSubwoofferCompressorSetup(0xff,progress,0xff,0xff,0xff);
                    mCardSettings.set(WsdAudioCardCtrl.SettingsName.OutputCompressorRelease.ordinal(),progress);
                }
            }
        });
        tmpProgress = mCardSettings.get(WsdAudioCardCtrl.SettingsName.OutputCompressorRelease.ordinal());
        if (tmpProgress!=-1) {
            String appendString = " " + tmpProgress + "ms";
            onSeekBarDataChanged(outputCompressorReleaseTextView,appendString);
            outputCompressorReleaseSeekBar.setProgress(tmpProgress);
        }

        outputCompressorThresholdSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    //Log.i(TAG, seekBar.toString() + "onProgressChanged " + progress);
                    seekBar.setFocusable(true);
                    seekBar.setFocusableInTouchMode(true);
                    seekBar.requestFocus();
                    seekBar.requestFocusFromTouch();

                    String appendString = " " + progress;
                    onSeekBarDataChanged(outputCompressorThresholdTextView,appendString);
                    mAudioCardCtrl.outputSubwoofferCompressorSetup(0xff,0xff,progress,0xff,0xff);
                    mCardSettings.set(WsdAudioCardCtrl.SettingsName.OutputCompressorThreshold.ordinal(),progress);
                }
            }
        });
        tmpProgress = mCardSettings.get(WsdAudioCardCtrl.SettingsName.OutputCompressorThreshold.ordinal());
        if (tmpProgress!=-1) {
            String appendString = " " + tmpProgress;
            onSeekBarDataChanged(outputCompressorThresholdTextView,appendString);
            outputCompressorThresholdSeekBar.setProgress(tmpProgress);
        }

        outputCompressorRatioSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    //Log.i(TAG, seekBar.toString() + "onProgressChanged " + progress);
                    seekBar.setFocusable(true);
                    seekBar.setFocusableInTouchMode(true);
                    seekBar.requestFocus();
                    seekBar.requestFocusFromTouch();

                    String appendString = " " + compressorRatioMap[progress];
                    onSeekBarDataChanged(outputCompressorRatioTextView,appendString);
                    mAudioCardCtrl.outputSubwoofferCompressorSetup(0xff,0xff,0xff,progress,0xff);
                    mCardSettings.set(WsdAudioCardCtrl.SettingsName.OutputCompressorRatio.ordinal(),progress);
                }
            }
        });
        tmpProgress = mCardSettings.get(WsdAudioCardCtrl.SettingsName.OutputCompressorRatio.ordinal());
        if (tmpProgress!=-1) {
            String appendString = " " + compressorRatioMap[tmpProgress];
            onSeekBarDataChanged(outputCompressorRatioTextView,appendString);
            outputCompressorRatioSeekBar.setProgress(tmpProgress);
        }

        outputCompressorPostgainSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    //Log.i(TAG, seekBar.toString() + "onProgressChanged " + progress);
                    seekBar.setFocusable(true);
                    seekBar.setFocusableInTouchMode(true);
                    seekBar.requestFocus();
                    seekBar.requestFocusFromTouch();

                    String appendString = " " + progress + "dB";
                    onSeekBarDataChanged(outputCompressorPostgainTextView,appendString);
                    mAudioCardCtrl.outputSubwoofferCompressorSetup(0xff,0xff,0xff,0xff,progress);
                    mCardSettings.set(WsdAudioCardCtrl.SettingsName.OutputCompressorPostgain.ordinal(),progress);
                }
            }
        });
        tmpProgress = mCardSettings.get(WsdAudioCardCtrl.SettingsName.OutputCompressorPostgain.ordinal());
        if (tmpProgress!=-1) {
            String appendString = " " + tmpProgress + "dB";
            onSeekBarDataChanged(outputCompressorPostgainTextView,appendString);
            outputCompressorPostgainSeekBar.setProgress(tmpProgress);
        }

        final TextView outputHighpassFreqTextView = (TextView)currentOutputView.findViewById(R.id.OutputHighpassFreqTextView);
        SeekBar outputHighpassFreqSeekBar = (SeekBar)currentOutputView.findViewById(R.id.OutputHighpassFreqSeekBar);
        outputHighpassFreqSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    //Log.i(TAG, seekBar.toString() + "onProgressChanged " + progress);
                    seekBar.setFocusable(true);
                    seekBar.setFocusableInTouchMode(true);
                    seekBar.requestFocus();
                    seekBar.requestFocusFromTouch();

                    String appendString = " " + highCutFreqMap[progress];
                    onSeekBarDataChanged(outputHighpassFreqTextView,appendString);
                    mAudioCardCtrl.outputBandpassfilterSetup(channel,0xff,progress,0xff,0xff);
                    mCardSettings.set(WsdAudioCardCtrl.SettingsName.RearOutputHighpassFreq.ordinal() + settingsPerChannel*(channel-1),progress);
                }
            }
        });
        tmpProgress = mCardSettings.get(WsdAudioCardCtrl.SettingsName.RearOutputHighpassFreq.ordinal() + settingsPerChannel*(channel-1));
        if (tmpProgress!=-1) {
            String appendString = " " + highCutFreqMap[tmpProgress];
            onSeekBarDataChanged(outputHighpassFreqTextView,appendString);
            outputHighpassFreqSeekBar.setProgress(tmpProgress);
        }

        final TextView outputHighpassTypeTextView = (TextView)currentOutputView.findViewById(R.id.OutputHighpassTypeTextView);
        SeekBar outputHighpassTypeSeekBar = (SeekBar)currentOutputView.findViewById(R.id.OutputHighpassTypeSeekBar);
        outputHighpassTypeSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    //Log.i(TAG, seekBar.toString() + "onProgressChanged " + progress);
                    seekBar.setFocusable(true);
                    seekBar.setFocusableInTouchMode(true);
                    seekBar.requestFocus();
                    seekBar.requestFocusFromTouch();

                    String appendString = " " + cutTypeMap[progress];
                    onSeekBarDataChanged(outputHighpassTypeTextView,appendString);
                    mAudioCardCtrl.outputBandpassfilterSetup(channel,progress,0xff,0xff,0xff);
                    mCardSettings.set(WsdAudioCardCtrl.SettingsName.RearOutputHighpassType.ordinal() + settingsPerChannel*(channel-1),progress);
                }
            }
        });
        tmpProgress = mCardSettings.get(WsdAudioCardCtrl.SettingsName.RearOutputHighpassType.ordinal() + settingsPerChannel*(channel-1));
        if (tmpProgress!=-1) {
            String appendString = " " + cutTypeMap[tmpProgress];
            onSeekBarDataChanged(outputHighpassTypeTextView,appendString);
            outputHighpassTypeSeekBar.setProgress(tmpProgress);
        }

        final TextView outputLowpassFreqTextView = (TextView)currentOutputView.findViewById(R.id.OutputLowpassFreqTextView);
        SeekBar outputLowpassFreqSeekBar = (SeekBar)currentOutputView.findViewById(R.id.OutputLowpassFreqSeekBar);
        outputLowpassFreqSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    //Log.i(TAG, seekBar.toString() + "onProgressChanged " + progress);
                    seekBar.setFocusable(true);
                    seekBar.setFocusableInTouchMode(true);
                    seekBar.requestFocus();
                    seekBar.requestFocusFromTouch();

                    String appendString = " " + lowCutFreqMap[progress];
                    onSeekBarDataChanged(outputLowpassFreqTextView,appendString);
                    mAudioCardCtrl.outputBandpassfilterSetup(channel,0xff,0xff,0xff,progress);
                    mCardSettings.set(WsdAudioCardCtrl.SettingsName.RearOutputLowpassFreq.ordinal() + settingsPerChannel*(channel-1),progress);
                }
            }
        });
        tmpProgress = mCardSettings.get(WsdAudioCardCtrl.SettingsName.RearOutputLowpassFreq.ordinal() + settingsPerChannel*(channel-1));
        if (tmpProgress!=-1) {
            String appendString = " " + lowCutFreqMap[tmpProgress];
            onSeekBarDataChanged(outputLowpassFreqTextView,appendString);
            outputLowpassFreqSeekBar.setProgress(tmpProgress);
        }

        final TextView outputLowpassTypeTextView = (TextView)currentOutputView.findViewById(R.id.OutputLowpassTypeTextView);
        SeekBar outputLowpassTypeSeekBar = (SeekBar)currentOutputView.findViewById(R.id.OutputLowpassTypeSeekBar);
        outputLowpassTypeSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    //Log.i(TAG, seekBar.toString() + "onProgressChanged " + progress);
                    seekBar.setFocusable(true);
                    seekBar.setFocusableInTouchMode(true);
                    seekBar.requestFocus();
                    seekBar.requestFocusFromTouch();

                    String appendString = " " + cutTypeMap[progress];
                    onSeekBarDataChanged(outputLowpassTypeTextView,appendString);
                    mAudioCardCtrl.outputBandpassfilterSetup(channel,0xff,0xff,progress,0xff);
                    mCardSettings.set(WsdAudioCardCtrl.SettingsName.RearOutputLowpassType.ordinal() + settingsPerChannel*(channel-1),progress);
                }
            }
        });
        tmpProgress = mCardSettings.get(WsdAudioCardCtrl.SettingsName.RearOutputLowpassType.ordinal() + settingsPerChannel*(channel-1));
        if (tmpProgress!=-1) {
            String appendString = " " + cutTypeMap[tmpProgress];
            onSeekBarDataChanged(outputLowpassTypeTextView,appendString);
            outputLowpassTypeSeekBar.setProgress(tmpProgress);
        }

        CheckBox outputPeqSwitchCheckBox = (CheckBox)currentOutputView.findViewById(R.id.OutputPeqSwitchCheckBox);
        outputPeqSwitchCheckBox.setOnCheckedChangeListener(new CheckBox.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton button, boolean isChecked){
                int enable = isChecked?1:0;
                mAudioCardCtrl.output5bandPeqEnable(channel,enable);
                mCardSettings.set(WsdAudioCardCtrl.SettingsName.RearOutputPeqSwitch.ordinal() + settingsPerChannel*(channel-1),enable);
            }
        });
        tmpProgress = mCardSettings.get(WsdAudioCardCtrl.SettingsName.RearOutputPeqSwitch.ordinal() + settingsPerChannel*(channel-1));
        if (tmpProgress!=-1) {
            outputPeqSwitchCheckBox.setChecked(tmpProgress==0?false:true);
        }

        for(int i=0;i<5;i++){
            outputPeqGainTextView[i] = (TextView)currentOutputView.findViewById(outputPeqGainTextViewID[i]);
            outputPeqGainSeekBar[i] = (SeekBar)currentOutputView.findViewById(outputPeqGainSeekBarID[i]);
            outputPeqGainSeekBar[i].setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {}
                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {}
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    if (fromUser) {
                        //Log.i(TAG, seekBar.toString() + "onProgressChanged " + progress);
                        seekBar.setFocusable(true);
                        seekBar.setFocusableInTouchMode(true);
                        seekBar.requestFocus();
                        seekBar.requestFocusFromTouch();

                        int currentSeekBarId = seekBar.getId();
                        int seekBarIdx = findArrayIdx(outputPeqGainSeekBarID,currentSeekBarId);
                        String appendString = String.format(" %.1fdB",(float)(progress-60)/5);
                        onSeekBarDataChanged(outputPeqGainTextView[seekBarIdx],appendString);
                        mAudioCardCtrl.output5bandPeqSetup(channel,seekBarIdx+1,0xff,0xff,progress-60);
                        mCardSettings.set(WsdAudioCardCtrl.SettingsName.RearOutputPeqBand1Gain.ordinal() + settingsPerChannel*(channel-1) + seekBarIdx,progress);
                    }
                }
            });
            tmpProgress = mCardSettings.get(WsdAudioCardCtrl.SettingsName.RearOutputPeqBand1Gain.ordinal() + settingsPerChannel*(channel-1) + i);
            if (tmpProgress!=-1) {
                String appendString = String.format(" %.1fdB",(float)(tmpProgress-60)/5);
                onSeekBarDataChanged(outputPeqGainTextView[i],appendString);
                outputPeqGainSeekBar[i].setProgress(tmpProgress);
            }

            outputPeqFreqTextView[i] = (TextView)currentOutputView.findViewById(outputPeqFreqTextViewID[i]);
            outputPeqFreqSeekBar[i] = (SeekBar)currentOutputView.findViewById(outputPeqFreqSeekBarID[i]);
            outputPeqFreqSeekBar[i].setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {}
                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {}
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    if (fromUser) {
                        //Log.i(TAG, seekBar.toString() + "onProgressChanged " + progress);
                        seekBar.setFocusable(true);
                        seekBar.setFocusableInTouchMode(true);
                        seekBar.requestFocus();
                        seekBar.requestFocusFromTouch();

                        int currentSeekBarId = seekBar.getId();
                        int seekBarIdx = findArrayIdx(outputPeqFreqSeekBarID,currentSeekBarId);
                        String appendString = " " + hzMap[progress] + "Hz";
                        onSeekBarDataChanged(outputPeqFreqTextView[seekBarIdx],appendString);
                        mAudioCardCtrl.output5bandPeqSetup(channel,seekBarIdx+1,progress+2,0xff,0x7f);
                        mCardSettings.set(WsdAudioCardCtrl.SettingsName.RearOutputPeqBand1Freq.ordinal() + settingsPerChannel*(channel-1) + seekBarIdx,progress);
                    }
                }
            });
            tmpProgress = mCardSettings.get(WsdAudioCardCtrl.SettingsName.RearOutputPeqBand1Freq.ordinal() + settingsPerChannel*(channel-1) + i);
            if (tmpProgress!=-1) {
                String appendString = " " + hzMap[tmpProgress] + "Hz";
                onSeekBarDataChanged(outputPeqFreqTextView[i],appendString);
                outputPeqFreqSeekBar[i].setProgress(tmpProgress);
            }

            outputPeqQTextView[i] = (TextView)currentOutputView.findViewById(outputPeqQTextViewID[i]);
            outputPeqQSeekBar[i] = (SeekBar)currentOutputView.findViewById(outputPeqQSeekBarID[i]);
            outputPeqQSeekBar[i].setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {}
                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {}
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    if (fromUser) {
                        //Log.i(TAG, seekBar.toString() + "onProgressChanged " + progress);
                        seekBar.setFocusable(true);
                        seekBar.setFocusableInTouchMode(true);
                        seekBar.requestFocus();
                        seekBar.requestFocusFromTouch();

                        int CurrentSeekBarId = seekBar.getId();
                        int seekBarIdx = findArrayIdx(outputPeqQSeekBarID,CurrentSeekBarId);
                        String appendString = " " + qMap[progress];
                        onSeekBarDataChanged(outputPeqQTextView[seekBarIdx],appendString);
                        mAudioCardCtrl.output5bandPeqSetup(channel,seekBarIdx+1,0xff,progress,0x7f);
                        mCardSettings.set(WsdAudioCardCtrl.SettingsName.RearOutputPeqBand1Q.ordinal() + settingsPerChannel*(channel-1) + seekBarIdx,progress);
                    }
                }
            });
            tmpProgress = mCardSettings.get(WsdAudioCardCtrl.SettingsName.RearOutputPeqBand1Q.ordinal() + settingsPerChannel*(channel-1) + i);
            if (tmpProgress!=-1) {
                String appendString = " " + qMap[tmpProgress];
                onSeekBarDataChanged(outputPeqQTextView[i],appendString);
                outputPeqQSeekBar[i].setProgress(tmpProgress);
            }
        }

        if (channel!=3) {
            outputCompressorSwitchCheckbox.setVisibility(View.INVISIBLE);
            outputCompressorAttackTextView.setVisibility(View.INVISIBLE);
            outputCompressorAttackSeekBar.setVisibility(View.INVISIBLE);
            outputCompressorReleaseTextView.setVisibility(View.INVISIBLE);
            outputCompressorReleaseSeekBar.setVisibility(View.INVISIBLE);
            outputCompressorThresholdTextView.setVisibility(View.INVISIBLE);
            outputCompressorThresholdSeekBar.setVisibility(View.INVISIBLE);
            outputCompressorRatioTextView.setVisibility(View.INVISIBLE);
            outputCompressorRatioSeekBar.setVisibility(View.INVISIBLE);
            outputCompressorPostgainTextView.setVisibility(View.INVISIBLE);
            outputCompressorPostgainSeekBar.setVisibility(View.INVISIBLE);
        }
    }

    private void setupOutputPanel() {
        //raw
        ((Button) this.findViewById(R.id.btn_card_reset)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                mAudioCardCtrl.cardReset();
                mCardSettings.reset();
                mCardProfile.reset();
                mCardProfile.set(WsdAudioCardCtrl.SettingsName.SubwooferOutputPeqSwitch,0);
                mCardProfile.set(WsdAudioCardCtrl.SettingsName.OutputCompressorSwitch,0);

                mCardProfile.setToDspFast(mCardSettings);
                mCardSettings.set(mCardProfile);
            }
        });

        ((Button) this.findViewById(R.id.btn_card_save)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                mCardProfile.set(mCardSettings);
                mCardProfile.save();
            }
        });

        ((Button) this.findViewById(R.id.btn_card_load)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                mCardProfile.load();
                mCardProfile.setToDspFast(mCardSettings);
                mCardSettings.set(mCardProfile);
            }
        });

        //professional
        ((Button) this.findViewById(R.id.btn_professional_default)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                mCardProfileProfessional.reset();

                mCardProfileProfessional.set(WsdAudioCardCtrl.SettingsName.SubwooferOutputPeqSwitch,0);
                mCardProfileProfessional.set(WsdAudioCardCtrl.SettingsName.OutputCompressorSwitch,0);

                mCardProfileProfessional.set(WsdAudioCardCtrl.SettingsName.BassCutSwitch,1);
                mCardProfileProfessional.set(WsdAudioCardCtrl.SettingsName.BassCutFrequency,0x0e-1);

                mCardProfileProfessional.set(WsdAudioCardCtrl.SettingsName.NoiseSuppSwitch,1);

                mCardProfileProfessional.set(WsdAudioCardCtrl.SettingsName.CompressorSwitch,1);
                mCardProfileProfessional.set(WsdAudioCardCtrl.SettingsName.CompressorSustain,7);
                mCardProfileProfessional.set(WsdAudioCardCtrl.SettingsName.CompressorOutLevel,30);

                mCardProfileProfessional.set(WsdAudioCardCtrl.SettingsName.MicPeqSwitch,1);
                mCardProfileProfessional.set(WsdAudioCardCtrl.SettingsName.MicPeqBand1Freq,0x0a-2);
                mCardProfileProfessional.set(WsdAudioCardCtrl.SettingsName.MicPeqBand1Gain,65);
                mCardProfileProfessional.set(WsdAudioCardCtrl.SettingsName.MicPeqBand2Freq,0x11-2);
                mCardProfileProfessional.set(WsdAudioCardCtrl.SettingsName.MicPeqBand2Q,0x0d);
                mCardProfileProfessional.set(WsdAudioCardCtrl.SettingsName.MicPeqBand2Gain,73);
                mCardProfileProfessional.set(WsdAudioCardCtrl.SettingsName.MicPeqBand6Freq,0x2e-2);
                mCardProfileProfessional.set(WsdAudioCardCtrl.SettingsName.MicPeqBand6Gain,72);
                mCardProfileProfessional.set(WsdAudioCardCtrl.SettingsName.MicPeqBand7Freq,0x34-2);
                mCardProfileProfessional.set(WsdAudioCardCtrl.SettingsName.MicPeqBand7Gain,102);//98

                mCardProfileProfessional.set(WsdAudioCardCtrl.SettingsName.ReverbInput,87);
                mCardProfileProfessional.set(WsdAudioCardCtrl.SettingsName.ReverbType,1);
                mCardProfileProfessional.set(WsdAudioCardCtrl.SettingsName.ReverbTime,8);
                mCardProfileProfessional.set(WsdAudioCardCtrl.SettingsName.ReverbPreDelay,5);
                mCardProfileProfessional.set(WsdAudioCardCtrl.SettingsName.ReverbOutput,81);

                mAudioCardCtrl.cardReset();
                mCardSettings.reset();

                mCardProfileProfessional.setToDspFast(mCardSettings);
                mCardSettings.set(mCardProfileProfessional);
            }
        });

        //standard
        ((Button) this.findViewById(R.id.btn_standard_default)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                mCardProfileStandard.reset();

                mCardProfileStandard.set(WsdAudioCardCtrl.SettingsName.SubwooferOutputPeqSwitch,0);
                mCardProfileStandard.set(WsdAudioCardCtrl.SettingsName.OutputCompressorSwitch,0);

                mCardProfileStandard.set(WsdAudioCardCtrl.SettingsName.BassCutSwitch,1);
                mCardProfileStandard.set(WsdAudioCardCtrl.SettingsName.BassCutFrequency,0x0e-1);

                mCardProfileStandard.set(WsdAudioCardCtrl.SettingsName.NoiseSuppSwitch,1);

                mCardProfileStandard.set(WsdAudioCardCtrl.SettingsName.CompressorSwitch,1);
                mCardProfileStandard.set(WsdAudioCardCtrl.SettingsName.CompressorSustain,7);
                mCardProfileStandard.set(WsdAudioCardCtrl.SettingsName.CompressorOutLevel,30);

                mCardProfileStandard.set(WsdAudioCardCtrl.SettingsName.MicPeqSwitch,1);
                mCardProfileStandard.set(WsdAudioCardCtrl.SettingsName.MicPeqBand1Freq,0x09-2);
                mCardProfileStandard.set(WsdAudioCardCtrl.SettingsName.MicPeqBand1Gain,65);
                mCardProfileStandard.set(WsdAudioCardCtrl.SettingsName.MicPeqBand2Freq,0x10-2);
                mCardProfileStandard.set(WsdAudioCardCtrl.SettingsName.MicPeqBand2Q,0x0d);
                mCardProfileStandard.set(WsdAudioCardCtrl.SettingsName.MicPeqBand2Gain,82);//78
                mCardProfileStandard.set(WsdAudioCardCtrl.SettingsName.MicPeqBand6Freq,0x2e-2);
                mCardProfileStandard.set(WsdAudioCardCtrl.SettingsName.MicPeqBand6Gain,83);
                mCardProfileStandard.set(WsdAudioCardCtrl.SettingsName.MicPeqBand7Freq,0x37-2);
                mCardProfileStandard.set(WsdAudioCardCtrl.SettingsName.MicPeqBand7Gain,120);//109

                mCardProfileStandard.set(WsdAudioCardCtrl.SettingsName.ReverbInput,80);
                mCardProfileStandard.set(WsdAudioCardCtrl.SettingsName.ReverbType,2);
                mCardProfileStandard.set(WsdAudioCardCtrl.SettingsName.ReverbTime,10);
                mCardProfileStandard.set(WsdAudioCardCtrl.SettingsName.ReverbOutput,86);

                mCardProfileStandard.set(WsdAudioCardCtrl.SettingsName.EchoInput,80);
                mCardProfileStandard.set(WsdAudioCardCtrl.SettingsName.EchoFeedbackLevel,80);
                mCardProfileStandard.set(WsdAudioCardCtrl.SettingsName.EchoTime,15);

                mAudioCardCtrl.cardReset();
                mCardSettings.reset();

                mCardProfileStandard.setToDspFast(mCardSettings);
                mCardSettings.set(mCardProfileStandard);
            }
        });

        //for recording
        ((Button) this.findViewById(R.id.btn_recording_default)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                mCardProfileRecording.reset();

                mCardProfileRecording.set(WsdAudioCardCtrl.SettingsName.SubwooferOutputPeqSwitch,0);
                mCardProfileRecording.set(WsdAudioCardCtrl.SettingsName.OutputCompressorSwitch,0);

                mCardProfileRecording.set(WsdAudioCardCtrl.SettingsName.BassCutSwitch,1);
                mCardProfileRecording.set(WsdAudioCardCtrl.SettingsName.BassCutFrequency,0x0e-1);

                mCardProfileRecording.set(WsdAudioCardCtrl.SettingsName.NoiseSuppSwitch,1);

                mCardProfileRecording.set(WsdAudioCardCtrl.SettingsName.CompressorSwitch,1);
                mCardProfileRecording.set(WsdAudioCardCtrl.SettingsName.CompressorSustain,7);
                mCardProfileRecording.set(WsdAudioCardCtrl.SettingsName.CompressorOutLevel,30);

                mCardProfileRecording.set(WsdAudioCardCtrl.SettingsName.MicPeqSwitch,1);
                mCardProfileRecording.set(WsdAudioCardCtrl.SettingsName.MicPeqBand2Q,0x0d);
                mCardProfileRecording.set(WsdAudioCardCtrl.SettingsName.MicPeqBand2Gain,78);
                mCardProfileRecording.set(WsdAudioCardCtrl.SettingsName.MicPeqBand6Freq,0x2f-2);
                mCardProfileRecording.set(WsdAudioCardCtrl.SettingsName.MicPeqBand6Gain,74);
                mCardProfileRecording.set(WsdAudioCardCtrl.SettingsName.MicPeqBand7Freq,0x36-2);
                mCardProfileRecording.set(WsdAudioCardCtrl.SettingsName.MicPeqBand7Gain,108);//97

                mCardProfileRecording.set(WsdAudioCardCtrl.SettingsName.ReverbInput,48);
                mCardProfileRecording.set(WsdAudioCardCtrl.SettingsName.ReverbType,3);
                mCardProfileRecording.set(WsdAudioCardCtrl.SettingsName.ReverbTime,9);
                mCardProfileRecording.set(WsdAudioCardCtrl.SettingsName.ReverbPreDelay,16);
                mCardProfileRecording.set(WsdAudioCardCtrl.SettingsName.ReverbOutput,80);

                mAudioCardCtrl.cardReset();
                mCardSettings.reset();

                mCardProfileRecording.setToDspFast(mCardSettings);
                mCardSettings.set(mCardProfileRecording);
            }
        });

        //easy enjoy
        ((Button) this.findViewById(R.id.btn_enjoy_default)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                mCardProfileEnjoy.reset();

                mCardProfileEnjoy.set(WsdAudioCardCtrl.SettingsName.SubwooferOutputPeqSwitch,0);
                mCardProfileEnjoy.set(WsdAudioCardCtrl.SettingsName.OutputCompressorSwitch,0);

                mCardProfileEnjoy.set(WsdAudioCardCtrl.SettingsName.BassCutSwitch,1);
                mCardProfileEnjoy.set(WsdAudioCardCtrl.SettingsName.BassCutFrequency,0x0e-1);

                mCardProfileEnjoy.set(WsdAudioCardCtrl.SettingsName.NoiseSuppSwitch,1);

                mCardProfileEnjoy.set(WsdAudioCardCtrl.SettingsName.CompressorSwitch,1);
                mCardProfileEnjoy.set(WsdAudioCardCtrl.SettingsName.CompressorSustain,7);
                mCardProfileEnjoy.set(WsdAudioCardCtrl.SettingsName.CompressorOutLevel,30);

                mCardProfileEnjoy.set(WsdAudioCardCtrl.SettingsName.MicPeqSwitch,1);
                mCardProfileEnjoy.set(WsdAudioCardCtrl.SettingsName.MicPeqBand1Freq,0x08-2);
                mCardProfileEnjoy.set(WsdAudioCardCtrl.SettingsName.MicPeqBand1Gain,97);
                mCardProfileEnjoy.set(WsdAudioCardCtrl.SettingsName.MicPeqBand2Freq,0x10-2);
                mCardProfileEnjoy.set(WsdAudioCardCtrl.SettingsName.MicPeqBand2Q,0x0d);
                mCardProfileEnjoy.set(WsdAudioCardCtrl.SettingsName.MicPeqBand2Gain,83);
                mCardProfileEnjoy.set(WsdAudioCardCtrl.SettingsName.MicPeqBand4Gain,58);
                mCardProfileEnjoy.set(WsdAudioCardCtrl.SettingsName.MicPeqBand6Freq,0x2e-2);
                mCardProfileEnjoy.set(WsdAudioCardCtrl.SettingsName.MicPeqBand6Gain,83);
                mCardProfileEnjoy.set(WsdAudioCardCtrl.SettingsName.MicPeqBand7Freq,0x37-2);
                mCardProfileEnjoy.set(WsdAudioCardCtrl.SettingsName.MicPeqBand7Gain,120);//117

                mCardProfileEnjoy.set(WsdAudioCardCtrl.SettingsName.ReverbInput,80);
                mCardProfileEnjoy.set(WsdAudioCardCtrl.SettingsName.ReverbType,2);
                mCardProfileEnjoy.set(WsdAudioCardCtrl.SettingsName.ReverbTime,10);
                mCardProfileEnjoy.set(WsdAudioCardCtrl.SettingsName.ReverbTone,6);
                mCardProfileEnjoy.set(WsdAudioCardCtrl.SettingsName.ReverbOutput,86);

                mCardProfileEnjoy.set(WsdAudioCardCtrl.SettingsName.EchoInput,85);
                mCardProfileEnjoy.set(WsdAudioCardCtrl.SettingsName.EchoFeedbackLevel,80);
                mCardProfileEnjoy.set(WsdAudioCardCtrl.SettingsName.EchoTime,17);
                mCardProfileEnjoy.set(WsdAudioCardCtrl.SettingsName.EchoTap1Level,114);
                mCardProfileEnjoy.set(WsdAudioCardCtrl.SettingsName.EchoTap1Pan,127);
                mCardProfileEnjoy.set(WsdAudioCardCtrl.SettingsName.EchoTap2Time,0);
                mCardProfileEnjoy.set(WsdAudioCardCtrl.SettingsName.EchoTap2Level,110);
                mCardProfileEnjoy.set(WsdAudioCardCtrl.SettingsName.EchoTap2Pan,7);
                mCardProfileEnjoy.set(WsdAudioCardCtrl.SettingsName.EchoFeedbackLevel,80);
                mCardProfileEnjoy.set(WsdAudioCardCtrl.SettingsName.EchoOutput,75);

                mAudioCardCtrl.cardReset();
                mCardSettings.reset();

                mCardProfileEnjoy.setToDspFast(mCardSettings);
                mCardSettings.set(mCardProfileEnjoy);
            }
        });


        ((Button) this.findViewById(R.id.btn_slot1_save)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                mCardProfile1.set(mCardSettings);
                mCardProfile1.save();
            }
        });

        ((Button) this.findViewById(R.id.btn_slot1_load)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                mCardProfile1.load();
                mAudioCardCtrl.cardReset();
                mCardSettings.reset();
                mCardProfile1.setToDspFast(mCardSettings);
                mCardSettings.set(mCardProfile1);
            }
        });

        ((Button) this.findViewById(R.id.btn_slot2_save)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                mCardProfile2.set(mCardSettings);
                mCardProfile2.save();
            }
        });

        ((Button) this.findViewById(R.id.btn_slot2_load)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                mCardProfile2.load();
                mAudioCardCtrl.cardReset();
                mCardSettings.reset();
                mCardProfile2.setToDspFast(mCardSettings);
                mCardSettings.set(mCardProfile2);
            }
        });

        ((Button) this.findViewById(R.id.btn_slot3_save)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                mCardProfile3.set(mCardSettings);
                mCardProfile3.save();
            }
        });

        ((Button) this.findViewById(R.id.btn_slot3_load)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                mCardProfile3.load();
                mAudioCardCtrl.cardReset();
                mCardSettings.reset();
                mCardProfile3.setToDspFast(mCardSettings);
                mCardSettings.set(mCardProfile3);
            }
        });

        ((Button) this.findViewById(R.id.btn_slot4_save)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                mCardProfile4.set(mCardSettings);
                mCardProfile4.save();
            }
        });

        ((Button) this.findViewById(R.id.btn_slot4_load)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                mCardProfile4.load();
                mAudioCardCtrl.cardReset();
                mCardSettings.reset();
                mCardProfile4.setToDspFast(mCardSettings);
                mCardSettings.set(mCardProfile4);
            }
        });

        ((Button) this.findViewById(R.id.btn_front_output)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                LayoutInflater layoutInflater = LayoutInflater.from(MainActivity.this);
                View currentOutputView = layoutInflater.inflate(R.layout.view_output, null);
                builder.setView(currentOutputView);
                builder.setTitle("Front Output Panel");
                builder.show();
                final int channel = 2;
                prepareOutputPanel(channel,currentOutputView);

            }
        });

        ((Button) this.findViewById(R.id.btn_center_output)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                LayoutInflater layoutInflater = LayoutInflater.from(MainActivity.this);
                View currentOutputView = layoutInflater.inflate(R.layout.view_output, null);
                builder.setView(currentOutputView);
                builder.setTitle("Center Output Panel");
                builder.show();
                final int channel = 4;
                prepareOutputPanel(channel,currentOutputView);
            }
        });

        ((Button) this.findViewById(R.id.btn_sub_output)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                LayoutInflater layoutInflater = LayoutInflater.from(MainActivity.this);
                View currentOutputView = layoutInflater.inflate(R.layout.view_output, null);
                builder.setView(currentOutputView);
                builder.setTitle("Subwoofer Output Panel");
                builder.show();
                final int channel = 3;
                prepareOutputPanel(channel,currentOutputView);
            }
        });

        ((Button) this.findViewById(R.id.btn_rear_output)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                LayoutInflater layoutInflater = LayoutInflater.from(MainActivity.this);
                View currentOutputView = layoutInflater.inflate(R.layout.view_output, null);
                builder.setView(currentOutputView);
                builder.setTitle("Rear Output Panel");
                builder.show();
                final int channel = 1;
                prepareOutputPanel(channel,currentOutputView);
            }
        });
    }

    private int copyRecordFile(String inFilename, String outFilename, boolean overlay) {
        File srcFile = new File(inFilename);
        // 判断源文件是否存在
        if (!srcFile.exists()) {
            Log.e(TAG, "source file not exist");
            return -1;
        } else if (!srcFile.isFile()) {
            Log.e(TAG, "source is not a file");
            return -1;
        }

        // 判断目标文件是否存在
        File destFile = new File(outFilename);
        if (destFile.exists()) {
            // 如果目标文件存在并允许覆盖
            if (overlay) {
                // 删除已经存在的目标文件，无论目标文件是目录还是单个文件
                new File(outFilename).delete();
            }
        } else {
            // 如果目标文件所在目录不存在，则创建目录
            if (!destFile.getParentFile().exists()) {
                // 目标文件所在目录不存在
                if (!destFile.getParentFile().mkdirs()) {
                    // 复制文件失败：创建目标文件所在目录失败
                    Log.e(TAG, "source mkdirs fail");
                    return -1;
                }
            }
        }

        FileInputStream in = null;
        FileOutputStream out = null;

        byte[] data = new byte[4096];

        try {
            in = new FileInputStream(inFilename);
            out = new FileOutputStream(outFilename);

            while (in.read(data) != -1) {
                out.write(data);
            }
            out.getFD().sync();
            in.close();
            out.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }

    @Override
    protected void onPause() {
        Log.d(TAG, "onPause()");
        super.onPause();

        stopPlayFile();
        stopRecordAudio();
    }

    @Override
    protected void onResume() {
        Log.d(TAG, "onResume()");
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        Log.d(TAG, "onDestroy()");
        mAudioCardCtrl.audioCardDeinit();
        super.onDestroy();
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return false;
    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub

        if (v == mMediaPlayerView) {
            // TODO Auto-generated method stub
            if ((mMediaPlayer != null) && mMediaPlayer.isPlaying()) {
                RelativeLayout.LayoutParams parms = (RelativeLayout.LayoutParams) mMediaPlayerView.getLayoutParams();
                if (parms != null) {
                    parms.width = 640;
                    parms.height = 360;
                    parms.leftMargin = 0;
                    parms.rightMargin = 0;
                    mMediaPlayerView.setLayoutParams(parms);
                }
            }
        } else if (v == mPlayBtn) {
            if (videoPathList.isEmpty()) {
                Log.d(TAG, "no songs");
            } else {
                if (videoIndex >= videoPathList.size()) {
                    videoIndex = 0;
                }
                startPlayFile(videoPathList.get(videoIndex++));
            }
        } else if(v == mCircelBtn) {
            if (mMediaPlayer != null && mMediaPlayer.isPlaying()) {
                int tIndex = 0;
                if (0 == videoIndex) {
                    tIndex = 0;
                } else {
                    tIndex = videoIndex - 1;
                }
                startPlayFile(videoPathList.get(tIndex));
            }
        } else if(v == mSuspendBtn) {
            if (tpause == true) {
                tpause = false;
            } else {
                tpause = true;
            }
            pausePlayFile(tpause);
        } else if(v == mStopBtn) {
            stopPlayFile();
        } else if (v == mMuteSwitchBtn) {
            if (mMute == 0) {
                mVolumeBeforeMute = mCardSettings.get(WsdAudioCardCtrl.SettingsName.MusicInputGain);
                mCardSettings.set(WsdAudioCardCtrl.SettingsName.MusicInputGain,0);
                mAudioCardCtrl.musicInputLevelSetup(0);
                mMuteSwitchBtn.setText("恢复");
                mMute = 1;
            } else {
                mCardSettings.set(WsdAudioCardCtrl.SettingsName.MusicInputGain,mVolumeBeforeMute);
                mAudioCardCtrl.musicInputLevelSetup(mVolumeBeforeMute);
                mMuteSwitchBtn.setText("静音");
                mMute = 0;
            }
        } else if (v == mRecordStartBtn) {
            if (mRecordStartStop == 0) {
                Log.i(TAG, "record start");
                mRecordStartStop = 1;
                //startRecordAudio(active_data_storage_path + mRecordFile, active_data_storage_path + RecTempFile,  MediaRecorder.AudioSource.CAMCORDER);
                startRecordAudio(active_data_storage_path + mRecordFile, active_data_storage_path + RecTempFile, MediaRecorder.AudioSource.MIC);
            }
        } else if (v == mRecordStopBtn) {
            if (mRecordStartStop == 1) {
                Log.i(TAG, "record stop");
                mRecordStartStop = 0;
                stopRecordAudio();
            }
        } else if (v == mRecordPlayBtn) {
            if (mRecordStartStop == 1) {
                Log.i(TAG, "record stop");
                mRecordStartStop = 0;
                stopRecordAudio();
            }
            Log.i(TAG, "play start");
            startPlayFile(active_data_storage_path + "/myrecord.wav");
        }  else if (v == mRecordPlayLeftBtn) {
            if (mRecordStartStop == 1) {
                Log.i(TAG, "record stop first");
                mRecordStartStop = 0;
                stopRecordAudio();
            }
            Log.i(TAG, "play left start");
            startPlayFile(active_data_storage_path + "/L.wav");
        } else if (v == mRecordPlayRightBtn) {
            if (mRecordStartStop == 1) {
                Log.i(TAG, "record stop first");
                mRecordStartStop = 0;
                stopRecordAudio();
            }
            Log.i(TAG, "play right start");
            startPlayFile(active_data_storage_path + "/R.wav");
        } else if (v == mRecordSaveBtn) {
            if (mRecordStartStop == 1) {
                Log.i(TAG, "record stop first");
                mRecordStartStop = 0;
                stopRecordAudio();
            }
            mUsbSaveProgress = ProgressDialog.show(this, "save to usb storage", "please wait......");

            /* 开启一个新线程，在新线程里执行耗时的方法 */
            new Thread(new Runnable() {
                @Override
                public void run() {
                    String inFilePath = active_data_storage_path + mRecordFile;
                    String outFilePath = active_usb_storage_path + mRecordFile;
                    copyRecordFile(inFilePath, outFilePath, true);
                    inFilePath = active_data_storage_path + "/L.wav";
                    outFilePath = active_usb_storage_path + "/L.wav";
                    copyRecordFile(inFilePath, outFilePath, true);
                    inFilePath = active_data_storage_path + "/R.wav";
                    outFilePath = active_usb_storage_path + "/R.wav";
                    copyRecordFile(inFilePath, outFilePath, true);
                    mhandler.sendEmptyMessage(3);// 执行耗时的方法之后发送消给handler
                }
            }).start();
        }
    }

    Handler mhandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    Log.e(TAG, "^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^ mhandler.handleMessage,0");
                    break;
                case 1:
                    Log.e(TAG, "^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^ mhandler.handleMessage,1");
                    break;
                case 2:
                    Log.e(TAG, "^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^ mhandler.handleMessage, dimiss mRecordSaveProgress");
                    mRecordSaveProgress.dismiss();// 关闭ProgressDialog
                    break;
                case 3:
                    Log.e(TAG, "^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^ mhandler.handleMessage, dimiss mUsbSaveProgress");
                    mUsbSaveProgress.dismiss();// 关闭ProgressDialog
                    break;
            }
        }
    };

    private void getVideoPathFromUSB() {
        videoPathList = new ArrayList<String>();
        String videoPath = Environment.getExternalStorageDirectory().toString();

        Log.i(TAG, videoPath);

        if (!videoPath.isEmpty()) {
            File fileUSB = new File(videoPath);
            if (fileUSB.isDirectory()) {
                File[] files = fileUSB.listFiles();
                for (int i = 0; i < files.length; i++) {
                    File file = files[i];
                    if (checkIsVideoFile(file.getPath())) {
                        videoPathList.add(file.getPath());
                    }
                }
            }
        }

        String videoPathUsb = active_usb_storage_path;
        File fileSD = new File(videoPathUsb);
        if (fileSD.isDirectory()){
            File[] files1 = fileSD.listFiles();
            for (int i = 0; i < files1.length; i++) {
                 File file = files1[i];
                 if (checkIsVideoFile(file.getPath())) {
                     videoPathList.add(file.getPath());
                 }
            }
        }
        return;
    }

    private boolean checkIsVideoFile(String fName) {
        boolean isVideoFile = false;

        String FileEnd = fName.substring(fName.lastIndexOf(".") + 1,
                fName.length()).toLowerCase();
        if (FileEnd.equals("mpg") || FileEnd.equals("mkv")
                || FileEnd.equals("ape") || FileEnd.equals("mp3")
                || FileEnd.equals("flac") || FileEnd.equals("mp4")
                || FileEnd.equals("ts")) {
            isVideoFile = true;
        } else {
            isVideoFile = false;
        }
        return isVideoFile;
    }

    private void startPlayFile(String videoPath) {
        try {
            Log.i(TAG, this + ":" + "startPlayback, setDataSource=" + videoPath);

            if (mMediaPlayer != null) {
                mMediaPlayer.release();
                mMediaPlayer = null;
            }

            mMediaPlayer = new MediaPlayer();
            SurfaceHolder holder = mMediaPlayerView.getHolder();

            try {
                boolean isPlaying = mMediaPlayer.isPlaying();
                mMediaPlayer.pause();

                holder.setFormat(PixelFormat.RGBA_8888);
                mMediaPlayer.setDisplay(holder);

                if (isPlaying) {
                    mMediaPlayer.start();
                }
            } catch (IllegalArgumentException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            // 设置播放完成侦听器
            mMediaPlayer.setOnCompletionListener(this);
            // 设置播放错误侦听器
            mMediaPlayer.setOnErrorListener(this);
            mMediaPlayer.setDisplay(holder);
            mMediaPlayer.setDataSource(videoPath);
            mMediaPlayer.prepare();
            mMediaPlayer.start();
            mMediaPlayer.setLooping(true);
            setMusicVolume();

            Log.i(TAG,videoPath);

            String FileEnd = videoPath.substring(videoPath.lastIndexOf("/") + 1,
                    videoPath.length()).toLowerCase();
            //mSongNameView.setText(FileEnd,0,FileEnd.length());
            mSongNameView.setText(FileEnd);

        } catch (IllegalArgumentException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (SecurityException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IllegalStateException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    private void stopPlayFile() {
        if (mMediaPlayer != null) {
            mMediaPlayer.release();
            mMediaPlayer = null;
        }
        return;
    }

    private void pausePlayFile(boolean tpause)
    {
        if (mMediaPlayer == null)
        {
            return;
        } else {
            if (tpause == false) {
                mMediaPlayer.pause();
            } else {
                mMediaPlayer.start();
            }
        }
        return;
    }

    private void startRecordAudio(String recordPath,String tmpPath, int recordSource) {
        File saveFilePath = new File(recordPath);
        try {
            saveFilePath.delete();
            saveFilePath.createNewFile();
        } catch (IOException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
            return;
        }
        mRecordStartStop = 1;

        if (mWavRecorder == null) {
            mWavRecorder = new WavRecorder();
        }

        mWavRecorder.startRecorder(
                recordSource,
                48000,
                AudioFormat.CHANNEL_IN_MONO,
                AudioFormat.ENCODING_PCM_16BIT,
                recordPath,
                tmpPath);
    }

    private void stopRecordAudio() {
        mRecordStartStop = 0;

        if (mWavRecorder != null) {
            mRecordSaveProgress = ProgressDialog.show(this, "record save", "please wait......");
            mWavRecorder.stopRecorder(mhandler);
            mWavRecorder = null;
        }
    }

    @SuppressLint("NewApi")
    private void switchAudioTrack() {
        if (mSelectedAudioTrackIndex == 0) {
            mSelectedAudioTrackIndex = 1;
            if (mMediaPlayer != null) {
                mMediaPlayer.pause();
            }
        } else {
            mSelectedAudioTrackIndex = 0;
            if (mMediaPlayer != null) {
                mMediaPlayer.start();
            }
        }
    }

    private void switchAudioLeftRight() {
        if (mSelectedAudioTrackIndex == 5) {
            mSelectedAudioTrackIndex = 6;
        } else {
            mSelectedAudioTrackIndex = 5;
        }

        if (mMediaPlayer != null) {
            Parcel request = Parcel.obtain();
            Parcel reply = Parcel.obtain();

            request.writeInterfaceToken("android.media.IMediaPlayer");
            request.writeInt(5006);
            request.writeInt(mSelectedAudioTrackIndex);

            try {
                Class<?> cls = mMediaPlayer.getClass();
                Method method = cls.getDeclaredMethod("invoke", Parcel.class, Parcel.class);
                method.setAccessible(true); //如果隐藏接口是public的, 这句可以不要
                method.invoke(mMediaPlayer, request, reply);
            } catch (Exception e) {
                e.printStackTrace();
            }

            request.recycle();
            reply.recycle();
        }
    }

    @SuppressLint("NewApi")
    private int getAudioTrackCount() {
        return 2;
		/*
		int nAudioTrackCount = 0;
		try
        {
            TrackInfo[] trackInfos = mMediaPlayer.getTrackInfo();

            if (trackInfos != null && trackInfos.length > 0)
            {
                for (int i = 0; i < trackInfos.length; i++)
                {
                    TrackInfo info = trackInfos[i];

                    if (info.getTrackType() == TrackInfo.MEDIA_TRACK_TYPE_AUDIO)
                    {
                    	nAudioTrackCount++;
                    }
                }
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

		Log.i(TAG, "audio track count = "+nAudioTrackCount);	
		
		return nAudioTrackCount;*/
    }

    private void setAC3DTSPassThrough() {
        File path = new File("/data/audiopassthrough");
        if (!path.isDirectory()) {
            path.mkdir();
        }

        File file = new File("/data/audiopassthrough/enable");
        if (file.isFile()) {
            Log.i(TAG, "audiopassthrough change to disable");
            file.delete();
        } else {
            try {
                file.createNewFile();
                Log.i(TAG, "audiopassthrough change to enable");
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                Log.i(TAG, "audiopassthrough change to enable failed!");
            }
        }
    }

    private void setMusicVolume() {
        if (mDefaultMusicVolume >= 0 && mDefaultMusicVolume <= 100) {
            if (mMediaPlayer != null) {
                Parcel request = Parcel.obtain();
                Parcel reply = Parcel.obtain();

                request.writeInterfaceToken("android.media.IMediaPlayer");
                request.writeInt(5020);
                request.writeInt(mDefaultMusicVolume);

                try {
                    Class<?> cls = mMediaPlayer.getClass();
                    Method method = cls.getDeclaredMethod("invoke", Parcel.class, Parcel.class);
                    method.setAccessible(true); //如果隐藏接口是public的, 这句可以不要
                    method.invoke(request, reply);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                request.recycle();
                reply.recycle();
            }
        }
    }

    // 播放完毕
    public void onCompletion(MediaPlayer mp) {
        // TODO Auto-generated method stub
        Log.i(TAG, this + ":" + "mMediaPlayer, onCompletion, position=" + mMediaPlayer.getCurrentPosition() + " duration=" + mMediaPlayer.getDuration());
    }

    public boolean onError(MediaPlayer mp, int what, int extra) {
        Log.i(TAG, this + ":" + "mMediaPlayer, onError, position=" + mMediaPlayer.getCurrentPosition() + " duration=" + mMediaPlayer.getDuration());
        return false;
    }

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("Main Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        AppIndex.AppIndexApi.start(client, getIndexApiAction());
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.end(client, getIndexApiAction());
        client.disconnect();
    }
}
