/*
 * Copyright (C) 2007 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.ibotn.ibotncamera2.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;

import com.ibotn.ibotncamera2.CameraPreviewService;
import com.ibotn.ibotncamera2.services.SendCommandService;
import com.ibotn.ibotncamera2.utils.LogUtils;

/**
 * OperationReceiver
 *
 * @author
 */
public class OperationReceiver extends BroadcastReceiver {

    private final String TAG = OperationReceiver.class.getSimpleName();
    private Handler mHandler = new Handler();
    private final int DELAYED_MS = 100;
    private static final String EXTRA_IS_BACKGROUND = "is_background";
    public static final String EXTRA_WITH_AUDIO = "with_audio";
    /**
     * 拍照类型;0拍照，1抓拍，2连拍；
     */
    public static final String EXTRA_CAPTURE_TYPE = "capture_type";
    public static final String EXTRA_FUNCTION_TYPE = "function_type";
    public static final String EXTRA_WIDTH = "width";
    public static final String EXTRA_HEIGHT = "height";
    public static final String FUNCTION_TYPE_TAKE_PICTURE = "take_picture";
    public static final String FUNCTION_TYPE_START_VIDEO_RECORDING = "start_video_recording";
    public static final String FUNCTION_TYPE_STOP_VIDEO_RECORDING = "stop_video_recording";
    public static final String FUNCTION_TYPE_STOP_PREVIEW = "stop_preview";

    /**
     * 该字段作为广播action。<br/>
     * 适用于，语音【拍照】、【开始录像】，具体对应类型以extra来区分； <br/>
     * extra【拍照】："take_picture" ; extra【开始录像】："start_video_recording"<br/>
     * 新增extra字段"capture_type",//拍照类型;0拍照，1抓拍，2连拍；<br/>
     */
    public static final String ACTION_CAMERA_OPERATION = "com.ibotn.ibotncamera.ACTION_CAMERA_OPERATION";
    public static final String ACTION_ACK_NOTIFY = "com.ibotn.ibotncamera.ACTION_ACK_NOTIFY";

    @Override
    public void onReceive(final Context context, final Intent intent) {

        String action = intent.getAction();
        LogUtils.d(TAG, TAG + ">>>onReceive()>>action:" + action);
		/*if (Intent.ACTION_BOOT_COMPLETED.equals(action)) {
			if (!CameraPreviewService.isReady())
				context.startService(new Intent(Intent.ACTION_MAIN).setClass(context, CameraPreviewService.class));
		}else */
        if (ACTION_CAMERA_OPERATION.equals(action)) {
            boolean withAudio = intent.getBooleanExtra(EXTRA_WITH_AUDIO, true);
            String functionType = intent.getStringExtra(EXTRA_FUNCTION_TYPE);
            int captureType = intent.getIntExtra(EXTRA_CAPTURE_TYPE, 0);
            int width = intent.getIntExtra(EXTRA_WIDTH, -1);
            int height = intent.getIntExtra(EXTRA_HEIGHT, -1);
            Intent sIntent = new Intent(context, SendCommandService.class);
            sIntent.putExtra(EXTRA_FUNCTION_TYPE, functionType);
            sIntent.putExtra(EXTRA_WITH_AUDIO, withAudio);
            sIntent.putExtra(EXTRA_CAPTURE_TYPE, captureType);
            sIntent.putExtra(EXTRA_WIDTH, width);
            sIntent.putExtra(EXTRA_HEIGHT, height);
            context.startService(sIntent);
        }
    }
}
