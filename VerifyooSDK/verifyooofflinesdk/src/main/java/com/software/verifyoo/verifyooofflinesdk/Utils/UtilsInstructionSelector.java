package com.software.verifyoo.verifyooofflinesdk.Utils;

import java.util.ArrayList;
import java.util.Random;

import Data.UserProfile.Extended.TemplateExtended;

/**
 * Created by roy on 7/7/2016.
 */
public class UtilsInstructionSelector {
    protected TemplateExtended mTemplateExtended;

    protected ArrayList<String> mInstructionsAuth;
    protected ArrayList<String> mInstructionsExtra;

    protected String mInstructionExtra;

    protected Random mRandom;
    ArrayList<String> mListWords;

    public UtilsInstructionSelector(TemplateExtended templateExtended) {
        mTemplateExtended = templateExtended;

        createWords();
        init();
    }

    private void init() {
        mRandom = new Random();
        mInstructionExtra = "";
        mInstructionsAuth = new ArrayList<>();
        mInstructionsExtra = new ArrayList<>();

        int idxSelectedWord = mRandom.nextInt(mListWords.size());
        String selectedWord = mListWords.get(idxSelectedWord);

        int numGestures;
        boolean isGestureUsedForAuth;
        String tempInstruction;
        String tempChar;
        for(int idxChar = 0; idxChar < selectedWord.length(); idxChar++) {
            tempChar = selectedWord.substring(idxChar, idxChar + 1);
            for(int idxInstruction = 0; idxInstruction < Consts.TOTAL_NUM_GESTURES; idxInstruction++) {
                tempInstruction = UtilsInstructions.GetInstruction(idxInstruction);
                if (UtilsConvert.InstructionCodeToInstruction(tempInstruction).toLowerCase().compareTo(tempChar) == 0) {
                    mInstructionsAuth.add(tempInstruction);
                }
            }
        }
    }

    private void createWords() {
        mListWords = new ArrayList<>();

        mListWords.add("abed");  mListWords.add("adze");  mListWords.add("aged");  mListWords.add("ahed");  mListWords.add("aide");  mListWords.add("bade");  mListWords.add("bake");  mListWords.add("bead");  mListWords.add("beak");  mListWords.add("bide");  mListWords.add("bidi");  mListWords.add("biga");  mListWords.add("bike");  mListWords.add("bize");  mListWords.add("daze");  mListWords.add("deaf");  mListWords.add("defi dhak");  mListWords.add("dike");  mListWords.add("egad");  mListWords.add("fade");  mListWords.add("fake");  mListWords.add("faze");  mListWords.add("gadi");  mListWords.add("gaed");  mListWords.add("gaze");  mListWords.add("gibe");  mListWords.add("gied");  mListWords.add("hade");  mListWords.add("hadj");  mListWords.add("haed");  mListWords.add("haik");  mListWords.add("haji");  mListWords.add("hake");  mListWords.add("haze");  mListWords.add("head");  mListWords.add("hide");  mListWords.add("hied");  mListWords.add("hike");  mListWords.add("idea");  mListWords.add("jade");  mListWords.add("jake");  mListWords.add("jibe");  mListWords.add("kadi");  mListWords.add("kaif");  mListWords.add("khaf");  mListWords.add("kibe");  mListWords.add("kief aery");  mListWords.add("aims");  mListWords.add("ains");  mListWords.add("airn");  mListWords.add("airs");  mListWords.add("airt");  mListWords.add("airy");  mListWords.add("aits");  mListWords.add("amen");  mListWords.add("amie");  mListWords.add("amin");  mListWords.add("amir");  mListWords.add("amis");  mListWords.add("amps");  mListWords.add("anes");  mListWords.add("anew");  mListWords.add("anis");  mListWords.add("ante");  mListWords.add("anti");  mListWords.add("ants");  mListWords.add("aper");  mListWords.add("apes");  mListWords.add("apse");  mListWords.add("ares");  mListWords.add("arms");  mListWords.add("army");  mListWords.add("arse");  mListWords.add("arts");  mListWords.add("arty");  mListWords.add("ates");  mListWords.add("awes");  mListWords.add("awns");  mListWords.add("awny");  mListWords.add("awry");  mListWords.add("ayes");  mListWords.add("ayin");  mListWords.add("earn");  mListWords.add("ears");  mListWords.add("east");  mListWords.add("easy");  mListWords.add("eats");  mListWords.add("emir");  mListWords.add("emit");  mListWords.add("eras");  mListWords.add("erns");  mListWords.add("erst");  mListWords.add("espy");  mListWords.add("etas");  mListWords.add("etna");  mListWords.add("eyas");  mListWords.add("eyra");  mListWords.add("imps");  mListWords.add("ires");  mListWords.add("item");  mListWords.add("iter");  mListWords.add("maes");  mListWords.add("main");  mListWords.add("mair");  mListWords.add("mane");  mListWords.add("mans");  mListWords.add("many");  mListWords.add("maps");  mListWords.add("mare");  mListWords.add("mars");  mListWords.add("mart");  mListWords.add("mast");  mListWords.add("mate");  mListWords.add("mats");  mListWords.add("mawn");  mListWords.add("maws");  mListWords.add("mays");  mListWords.add("mean");  mListWords.add("meat mesa");  mListWords.add("meta");  mListWords.add("mews");  mListWords.add("mien");  mListWords.add("mina");  mListWords.add("mine");  mListWords.add("mint");  mListWords.add("mips");  mListWords.add("mire");  mListWords.add("mirs");  mListWords.add("miry");  mListWords.add("mise");  mListWords.add("mist");  mListWords.add("mite");  mListWords.add("mity");  mListWords.add("myna");  mListWords.add("name");  mListWords.add("nape");  mListWords.add("naps");  mListWords.add("nary");  mListWords.add("nays");  mListWords.add("neap");  mListWords.add("near");  mListWords.add("neat");  mListWords.add("nema");  mListWords.add("nest");  mListWords.add("nets");  mListWords.add("news");  mListWords.add("newt");  mListWords.add("nims");  mListWords.add("nipa");  mListWords.add("nips");  mListWords.add("nite");  mListWords.add("nits");  mListWords.add("pain");  mListWords.add("pair");  mListWords.add("pams");  mListWords.add("pane");  mListWords.add("pans");  mListWords.add("pant");  mListWords.add("pare");  mListWords.add("pars");  mListWords.add("part");  mListWords.add("pase");  mListWords.add("past");  mListWords.add("pate");  mListWords.add("pats");  mListWords.add("paty");  mListWords.add("pawn");  mListWords.add("paws");  mListWords.add("pays");  mListWords.add("pean");  mListWords.add("pear");  mListWords.add("peas");  mListWords.add("peat");  mListWords.add("pein");  mListWords.add("pens");  mListWords.add("pent");  mListWords.add("peri");  mListWords.add("perm");  mListWords.add("pert");  mListWords.add("pest");  mListWords.add("pets");  mListWords.add("pews");  mListWords.add("pian");  mListWords.add("pias");  mListWords.add("pier");  mListWords.add("pies");  mListWords.add("pima");  mListWords.add("pina");  mListWords.add("pine");  mListWords.add("pins");  mListWords.add("pint");  mListWords.add("piny");  mListWords.add("pirn");  mListWords.add("pita");  mListWords.add("pits");  mListWords.add("pity");  mListWords.add("pram");  mListWords.add("prat");  mListWords.add("pray");  mListWords.add("prey");  mListWords.add("prim");  mListWords.add("pyas");  mListWords.add("pyes");  mListWords.add("pyin");  mListWords.add("pyre");  mListWords.add("qats");  mListWords.add("rain");  mListWords.add("rais");  mListWords.add("rami");  mListWords.add("ramp");  mListWords.add("rams");  mListWords.add("rani");  mListWords.add("rant");  mListWords.add("rape");  mListWords.add("raps");  mListWords.add("rapt");  mListWords.add("rase");  mListWords.add("rasp");  mListWords.add("rate");  mListWords.add("rats");  mListWords.add("raws");  mListWords.add("rays");  mListWords.add("ream");  mListWords.add("reap");  mListWords.add("rein");  mListWords.add("reis");  mListWords.add("rems");  mListWords.add("rent");  mListWords.add("reps");  mListWords.add("rest");  mListWords.add("rets");  mListWords.add("rias");  mListWords.add("rime");  mListWords.add("rims");  mListWords.add("rimy");  mListWords.add("rins");  mListWords.add("ripe");  mListWords.add("rips");  mListWords.add("rise");  mListWords.add("rite");  mListWords.add("ryas");  mListWords.add("ryes");  mListWords.add("sain");  mListWords.add("same");  mListWords.add("samp");  mListWords.add("sane");  mListWords.add("sari");  mListWords.add("sate");  mListWords.add("sati");  mListWords.add("sawn");  mListWords.add("seam");  mListWords.add("sear");  mListWords.add("seat");  mListWords.add("semi");  mListWords.add("sent");  mListWords.add("sept");  mListWords.add("sera");  mListWords.add("seta");  mListWords.add("sewn");  mListWords.add("sima");  mListWords.add("simp");  mListWords.add("sine");  mListWords.add("sipe");  mListWords.add("sire");  mListWords.add("site");  mListWords.add("smew");  mListWords.add("smit");  mListWords.add("snap");  mListWords.add("snaw");  mListWords.add("snip");  mListWords.add("snit");  mListWords.add("snye");  mListWords.add("spae");  mListWords.add("spam");  mListWords.add("span");  mListWords.add("spar");  mListWords.add("spat");  mListWords.add("spay");  mListWords.add("spew");  mListWords.add("spin");  mListWords.add("spit");  mListWords.add("spry");  mListWords.add("star");  mListWords.add("staw");  mListWords.add("stay");  mListWords.add("stem");  mListWords.add("step");  mListWords.add("stew");  mListWords.add("stey");  mListWords.add("stir");  mListWords.add("stye");  mListWords.add("swam");  mListWords.add("swan");  mListWords.add("swap");  mListWords.add("swat");  mListWords.add("sway");  mListWords.add("swim");  mListWords.add("syne");  mListWords.add("tain");  mListWords.add("tame");  mListWords.add("tamp");  mListWords.add("tams");  mListWords.add("tans");  mListWords.add("tape");  mListWords.add("taps");  mListWords.add("tare");  mListWords.add("tarn");  mListWords.add("tarp");  mListWords.add("tars");  mListWords.add("taws");  mListWords.add("team");  mListWords.add("tear");  mListWords.add("teas");  mListWords.add("temp");  mListWords.add("tens");  mListWords.add("tepa");  mListWords.add("term");  mListWords.add("tern");  mListWords.add("tews");  mListWords.add("tier");  mListWords.add("ties");  mListWords.add("time");  mListWords.add("tine");  mListWords.add("tins");  mListWords.add("tiny");  mListWords.add("tips");  mListWords.add("tire");  mListWords.add("tram");  mListWords.add("trap");  mListWords.add("tray");  mListWords.add("tres");  mListWords.add("trey");  mListWords.add("trim");  mListWords.add("trip");  mListWords.add("tsar");  mListWords.add("twae");  mListWords.add("twas");  mListWords.add("twin");  mListWords.add("tyer");  mListWords.add("tyes");  mListWords.add("tyin");  mListWords.add("tyne");  mListWords.add("type");  mListWords.add("tyre");  mListWords.add("waes");  mListWords.add("wain");  mListWords.add("wair");  mListWords.add("wait");  mListWords.add("wame");  mListWords.add("wane");  mListWords.add("wans");  mListWords.add("want");  mListWords.add("wany");  mListWords.add("waps");  mListWords.add("ware");  mListWords.add("warm");  mListWords.add("warn");  mListWords.add("warp");  mListWords.add("wars");  mListWords.add("wart");  mListWords.add("wary");  mListWords.add("wasp");  mListWords.add("wast");  mListWords.add("wats");  mListWords.add("ways");  mListWords.add("wean");  mListWords.add("wear");  mListWords.add("weir");  mListWords.add("wens");  mListWords.add("went");  mListWords.add("wept");  mListWords.add("wert");  mListWords.add("west");  mListWords.add("wets");  mListWords.add("wimp");  mListWords.add("wine");  mListWords.add("wins");  mListWords.add("winy");  mListWords.add("wipe");  mListWords.add("wire");  mListWords.add("wiry");  mListWords.add("wise");  mListWords.add("wisp");  mListWords.add("wist");  mListWords.add("wite");  mListWords.add("wits");  mListWords.add("wrap");  mListWords.add("wren");  mListWords.add("writ");  mListWords.add("wyes");  mListWords.add("wyns");  mListWords.add("wyte");  mListWords.add("yams");  mListWords.add("yaps");  mListWords.add("yare");  mListWords.add("yarn");  mListWords.add("yawn");  mListWords.add("yawp");  mListWords.add("yaws");  mListWords.add("yean");  mListWords.add("year");  mListWords.add("yeas");  mListWords.add("yens");  mListWords.add("yeps");  mListWords.add("yeti");  mListWords.add("yews");  mListWords.add("yins");  mListWords.add("yipe");  mListWords.add("yips");  mListWords.add("ywis"); mListWords.add("amps");  mListWords.add("aped");  mListWords.add("aper");  mListWords.add("apes");  mListWords.add("apse");  mListWords.add("arbs");  mListWords.add("ares");  mListWords.add("arfs");  mListWords.add("arks");  mListWords.add("arms");  mListWords.add("arse");  mListWords.add("bade");  mListWords.add("bads");  mListWords.add("bake");  mListWords.add("bams");  mListWords.add("baps");  mListWords.add("bard");  mListWords.add("bare");  mListWords.add("bark");  mListWords.add("barm");  mListWords.add("bars");  mListWords.add("base");  mListWords.add("bask");  mListWords.add("bead");  mListWords.add("beak");  mListWords.add("beam");  mListWords.add("bear");  mListWords.add("beds");  mListWords.add("bema");  mListWords.add("berk");  mListWords.add("berm");  mListWords.add("brad");  mListWords.add("brae");  mListWords.add("bras");  mListWords.add("bred");  mListWords.add("dabs");  mListWords.add("daks");  mListWords.add("dame");  mListWords.add("damp");  mListWords.add("dams");  mListWords.add("daps");  mListWords.add("darb");  mListWords.add("dare");  mListWords.add("dark");  mListWords.add("deaf");  mListWords.add("dear");  mListWords.add("debs");  mListWords.add("derm");  mListWords.add("desk");  mListWords.add("drab");  mListWords.add("dram");  mListWords.add("drek");  mListWords.add("ears");  mListWords.add("emfs");  mListWords.add("eras");  mListWords.add("fabs");  mListWords.add("fade");  mListWords.add("fads");  mListWords.add("fake");  mListWords.add("fame");  mListWords.add("fard");  mListWords.add("fare");  mListWords.add("farm");  mListWords.add("fear");  mListWords.add("feds");  mListWords.add("fems");  mListWords.add("frae");  mListWords.add("frap");  mListWords.add("kabs");  mListWords.add("kaes");  mListWords.add("kafs");  mListWords.add("kame");  mListWords.add("kbar");  mListWords.add("keas");  mListWords.add("kefs");  mListWords.add("kemp");  mListWords.add("keps");  mListWords.add("kerb");  mListWords.add("kerf");  mListWords.add("mabe");  mListWords.add("made");  mListWords.add("mads");  mListWords.add("maes");  mListWords.add("make");  mListWords.add("maps");  mListWords.add("mare");  mListWords.add("mark");  mListWords.add("mars");  mListWords.add("mask");  mListWords.add("mead");  mListWords.add("meds");  mListWords.add("merk");  mListWords.add("mesa");  mListWords.add("pads");  mListWords.add("pams");  mListWords.add("pard");  mListWords.add("pare");  mListWords.add("park");  mListWords.add("pars");  mListWords.add("pase");  mListWords.add("peak");  mListWords.add("pear");  mListWords.add("peas");  mListWords.add("peds");  mListWords.add("perk");  mListWords.add("perm");  mListWords.add("pram");  mListWords.add("rads");  mListWords.add("rake");  mListWords.add("ramp");  mListWords.add("rams");  mListWords.add("raps");  mListWords.add("rase");  mListWords.add("rasp");  mListWords.add("read");  mListWords.add("ream");  mListWords.add("reap");  mListWords.add("rebs");  mListWords.add("reds");  mListWords.add("refs");  mListWords.add("rems");  mListWords.add("reps");  mListWords.add("sabe");  mListWords.add("sade");  mListWords.add("safe");  mListWords.add("sake");  mListWords.add("same");  mListWords.add("samp");  mListWords.add("sard");  mListWords.add("sark");  mListWords.add("seam");  mListWords.add("sear");  mListWords.add("sera");  mListWords.add("serf");  mListWords.add("skep");  mListWords.add("spae");  mListWords.add("spam");  mListWords.add("spar");  mListWords.add("sped");
    }

    private void init1() {
        mRandom = new Random();
        mInstructionsAuth = new ArrayList<>();
        mInstructionsExtra = new ArrayList<>();

        String tempInstruction;
        int numGestures;
        boolean isGestureUsedForAuth;

        for(int idx = 0; idx < Consts.TOTAL_NUM_GESTURES; idx++) {
            tempInstruction = UtilsInstructions.GetInstruction(idx);

            isGestureUsedForAuth = true;
            if (mTemplateExtended.GetHashGesturesByInstruction().containsKey(tempInstruction)) {
                numGestures = mTemplateExtended.GetHashGesturesByInstruction().get(tempInstruction).size();

                if (numGestures < Consts.DEFAULT_NUM_REPEATS_PER_INSTRUCTION) {
                    isGestureUsedForAuth = false;
                }
            }
            else {
                isGestureUsedForAuth = false;
            }

            if (isGestureUsedForAuth) {
                RandomAdd(mInstructionsAuth, tempInstruction);
            }
            else {
                RandomAdd(mInstructionsExtra, tempInstruction);
            }
        }

        int totalNumGestures = Consts.DEFAULT_NUM_REQ_GESTURES_AUTH;
        mInstructionExtra = "";
        if (mInstructionsExtra.size() > 0) {
            totalNumGestures--;
            mInstructionExtra = mInstructionsExtra.get(0);
        }

        while (mInstructionsAuth.size() > totalNumGestures) {
            mInstructionsAuth.remove(0);
        }
    }

    private void RandomAdd(ArrayList<String> listInstructions, String tempInstruction) {
        int numEelements = listInstructions.size();

        if (numEelements == 0) {
            listInstructions.add(tempInstruction);
        } else {
            int insertIdx = mRandom.nextInt(numEelements + 1);
            listInstructions.add(insertIdx, tempInstruction);
        }
    }

    public String GetInstructionExtra() {
        return mInstructionExtra;
    }

    public ArrayList<String> GetInstructionsAuth() {
        return mInstructionsAuth;
    }
}
