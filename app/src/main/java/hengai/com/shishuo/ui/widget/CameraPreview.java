package hengai.com.shishuo.ui.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.hardware.Camera;
import android.hardware.Camera.Parameters;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

@SuppressLint("ViewConstructor")
public class CameraPreview extends SurfaceView implements
		SurfaceHolder.Callback {
	private static final String TAG = "CameraPreview";
	private SurfaceHolder mHolder;
	private Camera mCamera;

	@SuppressWarnings("deprecation")
	public CameraPreview(Context context, Camera camera) {
		super(context);
		mCamera = camera;

		mHolder = getHolder();
		mHolder.addCallback(this);
		mHolder.setKeepScreenOn(true);
		mHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
	}

	public void surfaceCreated(SurfaceHolder holder) {
		try {
			mCamera.setPreviewDisplay(holder);
			mCamera.startPreview();
			openFlash();
		} catch (Exception e) {
			Log.d(TAG, "Error setting camera preview: " + e.getMessage());
		}
	}

	public void openFlash() {
		Parameters param = mCamera.getParameters();
		param.setFlashMode(Parameters.FLASH_MODE_OFF);
		mCamera.setParameters(param);
	}

	public void closeFlash() {
		Parameters param = mCamera.getParameters();
		param.setFlashMode(Parameters.FLASH_MODE_OFF);
		mCamera.setParameters(param);
	}

	public void surfaceDestroyed(SurfaceHolder holder) {
		// empty. Take care of releasing the Camera preview in your activity.
		// mHolder=null;
		// mCamera.setPreviewCallback(null) ;
		// mCamera.stopPreview();
		// mCamera.release();
		// mCamera=null;
		try {
			mCamera.stopPreview();
			openFlash();
		} catch (Exception e) {
			// ignore: tried to stop a non-existent preview
		}
	}

	public void surfaceChanged(SurfaceHolder holder, int format, int w, int h) {
		// If your preview can change or rotate, take care of those events here.
		// Make sure to stop the preview before resizing or reformatting it.

		if (mHolder.getSurface() == null) {
			// preview surface does not exist
			return;
		}

		// stop preview before making changes
		try {
			mCamera.stopPreview();
			openFlash();
		} catch (Exception e) {
			// ignore: tried to stop a non-existent preview
		}

		// set preview size and make any resize, rotate or
		// reformatting changes here

		// start preview with new settings
		try {
			mCamera.setPreviewDisplay(mHolder);
			mCamera.startPreview();

		} catch (Exception e) {
			// Log.d(TAG, "Error starting camera preview: " + e.getMessage());
		}
	}
}
