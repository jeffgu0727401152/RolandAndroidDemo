package com.example.testmediaplayer;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaRecorder;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

public class WavRecorder {

    private static final String TAG = "WAVRECORDER";

    private AudioRecord mAudioRecord = null;
    private int mAudioSource = MediaRecorder.AudioSource.MIC;
    private int mSampleRate = 48000;
    private int mChannelCount = AudioFormat.CHANNEL_IN_STEREO;// 设置声道  
    private int mEncodingBitRate = AudioFormat.ENCODING_PCM_16BIT;// 设置编码  
    private final int mRecorderBPP = 16;
    private int mRecBufSize = 0;
    private boolean mIsRecording = false;// 录音标志位

    private String mTempFilePath = "/sdcard/Music/temp.raw";// 临时文件的路径
    private String mTempLFilePath = "/sdcard/L.raw";// 临时文件的路径
    private String mTempRFilePath = "/sdcard/R.raw";// 临时文件的路径

    private String mSaveFilePath = "/sdcard/myrecord.wav";// 文件的路径
    private String mSaveLFilePath = "/sdcard/L.wav";// 文件的路径
    private String mSaveRFilePath = "/sdcard/R.wav";// 文件的路径
    private Thread mRecordThread = null;//录音线程  

    public void WavRecorder()
    {}

    public void startRecorder(int nAudioSource, int nSampleRate, int nChannelType,
                              int nEncodingBitRate, String sSaveFilePath, String sTmpFilePath) {

        mAudioSource = nAudioSource;
        mSampleRate = nSampleRate;
        mEncodingBitRate = nEncodingBitRate;
        mSaveFilePath = sSaveFilePath;
        mTempFilePath = sTmpFilePath;

        switch (nChannelType) {
            case AudioFormat.CHANNEL_IN_DEFAULT: // AudioFormat.CHANNEL_CONFIGURATION_DEFAULT
            case AudioFormat.CHANNEL_IN_MONO:
            case AudioFormat.CHANNEL_CONFIGURATION_MONO:
                mChannelCount = 1;
                break;
            case AudioFormat.CHANNEL_IN_STEREO:
            case AudioFormat.CHANNEL_CONFIGURATION_STEREO:
            case (AudioFormat.CHANNEL_IN_FRONT | AudioFormat.CHANNEL_IN_BACK):
                mChannelCount = 2;
                break;
            case AudioFormat.CHANNEL_INVALID:
            default:
                mChannelCount = 1;
                Log.e("TAG", "getMinBufferSize(): Invalid channel configuration.");
        }

        mRecBufSize = AudioRecord.getMinBufferSize(
                mSampleRate, nChannelType,
                mEncodingBitRate);
        mAudioRecord = new AudioRecord(
                mAudioSource,
                mSampleRate, nChannelType,
                mEncodingBitRate,
                mRecBufSize);
        mAudioRecord.startRecording();
        mIsRecording = true;
        mRecordThread = new Thread(new Runnable() {
            public void run() {
                writeAudioDataToFile();
            }
        }, "AudioRecorder Thread");
        mRecordThread.start();
    }

    public void stopRecorder(final Handler handler) {
        if (null != mAudioRecord) {
            mIsRecording = false;
            try {
                mRecordThread.join();
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            mAudioRecord.stop();
            mAudioRecord.release();
            mAudioRecord = null;
            mRecordThread = null;
        }

        /* 开启一个新线程，在新线程里执行耗时的方法 */
        new Thread(new Runnable() {
            @Override
            public void run() {
                copyWaveFile(mTempFilePath, mSaveFilePath);
                copyWaveFile(mTempLFilePath, mSaveLFilePath);
                copyWaveFile(mTempRFilePath, mSaveRFilePath);
                deleteTempFile(mTempFilePath);
                deleteTempFile(mTempLFilePath);
                deleteTempFile(mTempRFilePath);
                handler.sendEmptyMessage(2);// 执行耗时的方法之后发送消给handler
            }

        }).start();
    }

    // 将音频数据写入文件  
    private void writeAudioDataToFile() {
        byte data[] = new byte[mRecBufSize * 2];
        byte L_data[] = new byte[mRecBufSize * 2];
        byte R_data[] = new byte[mRecBufSize * 2];
        FileOutputStream os = null;
        FileOutputStream os_L = null;
        FileOutputStream os_R = null;

        try {
            Log.e(TAG," " + mTempFilePath);
            os = new FileOutputStream(mTempFilePath);
            os_L = new FileOutputStream(mTempLFilePath);
            os_R = new FileOutputStream(mTempRFilePath);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        int read = 0;
        if (null != os) {
            while (mIsRecording) {
                read = mAudioRecord.read(data, 0, mRecBufSize * 2);
                if (AudioRecord.ERROR_INVALID_OPERATION != read) {
                    try {
                        for (int i=0;i*2<read ;i++)
                        {
                            if (i%2==0) {
                                L_data[2*i] = data[2*i];
                                L_data[2*i + 1] = data[2*i + 1];
                                L_data[2*i + 2] = data[2*i];
                                L_data[2*i + 3] = data[2*i + 1];
                            } else {
                                R_data[2*i-2] = data[2*i];
                                R_data[2*i -1] = data[2*i + 1];
                                R_data[2*i] = data[2*i];
                                R_data[2*i + 1] = data[2*i + 1];
                            }
                        }
                        os.write(data, 0, read);
                        os_L.write(L_data, 0, read);
                        os_R.write(R_data, 0, read);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            try {
                os.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    // 删除临时文件  
    private void deleteTempFile(String filePath) {
        File file = new File(filePath);
        file.delete();
    }

    private void copyWaveFile(String inFilename, String outFilename) {
        FileInputStream in = null;
        FileOutputStream out = null;

        long totalAudioLen = 0;
        long totalDataLen;
        long byteRate = mRecorderBPP * mSampleRate * mChannelCount / 8;

        byte[] data = new byte[mRecBufSize];

        try {
            in = new FileInputStream(inFilename);
            out = new FileOutputStream(outFilename);

            totalAudioLen = in.getChannel().size();
            totalDataLen = totalAudioLen + 44;
            WriteWaveFileHeader(out, totalAudioLen, totalDataLen, byteRate);

            while (in.read(data) != -1) {
                out.write(data);
            }

            in.close();
            out.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void WriteWaveFileHeader(
            FileOutputStream out,
            long totalAudioLen,
            long totalDataLen,
            long byteRate) throws IOException {
        byte[] header = new byte[44];

        header[0] = 'R'; // RIFF/WAVE header  
        header[1] = 'I';
        header[2] = 'F';
        header[3] = 'F';
        header[4] = (byte) ((totalDataLen - 8) & 0xff);
        header[5] = (byte) (((totalDataLen - 8) >> 8) & 0xff);
        header[6] = (byte) (((totalDataLen - 8) >> 16) & 0xff);
        header[7] = (byte) (((totalDataLen - 8) >> 24) & 0xff);
        header[8] = 'W';
        header[9] = 'A';
        header[10] = 'V';
        header[11] = 'E';
        header[12] = 'f'; // 'fmt ' chunk
        header[13] = 'm';
        header[14] = 't';
        header[15] = ' ';
        header[16] = 16; // 4 bytes: size of 'fmt ' chunk
        header[17] = 0;
        header[18] = 0;
        header[19] = 0;
        header[20] = 1; // format = 1
        header[21] = 0;
        header[22] = (byte) mChannelCount;
        header[23] = 0;
        header[24] = (byte) (mSampleRate & 0xff);
        header[25] = (byte) ((mSampleRate >> 8) & 0xff);
        header[26] = (byte) ((mSampleRate >> 16) & 0xff);
        header[27] = (byte) ((mSampleRate >> 24) & 0xff);
        header[28] = (byte) (byteRate & 0xff);
        header[29] = (byte) ((byteRate >> 8) & 0xff);
        header[30] = (byte) ((byteRate >> 16) & 0xff);
        header[31] = (byte) ((byteRate >> 24) & 0xff);
        header[32] = (byte) (mChannelCount * 16 / 8); // block align
        header[33] = 0;
        header[34] = mRecorderBPP; // bits per sample  
        header[35] = 0;
        header[36] = 'd';
        header[37] = 'a';
        header[38] = 't';
        header[39] = 'a';
        header[40] = (byte) (totalAudioLen & 0xff);
        header[41] = (byte) ((totalAudioLen >> 8) & 0xff);
        header[42] = (byte) ((totalAudioLen >> 16) & 0xff);
        header[43] = (byte) ((totalAudioLen >> 24) & 0xff);

        out.write(header, 0, 44);
    }
}
