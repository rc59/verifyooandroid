package com.software.exp.expapp2.Models;

import android.content.Context;

import com.software.exp.expapp2.R;

/**
 * Created by work on 16/02/2016.
 */
public class ShapesType {

    public enum ShapesTypeEnum {
        FIVE(0),EIGHT(1), ALETTER(2), RLETTER(3), TRIANGULAR(4),HEART(5),LINE(6);

        private int id;

        ShapesTypeEnum(int id) {
            this.id = id;
        }

        public int getID() {
            return id;
        }
    }

    static public String getTitle(int num, Context context){
        if (num == ShapesTypeEnum.FIVE.getID()){
            return context.getResources(). getString(R.string.draw_5);
        }

        else if(num == ShapesTypeEnum.EIGHT.getID()) {
            return context.getResources().getString(R.string.draw_eight);
        }

        else if (num == ShapesTypeEnum.ALETTER.getID()){
            return context.getResources(). getString(R.string.draw_a);
        }

        else if (num == ShapesTypeEnum.RLETTER.getID()){
            return context.getResources(). getString(R.string.draw_r);
        }

        else if (num == ShapesTypeEnum.TRIANGULAR.getID()){
            return context.getResources(). getString(R.string.draw_triangular);
        }
        else if(num == ShapesTypeEnum.HEART.getID()){
            return context.getResources().getString(R.string.draw_heart);
        }

        else {//LINE
            return context.getResources().getString(R.string.draw_line);
        }
    }

    static public String getInstructionName(int num){
        if (num == ShapesTypeEnum.FIVE.getID()){
            return ShapesTypeEnum.FIVE.name();
        }

        else if (num == ShapesTypeEnum.EIGHT.getID()){
            return ShapesTypeEnum.EIGHT.name();
        }
        else if (num == ShapesTypeEnum.ALETTER.getID()){
            return ShapesTypeEnum.ALETTER.name();

        }
        else if (num == ShapesTypeEnum.RLETTER.getID()){
            return ShapesTypeEnum.RLETTER.name();
        }

        else if (num == ShapesTypeEnum.TRIANGULAR.getID()){
            return ShapesTypeEnum.TRIANGULAR.name();
        }
        else if (num == ShapesTypeEnum.HEART.getID()){
            return ShapesTypeEnum.HEART.name();
        }
        else{//LINE
            return ShapesTypeEnum.LINE.name();
        }
    }


}
