package com.codepath.hackthehood.fragments;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.codepath.hackthehood.R;

import java.io.IOException;

public class VoiceRecordingDialog extends DialogFragment {

    private TextView tvNotify;
    private Button btnStartRecording, btnStopRecording,btnPlay;
    private MediaRecorder mRecorder = null;
    private MediaPlayer mPlayer;
    private String fileName, title;
    private int fieldNum;

    public VoiceRecordingDialog() {
        // Empty constructor required for DialogFragment
    }

    public static VoiceRecordingDialog newInstance(String title, int fieldNum) {
        VoiceRecordingDialog frag = new VoiceRecordingDialog();
        Bundle args = new Bundle();
        args.putString("title", title);
        args.putInt("fieldNum", fieldNum);
        frag.setArguments(args);
        return frag;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        title = getArguments().getString("title", "Voice Recording!!!");
        fieldNum = getArguments().getInt("fieldNum");
        fileName = Environment.getExternalStorageDirectory().getAbsolutePath();
        fileName += "/audiorecord"+fieldNum+".3gp";
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder b=  new  AlertDialog.Builder(getActivity())
                .setTitle(title)
                .setPositiveButton("Save Recording",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                Toast.makeText(getActivity(), "recordingURL: " + fileName, Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                            }
                        }
                )
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                dialog.dismiss();
                            }
                        }
                );

        LayoutInflater i = getActivity().getLayoutInflater();
        View view = i.inflate(R.layout.fragment_audio_recording_dialog, null);
        tvNotify = (TextView) view.findViewById(R.id.tvNotify);
        tvNotify.setVisibility(View.INVISIBLE);
        btnStartRecording = (Button) view.findViewById(R.id.btnRec);
        btnStopRecording = (Button) view.findViewById(R.id.btnStop);
        btnPlay = (Button) view.findViewById(R.id.btnPlay);
        setupOnStartRecording();
        setupOnStopRecording();
        setupOnPlayRecording();
        b.setView(view);
        return b.create();

    }

    private void setupOnStartRecording() {
        btnStartRecording.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                startRecording();
            }
        });
    }

    private void setupOnStopRecording() {
        btnStopRecording.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                stopRecording();
            }
        });
    }

    private void setupOnPlayRecording() {
        btnPlay.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                tvNotify.setVisibility(View.INVISIBLE);
                startPlaying();
            }
        });
    }

    private void startRecording() {
        tvNotify.setText("Recording...");
        tvNotify.setVisibility(View.VISIBLE);
        mRecorder = new MediaRecorder();
        mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        mRecorder.setOutputFile(fileName);
        mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
        try {
            mRecorder.prepare();
        } catch (IOException e) {
            Log.e("debug", "prepare() failed");
        }
        mRecorder.start();

    }

    private void startPlaying() {
        mPlayer = new MediaPlayer();
        try {
            mPlayer.setDataSource(fileName);
            mPlayer.prepare();
            mPlayer.start();
        } catch (IOException e) {
            Log.e("debug", "prepare() failed");
        }
    }

    private void stopRecording() {
        if (mRecorder != null) {
            tvNotify.setText("Recording stopped.");
            tvNotify.setVisibility(View.VISIBLE);
            mRecorder.stop();
            mRecorder.release();
            mRecorder = null;
        }
    }

}

