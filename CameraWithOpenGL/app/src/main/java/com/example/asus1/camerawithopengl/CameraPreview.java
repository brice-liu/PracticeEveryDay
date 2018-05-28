package com.example.asus1.camerawithopengl;

import android.app.Activity;
import android.content.Context;
import android.graphics.PixelFormat;
import android.hardware.Camera;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.WindowManager;

import java.io.IOException;

public class CameraPreview extends SurfaceView implements SurfaceHolder.Callback{

    private Camera mCamera;
    private SurfaceHolder mHolder;
    private Context mContext;
    private boolean isPreviwing = false;

    private static final String TAG = "CameraPreview";

    public CameraPreview(Context context) {
        this(context,null);
    }

    public CameraPreview(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public CameraPreview(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        mHolder = getHolder();
        mHolder.setFormat(PixelFormat.TRANSPARENT);
        mHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        mHolder.addCallback(this);
        Log.d(TAG, "CameraPreview: ");


    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        Log.d(TAG, "surfaceCreated: ");

    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        Log.d(TAG, "surfaceChanged: ");

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
//        if(mCamera!=null){
//            if(isPreviwing){
//                mCamera.stopPreview();
//                mCamera.release();
//                mCamera = null;
//                isPreviwing = false;
//            }
//        }

        Log.d(TAG, "surfaceDestroyed: ");

    }

    public void destoryCamera(){
        if(mCamera!=null){
            if(isPreviwing){
                mCamera.stopPreview();


            }
            mCamera.release();
            mCamera = null;
            isPreviwing = false;
        }
    }
    
    public void stopCamera(){
        if(mCamera!=null){
            if(isPreviwing){
                mCamera.stopPreview();
               // mCamera.release();
                //mCamera = null;
                isPreviwing = false;
            }
        }
    }

    public void  openCamera(){
        try {
        if(mCamera == null){
            mCamera = Camera.open(1);
            setCameraDisplayOrientation(mContext,1,mCamera);

        }
            mCamera.setPreviewDisplay(mHolder);
            mCamera.startPreview();
            isPreviwing = true;
        }catch (IOException e){
            e.printStackTrace();
        }

    }

    public static void setCameraDisplayOrientation(Context activity,
                                                   int cameraId, Camera camera
                                                   ){
        Camera.CameraInfo info = new Camera.CameraInfo();
        Camera.getCameraInfo(cameraId,info);
        int rotation = ((WindowManager)activity.getSystemService(Context.WINDOW_SERVICE)).
                getDefaultDisplay().getRotation();
        int degress = 0;
        switch (rotation){
            case Surface.ROTATION_0:
                degress = 0;
                break;
            case Surface.ROTATION_90:
                degress = 90;
                break;
            case Surface.ROTATION_180:
                degress = 180;
                break;
            case Surface.ROTATION_270:
                degress = 270;
                break;

        }
        int result;
        if(info.facing == Camera.CameraInfo.CAMERA_FACING_FRONT){
            result = (info.orientation+degress)%360;
            result = (360-result)%360;
        }else {
            result = (info.orientation - degress+360)%360;
        }
        camera.setDisplayOrientation(result);
    }
}
