package com.example.theophilus.agriwaves.Utils;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.v7.widget.AppCompatTextView;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.util.AttributeSet;

/**
 * Created by Theophilus on 10/18/2018.
 */

public class TextViewJustify extends AppCompatTextView{

    private boolean justify;
    private float textAreaWidth;
    private float spaceCharSize;
    private float lineY;
    TextPaint paint;
    private boolean lastLine = false;
    boolean isLayoutRtl;

    public TextViewJustify(Context context) {
        super(context);
    }

    public TextViewJustify(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TextViewJustify(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    /**
     * @param attrs the attributes from the xml
     *              This function loads all the parameters from the xml
     */
    private void init(AttributeSet attrs) {

//        TypedArray ta = getContext().obtainStyledAttributes(attrs, R.styleable.DTextView, 0, 0);
//
//        justify = ta.getBoolean(R.styleable.DTextView_justify, false);
//
//        ta.recycle();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        //drawText(canvas);
        if (justify) {
            drawText(canvas);
            return;
        }
        super.onDraw(canvas);
    }

    private void drawText(Canvas canvas) {
        paint = getPaint();
        textAreaWidth = getMeasuredWidth() - (getPaddingLeft() + getPaddingRight());

        spaceCharSize = paint.measureText(" ");

        String text = getText().toString();
        lineY = getTextSize();

        Layout textLayout = getLayout();

        if (textLayout == null) {
            return;
        }

        isLayoutRtl = getLayoutDirection() == LAYOUT_DIRECTION_RTL;

        Paint.FontMetrics fm = paint.getFontMetrics();
        int textHeight = (int) Math.ceil(fm.descent - fm.ascent);
        textHeight = (int) (textHeight * getLineSpacingMultiplier() + textLayout.getSpacingAdd());

        int linesCount = textLayout.getLineCount();

        for (int i = 0; i < linesCount; i++) {
            if (i == linesCount - 1)
                lastLine = true;

            int lineStart = textLayout.getLineStart(i);
            int lineEnd = textLayout.getLineEnd(i);

            float lineWidth = textLayout.getLineWidth(i);

            String line = text.substring(lineStart, lineEnd);

            //take off spaces at the end or beginning of line (otherwise it will ruin the justification)
            if (line.charAt(0) == ' ') {
                line = line.substring(1, line.length());
            }

            //Take off space at end, if necessary
            if (line.charAt(line.length() - 1) == ' ') {
                line = line.substring(0, line.length() - 1);
            }

            if (line.equals("\n") || line.equals("")) {
                lineY += textHeight;
                continue;
            }

            if (isLayoutRtl) {
                line = flipRtl(line);
            }

            drawLineJustified(canvas, line, lineWidth);

            lineY += textHeight;
        }
    }

    private void drawLineJustified(Canvas canvas, String line, float lineWidth) {

        //Get how much "empty" space there is in the line
        float emptySpace = textAreaWidth - lineWidth;
        //Get the number of spaces in the String
        int spaces = line.split(" ").length - 1;
        //Calculate the size of the new space based on the width of the line.
        float newSpaceSize = (emptySpace / spaces) + spaceCharSize;

        float charX;

        if (isLayoutRtl && lastLine) {
            charX = emptySpace + getPaddingStart();
        } else {
            charX = getPaddingStart();
        }

        int chars = line.length() - 1;

        for (int i = 0; i <= chars; i++) {

            String character = String.valueOf(line.charAt(i));
            //float charWidth = StaticLayout.getDesiredWidth(character, paint);


//            if (!lastLine && character.equals(" ")) {
//                charX += newSpaceSize;
//            } else {
//                canvas.drawText(character, charX, lineY, paint);
//                charX += charWidth;
//            }

            float charWidth = StaticLayout.getDesiredWidth(character, paint);

            if (!character.equals(" ")) {
                canvas.drawText(character, charX, lineY, paint);
            }

            if (character.equals(" ") && i != chars && !lastLine)
                charX += newSpaceSize;
            else {
                charX += charWidth;
            }
        }
    }

    private String flipRtl(String originalLine) {
        String flipped = "";

        char[] lineChars = originalLine.toCharArray();
        String tempNonRtl = "";


        for (int i = lineChars.length - 1; i >= 0; i--) {

            char character = lineChars[i];

            if (isLatinLetter(character) || isNumericChar(character)) {
                tempNonRtl += character;
                continue;
            }

            if (i - 1 > 0) {
                if (character == '.' && isNumericChar(lineChars[i - 1])) {
                    tempNonRtl += character;
                    continue;
                }
            }

            switch (character) {
                case ')':
                    character = '(';
                    break;
                case '(':
                    character = ')';
                    break;
                case '[':
                    character = ']';
                    break;
                case ']':
                    character = '[';
                    break;
                case '{':
                    character = '}';
                    break;
                case '}':
                    character = '{';
                    break;
                case '>':
                    character = '<';
                    break;
                case '<':
                    character = '>';
                    break;
            }


            if (!tempNonRtl.equals("")) {
                flipped += mirrorString(tempNonRtl);
                tempNonRtl = "";
            }

            flipped += character;
        }

        getPaddingStart();

        return flipped;
    }

    private String mirrorString(String toMirror) {
        String mirror = "";

        for (int i = toMirror.length() - 1; i >= 0; i--) {
            mirror += toMirror.charAt(i);
        }

        return mirror;
    }

    boolean isLatinLetter(char character) {
        return (character >= 'A' && character <= 'Z') ||
                (character >= 'a' && character <= 'z');

    }

    boolean isNumericChar(char character) {
        return character <= '9' && character >= '0';
    }

    boolean isNumber(String number) {
        try {
            float num = Float.parseFloat(number);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
