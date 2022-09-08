package cat.customize.animation;

import android.graphics.Camera;
import android.graphics.Matrix;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.Transformation;

/**
 * Created by HSL
 * on 2022/9/8.
 */

public class Ratate3DAnimation extends Animation {
    private final float mFromDegrees;
    private final float mEndDegrees;
    private float mDepthZ = 400;
    private float mCenterX, mCenterY;
    private Camera mCamera;
    private boolean mReverse;

    public Ratate3DAnimation(float mFromDegrees, float mEndDegrees, boolean reverse) {
        this.mFromDegrees = mFromDegrees;
        this.mEndDegrees = mEndDegrees;
        this.mReverse = reverse;
    }

    @Override
    public void initialize(int width, int height, int parentWidth, int parentHeight) {
        super.initialize(width, height, parentWidth, parentHeight);
        mCamera = new Camera();
        mCenterX = width / 2;
        mCenterY = height / 2;
    }

    @Override
    protected void applyTransformation(float interpolatedTime, Transformation t) {
        float degrees = mFromDegrees + ((mEndDegrees - mFromDegrees) * interpolatedTime);
        mCamera.save();
        float z;
        if (mReverse) {
            z = mDepthZ * interpolatedTime;
            mCamera.translate(0.0f, 0.0f, z);
        } else {
            z = mDepthZ * (1.0f - interpolatedTime);
            mCamera.translate(0.0f, 0.0f, z);
        }

        final Matrix matrix = t.getMatrix();
        mCamera.rotateY(degrees);
        mCamera.getMatrix(matrix);
        mCamera.restore();

        matrix.preTranslate(-mCenterX, -mCenterY);
        matrix.postTranslate(mCenterX, mCenterY);
        super.applyTransformation(interpolatedTime, t);
    }
}
