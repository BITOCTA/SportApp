package com.bitocta.sportapp.util;

import android.text.InputFilter;
import android.text.Spanned;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class WeightFilter implements InputFilter {

    Pattern mPattern;
    private int maxDigitsBeforeDecimalPoint;
    private int maxDigitsAfterDecimalPoint;

    public WeightFilter(int digitsBeforeZero,int digitsAfterZero) {
        maxDigitsBeforeDecimalPoint = digitsBeforeZero;
        maxDigitsAfterDecimalPoint = digitsAfterZero;
    }

    @Override
    public CharSequence filter(CharSequence source, int start, int end,
                               Spanned dest, int dstart, int dend) {
        StringBuilder builder = new StringBuilder(dest);
        builder.replace(dstart, dend, source
                .subSequence(start, end).toString());
        if (!builder.toString().matches(
                "(([1-9]{1})([0-9]{0,"+(maxDigitsBeforeDecimalPoint-1)+"})?)?(\\.[0-9]{0,"+maxDigitsAfterDecimalPoint+"})?"

        )) {
            if(source.length()==0)
                return dest.subSequence(dstart, dend);
            return "";
        }

        return null;

    }

}
