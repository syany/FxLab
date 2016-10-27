/*
 * Copyright 2013-2016 the Uranoplums Foundation and the Others.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 *
 * $Id: KeyBoardUtils.java$
 */
package org.util;

import org.uranoplums.typical.util.UraUtils;


/**
 * KeyBoardUtilsクラス。<br>
 *
 * @since 2016/10/14
 * @author syany
 */
public class KeyBoardUtils extends UraUtils {

    // flat, sharp
    public static final int OCTAVE_OFFSET = 12;
    public static final int BASE_NOTE = 60;
    public static final int CHANEL_DRUM_PART = 9;

    /** ノートキー C: ド */
    public static final int NOTE_C = 0;
    public static final int NOTE_C_SHARP = 1;
    public static final int NOTE_D = 2;
    public static final int NOTE_D_SHARP = 3;
    public static final int NOTE_E = 4;
    public static final int NOTE_F = 5;
    public static final int NOTE_F_SHARP =6;
    public static final int NOTE_G = 7;
    public static final int NOTE_G_SHARP = 8;
    public static final int NOTE_A = 9;
    public static final int NOTE_A_SHARP = 10;
    public static final int NOTE_B = 11;

    public static final int getNote(int keyOffset) {
        return (keyOffset % OCTAVE_OFFSET);
    }

    public static final boolean isBlack(int note) {
        int noteBase = note % OCTAVE_OFFSET;
        return (noteBase == NOTE_C_SHARP) || (noteBase == NOTE_D_SHARP) ||
                (noteBase == NOTE_F_SHARP) || (noteBase == NOTE_G_SHARP) ||
                (noteBase == NOTE_A_SHARP);
    }


    public static final boolean isWhite(int note) {
        int noteBase = note % OCTAVE_OFFSET;
        return (noteBase == NOTE_C) || (noteBase == NOTE_D) || (noteBase == NOTE_E) ||
                (noteBase == NOTE_F) || (noteBase == NOTE_G) || (noteBase == NOTE_A) ||
                (noteBase == NOTE_B);
    }

    private KeyBoardUtils() {
        super();
    }
}
