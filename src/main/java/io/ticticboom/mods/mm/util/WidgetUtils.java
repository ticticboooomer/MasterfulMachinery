package io.ticticboom.mods.mm.util;

public class WidgetUtils {
    public static boolean isPointerWithin(int mX, int mY, int minX, int minY, int maxX, int maxY) {
        return mX > minX && mX < maxX && mY > minY && mY < maxY;
    }

    public static boolean isPointerWithinSized(int mX, int mY, int x, int y, int w, int h) {
        return isPointerWithin(mX, mY, x, y, x + w, y + h);
    }
}
