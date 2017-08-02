package net.impacthub.members.ui.widgets;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.TextView;

import net.impacthub.members.R;

import java.util.HashMap;
import java.util.Map;

class TypefaceResolver {

    static final Map<String, Typeface> typefaces = new HashMap<>();
    private final TypefaceCreator mTypefaceCreator;

    TypefaceResolver() {
        this(new TypefaceCreator());
    }

    private TypefaceResolver(TypefaceCreator typefaceCreator) {
        mTypefaceCreator = typefaceCreator;
    }

    void setTypeface(TextView textView, AttributeSet attributeSet) {
        if (!textView.isInEditMode()){
            resolveAndSetTypeface(textView, attributeSet);
        }
    }

    private void resolveAndSetTypeface(TextView textView, AttributeSet attributeSet) {
        Context context = textView.getContext();
        TypedArray styledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.custom_font);
        if (styledAttributes != null) {
            Typeface typeface = getTypeface(context, styledAttributes.getString(R.styleable.custom_font_typeface));
            if (typeface != null) {
                textView.setTypeface(typeface);
            }
            styledAttributes.recycle();
        }
    }

    @Nullable
    private Typeface getTypeface(Context context, String typefaceName) {
        if (typefaceName != null) {
            return resolveTypeface(context, typefaceName);
        }
        return null;
    }

    @Nullable
    private Typeface resolveTypeface(Context context, String typefaceName) {
        Typeface typeface = typefaces.get(typefaceName);
        if (typeface == null) {
            typeface = mTypefaceCreator.createFromAsset(context.getAssets(), typefaceName);
            if (typeface != null) {
                typefaces.put(typefaceName, typeface);
            }
        }
        return typeface;
    }
}
