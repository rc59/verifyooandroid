package com.software.verifyoo.verifyooofflinesdk.ServerAPI.API;

import android.graphics.Point;
import android.os.Build;
import android.text.TextUtils;
import android.view.Display;
import android.view.WindowManager;

import com.software.verifyoo.verifyooofflinesdk.ServerAPI.Objects.ExpMotionEventCompact;
import com.software.verifyoo.verifyooofflinesdk.ServerAPI.Objects.ExpShape;
import com.software.verifyoo.verifyooofflinesdk.ServerAPI.Objects.ExpStroke;
import com.software.verifyoo.verifyooofflinesdk.ServerAPI.Objects.ExpTemplate;

import java.util.ArrayList;

import Data.UserProfile.Raw.Gesture;
import Data.UserProfile.Raw.MotionEventCompact;
import Data.UserProfile.Raw.Stroke;
import Data.UserProfile.Raw.Template;
import flexjson.JSONSerializer;

/**
 * Created by roy on 2/24/2016.
 */
public class ApiMgr {
    protected String mUserName;
    protected String mState;
    protected String mTemplate;
    protected String mCompany;
    protected double mXdpi;
    protected double mYdpi;
    WindowManager mWindowManager;

    public void StoreData(ApiMgrStoreDataParams params, Template template) {
        try {
            if (params.IsStoreData) {
                mCompany = params.Company;
                mState = params.State;
                mUserName = params.UserName;
                mTemplate = "";
                mWindowManager = params.WindowMgr;
                mXdpi = params.XDpi;
                mYdpi = params.YDpi;

                ConvertData(template);
                if (mTemplate != "") {
                    new ApiStoreTemplate().run(mTemplate);
                }
            }
        } catch (Exception exc) {
            String msg = exc.getMessage();
        }
    }

    private void ConvertData(Template template) {
        ExpTemplate tempExpTemplate = new ExpTemplate();

        tempExpTemplate.Company = mCompany;
        tempExpTemplate.Name = mUserName;
        tempExpTemplate.State = mState;
        tempExpTemplate.OS = Build.VERSION.RELEASE;
        tempExpTemplate.ModelName = getDeviceName();
        tempExpTemplate.DeviceId = Build.SERIAL;

        Display display = mWindowManager.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        tempExpTemplate.ScreenWidth = size.x;
        tempExpTemplate.ScreenHeight = size.y;
        tempExpTemplate.Xdpi = mXdpi;
        tempExpTemplate.Ydpi = mYdpi;

        ConvertTemplate(template, tempExpTemplate);

        JSONSerializer serializer = new JSONSerializer();
        mTemplate = serializer.deepSerialize(tempExpTemplate);
    }

    private void ConvertTemplate(Template templateReg, ExpTemplate tempExpTemplate) {
        Gesture tempGesture;
        ExpShape tempExpShape;
        Stroke tempStroke;
        ExpStroke tempExpStroke;
        MotionEventCompact tempEvent;
        ExpMotionEventCompact tempExpMotionEvent;
        for (int idxGesture = 0; idxGesture < templateReg.ListGestures.size(); idxGesture++) {
            tempGesture = templateReg.ListGestures.get(idxGesture);

            tempExpShape = new ExpShape();
            tempExpShape.Instruction = "";
            tempExpShape.Strokes = new ArrayList<>();

            for (int idxStroke = 0; idxStroke < tempGesture.ListStrokes.size(); idxStroke++) {
                tempStroke = tempGesture.ListStrokes.get(idxStroke);
                tempExpStroke = new ExpStroke();
                tempExpStroke.Length = tempStroke.Length;

                for (int idxEvent = 0; idxEvent < tempStroke.ListEvents.size(); idxEvent++) {
                    tempEvent = tempStroke.ListEvents.get(idxEvent);
                    tempExpMotionEvent = new ExpMotionEventCompact(tempEvent);
                    tempExpStroke.ListEvents.add(tempExpMotionEvent);
                }

                tempExpShape.Strokes.add(tempExpStroke);
            }

            tempExpTemplate.ExpShapeList.add(tempExpShape);
        }
    }

    private String getDeviceName() {
        String manufacturer = Build.MANUFACTURER;
        String model = Build.MODEL;
        if (model.startsWith(manufacturer)) {
            return capitalize(model);
        }
        if (manufacturer.equalsIgnoreCase("HTC")) {
            // make sure "HTC" is fully capitalized.
            return "HTC " + model;
        }
        return capitalize(manufacturer) + " " + model;
    }

    private static String capitalize(String str) {
        if (TextUtils.isEmpty(str)) {
            return str;
        }
        char[] arr = str.toCharArray();
        boolean capitalizeNext = true;
        String phrase = "";
        for (char c : arr) {
            if (capitalizeNext && Character.isLetter(c)) {
                phrase += Character.toUpperCase(c);
                capitalizeNext = false;
                continue;
            } else if (Character.isWhitespace(c)) {
                capitalizeNext = true;
            }
            phrase += c;
        }
        return phrase;
    }
}
