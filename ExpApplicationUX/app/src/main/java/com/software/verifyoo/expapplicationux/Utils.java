package com.software.verifyoo.expapplicationux;

import android.content.Context;
import android.content.Intent;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by roy on 12/27/2016.
 */
public class Utils {
    public static ArrayList<String> Words = new ArrayList<>();

    public static String GetRandomWord4() {
        if(Words.size() == 0) {
            Words.add("abed");  Words.add("amps");  Words.add("aped");  Words.add("aper");  Words.add("apes");  Words.add("apse");  Words.add("arbs");  Words.add("ares");  Words.add("arfs");  Words.add("arks");  Words.add("arms");  Words.add("arse");  Words.add("bade");  Words.add("bads");  Words.add("bake");  Words.add("bams");  Words.add("baps");  Words.add("bard");  Words.add("bare");  Words.add("barf");  Words.add("bark");  Words.add("barm");  Words.add("bars");  Words.add("base");  Words.add("bask");  Words.add("bead");  Words.add("beak");  Words.add("beam");  Words.add("bear");  Words.add("beds");  Words.add("bema");  Words.add("berk");  Words.add("berm");  Words.add("brad");  Words.add("brae");  Words.add("bras");  Words.add("bred");  Words.add("dabs");  Words.add("daks");  Words.add("dame");  Words.add("damp");  Words.add("dams");  Words.add("daps");  Words.add("darb");  Words.add("dare");  Words.add("dark");  Words.add("deaf");  Words.add("dear");  Words.add("debs");  Words.add("derm");  Words.add("desk");  Words.add("drab");  Words.add("dram");  Words.add("drek");  Words.add("ears");  Words.add("emfs");  Words.add("eras");  Words.add("fabs");  Words.add("fade");  Words.add("fads");  Words.add("fake");  Words.add("fame");  Words.add("fard");  Words.add("fare");  Words.add("farm");  Words.add("fear");  Words.add("feds");  Words.add("fems");  Words.add("frae");  Words.add("frap");  Words.add("kabs");  Words.add("kaes");  Words.add("kafs");  Words.add("kame");  Words.add("kbar");  Words.add("keas");  Words.add("kefs");  Words.add("kemp");  Words.add("keps");  Words.add("kerb");  Words.add("kerf");  Words.add("mabe");  Words.add("made");  Words.add("mads");  Words.add("maes");  Words.add("make");  Words.add("maps");  Words.add("mare");  Words.add("mark");  Words.add("mars");  Words.add("mask");  Words.add("mead");  Words.add("meds");  Words.add("merk");  Words.add("mesa");  Words.add("pads");  Words.add("pams");  Words.add("pard");  Words.add("pare");  Words.add("park");  Words.add("pars");  Words.add("pase");  Words.add("peak");  Words.add("pear");  Words.add("peas");  Words.add("peds");  Words.add("perk");  Words.add("perm");  Words.add("pram");  Words.add("rads");  Words.add("rake");  Words.add("ramp");  Words.add("rams");  Words.add("raps");  Words.add("rase");  Words.add("rasp");  Words.add("read");  Words.add("ream");  Words.add("reap");  Words.add("rebs");  Words.add("reds");  Words.add("refs");  Words.add("rems");  Words.add("reps");  Words.add("sabe");  Words.add("sade");  Words.add("safe");  Words.add("sake");  Words.add("same");  Words.add("samp");  Words.add("sard");  Words.add("sark");  Words.add("seam");  Words.add("sear");  Words.add("sera");  Words.add("serf");  Words.add("skep");  Words.add("spae");  Words.add("spam");  Words.add("spar");  Words.add("sped");
        }

        Random random = new Random();
        int idxWord = random.nextInt(Words.size());
        return Words.get(idxWord).toUpperCase();
    }
    public static String GetRandomWord3() {
        String word = GetRandomWord4();
        return word.substring(0, 3).toUpperCase();
    }

    public static int CurrentExperiment;
    public static int CurrentActivity;

    public static int[] ExperimentOrder = null;

    public static int[] GetExperimentOrder() {
        if (ExperimentOrder == null) {
            ExperimentOrder = new int[3];
            ExperimentOrder[0] = 0;
            ExperimentOrder[1] = 1;
            ExperimentOrder[2] = 2;

            Random random = new Random();
            int idxToSwitch;
            int idxTemp;

            for (int idx = 0; idx < 3; idx++) {
                idxToSwitch = random.nextInt(3);

                idxTemp = ExperimentOrder[idxToSwitch];
                ExperimentOrder[idxToSwitch] = ExperimentOrder[idx];
                ExperimentOrder[idx] = idxTemp;
            }
        }

        return ExperimentOrder;
    }

    public static Intent GetNextActivity(Context appContext) {
        Intent i;
        int experiment = CurrentExperiment;
        int idxActivity = CurrentActivity;

        if(experiment >= 2 && idxActivity >= 4) {
            i = new Intent(appContext, Finished.class);
            return i;
        }

        if(experiment < 3 && idxActivity >= 4) {
            CurrentExperiment++;
            CurrentActivity = 0;

            i = new Intent(appContext, MainActivity.class);
            return i;
        }

        experiment = GetExperimentOrder()[experiment];

        switch (experiment) {
            default:
                i = GetExperiment1(idxActivity, appContext);
                break;
            case 0:
                i = GetExperiment1(idxActivity, appContext);
                break;
            case 1:
                i = GetExperiment2(idxActivity, appContext);
                break;
            case 2:
                i = GetExperiment3(idxActivity, appContext);
                break;
        }

        CurrentActivity++;

        return  i;
    }


    public static Intent GetExperiment1(int idx, Context appContext) {
        Intent i = null;

        switch (idx) {
            default:
                i = new Intent(appContext, InputActivity1.class);
            case 0:
                i = new Intent(appContext, InputActivity1.class);
            break;
            case 1:
                i = new Intent(appContext, InputActivity2.class);
                break;
            case 2:
                i = new Intent(appContext, InputActivity3.class);
                break;
            case 3:
                i = new Intent(appContext, InputActivity4.class);
                break;
        }

        return i;
    }

    public static Intent GetExperiment2(int idx, Context appContext) {
        Intent i = null;

        switch (idx) {
            default:
                i = new Intent(appContext, InputActivity3.class);
            case 0:
                i = new Intent(appContext, InputActivity3.class);
                break;
            case 1:
                i = new Intent(appContext, InputActivity2.class);
                break;
            case 2:
                i = new Intent(appContext, InputActivity4.class);
                break;
            case 3:
                i = new Intent(appContext, InputActivity1.class);
                break;
        }

        return i;
    }

    public static Intent GetExperiment3(int idx, Context appContext) {
        Intent i = null;

        switch (idx) {
            default:
                i = new Intent(appContext, InputActivity4.class);
            case 0:
                i = new Intent(appContext, InputActivity4.class);
                break;
            case 1:
                i = new Intent(appContext, InputActivity3.class);
                break;
            case 2:
                i = new Intent(appContext, InputActivity2.class);
                break;
            case 3:
                i = new Intent(appContext, InputActivity1.class);
                break;
        }

        return i;
    }
}
